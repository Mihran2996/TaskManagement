package manager;


import db.DBConnectionProvider;
import model.Task;
import model.TaskStatus;


import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class TaskManager {
    private UserManager userManager = new UserManager();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
    private Connection connection;


    {
        try {
            connection = DBConnectionProvider.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addTask(Task task) {
        String sql = "INSERT INTO task(name,description,deadline,status,user_id) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, task.getName());
            preparedStatement.setString(2, task.getDescription());
            if (task.getDeadline() != null) {
                preparedStatement.setString(3, sdf.format(task.getDeadline()));
            } else {
                preparedStatement.setString(3, null);
            }
            preparedStatement.setString(4, task.getStatus().name());
            preparedStatement.setInt(5, task.getUserId());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                task.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Task> getAllTaskByUserId(int id) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM task WHERE user_id = " + id;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            tasks.add(getTaskFromResultSet(resultSet));
        }
        return tasks;
    }

    public Task getTaskById(int id) throws SQLException {
        String sql = "SELECT * FROM task WHERE id = " + id;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        Task task = null;
        if (resultSet.next()) {
            task = getTaskFromResultSet(resultSet);
        }
        return task;
    }

    public boolean updateTask(int id, TaskStatus status) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `task` SET status=? WHERE id=?");
            preparedStatement.setLong(2, id);
            preparedStatement.setString(1, status.name());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteTask(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE  FROM `task`  WHERE id =?");
            statement.setInt(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public List<Task> getAllTask() throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM task  ";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            tasks.add(getTaskFromResultSet(resultSet));
        }
        return tasks;
    }

    public List<Task> getAllTaskByUserIdAndStatus(int userId, TaskStatus status) throws SQLException {
        List<Task> tasks = new ArrayList<Task>();
        ResultSet resultSet;
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM task WHERE user_id=? AND status=?");
        preparedStatement.setLong(1, userId);
        preparedStatement.setString(2, status.name());
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            tasks.add(getTaskFromResultSet(resultSet));
        }
        return tasks;
    }

    private Task getTaskFromResultSet(ResultSet resultSet) {
        Task task = null;
        try {
            task = task.builder()
                    .id(resultSet.getInt(1))
                    .name(resultSet.getString(2))
                    .description(resultSet.getString(3))
                    .deadline(resultSet.getString(4) == null ? null : sdf.parse(resultSet.getString(4)))
                    .status(TaskStatus.valueOf(resultSet.getString(5)))
                    .userId(resultSet.getInt(6))
                    .user(userManager.getUserById(resultSet.getInt(6)))
                    .build();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return task;
    }

    public void changeStatus(String status, int taskId) throws SQLException, ParseException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `task` SET status=? WHERE id=?");
        preparedStatement.setString(1, status);
        preparedStatement.setInt(2, taskId);
        preparedStatement.executeUpdate();
    }
    public void changeTaskId(int taskId, int userId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `task` SET user_id=? WHERE id=?");
        preparedStatement.setInt(2, taskId);
        preparedStatement.setInt(1, userId);
        preparedStatement.executeUpdate();
    }
}
