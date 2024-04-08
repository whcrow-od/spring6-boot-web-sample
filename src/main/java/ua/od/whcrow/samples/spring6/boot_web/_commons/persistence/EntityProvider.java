package ua.od.whcrow.samples.spring6.boot_web._commons.persistence;

import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.od.whcrow.samples.spring6.boot_web._commons.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public interface EntityProvider<ENT, ID> extends EntityService<ENT,ID> {
	
	@Nonnull
	List<ENT> findAll();
	
	@Nonnull
	Page<ENT> findAll(@Nonnull Pageable pageable);
	
	@Nonnull
	Optional<ENT> findById(@Nonnull ID id);
	
	@Nonnull
	default ENT getById(@Nonnull ID id)
			throws EntityNotFoundException {
		return findById(id).orElseThrow(() -> EntityNotFoundException.ofId(getEntityClass(), id));
	}
	
}
