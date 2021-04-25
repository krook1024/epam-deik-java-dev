package com.epam.training.ticketservice.dataaccess.init;

import com.epam.training.ticketservice.dataaccess.dao.UserDao;
import com.epam.training.ticketservice.dataaccess.projection.UserProjection;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;


@Component
public class UserDatabaseInitializer {

    private UserDao userDao;

    public UserDatabaseInitializer(UserDao userDao) {
        this.userDao = userDao;
    }

    @PostConstruct
    public void initDatabase() {
        Optional<UserProjection> userProjectionOptional = userDao.findByName("admin");

        if (!userProjectionOptional.isPresent()) {
            userDao.save(
                    new UserProjection("admin", "admin", true)
            );
        }

    }
}
