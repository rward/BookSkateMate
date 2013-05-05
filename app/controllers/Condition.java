package controllers;

import static play.data.Form.form;
import java.util.List;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Condition extends Controller  {
  /**
   * Response for a request for all the conditions available
   * @return Either the string with list of books or the string "No books"
   */
  public static Result index() {
    
    List<models.Condition> conditions = models.Condition.find().findList();
    
   // return ok(conditions.render("Conditions"));
    return ok(conditions.isEmpty() ? "No Conditions" : conditions.toString());

  }
  /**
   * Response for a request creation of new condition.
   * @return Either OK or badRequest if invalid request.
   */
  public static Result newCondition() {
    
    Form<models.Condition> conditionForm = form(models.Condition.class).bindFromRequest();
    List<models.Condition> conditionList = models.Condition.find().findList();
    if(conditionForm.hasErrors()) {
      return badRequest(conditions.render("The Name is required.",conditionList));
     
    }
        
    models.Condition condition = conditionForm.get();
   if(!("!Fred3".equals(condition.getPassword()))) {
      return badRequest(conditions.render("You are not an administrator.",conditionList));
   }
   
    try {
     
      
      condition.save();
    }
    catch (Exception e) {
    
      return badRequest(conditions.render("The Condition is not valid or already exists.",conditionList));
     // return badRequest("The Condition not valid or already exists.");
    }
    
    conditionList = models.Condition.find().findList();
    return ok(conditions.render("New condition added",conditionList));
        
  }
  
  /**
   * Response for a request delete a condition.
   * @return Either OK or badRequest if invalid request to delete.
   */
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
