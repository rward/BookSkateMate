package models;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "RemovedRequests")
public class RemovedRequest extends BaseOffer {

  /**
   * 
   */
  private static final long serialVersionUID = 3286429588663939067L;

  public RemovedRequest(double price, Condition condition, Book book, Student student) {
    super(price, condition,book,student);
    
  }
 
  public RemovedRequest(CurrentRequest removeRequest) {
    super(removeRequest.getPrice(), removeRequest.getCondition() ,removeRequest.getBook(),
        removeRequest.getStudent());
    
  }
 public static Finder<Long,RemovedRequest> find() {
   
   return new Finder<Long,RemovedRequest>(Long.class,RemovedRequest.class);
 }
}
