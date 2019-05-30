package com.example.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.example.common.beans.Student;
import com.sun.istack.internal.logging.Logger;

@ManagedBean
@SessionScoped
public class StudentController {
	
	private List<Student> students;
	private StudentDBUtil studentDBUtil;
	private Logger logger = Logger.getLogger(StudentController.class);
	
	private String searchName;
	
	public StudentController() throws Exception {
		students = new ArrayList<>();
		
		studentDBUtil = StudentDBUtil.getInstance();
	}
	
	public List<Student > getStudents() {
		return students;
	}
	
	public void loadStudents() {

		logger.info("Loading students");
		
		logger.info("searchName = " + searchName);
		
		try {
			
			if (searchName != null && searchName.trim().length() > 0) {
				// search for students by name
				students = studentDBUtil.searchStudents(searchName);				
			}
			else {
				// get all students from database
				students = studentDBUtil.getStudents();
			}
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading students", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
		}
		finally {
			// reset the search info
			searchName = null;
		}
	}

	
	public String addStudent(Student theStudent) {

		logger.info("Adding student: " + theStudent);

		try {
			
			// add student to the database
			studentDBUtil.addStudent(theStudent);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error adding students", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);

			return null;
		}
		
		return "list-students?faces-redirect=true";
	}
	
	public String loadStudent(int studentId) {
		
		logger.info("loading student: " + studentId);
		
		try {
			// get student from database
			Student theStudent = studentDBUtil.getStudent(studentId);
			
			// put in the request attribute ... so we can use it on the form page
			ExternalContext externalContext = 
						FacesContext.getCurrentInstance().getExternalContext();		

			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("student", theStudent);	
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading student id:" + studentId, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
				
		return "update-student-form.xhtml";
	}	

	public String updateStudent(Student theStudent) {

		logger.info("updating student: " + theStudent);
		
		try {
			
			// update student in the database
			studentDBUtil.updateStudent(theStudent);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error updating student: " + theStudent, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
		
		return "list-students?faces-redirect=true";		
	}

	public String deleteStudent(int studentId) {

		logger.info("Deleting student id: " + studentId);
		
		try {

			// delete the student from the database
			studentDBUtil.deleteStudent(studentId);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error deleting student id: " + studentId, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
		
		return "list-students";	
	}	

	private void addErrorMessage(Exception exc) {
		
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
		
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

}
