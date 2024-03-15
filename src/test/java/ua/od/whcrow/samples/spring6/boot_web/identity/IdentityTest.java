package ua.od.whcrow.samples.spring6.boot_web.identity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.od.whcrow.samples.spring6.boot_web.ApplicationTest;
import ua.od.whcrow.samples.spring6.boot_web._global.Operation;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ApplicationTest
class IdentityTest {
	
	@Autowired
	private Identity identity;
	
	@Autowired
	private Role role1;
	
	@Autowired
	private Role role2;
	
	@Test
	void checkNewIdentityIdDefaultValue() {
		Identity identity = new Identity();
		assertNull(identity.getId());
	}
	
	@Test
	void checkNewIdentityUsernameDefaultValue() {
		Identity identity = new Identity();
		assertNull(identity.getUsername());
	}
	
	@Test
	void checkNewIdentityPasswordDefaultValue() {
		Identity identity = new Identity();
		assertNull(identity.getPassword());
	}
	
	@Test
	void checkNewIdentityEnabledDefaultValue() {
		Identity identity = new Identity();
		assertTrue(identity.isEnabled());
	}
	
	@Test
	void checkNewIdentityRolesDefaultValue() {
		Identity identity = new Identity();
		assertNotNull(identity.getRoles());
		assertIterableEquals(Set.of(), identity.getRoles());
	}
	
	@Test
	void checkNewIdentityEmailDefaultValue() {
		Identity identity = new Identity();
		assertNull(identity.getEmail());
	}
	
	@Test
	void checkNewIdentityFirstNameDefaultValue() {
		Identity identity = new Identity();
		assertNull(identity.getFirstName());
	}
	
	@Test
	void checkNewIdentityLastNameDefaultValue() {
		Identity identity = new Identity();
		assertNull(identity.getLastName());
	}
	
	@Test
	void checkNewIdentityAllowedOperationsDefaultValue() {
		Identity identity = new Identity();
		assertNotNull(identity.getAllowedOperations());
		assertIterableEquals(Set.of(), identity.getAllowedOperations());
	}
	
	@Test
	void checkNewIdentityDisallowedOperationsDefaultValue() {
		Identity identity = new Identity();
		assertNotNull(identity.getDisallowedOperations());
		assertIterableEquals(Set.of(), identity.getDisallowedOperations());
	}
	
	@Test
	void checkNewIdentityAuditDefaultValue() {
		Identity identity = new Identity();
		assertNotNull(identity.getAudit());
	}
	
	@Test
	void nameEqualsId() {
		assertEquals(identity.getName(), identity.getId().toString());
	}
	
	@Test
	void getAuthoritiesWithRolesAndOperations() {
		role1.setAllowedOperations(Set.of(Operation.READ_ROLE, Operation.LIST_ROLE, Operation.DELETE_ROLE));
		role2.setAllowedOperations(Set.of(Operation.READ_IDENTITY, Operation.LIST_IDENTITY, Operation.DELETE_IDENTITY));
		Set<String> expectedAuthorities = new LinkedHashSet<>();
		identity.setRoles(Set.of(role1, role2));
		expectedAuthorities.addAll(role1.getAuthorities());
		expectedAuthorities.addAll(role2.getAuthorities());
		Set<Operation> allowedOperation = Set.of(Operation.UPDATE_ROLE, Operation.UPDATE_IDENTITY);
		identity.setAllowedOperations(allowedOperation);
		Set<Operation> disallowedOperation = Set.of(Operation.DELETE_ROLE, Operation.DELETE_IDENTITY);
		identity.setDisallowedOperations(disallowedOperation);
		allowedOperation.stream().map(Operation::name).forEach(expectedAuthorities::add);
		disallowedOperation.stream().map(Operation::name).forEach(expectedAuthorities::remove);
		assertThat(identity.getAuthorities()).hasSameElementsAs(expectedAuthorities);
	}
	
}