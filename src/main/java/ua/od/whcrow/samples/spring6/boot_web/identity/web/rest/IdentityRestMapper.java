package ua.od.whcrow.samples.spring6.boot_web.identity.web.rest;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ua.od.whcrow.samples.spring6.boot_web._commons.mapping.ElectiveMapper;
import ua.od.whcrow.samples.spring6.boot_web._global.mapping.CommonMappings;
import ua.od.whcrow.samples.spring6.boot_web.identity.Identity;

import java.util.List;

@Mapper
abstract class IdentityRestMapper implements CommonMappings, ElectiveMapper {
	
	@Nullable
	@Override
	public Object createIfApplicable(@Nonnull Object source, @Nonnull Class<?> target) {
		if (source instanceof IdentityDto dto && target.equals(Identity.class)) {
			return mapToIdentity(dto);
		}
		return null;
	}
	
	@Nullable
	@Override
	public Object updateIfApplicable(@Nonnull Object source, @Nonnull Object target) {
		if (source instanceof IdentityDto dto && target instanceof Identity identity) {
			return mapToIdentity(dto, identity);
		}
		return null;
	}
	
	public abstract IdentityDto mapToIdentityDto(Identity source);
	
	public abstract IdentityIDto mapToIdentityIDto(Identity source);
	
	public abstract List<IdentityIDto> mapToIdentityIDtoList(List<Identity> source);
	
	public abstract Identity mapToIdentity(IdentityDto source);
	
	public abstract Identity mapToIdentity(IdentityDto source, @MappingTarget Identity target);
	
}
