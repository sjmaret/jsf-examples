package com.example.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.example.common.beans.Student;

public class StudentDBUtil {
	
	 private static StudentDBUtil instance;
	 private DataSource dataSource;
	 private String jndiName = "java:comp/env/jdbc/student_tracker";
	 
	 public static StudentDBUtil getInstance() throws Exception {
		 
		 if (instance == null) {
			 instance = new StudentDBUtil();
		 }
		 
		 return instance;
	 }
	 
	 private StudentDBUtil() throws Exception {
		 
		 dataSource = getDataSource();
	 }
	 
	 private DataSource getDataSource() throws NamingException {
		 Context context = new InitialContext();
		 DataSource dataSource = (DataSource) context.lookup(jndiName);
		 return dataSource;
	 }
	 
	 public List<Student> getStudents() throws Exception {
		 List<Student> students = new ArrayList<>();
		 
		 	Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;
			
			try {
				
				conn = getConnection();
				String sql = "select * from student order by last_name;";
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				
				while (rs.next()) {
					int id = rs.getInt("id");
					String firstName = rs.getString("first_name");
					String lastName = rs.getString("last_name");
					String email = rs.getString("email");
					
					Student tempStudent = new Student(id, firstName, lastName, email);
					students.add(tempStudent);
				}
			} finally {
				close(conn, stmt,rs);
			}
			return students;
		 
	 }
	 
	 public void addStudent(Student student) throws Exception {
		 
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
		
			conn = getConnection();
			String sql = "insert into student (first_name, last_name, email) values (?, ?, ?);";
			stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, student.getFirstName());
			stmt.setString(2, student.getLastName());
			stmt.setString(3, student.getEmail());
			
			stmt.execute();
		} finally {
			close(conn, stmt);
		}	
		 
	 }

		public Student getStudent(int studentId) throws Exception {
			
			Connection myConn = null;
			PreparedStatement myStmt = null;
			ResultSet myRs = null;
			
			try {
				myConn = getConnection();

				String sql = "select * from student where id=?";

				myStmt = myConn.prepareStatement(sql);
				
				// set params
				myStmt.setInt(1, studentId);
				
				myRs = myStmt.executeQuery();

				Student theStudent = null;
				
				// retrieve data from result set row
				if (myRs.next()) {
					int id = myRs.getInt("id");
					String firstName = myRs.getString("first_name");
					String lastName = myRs.getString("last_name");
					String email = myRs.getString("email");

					theStudent = new Student(id, firstName, lastName,
							email);
				}
				else {
					throw new Exception("Could not find student id: " + studentId);
				}

				return theStudent;
			}
			finally {
				close (myConn, myStmt, myRs);
			}
		}

		public void updateStudent(Student theStudent) throws Exception {
	
		Connection myConn = null;
		PreparedStatement myStmt = null;
	
		try {
			myConn = getConnection();
	
			String sql = "update student "
						+ " set first_name=?, last_name=?, email=?"
						+ " where id=?";
	
			myStmt = myConn.prepareStatement(sql);
	
			// set params
			myStmt.setString(1, theStudent.getFirstName());
			myStmt.setString(2, theStudent.getLastName());
			myStmt.setString(3, theStudent.getEmail());
			myStmt.setInt(4, theStudent.getId());
			
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}
		
	}
	
	public void deleteStudent(int studentId) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "delete from student where id=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, studentId);
			
			myStmt.execute();
		}
		finally {
			close (myConn, myStmt);
		}		
	}	

	public List<Student> searchStudents(String searchName)  throws Exception {

		List<Student> students = new ArrayList<>();
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int studentId;
		
		try {
			
			// get connection to database
			myConn = dataSource.getConnection();
			
	        //
	        // only search by name if theSearchName is not empty
	        //
			if (searchName != null && searchName.trim().length() > 0) {

				// create sql to search for students by name
				String sql = "select * from student where lower(first_name) like ? or lower(last_name) like ?";

				// create prepared statement
				myStmt = myConn.prepareStatement(sql);

				// set params
				String searchNameLike = "%" + searchName.toLowerCase() + "%";
				myStmt.setString(1, searchNameLike);
				myStmt.setString(2, searchNameLike);
				
			} else {
				// create sql to get all students
				String sql = "select * from student order by last_name";

				// create prepared statement
				myStmt = myConn.prepareStatement(sql);
			}
	        
			// execute statement
			myRs = myStmt.executeQuery();
			
			// retrieve data from result set row
			while (myRs.next()) {
				
				// retrieve data from result set row
				int id = myRs.getInt("id");
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String email = myRs.getString("email");
				
				// create new student object
				Student tempStudent = new Student(id, firstName, lastName, email);
				
				// add it to the list of students
				students.add(tempStudent);			
			}
			
			return students;
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
	}

	private Connection getConnection() throws Exception {

		Connection conn = dataSource.getConnection();
		
		return conn;
	}
	 
	 private void close(Connection theConn, Statement theStmt) {
			close(theConn, theStmt, null);
	 }
	
	 private void close(Connection theConn, Statement theStmt, ResultSet theRs) {

		try {
			if (theRs != null) {
				theRs.close();
			}

			if (theStmt != null) {
				theStmt.close();
			}

			if (theConn != null) {
				theConn.close();
			}
			
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

}