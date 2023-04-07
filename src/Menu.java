import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

/**
 * Saves information of the sent project object and
 * generated a menu. The menu gives options to
 * modify the sent project in different ways.
 * 
 * 
 * @author Ifekharul Islam
 * @version 4.0, 25/09/2022
 */
public class Menu {
	private Project proj;
	
	/**
	 * Constructor thats saves the project object.
	 * 
	 * @see Project
	 * @param proj object of class 'Project'.
	 */
	public Menu(Project proj) {
		this.proj = proj;
	}
	
	/**
	 * generated a menu that lest the user to modify aspects of
	 * the project.
	 * <p>
	 * User is given options to:
	 * <br>
	 * Update the deadline.
	 * <br>
	 * Update the contractors details.
	 * <br>
	 * Update the paid amount.
	 * <br>
	 * Finalise the project.
	 * <br>
	 * Exit the menu.
	 * 
	 * @see Project#getContractor()
	 * @see Person#setAddress(String)
	 * @see Person#setEmail(String)
	 * @see Person#setName(String)
	 * @see Person#setTeleNum(String)
	 * @see Project#setDeadline(Date)
	 * @see Project#getFee()
	 * @see Project#getPaidAmount()
	 * @see Project#setPaidAmount(int)
	 * @see Project#setFinalised(boolean)
	 * @see Project#setDateCompleted(Date)
	 * @see Checks#checkEmail(Scanner)
	 * @see Checks#checkName(String, Scanner)
	 * @see Checks#checkPayment(String, int, Scanner)
	 * @see Checks#checkEmpty(String, Scanner)
	 * @see Checks#checkTeleNo(Scanner)
	 * @see Checks#formatDate(String, Scanner)
	 * @see Checks#stringDate(Date)
	 * @param input2 scanner object to take user input. 
	 */
	public void editProj(Scanner input2) {
		while (true){
			System.out.println("""
					
					Choose from the menu below.
					ud - Update due date.
					uc - Update contractors details.
					up - Update total amount paid to date.
					f - Finalise the project.
					e - Exit
					""");
			
			String menu = input2.nextLine().strip().toLowerCase();
			
			// This updates the date with new user inputed date using setters in the class.
			if (menu.equals("ud")) {
				System.out.println("Current deadline: " + Checks.stringDate(proj.getDeadline()));
				System.out.println();
				Date newDeadline = Checks.formatDate("Enter new due date (yyyy-MM-dd)", input2);
				proj.setDeadline(newDeadline);
				System.out.println("New deadline set to " + newDeadline);
				
				try {
					 Connection connection = DriverManager.getConnection(
			                    "jdbc:mysql://localhost:3306/poisedpms?useSSL=false",
			                    "otheruser",
			                    "swordfish"
			                    );
					 
					 Statement statement = connection.createStatement();
					 
					 statement.executeUpdate(
							 "UPDATE proj_data SET Deadline = " + "'" + newDeadline + "'" + " WHERE Proj_No =" + "'" + proj.getProjNum() + "'");
					 
					 statement.close();
					 connection.close();
				 }catch (SQLException e) {
			            // We only want to catch a SQLException - anything else is off-limits for now.
			            e.printStackTrace();
				 }
			
			// This updates the details of the contractor with new user inputed data using setters in the class.
			// We first access the object under 'Person' but using a getter in the 'project' class.
			// Then we can access the setter to set the new value.
			}else if (menu.equals("uc")) {
				System.out.println("Enter new details for " + proj.getContractor().getName());
				
				String newTeleNum = Checks.checkTeleNo(input2);
				proj.getContractor().setTeleNum(newTeleNum);
				
				String newEmail = Checks.checkEmail(input2);
				proj.getContractor().setEmail(newEmail);
				
				String newAddress = Checks.checkEmpty("Address", input2);
				proj.getContractor().setAddress(newAddress);
				
				System.out.println("Details updated.");
				
				try {
					 Connection connection = DriverManager.getConnection(
			                    "jdbc:mysql://localhost:3306/poisedpms?useSSL=false",
			                    "otheruser",
			                    "swordfish"
			                    );
					 
					 Statement statement = connection.createStatement();
					 
					 statement.executeUpdate(
							 "UPDATE contacts SET Address = '" + newAddress 
							 + "' ,Email = '" + newEmail + "' ,Telephone = '" + newTeleNum + "'" + "WHERE Name =" + "'" + proj.getContractor().getName() + "'");
					 
					 statement.close();
					 connection.close();
				 }catch (SQLException e) {
			            // We only want to catch a SQLException - anything else is off-limits for now.
			            e.printStackTrace();
				 }
				
			// This sets the new paid amount.
			}else if (menu.equals("up")) {
				String text = "Total fee of the project: " 
								+ proj.getFee()
								+ "\nCurrent amount paid to date: "
								+ proj.getPaidAmount()
								+ "\nEnter new amount";
				int newAmount = Checks.checkPayment(text, proj.getFee(), input2);
				proj.setPaidAmount(newAmount);
				
				
				try {
					 Connection connection = DriverManager.getConnection(
			                    "jdbc:mysql://localhost:3306/poisedpms?useSSL=false",
			                    "otheruser",
			                    "swordfish"
			                    );
					 
					 Statement statement = connection.createStatement();
					 
					 statement.executeUpdate(
							 "UPDATE proj_data SET Paid_to_date =" + newAmount + " WHERE Proj_No =" + "'" + proj.getProjNum() + "'");
					 
					 statement.close();
					 connection.close();
				 }catch (SQLException e) {
			            // We only want to catch a SQLException - anything else is off-limits for now.
			            e.printStackTrace();
				 }
				
			// This finalises the project if the total fee is paid.
			// Also sets the current date if the project is finalised and displays all the info.
			}else if (menu.equals("f")) {
				if (proj.getFinalised()) {
					System.out.println("Project already finalised.");
				}else if (proj.getPaidAmount() == proj.getFee()) {
					proj.setFinalised(true);
					proj.setDateCompleted(Checks.getDate());
					
					System.out.println("Project finalised.");
					System.out.println("\nCompletion date: " + proj.getDateCompleted() );
					
					updateFinalisedDB();
				}else {
					proj.setFinalised(true);
					proj.setDateCompleted(Checks.getDate());
					System.out.println(proj + "\nCompletion date: " + proj.getDateCompleted() );
					
					updateFinalisedDB();
				}
			// Exits the menu.
			}else if (menu.equals("e")) {
				break;
			}else {
				System.err.println("Incorrect choice. Try again.");
			}
		}
	}
	
	/**
	 * Updates the 'finalised_projs' table in the database with new data.
	 */
	public void updateFinalisedDB() {
		try {
			 Connection connection = DriverManager.getConnection(
	                    "jdbc:mysql://localhost:3306/poisedpms?useSSL=false",
	                    "otheruser",
	                    "swordfish"
	                    );
			 
			 Statement statement = connection.createStatement();
			 
			 statement.executeUpdate(
					 "UPDATE proj_data SET Completion_state = 'Yes' WHERE Proj_No =" + "'" + proj.getProjNum() + "'");
			 statement.executeUpdate(
					 "INSERT INTO finalised_projs VALUES (" + "'" + proj.getProjNum() + "', '" + proj.getDateCompleted() + "')");
			 
			 statement.close();
			 connection.close();
		 }catch (SQLException e) {
	            // We only want to catch a SQLException - anything else is off-limits for now.
	            e.printStackTrace();
		 }
	}
}
