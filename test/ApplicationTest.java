
import org.junit.*;

import play.mvc.*;
import play.test.*;


import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest {

  
 private FakeApplication application;
  
  
  @Before
  public void startApp() {
    application = fakeApplication(inMemoryDatabase());
    start(application);
     
  }
  
  @After
  public void stopApp() {
    
    stop(application);
  }
    @Test 
    public void simpleCheck() {
        int a = 1 + 1;
        assertThat(a).isEqualTo(2);
    }
    
    @Test
    public void renderTemplate() {
        Content html = views.html.index.render("Your new application is ready.");
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("Welcome to BookSkateMate!");
    }
  
   
}
