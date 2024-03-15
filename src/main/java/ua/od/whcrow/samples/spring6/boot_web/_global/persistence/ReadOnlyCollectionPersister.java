package ua.od.whcrow.samples.spring6.boot_web._global.persistence;

import org.hibernate.MappingException;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.access.CollectionDataAccess;
import org.hibernate.mapping.Collection;
import org.hibernate.metamodel.spi.RuntimeModelCreationContext;
import org.hibernate.persister.collection.BasicCollectionPersister;

public class ReadOnlyCollectionPersister extends BasicCollectionPersister {
	
	public ReadOnlyCollectionPersister(Collection collectionBinding,
			CollectionDataAccess cacheAccessStrategy,
			RuntimeModelCreationContext creationContext)
			throws MappingException, CacheException {
		super(asInverse(collectionBinding), cacheAccessStrategy, creationContext);
	}
	
	private static Collection asInverse(Collection collection) {
		collection.setInverse(true);
		return collection;
	}
	
}
