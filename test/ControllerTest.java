import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.NOT_FOUND;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.callAction;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.start;
import static play.test.Helpers.stop;
import static play.test.Helpers.status; 
import java.util.HashMap;
import java.util.Map;
import models.Book;
import models.Condition;
import models.CurrentOffer;
import models.RemovedOffer;
import models.RemovedRequest;
import models.Student;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.mvc.Result;
import play.test.FakeApplication;
import play.test.FakeRequest;

public class ControllerTest {
   
  
  private FakeApplication application;
  
  
  @Before
  public void startApp() {
    application = fakeApplication(inMemoryDatabase());
    start(application);
     
  }
  
  @After
  public void stopApp() {
    
    stop(application);
  }
  @Test
  public void TestStudetnsController() {
     
    //Test Get 
    Result result = callAction(controllers.routes.ref.Student.index());
    assertTrue("Empty Students", contentAsString(result).contains("No Students"));
    
    //Test GET /students/on a database containing a single student
    String studentId = "Students-01";
    models.Student student = new Student(studentId, "firstName","lastName", "email");
    student.save();
    result = callAction(controllers.routes.ref.Student.index());
    assertTrue("One Student", contentAsString(result).contains(studentId));
    
    
    
    //Test GET /students/Students-01
    result = callAction(controllers.routes.ref.Student.details(studentId));
    assertTrue("Student detail", contentAsString(result).contains(studentId));
    
    //Test GET /student/BadStudetnsId  and make sure we get a 404
    result = callAction(controllers.routes.ref.Student.details("BadStudentId"));
    assertEquals("Student detail (bad)", NOT_FOUND ,status(result));
    
    Map<String,String> studentData = new HashMap<String, String>();
    studentData.put("StudentId", "Students-02");
    studentData.put("firstName", "firstName");
    studentData.put("lastName", "lastName");
    studentData.put("email", "email");
    FakeRequest request = fakeRequest();
    request.withFormUrlEncodedBody(studentData);
    result = callAction(controllers.routes.ref.Student.newStudent(),request);
    assertEquals("Create new Student",OK, status(result));
    
    //request duplicate addition
    request = fakeRequest();
    request.withFormUrlEncodedBody(studentData);
    result = callAction(controllers.routes.ref.Student.newStudent(),request);
    assertEquals("Create duplicate student should fail",BAD_REQUEST, status(result));
    
    // Test POST /products (with simulated, invalid form data
    request = fakeRequest();
    result = callAction(controllers.routes.ref.Student.newStudent(),request);
    assertEquals("Create bad product fails",BAD_REQUEST, status(result));
    
    
    // Test DELETE /products/Product-01(a valid ProductId)
    result = callAction(controllers.routes.ref.Student.delete(studentId));
    assertEquals("Delete current product OK",OK, status(result));
    result = callAction(controllers.routes.ref.Student.details(studentId));
    assertEquals("Delete current product OK",NOT_FOUND, status(result));
    result = callAction(controllers.routes.ref.Student.delete(studentId));
    assertEquals("Delete current product OK",OK, status(result));
    
    
  }
  @Test
  public void TestBookController() {
  //Test Get 
  Result result = callAction(controllers.routes.ref.Book.index());
  assertTrue("Empty Students", contentAsString(result).contains("No Books"));
  
  //Test GET /product/on a database containing a single product
  String bookId = "Book-01";
  models.Book book = new Book(bookId, "title","author","isbn", 20,22.20);
  book.save();
  result = callAction(controllers.routes.ref.Book.index());
  assertTrue("One Book", contentAsString(result).contains(bookId));

  //Test GET /product/Product-01
  result = callAction(controllers.routes.ref.Book.details(bookId));
  assertTrue("Book detail", contentAsString(result).contains(bookId));
  
  //Test GET /product/BadProductId  and make sure we get a 404
  result = callAction(controllers.routes.ref.Book.details("BadBookId"));
  assertEquals("Book detail (bad)", NOT_FOUND ,status(result));
  
  Map<String,String> bookData = new HashMap<String, String>();
  bookData.put("bookId", "Book-02");
  bookData.put("title", "title");
  bookData.put("author", "author");
  bookData.put("isbn", "isbn");
  bookData.put("edition", "2");
  bookData.put("bookStorePrice", "22.00");
  bookData.put("password", "!Fred3");
  FakeRequest request = fakeRequest();
  request.withFormUrlEncodedBody(bookData);
  result = callAction(controllers.routes.ref.Book.newBook(),request);
  assertEquals("Create new book via Contoller",OK, status(result));
  
  //request duplicate addition
  request = fakeRequest();
  request.withFormUrlEncodedBody(bookData);
  result = callAction(controllers.routes.ref.Book.newBook(),request);
  assertEquals("Create duplicate book should fail",BAD_REQUEST, status(result));
  
  // Test POST /products (with simulated, invalid form data
  request = fakeRequest();
  result = callAction(controllers.routes.ref.Book.newBook(),request);
  assertEquals("Create bad book fails",BAD_REQUEST, status(result));
  
   
  // Test DELETE /products/Product-01(a valid ProductId)
  result = callAction(controllers.routes.ref.Book.delete(bookId));
  assertEquals("Delete current product OK",OK, status(result));
  result = callAction(controllers.routes.ref.Book.details(bookId));
  assertEquals("Delete current product OK",NOT_FOUND, status(result));
  result = callAction(controllers.routes.ref.Book.delete(bookId));
  assertEquals("Delete current product OK",OK, status(result));
}
  @Test
  public void TestCondtionController() {
  //Test Get 
  Result result = callAction(controllers.routes.ref.Condition.index());
  assertTrue("Empty Condtions", contentAsString(result).contains("No Conditions"));
  
  //Test GET /product/on a database containing a single product
  String name = "Condition-01";
    
  models.Condition condition = new Condition(name);
  condition.save();
  
  result = callAction(controllers.routes.ref.Condition.index());
  assertTrue("One Condtion", contentAsString(result).contains(name));

 
  
  Map<String,String> condtionData = new HashMap<String, String>();
  condtionData.put("name", "Condition-02");
  condtionData.put("password", "!Fred3");
  FakeRequest request = fakeRequest(); 
  request.withFormUrlEncodedBody(condtionData);
  result = callAction(controllers.routes.ref.Condition.newCondition(),request);
  assertEquals("Create new Condition",OK, status(result));
  
  //request duplicate addition
  request = fakeRequest();
  request.withFormUrlEncodedBody(condtionData);
  result = callAction(controllers.routes.ref.Condition.newCondition(),request);
  assertEquals("Create duplicate Condition should fail",BAD_REQUEST, status(result));
  
  // Test POST /products (with simulated, invalid form data
  request = fakeRequest();
  result = callAction(controllers.routes.ref.Condition.newCondition(),request);
  assertEquals("Create bad new Condition fails",BAD_REQUEST, status(result));
  
   
  // Test DELETE /products/Product-01(a valid ProductId)
  result = callAction(controllers.routes.ref.Condition.  delete(name));
  assertEquals("Delete current product OK",OK, status(result));
  result = callAction(controllers.routes.ref.Condition.index());
  assertFalse("Name deleted of condition", contentAsString(result).contains(name));
    
  result = callAction(controllers.routes.ref.Condition.delete(name));
  assertEquals("Delete current product OK",OK, status(result));
}  
  
  
  
