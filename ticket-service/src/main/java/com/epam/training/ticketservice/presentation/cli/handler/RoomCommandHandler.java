package com.epam.training.ticketservice.presentation.cli.handler;

import com.epam.training.ticketservice.auth.SecuredCommandHandler;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.service.RoomService;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;

@ShellComponent
@ShellCommandGroup("Room commands")
public class RoomCommandHandler extends SecuredCommandHandler {

    RoomService roomService;
    RoomRepository roomRepository;

    public RoomCommandHandler(RoomService roomService, RoomRepository roomRepository) {
        this.roomService = roomService;
        this.roomRepository = roomRepository;
    }

    @ShellMethod(value = "Creates a room", key = "create room")
    @ShellMethodAvailability("isUserSignedIn")
    public void createRoom(String name, int rows, int cols) {
        roomService.saveRoom(name, rows, cols);
    }

    @ShellMethod(value = "Updates a room", key = "update room")
    @ShellMethodAvailability("isUserSignedIn")
    public void updateRoom(String name, int rows, int cols) {
        Room room = new Room(name, rows, cols);
        roomService.updateRoom(name, room);
    }

    @ShellMethod(value = "Deletes a room", key = "delete room")
    @ShellMethodAvailability("isUserSignedIn")
    public void deleteRoom(String name) {
        roomRepository.delete(name);
    }

    @ShellMethod(value = "Lists rooms", key = "list rooms")
    public List<Room> listRooms() {
        List<Room> rooms = roomRepository.findAll();

        if (rooms.isEmpty()) {
            System.out.println("There are no rooms at the moment");
        }

        return rooms;
    }
}
