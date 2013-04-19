package controllers;

import static play.data.Form.form;
import java.util.List;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class Condition extends Controller  {
  
  public static Result index() {
    
    List<models.Condition> conditions = models.Condition.find().findList();
    return ok(conditions.isEmpty() ? "No Conditions" : conditions.toString());

  }
  
  public static Result newCondition() {
    
    Form<models.Condition> conditionForm = form(models.Condition.class).bindFromRequest();
    if(conditionForm.hasErrors()) {
      
      return badRequest("The name is required.");
    }
        
    models.Condition condition = conditionForm.get();
    
   
    
    try {
     
      
      condition.save();
    }
    catch (Exception e) {
      return badRequest("The Condition not valid or already exists.");
    }
    
    return ok(condition.toString());
     
    
  }
public static Result delete(String name) {
    
    models.Condition condition = models.Condition.find().where().eq("name", name).findUnique();
    
    
    if (condition != null) {
      try {
           
            condition.delete();
           
      }
      catch (Exception e) {
        return badRequest("The Condtion cannot be deleted.");
      }
    }
    return ok();
  }

}
