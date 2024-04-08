package ua.od.whcrow.samples.spring6.boot_web.identity.web;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ua.od.whcrow.samples.spring6.boot_web._commons.mapping.ElectiveMapper;
import ua.od.whcrow.samples.spring6.boot_web._commons.util.Assert;
import ua.od.whcrow.samples.spring6.boot_web._global.mapping.CommonMappings;
import ua.od.whcrow.samples.spring6.boot_web.identity.Identity;
import ua.od.whcrow.samples.spring6.boot_web.identity.Role;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
abstract class IdentityMapper implements CommonMappings, ElectiveMapper {
	
	@Nullable
	@Override
	public Object createIfApplicable(@Nonnull Object source, @Nonnull Class<?> target) {
		if (source instanceof IdentityPersistDto dto && target.equals(Identity.class)) {
			return map(dto);
		}
		return null;
	}
	
	@Nullable
	@Override
	public Object updateIfApplicable(@Nonnull Object source, @Nonnull Object target) {
		if (source instanceof IdentityPersistDto dto && target instanceof Identity identity) {
			map(dto, identity);
			return identity;
		}
		return null;
	}
	
	public abstract List<IdentityProvideDto> map(List<Identity> all);
	
	public abstract Identity map(IdentityPersistDto source);
	
	public abstract IdentityProvideDto map(Identity source);
	
	public abstract void map(IdentityPersistDto source, @MappingTarget Identity target);
	
	@Nonnull
	protected Set<String> map(@Nonnull Set<Role> source) {
		Assert.notNull(source, "source Set of Roles");
		return source.stream().map(Role::getName).collect(Collectors.toSet());
	}
	
}
