package models;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


import play.db.ebean.Model;
@Entity
public class Book extends Model{

  


  /**
   * 
   */
  private static final long serialVersionUID = -5295046043310689924L;
  
  @Id
  public long id;
  public String title;
  public String author;
  public String isbn;
  public double bookStorePrice;
  public int edition;
  
   
  
  @OneToMany(mappedBy="book", cascade=CascadeType.ALL,fetch=FetchType.EAGER)
  public List<Request> requests = new ArrayList<>();
  
  @OneToMany(mappedBy="book", cascade=CascadeType.ALL,fetch=FetchType.EAGER )
  public List<Offer> offers = new ArrayList<>();
    
  public Book (String title, String author){
    
    this.title = title;
    this.author = author;    
    
  }
  /**
   * @return the title
   */
  public String getTitle() {
    return title;
  }
  /**
   * @param title the title to set
   */
  public void setTitle(String title) {
    this.title = title;
  }
  /**
   * @return the author
   */
  public String getAuthor() {
    return author;
  }
  /**
   * @param author the author to set
   */
  public void setAuthor(String author) {
    this.author = author;
  }
  
 
  /**
   * @return the isbn
   */
  public String getIsbn() {
    return isbn;
  }
  /**
   * @param isbn the isbn to set
   */
  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }
  /**
   * @return the bookStorePrice
   */
  public double getBookStorePrice() {
    return bookStorePrice;
  }
  /**
   * @param bookStorePrice the bookStorePrice to set
   */
  public void setBookStorePrice(double bookStorePrice) {
    this.bookStorePrice = bookStorePrice;
  }
  /**
   * @return the edition
   */
  public int getEdition() {
    return edition;
  }
  /**
   * @param edition the edition to set
   */
  public void setEdition(int edition) {
    this.edition = edition;
  }


 
  public void clearOffers() {
    
    offers.clear();
  }
  public void addOffer(Offer offer) {
    
    offer.setBook(this);
    offers.add(offer);
    offer.save();
    this.save();
  }
  public void removeOffer(Offer offer) {
   
    offers.remove(offer);
    offer.removeBook();
    offer.save();
    this.save();
  }
   
  public void addRequest(Request request) {
    
    request.setBook(this);
    requests.add(request);
    request.save();
    this.save();
    
  }
  
  public void removeRequest(Request request) {
   
    requests.remove(request);
    request.removeBook();
    request.save();
    this.save();
  }
  
  
  public void clearRequests() {
    
    requests.clear();
  }
  
  
  public List<Offer> getOffers() {
    
    return Offer.find().query().where("fkBookId = " + this.id).findList();
      
   }
   
   public List<Request> getRequests() {
     
     return Request.find().query().where("fkBookId = " + this.id).findList();
       
    }
  public static Finder<Long,Book> find() {
    
    return new Finder<Long,Book>(Long.class,Book.class);
  }

 

}
