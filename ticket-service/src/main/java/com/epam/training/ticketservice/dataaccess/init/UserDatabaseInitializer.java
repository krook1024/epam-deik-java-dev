package com.epam.training.ticketservice.dataaccess.init;

import com.epam.training.ticketservice.dataaccess.dao.UserDao;
import com.epam.training.ticketservice.dataaccess.projection.UserProjection;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class UserDatabaseInitializer {

    private final UserDao userDao;

    public UserDatabaseInitializer(UserDao userDao) {
        this.userDao = userDao;
    }

    @PostConstruct
    public void initDatabase() {
        Optional<UserProjection> userProjectionOptional = userDao.findByName("admin");

        if (userProjectionOptional.isEmpty()) {
            userDao.save(
                new UserProjection(null, "admin", "admin", true)
            );
        }

    }
}