  @Test
  public void TestOfferController() {
  //Test Get 
    Result result = callAction(controllers.routes.ref.CurrentOffer.index());
    assertTrue("Empty Offers", contentAsString(result).contains("No Offers"));
    
    //Create a book to use in the offer
    String bookId = "Book-01";
    models.Book book = new Book(bookId, "title","author","isbn", 20,22.20);
    book.save();
    
    //Create a book to use in the offer
    String studentId = "Students-01";
    models.Student student = new Student(studentId, "firstName","lastName", "email");
    student.save();
    
    //Create a book to use in the offer
    String name = "Condition-01";
    models.Condition condition = new Condition(name);
    condition.save();
    
    models.CurrentOffer offer = new CurrentOffer( 22.20 ,  condition,  book,  student);
    offer.save();
        
    result = callAction(controllers.routes.ref.CurrentOffer.index());
    assertTrue("One Offer", contentAsString(result).contains(bookId));

    
    result = callAction(controllers.routes.ref.CurrentOffer.details(studentId,bookId));
    assertTrue("Offer detail", contentAsString(result).contains(bookId));
     
    //Test GET /offer/Student-01,Book-01 
    result = callAction(controllers.routes.ref.CurrentOffer.details(studentId,bookId));
    assertTrue("Offer detail", contentAsString(result).contains(bookId));
    
    
    //Test GET /product/BadBookId  and make sure we get a 404
    result = callAction(controllers.routes.ref.CurrentOffer.details("BadStudentId","BadBookId"));
    assertEquals("Offer detail (bad)", NOT_FOUND ,status(result));
    
    //Create a book to use in the offer
    String bookId2 = "Book-02";
    models.Book book2 = new Book(bookId2, "title","author","isbn", 20,22.20);
    book2.save();
    
    Map<String,String> offerData = new HashMap<String, String>();
    offerData.put("book.primaryKey", "2");
    offerData.put("condition.primaryKey", "1");
    offerData.put("bookKey", "2");
    offerData.put("conditionKey", "1");
    offerData.put("student.primaryKey", "1");
    offerData.put("bookId", "Book-02");
    offerData.put("studentId", "Students-01");
    offerData.put("conditionName", "Condition-01");
    offerData.put("price", "22.00");
    
    FakeRequest request = fakeRequest();
    request.withFormUrlEncodedBody(offerData);
    result = callAction(controllers.routes.ref.CurrentOffer.newOffer(),request);
        
    assertEquals("Create new Offer Test",OK, status(result));
    
         
    // Test POST /products (with simulated, invalid form data
    request = fakeRequest();
    result = callAction(controllers.routes.ref.CurrentOffer.newOffer(),request);
    assertEquals("Create bad offer fails",BAD_REQUEST, status(result));
    
     
    // Test DELETE /products/Product-01(a valid ProductId)
    result = callAction(controllers.routes.ref.CurrentOffer.delete(studentId,bookId));
    assertEquals("Delete current product OK",OK, status(result));
    
    RemovedOffer dbOffer =  RemovedOffer.find().findList().get(0);
    assertEquals("Removed Request Price",22.20,dbOffer.getPrice(),.1);
    assertEquals("Removed Request Book Id",1,dbOffer.getBook().getPrimaryKey());
    assertTrue("Removed Request Condition Name",
        "Condition-01".equals(dbOffer.getCondition().getName()));
    
    
    result = callAction(controllers.routes.ref.CurrentOffer.details(studentId,bookId));
    assertEquals("Delete current product OK",NOT_FOUND, status(result));
    result = callAction(controllers.routes.ref.CurrentOffer.delete(studentId,bookId));
    assertEquals("Delete current product OK",OK, status(result));
    
    
  }
  @Test
  public void TestRequestController() {
  //Test Get  
    Result result = callAction(controllers.routes.ref.CurrentRequest.index());
    assertTrue("Empty Requests", contentAsString(result).contains("No Requests"));
    
    //Test GET /product/on a database containing a single product
    String bookId = "Book-01";
    models.Book book = new Book(bookId, "title","author","isbn", 20,22.20);
    book.save();
    
    //Test GET /product/on a database containing a single product
    String studentId = "Students-01";
    models.Student student = new Student(studentId, "firstName","lastName", "email");
    student.save();
    
    //Test GET /product/on a database containing a single product
    String name = "Condition-01";
    models.Condition condition = new Condition(name);
    condition.save();
    
    models.CurrentRequest currentRequest = new models.CurrentRequest
                                           ( 22.20 ,  condition,  book,  student);
    currentRequest.save();
        
    result = callAction(controllers.routes.ref.CurrentRequest.index());
    assertTrue("One Offer", contentAsString(result).contains(bookId));

    //Test GET /product/Student-01
    result = callAction(controllers.routes.ref.CurrentRequest.details(studentId,bookId));
    assertTrue("Offer detail", contentAsString(result).contains(bookId));
     
    //Test GET /offer/Student-01,Book-01 
    result = callAction(controllers.routes.ref.CurrentRequest.details(studentId,bookId));
    assertTrue("Offer detail", contentAsString(result).contains(bookId));
    
    
    //Test GET /product/BadBookId  and make sure we get a 404
    result = callAction(controllers.routes.ref.CurrentRequest.details("BadStudentID","BadBookId"));
    assertEquals("Offer detail (bad)", NOT_FOUND ,status(result));
    
  //Test GET /product/on a database containing a single product
    String bookId2 = "Book-02";
    models.Book book2 = new Book(bookId2, "title","author","isbn", 20,22.20);
    book2.save();
    
    Map<String,String> requestData = new HashMap<String, String>();
    requestData.put("bookKey", "2");
    requestData.put("conditionKey", "1");
    requestData.put("book.primaryKey", "2");
    requestData.put("condition.primaryKey", "1");
    requestData.put("student.primaryKey", "1");
    requestData.put("bookId", "Book-02");
    requestData.put("studentId", "Students-01");
    requestData.put("conditionName", "Condition-01");
    requestData.put("price", "22.00");
     
    FakeRequest request = fakeRequest();
    request.withFormUrlEncodedBody(requestData);
    result = callAction(controllers.routes.ref.CurrentRequest.newRequest(),request);
        
    assertEquals("Create new Request",OK, status(result));
    
        
    // Test POST /requests (with simulated, invalid form data
    request = fakeRequest();
    result = callAction(controllers.routes.ref.CurrentRequest.newRequest(),request);
    assertEquals("Create bad offer fails",BAD_REQUEST, status(result));
    
     
    // Test DELETE 
    result = callAction(controllers.routes.ref.CurrentRequest.delete(studentId,bookId));
    assertEquals("Delete current product OK",OK, status(result));
    
    //check if request was moved to removed requests
    RemovedRequest dbrequest =  RemovedRequest.find().findList().get(0);
    assertEquals("Removed Request Price",22.20,dbrequest.getPrice(),.1);
    assertEquals("Removed Request Book Id",1,dbrequest.getBook().getPrimaryKey());
    assertTrue("Removed Request Condition Name",
        "Condition-01".equals(dbrequest.getCondition().getName()));
    
    
    result = callAction(controllers.routes.ref.CurrentRequest.details(studentId,bookId));
    assertEquals("Delete current request OK",NOT_FOUND, status(result));
    result = callAction(controllers.routes.ref.CurrentRequest.delete(studentId,bookId));
    assertEquals("Delete current request OK",OK, status(result));
    
    
  }

}
