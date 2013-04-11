package models;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import play.db.ebean.Model;
 
@Entity
public class ConditionName extends Model {
 
  
  


  /**
   * 
   */
  private static final long serialVersionUID = -4509221223988430875L;

  @Id 
  public long id;
  
  public String name = "";
  
  @OneToOne @MapsId
  public Condition condition;
  
  private long fkConditionId; 
  
  public Condition getCondition() {
      return condition;
  }
  
  public void setName(String name) {
    
    this.name = name;
   
}
  public void setCondition(Condition  condition) {
      
      condition.conditionName = this;
      condition.fkConditionNameId = this.id;
           
      condition.save();
      this.condition = condition;
      fkConditionId = condition.id;
      this.save();
      
      
     
  }
  public Long getConditionID() {
    if (fkConditionId !=  0) {
      return Condition.find().byId(fkConditionId).id;
    }
    return -1L;
  }  
  public static Finder<Long,ConditionName> find() {
    
    return new Finder<Long,ConditionName>(Long.class,ConditionName.class);
  }

}
