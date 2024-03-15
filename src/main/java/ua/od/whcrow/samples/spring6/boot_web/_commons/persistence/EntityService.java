package ua.od.whcrow.samples.spring6.boot_web._commons.persistence;

import jakarta.annotation.Nonnull;

public interface EntityService<ENT, ID> {
	
	@Nonnull
	Class<ENT> getEntityClass();
	
	@Nonnull
	Class<ID> getIdClass();
	
}
