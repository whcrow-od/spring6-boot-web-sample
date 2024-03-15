package ua.od.whcrow.samples.spring6.boot_web._global.mapping;

import java.time.LocalDateTime;

public record EmbeddableAuditDto(
		String createdBy,
		LocalDateTime createdOn,
		String modifiedBy,
		LocalDateTime modifiedOn
) {}
