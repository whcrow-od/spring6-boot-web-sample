package ua.od.whcrow.samples.spring6.boot_web._commons.mapping;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public interface ElectiveMapper {
	
	@Nullable
	default Object createIfApplicable(@Nonnull Object source, @Nonnull Class<?> target) {
		return null;
	}
	
	@Nullable
	default Object updateIfApplicable(@Nonnull Object source, @Nonnull Object target) {
		return null;
	}
	
}
