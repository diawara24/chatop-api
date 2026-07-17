package com.openclassroom.chatopapi.services.impl;

import com.openclassroom.chatopapi.dto.UserDto;
import com.openclassroom.chatopapi.model.User;
import com.openclassroom.chatopapi.repository.UserRepository;
import com.openclassroom.chatopapi.services.UserService;
import com.openclassroom.chatopapi.utils.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper mapper;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> user = repository.findByEmail(email);
        if (user.isEmpty()) {
            LOGGER.error("Utilisateur non trouvé par email: " + email);
            throw new UsernameNotFoundException("Utilisateur non trouvé par email:  " + email);
        } else {
            return new UserPrincipal(user.get());
        }
    }

    @Override
    public User save(User user) {
        if(repository.findByEmail(user.getEmail()).isPresent()){
            throw new RuntimeException("Email Exist déjà");
        }
        user.setPassword(encodePassword(user.getPassword()));
        return repository.save(user);
    }

    @Override
    public UserDto findById(Integer id) {
        Optional<User> user = repository.findById(id);
        if (user.isEmpty()) {
            LOGGER.error("Utilisateur non trouvé par l'id: " + id);
            throw new UsernameNotFoundException("Utilisateur non trouvé par l'id :  " + id);
        }
        return mapper.map(user.get(), UserDto.class);
    }


    private String encodePassword(String password) {
        return  passwordEncoder.encode(password);
    }
}
