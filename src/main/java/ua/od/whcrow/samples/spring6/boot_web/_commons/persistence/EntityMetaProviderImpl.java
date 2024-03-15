package ua.od.whcrow.samples.spring6.boot_web._commons.persistence;

import jakarta.annotation.Nonnull;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.EntityType;
import ua.od.whcrow.samples.spring6.boot_web._commons.collections.LimitedSizeHashMap;
import ua.od.whcrow.samples.spring6.boot_web._commons.persistence.exceptions.EntityAttributeNotFoundException;
import ua.od.whcrow.samples.spring6.boot_web._commons.util.Assert;

import java.util.Optional;

/**
 * Spring Boot v3 tries to autodetect custom repository implementation fragments by scanning for classes <b>below the
 * package in which it found a repository</b> (actually the custom repository interface also should be there).
 * Therefore, this implementation cannot be found by
 * org.springframework.data.repository.config.CustomRepositoryImplementationDetector. Hope this will become configurable
 * in the future.
 */
public abstract class EntityMetaProviderImpl<ENT> implements EntityMetaProvider<ENT> {
	
	private static final int DEEP_ATTRIBUTES_CACHE_SIZE = 128;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private EntityType<ENT> entityType;
	private EntityMeta entityMeta;
	private final LimitedSizeHashMap<String,AttributeMeta> deepAttributesCache =
			new LimitedSizeHashMap<>(DEEP_ATTRIBUTES_CACHE_SIZE);
	
	@PostConstruct
	protected void postConstruct() {
		entityType = entityManager.getMetamodel().entity(getEntityClass());
		entityMeta = EntityMetaImpl.of(entityType);
	}
	
	@Nonnull
	protected EntityType<ENT> _getEntityType() {
		return entityType;
	}
	
	protected AttributeMeta _getDeepAttributeMeta(@Nonnull String name, boolean optional) {
		synchronized (deepAttributesCache) {
			if (deepAttributesCache.containsKey(name)) {
				return deepAttributesCache.get(name);
			}
			Attribute<?,?> attribute;
			try {
				attribute = _getEntityType().getAttribute(name);
			} catch (IllegalArgumentException e) {
				if (optional) {
					deepAttributesCache.put(name, null);
					return null;
				}
				throw new EntityAttributeNotFoundException(getEntityClass(), name, e);
			}
			return deepAttributesCache.put(name, AttributeMetaImpl.of(attribute));
		}
	}
	
	protected AttributeMeta _getAttributeMeta(@Nonnull String name, boolean optional) {
		return findDeclaredAttributeMeta(name)
				.orElseGet(() -> _getDeepAttributeMeta(name, optional));
	}
	
	@Nonnull
	@Override
	public EntityMeta getEntityMeta() {
		return entityMeta;
	}
	
	@Nonnull
	@Override
	public AttributeMeta getIdAttributeMeta() {
		return getEntityMeta().getIdAttributeMeta();
	}
	
	@Nonnull
	@Override
	public AttributeMeta getDeclaredAttributeMeta(@Nonnull String name)
			throws EntityAttributeNotFoundException {
		return findDeclaredAttributeMeta(name)
				.orElseThrow(() -> new EntityAttributeNotFoundException(getEntityClass(), name));
	}
	
	@Nonnull
	@Override
	public AttributeMeta getAttributeMeta(@Nonnull String name)
			throws EntityAttributeNotFoundException {
		return _getAttributeMeta(name, false);
	}
	
	@Nonnull
	@Override
	public Optional<AttributeMeta> findDeclaredAttributeMeta(@Nonnull String name) {
		Assert.notNull(name, "attribute name");
		return getEntityMeta().findAttributeMeta(name);
	}
	
	@Nonnull
	@Override
	public Optional<AttributeMeta> findAttributeMeta(@Nonnull String name) {
		return Optional.ofNullable(_getAttributeMeta(name, true));
	}
	
}
