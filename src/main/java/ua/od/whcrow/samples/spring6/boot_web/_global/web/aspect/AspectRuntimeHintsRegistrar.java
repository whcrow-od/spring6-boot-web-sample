package ua.od.whcrow.samples.spring6.boot_web._global.web.aspect;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;

// TODO: AspectJ v1.9.21 (Spring Boot v3.2.2) doesn't support native image (see POM.xml for more info). It's manual workaround.
class AspectRuntimeHintsRegistrar implements RuntimeHintsRegistrar {
	
	@Override
	public void registerHints(@Nonnull RuntimeHints hints, @Nullable ClassLoader classLoader) {
		hints.reflection().registerType(
				LoggingAspect.class,
				builder -> builder.withMembers(MemberCategory.INVOKE_PUBLIC_METHODS)
		);
	}
	
}
