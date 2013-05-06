package controllers;

import java.util.List;
import play.data.Form;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
      
    public static Result index() {
      return ok(index.render("Home"));
    }
    public static Result about() {
      return ok(about.render("About"));
    }
    public static Result searchRequests() {
      return ok(searchRequests.render("Search Requests"));
    }
    
    public static Result searchOffers() {
      return ok(searchOffers.render("Search Offers"));
    }
    public static Result conditions() {
      List<models.Condition> conditionList = models.Condition.find().findList();
      return ok(conditions.render("",conditionList));
    }
    
}
