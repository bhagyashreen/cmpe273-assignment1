package edu.sjsu.cmpe.library.domain;


public class Review {
	
	private int id;
	private int rating;
	private String comment;
		
	  
    /**
     * @return the id
     */
    public int getID() {
	return id;
    }

    /**
     * @param id
     *            the title to set
     */
    public void setID(int id) {
	this.id = id;
    }
	  
    /**
     * @return the rating
     */
	
    public int getRating() {
	return rating;
    }

    /**
     * @param rating
     *            the title to set
     */
    public void setRating(int rating) {
	this.rating = rating;
    }
	
    
    /**
     * @return the comment
     */
    public String getComment() {
	return comment;
    }

    /**
     * @param comment
     *            the title to set
     */
    public void setComment (String comment) {
	this.comment = comment;
    }
	
    
	
	

}
