import static org.junit.Assert.assertEquals;
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
    
    //Test GET /product/on a database containing a single product
    String studentId = "Students-01";
    models.Student student = new Student(studentId, "firstName","lastName", "email");
    student.save();
    result = callAction(controllers.routes.ref.Student.index());
    assertTrue("One Student", contentAsString(result).contains(studentId));
    
    
    
    //Test GET /product/Product-01
    result = callAction(controllers.routes.ref.Student.details(studentId));
    assertTrue("Student detail", contentAsString(result).contains(studentId));
    
    //Test GET /product/BadProductId  and make sure we get a 404
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
    assertEquals("Create new product",OK, status(result));
    
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
  bookData.put("bookId", "Students-02");
  bookData.put("title", "title");
  bookData.put("author", "author");
  bookData.put("isbn", "isbn");
  bookData.put("edition", "2");
  bookData.put("bookStorePrice", "22.00");
  
  FakeRequest request = fakeRequest();
  request.withFormUrlEncodedBody(bookData);
  result = callAction(controllers.routes.ref.Book.newBook(),request);
  assertEquals("Create new book",OK, status(result));
  
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
//  @Test
//  public void TestTagController() {
//    
//    Result result = callAction(controllers.routes.ref.Tag.index());
//    assertTrue("Empty Tag", contentAsString(result).contains("No Tags"));
//    
//    //Test GET /product/on a database containing a single product
//    String tagId = "Tag-01";
//    Tag tag = new Tag(tagId);
//    tag.save();
//    result = callAction(controllers.routes.ref.Tag.index());
//    assertTrue("One Tag", contentAsString(result).contains(tagId));
//    
//    //Test GET /product/Product-01
//    result = callAction(controllers.routes.ref.Tag.details(tagId));
//    assertTrue("tag detail", contentAsString(result).contains(tagId));
//    
//    //Test GET /product/BadProductId  and make sure we get a 404
//    result = callAction(controllers.routes.ref.Tag.details("BadTagId"));
//    assertEquals("Tag detail (bad)", NOT_FOUND ,status(result));
//    
//    Map<String,String> tagData = new HashMap<String, String>();
//    tagData.put("tagId", "Tag-02");
//        
//    FakeRequest request = fakeRequest();
//    request.withFormUrlEncodedBody(tagData);
//    result = callAction(controllers.routes.ref.Tag.newTag(),request);
//    assertEquals("Create new tag",OK, status(result));
//    
//    //request duplicate addition
//    //request = fakeRequest();
//    //request.withFormUrlEncodedBody(productData);
//    //result = callAction(controllers.routes.ref.Product.newProduct(),request);
//    //assertEquals("Create duplicate product fails",BAD_REQUEST, status(result));
//    
//    // Test POST /products (with simulated, invalid form data
//    request = fakeRequest();
//    result = callAction(controllers.routes.ref.Tag.newTag(),request);
//    assertEquals("Create bad product fails",BAD_REQUEST, status(result));
//    
//    
//    // Test DELETE /products/Product-01(a valid ProductId)
//    result = callAction(controllers.routes.ref.Tag.delete(tagId));
//    assertEquals("Delete current product OK",OK, status(result));
//    result = callAction(controllers.routes.ref.Tag.details(tagId));
//    assertEquals("Delete current product OK",NOT_FOUND, status(result));
//    result = callAction(controllers.routes.ref.Tag.delete(tagId));
//    assertEquals("Delete current product OK",OK, status(result));
//  
//  }
//  
//  @Test
//  public void TestAddessController() {
//    
//    Result result = callAction(controllers.routes.ref.Address.index());
//    assertTrue("Empty Address", contentAsString(result).contains("No Addresses"));
//  }
//  @Test
//  public void TestWarehouseController() {
//    
//    Result result = callAction(controllers.routes.ref.Warehouse.index());
//    assertTrue("Empty Warehouses", contentAsString(result).contains("No Warehouses"));
//    
//    //Test GET /product/on a database containing a single product
//    String warehouseId = "Warehouse-01";
//    Warehouse warehouse = new Warehouse(warehouseId,"warehouseName");
//    warehouse.save();
//    result = callAction(controllers.routes.ref.Warehouse.index());
//    
//    
//    assertTrue("One Warehouse", contentAsString(result).contains(warehouseId));
//    
//    //Test GET /product/Product-01
//    result = callAction(controllers.routes.ref.Warehouse.details(warehouseId));
//    assertTrue("warehouse detail", contentAsString(result).contains(warehouseId));
//    
//    //Test GET /product/BadProductId  and make sure we get a 404
//    result = callAction(controllers.routes.ref.Warehouse.details("BadWarehouseId"));
//    assertEquals("warehouse detail (bad)", NOT_FOUND ,status(result));
//    
//    Map<String,String> warehouseData = new HashMap<String, String>();
//    warehouseData.put("warehouseId", "Warehouse-02");
//    warehouseData.put("name", "warehouseName");   
//    FakeRequest request = fakeRequest();
//    request.withFormUrlEncodedBody(warehouseData);
//    result = callAction(controllers.routes.ref.Warehouse.newWarehouse(),request);
//    assertEquals("Create new tag",OK, status(result));
//    
//    //request duplicate addition
//    //request = fakeRequest();
//    //request.withFormUrlEncodedBody(productData);
//    //result = callAction(controllers.routes.ref.Product.newProduct(),request);
//    //assertEquals("Create duplicate product fails",BAD_REQUEST, status(result));
//    
//    // Test POST /products (with simulated, invalid form data
//    request = fakeRequest();
//    result = callAction(controllers.routes.ref.Warehouse.newWarehouse(),request);
//    assertEquals("Create bad product fails",BAD_REQUEST, status(result));
//    
//    
//    // Test DELETE /products/Product-01(a valid ProductId)
//    result = callAction(controllers.routes.ref.Warehouse.delete(warehouseId));
//    assertEquals("Delete current product OK",OK, status(result));
//    result = callAction(controllers.routes.ref.Warehouse.details(warehouseId));
//    assertEquals("Delete current product OK",NOT_FOUND, status(result));
//    result = callAction(controllers.routes.ref.Warehouse.delete(warehouseId));
//    assertEquals("Delete current product OK",OK, status(result));
//    
//  }
}
