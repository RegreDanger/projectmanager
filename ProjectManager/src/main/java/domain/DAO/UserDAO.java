package domain.DAO;
import java.sql.*;

import domain.User;
import domain.utils.HashingUtils;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class UserDAO {
    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public void createUser(User user) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        String hashedPassword = HashingUtils.hash(user.getPassword(), HashingUtils.generateSalt());
        String sql = "INSERT INTO users (username, fullname, email, password_hash, email_verified) VALUES (?, ?, ?, ?, ?, false)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getFullName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, hashedPassword);
            stmt.executeUpdate();
        }
    }

    public User getUserByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("userID"), rs.getString("username"), rs.getString("fullname"), rs.getString("email"), rs.getString("password"), new Date (rs.getDate("created_at").getTime()));
            }
        }
        return null;
    }

    public void deleteUser(int userId) throws SQLException {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }

    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE users (username, fullname, email,) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getFullName());
            stmt.setString(3, user.getEmail());
            stmt.executeUpdate();
        }
    }
}
