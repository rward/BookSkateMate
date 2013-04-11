package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.ManyToOne;


import play.db.ebean.Model;
@Entity
public class Offer extends Model{




  /**
   * 
   */
  private static final long serialVersionUID = -2599641293013380598L;
  @Id
  public long id;
  public double price;
  private long fkBookId;
  private long fkStudentId;
  private long fkConditionId;
  
  
  @ManyToOne()
   public Book book;
  
  @ManyToOne()
   public Student student;

  
  @ManyToOne()
  public Condition condition;

  public Offer (double price, Condition condition) {

    this.price = price;
    this.condition = condition;
    fkConditionId = condition.id;
   
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
  
  public Student getStudent() {
    if (fkStudentId !=  0) {
      return Student.find().byId(fkStudentId);
    }
    return null;
  }
  public void setPrice(double price) {
   
   this.price = price;
  }
 
  
  
  
  
  public static Finder<Long,Offer> find() {
    
    return new Finder<Long,Offer>(Long.class,Offer.class);
  }

 

}
