package ua.od.whcrow.samples.spring6.boot_web._global;

import jakarta.annotation.Nonnull;
import ua.od.whcrow.samples.spring6.boot_web._commons.exceptions.ConstantsClassInstantiationException;
import ua.od.whcrow.samples.spring6.boot_web._commons.exceptions.FaultyCodeException;
import ua.od.whcrow.samples.spring6.boot_web._commons.util.ConstantsUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

public enum Operation {
	
	CREATE_ROLE(100),
	READ_ROLE(101),
	UPDATE_ROLE(102),
	DELETE_ROLE(103),
	LIST_ROLE(104),
	CREATE_IDENTITY(200),
	READ_IDENTITY(201),
	UPDATE_IDENTITY(202),
	DELETE_IDENTITY(203),
	LIST_IDENTITY(204),
	;
	
	public static final class Constants {
		
		public static final String CREATE_ROLE = "CREATE_ROLE";
		public static final String READ_ROLE = "READ_ROLE";
		public static final String UPDATE_ROLE = "UPDATE_ROLE";
		public static final String DELETE_ROLE = "DELETE_ROLE";
		public static final String LIST_ROLE = "LIST_ROLE";
		public static final String CREATE_IDENTITY = "CREATE_IDENTITY";
		public static final String READ_IDENTITY = "READ_IDENTITY";
		public static final String UPDATE_IDENTITY = "UPDATE_IDENTITY";
		public static final String DELETE_IDENTITY = "DELETE_IDENTITY";
		public static final String LIST_IDENTITY = "LIST_IDENTITY";
		
		private Constants()
				throws ConstantsClassInstantiationException {
			throw new ConstantsClassInstantiationException(Constants.class);
		}
		
		@Nonnull
		private static Collection<String> getInvalidConstants() {
			return ConstantsUtil.getNonMatchingConstants(Constants.class, true, Constants::matchConstant).keySet()
					.stream()
					.map(Field::getName)
					.toList();
		}
		
		private static boolean matchConstant(@Nonnull Field field, Object value) {
			return field.getName().equals(value)
					&& Arrays.stream(Operation.values()).anyMatch(o -> field.getName().equals(o.name()));
		}
		
		static void checkConstants() {
			Collection<String> invalidConstants = getInvalidConstants();
			if (!invalidConstants.isEmpty()) {
				throw new FaultyCodeException(
						"Following constants are invalid (name->value mismatch; not backed by enum): {}",
						invalidConstants);
			}
		}
		
	}
	
	private final int id;
	
	Operation(int id) {
		this.id = id;
		if (ConstantsUtil.getConstantFieldStream(Constants.class, true).noneMatch(f -> f.getName().equals(name()))) {
			throw new FaultyCodeException("Operation {} is not represented in constants", name());
		}
	}
	
	static {
		Constants.checkConstants();
	}
	
	public int getId() {
		return id;
	}
	
}
