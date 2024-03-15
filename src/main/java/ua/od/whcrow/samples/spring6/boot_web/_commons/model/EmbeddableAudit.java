package ua.od.whcrow.samples.spring6.boot_web._commons.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import ua.od.whcrow.samples.spring6.boot_web._commons.persistence.Stringable;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class EmbeddableAudit extends Stringable {
	
	@CreatedBy
	@NotBlank
	@ColumnDefault("'admin'")
	@Column(
			name = "created_by",
			nullable = false,
			updatable = false
	)
	private String createdBy;
	
	@CreatedDate
	@ColumnDefault("'1970-01-01'")
	@Column(
			name = "created_on",
			nullable = false,
			updatable = false
	)
	private LocalDateTime createdOn;
	
	@LastModifiedBy
	@Column(name = "modified_by")
	private String modifiedBy;
	
	@LastModifiedDate
	@Column(name = "modified_on")
	private LocalDateTime modifiedOn;
	
}
