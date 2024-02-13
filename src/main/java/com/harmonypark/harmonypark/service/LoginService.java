package com.harmonypark.harmonypark.service;

import com.harmonypark.harmonypark.dto.AuthenticationResponse;
import com.harmonypark.harmonypark.dto.LoginRequest;
import com.harmonypark.harmonypark.dto.OTPRequest;
import com.harmonypark.harmonypark.entities.User;
import com.harmonypark.harmonypark.exception.UnAuthorizedException;
import com.harmonypark.harmonypark.exception.UserNotFoundException;
import com.harmonypark.harmonypark.repositories.UserRepository;
import com.harmonypark.harmonypark.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.harmonypark.harmonypark.ErrorCode.INVALID_CREDENTIALS;
import static com.harmonypark.harmonypark.service.UserServiceImp.generateOTP;
import static java.lang.String.format;
import static utils.GeneralConstants.ERROR_MSG;

@Slf4j
@Service
@RequiredArgsConstructor

public class LoginService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final EmailServiceImp emailService;
    private final HttpServletRequest request;


    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Transactional
    public AuthenticationResponse loginUser(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()
                    ));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(UserNotFoundException::new);


            if (user.isTwoFactorEnabled()) {
                String otp = String.valueOf(generateOTP());
                user.setValidOtp(passwordEncoder.encode(otp));
                userRepository.save(user);
                String url = "http://" + request.getServerName() + ":8080" + "/api/v1/auth/login/verify-login?otp="
                        + otp + "&email=" + user.getEmail();

                String subject = "Harmony Park: Verify Login";

                String body =
                        "<html> " +
                                "<body>" +
                                "<h4>Hi " + user.getFirstName() + " " + user.getLastName() + ",</h4> \n" +
                                "<p>Welcome to Harmony Park.\n" +
                                "To continue your Login, enter your OTP" +
                                "Your otp is " + otp + "\n" +
                                "<a href=" + url + ">verify here</a></p>" +
                                "</body> " +
                                "</html>";

                emailService.sendMail(user.getEmail(), subject, body, "text/html");
                // user should be redirected to where they will verify their otp
                // Return a placeholder response indicating that OTP verification is pending
                return new AuthenticationResponse(
                        "Pending",
                        user.getFirstName()+ " " +user.getLastName(),
                        user.getRole(),
                        user.getEmail(),
                        user.getPhoneNumber(),
                        user.getId());

            } else {
                // if user.is2FAenabled = false then
                var userToken = jwtService.generateJwtToken(user);
                var name = user.getFirstName() + " " + user.getLastName();
                var email = user.getEmail();
                var phoneNumber = user.getPhoneNumber();
                var id = user.getId();
                var savedPassword = user.getPassword();
                var loginPassword = loginRequest.getPassword(); // Assuming it's already plain text
                if (passwordEncoder.matches(loginPassword, savedPassword)) {
                    return new AuthenticationResponse(userToken, name, user.getRole(), email, phoneNumber, id);
                }
                throw new UnAuthorizedException("Incorrect password", INVALID_CREDENTIALS);
            }

        } catch (Exception e) {
            log.error(format(ERROR_MSG, e.getLocalizedMessage()));
            throw new UnAuthorizedException("Invalid email/or password", INVALID_CREDENTIALS);
        }


    }
}
