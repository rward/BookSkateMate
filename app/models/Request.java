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
  /**
   * 
   */
  private long fkBookId;
  /**
   * 
   */
  private long fkStudentId;
  private long fkConditionId;
  
  @ManyToOne( )
  public Book book;
  
  @ManyToOne()
  public Student student;
  
  @ManyToOne()
  public Condition condition;

  public Request(double price, Condition condition  ) {

    this.price = price;
    this.condition = condition;
    fkConditionId = condition.id;
   
  }
  
 
  public void setPrice(double price) {
    
    this.price = price;
  }
  public void removeBook() {
    book = null;
    fkBookId = 0;
  }
  public void removeStudent() {
    student = null;
    fkStudentId = 0;
  }
  public void removeCondition() {
    condition = null;
    fkConditionId = 0;
  }
  
  

  public void setCondition(Condition condition) {
    this.condition = condition;
    fkConditionId = condition.id;
  }
  
  public void setStudent(Student student) {
    this.student = student;
    fkStudentId = student.id;
  }
  public void setBook(Book book) {
    this.book = book;
    fkBookId = book.id;
  }
  public Condition getCondition() {
    if (fkConditionId !=  0) {
      return Condition.find().byId(fkConditionId);
    }
    return null;
  }
  public Book getBook() {
    if (fkBookId !=  0) {
      return Book.find().byId(fkBookId);
    }
    return null;
  }
  public Book getBook2() {
    
    return book;
  }
  public Student getStudent() {
    if (fkStudentId !=  0) {
      return Student.find().byId(fkStudentId);
    }
    return null;
  }
    
  
  public static Finder<Long,Request> find() {
    
    return new Finder<Long,Request>(Long.class,Request.class);
  }

 

}
