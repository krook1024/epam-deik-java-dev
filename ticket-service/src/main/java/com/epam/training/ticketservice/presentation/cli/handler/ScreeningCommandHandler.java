package com.epam.training.ticketservice.presentation.cli.handler;

import com.epam.training.ticketservice.auth.SecuredCommandHandler;
import com.epam.training.ticketservice.domain.Screening;
import com.epam.training.ticketservice.service.ScreeningService;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.Date;
import java.util.List;

@ShellComponent
@ShellCommandGroup("Screening commands")
public class ScreeningCommandHandler extends SecuredCommandHandler {

    ScreeningService screeningService;

    public ScreeningCommandHandler(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    @ShellMethod(key = "create screening", value = "Creates a screening")
    @ShellMethodAvailability("isUserSignedIn")
    public void createScreening(String movieTitle, String roomName, Date date) {
        try {
            screeningService.createScreening(movieTitle, roomName, date);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    @ShellMethod(key = "delete screening", value = "Deletes a screening")
    @ShellMethodAvailability("isUserSignedIn")
    public void deleteScreening(String movieTitle, String roomName, Date date) {
        screeningService.deleteScreening(movieTitle, roomName, date);
    }

    @ShellMethod(key = "list screenings", value = "Lists screenings")
    public List<Screening> listScreenings() {
        return screeningService.findAll();
    }
}
