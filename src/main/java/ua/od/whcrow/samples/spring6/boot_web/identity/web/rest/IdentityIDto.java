package ua.od.whcrow.samples.spring6.boot_web.identity.web.rest;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonPropertyOrder(value = {"id"}, alphabetic = true)
class IdentityIDto extends IdentityDto {
	
	@NotNull
	private UUID id;
	
}
