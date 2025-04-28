package nshubin_CSCI201_Project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AssignmentManager {
	public static void main(String[] args) {
//		create("test", 201, "4/28/2025", "11:59pm", "this is a test");
//		addToUser("bob", "test");
//		markComplete("bob", "test");
//		remove("test");
//		edit("test", 201, "4/29/2025", "11:59pm", "editted the description and date");
//		String str = search("test");
		// String str = getAllAssignments("bob");
		// System.out.println(str);		
	}
	
	static String getAllAssignments(String username) { // gets details of assignments from UA table given username
		Connection conn = null; 
		Statement st = null; 
		ResultSet rs = null; 
		try { 
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/201project?user=root&password=root"); 
			st = conn.createStatement();
			String query = "SELECT assignment.name AS name, assignment.courseID AS courseID, \r\n"
					+ "assignment.dueDate AS dueDate,\r\n"
					+ "assignment.dueTime AS dueTime, assignment.description AS description\r\n"
					+ "FROM assignment\r\n"
					+ "JOIN user_assignment ON user_assignment.assignmentName = assignment.name\r\n"
					+ "WHERE user_assignment.username = '" + username + "';";
			rs = st.executeQuery(query);
			int i = 0;
			String totalOutput = "{\n";
			while(rs.next()) {
				String name = rs.getString("name");
				String courseID = rs.getString("courseID");
				String dueDate = rs.getString("dueDate");
				String dueTime = rs.getString("dueTime");
				String description = rs.getString("description");
				String output = "{\n";
				output = output + "\"name\":" + name + ",\n";
				output = output + "\"courseID\":" + courseID + ",\n";
				output = output + "\"dueDate\":" + dueDate + ",\n";
				output = output + "\"dueTime\":" + dueTime + ",\n";
				output = output + "\"description\":" + description + "\n";
				output = output + "}";
				totalOutput = totalOutput + output + ",\n";
				i++;
			}
			totalOutput = totalOutput + "\"total_count\":" + i + "\n}";
			return totalOutput;
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) { 
					rs.close(); 
				}
				if (st != null) { 
					st.close(); 
				}
				if (conn != null) { 
					conn.close(); 
				}
			} catch (SQLException sqle) {
				System.out.println(sqle.getMessage());
			}
		}
		return ""; // blank if failed/error
	}
	
	static String search(String assignmentName) { // gets all assignment details given assignment name
		Connection conn = null; 
		Statement st = null; 
		ResultSet rs = null; 
		try { 
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/201project?user=root&password=root"); 
			st = conn.createStatement();
			String query = "SELECT courseID, dueDate, dueTime, description FROM 201project.assignment WHERE `name`= '" + assignmentName +"';";
			rs = st.executeQuery(query);
			if(rs.next()) {
				String courseID = rs.getString("courseID");
				String dueDate = rs.getString("dueDate");
				String dueTime = rs.getString("dueTime");
				String description = rs.getString("description");
				String output = "{\n";
				output = output + "\"courseID\":" + courseID + ",\n";
				output = output + "\"dueDate\":" + dueDate + ",\n";
				output = output + "\"dueTime\":" + dueTime + ",\n";
				output = output + "\"description\":" + description + "\n";
				output = output + "}";
				return output; // return json format
			}
			return "";
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) { 
					rs.close(); 
				}
				if (st != null) { 
					st.close(); 
				}
				if (conn != null) { 
					conn.close(); 
				}
			} catch (SQLException sqle) {
				System.out.println(sqle.getMessage());
			}
		}
		return ""; // blank if failed/error
	}
	
	static void edit(String assignmentName, int courseID, String dueDate, String dueTime, String description) { // edits assignment info 
		// cannot change assignment name
		Connection conn = null; 
		Statement st = null; 
		ResultSet rs = null; 
		try { 
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/201project?user=root&password=root"); 
			st = conn.createStatement();
			String query = "UPDATE 201project.assignment SET `courseID` = '" + courseID + "', `dueDate` = '" + dueDate + "', `dueTime` = '" + dueTime + "', `description` = '" + description + "' WHERE `name`= '" + assignmentName +"';";
			st.executeUpdate(query);
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) { 
					rs.close(); 
				}
				if (st != null) { 
					st.close(); 
				}
				if (conn != null) { 
					conn.close(); 
				}
			} catch (SQLException sqle) {
				System.out.println(sqle.getMessage());
			}
		}
	}
	
	
	static void remove(String assignmentName) { //removes assignment from assignment table
		Connection conn = null; 
		Statement st = null; 
		ResultSet rs = null; 
		try { 
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/201project?user=root&password=root"); 
			st = conn.createStatement();
			String query = "DELETE FROM 201project.assignment WHERE `name`= '" + assignmentName +"';";
			st.executeUpdate(query);
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) { 
					rs.close(); 
				}
				if (st != null) { 
					st.close(); 
				}
				if (conn != null) { 
					conn.close(); 
				}
			} catch (SQLException sqle) {
				System.out.println(sqle.getMessage());
			}
		}
	}
	
	static void markComplete(String username, String assignmentName) { // marks assignment as complete in UA
		Connection conn = null; 
		Statement st = null; 
		ResultSet rs = null; 
		try { 
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/201project?user=root&password=root"); 
			st = conn.createStatement();
			String query = "UPDATE 201project.user_assignment SET `status` = 1 WHERE `username`='" + username + "'AND `assignmentName`='"+assignmentName+"';"; // mark as 1 if complete
			st.executeUpdate(query);
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) { 
					rs.close(); 
				}
				if (st != null) { 
					st.close(); 
				}
				if (conn != null) { 
					conn.close(); 
				}
			} catch (SQLException sqle) {
				System.out.println(sqle.getMessage());
			}
		}
	}
	
	static void addToUser(String username, String assignmentName) { // adds assignment to user in UA
		Connection conn = null; 
		Statement st = null; 
		ResultSet rs = null; 
		try { 
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/201project?user=root&password=root"); 
			st = conn.createStatement();
			String query = "INSERT INTO 201project.user_assignment (`username`, `assignmentName`,`status`) VALUES ('" + username + "','"+assignmentName+ "','"+ 0 +"');"; // default marked not complete
			st.executeUpdate(query);
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) { 
					rs.close(); 
				}
				if (st != null) { 
					st.close(); 
				}
				if (conn != null) { 
					conn.close(); 
				}
			} catch (SQLException sqle) {
				System.out.println(sqle.getMessage());
			}
		}
	}
	
	static void create(String assignmentName, int courseID, String dueDate, String dueTime, String description) {
		Connection conn = null; 
		Statement st = null; 
		ResultSet rs = null; 
		try { 
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/201project?user=root&password=root"); 
			st = conn.createStatement();
			String query = "INSERT INTO 201project.assignment (`name`, `courseID`,`dueDate`, `dueTime`,`description`) VALUES ('" + assignmentName + "','"+courseID+ "','"+ dueDate + "','"+ dueTime + "','"+ description +"');";
			st.executeUpdate(query);
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) { 
					rs.close(); 
				}
				if (st != null) { 
					st.close(); 
				}
				if (conn != null) { 
					conn.close(); 
				}
			} catch (SQLException sqle) {
				System.out.println(sqle.getMessage());
			}
		}
	}
}
