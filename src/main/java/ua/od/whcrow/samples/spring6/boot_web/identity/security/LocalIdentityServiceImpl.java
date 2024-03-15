package ua.od.whcrow.samples.spring6.boot_web.identity.security;

import jakarta.annotation.Nonnull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ua.od.whcrow.samples.spring6.boot_web._commons.util.Assert;
import ua.od.whcrow.samples.spring6.boot_web._commons.util.Msg;
import ua.od.whcrow.samples.spring6.boot_web._global.security.IdentityInfo;
import ua.od.whcrow.samples.spring6.boot_web._global.security.LocalIdentityService;
import ua.od.whcrow.samples.spring6.boot_web.identity.Identity;
import ua.od.whcrow.samples.spring6.boot_web.identity.IdentityService;

@Component
class LocalIdentityServiceImpl implements LocalIdentityService {
	
	private final IdentityService identityService;
	private final IdentityInfoMapper identityInfoMapper;
	
	public LocalIdentityServiceImpl(IdentityService identityService, IdentityInfoMapper identityInfoMapper) {
		this.identityService = identityService;
		this.identityInfoMapper = identityInfoMapper;
	}
	
	@Nonnull
	@Override
	public IdentityInfo loadUserByUsername(@Nonnull String username)
			throws UsernameNotFoundException {
		Assert.notNull(username, "username");
		Identity identity = identityService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
				Msg.format("Identity with the following username is not found: {}", username)));
		return identityInfoMapper.map(identity);
	}
	
}
