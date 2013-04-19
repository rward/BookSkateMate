package models;

import javax.persistence.Entity;
import javax.persistence.Table;
import play.db.ebean.Model.Finder;


@Entity
@Table(name = "CurrentRequests")
public class CurrentRequest extends BaseOffer {
   
  

   /**
   * 
   */
   private static final long serialVersionUID = 3503395897207103360L;

   public CurrentRequest(double price, Condition condition, Book book, Student student) {
      super(price, condition,book,student);
      
    }
   
   public static Finder<Long,CurrentRequest> find() {
     
     return new Finder<Long,CurrentRequest>(Long.class,CurrentRequest.class);
   }



}
