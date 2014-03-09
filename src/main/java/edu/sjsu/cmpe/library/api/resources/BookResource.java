package edu.sjsu.cmpe.library.api.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.yammer.dropwizard.jersey.params.LongParam;
import com.yammer.metrics.annotation.Timed;

import edu.sjsu.cmpe.library.domain.Author;
import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.domain.Review;
import edu.sjsu.cmpe.library.dto.AuthorDto;
import edu.sjsu.cmpe.library.dto.AuthorsDto;
import edu.sjsu.cmpe.library.dto.BookDto;
import edu.sjsu.cmpe.library.dto.LinkDto;
import edu.sjsu.cmpe.library.dto.ReviewDto;
import edu.sjsu.cmpe.library.dto.ReviewsDto;
import edu.sjsu.cmpe.library.repository.BookRepositoryInterface;

@Path("/v1/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
    /** bookRepository instance */
    private final BookRepositoryInterface bookRepository;
    private int reviewId =1; 
    /**
     * BookResource constructor
     * 
     * @param bookRepository
     *            a BookRepository instance
     */
    public BookResource(BookRepositoryInterface bookRepository) {
	this.bookRepository = bookRepository;
    }
    
    ////////  VIEW BOOK
    @GET
    @Path("/{isbn}")
    @Timed(name = "view-book")
    public BookDto getBookByIsbn(@PathParam("isbn") LongParam isbn) {
	Book book = bookRepository.getBookByISBN(isbn.get());
	BookDto bookResponse = new BookDto(book);
	
	bookResponse.addLink(new LinkDto("view-book", "/books/" + book.getIsbn(),"GET"));
    bookResponse.addLink(new LinkDto("update-book", "/books/" + book.getIsbn(), "PUT"));
	bookResponse.addLink(new LinkDto("delete-book", "/books/" + book.getIsbn(), "DELETE"));
	bookResponse.addLink(new LinkDto("create-review", "/books/" + book.getIsbn(), "POST"));
	bookResponse.addLink(new LinkDto("view-all-reviews", "/books/" + book.getIsbn(), "GET"));
	

	return bookResponse;
    }

    ////////  CREATE BOOK
    
    @POST
    @Timed(name = "create-book")
    public Response createBook(Book request) {
	// Store the new book in the BookRepository so that we can retrieve it.
	Book savedBook = bookRepository.saveBook(request);
	int author_id = 1;
	for (int author=0;author<savedBook.getAuthors().length;author++)
	{
		savedBook.getAuthors()[author].id = author_id;
		author_id++;

	}
	String location = "/books/" + savedBook.getIsbn();
	
	    Map<String, Object> response_Map = new HashMap<String, Object>();
	    List<LinkDto> links = new ArrayList<LinkDto>();
	    links.add(new LinkDto("view-book", location, "GET"));
	    links.add(new LinkDto("update-book",location,"PUT"));
	    links.add(new LinkDto("delete-book",location,"DELETE"));
	    links.add(new LinkDto("create-review",location+ "/reviews","POST"));
	
	  //BookDto bookResponse = new BookDto(savedBook);
	  //bookResponse.addLink(new LinkDto("view-book", location, "GET"));
	  //bookResponse.addLink(new LinkDto("update-book", location, "PUT"));
	  //bookResponse.addLink(new LinkDto("delete-book", location, "DELETE"));
	  //bookResponse.addLink(new LinkDto("create-review", location, "POST"));
	  //Add other links if needed
	    response_Map.put("links", links);
	    return Response.status(201).entity(response_Map).build();
	    
    }
    
    ////////  DELETE BOOK 
    
    @DELETE
    @Path("/{isbn}")
    @Timed(name = "delete-book")
    public Response deleteBook(@PathParam("isbn") LongParam isbn) {

	Book deletedBook = bookRepository.getBookByISBN(isbn.get());
    bookRepository.deleteBook(deletedBook);
	
	
	System.out.println("book has been deleted");
		
	BookDto bookResponse = new BookDto();
	
	Map<String, Object> response_Map = new HashMap<String, Object>();
    List<LinkDto> links = new ArrayList<LinkDto>();
    links.add(new LinkDto("create-book", "/books/", "POST"));
    
    response_Map.put("links", links);
    return Response.status(201).entity(response_Map).build();
    }
 
    ////////   UPDATE BOOK
    
    @PUT
    @Path("/{isbn}")
    @Timed(name = "update-book")
    public Response updateBook(@PathParam("isbn") LongParam isbn,@QueryParam("status") String status) {
    	
    	Book updatedBook = bookRepository.getBookByISBN(isbn.get());
    	updatedBook.setStatus(status);
        //bookRepository.updateBook(updatedBook);
    	
    	//Long isbnValue = isbn.get();
    	    		
    	//BookDto bookResponse = new BookDto();
    	
    	Map<String, Object> response_Map = new HashMap<String, Object>();
	    List<LinkDto> links = new ArrayList<LinkDto>();
	    links.add(new LinkDto("view-book", "/books/" + updatedBook.getIsbn() , "GET"));
	    links.add(new LinkDto("update-book","/books/" + updatedBook.getIsbn() ,"PUT"));
	    links.add(new LinkDto("delete-book","/books/" + updatedBook.getIsbn() ,"DELETE"));
	    links.add(new LinkDto("create-review","/books/" + updatedBook.getIsbn() + "/reviews","POST"));
	    links.add(new LinkDto("view-all-reviews","/books/" + updatedBook.getIsbn() + "/reviews","GET"));
	    
	    response_Map.put("links", links);
	    return Response.status(201).entity(response_Map).build();
  
    }
    
    //////// CREATE BOOK REVIEW    
    @POST
    @Path("/{isbn}/reviews")
    @Timed(name = "create-review")
    public Response createReview(@PathParam("isbn") LongParam isbn, Review reviews) {
    	
    Book book = bookRepository.getBookByISBN(isbn.get());
    
    reviews.setID(reviewId);
    reviewId++;
    book.getReview().add(reviews);
    
    ReviewDto reviewResponse = new ReviewDto(reviews);
	
	
	Map<String, Object> response_Map = new HashMap<String, Object>();
    List<LinkDto> links = new ArrayList<LinkDto>();
    links.add(new LinkDto("view-review", "/books/" + book.getIsbn() + "/reviews/" + reviews.getID(), "GET"));
    
    response_Map.put("links", links);
    return Response.status(201).entity(response_Map).build();
	
    } 
    
////////VIEW PARTICULAR BOOK REVIEW
    @GET
    @Path("/{isbn}/reviews/{review_id}")
    @Timed(name = "view-book-review")

    public ReviewDto viewReview (@PathParam("isbn") LongParam isbn,@PathParam("review_id") int reviewid ) {

    	Book book = bookRepository.getBookByISBN(isbn.get());
    	Review review =book.getbookReview(reviewid);
    	
       	ReviewDto reviewResponse = new ReviewDto(review);
       	
       	reviewResponse.addLink(new LinkDto("view-book-review", "/books/" + book.getIsbn()+"/reviews/","GET"));
    	return reviewResponse;
    	  	
    	   }
    	

    ////////VIEW ALL BOOK REVIEWS
    @GET
    @Path("/{isbn}/reviews")
    @Timed(name = "view-book-review")

    public Response viewallReviews (@PathParam("isbn") LongParam isbn ) {

    	Book book = bookRepository.getBookByISBN(isbn.get());

    	//Review review = book.getReview();

    	List<Review> review=book.getReview();
    	
    	ReviewsDto reviewResponse = new ReviewsDto(review);

    	reviewResponse.addLink(new LinkDto("view-book-review", "/books/" + book.getIsbn()+"/reviews/","GET"));
    	return Response.status(200).entity(reviewResponse).build();
    	
    	
    	}
    	


    ////////VIEW  BOOK AUTHOR
    @GET
    @Path("/{isbn}/authors/{id}")
    @Timed(name = "view-author")
    public Response viewAuthor(@PathParam("isbn") long isbn, @PathParam("id") long id) {
		int i=0;
		Book book = bookRepository.getBookByISBN(isbn);

		while (book.getbookAuthor(i).getID()!=id)
		{
			i++;
		}
		AuthorDto authorResponse = new AuthorDto(book.getbookAuthor(i));
		authorResponse.addLink(new LinkDto("view-author", "/books/" + book.getIsbn() + "/authors/" + book.getbookAuthor(i).getID(), "GET"));

	return Response.ok(authorResponse).build();
    }
    
    
    ////////VIEW  ALL BOOK AUTHORS
    @GET
    @Path("/{isbn}/authors")
    @Timed(name = "view-all-authors")
    public AuthorsDto viewAllAuthors(@PathParam("isbn") long isbn) {

		Book book = bookRepository.getBookByISBN(isbn);
		AuthorsDto authorResponse = new AuthorsDto(book.getAuthors());

	return authorResponse;
    }
    
    


	
    
}
