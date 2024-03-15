package ua.od.whcrow.samples.spring6.boot_web._commons.persistence;

import jakarta.annotation.Nonnull;

public interface AttributeMeta {
	
	@Nonnull
	String getName();
	
	@Nonnull
	Class<?> getType();
	
	boolean isId();
	
	boolean isCollection();
	
	Object getValue(@Nonnull Object entityObj);
	
}
