import models.Book;
import models.Condition;
import models.Student;
import play.*;

public class Global extends GlobalSettings {

  @Override
  public void onStart(Application app) {
    Logger.info("BookSkateMate Application has started");
    
    Book book = new Book("The Elements of Java StyleVER1", "The Elements of Java Style", "Allan Vermeulen","978-0521777681",1 , 22.25);
    book.save();
    Book book1 = new Book("Java ConceptsVER6", "Java Concepts", "Horstmann","978-0470509470",6 , 59.95);
    book1.save();
    Book book2 = new Book("DATABASE SYSTEMS THE COMPLETE BOOKVER2", "DATABASE SYSTEMS THE COMPLETE BOOK", "Garcia-Molina","978-0131873254",2 , 169.99);
    book2.save();
    Book book3 = new Book("DISCRETE MATH+ITS APPLICATIONVER7", "DISCRETE MATH and its Applications", "Rosen","978-0073383095",7 , 222.25);
    book3.save();
    Book book4 = new Book("INTRODUCTION TO ALGORITHMSVER3", "INTRODUCTION TO ALGORITHMS", "Cormen","978-0262033848",9 , 92.99);
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
  
  @Override
  public void onStop(Application app) {
    Logger.info("BookSkateMate Application shutdown...");
  }  
    
}