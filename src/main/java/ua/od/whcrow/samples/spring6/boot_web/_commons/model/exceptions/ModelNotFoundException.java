package ua.od.whcrow.samples.spring6.boot_web._commons.model.exceptions;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Collections;
import java.util.Map;

public class ModelNotFoundException extends ModelException {
	
	private static final String MSG_WITH_ATTR = "Model {} with the following attribute/s is not found: {}";
	private static final String MSG_NO_ATTR = "Model {} is not found";
	
	private final Class<?> modelType;
	private final Map<String,Object> attributes;
	
	public ModelNotFoundException(@Nonnull Class<?> modelType, @Nullable Map<String,Object> attributes) {
		super(attributes == null ? MSG_NO_ATTR : MSG_WITH_ATTR, modelType.getName(), attributes);
		this.modelType = modelType;
		this.attributes = wrapAttributes(attributes);
	}
	
	public ModelNotFoundException(@Nonnull Class<?> modelType, @Nullable Map<String,Object> attributes,
			@Nonnull Throwable cause) {
		super(attributes == null ? MSG_NO_ATTR : MSG_WITH_ATTR, cause, modelType.getName(), attributes);
		this.modelType = modelType;
		this.attributes = wrapAttributes(attributes);
	}
	
	@Nullable
	private static Map<String,Object> wrapAttributes(@Nullable Map<String,Object> attributes) {
		return attributes == null ? null : Collections.unmodifiableMap(attributes);
	}
	
	public ModelNotFoundException(@Nonnull Class<?> modelType, @Nonnull String attributeName,
			@Nullable Object attributeValue) {
		this(modelType, Collections.singletonMap(attributeName, attributeValue));
	}
	
	public ModelNotFoundException(@Nonnull Class<?> modelType, @Nonnull String attributeName,
			@Nullable Object attributeValue, @Nonnull Throwable cause) {
		this(modelType, Collections.singletonMap(attributeName, attributeValue), cause);
	}
	
	@Nonnull
	public static ModelNotFoundException ofId(@Nonnull Class<?> modelType, @Nullable Object id) {
		return new ModelNotFoundException(modelType, "ID", id);
	}
	
	@Nonnull
	public static ModelNotFoundException ofId(@Nonnull Class<?> modelType, @Nullable Object id,
			@Nonnull Throwable cause) {
		return new ModelNotFoundException(modelType, "ID", id, cause);
	}
	
	@Nonnull
	public Class<?> getModelType() {
		return modelType;
	}
	
	@Nullable
	public Map<String,Object> getAttributes() {
		return attributes;
	}
	
}
