package models;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
@Entity
public class Student extends Model{

  /**
   * 
   */
  private static final long serialVersionUID = 1765615104131291592L;
  @Id
  private long primaryKey;
  /**
   * @return the primaryKey
   */
  public long getPrimaryKey() {
    return primaryKey;
  }
  /**
   * @param primaryKey the primaryKey to set
   */
  public void setPrimaryKey(long primaryKey) {
    this.primaryKey = primaryKey;
  }

  @Required
  @Column(unique=true, nullable=false)
  private String studentId;
  
  @Required
  private String firstName;
  @Required
  private String lastName;
  @Required
  private String email;
  
  @OneToMany(mappedBy="student", cascade=CascadeType.ALL,orphanRemoval= true)
  public List<CurrentOffer> offers = new ArrayList<>();
  
  @OneToMany(mappedBy="student", cascade=CascadeType.ALL,orphanRemoval= true)
  public List<CurrentRequest> requests = new ArrayList<>();
 
  /**
   * 
   * @param studentId
   * @param firstName
   * @param lastName
   * @param email
   */
  public Student(String studentId, String firstName,String lastName, String email) {
   
    this.studentId = studentId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }
  /**
   * 
   */
  public String toString() {
    return String.format("[ %s %s %s %s ]", studentId,firstName,lastName,email) ;
  }
  /**
   * @return the studentId
   */
  public String getStudentId() {
    return studentId;
  }

  /**
   * @param studentId the studentId to set
   */
  public void setStudentId(String studentId) {
    this.studentId = studentId;
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
 public void addOffer(CurrentOffer offer) {
    
   offer.setStudent(this);
   offers.add(offer);
   offer.save();
   this.save();
  }
 public void removeOffer(CurrentOffer offer) {
   
   offers.remove(offer);
   offer.removeStudent();
   offer.save();
   this.save();
 }

 public void addRequest(CurrentRequest request) {
   
   request.setStudent(this);
   requests.add(request);
   request.save();
   this.save();
   
 }
 public void removeRequest(CurrentRequest request) {
   
   requests.remove(request);
   request.removeStudent();
   request.save();
   this.save();
 }
 
  public void clearRequests() {
    
    requests.clear();
  }
  public List<CurrentOffer> getOffers() {
    
    return offers;
      
   }
   
   public List<CurrentRequest> getRequests() {
     
     return requests;
       
    }
  
}
