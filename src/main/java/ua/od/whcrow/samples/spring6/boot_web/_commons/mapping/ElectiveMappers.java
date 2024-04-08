package ua.od.whcrow.samples.spring6.boot_web._commons.mapping;

import jakarta.annotation.Nonnull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ua.od.whcrow.samples.spring6.boot_web._commons.exceptions.FaultyCodeException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Lazy
@Service
public class ElectiveMappers {
	
	private static final String MSG_CREATE = "None of {}s can create an object of type {} from the following object:"
			+ System.lineSeparator() + "{}";
	private static final String MSG_UPDATE = "None of {}s can update a target object from a source object:"
			+ System.lineSeparator() + "SOURCE: {}" + System.lineSeparator() + "TARGET: {}";
	
	private final List<ElectiveMapper> mappers;
	
	public ElectiveMappers(List<ElectiveMapper> mappers) {
		this.mappers = mappers;
	}
	
	@Nonnull
	public Optional<?> createOptional(@Nonnull Object source, @Nonnull Class<?> target) {
		return mappers.stream()
				.map(m -> m.createIfApplicable(source, target))
				.filter(Objects::nonNull)
				.findFirst();
	}
	
	@Nonnull
	public Optional<?> updateOptional(@Nonnull Object source, @Nonnull Object target) {
		return mappers.stream()
				.map(m -> m.updateIfApplicable(source, target))
				.filter(Objects::nonNull)
				.findFirst();
	}
	
	@Nonnull
	public Object create(@Nonnull Object source, @Nonnull Class<?> target)
			throws FaultyCodeException {
		return createOptional(source, target).orElseThrow(() ->
				new FaultyCodeException(MSG_CREATE, ElectiveMapper.class.getSimpleName(), target.getName(), source));
	}
	
	@Nonnull
	public Object update(@Nonnull Object source, @Nonnull Object target)
			throws FaultyCodeException {
		return updateOptional(source, target).orElseThrow(() ->
				new FaultyCodeException(MSG_UPDATE, ElectiveMapper.class.getSimpleName(), source, target));
	}
	
}
