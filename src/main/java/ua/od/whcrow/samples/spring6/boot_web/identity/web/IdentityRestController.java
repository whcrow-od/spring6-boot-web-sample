package ua.od.whcrow.samples.spring6.boot_web.identity.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.od.whcrow.samples.spring6.boot_web._global.web.WebConstants;
import ua.od.whcrow.samples.spring6.boot_web._commons.security.AuthorityAccess;
import ua.od.whcrow.samples.spring6.boot_web._commons.web.BodyModelResponse;
import ua.od.whcrow.samples.spring6.boot_web._commons.web.exceptions.NotFoundException;
import ua.od.whcrow.samples.spring6.boot_web._global.Operation;
import ua.od.whcrow.samples.spring6.boot_web.identity.Identity;
import ua.od.whcrow.samples.spring6.boot_web.identity.IdentityConstants;
import ua.od.whcrow.samples.spring6.boot_web.identity.IdentityService;

import java.util.UUID;

@RestController
@RequestMapping(IdentityConstants.REQ_P_API_IDENTITIES)
class IdentityRestController {
	
	private final IdentityService identityService;
	private final IdentityDtoMapper identityDtoMapper;
	
	IdentityRestController(IdentityService identityService, IdentityDtoMapper identityDtoMapper) {
		this.identityService = identityService;
		this.identityDtoMapper = identityDtoMapper;
	}
	
	@AuthorityAccess(Operation.Constants.READ_IDENTITY)
	@GetMapping("/{id}")
	public BodyModelResponse<IdentityProvideDto> getIdentity(@PathVariable UUID id) {
		Identity identity = identityService.findById(id).orElseThrow(() -> NotFoundException.ofId(Identity.class, id));
		return new BodyModelResponse<>(identityDtoMapper.map(identity));
	}
	
}
