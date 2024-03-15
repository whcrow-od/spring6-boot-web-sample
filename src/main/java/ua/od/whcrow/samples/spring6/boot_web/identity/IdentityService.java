package ua.od.whcrow.samples.spring6.boot_web.identity;

import jakarta.annotation.Nonnull;
import ua.od.whcrow.samples.spring6.boot_web._commons.persistence.EntityProvider;

import java.util.Optional;
import java.util.UUID;

public interface IdentityService extends EntityProvider<Identity,UUID> {
	
	@Nonnull
	@Override
	default Class<Identity> getEntityClass() {
		return Identity.class;
	}
	
	@Nonnull
	@Override
	default Class<UUID> getIdClass() {
		return UUID.class;
	}
	
	@Nonnull
	Optional<Identity> findByUsername(@Nonnull String username);
	
	@Nonnull
	Identity save(@Nonnull Identity identity);
	
}
