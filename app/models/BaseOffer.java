package models;



import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.ManyToOne;
import javax.persistence.UniqueConstraint;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

//@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@MappedSuperclass
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "book_primary_key","student_primary_key" }) )

@Entity
public class BaseOffer extends Model{

 
  private static final long serialVersionUID = -2599641293013380598L;
  @Id
  private long primaryKey;

  @Required
  @Transient
  public String studentId;
  @Required
  @Transient
  public String bookId;
  @Required
  @Transient
  public String conditionName;

  

  private double price;
  
 
  @ManyToOne()
  private Book book;
  
  
  @ManyToOne()
  private Student student;

  
  @ManyToOne()
  private Condition condition;

  public BaseOffer (double price, Condition condition) {

    this.price = price;
    this.condition = condition;
   
   
  }
  /**
   * 
   * @param price
   * @param condition
   * @param book
   * @param student
   */
  public BaseOffer (double price, Condition condition, Book book, Student student) {

    this.price = price;
    this.condition = condition;
    this.student = student;
    this.book = book;
   
   
  }
  /**
   * 
   */
  public String toString() {
    return String.format("[ %s %s %s %s %s ]"
        ,book.getBookId() , book.getTitle(),student.getStudentId(),condition.getName(),price) ;
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
   * 
   */
  public void removeBook() {
    book = null;
   
  }
  /**
   * 
   */
  public void removeStudent() {
    student = null;
   
  }
  /**
   * 
   */
  public void removeCondition() {
    condition = null;
    
  }
  /**
   * 
   * @param condition
   */
  public void setCondition(Condition condition) {
    this.condition = condition;
    
  }
  /**
   * 
   * @param student
   */
  public void setStudent(Student student) {
    this.student = student;
    
  }
  /**
   * 
   * @param book
   */
  public void setBook(Book book) {
    this.book = book;
    
  }
  /**
   * 
   * @return
   */
  public Condition getCondition() {
    return condition;
  }
  /**
   * 
   * @return
   */
  public Book getBook() {
    return book;
  }
  /**
   * 
   * @return
   */
  public Student getStudent() {
   
    return student;
  }
  /**
   * 
   * @param price
   */
  public void setPrice(double price) {
   
   this.price = price;
  }
  /**
   * 
   * @return
   */
  public double getPrice() {
    
    return price;
  }
  
 
 

}
