import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.avaje.ebean.Ebean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;



import models.Book;
import models.Condition;
import models.ConditionName;
import models.Offer;
import models.Request;
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
    public void testModel() {
      
      // create 1 tag warehouse that's associated with 1 StockItem for 1 product
      Book book = new Book("Title", "Author");
      book.isbn = "ISBN";
      book.edition = 1;
      
      book.save();
     
     
      // Create 1 Warehouse that's associated with 1 StockItem for 1 product
      Student student = new Student("First","Last","Email");
      student.save();
      
     ConditionName  conditionNameNew  = new ConditionName();
     conditionNameNew.setName("New");
     conditionNameNew.save();
     
     ConditionName  conditionNameUsed  = new ConditionName();
     conditionNameUsed.setName("Used");
     conditionNameUsed.save();
     
     Condition conditionNew = new Condition();
     conditionNameNew.setCondition(conditionNew);
     
     Condition conditionUsed = new Condition();
     conditionNameUsed.setCondition(conditionUsed);
     
          
     
     conditionNew = Condition.find().findList().get(0);
     conditionNameNew = ConditionName.find().findList().get(0);
     
     assertTrue( "Condition name not New ",  "New".equals(conditionNew.getConditionName())  );
     assertTrue( "ConditionName name not New ",  "New".equals(conditionNameNew.name) );
     
     conditionUsed = Condition.find().findList().get(1);
     conditionNameUsed = ConditionName.find().findList().get(1);
     
     assertTrue( "Condition name not Used ",  "Used".equals(conditionUsed.getConditionName())  );
     assertTrue( "ConditionName name not Used ",  "Used".equals(conditionNameUsed.name) );
     
    
      Offer offer = new Offer(100,conditionNew);
      conditionNew.addOffer(offer);
      student.addOffer(offer);
      book.addOffer(offer);
           
      assertEquals("Added 1 offer to condition",  1, conditionNew.getOffers().size());
      assertEquals("Added 1 offer to student",  1, student.getOffers().size());
      assertEquals("Added 1 offer to book",  1, book.getOffers().size());
      
      Request request = new Request(100, conditionUsed);
            
      conditionUsed.addRequest(request);
      student.addRequest(request);
      book.addRequest(request);
      
      assertEquals("Added 1 offer to condition",  1, conditionUsed.getRequests().size());
      assertEquals("Added 1 offer to student",  1, student.getRequests().size());
      assertEquals("Added 1 offer to book",  1, book.getRequests().size());
      
      //Get Book from database 
      Book dbBook =  Book.find().findList().get(0);
      assertTrue( "Book Title",  "Title".equals(dbBook.title)  );
      assertTrue("Book Author", "Author".equals(dbBook.author )  );
      assertTrue("Book ISBN", "ISBN".equals(dbBook.isbn));
     
      Book dbBook2 =  Book.find().byId(1L);
      
      assertTrue( "Book Title",  "Title".equals(dbBook2.title)  );
      assertTrue("Book Author", "Author".equals(dbBook2.author )  );
      assertTrue("Book ISBN", "ISBN".equals(dbBook2.isbn));
      
      
    //Get Book from database 
      Student dbStudent =  Student.find().findList().get(0);
      assertTrue("Student First","First".equals(dbStudent.firstName) );
      assertTrue("Student Last","Last".equals(dbStudent.lastName ) );
      assertTrue("Student Email","Email".equals(dbStudent.email));
     
      ConditionName dbConditionName = ConditionName.find().findList().get(0);
      assertTrue("Condition Name","New".equals(dbConditionName.name) );
      
      
      //Get offer from database 
      Offer dbOffer =  Offer.find().findList().get(0);
      assertEquals("Offer Price",100,dbOffer.price,.1);
      assertEquals("Offer Book Id",1,dbOffer.getBook().id);
      assertTrue("Offer Condition Name",
          "New".equals(dbOffer.getCondition().getConditionName()));
      
      
     //Get request from database 
      Request dbRequest =  Request.find().findList().get(0);
      assertEquals("Request Price",100,dbRequest.price,.1);
      assertEquals("Request Book Id",1,dbRequest.getBook().id);
      assertTrue("Request Condition Name not Used",
          "Used".equals(dbRequest.getCondition().getConditionName()));
      //retrieve the entire model from the database
      
      List<ConditionName> conditionNames = ConditionName.find().findList();
      List<Condition> conditions = Condition.find().findList();
      List<Book> books = Book.find().findList();
      List<Student> students = Student.find().findList();
      List<Offer> offers = Offer.find().findList();
      List<Request> requests = Request.find().findList();
     
      // check that we've recovered all our entities.
      
      assertEquals("Checking conditionNames", conditionNames.size(), 2);
      assertEquals("Checking conditions", conditions.size(), 2);
      assertEquals("Checking books", books.size(), 1);
      assertEquals("Checking sudents", students.size(), 1);
      assertEquals("Checking offers", offers.size(), 1);
      assertEquals("Checking requests", requests.size(), 1);
      
      
      // check request is associated with Book 
      assertEquals("Request -Book ID", dbRequest.id,books.get(0).requests.get(0).id);
      assertEquals("Request Book Author vs Book Author", 
          dbRequest.getBook().author,books.get(0).author);
      assertEquals("Request Book Title vs Book Title", 
          dbRequest.getBook().title,books.get(0).title);
      
      // check request is associated with Student 
      assertEquals("Request ID -Student Request ID", dbRequest.id,
          students.get(0).requests.get(0).id);
      assertEquals("Request Student Email vs Student Email", dbRequest.getStudent().email
          ,students.get(0).email);
      assertEquals("Request Student First vs Student First", dbRequest.getStudent().firstName
          ,students.get(0).firstName);
      assertEquals("Request Student Last vs Student Last", dbRequest.getStudent().lastName 
          ,students.get(0).lastName);
      
      
      // check Offer is associated with Book 
      assertEquals("Offer -Book ID", dbOffer.id,books.get(0).requests.get(0).id);
      assertEquals("Offer Book Author vs Book Author", 
          dbOffer.getBook().author,books.get(0).author);
      assertEquals("Offer Book Title vs Book Title", 
          dbOffer.getBook().title,books.get(0).title);
      
      // check request is associated with Student 
      assertEquals("Offer ID -Student Offer ID", dbOffer.id,
          students.get(0).requests.get(0).id);
      assertEquals("Offer Student Email vs Student Email", dbOffer.getStudent().email
          ,students.get(0).email);
      assertEquals("Offer Student First vs Student First", dbOffer.getStudent().firstName
          ,students.get(0).firstName);
      assertEquals("Offer Student Last vs Student Last", dbOffer.getStudent().lastName 
          ,students.get(0).lastName);
      
      //create new offer check book/student doesn't refer to it
      Offer offer2 = new Offer(1000,conditionNew);
      offer2.save();
      assertTrue("Current Book has > 1 offer",books.get(0).getOffers().size() == 1);
      assertTrue("Current Student has > 1 offer",students.get(0).getOffers().size() == 1);
      
      //create new offer check book/student doesn't refer to it
      Request request2 = new Request(1000,conditionNew);
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
      assertEquals("Number of offers left after delete",Offer.find().findList().size() ,1);
      offer2.delete();
      assertEquals("Number of offers left after delete",Offer.find().findList().size() ,0);
   
      //remove condition
      Request remove =  conditions.get(1).getRequests().get(0);
      conditions.get(1).removeRequest(remove);
       
      //delete request
      remove.delete();
      assertEquals("Number of requests left after delete",Offer.find().findList().size() ,0);
      //request.delete();
      //assertEquals("Number of offers left after delete",Offer.find().findList().size() ,0);
      
            
      
           
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
