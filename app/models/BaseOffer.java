package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import javax.persistence.ManyToOne;


import play.db.ebean.Model;
@Entity
//@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@MappedSuperclass
public class BaseOffer extends Model{




  /**
   * 
   */
  private static final long serialVersionUID = -2599641293013380598L;
  @Id
  public long id;
  public double price;
  
  
  
  @ManyToOne()
   public Book book;
  
  @ManyToOne()
   public Student student;

  
  @ManyToOne()
  public Condition condition;

  public BaseOffer (double price, Condition condition) {

    this.price = price;
    this.condition = condition;
   
   
  }
  public BaseOffer (double price, Condition condition, Book book, Student student) {

    this.price = price;
    this.condition = condition;
    this.student = student;
    this.book = book;
   
   
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
  
  public Student getStudent() {
   
    return student;
  }
  public void setPrice(double price) {
   
   this.price = price;
  }
  public double getPrice() {
    
    return price;
  }
  
  public static Finder<Long,Offer> find() {
    
    return new Finder<Long,Offer>(Long.class,Offer.class);
  }

 

}
