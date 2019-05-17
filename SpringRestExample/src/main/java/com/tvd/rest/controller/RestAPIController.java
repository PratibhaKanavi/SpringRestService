package com.tvd.rest.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tvd.rest.dto.StudentData;

@RestController
public class RestAPIController {
	
	@Autowired
	private SessionFactory sf;

	@RequestMapping("/home")
	public String homepage() {
		return "home page getting loaded......";
		
	}
	
	@GetMapping(value="/getallStudent")
	public List<StudentData> getAll(){
		
		return sf.openSession().createQuery("from StudentData",StudentData.class).getResultList();
		
	}
	
	@PostMapping(value="/saveStudent",consumes=MediaType.APPLICATION_JSON)
	@Transactional
	public void Save(@RequestBody StudentData s) {
		
		  Session ses = sf.openSession();
		  ses.beginTransaction(); 
		  ses.save(s);
		  ses.getTransaction().commit(); 
		  ses.close();
		 
		System.out.println("save data successfully");
		
	}
	
	 @GetMapping("/getStudent/{id}")
		public StudentData getStudent(@PathVariable("id") int id) {
		 
		 return sf.openSession().get(StudentData.class, id);
		}
	
	 @DeleteMapping("/deleteStudent/{id}")
		public void deleteStudent(@PathVariable("id") int id) {
		 System.out.println(id);
		 Session session = sf.openSession();
		  session.beginTransaction();
		  StudentData student = (StudentData)session.load(StudentData.class, id);
		  session.delete(student);
		  System.out.println("Deleted Successfully");
		  session.getTransaction().commit();
		     sf.close();
		 
		// sf.openSession().delete(sf.openSession().get(StudentData.class, id));
		}
		
}
