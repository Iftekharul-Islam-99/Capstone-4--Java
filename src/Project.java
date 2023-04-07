import java.util.Date;
/**
 * Stores all data for a project.
 * Data is read from a file or read from the user
 * 
 * @author Ifekharul Islam
 * @version 4.0, 25/09/2022
 */
public class Project {
	// All the attributes for each object.
	// Set up a few variables and methods ahead of time.
	private String projName;
	private String projNum;
	private String address;
	private String erf;
	private int paidAmount;
	private int fee;
	private Date deadline;
	private boolean finalised = false;
	private Person customer, contractor, architect;
	private String buildType;
	private Date dateCompleted;
	
	/**
	 * This constructs a project with a name, number, address,
	 * ERF number, type of building, fee, amount paid, deadline,
	 * details of the customer, architect and the contractor.
	 * 
	 * @param projName Name of project.
	 * @param projNum Project number.
	 * @param address Site address.
	 * @param erf ERF number.
	 * @param buildType Building type (House, Apartment, Store).
	 * @param fee Total cost of project.
	 * @param paidAmount Current amount paid by customer.
	 * @param deadline Due date of the project.
	 * @param customer All information on the customer.
	 * @param contractor All information on the contractor.
	 * @param architect All information on the architect.
	 */
	public Project(String projName, String projNum, String address
			, String erf, String buildType, int fee, int paidAmount
			, Date deadline, Person customer,
			Person contractor, Person architect) {
		this.projName = projName;
		this.projNum = projNum;
		this.address = address;
		this.erf = erf;
		this.paidAmount = paidAmount;
		this.deadline = deadline;
		this.customer = customer;
		this.contractor = contractor;
		this.architect = architect;
		this.buildType = buildType;
		this.fee = fee;
	}
	
	/**
	 * This returns the access to the details of the 
	 * current customer assigned to the project.
	 * 
	 * @return This project's customer object.
	 */
	public Person getCustomer() {
		return customer;
	}
	
	/**
	 * This returns the access to the details of the 
	 * current contractor assigned to the project.
	 * 
	 * @return This project's contractor object.
	 */
	public Person getContractor() {
		return contractor;
	}
	
	/**
	 * This returns the access to the details of the 
	 * current architect assigned to the project.
	 * 
	 * @return This project's architect object.
	 */
	public Person getArchitect() {
		return architect;
	}
	
	/**
	 * This returns the current amount paid for this project.
	 * 
	 * @return This project's current deposit.
	 */
	public int getPaidAmount() {
		return paidAmount;
	}
	
	/**
	 * This returns the total cost of this project.
	 * 
	 * @return This project's current fee.
	 */
	public int getFee() {
		return fee;
	}
	
	/**
	 * This returns a true or false value depending
	 * on whether or not this project is completed
	 * or not.
	 * 
	 * @return This project's completion state.
	 */
	public boolean getFinalised() {
		return finalised;
	}
	
	/**
	 * This returns the name of this project.
	 * 
	 * @return This project's name.
	 */
	public String getProjName() {
		return projName;
	}
	
	/**
	 * This returns the number of this project.
	 * 
	 * @return This project's number.
	 */
	public String getProjNum() {
		return projNum;
	}
	
	/**
	 * This returns the due date of this project.
	 * 
	 * @return This project's deadline.
	 */
	public Date getDeadline() {
		return deadline;
	}
	
	/**
	 * This returns the completion date of this project.
	 * 
	 * @return This project's completion date.
	 */
	public Date getDateCompleted() {
		return dateCompleted;
	}
	
	/**
	 * Receives a new name and sets it to this projects name.
	 * @param newName new project name.
	 */
	public void setName(String newName) {
		projName = newName;
	}
	
	/**
	 * Sets a received amount as the new paid amount for
	 * this project.
	 * 
	 * @param newAmount value of the new amount.
	 */
	public void setPaidAmount(int newAmount) {
		paidAmount = newAmount;
	}
	
	/**
	 * Sets a received date as the new deadline for
	 * this project.
	 * 
	 * @param newdate value of the new date.
	 */
	public void setDeadline(Date newdate) {
		deadline = newdate;
	}
	
	/**
	 * Sets a received condition as the completion state 
	 * for this project.
	 * 
	 * @param condi value of the new state.
	 */
	public void setFinalised(boolean condi) {
		this.finalised = condi;
	}
	
	/**
	 * Sets a received date as the completion date
	 * for this project.
	 * 
	 * @param compDate value of the completion date.
	 */
	public void setDateCompleted(Date compDate) {
		dateCompleted = compDate;
	}
	
	/**
	 * Returns the amount left to pay for this project.
	 * 
	 * @return difference between fee and paidAmount.
	 */
	public int unpaid() {
		return fee - paidAmount;
	}
	
	/**
	 * Returns a formatted string representation of the project.
	 * 
	 * @return formatted string representation of this object.
	 */
	public String fileFormat() {
			return  String.format("'%s', '%s', '%s', '%s', '%s', %d, '%tF', '%s'a",
					projName, projNum, address, erf, buildType, paidAmount, 
					deadline, genCompState());
	}
	
	/**
	 * Returns strings yes or no depending on
	 * this projects completion state.
	 * 
	 * @return strings yes or no.
	 */
	public String genCompState() {
		if (finalised) {
			return "Yes";
		}else {
			return "No";
		}
	}
	
	// String representation of the relevant attributes of the objects in 'Project' and
	// the 'toString' of the customer object in 'Person'.
	public String toString() {
		return "\nProject name: " + projName
				+ "\nProject No.: " + projNum
				+ "\nERF No.: " + erf
				+ "\nBuilding type: " + buildType
				+ "\nSite address: " + address
				+ "\n" + customer
				+ "\nAmount left to pay: "
				+ this.unpaid();
	}
	
	
}
