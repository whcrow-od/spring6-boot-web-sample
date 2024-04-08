package ua.od.whcrow.samples.spring6.boot_web._commons.web;

import jakarta.annotation.Nonnull;
import ua.od.whcrow.samples.spring6.boot_web._commons.exceptions.EntityNotFoundException;
import ua.od.whcrow.samples.spring6.boot_web._commons.persistence.EntityProvider;
import ua.od.whcrow.samples.spring6.boot_web._commons.web.exceptions.NotFoundException;

public interface EntityRestController {
	
	@Nonnull
	default <ENT, ID> ENT getById(@Nonnull EntityProvider<ENT,ID> provider, @Nonnull ID id)
			throws NotFoundException {
		try {
			return provider.getById(id);
		} catch (EntityNotFoundException e) {
			throw NotFoundException.ofId(provider.getEntityClass(), id, e);
		}
	}
	
}
