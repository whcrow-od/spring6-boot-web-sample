package ua.od.whcrow.samples.spring6.boot_web;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

public class ApplicationModularityTests {
	
	private static ApplicationModules modules;
	
	@BeforeAll
	static void init() {
		modules = ApplicationModules.of(Application.class);
	}
	
	@Test
	void verifyModules() {
		modules.forEach(System.out::println);
		modules.verify();
	}
	
	@Test
	void writeDocumentationSnippets() {
		new Documenter(modules)
				.writeModulesAsPlantUml()
				.writeIndividualModulesAsPlantUml();
	}
	
}
