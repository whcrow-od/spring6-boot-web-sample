package ua.od.whcrow.samples.spring6.boot_web._commons.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@MappedSuperclass
public abstract class RevisionableModel<T> extends AbstractModel<T> {
	
	@Version
	@Column(name = "revision")
	private volatile Long revision;
	
	protected void setRevision(Long revision) {
		this.revision = revision;
	}
	
}
