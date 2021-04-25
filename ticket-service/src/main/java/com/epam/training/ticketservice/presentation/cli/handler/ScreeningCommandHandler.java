package com.epam.training.ticketservice.presentation.cli.handler;

import com.epam.training.ticketservice.auth.SecuredCommandHandler;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.service.RoomService;
import com.epam.training.ticketservice.service.ScreeningService;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@ShellComponent
@ShellCommandGroup("Screening commands")
public class ScreeningCommandHandler extends SecuredCommandHandler {
    protected static final String DATE_FORMAT = "yyyy-mm-dd HH:mm";

    ScreeningService screeningService;

    public ScreeningCommandHandler(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    @ShellMethod(key = "create screening", value = "Creates a screening")
    @ShellMethodAvailability("isUserSignedIn")
    public void createScreening(String movieTitle, String roomName, String dateString) {
        Date date;
        try {
            date = new SimpleDateFormat(DATE_FORMAT).parse(dateString);
        } catch (IllegalArgumentException | ParseException e) {
            System.out.println("Invalid date format");
            return;
        }

        try {
            screeningService.createScreening(movieTitle, roomName, date);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
