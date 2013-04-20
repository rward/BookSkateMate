package models;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "CurrentOffers")
public class CurrentOffer extends BaseOffer {

  
  /**
   * 
   */
  private static final long serialVersionUID = 8524917640068429957L;

  public CurrentOffer(double price, Condition condition, Book book, Student student) {
    super(price, condition,book,student);
    
  } 

  public static Finder<Long,CurrentOffer> find() {
    
    return new Finder<Long,CurrentOffer>(Long.class,CurrentOffer.class);
  }

  
}
