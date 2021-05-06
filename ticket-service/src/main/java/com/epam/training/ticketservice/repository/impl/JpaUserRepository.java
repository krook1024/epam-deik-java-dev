package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.UserDao;
import com.epam.training.ticketservice.dataaccess.projection.UserProjection;
import com.epam.training.ticketservice.domain.User;
import com.epam.training.ticketservice.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public class JpaUserRepository implements UserRepository {

    private final UserDao userDao;

    public JpaUserRepository(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User findByName(String name) throws UsernameNotFoundException {
        UserProjection userProjection = userDao.findByName(name).orElseThrow(
            () -> new UsernameNotFoundException("Username not found for name " + name)
        );

        return new User(userProjection.getName(),
            userProjection.getPassword(),
            userProjection.getIsAdmin());
    }

    @Override
    public void save(User user) {
        userDao.save(new UserProjection(
            null,
            user.getName(),
            user.getPassword(),
            user.getIsAdmin()
        ));
    }
}
