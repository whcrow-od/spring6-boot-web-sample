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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ua.od.whcrow.samples.spring6.boot_web._commons.model.EmbeddableAudit;
import ua.od.whcrow.samples.spring6.boot_web._commons.persistence.listeners.LoggingEntityListener;
import ua.od.whcrow.samples.spring6.boot_web._global.Operation;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners({AuditingEntityListener.class, LoggingEntityListener.class})
@Table(
		name = "roles",
		indexes = {
				@Index(name = "idx__roles__created_by", columnList = "created_by"),
				@Index(name = "idx__roles__created_on", columnList = "created_on"),
				@Index(name = "idx__roles__modified_by", columnList = "modified_by"),
				@Index(name = "idx__roles__modified_on", columnList = "modified_on"),
		},
		uniqueConstraints = {
				@UniqueConstraint(name = "uk__roles__name", columnNames = "name"),
		}
)
public class Role extends Principal {
	
	public static final String ROLE_PREFIX = "ROLE_";
	public static final int NAME_MAX_LENGTH = 31;
	
	@NotNull
	@Column(
			name = "name",
			length = NAME_MAX_LENGTH,
			nullable = false
	)
	private String name;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "role_identity_junction",
			joinColumns = @JoinColumn(name = "role_id"),
			foreignKey = @ForeignKey(name = "fk__role_identity_junction__role"),
			inverseJoinColumns = @JoinColumn(name = "identity_id"),
			inverseForeignKey = @ForeignKey(name = "fk__role_identity_junction__identity")
	)
	private Set<Identity> identities = new LinkedHashSet<>();
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
			name = "role_allowed_operations",
			joinColumns = @JoinColumn(
					name = "role_id",
					nullable = false,
					foreignKey = @ForeignKey(name = "fk__role_allowed_operations__roles")
			),
			indexes = @Index(name = "idx__role_allowed_operations__operation_id", columnList = "operation_id")
	)
	@Column(
			name = "operation_id",
			nullable = false
	)
	private Set<Operation> allowedOperations = new LinkedHashSet<>();
	
	/*@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
			name = "role_disallowed_operations",
			joinColumns = @JoinColumn(
					name = "role_id",
					nullable = false,
					foreignKey = @ForeignKey(name = "fk__role_disallowed_operations__roles")
			),
			indexes = @Index(name = "idx__role_disallowed_operations__operation_id", columnList = "operation_id")
	)
	@Column(
			name = "operation_id",
			nullable = false
	)
	private Set<Operation> disallowedOperations = new LinkedHashSet<>();*/
	
	@Embedded
	private EmbeddableAudit audit = new EmbeddableAudit();
	
	@Transient
	@Nonnull
	@Override
	public Set<String> getAuthorities() {
		Set<String> authorities = new LinkedHashSet<>();
		String role = getName();
		if (!role.startsWith(ROLE_PREFIX)) {
			role = ROLE_PREFIX + role;
		}
		authorities.add(role);
		authorities.addAll(allowedOperations.stream()
				.map(Operation::name)
				.collect(Collectors.toSet()));
		//authorities.removeAll(disallowedOperations.stream().map(Operation::name).collect(Collectors.toSet()));
		return authorities;
	}
	
}
