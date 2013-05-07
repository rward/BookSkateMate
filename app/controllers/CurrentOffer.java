package controllers;

import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.myOffers;
import java.util.List;
import models.Book;
import models.Condition;
import models.Student;
import static play.data.Form.form;
/**
 * Controller for offers that are currently active.
 * @author Robert Ward
 *
 */
public class CurrentOffer extends Controller  {
  
  
 public static Result myOffers() {
    
    List<models.CurrentOffer> offerList = models.CurrentOffer.find().findList();
    List<models.Book> bookList = models.Book.find().findList();
    List<models.Condition> conditions = models.Condition.find().findList();
    return ok(myOffers.render("",bookList,conditions,offerList,bookList.get(0).primaryKey," ",0L));
  }
 public static Result myOffers(String message, boolean returnCode, Long selectedBook,
     Double price , Long conditionIndex ) {
    
    List<models.CurrentOffer> offerList = models.CurrentOffer.find().findList();
    List<models.Book> bookList = models.Book.find().findList();
    List<models.Condition> conditions = models.Condition.find().findList();
    if (returnCode) {
      return ok(myOffers.render(message,bookList,conditions,offerList,
          selectedBook, price.toString(), conditionIndex));
    }
    else
    {
      return badRequest(myOffers.render(message,bookList,conditions,offerList,
          selectedBook, price.toString(), conditionIndex));
      
      
    }
  }
  
 /**
  * Response for a request the creation of a new request.
  * @return OK or badRequest based on whether new request created
  */
  public static Result newOffer() {
    
    DynamicForm form = form().bindFromRequest();   
    Long bid  = -1L;
    Long cid  = -1L;
       
    if(form.data().get("bookKey") != null) {
      bid  = Long.parseLong(form.data().get("bookKey"));
      
    }
    if(form.data().get("conditionKey") != null) {
      cid  = Long.parseLong(form.data().get("conditionKey"));
      
    }
    Double price = 0.0;
    if(form.data().get("price") != null) {
      price = Double.parseDouble(form.data().get("price"));
     
    }
    
    
    Book dbBook =  Book.find().byId(bid);
    Condition dbCondition =  Condition.find().byId(cid);
    Student dbStudent =  Student.find().findList().get(0);
  
    models.CurrentOffer newOffer = new models.CurrentOffer(price,dbCondition,dbBook, dbStudent);
   
    if(dbBook == null || dbStudent == null || dbStudent == null  ) {
      return myOffers("The request StudetnId, BookId and Condtion name required.",false,bid,price,cid ); 
    }
       
    try {
      newOffer.save();
    }
    catch (Exception e) {
      return myOffers("The request StudetnId, BookId and Condtion name required.",false,bid,price ,cid ); 
    }
    
    return myOffers("Offer Created.",true,bid,price,cid ); 
     
    
  }
  
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
