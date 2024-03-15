package ua.od.whcrow.samples.spring6.boot_web._commons.mapping;

import jakarta.annotation.Nonnull;

public interface FromOneCreator<SOURCE, TARGET> {
	
	@Nonnull
	TARGET map(@Nonnull SOURCE source);
	
}
