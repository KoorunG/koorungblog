package com.koorung.blog.global.utils.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Boolean값을 DB에 저장할 때 "Y", "N" 으로 변경해주는 컨버터
 */
@Converter(autoApply = true)
public class BooleanYnConverter implements AttributeConverter<Boolean, String> {
    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return (attribute != null && attribute) ? "Y" : "N";
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return "Y".equals(dbData);
    }
}
