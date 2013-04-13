package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import play.db.ebean.Model;
@Entity
public class Request extends Model{

  
  /**
   * 
   */
  private static final long serialVersionUID = 6514456835286637428L;
  @Id
  public long id;
  /**
   * Price that student is willing to pay
   */
  public double price;
  
  
  @ManyToOne( )
  public Book book;
  
  @ManyToOne()
  public Student student;
  
  @ManyToOne()
  public Condition condition;

  public Request(double price, Condition condition  ) {

    this.price = price;
    this.condition = condition;
  
   
  }
  
  public Request(double price, Condition condition, Book book, Student student ) {

    this.price = price;
    this.condition = condition;
    this.book = book;
    this.student = student;
   
  }
  
 
  public void setPrice(double price) {
    
    this.price = price;
  }
  public double getPrice() {
    return price;
  }
  public void removeBook() {
    book = null;
  
  }
  public void removeStudent() {
    student = null;
   
  }
  public void removeCondition() {
    condition = null;
    
  }
  
  

  public void setCondition(Condition condition) {
    this.condition = condition;
    
  }
  
  public void setStudent(Student student) {
    this.student = student;
    
  }
  public void setBook(Book book) {
    this.book = book;
   
  }
  public Condition getCondition() {
   
    return condition;
  }
  public Book getBook() {
   
    return book;
  }
  public Book getBook2() {
    
    return book;
  }
  public Student getStudent() {
    
    return student;
  }
    
  
  public static Finder<Long,Request> find() {
    
    return new Finder<Long,Request>(Long.class,Request.class);
  }

 

}
