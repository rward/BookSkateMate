import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import models.Book;
import models.Condition;
import models.CurrentRequest;
import models.CurrentOffer;
import models.Student;
import play.test.FakeApplication;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.start;
import static play.test.Helpers.stop;


import java.util.List;

public class ModelTest {

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
    public void testAddNewItems() {
     
      Book book = new Book("BookId01", "Title", "Author","ISBN",1 , 22.00);
      book.save();
          
      Student student = new Student("StudentID01", "First","Last","Email");
      student.save();
      
    
     
     Condition conditionNew = new Condition("New");
     conditionNew.save();
     Condition conditionUsed = new Condition("Used");
     conditionUsed.save(); 
     
          
  
     conditionNew = Condition.find().findList().get(0);
     assertTrue( "Condition name not New ",  "New".equals(conditionNew.getName())  );
    
     
     conditionUsed = Condition.find().findList().get(1);
    
     
     assertTrue( "Condition name not Used ",  "Used".equals(conditionUsed.getName())  );
   
         
     CurrentOffer offer = new CurrentOffer(100,conditionNew,book,student);
     
     conditionNew.addOffer(offer);
     student.addOffer(offer);
     book.addOffer(offer);
          
      assertEquals("Added 1 offer to condition",  1, conditionNew.getOffers().size());
      assertEquals("Added 1 offer to student",  1, student.getOffers().size());
      assertEquals("Added 1 offer to book",  1, book.getOffers().size());
      
      CurrentRequest request = new CurrentRequest(100, conditionUsed,book,student );
            
      conditionUsed.addRequest(request); 
      student.addRequest(request);
      book.addRequest(request);
      
      assertEquals("Added 1 offer to condition",  1, conditionUsed.getRequests().size());
      assertEquals("Added 1 offer to student",  1, student.getRequests().size());
      assertEquals("Added 1 offer to book",  1, book.getRequests().size());
      
      //Get Book from database 
      Book dbBook =  Book.find().findList().get(0);
      assertTrue( "Book Title",  "Title".equals(dbBook.getTitle())  );
      assertTrue("Book Author", "Author".equals(dbBook.getAuthor() )  );
      assertTrue("Book ISBN", "ISBN".equals(dbBook.getIsbn()));
     
      Book dbBook2 =  Book.find().byId(1L);
      
      assertTrue( "Book Title",  "Title".equals(dbBook2.getTitle())  );
      assertTrue("Book Author", "Author".equals(dbBook2.getAuthor() )  );
      assertTrue("Book ISBN", "ISBN".equals(dbBook2.getIsbn()));
      
      
    //Get Book from database 
      Student dbStudent =  Student.find().findList().get(0);
      assertEquals("Student First","First", dbStudent.getFirstName() );
      assertEquals("Student Last","Last", dbStudent.getLastName());
      assertEquals("Student Email","Email", dbStudent.getEmail());
     
     
      
      
      //Get offer from database 
      CurrentOffer dbOffer =  CurrentOffer.find().findList().get(0);
      assertEquals("Offer Price",100,dbOffer.getPrice(),.1);
      assertEquals("Offer Book Id",1,dbOffer.getBook().getPrimaryKey());
      assertTrue("Offer Condition Name",
          "New".equals(dbOffer.getCondition().getName()));
      
      
     //Get request from database 
      CurrentRequest dbRequest =  CurrentRequest.find().findList().get(0);
      assertEquals("Request Price",100,dbRequest.getPrice() ,.1);
      assertEquals("Request Book Id",1,dbRequest.getBook().getPrimaryKey());
      assertTrue("Request Condition Name not Used",
          "Used".equals(dbRequest.getCondition().getName()));
      //retrieve the entire model from the database
      
    }
    
    
    @Test
    public void testModel() {
      
     
      
      // create 1 tag warehouse that's associated with 1 StockItem for 1 product
      Book book = new Book("BookId01", "Title", "Author","ISBN",1 , 22.00);
      book.save();
     
     
      // Create 1 Warehouse that's associated with 1 StockItem for 1 product
      Student student = new Student("StudentID01","First","Last","Email");
      student.save();
      
    
     
     Condition conditionNew = new Condition("New");;
        
     conditionNew.save();
     
     Condition conditionUsed = new Condition("Used");
     conditionUsed.save();
          
     
     conditionNew = Condition.find().findList().get(0);
   
     
     assertTrue( "Condition name not New ",  "New".equals(conditionNew.getName())  );
   
     
     conditionUsed = Condition.find().findList().get(1);
    
     
     assertTrue( "Condition name not Used ",  "Used".equals(conditionUsed.getName())  );
     
      
    
      //Offer offer = new Offer(100,conditionNew);
     CurrentOffer offer = new CurrentOffer(100,conditionNew,book,student);
     
     conditionNew.addOffer(offer);
     student.addOffer(offer);
     book.addOffer(offer);
        
      
           
      assertEquals("Added 1 offer to condition",  1, conditionNew.getOffers().size());
      assertEquals("Added 1 offer to student",  1, student.getOffers().size());
      assertEquals("Added 1 offer to book",  1, book.getOffers().size());
      
      CurrentRequest request = new CurrentRequest(100, conditionUsed,book,student);
            
      conditionUsed.addRequest(request);
      student.addRequest(request);
      book.addRequest(request);
      
      assertEquals("Added 1 offer to condition",  1, conditionUsed.getRequests().size());
      assertEquals("Added 1 offer to student",  1, student.getRequests().size());
      assertEquals("Added 1 offer to book",  1, book.getRequests().size());
      
      //Get Book from database 
      Book dbBook =  Book.find().findList().get(0);
      assertTrue( "Book Title",  "Title".equals(dbBook.getTitle())  );
      assertTrue("Book Author", "Author".equals(dbBook.getAuthor() )  );
      assertTrue("Book ISBN", "ISBN".equals(dbBook.getIsbn()));
     
      Book dbBook2 =  Book.find().byId(1L);
      
      assertTrue( "Book Title",  "Title".equals(dbBook2.getTitle())  );
      assertTrue("Book Author", "Author".equals(dbBook2.getAuthor() )  );
      assertTrue("Book ISBN", "ISBN".equals(dbBook2.getIsbn() ));
      
      
    //Get Book from database 
      Student dbStudent =  Student.find().findList().get(0);
      assertEquals("Student First","First", dbStudent.getFirstName() );
      assertEquals("Student Last","Last", dbStudent.getLastName());
      assertEquals("Student Email","Email", dbStudent.getEmail());
     
        
       
      
      //Get offer from database 
      CurrentOffer dbOffer =  CurrentOffer.find().findList().get(0);
      assertEquals("Offer Price",100,dbOffer.getPrice(),.1);
      assertEquals("Offer Book Id",1,dbOffer.getBook().getPrimaryKey());
      assertTrue("Offer Condition Name",
          "New".equals(dbOffer.getCondition().getName()));
      
      
     //Get request from database 
      CurrentRequest dbRequest =  CurrentRequest.find().findList().get(0);
      assertEquals("Request Price",100,dbRequest.getPrice() ,.1);
      assertEquals("Request Book Id",1,dbRequest.getBook().getPrimaryKey() );
      assertTrue("Request Condition Name not Used",
          "Used".equals(dbRequest.getCondition().getName()));
      //retrieve the entire model from the database
      
      
      List<Condition> conditions = Condition.find().findList();
      List<Book> books = Book.find().findList();
      List<Student> students = Student.find().findList();
      List<CurrentOffer> offers = CurrentOffer.find().findList();
      List<CurrentRequest> requests = CurrentRequest.find().findList();
     
      // check that we've recovered all our entities.
      
     
      assertEquals("Checking conditions", conditions.size(), 2);
      assertEquals("Checking books", books.size(), 1);
      assertEquals("Checking sudents", students.size(), 1);
      assertEquals("Checking offers", offers.size(), 1);
      assertEquals("Checking requests", requests.size(), 1);
      
       
      // check request is associated with Book 
      assertEquals("Request -Book ID", dbRequest.getPrimaryKey() ,books.get(0).requests.get(0).getPrimaryKey()); 
      assertEquals("Request Book Author vs Book Author", 
          dbRequest.getBook().getAuthor(),books.get(0).getAuthor());
      assertEquals("Request Book Title vs Book Title", 
          dbRequest.getBook().getTitle(),books.get(0).getTitle());
      
      // check request is associated with Student 
      assertEquals("Request ID -Student Request ID", dbRequest.getPrimaryKey(),
          students.get(0).requests.get(0).getPrimaryKey());
      assertEquals("Request Student Email vs Student Email", dbRequest.getStudent().getEmail()
          ,students.get(0).getEmail());
      assertEquals("Request Student First vs Student First", dbRequest.getStudent().getFirstName()
          ,students.get(0).getFirstName());
      assertEquals("Request Student Last vs Student Last", dbRequest.getStudent().getLastName() 
          ,students.get(0).getLastName());
      
      
      // check Offer is associated with Book 
      assertEquals("Offer -Book ID", dbOffer.getPrimaryKey(),books.get(0).requests.get(0).getPrimaryKey());
      assertEquals("Offer Book Author vs Book Author", 
          dbOffer.getBook().getAuthor(),books.get(0).getAuthor());
      assertEquals("Offer Book Title vs Book Title", 
          dbOffer.getBook().getTitle(),books.get(0).getTitle());
      
      // check request is associated with Student 
      assertEquals("Offer ID -Student Offer ID", dbOffer.getPrimaryKey(),
          students.get(0).requests.get(0).getPrimaryKey());
      assertEquals("Offer Student Email vs Student Email", dbOffer.getStudent().getEmail()
          ,students.get(0).getEmail());
      assertEquals("Offer Student First vs Student First", dbOffer.getStudent().getFirstName()
          ,students.get(0).getFirstName());
      assertEquals("Offer Student Last vs Student Last", dbOffer.getStudent().getLastName() 
          ,students.get(0).getLastName());
      
      
      assertTrue("Current Book has > 1 offer",books.get(0).getOffers().size() == 1);
      assertTrue("Current Student has > 1 offer",students.get(0).getOffers().size() == 1);
      //create new offer check book/student doesn't refer to it
      CurrentOffer offer2 = new CurrentOffer(1000,conditionNew,book,student);
      offer2.save();
     
     
      
      //create new offer check book/student doesn't refer to it
      CurrentRequest request2 = new CurrentRequest(1000,conditionNew,book,student);
      request2.save();
      assertTrue("Current Book has > 1 request",books.get(0).getRequests().size() == 1);
      assertTrue("Current Student has > 1 request",students.get(0).getRequests().size() == 1);
      
    //add offer check book/student does refer to it
     
      books.get(0).addOffer(offer2);
      students.get(0).addOffer(offer2);
      assertTrue("Current Book has != 2 offer",books.get(0).getOffers().size() == 2);
      assertTrue("Current Student != 2 offer",students.get(0).getOffers().size() == 2);
      
    //add offer check book/student does refer to it
      
      books.get(0).addRequest(request2);
      students.get(0).addRequest(request2);
      assertTrue("Current Book has != 2 request",books.get(0).getRequests().size() == 2);
      assertTrue("Current Student has != 2 request",students.get(0).getRequests().size() == 2);
      
      
     
      //remove a request from a book
    
      books.get(0).removeRequest(dbRequest);
      assertTrue("Current Book has request",books.get(0).requests.size() == 1);
      assertTrue("Fresh Book has request",Book.find().findList().get(0).requests.size() == 1);
      books.get(0).removeRequest(request2);
      assertTrue("Current Book has request",books.get(0).requests.isEmpty());
      assertTrue("Fresh Book has request",Book.find().findList().get(0).requests.isEmpty());
      
      //remove an request from a student
      
      students.get(0).removeRequest(dbRequest);
      assertTrue("Current Student has request",students.get(0).requests.size() == 1);
      assertTrue("Fresh Student has request",Student.find().findList().get(0).requests.size() == 1);
      students.get(0).removeRequest(request2);
      assertTrue("Current Student has request",students.get(0).requests.isEmpty());
      assertTrue("Fresh Student has request",Student.find().findList().get(0).requests.isEmpty());
      
      
      //remove a request from a book
      
      books.get(0).removeOffer(dbOffer);
      books.get(0).removeOffer(offer2);
      assertTrue("Current Book has offer",books.get(0).offers.isEmpty());
      assertTrue("Fresh Book has offer",Book.find().findList().get(0).offers.isEmpty());
      
      //remove an request from a student
     
      students.get(0).removeOffer(dbOffer);
      students.get(0).removeOffer(offer2);
      assertTrue("Current Student has offer",students.get(0).offers.isEmpty());
      assertTrue("Fresh Student has offer",Student.find().findList().get(0).offers.isEmpty());
      
      conditions.get(0).removeOffer(dbOffer);
     
     
      dbOffer.delete();
      assertEquals("Number of offers left after delete",CurrentOffer.find().findList().size() ,1);
      offer2.delete();
      assertEquals("Number of offers left after delete",CurrentOffer.find().findList().size() ,0);
   
      //remove condition
      CurrentRequest remove =  conditions.get(1).getRequests().get(0);
      conditions.get(1).removeRequest(remove);
       
      //delete request
      remove.delete();
      assertEquals("Number of requests left after delete",1,CurrentRequest.find().findList().size());
      
      remove =  CurrentRequest.find().findList().get(0);
      //delete request
      remove.delete();
      assertEquals("Number of requests left after delete",0,CurrentRequest.find().findList().size());
      
          
      book.delete();
      assertTrue("Should be no more books in database", Book.find().findList().isEmpty());
     
          
      student.delete();
      assertTrue("Should be no more students in database", Student.find().findList().isEmpty());
      
      
      conditions.get(0).delete();
      assertEquals("Should be no conditions more students in database",1,
          Condition.find().findList().size() );
      
      conditions.get(1).delete();
      assertEquals("Should be no conditions more students in database",0,
          Condition.find().findList().size() );
     
      
      
    }
    
    
}
