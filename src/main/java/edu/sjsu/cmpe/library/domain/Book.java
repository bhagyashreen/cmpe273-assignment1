package edu.sjsu.cmpe.library.domain;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;


public class Book {
    private long isbn;
    private String title;
    @JsonProperty("publication-date") 
    private String publication;
    private String language;
    @JsonProperty("num-pages") 
    private int numpages;
    private String status;
    private List<Review> reviews = new ArrayList<Review>();
    @NotEmpty
	@Valid
	@JsonProperty
    private Author[] authors;
    // add more fields here

    /**
     * @return the isbn
     */
    public long getIsbn() {
	return isbn;
    }

    /**
     * @param isbn
     *            the isbn to set
     */
    public void setIsbn(long isbn) {
	this.isbn = isbn;
    }

    /**
     * @return the title
     */
    public String getTitle() {
	return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
	this.title = title;
    }
    
    /**
     * @return the publication
     */
    public String getPublication() {
	return publication;
    }

    /**
     * @param publication
     *            the publication to set
     */
    public void setPublication(String publication) {
	this.publication = publication;
    }
    
    
    /**
     * @return the language
     */
    public String getLanguage() {
	return language;
    }

    /**
     * @param language
     *            the title to set
     */
    public void setLanguage(String language) {
	this.language = language;
    }
    
    
    /**
     * @return the numpages
     */
    public int getNumpages() {
	return numpages;
    }

    /**
     * @param numpages
     *            the numpages to set
     */
    public void setNumpages(int numpages) {
	this.numpages = numpages;
    }
    
    
    /**
     * @return the status
     */
    public String getStatus() {
	return status;
    }

    /**
     * @param language
     *            the title to set
     */
    public void setStatus(String status) {
	this.status = status;
    }
   
    
    /**
     * @return the review
     */
    public List<Review> getReview() {
	return reviews;
    }

    /**
     * @param review
     *            the title to set
     */
    public void setReview(List<Review> reviews) {
	this.reviews = reviews;
    }
    
    public  Review getbookReview(int reviewid) {
    	return this.reviews.get(reviewid);
        }
    
    public Author[] getAuthors() {
		return authors;
	}

    
    public void setAuthors(Author[] authors) {
		this.authors = authors;
	}
    
    public  Author getbookAuthor(int authorid) {
    	return this.authors[authorid];
        }    
}
