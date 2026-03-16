package com.fflmanager.events.entity;


import com.fflmanager.enums.FightCategory;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class FightCategoryConverter implements AttributeConverter<FightCategory, String> {

    @Override
    public String convertToDatabaseColumn(FightCategory category) {
        return category == null ? null : category.name();
    }

    @Override
    public FightCategory convertToEntityAttribute(String value) {
        return value == null ? null : FightCategory.valueOf(value);
    }

}