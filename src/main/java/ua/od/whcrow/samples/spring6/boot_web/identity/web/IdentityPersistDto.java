package ua.od.whcrow.samples.spring6.boot_web.identity.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import ua.od.whcrow.samples.spring6.boot_web._commons.validation.groups.Create;
import ua.od.whcrow.samples.spring6.boot_web._commons.validation.groups.Update;
import ua.od.whcrow.samples.spring6.boot_web.identity.Identity;

import java.util.UUID;

record IdentityPersistDto(
		
		@NotNull
		@NotBlank
		@Size(min = Identity.USERNAME_MIN_LENGTH, max = Identity.USERNAME_MAX_LENGTH)
		String username,
		
		@NotNull
		@Email
		@Size(max = Identity.EMAIL_MAX_LENGTH)
		String email,
		
		@NotNull
		@NotBlank
		@Size(max = Identity.FIRST_NAME_MAX_LENGTH)
		String firstName,
		
		@NotNull
		@NotBlank
		@Size(max = Identity.LAST_NAME_MAX_LENGTH)
		String lastName,
		
		boolean enabled
		
) {}
