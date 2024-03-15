package ua.od.whcrow.samples.spring6.boot_web.identity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ua.od.whcrow.samples.spring6.boot_web._commons.model.EmbeddableAudit;
import ua.od.whcrow.samples.spring6.boot_web._commons.persistence.Stringable;
import ua.od.whcrow.samples.spring6.boot_web._commons.persistence.listeners.LoggingEntityListener;
import ua.od.whcrow.samples.spring6.boot_web._global.Operation;

import java.sql.Types;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners({AuditingEntityListener.class, LoggingEntityListener.class})
@Table(
		name = "identities",
		indexes = {
				@Index(name = "idx__identities__created_by", columnList = "created_by"),
				@Index(name = "idx__identities__created_on", columnList = "created_on"),
				@Index(name = "idx__identities__modified_by", columnList = "modified_by"),
				@Index(name = "idx__identities__modified_on", columnList = "modified_on"),
		},
		uniqueConstraints = {
				@UniqueConstraint(name = "uk__identities__username", columnNames = "username"),
		}
)
public class Identity extends Principal {
	
	public static final int EMAIL_MAX_LENGTH = 320;
	
	public static final int USERNAME_MIN_LENGTH = 3;
	public static final int USERNAME_MAX_LENGTH = EMAIL_MAX_LENGTH;
	
	// BCrypted password length
	public static final int PASSWORD_LENGTH = 60;
	
	public static final int FIRST_NAME_MAX_LENGTH = 31;
	public static final int LAST_NAME_MAX_LENGTH = 63;
	
	@NotNull
	@NotBlank
	@Length(min = USERNAME_MIN_LENGTH, max = USERNAME_MAX_LENGTH)
	@Column(
			name = "username",
			nullable = false,
			length = USERNAME_MAX_LENGTH
	)
	private String username;
	
	@NotBlank
	@Column(
			name = "password",
			nullable = false,
			// BCrypted password length
			length = PASSWORD_LENGTH
	)
	@JdbcTypeCode(Types.CHAR)
	private String password;
	
	/*@ElementCollection(
			targetClass = Authority.class,
			fetch = FetchType.EAGER
	)
	@CollectionTable(
			name = "user_authority",
			joinColumns = @JoinColumn(
					name = "user_id",
					nullable = false,
					foreignKey = @ForeignKey(name = "fk__user_authority__user")
			)
	)
	@Enumerated(EnumType.STRING)
	@Column(
			name = "authority",
			nullable = false
	)
	private Set<Authority> authorities;*/
	
	@ColumnDefault("TRUE")
	@Column(name = "enabled")
	private boolean enabled = true;
	
	@ManyToMany(
			mappedBy = "identities",
			fetch = FetchType.EAGER
	)
	private Set<Role> roles = new LinkedHashSet<>();
	
	@Email
	@Column(
			name = "email",
			length = EMAIL_MAX_LENGTH
	)
	private String email;
	
	@Column(
			name = "first_name",
			length = FIRST_NAME_MAX_LENGTH
	)
	private String firstName;
	
	@Column(
			name = "last_name",
			length = LAST_NAME_MAX_LENGTH
	)
	private String lastName;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
			name = "identity_allowed_operations",
			joinColumns = @JoinColumn(
					name = "identity_id",
					nullable = false,
					foreignKey = @ForeignKey(name = "fk__identity_allowed_operations__identities")
			),
			indexes = @Index(name = "idx__identity_allowed_operations__operation_id", columnList = "operation_id")
	)
	@Column(
			name = "operation_id",
			nullable = false
	)
	private Set<Operation> allowedOperations = new LinkedHashSet<>();
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
			name = "identity_disallowed_operations",
			joinColumns = @JoinColumn(
					name = "identity_id",
					nullable = false,
					foreignKey = @ForeignKey(name = "fk__identity_disallowed_operations__identities")
			),
			indexes = @Index(name = "idx__identity_disallowed_operations__operation_id", columnList = "operation_id")
	)
	@Column(
			name = "operation_id",
			nullable = false
	)
	private Set<Operation> disallowedOperations = new LinkedHashSet<>();
	
	@Embedded
	private EmbeddableAudit audit = new EmbeddableAudit();
	
	@Transient
	@Override
	public String getName() {
		return getId() == null ? null : String.valueOf(getId());
	}
	
	@Stringable.Ignore
	public String getPassword() {
		return password;
	}
	
	@Transient
	@Nonnull
	@Override
	public Set<String> getAuthorities() {
		Set<String> authorities = new LinkedHashSet<>();
		roles.stream().map(Role::getAuthorities).forEach(authorities::addAll);
		authorities.addAll(allowedOperations.stream().map(Operation::name).collect(Collectors.toSet()));
		authorities.removeAll(disallowedOperations.stream().map(Operation::name).collect(Collectors.toSet()));
		return authorities;
	}
	
}
