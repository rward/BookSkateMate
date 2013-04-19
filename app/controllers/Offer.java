package controllers;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import java.util.List;
import static play.data.Form.form;

public class Offer extends Controller  {
  
  public static Result index() {
    
    List<models.CurrentOffer> offers = models.CurrentOffer.find().findList();
    return ok(offers.isEmpty() ? "No Offers" : offers.toString());

  }
  
  public static Result details(String studentId) {
    
    models.CurrentOffer offer = models.CurrentOffer.find().where().eq("student.studentId", studentId).findUnique();
    return (offer == null) ? notFound("No offer found") : ok(offer.toString());
    
  }
 public static Result offerDetails(String studentId, String bookId) {
    
    models.CurrentOffer offer = models.CurrentOffer.find().where()
        .eq("student.studentId", studentId).eq("book.bookId", bookId).findUnique();
    return (offer == null) ? notFound("No offer found") : ok(offer.toString());
    
  }
  
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
  public static Result delete(String studentId) {
    
    models.CurrentOffer offer = models.CurrentOffer.find().where().eq("student.studentId", studentId).findUnique();
    
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
