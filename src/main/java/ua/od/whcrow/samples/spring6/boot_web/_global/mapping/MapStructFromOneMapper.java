package ua.od.whcrow.samples.spring6.boot_web._global.mapping;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import ua.od.whcrow.samples.spring6.boot_web._commons.util.Assert;

public interface MapStructFromOneMapper<SOURCE, TARGET> extends CommonMappings {
	
	@BeforeMapping
	default void checkSourceForNull(SOURCE source) {
		Assert.notNull(source, "mapping source");
	}
	
	@AfterMapping
	default void checkResultForNull(@MappingTarget TARGET result) {
		Assert.notNull(result, "mapping result");
	}
	
}
