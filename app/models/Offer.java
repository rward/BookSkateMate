package models;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CurrentOffers")
public class Offer extends BaseOffer {

  
  /**
   * 
   */
  private static final long serialVersionUID = 8524917640068429957L;

  public Offer(double price, Condition condition, Book book, Student student) {
    super(price, condition,book,student);
    
  }

}
