package com.dux.equipos_futbol.service.impl;

import com.dux.equipos_futbol.model.Users;
import com.dux.equipos_futbol.repository.UserRepository;
import com.dux.equipos_futbol.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Users save(Users user) {
        return userRepository.save(user);
    }
}