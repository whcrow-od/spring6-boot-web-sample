package ua.od.whcrow.samples.spring6.boot_web._commons.web;

import jakarta.annotation.Nonnull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.od.whcrow.samples.spring6.boot_web._commons.exceptions.UtilClassInstantiationException;
import ua.od.whcrow.samples.spring6.boot_web._commons.model.Model;
import ua.od.whcrow.samples.spring6.boot_web._commons.model.ModelService;
import ua.od.whcrow.samples.spring6.boot_web._commons.model.exceptions.ModelNotFoundException;
import ua.od.whcrow.samples.spring6.boot_web._commons.util.Assert;
import ua.od.whcrow.samples.spring6.boot_web._commons.web.exceptions.NotFoundException;

import java.net.URI;
import java.util.function.BiFunction;
import java.util.function.Function;

public final class ModelHelper {
	
	public static final String MODEL_ID_APPEND_PATH = "/{id}";
	
	private ModelHelper()
			throws UtilClassInstantiationException {
		throw new UtilClassInstantiationException(ModelHelper.class);
	}
	
	/**
	 * Map the given {@code model} to a resulting object by applying {@code resultMapper}. Resulting object is checked
	 * for {@code NULL}.
	 * @param model object to be used as data source during mapping of resulting object.
	 * @param resultMapper maps the given {@code model} to a resulting object.
	 * @return object mapped from the given {@code model}.
	 */
	@Nonnull
	public static <M, R> R mapToTarget(@Nonnull M model, @Nonnull Function<M,R> resultMapper) {
		Assert.notNull(model, "model");
		Assert.notNull(resultMapper, "resultMapper");
		R target = resultMapper.apply(model);
		Assert.notNull(target, "mapped target object");
		return target;
	}
	
	/**
	 * Saves the given {@code model} and maps it to a resulting object by applying {@code resultMapper}.
	 * @param modelService saves the given {@code model}.
	 * @param model object to be saved and used as data source during mapping of resulting object.
	 * @param resultMapper maps the saved {@code model} to a resulting object.
	 * @return object mapped from the saved {@code model}.
	 */
	@Nonnull
	public static <M extends Model<ID>, ID, R> R save(@Nonnull ModelService<M,ID> modelService, @Nonnull M model,
			@Nonnull Function<M,R> resultMapper) {
		Assert.notNull(modelService, "modelManager");
		Assert.notNull(model, "model");
		M savedEntity = modelService.save(model);
		return mapToTarget(savedEntity, resultMapper);
	}
	
	/**
	 * Retrieves a model with the given {@code modelId} from {@code modelService}.
	 * @param modelService provides a model.
	 * @param modelId id of a model to be retrieved.
	 * @return model with the given {@code modelId}.
	 * @throws NotFoundException if model with the given {@code modelId} is not found.
	 */
	@Nonnull
	public static <M extends Model<ID>, ID> M getById(@Nonnull ModelService<M,ID> modelService, @Nonnull ID modelId)
			throws NotFoundException {
		Assert.notNull(modelService, "modelManager");
		Assert.notNull(modelId, "modelId");
		try {
			return modelService.getById(modelId);
		} catch (ModelNotFoundException e) {
			throw NotFoundException.ofId(modelService.getModelClass(), modelId, e);
		}
	}
	
	/**
	 * Retrieves a model with the given {@code modelId} from {@code modelService} and maps it to a resulting object by
	 * applying {@code resultMapper}.
	 * @param modelService provides a model.
	 * @param modelId id of a model to be retrieved.
	 * @param resultMapper maps a retrieved model to a resulting object.
	 * @return object mapped from retrieved {@code model}.
	 */
	@Nonnull
	public static <M extends Model<ID>, ID, R> R getById(@Nonnull ModelService<M,ID> modelService, @Nonnull ID modelId,
			@Nonnull Function<M,R> resultMapper)
			throws NotFoundException {
		return mapToTarget(getById(modelService, modelId), resultMapper);
	}
	
	@Nonnull
	public static <SOURCE, M extends Model<ID>, ID, R> R create(@Nonnull SOURCE modelSource,
			@Nonnull Function<SOURCE,M> modelCreator, @Nonnull ModelService<M,ID> modelService,
			@Nonnull Function<M,R> resultMapper) {
		Assert.notNull(modelSource, "modelSource");
		Assert.notNull(modelCreator, "modelCreator");
		M newEntity = modelCreator.apply(modelSource);
		return save(modelService, newEntity, resultMapper);
	}
	
	@Nonnull
	public static <SOURCE, M extends Model<ID>, ID> ResponseEntity<Object> create(@Nonnull SOURCE modelSource,
			@Nonnull Function<SOURCE,M> modelCreator, @Nonnull ModelService<M,ID> modelService,
			@Nonnull String modelIdAppendPath) {
		Assert.notNull(modelIdAppendPath, modelIdAppendPath);
		return create(modelSource, modelCreator, modelService, savedEntity -> {
			URI location = ServletUriComponentsBuilder.fromCurrentRequest()
					.path(modelIdAppendPath)
					.buildAndExpand(savedEntity.getId())
					.toUri();
			return ResponseEntity.created(location).build();
		});
	}
	
	@Nonnull
	public static <SOURCE, M extends Model<ID>, ID> ResponseEntity<Object> create(@Nonnull SOURCE modelSource,
			@Nonnull Function<SOURCE,M> modelCreator, @Nonnull ModelService<M,ID> modelService) {
		return create(modelSource, modelCreator, modelService, MODEL_ID_APPEND_PATH);
	}
	
	@Nonnull
	public static <SOURCE, M extends Model<ID>, ID, R> R update(@Nonnull SOURCE updateSource,
			@Nonnull M model, @Nonnull BiFunction<SOURCE,M,M> modelUpdater,
			@Nonnull ModelService<M,ID> modelService, @Nonnull Function<M,R> resultMapper) {
		Assert.notNull(updateSource, "updateSource");
		Assert.notNull(model, "model");
		Assert.notNull(modelUpdater, "modelUpdater");
		M updatedEntity = modelUpdater.apply(updateSource, model);
		return save(modelService, updatedEntity, resultMapper);
	}
	
	@Nonnull
	public static <SOURCE, M extends Model<ID>, ID, R> R update(@Nonnull ModelService<M,ID> modelService,
			@Nonnull ID modelId, @Nonnull SOURCE updateSource, @Nonnull BiFunction<SOURCE,M,M> modelUpdater,
			@Nonnull Function<M,R> resultMapper) {
		return update(updateSource, getById(modelService, modelId), modelUpdater, modelService, resultMapper);
	}
	
}
