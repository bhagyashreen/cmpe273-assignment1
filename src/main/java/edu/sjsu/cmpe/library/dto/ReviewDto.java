package edu.sjsu.cmpe.library.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.sjsu.cmpe.library.domain.Review;

@JsonPropertyOrder(alphabetic = true)
public class ReviewDto extends LinksDto {
    private Review reviews;

    /**
     * @param review
     */
    
    public ReviewDto(){
    	
    }
    
    public ReviewDto(Review reviews) {
	super();
	this.reviews = reviews;
    }

    /**
     * @return the review
     */
    public Review getReview() {
	return reviews;
    }

    /**
     * @param review
     *            the review to set
     */
    public void setReview(Review reviews) {
	this.reviews = reviews;
    }
}
