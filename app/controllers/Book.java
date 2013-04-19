package controllers;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import java.util.List;
import static play.data.Form.form;
public class Book extends Controller {
  
 public static Result index() {
    
    List<models.Book> books = models.Book.find().findList();
    return ok(books.isEmpty() ? "No Books" : books.toString());

  }
  
  public static Result details(String bookId) {
    
    models.Book book = models.Book.find().where().eq("bookId", bookId).findUnique();
    return (book == null) ? notFound("No Book found") : ok(book.toString());
    
  }
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
public static Result delete(String bookId) {
    
    models.Book book = models.Book.find().where().eq("bookId", bookId).findUnique();
    if (book != null) {
      book.delete();
    }
    return ok();
  }
}
