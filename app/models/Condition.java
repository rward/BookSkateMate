package models;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import play.db.ebean.Model;
@Entity
public class Condition extends Model {
  
  
  /**
   * 
   */
  private static final long serialVersionUID = -5140179394166824227L;

  @Id
  public long id ;
  
  public long fkConditionNameId;
  
  
  @OneToOne(mappedBy="condition",cascade=CascadeType.ALL)
  public ConditionName conditionName;
  
  @OneToMany(mappedBy="condition", cascade=CascadeType.ALL)
  public List<Request> requests = new ArrayList<>();
  
  @OneToMany(mappedBy="condition", cascade=CascadeType.ALL)
  public List<Offer> offers = new ArrayList<>();
     

  public void setConditionName(ConditionName conditionName) {
     conditionName.setCondition(this);
     
  }
  
  public void addOffer(Offer offer) {
    
    offer.setCondition(this);
    offers.add(offer);
    offer.save();
    this.save();
  }
  public void removeOffer(Offer offer) {
   
    offers.remove(offer);
    offer.removeCondition();
    offer.save();
    this.save();
  }
   
  public void addRequest(Request request) {
    
    request.setCondition(this);
    requests.add(request);
    request.save();
    this.save();
    
  }
  
  public void removeRequest(Request request) {
   
    requests.remove(request);
    request.removeCondition();
    request.save();
    this.save();
  }
  
  public static Finder<Long,Condition> find() {
    
    return new Finder<Long,Condition>(Long.class,Condition.class);
  }
  public List<Offer> getOffers() {
    
    return Offer.find().query().where("fkConditionId = " + this.id).findList();
      
   }
   
   public List<Request> getRequests() {
     
     return Request.find().query().where("fkConditionId = " + this.id).findList();
       
    }
  
  public String getConditionName() {
    if (fkConditionNameId !=  0) {
      return ConditionName.find().byId(fkConditionNameId).name;
    }
    return null;
  }
  

}
