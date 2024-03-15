package ua.od.whcrow.samples.spring6.boot_web._global.web.aspect;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Aspect
@Component
@ImportRuntimeHints(AspectRuntimeHintsRegistrar.class)
class LoggingAspect implements Pointcuts {
	
	private static final String MSG_DURATION = "{} ended in {}ms";
	private static final String MSG_DURATION_EXC = "{} ended in {}ms with exception";
	
	@Nonnull
	private Logger getLogger(@Nonnull JoinPoint joinPoint) {
		return LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType().getName()
				+ "-" + getClass().getSimpleName());
	}
	
	// TODO: configure log level via app properties
	private void logDuration(@Nonnull JoinPoint joinPoint, @Nonnull LocalDateTime startTime,
			@Nullable Throwable throwable) {
		getLogger(joinPoint).debug(throwable == null ? MSG_DURATION : MSG_DURATION_EXC,
				joinPoint.toShortString(), Duration.between(startTime, LocalDateTime.now()).toMillis());
	}
	
	private void logDuration(@Nonnull JoinPoint joinPoint, @Nonnull LocalDateTime startTime) {
		logDuration(joinPoint, startTime, null);
	}
	
	@Around("anyControllerAnyMappingMethod()")
	public Object logRequestMethodDuration(@Nonnull ProceedingJoinPoint joinPoint)
			throws Throwable {
		LocalDateTime startTime = LocalDateTime.now();
		Object result;
		try {
			result = joinPoint.proceed(joinPoint.getArgs());
		} catch (Throwable e) {
			logDuration(joinPoint, startTime, e);
			throw e;
		}
		logDuration(joinPoint, startTime);
		return result;
	}
	
}
