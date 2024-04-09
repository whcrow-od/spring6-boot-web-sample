package ua.od.whcrow.samples.spring6.boot_web.identity;

import jakarta.annotation.Nonnull;
import ua.od.whcrow.samples.spring6.boot_web._commons.model.ModelService;

import java.util.Optional;
import java.util.UUID;

public interface IdentityService extends ModelService<Identity,UUID> {
	
	@Nonnull
	@Override
	default Class<Identity> getModelClass() {
		return Identity.class;
	}
	
	@Nonnull
	@Override
	default Class<UUID> getIdClass() {
		return UUID.class;
	}
	
	@Nonnull
	Optional<Identity> findByUsername(@Nonnull String username);
	
}
