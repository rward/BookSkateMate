package pages;

import models.Book;
import models.Condition;
import models.Student;
import org.fluentlenium.core.FluentPage;
import org.openqa.selenium.WebDriver;
import play.Logger;
import static org.fluentlenium.core.filter.FilterConstructor.withText;


public class MyRequestsPage extends FluentPage {
private String url;
  
  public MyRequestsPage (WebDriver webDriver, int port) {
    super(webDriver);
    this.url = "http://localhost:" + port + "/myRequests";
  }
  
 
  
  public String getUrl() {
    return this.url;
  }
  
  public void isAt() {
    assert(title().equals("My Reqiuests"));
  }
  
  // For testing purposes, use the same string for both ID and name.
  public void makeNewOffer(String bookName,String conditionName, String price) {
    //fill("select").with(bookId);
    click("option", withText(bookName));
    click("option", withText(conditionName));
    fill("input").with(price);
    submit("#newRequest");
    
  }
  
  

}
