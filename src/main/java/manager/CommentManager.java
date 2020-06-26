package manager;


import db.DBConnectionProvider;
import model.Comment;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentManager {
    private Connection connection;

    {
        try {
            connection = DBConnectionProvider.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addComment(Comment comment) throws SQLException {
        String sql = "INSERT INTO comment(task_id,user_id,comment) VALUES(?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, comment.getTaskId());
        preparedStatement.setInt(2, comment.getUserId());
        preparedStatement.setString(3, comment.getComment());
        preparedStatement.executeUpdate();
        ResultSet rs = preparedStatement.getGeneratedKeys();
        if (rs.next()) {
            comment.setId(rs.getInt(1));
        }
    }

    public List<Comment> getAllCommentByTaskId(int id) throws SQLException {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM comment WHERE task_id = " + id;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            comments.add(getCommentFromResultSet(resultSet));
        }
            return comments;
    }

    private Comment getCommentFromResultSet(ResultSet resultSet) {
        Comment comment = null;
        try {
            comment = comment.builder()
                    .id(resultSet.getInt(1))
                    .taskId(resultSet.getInt(2))
                    .userId(resultSet.getInt(3))
                    .comment(resultSet.getString(4))
                    .date(resultSet.getDate(5))
                    .build();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return comment;
    }
    public void deleteCommentById(int Id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE  FROM comment WHERE id=?");
        preparedStatement.setInt(1, Id);
        preparedStatement.executeUpdate();
    }
}
