package ua.od.whcrow.samples.spring6.boot_web.identity.security;

import jakarta.annotation.Nonnull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.security.core.GrantedAuthority;
import ua.od.whcrow.samples.spring6.boot_web._commons.Ascii128;
import ua.od.whcrow.samples.spring6.boot_web._global.mapping.MapStructFromOneCreator;
import ua.od.whcrow.samples.spring6.boot_web._global.security.IdentityInfo;
import ua.od.whcrow.samples.spring6.boot_web.identity.Identity;
import ua.od.whcrow.samples.spring6.boot_web.identity.Role;

import java.util.Collection;

@Mapper
abstract class IdentityInfoMapper implements MapStructFromOneCreator<Identity,IdentityInfo> {
	
	@Mapping(target = "credentialsNonExpired", constant = "true")
	@Mapping(target = "accountNonLocked", constant = "true")
	@Mapping(target = "accountNonExpired", constant = "true")
	@Mapping(target = "authorities", source = "source", qualifiedByName = "persistedUserDetailsAuthorities")
	@Mapping(target = "roles", source = "source", qualifiedByName = "persistedUserDetailsRoles")
	@Mapping(target = "nickname", source = "source", qualifiedByName = "persistedUserDetailsNickname")
	@Nonnull
	@Override
	public abstract IdentityInfoImpl map(@Nonnull Identity source);
	
	@Named("persistedUserDetailsAuthorities")
	protected Collection<? extends GrantedAuthority> mapAuthorities(@Nonnull Identity source) {
		return source.getAuthorities().stream()
				.map(GrantedAuthorityImpl::new)
				.toList();
	}
	
	@Named("persistedUserDetailsRoles")
	protected Collection<String> mapRoles(@Nonnull Identity source) {
		return source.getRoles().stream()
				.map(Role::getName)
				.toList();
	}
	
	@Named("persistedUserDetailsNickname")
	protected String mapNickname(@Nonnull Identity source) {
		if (source.getFirstName() != null || source.getLastName() != null) {
			return String.join(Ascii128.SPACE.getString(), source.getFirstName(), source.getLastName());
		}
		return source.getUsername();
	}
	
}
