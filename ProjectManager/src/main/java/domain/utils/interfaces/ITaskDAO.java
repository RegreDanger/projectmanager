package domain.utils.interfaces;

import java.sql.SQLException;
import java.util.List;

import domain.Task;

public interface ITaskDAO {
	    void createTask(Task task) throws SQLException;
	    List<Task> readTasks(int projectId) throws SQLException;
	    void updateTask(Task task) throws SQLException;
	    void deleteTask(int taskId) throws SQLException;
}
