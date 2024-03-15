package ua.od.whcrow.samples.spring6.boot_web._global.aspect;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

@Configuration
@ImportRuntimeHints(AspectConfig.AspectHintsRegistrar.class)
class AspectConfig {
	
	// TODO: AspectJ v1.9.21 (Spring Boot v3.2.2) doesn't support native image (see POM.xml for more info). It's manual workaround.
	static class AspectHintsRegistrar implements RuntimeHintsRegistrar {
		
		@Override
		public void registerHints(@Nonnull RuntimeHints hints, @Nullable ClassLoader classLoader) {
			hints.resources().registerResourceBundle("org.aspectj.weaver.weaver-messages");
		}
		
	}
	
}
