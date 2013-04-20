package models;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "RemovedOffers")
public class RemovedOffer extends BaseOffer {

  
  /**
   * 
   */
  private static final long serialVersionUID = 6439382917334961346L;

  public RemovedOffer(double price, Condition condition, Book book, Student student) {
    super(price, condition, book, student);
    
  }
  
  public RemovedOffer(CurrentOffer offer) {
    super(offer.getPrice() , offer.getCondition(), offer.getBook(), offer.getStudent());
  }
  
 public static Finder<Long,RemovedOffer> find() {
    
    return new Finder<Long,RemovedOffer>(Long.class,RemovedOffer.class);
  }

}
