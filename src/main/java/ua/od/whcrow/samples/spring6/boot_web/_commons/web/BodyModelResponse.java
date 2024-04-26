package ua.od.whcrow.samples.spring6.boot_web._commons.web;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ua.od.whcrow.samples.spring6.boot_web._commons.util.Assert;

public class BodyModelResponse<T> extends ResponseEntity<BodyModel<T>> {
	
	public BodyModelResponse(@Nonnull BodyModel<T> bodyModel) {
		super(Assert.notNull(bodyModel, "bodyModel"), bodyModel.getStatus());
	}
	
	public BodyModelResponse(@Nonnull HttpStatus status, @Nullable T payload, @Nullable String details) {
		super(new BodyModel<>(status, payload, details), status);
	}
	
	public BodyModelResponse(@Nullable T payload, @Nullable String description) {
		this(HttpStatus.OK, payload, description);
	}
	
	public BodyModelResponse(@Nullable T payload) {
		this(payload, null);
	}
	
}
