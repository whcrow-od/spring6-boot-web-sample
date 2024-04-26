package ua.od.whcrow.samples.spring6.boot_web.identity.web.mvc;

import ua.od.whcrow.samples.spring6.boot_web._global.mapping.EmbeddableAuditDto;

import java.util.Set;
import java.util.UUID;

record IdentityDto(
		UUID id,
		String username,
		boolean enabled,
		Set<String> roles,
		String email,
		String firstName,
		String lastName,
		Set<String> authorities,
		EmbeddableAuditDto audit
) {}
