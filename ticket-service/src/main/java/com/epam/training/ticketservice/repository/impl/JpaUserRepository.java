package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.UserDao;
import com.epam.training.ticketservice.dataaccess.projection.UserProjection;
import com.epam.training.ticketservice.domain.User;
import com.epam.training.ticketservice.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaUserRepository implements UserRepository {
    private final UserDao userDao;

    public JpaUserRepository(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void saveUser(User user) {
        userDao.save(mapUser(user));
    }

    @Override
    public User findByName(String name) throws UsernameNotFoundException {
        Optional<UserProjection> userProjection = userDao.findByName(name);

        if (userProjection.isPresent()) {
            UserProjection projection = userProjection.get();
            return new User(projection.getName(),
                    projection.getPassword(),
                    projection.getIsAdmin());
        } else {
            throw new UsernameNotFoundException("Username not found for name " + name);
        }
    }

    private List<UserProjection> mapUsers(List<User> users) {
        return users.stream()
                .map(this::mapUser)
                .collect(Collectors.toList());
    }

    private UserProjection mapUser(User user) {
        return userDao.findByName(user.getName())
                .orElseThrow(
                        () -> new IllegalArgumentException("User not found for name " + user.getName())
                );
    }
}
