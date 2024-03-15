package ua.od.whcrow.samples.spring6.boot_web._commons.persistence;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;
import ua.od.whcrow.samples.spring6.boot_web._commons.exceptions.UtilClassInstantiationException;

public final class EntityUtils {
	
	private EntityUtils()
			throws UtilClassInstantiationException {
		throw new UtilClassInstantiationException(EntityUtils.class);
	}
	
	/**
	 * @return The given object's class obtaining it from a proxy-object (ORM lazy loading mechanism) if necessary. The
	 * given object doesn't have to represent an entity.
	 */
	@Nonnull
	public static Class<?> getEntityType(@Nonnull Object obj) {
		return obj instanceof HibernateProxy proxy
				? proxy.getHibernateLazyInitializer().getPersistentClass()
				: obj.getClass();
	}
	
	/**
	 * @return {@code true} if the given object is already initialized proxy-object (ORM lazy loading mechanism) or not
	 * a proxy-object at all. The given object doesn't have to represent an entity.
	 */
	public static boolean isInitialized(@Nullable Object obj) {
		if (obj instanceof PersistentCollection<?> proxy) {
			return proxy.wasInitialized();
		}
		if (obj instanceof HibernateProxy proxy) {
			return !proxy.getHibernateLazyInitializer().isUninitialized();
		}
		return true;
	}
	
}
