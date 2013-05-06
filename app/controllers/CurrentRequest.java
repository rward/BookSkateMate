package controllers;

import play.data.DynamicForm;
import play.data.Form;
import play.db.ebean.Model;
import play.mvc.Controller;
import play.mvc.Result;
import java.util.List;
import models.Book;
import models.Condition;
import models.Student;
import static play.data.Form.form;
import views.html.myRequests;



public class CurrentRequest extends Controller  {
  
  
  public static Result myRequests() {
    
    List<models.CurrentRequest> requestList = models.CurrentRequest.find().findList();
    List<models.Book> bookList = models.Book.find().findList();
    List<models.Condition> conditions = models.Condition.find().findList();
    return ok(myRequests.render("",bookList,conditions,requestList,bookList.get(0).primaryKey," ",0L));
  }
 public static Result myRequests(String message, boolean returnCode, Long selectedBook,
     Double price , Long conditionIndex ) {
    
    List<models.CurrentRequest> requestList = models.CurrentRequest.find().findList();
    List<models.Book> bookList = models.Book.find().findList();
    List<models.Condition> conditions = models.Condition.find().findList();
    if (returnCode) {
      return ok(myRequests.render(message,bookList,conditions,requestList,
          selectedBook, price.toString(), conditionIndex));
    }
    else
    {
      return badRequest(myRequests.render(message,bookList,conditions,requestList,
          selectedBook, price.toString(), conditionIndex));
      
      
    }
  }
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
  public static Result newRequest() {
    
    DynamicForm form = form().bindFromRequest();  
    
    Long bid  = 0L;
    Long cid  = 0L;
    if(form.data().get("bookKey") != null)
      bid  = Long.parseLong(form.data().get("bookKey"));
    if(form.data().get("conditionKey") != null)
      cid  = Long.parseLong(form.data().get("conditionKey"));
    double price = 0;
    if(form.data().get("price") != null)
      price = Double.parseDouble(form.data().get("price"));
    
    Book dbBook =  Book.find().byId(bid);
    Condition dbCondition =  Condition.find().byId(cid);
    Student dbStudent =  Student.find().findList().get(0);
    
    
    models.CurrentRequest newRequest = new models.CurrentRequest(price,dbCondition,dbBook, dbStudent);
   
    if(dbBook == null || dbStudent == null || dbStudent == null  ) {
      return myRequests("The request StudetnId, BookId and Condtion name required.",false,bid,price,cid ); 
    }
       
    try {
      newRequest.save();
    }
    catch (Exception e) {
      return myRequests("The request StudetnId, BookId and Condtion name required.",false,bid,price ,cid ); 
    }
   
    return myRequests("Request Created.",true,bid,price,cid ); 
     
    
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
