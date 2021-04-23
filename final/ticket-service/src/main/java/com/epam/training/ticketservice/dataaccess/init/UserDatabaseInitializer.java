package com.epam.training.ticketservice.dataaccess.init;

import com.epam.training.ticketservice.dataaccess.dao.UserDao;
import com.epam.training.ticketservice.dataaccess.projection.UserProjection;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class UserDatabaseInitializer {

    private UserDao userDao;

    public UserDatabaseInitializer(UserDao userDao) {
        this.userDao = userDao;
    }

    @PostConstruct
    public void initDatabase() {
        userDao.save(
                new UserProjection("admin", "admin", true)
        );
    }
}
