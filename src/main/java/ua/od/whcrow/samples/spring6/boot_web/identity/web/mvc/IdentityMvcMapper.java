package ua.od.whcrow.samples.spring6.boot_web.identity.web.mvc;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.mapstruct.Mapper;
import ua.od.whcrow.samples.spring6.boot_web._commons.mapping.ElectiveMapper;
import ua.od.whcrow.samples.spring6.boot_web._commons.util.Assert;
import ua.od.whcrow.samples.spring6.boot_web._global.mapping.CommonMappings;
import ua.od.whcrow.samples.spring6.boot_web.identity.Identity;
import ua.od.whcrow.samples.spring6.boot_web.identity.Role;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
abstract class IdentityMvcMapper implements CommonMappings, ElectiveMapper {
	
	/*@Nullable
	@Override
	public Object createIfApplicable(@Nonnull Object source, @Nonnull Class<?> target) {
		if (source instanceof IdentityDto dto && target.equals(Identity.class)) {
			return mapToIdentity(dto);
		}
	}
	
	@Nullable
	@Override
	public Object updateIfApplicable(@Nonnull Object source, @Nonnull Object target) {
		if (source instanceof IdentityDto dto && target instanceof Identity identity) {
			return mapToIdentity(dto, identity);
		}
		return null;
	}*/
	
	public abstract List<IdentityDto> mapToIdentityDtoList(List<Identity> all);
	
	public abstract IdentityDto mapToIdentityDto(Identity source);
	
	@Nonnull
	protected Set<String> map(@Nonnull Set<Role> source) {
		Assert.notNull(source, "source");
		return source.stream()
				.map(Role::getName)
				.collect(Collectors.toSet());
	}
	
}
