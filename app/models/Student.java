package models;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import play.db.ebean.Model;
@Entity
public class Student extends Model{

  /**
   * 
   */
  private static final long serialVersionUID = 1765615104131291592L;
  @Id
  public long id;
  public String firstName;
  public String lastName;
  public String email;
  
  @OneToMany(mappedBy="student", cascade=CascadeType.ALL,orphanRemoval= true)
  public List<Offer> offers = new ArrayList<>();
  
  @OneToMany(mappedBy="student", cascade=CascadeType.ALL,orphanRemoval= true)
  public List<Request> requests = new ArrayList<>();

  public Student(String firstName,String lastName, String email) {
   
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }
 
  /**
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @param firstName the firstName to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @param lastName the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }


  
  public static Finder<Long,Student> find() {
    
    return new Finder<Long,Student>(Long.class,Student.class);
  }

  
  public void clearOffers() {
    
    
    offers.clear();
  }
 public void addOffer(Offer offer) {
    
   offer.setStudent(this);
   offers.add(offer);
   offer.save();
   this.save();
  }
 public void removeOffer(Offer offer) {
   
   offers.remove(offer);
   offer.removeStudent();
   offer.save();
   this.save();
 }

 public void addRequest(Request request) {
   
   request.setStudent(this);
   requests.add(request);
   request.save();
   this.save();
   
 }
 public void removeRequest(Request request) {
   
   requests.remove(request);
   request.removeStudent();
   request.save();
   this.save();
 }
 
  public void clearRequests() {
    
    requests.clear();
  }
  public List<Offer> getOffers() {
    
    return Offer.find().query().where("fkStudentId = " + this.id).findList();
      
   }
   
   public List<Request> getRequests() {
     
     return Request.find().query().where("fkStudentId = " + this.id).findList();
       
    }
  
}
