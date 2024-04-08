package ua.od.whcrow.samples.spring6.boot_web._commons.web.exceptions;

import jakarta.annotation.Nonnull;
import org.springframework.http.HttpStatus;

public class NotFoundException extends StatusException {
	
	private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;
	private static final String MSG_MULTIPLE_ATTR = "Not found matching {}: {}";
	private static final String MSG_SINGLE_ATTR = "Not found {} with the following {}: {}";
	
	public NotFoundException(@Nonnull String message, Object... args) {
		super(STATUS, message, args);
	}
	
	public NotFoundException(@Nonnull String message, @Nonnull Throwable cause, Object... args) {
		super(STATUS, message, cause, args);
	}
	
	@Nonnull
	public static NotFoundException ofAttributes(@Nonnull Class<?> searchForType, @Nonnull Object attributes,
			@Nonnull Throwable cause) {
		return new NotFoundException(MSG_MULTIPLE_ATTR, cause, searchForType.getSimpleName(), attributes);
	}
	
	@Nonnull
	public static NotFoundException ofAttribute(@Nonnull Class<?> searchForType, @Nonnull String attributeName,
			@Nonnull Object attributeValue, @Nonnull Throwable cause) {
		return new NotFoundException(MSG_SINGLE_ATTR, searchForType.getSimpleName(), attributeName, attributeValue,
				cause);
	}
	
	@Nonnull
	public static NotFoundException ofId(@Nonnull Class<?> searchForType, @Nonnull Object searchForId,
			@Nonnull Throwable cause) {
		return ofAttribute(searchForType, "ID", searchForId, cause);
	}
	
	@Nonnull
	public static NotFoundException ofAttributes(@Nonnull Class<?> searchForType, @Nonnull Object attributes) {
		return new NotFoundException(MSG_MULTIPLE_ATTR, searchForType.getSimpleName(), attributes);
	}
	
	@Nonnull
	public static NotFoundException ofAttribute(@Nonnull Class<?> searchForType, @Nonnull String attributeName,
			@Nonnull Object attributeValue) {
		return new NotFoundException(MSG_SINGLE_ATTR, searchForType.getSimpleName(), attributeName, attributeValue);
	}
	
	@Nonnull
	public static NotFoundException ofId(@Nonnull Class<?> searchForType, @Nonnull Object searchForId) {
		return ofAttribute(searchForType, "ID", searchForId);
	}
	
}
