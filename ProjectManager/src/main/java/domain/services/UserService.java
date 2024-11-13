package domain.services;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.UUID;
import javax.mail.MessagingException;

import domain.User;
import domain.DAO.UserDAO;

public class UserService {
    private UserDAO userDAO;
    //private EmailVerificationDAO emailVerificationDAO;
    private EmailService emailService;

    public UserService(UserDAO userDAO, /*EmailVerificationDAO emailVerificationDAO,*/ EmailService emailService) {
        this.userDAO = userDAO;
        //this.emailVerificationDAO = emailVerificationDAO;
        this.emailService = emailService;
    }

    public void registerUser(User user) throws SQLException, MessagingException, NoSuchAlgorithmException, InvalidKeySpecException {
        userDAO.createUser(user);
        String token = UUID.randomUUID().toString();
        //emailVerificationDAO.saveVerificationToken();
        //emailService.sendVerificationEmail(user.getEmail(), token);
    }

    public boolean verifyEmail(String token) throws SQLException {
        return false; //emailVerificationDAO.verifyToken(token);
    }
}

