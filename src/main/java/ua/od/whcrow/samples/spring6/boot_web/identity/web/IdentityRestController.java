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
import ua.od.whcrow.samples.spring6.boot_web._commons.model.exceptions.ModelNotFoundException;
import ua.od.whcrow.samples.spring6.boot_web._commons.security.AuthorityAccess;
import ua.od.whcrow.samples.spring6.boot_web._commons.web.DPShortcut;
import ua.od.whcrow.samples.spring6.boot_web._global.Operation;
import ua.od.whcrow.samples.spring6.boot_web.identity.IdentityConstants;
import ua.od.whcrow.samples.spring6.boot_web.identity.IdentityService;

import java.util.UUID;

@RestController
@Validated
@RequestMapping(IdentityConstants.REQ_P_API_IDENTITIES)
class IdentityRestController {
	
	private final IdentityService service;
	private final IdentityMapper mapper;
	
	IdentityRestController(IdentityService service, IdentityMapper mapper) {
		this.service = service;
		this.mapper = mapper;
	}
	
	@PostMapping
	@AuthorityAccess(Operation.Constants.CREATE_IDENTITY)
	@ResponseStatus(HttpStatus.CREATED)
	public IdentityProvideDto createIdentity(@RequestBody IdentityPersistDto dto) {
		return DPShortcut.create(dto, mapper::map, service::save, mapper::map);
	}
	
	@GetMapping("/{id}")
	@AuthorityAccess(Operation.Constants.READ_IDENTITY)
	public IdentityProvideDto getIdentity(@PathVariable UUID id)
			throws ModelNotFoundException {
		return DPShortcut.read(id, service::getById, mapper::map);
	}
	
	@PostMapping("/{id}")
	@AuthorityAccess(Operation.Constants.UPDATE_IDENTITY)
	public IdentityProvideDto updateIdentity(@PathVariable UUID id, @RequestBody IdentityPersistDto dto)
			throws ModelNotFoundException {
		return DPShortcut.update(id, service::getById, dto, mapper::map, service::save, mapper::map);
	}
	
	@GetMapping
	@AuthorityAccess(Operation.Constants.LIST_IDENTITY)
	public Page<IdentityProvideDto> listIdentities(@ParameterObject Pageable pageable) {
		return service.findAll(pageable).map(mapper::map);
	}
	
	/*@GetMapping
	@AuthorityAccess(Operation.Constants.LIST_IDENTITY)
	public List<IdentityProvideDto> listIdentities() {
		return ModelUtils.read(service::findAll, mapper::map);
	}*/
	
}
