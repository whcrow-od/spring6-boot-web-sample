package ua.od.whcrow.samples.spring6.boot_web.identity.web.rest;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ua.od.whcrow.samples.spring6.boot_web.identity.Identity;

@Data
@JsonPropertyOrder(alphabetic = true)
class IdentityDto {
	
	@NotNull
	@NotBlank
	@Size(min = Identity.USERNAME_MIN_LENGTH, max = Identity.USERNAME_MAX_LENGTH)
	private String username;
	
	private boolean enabled;
	
	@NotNull
	@Email
	@Size(max = Identity.EMAIL_MAX_LENGTH)
	private String email;
	
	@NotNull
	@NotBlank
	@Size(max = Identity.FIRST_NAME_MAX_LENGTH)
	private String firstName;
	
	@NotNull
	@NotBlank
	@Size(max = Identity.LAST_NAME_MAX_LENGTH)
	private String lastName;
	
}
