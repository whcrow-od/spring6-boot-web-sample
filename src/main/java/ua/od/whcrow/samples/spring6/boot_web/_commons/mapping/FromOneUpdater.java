package ua.od.whcrow.samples.spring6.boot_web._commons.mapping;

import jakarta.annotation.Nonnull;

public interface FromOneUpdater<SOURCE, TARGET> {
	
	void map(@Nonnull SOURCE source, @Nonnull TARGET target);
	
}
