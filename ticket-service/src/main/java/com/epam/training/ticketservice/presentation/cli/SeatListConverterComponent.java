package com.epam.training.ticketservice.presentation.cli;

import com.epam.training.ticketservice.domain.Seat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SeatListConverterComponent implements Converter<String, List<Seat>> {

    @Override
    public List<Seat> convert(String s) {
        return Arrays.stream(s.split(" ")).map(element -> {
            List<String> seats = List.of(element.split(","));
            return new Seat(Integer.parseInt(seats.get(0)), Integer.parseInt(seats.get(1)));
        }).collect(Collectors.toList());
    }
}
