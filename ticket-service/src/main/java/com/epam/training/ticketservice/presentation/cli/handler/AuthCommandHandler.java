package com.epam.training.ticketservice.presentation.cli.handler;

import com.epam.training.ticketservice.auth.SecuredCommandHandler;
import com.epam.training.ticketservice.domain.Booking;
import com.epam.training.ticketservice.repository.BookingRepository;
import com.epam.training.ticketservice.service.UserService;
import java.util.Collection;
import java.util.List;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@ShellCommandGroup("Authentication Commands")
public class AuthCommandHandler extends SecuredCommandHandler {

    AuthenticationManager authenticationManager;
    UserService userService;
    BookingRepository bookingRepository;

    public AuthCommandHandler(AuthenticationManager authenticationManager,
        UserService userService,
        BookingRepository bookingRepository) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.bookingRepository = bookingRepository;
    }

    @ShellMethod(value = "Signs up as a regular user", key = "sign up")
    public void register(String name, String password) {
        userService.createUser(name, password);
    }

    @ShellMethod(value = "Attempts a login as a regular user", key = "sign in")
    @ShellMethodAvailability("isUserSignedOut")
    public String login(String name, String password) {
        return attemptLogin(name, password);
    }

    @ShellMethod(value = "Attempts a login as an admin", key = "sign in privileged")
    @ShellMethodAvailability("isUserSignedOut")
    public String loginPrivileged(String name, String password) {
        return attemptLogin(name, password);
    }

    @ShellMethod(value = "Logs out", key = "sign out")
    @ShellMethodAvailability("isUserSignedIn")
    public void logout() {
        SecurityContextHolder.clearContext();
    }

    @ShellMethod(value = "Shows account information when signed in", key = "describe account")
    public String describeAccount() {
        if (isUserSignedIn().isAvailable()) {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();

            String userName = SecurityContextHolder.getContext().getAuthentication().getName();

            if (roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                return "Signed in with privileged account '" + userName + "'";
            }

            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("Signed in with account '" + userName + "'\n");

            List<Booking> bookings = bookingRepository.findByUserName(userName);

            if (!bookings.isEmpty()) {
                stringBuilder.append("Your previous bookings are\n");
                bookings.forEach(booking -> stringBuilder.append(booking + "\n"));
            } else {
                stringBuilder.append("You have not booked any tickets yet");
            }

            return stringBuilder.toString();
        }

        return "You are not signed in";
    }

    private String attemptLogin(String name, String password) {
        Authentication req = new UsernamePasswordAuthenticationToken(name, password);

        try {
            Authentication result = authenticationManager.authenticate(req);
            SecurityContextHolder.getContext().setAuthentication(result);
            return null;
        } catch (AuthenticationException e) {
            return "Login failed due to incorrect credentials";
        }
    }
}
