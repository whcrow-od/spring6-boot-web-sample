package ua.od.whcrow.samples.spring6.boot_web._global.mapping;

import jakarta.annotation.Nullable;
import ua.od.whcrow.samples.spring6.boot_web._commons.model.EmbeddableAudit;

public interface CommonMappings {
	
	@Nullable
	EmbeddableAuditDto map(@Nullable EmbeddableAudit source);
	
}
