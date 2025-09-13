package com.example.webapp;

import java.sql.*;
import java.util.*;
import org.mindrot.jbcrypt.BCrypt;

public class UserDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/user_management_db";
    private String jdbcUsername = "root";
    private String jdbcPassword = "your_mysql_password";

    private static final String INSERT_USER = "INSERT INTO users (username,password,email,role) VALUES (?,?,?,?)";
    private static final String SELECT_USER_BY_USERNAME = "SELECT * FROM users WHERE username=?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String UPDATE_USER = "UPDATE users SET username=?, email=?, role=? WHERE id=?";
    private static final String DELETE_USER = "DELETE FROM users WHERE id=?";

    protected Connection getConnection() throws SQLException {
        try { Class.forName("com.mysql.cj.jdbc.Driver"); } catch(Exception e) { e.printStackTrace(); }
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    public boolean registerUser(User user) {
        boolean rowInserted = false;
        try(Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(INSERT_USER)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, hashPassword(user.getPassword()));
            ps.setString(3, user.getEmail());
            ps.setString(4, "user");
            rowInserted = ps.executeUpdate() > 0;
        } catch(SQLException e) { e.printStackTrace(); }
        return rowInserted;
    }

    public User authenticateUser(String username, String password) {
        User user = null;
        try(Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(SELECT_USER_BY_USERNAME)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                String hashed = rs.getString("password");
                if(checkPassword(password, hashed)) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setRole(rs.getString("role"));
                }
            }
        } catch(SQLException e) { e.printStackTrace(); }
        return user;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try(Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(SELECT_ALL_USERS)) {
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setEmail(rs.getString("email"));
                u.setRole(rs.getString("role"));
                users.add(u);
            }
        } catch(SQLException e) { e.printStackTrace(); }
        return users;
    }

    public boolean updateUser(User user) {
        boolean rowUpdated = false;
        try(Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(UPDATE_USER)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getRole());
            ps.setInt(4, user.getId());
            rowUpdated = ps.executeUpdate() > 0;
        } catch(SQLException e) { e.printStackTrace(); }
        return rowUpdated;
    }

    public boolean deleteUser(int id) {
        boolean rowDeleted = false;
        try(Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(DELETE_USER)) {
            ps.setInt(1, id);
            rowDeleted = ps.executeUpdate() > 0;
        } catch(SQLException e) { e.printStackTrace(); }
        return rowDeleted;
    }

    private String hashPassword(String password) { return BCrypt.hashpw(password, BCrypt.gensalt()); }
    private boolean checkPassword(String password, String hashed) { return BCrypt.checkpw(password, hashed); }
}
