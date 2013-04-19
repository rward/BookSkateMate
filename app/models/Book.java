package models;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
@Entity
public class Book extends Model{

  


  /**
   * 
   */
  private static final long serialVersionUID = -5295046043310689924L;
  
  @Id
  public long primaryKey;
  
  @Required
  @Column(unique=true, nullable=false)
  private String bookId;
  
  
  
 
  private String title;
  private String author;
  private String isbn;
  private double bookStorePrice;
  private int edition;
  
   
  
  @OneToMany(mappedBy="book", cascade=CascadeType.ALL,fetch=FetchType.EAGER)
  public List<Request> requests = new ArrayList<>();
  
  @OneToMany(mappedBy="book", cascade=CascadeType.ALL,fetch=FetchType.EAGER )
  public List<Offer> offers = new ArrayList<>();
  /**
   * Constructor for book with all required fields.
   * @param bookId The unique identifier to use to find a book
   * @param title the title of the book
   * @param author the authors name
   * @param isbn the isbn of this book
   * @param edition the edition number of this book
   * @param bookStorePrice the current price of this book at the book store
   */
   public Book (String bookId, String title, String author, String isbn, int edition, double bookStorePrice){
    
    this.bookId = bookId;
    this.title = title;
    this.author = author;    
    this.isbn = isbn;
    this.edition = edition;
    this.bookStorePrice = bookStorePrice;
    
  }
   /**
    * Prints out all the items of a book. 
    */
   public String toString() {
     return String.format("[ %s %s %s %s ]", bookId,title,author,isbn,edition,bookStorePrice) ;
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
    * @return the bookId
    */
   public String getBookId() {
     return bookId;
   }
   /**
    * 
    * @param bookId
    */
   public void setBookId(String bookId) {
     this.bookId = bookId;
   }
   /**
    * @param bookId the bookId to set
    */
   
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
    for (Offer offer : offers ) {
      offer.removeBook();
    }
     
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
    
    return offers;
      
   }
   
   public List<Request> getRequests() {
     
     return requests;
       
    }
  public static Finder<Long,Book> find() {
    
    return new Finder<Long,Book>(Long.class,Book.class);
  }

 

}
