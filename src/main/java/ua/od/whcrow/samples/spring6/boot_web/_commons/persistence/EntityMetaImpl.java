package ua.od.whcrow.samples.spring6.boot_web._commons.persistence;

import jakarta.annotation.Nonnull;
import jakarta.persistence.metamodel.EntityType;
import ua.od.whcrow.samples.spring6.boot_web._commons.persistence.exceptions.EntityIdAttributeNotFoundException;
import ua.od.whcrow.samples.spring6.boot_web._commons.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

record EntityMetaImpl(
		@Nonnull
		String name,
		
		@Nonnull
		Class<?> type,
		
		@Nonnull
		AttributeMeta idAttributeMeta,
		
		@Nonnull
		List<AttributeMeta> attributeMetas

) implements EntityMeta {
	
	@Nonnull
	static EntityMetaImpl of(@Nonnull EntityType<?> entityType)
			throws EntityIdAttributeNotFoundException {
		Assert.notNull(entityType, "entity meta type");
		List<AttributeMeta> attributes = entityType.getAttributes().stream()
				.map(AttributeMetaImpl::of)
				.collect(Collectors.toList());
		AttributeMeta idAttribute = attributes.stream()
				.filter(AttributeMeta::isId)
				.findFirst()
				.orElseThrow(() -> new EntityIdAttributeNotFoundException(entityType.getJavaType()));
		return new EntityMetaImpl(entityType.getName(), entityType.getJavaType(), idAttribute, attributes);
	}
	
	@Nonnull
	@Override
	public String getName() {
		return name();
	}
	
	@Nonnull
	@Override
	public Class<?> getType() {
		return type();
	}
	
	@Nonnull
	@Override
	public AttributeMeta getIdAttributeMeta() {
		return idAttributeMeta();
	}
	
	@Nonnull
	@Override
	public List<AttributeMeta> getAttributeMetas() {
		return attributeMetas();
	}
	
}
