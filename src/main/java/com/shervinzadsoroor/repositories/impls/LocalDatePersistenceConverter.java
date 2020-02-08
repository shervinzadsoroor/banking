package com.shervinzadsoroor.repositories.impls;

import javax.persistence.AttributeConverter;
import java.time.LocalDate;
import java.sql.Date;

public class LocalDatePersistenceConverter implements
        AttributeConverter<LocalDate, Date> {
    @Override
    public Date convertToDatabaseColumn(LocalDate entityValue) {
        return Date.valueOf(entityValue);
    }


    @Override
    public LocalDate convertToEntityAttribute(Date databaseValue) {
        return databaseValue.toLocalDate();
    }
}