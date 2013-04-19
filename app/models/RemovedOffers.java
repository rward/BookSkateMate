package models;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "RemovedOffers")
public class RemovedOffers extends BaseOffer {

  /**
   * 
   */
  private static final long serialVersionUID = 6439382917334961346L;

  public RemovedOffers(double price, Condition condition, Book book, Student student) {
    super(price, condition, book, student);
    
  }
  
  public RemovedOffers(Offer offer) {
    super(offer.getPrice() , offer.getCondition(), offer.getBook(), offer.getStudent());
  }

}
