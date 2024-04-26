package ua.od.whcrow.samples.spring6.boot_web.identity.web.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.od.whcrow.samples.spring6.boot_web._commons.model.exceptions.ModelNotFoundException;
import ua.od.whcrow.samples.spring6.boot_web._commons.security.AuthorityAccess;
import ua.od.whcrow.samples.spring6.boot_web._commons.security.GeneralAccess;
import ua.od.whcrow.samples.spring6.boot_web._commons.security.GeneralAccessType;
import ua.od.whcrow.samples.spring6.boot_web._commons.web.DPShortcut;
import ua.od.whcrow.samples.spring6.boot_web._global.Operation;
import ua.od.whcrow.samples.spring6.boot_web.identity.IdentityConstants;
import ua.od.whcrow.samples.spring6.boot_web.identity.IdentityService;

import java.util.UUID;

@GeneralAccess(GeneralAccessType.AUTHENTICATED)
@RequestMapping(IdentityConstants.REQ_P_IDENTITIES)
@Controller
class IdentityMvcController {
	
	private static final String ATTR_IDENTITIES = "identities";
	private static final String ATTR_IDENTITY = "identity";
	
	private final IdentityService service;
	private final IdentityMvcMapper mapper;
	
	IdentityMvcController(IdentityService service, IdentityMvcMapper mapper) {
		this.service = service;
		this.mapper = mapper;
	}
	
	@AuthorityAccess(Operation.Constants.LIST_IDENTITY)
	@GetMapping({"", "/"})
	public String listIdentities(Model model) {
		model.addAttribute(ATTR_IDENTITIES, DPShortcut.read(service::findAll, mapper::mapToIdentityDtoList));
		return "identities/list";
	}
	
	@AuthorityAccess(Operation.Constants.READ_IDENTITY)
	@GetMapping("/{id}")
	public String viewIdentity(@PathVariable UUID id, Model model)
			throws ModelNotFoundException {
		model.addAttribute(ATTR_IDENTITY, DPShortcut.read(id, service::getById, mapper::mapToIdentityDto));
		return "identities/view";
	}
	
	/*@AuthorityAccess(Operation.Constants.CREATE_IDENTITY)
	@GetMapping("/new")
	public String createIdentityForm() {
	
	}
	
	@AuthorityAccess(Operation.Constants.CREATE_IDENTITY)
	@PostMapping("/new")
	public String createIdentity() {
	
	}*/
	
}
