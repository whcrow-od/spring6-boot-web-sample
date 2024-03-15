package ua.od.whcrow.samples.spring6.boot_web._commons.persistence.listeners;

import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Configurable;

@Slf4j
@Configurable
public class LoggingEntityListener {
	
	@PostLoad
	protected void postLoad(Object target) {
		log.atDebug().addArgument(target).log("PostLoad: {}");
	}
	
	@PrePersist
	protected void prePersist(Object target) {
		log.atDebug().addArgument(target).log("PrePersist: {}");
	}
	
	@PostPersist
	protected void postPersist(Object target) {
		log.atDebug().addArgument(target).log("PostPersist: {}");
	}
	
	@PreUpdate
	protected void preUpdate(Object target) {
		log.atDebug().addArgument(target).log("PreUpdate: {}");
	}
	
	@PostUpdate
	protected void postUpdate(Object target) {
		log.atDebug().addArgument(target).log("PostUpdate: {}");
	}
	
	@PreRemove
	protected void preRemove(Object target) {
		log.atDebug().addArgument(target).log("PreRemove: {}");
	}
	
	@PostRemove
	protected void postRemove(Object target) {
		log.atDebug().addArgument(target).log("PostRemove: {}");
	}
	
}
