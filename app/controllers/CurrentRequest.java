package controllers;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import java.util.List;
import static play.data.Form.form;

public class CurrentRequest extends Controller  {
  
  
  /**
   * Response for a request for all the CurrentRequest available.
   * @return Either the string with list of CurrentOffers or the string "No Requests"
   */
  public static Result index() {
      
    List<models.CurrentRequest> offers = models.CurrentRequest.find().findList();
    return ok(offers.isEmpty() ? "No Requests" : offers.toString());

  }
  /**
   * Response for a request the details CurrentRequests available.
   * @return Either the string details of an Offers or the string "No request found"
   */  
 public static Result details(String studentId, String bookId) {
    
    models.CurrentRequest request = models.CurrentRequest.find().where()
        .eq("student.studentId", studentId).eq("book.bookId", bookId).findUnique();
    return (request == null) ? notFound("No request found") : ok(request.toString());
    
  }
 /**
  * Response for a request the creation of a new request.
  * @return OK or badRequest based on whether new request created
  */
  public static Result newOffer() {
    
    Form<models.CurrentRequest> requestForm = form(models.CurrentRequest.class).bindFromRequest();
        
    if(requestForm.hasErrors()) {
      
      return badRequest("The offer StudetnId, BookId and Condtion name required.");
    }
   
    models.CurrentRequest offer = requestForm.get();
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
  /**
   * Response for a request the deletion of an request.
   * @return OK or badRequest based on whether it was deleted or not.If offer does 
   * not exist returns OK.
   * 
   */
  public static Result delete(String studentId, String bookId)  {
    
    models.CurrentRequest request = models.CurrentRequest.find().where()
        .eq("student.studentId", studentId).eq("book.bookId", bookId).findUnique();
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
