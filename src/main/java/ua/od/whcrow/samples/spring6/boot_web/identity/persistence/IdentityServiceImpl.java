package ua.od.whcrow.samples.spring6.boot_web.identity.persistence;

import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Service;
import ua.od.whcrow.samples.spring6.boot_web._commons.util.Assert;
import ua.od.whcrow.samples.spring6.boot_web.identity.Identity;
import ua.od.whcrow.samples.spring6.boot_web.identity.IdentityService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
class IdentityServiceImpl implements IdentityService {
	
	private final IdentityRepository identityRepository;
	
	public IdentityServiceImpl(IdentityRepository identityRepository) {
		this.identityRepository = identityRepository;
	}
	
	@Nonnull
	@Override
	public List<Identity> findAll() {
		return identityRepository.findAll();
	}
	
	@Nonnull
	@Override
	public Optional<Identity> findById(@Nonnull UUID id) {
		Assert.notNull(id, "id");
		return identityRepository.findById(id);
	}
	
	@Nonnull
	@Override
	public Optional<Identity> findByUsername(@Nonnull String username) {
		Assert.notNull(username, "name");
		return identityRepository.findByUsername(username);
	}
	
	@Nonnull
	@Override
	public Identity save(@Nonnull Identity identity) {
		Assert.notNull(identity, "user");
		return identityRepository.save(identity);
	}
	
}
