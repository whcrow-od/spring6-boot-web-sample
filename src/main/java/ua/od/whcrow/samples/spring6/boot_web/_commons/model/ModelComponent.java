package ua.od.whcrow.samples.spring6.boot_web._commons.model;

import jakarta.annotation.Nonnull;

public interface ModelComponent<M extends Model<ID>, ID> {
	
	@Nonnull
	Class<M> getModelClass();
	
	@Nonnull
	Class<ID> getIdClass();
	
}
