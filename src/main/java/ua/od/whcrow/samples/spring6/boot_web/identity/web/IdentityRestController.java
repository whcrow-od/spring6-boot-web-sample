package ua.od.whcrow.samples.spring6.boot_web.identity.web;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ua.od.whcrow.samples.spring6.boot_web._commons.security.AuthorityAccess;
import ua.od.whcrow.samples.spring6.boot_web._commons.web.EntityRestController;
import ua.od.whcrow.samples.spring6.boot_web._global.Operation;
import ua.od.whcrow.samples.spring6.boot_web.identity.Identity;
import ua.od.whcrow.samples.spring6.boot_web.identity.IdentityConstants;
import ua.od.whcrow.samples.spring6.boot_web.identity.IdentityService;

import java.util.UUID;

@RestController
@Validated
@RequestMapping(IdentityConstants.REQ_P_API_IDENTITIES)
class IdentityRestController implements EntityRestController {
	
	private final IdentityService identityService;
	private final IdentityMapper identityMapper;
	
	IdentityRestController(IdentityService identityService, IdentityMapper identityMapper) {
		this.identityService = identityService;
		this.identityMapper = identityMapper;
	}
	
	@GetMapping
	@AuthorityAccess(Operation.Constants.LIST_IDENTITY)
	public Page<IdentityProvideDto> listIdentities(@ParameterObject Pageable pageable) {
		return identityService.findAll(pageable).map(identityMapper::map);
	}
	
	@PostMapping
	@AuthorityAccess(Operation.Constants.CREATE_IDENTITY)
	@ResponseStatus(HttpStatus.CREATED)
	public IdentityProvideDto createIdentity(@RequestBody IdentityPersistDto dto) {
		Identity identity = identityService.save(identityMapper.map(dto));
		return identityMapper.map(identity);
	}
	
	@GetMapping("/{id}")
	@AuthorityAccess(Operation.Constants.READ_IDENTITY)
	public IdentityProvideDto getIdentity(@PathVariable UUID id) {
		return identityMapper.map(getById(identityService, id));
	}
	
	@PostMapping("/{id}")
	@AuthorityAccess(Operation.Constants.UPDATE_IDENTITY)
	public IdentityProvideDto updateIdentity(@PathVariable UUID id, @RequestBody IdentityPersistDto dto) {
		Identity identity = getById(identityService, id);
		identityMapper.map(dto, identity);
		return identityMapper.map(identityService.save(identity));
	}
	
	/*@AuthorityAccess(Operation.Constants.CREATE_IDENTITY)
	@PostMapping("/test")
	public ResponseEntity<Object> test(@RequestBody @Validated(Create.class) IdentityPersistDto dto) {
		Identity identity = identityService.save(identityDtoMapper.map(dto));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(identity.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}*/
	
}
