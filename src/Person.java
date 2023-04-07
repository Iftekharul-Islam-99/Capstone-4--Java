/**
 * Contains all the contact info of the
 * customer, contractor and the architect.
 * 
 * @author Ifekharul Islam
 * @version 4.0, 25/09/2022
 */
public class Person {
	private String name;
	private String teleNum;
	private String email;
	private String address;
	
	/**
	 * Constructs the information of each individual.
	 * 
	 * @param name name of the person.
	 * @param teleNum telephone number of the person.
	 * @param email email address of the person.
	 * @param address address of the person.
	 */
	public Person(String name, String teleNum, String email, String address) {
		this.name = name;
		this.teleNum = teleNum;
		this.email = email;
		this.address = address;
	}
	
	/**
	 * Returns the name of this person object.
	 * 
	 * @return name of this person.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the telephone number.
	 * 
	 * @return the telephone number of current object.
	 */
	public String getTeleNum() {
		return teleNum;
	}
	
	/**
	 * Returns the address.
	 * 
	 * @return the address of current object.
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Returns the address.
	 * 
	 * @return the address of current object.
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * Sets the received string as the new name
	 * of this person.
	 * 
	 * @param newName new name of this person.
	 */
	public void setName(String newName) {
		name = newName;
	}
	
	/**
	 * Sets the received string as the new 
	 * telephone number of this person.
	 * 
	 * @param newTeleNum String that represents the telephone number.
	 */
	public void setTeleNum(String newTeleNum) {
		teleNum = newTeleNum;
	}
	
	/**
	 * Sets the received string as the new 
	 * email address of this person.
	 * 
	 * @param newEmail new email address of this person.
	 */
	public void setEmail(String newEmail) {
		email = newEmail;
	}
	
	/**
	 * Sets the received string as the new 
	 * address of this person.
	 * 
	 * @param newAddress new address of this person.
	 */
	public void setAddress(String newAddress) {
		address = newAddress;
	}
	
	/**
	 * Returns a formatted string representation of the 
	 * contact details of this person to write to the database.
	 * 
	 * @return formatted string representation of this object.
	 */
	public String fileFormat() {
		return String.format("'%s', '%s', '%s', '%s'", name, address, email, teleNum);
	}
	
	// String representation of all the attributes of the object.
	public String toString() {
		return "Customer name: " + name
				+ "\nTelephone No.: " + teleNum
				+ "\nEmail: " + email
				+ "\nAddress: " + address;
	}
	
}

