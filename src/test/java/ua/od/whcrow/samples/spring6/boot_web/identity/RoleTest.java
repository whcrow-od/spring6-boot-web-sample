package ua.od.whcrow.samples.spring6.boot_web.identity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.od.whcrow.samples.spring6.boot_web.ApplicationTest;
import ua.od.whcrow.samples.spring6.boot_web._global.Operation;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ApplicationTest
class RoleTest {
	
	@Autowired
	private Role role;
	
	@Test
	void checkNewRoleIdDefaultValue() {
		Role role = new Role();
		assertNull(role.getId());
	}
	
	@Test
	void checkNewRoleNameDefaultValue() {
		Role role = new Role();
		assertNull(role.getName());
	}
	
	@Test
	void checkNewRoleIdentitiesDefaultValue() {
		Role role = new Role();
		assertNotNull(role.getIdentities());
		assertIterableEquals(Set.of(), role.getIdentities());
	}
	
	@Test
	void checkNewRoleAllowedOperationsDefaultValue() {
		Role role = new Role();
		assertNotNull(role.getAllowedOperations());
		assertIterableEquals(Set.of(), role.getAllowedOperations());
	}
	
	@Test
	void checkNewRoleAuditDefaultValue() {
		Role role = new Role();
		assertNotNull(role.getAudit());
	}
	
	@Test
	void getAuthoritiesWithPrefixedRoleName() {
		String roleName = Role.ROLE_PREFIX + "TEST";
		role.setName(roleName);
		assertIterableEquals(Set.of(roleName), role.getAuthorities());
	}
	
	@Test
	void getAuthoritiesWithNonPrefixedRoleName() {
		String roleName = "TEST";
		role.setName(roleName);
		assertIterableEquals(Set.of(Role.ROLE_PREFIX + roleName), role.getAuthorities());
	}
	
	@Test
	void getAuthoritiesWithOperations() {
		String roleName = Role.ROLE_PREFIX + "TEST";
		role.setName(roleName);
		Set<String> expectedAuthorities = new LinkedHashSet<>();
		expectedAuthorities.add(roleName);
		Set<Operation> allowedOperation = Set.of(Operation.READ_ROLE, Operation.LIST_ROLE, Operation.READ_IDENTITY,
				Operation.DELETE_IDENTITY, Operation.LIST_IDENTITY);
		role.setAllowedOperations(allowedOperation);
		allowedOperation.stream().map(Operation::name).forEach(expectedAuthorities::add);
		assertThat(role.getAuthorities()).hasSameElementsAs(expectedAuthorities);
	}
	
}