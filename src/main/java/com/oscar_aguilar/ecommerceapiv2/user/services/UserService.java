package com.oscar_aguilar.ecommerceapiv2.user.services;

import com.oscar_aguilar.ecommerceapiv2.onboarding.confirmation_code.entities.ConfirmationCode;
import com.oscar_aguilar.ecommerceapiv2.onboarding.confirmation_code.services.ConfirmationCodeService;
import com.oscar_aguilar.ecommerceapiv2.user.entities.User;
import com.oscar_aguilar.ecommerceapiv2.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "User with email %s not found";
    private final static Random random = new Random();

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationCodeService confirmationCodeService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public ConfirmationCode registerUser(User user) {
        boolean userExists = userRepository
                .findByEmail(user.getEmail())
                .isPresent();

//        TODO: If email not confirmed, send confirmation email again
        if (userExists)
            throw new IllegalStateException("Email already taken");

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);
        userRepository.save(user);

        int code = random.nextInt(900000) + 100000;

        ConfirmationCode confirmationCode = new ConfirmationCode(
                code,
                LocalDateTime.now().plusMinutes(15),
                LocalDateTime.now(),
                user
        );
        confirmationCodeService.saveConfirmationCode(confirmationCode);
        return confirmationCode;
    }

    public void enableUser(String email) {
        userRepository.enableUser(email);
    }
}
