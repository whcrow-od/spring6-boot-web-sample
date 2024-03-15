package ua.od.whcrow.samples.spring6.boot_web;

import jakarta.annotation.Nonnull;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.modulith.Modulith;

//@SpringBootApplication
@Modulith(sharedModules = {"_commons", "_global"})
@Slf4j
public class Application {
	
	private static long startup;
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
		startup = context.getStartupDate();
	}
	
	@PreDestroy
	private void preDestroy() {
		log.atInfo()
				.addArgument(this::getFormattedUptime)
				.log("Application is to be shutdown, {} uptime");
	}
	
	@Nonnull
	private String getFormattedUptime() {
		long uptime = System.currentTimeMillis() - startup;
		return DurationFormatUtils.formatDurationWords(uptime, true, true);
	}
	
}
