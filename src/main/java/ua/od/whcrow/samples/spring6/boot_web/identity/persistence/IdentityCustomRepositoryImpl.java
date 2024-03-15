package ua.od.whcrow.samples.spring6.boot_web.identity.persistence;

import jakarta.annotation.Nonnull;
import ua.od.whcrow.samples.spring6.boot_web._commons.persistence.EntityMetaProviderImpl;
import ua.od.whcrow.samples.spring6.boot_web.identity.Identity;

class IdentityCustomRepositoryImpl extends EntityMetaProviderImpl<Identity> implements IdentityCustomRepository {
	
	@Nonnull
	@Override
	public Class<Identity> getEntityClass() {
		return Identity.class;
	}
	
}
