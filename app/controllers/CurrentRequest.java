package controllers;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import java.util.List;
import static play.data.Form.form;

public class CurrentRequest extends Controller  {
  
  public static Result index() {
      
    List<models.CurrentRequest> offers = models.CurrentRequest.find().findList();
    return ok(offers.isEmpty() ? "No Requests" : offers.toString());

  }
  
  public static Result details(String studentId) {
    
    models.CurrentRequest offer = models.CurrentRequest.find().where().eq("student.studentId", studentId).findUnique();
    return (offer == null) ? notFound("No request found") : ok(offer.toString());
    
  }
 public static Result offerDetails(String studentId, String bookId) {
    
    models.CurrentRequest offer = models.CurrentRequest.find().where()
        .eq("student.studentId", studentId).eq("book.bookId", bookId).findUnique();
    return (offer == null) ? notFound("No request found") : ok(offer.toString());
    
  }
  
  public static Result newOffer() {
    
    Form<models.CurrentRequest> offerForm = form(models.CurrentRequest.class).bindFromRequest();
        
    if(offerForm.hasErrors()) {
      
      return badRequest("The offer StudetnId, BookId and Condtion name required.");
    }
   
    models.CurrentRequest offer = offerForm.get();
    if(offer.getStudent().getPrimaryKey() == 0 || offer.getBook().getPrimaryKey() == 0 ||
        offer.getCondition().getPrimaryKey() == 0 ) {
      
      return badRequest("The offer StudetnId, BookId and Condtion name required.");
    }
       
    try {
      offer.save();
    }
    catch (Exception e) {
      return badRequest("The condition name or bookId pr studenId is not valid.");
    }
    
    return ok(offer.toString());
     
    
  }
  public static Result delete(String studentId) {
    
    models.CurrentRequest request = models.CurrentRequest.find().where().eq("student.studentId", studentId).findUnique();
    if (request != null) {
      
      models.RemovedRequest removed = new   models.RemovedRequest(request);
      
      try {
        removed.save();
        request.delete();
      }
      catch (Exception e) {
        return badRequest(" Request not removed.");
      }
      
   
    }
    return ok();
  }
}
