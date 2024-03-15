package ua.od.whcrow.samples.spring6.boot_web._global.persistence;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ua.od.whcrow.samples.spring6.boot_web._global.Operation;

import java.util.Arrays;

@Converter(autoApply = true)
public class OperationAttributeConverter implements AttributeConverter<Operation,Integer> {
	
	@Override
	public Integer convertToDatabaseColumn(Operation attribute) {
		return attribute.getId();
	}
	
	@Override
	public Operation convertToEntityAttribute(Integer dbData) {
		return Arrays.stream(Operation.values())
				.filter(o -> o.getId() == dbData)
				.findFirst()
				.orElseThrow();
	}
	
}
