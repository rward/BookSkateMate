package controllers;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import java.util.List;
import static play.data.Form.form;

public class Student extends Controller {
  
  public static Result index() {
    
    List<models.Student> students = models.Student.find().findList();
    return ok(students.isEmpty() ? "No Students" : students.toString());

  }
  
  public static Result details(String studentId) {
    
    models.Student student = models.Student.find().where().eq("studentId", studentId).findUnique();
    return (student == null) ? notFound("No Student found") : ok(student.toString());
    
  }
  public static Result newStudent() {
    
    Form<models.Student> productForm = form(models.Student.class).bindFromRequest();
    if(productForm.hasErrors()) {
      
      return badRequest("The Student ID and name is required.");
    }
    models.Student student = productForm.get();
    try {
      student.save();
    }
    catch (Exception e) {
      return badRequest("The Student ID or name is not valid.");
    }
    
    return ok(student.toString());
        
  }
public static Result delete(String studentId) {
    
    models.Student student = models.Student.find().where().eq("studentId", studentId).findUnique();
    if (student != null) {
      student.delete();
    }
    return ok();
  }

}
