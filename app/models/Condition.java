package models;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
@Entity
public class Condition extends Model {
   
  /**
   * 
   */
  private static final long serialVersionUID = -5140179394166824227L;

  @Id
  private long primaryKey;
   
  @Required
  @Column(unique=true, nullable=false)
  private String name = "";
  
  @OneToMany(mappedBy="condition", cascade=CascadeType.ALL)
  private List<CurrentRequest> requests = new ArrayList<>();
  
  @OneToMany(mappedBy="condition", cascade=CascadeType.ALL)
  private List<CurrentOffer> offers = new ArrayList<>();
   
  /**
   * 
   * @param name
   */
  public Condition(String name) {
    
    this.name = name;
  }
  /**
   * 
   */
  public String toString() {
     
   return String.format("[%s]", name);
   
  }
  
  
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

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
   
      this.name = name;
      
   
  }

  /**
   * @param requests the requests to set
   */
  public void setRequests(List<CurrentRequest> requests) {
    this.requests = requests;
  }

  /**
   * @param offers the offers to set
   */
  public void setOffers(List<CurrentOffer> offers) {
    this.offers = offers;
  }

 
  
  public void addOffer(CurrentOffer offer) {
    
    offer.setCondition(this);
    offers.add(offer);
    offer.save();
    this.save();
  }
  public void removeOffer(CurrentOffer offer) {
   
    offers.remove(offer);
    offer.removeCondition();
    offer.save();
    this.save();
  }
   
  public void addRequest(CurrentRequest request) {
    
    request.setCondition(this);
    requests.add(request);
    request.save();
    this.save();
    
  }
  
  public void removeRequest(CurrentRequest request) {
   
    requests.remove(request);
    request.removeCondition();
    request.save();
    this.save();
  }
  
  public static Finder<Long,Condition> find() {
    
    return new Finder<Long,Condition>(Long.class,Condition.class);
  }
  public List<CurrentOffer> getOffers() {
    
    return offers;
      
   }
   
   public List<CurrentRequest> getRequests() {
     
     return requests;
       
    }
  
    

}
