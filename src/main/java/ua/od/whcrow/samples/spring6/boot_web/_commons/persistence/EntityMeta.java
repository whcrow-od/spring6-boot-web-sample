package ua.od.whcrow.samples.spring6.boot_web._commons.persistence;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.List;
import java.util.Optional;

public interface EntityMeta {
	
	@Nonnull
	String getName();
	
	@Nonnull
	Class<?> getType();
	
	@Nonnull
	AttributeMeta getIdAttributeMeta();
	
	/**
	 * @return list of meta info about the entity declared attributes
	 */
	@Nonnull
	List<AttributeMeta> getAttributeMetas();
	
	/**
	 * @return meta info about the corresponding declared attribute
	 */
	@Nonnull
	default Optional<AttributeMeta> findAttributeMeta(@Nonnull String name) {
		return getAttributeMetas().stream()
				.filter(attr -> attr.getName().equals(name))
				.findFirst();
	}
	
	/**
	 * @return {@code true} if entity has the corresponding declared attribute
	 */
	default boolean hasAttributeMeta(@Nonnull String name) {
		return getAttributeMetas().stream()
				.anyMatch(attr -> attr.getName().equals(name));
	}
	
}
