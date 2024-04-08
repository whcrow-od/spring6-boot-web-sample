package ua.od.whcrow.samples.spring6.boot_web.identity.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.od.whcrow.samples.spring6.boot_web._commons.security.AuthorityAccess;
import ua.od.whcrow.samples.spring6.boot_web._commons.security.GeneralAccess;
import ua.od.whcrow.samples.spring6.boot_web._commons.security.GeneralAccessType;
import ua.od.whcrow.samples.spring6.boot_web._commons.web.exceptions.NotFoundException;
import ua.od.whcrow.samples.spring6.boot_web._global.Operation;
import ua.od.whcrow.samples.spring6.boot_web.identity.Identity;
import ua.od.whcrow.samples.spring6.boot_web.identity.IdentityConstants;
import ua.od.whcrow.samples.spring6.boot_web.identity.IdentityService;

import java.util.UUID;

@GeneralAccess(GeneralAccessType.AUTHENTICATED)
@RequestMapping(IdentityConstants.REQ_P_IDENTITIES)
@Controller
class IdentityController {
	
	private static final String ATTR_IDENTITIES = "identities";
	private static final String ATTR_IDENTITY = "identity";
	
	private final IdentityService identityService;
	private final IdentityMapper identityMapper;
	
	IdentityController(IdentityService identityService, IdentityMapper identityMapper) {
		this.identityService = identityService;
		this.identityMapper = identityMapper;
	}
	
	@AuthorityAccess(Operation.Constants.LIST_IDENTITY)
	@GetMapping({"", "/"})
	public String listIdentities(Model model) {
		model.addAttribute(ATTR_IDENTITIES, identityMapper.map(identityService.findAll()));
		return "identities/list";
	}
	
	@AuthorityAccess(Operation.Constants.READ_IDENTITY)
	@GetMapping("/{id}")
	public String viewIdentity(@PathVariable UUID id, Model model) {
		Identity identity = identityService.findById(id).orElseThrow(() -> NotFoundException.ofId(Identity.class, id));
		model.addAttribute(ATTR_IDENTITY, identityMapper.map(identity));
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
