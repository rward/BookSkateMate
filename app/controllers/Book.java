package controllers;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import java.util.List;
import static play.data.Form.form;

/**
 * Provides basic information about a book.
 * Provides access to the current offers and request. 
 * @author Robert Ward
 *
 */

public class Book extends Controller {
  /**
   * Response for a request for all the books available
   * @return Either the string with list of books or the string "No books"
   */
 public static Result index() {
    
    List<models.Book> books = models.Book.find().findList();
    return ok(books.isEmpty() ? "No Books" : books.toString());

  }
 /**
  * Response for a request for all the offers for a specific book.
  * @param bookId the unique idea for this book
  * @return list of of offers currently active for the specified book
  */
 public static Result getCurrentOffers(String bookId) {
   
   models.Book book = models.Book.find().where().eq("bookId", bookId).findUnique();
   if(book ==null) {
     return badRequest("The Book ID is not valid.");
   }
   
   return ok(book.getOffers().toString());

 } 
 /**
  * Response for a request for all the offers for a specific book.
  * @param bookId the unique idea for this book
  * @return list of of offers currently active for the specified book
  */
public static Result getCurrentRequests(String bookId) {
   
   models.Book book = models.Book.find().where().eq("bookId", bookId).findUnique();
   if(book ==null) {
     return badRequest("The Book ID is not valid.");
   }
   
   return ok(book.getRequests().toString());

 } 
/**
 * Response for a request for all the offers for a specific book.
 * @param bookId the unique idea for this book
 * @return list of of offers currently active for the specified book
 */
public static Result getRemovedOffers(String bookId) {
  
  models.Book book = models.Book.find().where().eq("bookId", bookId).findUnique();
  if(book ==null) {
    return badRequest("The Book ID is not valid.");
  }
  
  return ok(book.getRemovedOffers().toString());

} 
/**
 * Response for a request for all the offers for a specific book.
 * @param bookId the unique idea for this book
 * @return list of of offers currently active for the specified book
 */
public static Result getRemovedRequests(String bookId) {
  
  models.Book book = models.Book.find().where().eq("bookId", bookId).findUnique();
  if(book ==null) {
    return badRequest("The Book ID is not valid.");
  }
  
  return ok(book.getRemovedRequests().toString());

} 
/**
 * Response for a request for details about a specific book.
 * @param bookId the unique idea for this book
 * @return result that includes information about a specific book for the specified book
 */
  public static Result details(String bookId) {
    
    models.Book book = models.Book.find().where().eq("bookId", bookId).findUnique();
    return (book == null) ? notFound("No Book found") : ok(book.toString());
    
  }
  /**
   * Response for a request for creation of a new book.
   * @param bookId the unique idea for this book
   * @return result that indicates if book was created.
   */
  public static Result newBook() {
    
    Form<models.Book> bookForm = form(models.Book.class).bindFromRequest();
    if(bookForm.hasErrors()) {
      
      return badRequest("The Book ID and name is required.");
    }
    models.Book book = bookForm.get();
    try {
      book.save();
    }
    catch (Exception e) {
      return badRequest("The Book ID or name is not valid.");
    }
    
    return ok(book.toString());
     
    
  }
  
  /**
   * Response for a request for creation of a new book.
   * @param bookId
   * @return result that indicates if book was created.
   */
  public static Result delete(String bookId) {
    
    models.Book book = models.Book.find().where().eq("bookId", bookId).findUnique();
    if (book != null) {
      try {
        book.delete();
      }
      catch (Exception e) {
        
        return badRequest(" Book ID not deleted.");
      }
    }
    return ok();
  }
}
