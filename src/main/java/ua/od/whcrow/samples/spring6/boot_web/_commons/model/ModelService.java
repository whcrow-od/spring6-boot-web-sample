package ua.od.whcrow.samples.spring6.boot_web._commons.model;

import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.od.whcrow.samples.spring6.boot_web._commons.model.exceptions.ModelNotFoundException;

import java.util.List;
import java.util.Optional;

public interface ModelService<M extends Model<ID>, ID> extends ModelComponent<M,ID> {
	
	@Nonnull
	List<M> findAll();
	
	@Nonnull
	Page<M> findAll(@Nonnull Pageable pageable);
	
	@Nonnull
	Optional<M> findById(@Nonnull ID id);
	
	@Nonnull
	default M getById(@Nonnull ID id)
			throws ModelNotFoundException {
		return findById(id).orElseThrow(() -> ModelNotFoundException.ofId(getModelClass(), id));
	}
	
	@Nonnull
	M save(@Nonnull M entity);
	
}
