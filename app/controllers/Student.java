package controllers;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import java.util.List;
import static play.data.Form.form;

public class Student extends Controller {
  
  /**
   * Response for a request for all the students.
   * @return Either the string with list of students or the string "No Students"
   */
  public static Result index() {
    
    List<models.Student> students = models.Student.find().findList();
    return ok(students.isEmpty() ? "No Students" : students.toString());

  }
  /**
   * Response for a request for all the Offers students.
   * @return Either the string with list of offers or the string "The student ID is not valid"
   */
  public static Result getOffers(String studentId) {
    
    models.Student student = models.Student.find().where().eq("bookId", studentId).findUnique();
    if(student ==null) {
      return badRequest("The student ID is not valid.");
    }
    
    return ok(student.offers.toString());

  } 
  /**
   * Response for a request for all the current requests of a student.
   * @return Either the string with list of offers or the string "The student ID is not valid"
   */
 public static Result getRequests(String bookId) {
    
    models.Student student = models.Student.find().where().eq("bookId", bookId).findUnique();
    if(student ==null) {
      return badRequest("The Book ID is not valid.");
    }
    
    return ok(student.requests.toString());

  } 
 /**
  * Response for a request for the details of a student.
  * @return Either the string with list of offers or the string "No Student found"
  */
  public static Result details(String studentId) {
    
    models.Student student = models.Student.find().where().eq("studentId", studentId).findUnique();
    return (student == null) ? notFound("No Student found") : ok(student.toString());
    
  }
  /**
   * Response for a request to create a new a student.
   * @return Either OK for creation or badRequest if invalid parameters
   */
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
  /**
   * Response for a request to delete a student.
   * @return Either OK for creation or badRequest if invalid parameters
   */
  public static Result delete(String studentId) {
    
    models.Student student = models.Student.find().where().eq("studentId", studentId).findUnique();
    if (student != null) {
      try {
        student.delete();
      }
      catch (Exception e) {
        
        return badRequest(" Student ID not deleted.");
      }
     
    }
    return ok();
  }

}
