package pages;

import models.Book;
import models.Condition;
import models.Student;
import org.fluentlenium.core.FluentPage;
import org.openqa.selenium.WebDriver;
import play.Logger;
import static org.fluentlenium.core.filter.FilterConstructor.withText;


public class MyOffersPage extends FluentPage {
private String url;
  
  public MyOffersPage (WebDriver webDriver, int port) {
    super(webDriver);
    this.url = "http://localhost:" + port + "/myOffers";
  }
  
 
  public String getUrl() {
    return this.url;
  }
  
  public void isAt() {
    assert(title().equals("My Offers"));
  }
  
  // For testing purposes, use the same string for both ID and name.
  public void makeNewOffer(String bookName,String conditionName, String price) {
    //fill("select").with(bookId);
    click("option", withText(bookName));
    click("option", withText(conditionName));
    fill("input").with(price);
    submit("#newOffer");
    
  }
  
 
  

}
