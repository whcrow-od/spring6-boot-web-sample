package ua.od.whcrow.samples.spring6.boot_web._global.mapping;

import jakarta.annotation.Nonnull;
import org.mapstruct.MappingTarget;
import ua.od.whcrow.samples.spring6.boot_web._commons.mapping.FromOneUpdater;

public interface MapStructFromOneUpdater<SOURCE, TARGET>
		extends MapStructFromOneMapper<SOURCE,TARGET>, FromOneUpdater<SOURCE,TARGET> {
	
	@Override
	void map(@Nonnull SOURCE source, @MappingTarget @Nonnull TARGET target);
	
}
