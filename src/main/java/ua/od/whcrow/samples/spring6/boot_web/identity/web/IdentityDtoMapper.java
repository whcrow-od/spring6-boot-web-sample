package ua.od.whcrow.samples.spring6.boot_web.identity.web;

import jakarta.annotation.Nonnull;
import org.mapstruct.Mapper;
import ua.od.whcrow.samples.spring6.boot_web._commons.util.Assert;
import ua.od.whcrow.samples.spring6.boot_web._global.mapping.MapStructFromOneCreator;
import ua.od.whcrow.samples.spring6.boot_web.identity.Identity;
import ua.od.whcrow.samples.spring6.boot_web.identity.Role;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
abstract class IdentityDtoMapper implements MapStructFromOneCreator<IdentityPersistDto,Identity> {
	
	@Nonnull
	public abstract Identity map(@Nonnull IdentityPersistDto source);
	
	public abstract IdentityProvideDto map(Identity source);
	
	public abstract List<IdentityProvideDto> map(List<Identity> all);
	
	@Nonnull
	protected Set<String> map(@Nonnull Set<Role> source) {
		Assert.notNull(source, "source Set of Roles");
		return source.stream().map(Role::getName).collect(Collectors.toSet());
	}
	
}
