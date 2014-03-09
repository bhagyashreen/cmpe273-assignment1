package edu.sjsu.cmpe.library.domain;

import org.hibernate.validator.constraints.NotEmpty;


public class Author {
	
	public int id;
	@NotEmpty
	private String name;
		
	  
    /**
     * @return the id
     */
    public int getID() {
	return id;
    }

    /**
     * 
     */
    public void setID(int id) {
	this.id = id;
    }
	  
    	
    
    /**
     * @return the name
     */
    public String getName() {
	return name;
    }

    /**
     * @param name
     *            the title to set
     */
    public void setName (String name) {
	this.name = name;
    }
	
    
	
	

}
