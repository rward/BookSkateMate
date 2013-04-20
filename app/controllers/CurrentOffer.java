package controllers;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import java.util.List;
import static play.data.Form.form;
/**
 * Controller for offers that are currently active.
 * @author Robert Ward
 *
 */
public class CurrentOffer extends Controller  {
  
  /**
   * Response for a request for all the CurrentOffers available.
   * @return Either the string with list of CurrentOffers or the string "No Offers"
   */
  public static Result index() {
    
    List<models.CurrentOffer> offers = models.CurrentOffer.find().findList();
    return ok(offers.isEmpty() ? "No Offers" : offers.toString());

  }
  
  /**
   * Response for a request the details CurrentOffers available.
   * @return Either the string details of an Offers or the string "No offer found"
   */
 public static Result details(String studentId, String bookId) {
    
    models.CurrentOffer offer = models.CurrentOffer.find().where()
        .eq("student.studentId", studentId).eq("book.bookId", bookId).findUnique();
    return (offer == null) ? notFound("No offer found") : ok(offer.toString());
    
  }
 /**
  * Response for a request the creation of a new offer.
  * @return OK or badRequest based on whether new request created
  */
  public static Result newOffer() {
    
    Form<models.CurrentOffer> offerForm = form(models.CurrentOffer.class).bindFromRequest();
        
    if(offerForm.hasErrors()) {
      
      return badRequest("The offer StudetnId, BookId and Condtion name required.");
    }
   
    models.CurrentOffer offer = offerForm.get();
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
   * Response for a request the deletion of an offer.
   * @return OK or badRequest based on whether it was deleted or not if offer does 
   * not exist returns OK.
   * 
   */
  public static Result delete(String studentId, String bookId) {
    
    models.CurrentOffer offer = models.CurrentOffer.find().where().eq("student.studentId", studentId).eq("book.bookId", bookId).findUnique();
    
    if (offer != null) {
      models.RemovedOffer removed = new models.RemovedOffer(offer);
    
      try {
        removed.save();
        offer.delete();
      }
      catch (Exception e) {
        return badRequest(" Request not removed.");
      }
    }
    return ok();
  }
}
