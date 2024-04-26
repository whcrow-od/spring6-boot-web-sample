package ua.od.whcrow.samples.spring6.boot_web;

import jakarta.annotation.Nonnull;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ua.od.whcrow.samples.spring6.boot_web._commons.model.EmbeddableAudit;
import ua.od.whcrow.samples.spring6.boot_web.identity.Identity;
import ua.od.whcrow.samples.spring6.boot_web.identity.Role;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
public class PersistedModelMockProvider {
	
	private static final int MIN_IN_DAY = 1440;
	
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public EmbeddableAudit persistedEmbeddableAudit() {
		EmbeddableAudit embeddableAudit = new EmbeddableAudit();
		embeddableAudit.setCreatedBy(buildIdentityUsername());
		embeddableAudit.setCreatedOn(LocalDateTime.now()
				.minusDays(2)
				.minusMinutes(ThreadLocalRandom.current().nextInt(MIN_IN_DAY)));
		embeddableAudit.setModifiedBy(buildIdentityUsername());
		embeddableAudit.setModifiedOn(LocalDateTime.now()
				.minusDays(1)
				.minusMinutes(ThreadLocalRandom.current().nextInt(MIN_IN_DAY)));
		return embeddableAudit;
	}
	
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public Role persistedRole() {
		Role role = new Role();
		role.setId(UUID.randomUUID());
		role.setName(RandomStringUtils.randomAlphanumeric(1, Role.NAME_MAX_LENGTH));
		role.setAudit(persistedEmbeddableAudit());
		return role;
	}
	
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public Identity persistedIdentity() {
		Identity identity = new Identity();
		identity.setId(UUID.randomUUID());
		identity.setUsername(buildIdentityUsername());
		identity.setPassword(RandomStringUtils.randomAlphanumeric(Identity.PASSWORD_LENGTH));
		identity.setEmail(RandomStringUtils.randomAlphanumeric(1, 10) + "@"
				+ RandomStringUtils.randomAlphanumeric(1, 10) + "." + RandomStringUtils.randomAlphabetic(1, 3));
		identity.setFirstName(RandomStringUtils.randomAscii(1, Identity.FIRST_NAME_MAX_LENGTH));
		identity.setLastName(RandomStringUtils.randomAscii(1, Identity.LAST_NAME_MAX_LENGTH));
		identity.setAudit(persistedEmbeddableAudit());
		return identity;
	}
	
	@Nonnull
	private String buildIdentityUsername() {
		return RandomStringUtils.randomAscii(Identity.USERNAME_MIN_LENGTH, Identity.USERNAME_MAX_LENGTH);
	}
	
}
