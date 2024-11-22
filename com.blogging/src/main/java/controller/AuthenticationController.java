package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import entity.User;
import exception.UserNotFoundException;
import service.UserService;
import util.JwtUtil;
import util.PasswordEncoder;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        User user = userService.findByUsername(loginRequest.getUsername());
        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return JwtUtil.generateToken(user);
        } else {
            throw new UserNotFoundException("Invalid username or password");
        }
    }

    @PostMapping("/register")
    public User register(@RequestBody LoginRequest loginRequest) {
        User user = new User(loginRequest.getUsername(), passwordEncoder.encode(loginRequest.getPassword()));
        return userService.save(user);
    }

    // Inner static class to handle username and password
    public static class LoginRequest {
        private String username;
        private String password;

        // Getters and setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
