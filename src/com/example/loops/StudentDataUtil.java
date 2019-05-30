package com.example.loops;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.example.common.beans.Student;

@ManagedBean
@ApplicationScoped
public class StudentDataUtil {
	
	private List<Student> students;
	
	public StudentDataUtil() {
		loadSampleData();
	}
	
	public void loadSampleData() {
		
		students = new ArrayList<>();
		students.add(new Student(1, "John", "Doe", "jdoe@gmail.com"));
		students.add(new Student(2, "Jane", "Does", "jdoes@gmail.com"));
		students.add(new Student(3, "Paul", "Bar", "pbar@gmail.com"));
		 
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

}
