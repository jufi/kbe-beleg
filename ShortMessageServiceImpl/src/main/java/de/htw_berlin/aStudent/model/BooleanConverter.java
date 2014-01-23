package de.htw_berlin.aStudent.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Kevin Goy
 */
@Converter(autoApply = true)
public class BooleanConverter implements AttributeConverter<Boolean, String> {
	@Override
	public String convertToDatabaseColumn(Boolean value) {
		if (value) {
			return "T";
		}
		else {
			return "F";
		}
	}

	@Override
	public Boolean convertToEntityAttribute(String value) {
		return "T".equals(value);
	}
}
