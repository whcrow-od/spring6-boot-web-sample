package ua.od.whcrow.samples.spring6.boot_web.identity.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ua.od.whcrow.samples.spring6.boot_web.ApplicationTest;
import ua.od.whcrow.samples.spring6.boot_web._commons.persistence.EntityProvider;
import ua.od.whcrow.samples.spring6.boot_web.identity.Identity;
import ua.od.whcrow.samples.spring6.boot_web.identity.IdentityService;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ApplicationTest
class IdentityServiceImplTest {
	
	@MockBean
	private IdentityRepository identityRepository;
	
	@Autowired
	private IdentityServiceImpl identityServiceImpl;
	
	@Autowired
	private EntityProvider<Identity,UUID> entityProvider;
	
	@Autowired
	private IdentityService identityService;
	
	@Autowired
	private Identity identity;
	
	@Test
	void checkWhetherIdentityEntityProviderIsSubject() {
		assertEquals(identityServiceImpl, entityProvider);
	}
	
	@Test
	void checkWhetherIdentityServiceIsSubject() {
		assertEquals(identityServiceImpl, identityService);
	}
	
	@Test
	void findByIdThrowsExceptionIfArgIsNull() {
		assertThrows(IllegalArgumentException.class, () -> identityServiceImpl.findById(null));
	}
	
	@Test
	void findByIdNonExistent() {
		when(identityRepository.findById(identity.getId())).thenReturn(Optional.empty());
		assertTrue(identityServiceImpl.findById(identity.getId()).isEmpty());
		verify(identityRepository).findById(identity.getId());
	}
	
	@Test
	void findById() {
		when(identityRepository.findById(identity.getId())).thenReturn(Optional.of(identity));
		assertEquals(identity, identityServiceImpl.findById(identity.getId()).get());
		verify(identityRepository).findById(identity.getId());
	}
	
	@Test
	void findByUsernameThrowsExceptionIfArgIsNull() {
		assertThrows(IllegalArgumentException.class, () -> identityServiceImpl.findByUsername(null));
	}
	
	@Test
	void findByUsernameNonExistent() {
		when(identityRepository.findByUsername(identity.getUsername())).thenReturn(Optional.empty());
		assertTrue(identityServiceImpl.findByUsername(identity.getUsername()).isEmpty());
		verify(identityRepository).findByUsername(identity.getUsername());
	}
	
	@Test
	void findByUsername() {
		when(identityRepository.findByUsername(identity.getUsername())).thenReturn(Optional.of(identity));
		assertEquals(identity, identityServiceImpl.findByUsername(identity.getUsername()).get());
		verify(identityRepository).findByUsername(identity.getUsername());
	}
	
	@Test
	void saveThrowsExceptionIfArgIsNull() {
		assertThrows(IllegalArgumentException.class, () -> identityServiceImpl.save(null));
	}
	
	@Test
	void save() {
		when(identityRepository.save(identity)).thenReturn(identity);
		assertEquals(identity.getId(), identityServiceImpl.save(identity).getId());
		verify(identityRepository).save(identity);
	}
	
}