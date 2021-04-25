package com.epam.training.ticketservice.presentation.cli;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateConverterComponent implements Converter<String, Date> {
    protected static final String DATE_FORMAT = "yyyy-mm-dd HH:MM";


    @Override
    public Date convert(String source) {
        try {
            return new SimpleDateFormat(DATE_FORMAT).parse(source);
        } catch (ParseException e) {
            return null;
        }
    }
}
