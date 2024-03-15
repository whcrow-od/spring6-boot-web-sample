package ua.od.whcrow.samples.spring6.boot_web._commons.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public abstract class UuidRevisionableEntity extends RevisionableEntity<UUID> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(
			name = "id",
			updatable = false
	)
	@JdbcTypeCode(Types.VARCHAR)
	private UUID id;
	
}
