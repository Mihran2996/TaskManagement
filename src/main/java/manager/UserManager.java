package manager;


import db.DBConnectionProvider;
import model.User;
import model.UserType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserManager {
    private Connection connection;

    {
        try {
            connection = DBConnectionProvider.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addUser(User user) {
        String sql = "INSERT INTO `user`(name,surname,email,password,type,picture_url) VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, String.valueOf(user.getType()));
            preparedStatement.setString(6, user.getPictureUrl());
//            User byEmail = getByEmail(user.getEmail());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public User getUserById(int id) throws SQLException {
        String sql = "SELECT * FROM user WHERE id = " + id;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            return getUserFromResultSet(resultSet);
        }
        return null;
    }

    public User getUserByEmailAndPassword(String email, String password) throws SQLException {
        String sql = "SELECT * FROM user WHERE email=? AND password=? ";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, email);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return getUserFromResultSet(resultSet);
        }
        return null;
    }

    public User getUserByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM user WHERE email=? ";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            User user = getUserFromResultSet(resultSet);
            return user;
        }
        return null;
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user ";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            users.add(getUserFromResultSet(resultSet));
        }
        return users;
    }

    private User getUserFromResultSet(ResultSet resultSet) {
        User user = null;
        try {
            user = User.builder()
                    .id(resultSet.getInt(1))
                    .name(resultSet.getString(2))
                    .surname(resultSet.getString(3))
                    .email(resultSet.getString(4))
                    .password(resultSet.getString(5))
                    .type(UserType.valueOf(resultSet.getString(6)))
                    .pictureUrl(resultSet.getString(7))
                    .build();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return user;
    }

    public void deleteById(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE  FROM user WHERE id=?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

}


