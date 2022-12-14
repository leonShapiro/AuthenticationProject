package AuthenticationProject.Controllers;

import AuthenticationProject.Services.*;
import java.util.regex.*;

public class UserController {
    private static UserController userController;
    private static UserService userService;
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z]).{8,20}$");
    private static final Pattern emailPattern = Pattern.compile(".+@.+\\.[a-z]+");
    private static AuthenticationService authenticationService;


    private UserController() {
        userService = UserService.getInstance();
        authenticationService = AuthenticationService.getInstance();
    }

    public static synchronized UserController getInstance() {
        if (userController == null) {
            userController = new UserController();
        }
        return userController;
    }

    public void updateUserName(String id, String token, String userName) {
        if (authenticateUser(id, token)) {
            userService.updateUserName(id, userName);
        }
        throw new IllegalStateException("The user was not authenticated");
    }

    public void updateEmail(String id, String token, String email) {
        if (authenticateUser(id, token)) {
            if (validateEmail(email)) {
                userService.updateEmail(id, email);
            } else {
                throw new IllegalArgumentException("Invalid email inserted");
            }
        } else {
            throw new IllegalStateException("The user was not authenticated");
        }

    }

    public void updatePassword(String id, String token, String password) {
        if (authenticateUser(id, token)) {
            if (validatePassword(password)) {
                userService.updatePassword(id, password);
            } else {
                throw new IllegalArgumentException("Invalid password inserted");
            }
        } else {
            throw new IllegalStateException("The user was not authenticated");
        }

    }

    public boolean authenticateUser(String id, String token) {
        return authenticationService.authUser(id, token);
    }

    public boolean validateEmail(String email) {
        Matcher m = emailPattern.matcher(email);
        boolean matchFound = m.matches();
        if (matchFound) {
            if (userService.checkIfEmailExists(email)) {
                throw new IllegalArgumentException("You cant change the email to the same one");
            }
            return true;
        }
        return false;
    }

    public boolean validatePassword(String password) {
        Matcher m = PASSWORD_PATTERN.matcher(password);
        boolean matchFound = m.matches();
        if (matchFound) {
            return true;
        }
        return false;
    }

    public void deleteUser(String id, String token) {
        if (authenticateUser(id, token)) {
            authenticationService.deleteUserFromMap(id);
            userService.deleteUser(id);
        } else {
            throw new IllegalStateException("The user was not authenticated");
        }
    }
}