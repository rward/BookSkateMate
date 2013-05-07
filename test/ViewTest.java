
import static play.test.Helpers.HTMLUNIT;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;
import models.Book;
import models.Condition;
import models.Student;
import org.junit.Test;
import pages.IndexPage;
import pages.MyOffersPage;
import pages.MyRequestsPage;

import play.Logger;
import play.libs.F.Callback;
import play.test.TestBrowser;
import static org.fest.assertions.Assertions.assertThat;


public class ViewTest {
  
  public void addData() {
    Logger.info("BookSkateMate Demo Data Loaded");
    Book book = new Book("The Elements of Java StyleVER1", "The Elements of Java Style", 
        "Allan Vermeulen","978-0521777681",1 , 22.25);
    book.save();
    Book book1 = new Book("Java ConceptsVER6", "Java Concepts", 
        "Horstmann","978-0470509470",6 , 59.95);
    book1.save();
    Book book2 = new Book("Database Systems the Complete BookVER2",
        "Database Systems the Complete Book", "Garcia-Molina","978-0131873254",2 , 169.99);
    book2.save();
    Book book3 = new Book("Discrete Math and its ApplicationsVER7", 
        "Discrete Math and its Applications", "Rosen","978-0073383095",7 , 222.25);
    book3.save();
    Book book4 = new Book("Introductions to AlgorithmsVER3", 
        "Introductions to Algorithms", "Cormen","978-0262033848",9 , 92.99);
    book4.save();
    Condition conditionNew = new Condition("New");
    conditionNew.save();
    Condition conditionLikeNew = new Condition("Like New");
    conditionLikeNew.save(); 
    Condition conditionUsed = new Condition("Used");
    conditionUsed.save(); 
    Condition conditionFair = new Condition("Fair");
    conditionFair.save();
    Condition conditionPoor = new Condition("Poor");
    conditionPoor.save();
    Student student = new Student("StudentID01", "First","Last","Email");
    student.save();
    
  }
  
  @Test
  public void testIndexPage () {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
      public void invoke(TestBrowser browser) {
        addData();
        IndexPage homePage = new IndexPage(browser.getDriver(), 3333);
        browser.goTo(homePage);
        homePage.isAt();
        homePage.gotoNewWarehouse();
        homePage.gotoHome();
      }
    });
  }
  
  @Test
  public void testMyOffersPage () {
    
    running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
      public void invoke(TestBrowser browser) {
        addData();
        MyOffersPage offersPage = new MyOffersPage(browser.getDriver(), 3333);
        browser.goTo(offersPage);
        offersPage.isAt();
        offersPage.makeNewOffer("Java Concepts", "Used", "22.15");
        assertThat(offersPage.pageSource()).contains("Offer Created.");
        String noWhiteSpace = offersPage.pageSource().replaceAll("\\s","");
        assertThat(noWhiteSpace.contains("<td>JavaConcepts</td><td>Used</td>"));
        assertThat(noWhiteSpace.contains("value=22.15"));
        offersPage.isAt();
               
      }
    });
  }
  @Test
  public void testMyRequestsPage () {
    
    running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
      public void invoke(TestBrowser browser) {
        addData();
        MyRequestsPage requestsPage = new MyRequestsPage(browser.getDriver(), 3333);
        browser.goTo(requestsPage);
        requestsPage.isAt();
        requestsPage.makeNewOffer("Java Concepts", "Used", "22.15");
        assertThat(requestsPage.pageSource()).contains("Request Created.");
        String noWhiteSpace = requestsPage.pageSource().replaceAll("\\s","");
        assertThat(noWhiteSpace.contains("<td>JavaConcepts</td><td>Used</td>"));
        assertThat(noWhiteSpace.contains("value=22.15"));
        requestsPage.isAt();
        
       
      
     
       
      }
    });
  }
  
}
