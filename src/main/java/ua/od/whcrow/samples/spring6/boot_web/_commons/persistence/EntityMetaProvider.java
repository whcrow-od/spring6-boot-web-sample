package ua.od.whcrow.samples.spring6.boot_web._commons.persistence;

import jakarta.annotation.Nonnull;
import ua.od.whcrow.samples.spring6.boot_web._commons.persistence.exceptions.EntityAttributeNotFoundException;

import java.util.Optional;

public interface EntityMetaProvider<ENT> {
	
	@Nonnull
	Class<ENT> getEntityClass();
	
	@Nonnull
	EntityMeta getEntityMeta();
	
	@Nonnull
	AttributeMeta getIdAttributeMeta();
	
	@Nonnull
	AttributeMeta getDeclaredAttributeMeta(@Nonnull String name)
			throws EntityAttributeNotFoundException;
	
	@Nonnull
	AttributeMeta getAttributeMeta(@Nonnull String name)
			throws EntityAttributeNotFoundException;
	
	@Nonnull
	Optional<AttributeMeta> findDeclaredAttributeMeta(@Nonnull String name);
	
	@Nonnull
	Optional<AttributeMeta> findAttributeMeta(@Nonnull String name);
	
}
