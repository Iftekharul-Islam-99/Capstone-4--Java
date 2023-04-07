import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Date;

/**
 * Creates objects under different classes to generate a database
 * to access data from and modify the data. 
 * <p>
 * Create objects under 'Contacts' for customer, contractor and architect
 * by reading from the database.
 * <br>
 * These object's lists are then sent to the 'ProjectDetails' class to crate
 * an  object under it. This essentially our database to work with.
 * <p>
 * We generate a menu that lets the user choose from:
 * <br>
 * Add a new project.
 * <br>
 * Update a chosen project.
 * <br>
 * View all uncompleted projects.
 * <br>
 * View all overdue projects.
 * <br>
 * Search for a project using its name or number.
 * <br>
 * Quit/terminate the program.
 * 
 *@author Ifekharul Islam
 *@version 4.0, 25/09/2022
 */
public class Poised {
	/**
	 * Executes the the relevant code.
	 * 
	 * @param args contains all the base executions.
	 */
	public static void main(String[] args) {
		Contacts allContacts;
		ProjectDetails readProj;
		
		// Creating objects under Contacts and generating and getting access to its attributes and methods.
		// setContactList() generates a list of 'Person' objects.
		allContacts = new Contacts();
		allContacts.setContactList();
		
		
		// Creating an object under 'ProjectDetails' and generating a list of 'Project' objects.
		readProj = new ProjectDetails(allContacts.getContactList());
		readProj.setProjList();
		
		Scanner user = new Scanner(System.in);
		while (true) {
			System.out.println("""
					Choose from the options below
					a  - Add a new project.
					u  - Update a projects details.
					vu - View all uncompleted projects.
					vo - View all overdue projects.
					s  - Search for a project
					q  - Quit.
					""");
			
			String choice = user.nextLine().strip().toLowerCase();
			
			if (choice.equals("a")) {
				addProj(readProj, user, allContacts);
			}else if (choice.equals("u")) {
				updateProj(readProj, user);
			}else if (choice.equals("vu")) {
				viewUncompleted(readProj);
			}else if (choice.equals("vo")) {
				viewOverdue(readProj);
			}else if (choice.equals("s")) {
				search(readProj, user);
			// Accessing methods under 'readProj' object to write to files.
			} else if (choice.equals("q")) {
				break;
			}else {
				System.err.println("Incorrect choice. Try again.");
			}
		}
		user.close();
	}
	
	
	/**
	 * Prints a list of uncompleted projects.
	 * 
	 * @see ProjectDetails
	 * @see ProjectDetails#getProjList()
	 * @see Project#getProjName()
	 * @see Project#getProjNum()
	 * @see Project#getFinalised()
	 * 
	 * @param readProj object under 'ProjectDetails'.
	 */
	public static void viewUncompleted(ProjectDetails readProj) {
		for (Project objects : readProj.getProjList()) {
			if (! objects.getFinalised()) {
				System.out.println( objects.getProjName() 
					+ "\n" + objects.getProjNum() + "\n");
			}
		}
	}
	
	/**
	 * Prints a list of overdue projects.
	 * 
	 * @see ProjectDetails
	 * @see ProjectDetails#getProjList()
	 * @see Project#getProjName()
	 * @see Project#getProjNum()
	 * @see Project#getDeadline()
	 * @param readProj readProj object under 'ProjectDetails'.
	 */
	public static void viewOverdue(ProjectDetails readProj) {
		for (Project objects : readProj.getProjList()) {
			if (objects.getDeadline().compareTo(new Date()) < 0) {
				System.out.println(objects.getProjName() 
						+ "\n" + objects.getProjNum() + "\n");
			}
		}
	}
	
	/**
	 * Lets the user search and edit for a project using a project name
	 * or project number.
	 * <p>
	 * If the searched project is matched with one in the database
	 * the user is then shown a menu that allows them to modify the
	 * project in different ways.
	 * 
	 * @see ProjectDetails
	 * @see ProjectDetails#getProjList()
	 * @see Project#getProjName()
	 * @see Project#getProjNum()
	 * @see Menu
	 * @see Menu#editProj(Scanner)
	 * @param readProj readProj object under 'ProjectDetails'.
	 * @param user scanner object to get user input.
	 */
	public static void search(ProjectDetails readProj, Scanner user) {
		int count = 0;
		
		System.out.println("Search by entering the project name or the project No.");
		String search = user.nextLine().strip();

		for (Project objects : readProj.getProjList()) {
			count++;
			
			if (objects.getProjName().equalsIgnoreCase(search) 
				|| objects.getProjNum().equalsIgnoreCase(search)) {
				System.out.println(objects);
				Menu genMenu = new Menu(objects);
				genMenu.editProj(user);
				
				break;
			}else if (count == readProj.getProjList().size()) {
				System.err.println("Project not found.");
			}
		}
	}
	
	/**
	 * User chooses and edits a project 
	 * from a list of all projects in the system.
	 * <p>
	 * User is displayed all projects in the system.
	 * User can choose from the list or enter -1 to exit to
	 * main menu.
	 * <br>
	 * When a correct project is chosen user is then shown a menu
	 * to edit that project.
	 * 
	 * @see Menu
	 * @see Menu#editProj(Scanner)
	 * @see ProjectDetails
	 * @see ProjectDetails#getProjList()
	 * @see Project#getProjName()
	 * @see Project#getProjNum()
	 * @param readProj readProj object under 'ProjectDetails'.
	 * @param user scanner object to get user input.
	 */
	public static void updateProj(ProjectDetails readProj, Scanner user) {
		int projNum = 0;
		int checkChoice = 0;
		
		System.out.println("Choose from the list below or enter -1 to return to main menu.");
		
		for (Project objects : readProj.getProjList()) {
			projNum ++;
			System.out.println(projNum + "\n" + objects.getProjName() 
				+ "\n" + objects.getProjNum() + "\n");
		}
		
		while (checkChoice != -1) {
			String numChoice = user.nextLine().strip();
			
			try {
				checkChoice = Integer.parseInt(numChoice);
				
				if ( ! (checkChoice < 1 || checkChoice > readProj.getProjList().size())) {
					
					System.out.println(readProj.getProjList().get(checkChoice - 1));
					Menu genMenu = new Menu(readProj.getProjList().get(checkChoice - 1));
					genMenu.editProj(user);
					
					break;
				}else if (checkChoice == -1){
					System.out.println("Returning to main menu...");
				}else {
					System.err.println("That is not one of the options. Try again.");
				}
			}
			catch (NumberFormatException e) {
				System.err.println("Invalid input. Try again.");
			}
		}
	}
	
	/**
	 * A 'Project' object is created from user inputed data.
	 * <p>
	 * User is prompted to enter details of each individual 
	 * (customer,contractor and architect) involved in the project.
	 * <br>
	 * User is then required to enter all the details for the project.
	 * 
	 * @see Person
	 * @see Person#getName()
	 * @see Contacts
	 * @see Contacts#addToContactList(Person)
	 * @see ProjectDetails
	 * @see ProjectDetails#addToList(Project)
	 * @see Checks
	 * @see Checks#checkEmail(Scanner)
	 * @see Checks#checkTeleNo(Scanner)
	 * @see Checks#checkName(String, Scanner)
	 * @see Checks#checkEmpty(String, Scanner)
	 * @see Checks#genBuildType(Scanner)
	 * @see Checks#getFee(String)
	 * @see Checks#checkPayment(String, int, Scanner)
	 * @see Checks#formatDate(String, Scanner)
	 * @see Poised#setProjName(String, String, String)
	 * @param readProj readProj object under 'ProjectDetails'.
	 * @param input scanner object to get user input.
	 * @param allContacts 'Contacts' object of all contacts.
	 */
	public static void addProj(ProjectDetails readProj, Scanner input
			, Contacts allContacts) {
		final String[] names = {"Customer", "Contractor", "Architect"}; // Array containing 3 titles.
		Person[] obj = new Person[3]; // Creating an array with length 3 of objects of 'Person' class.
		String name, teleNum, email, address;
		String projName, projNum, projAddress, buildType, erf;
		Date deadline;
		int paidAmount, fee;
		
		System.out.println();
		
		// The loop gets 3 sets of info for each title in the names array.
		// After the info is gathered from user 3 objects are created under 'Person'.
		for (int count = 0; count < 3; count++) {
			System.out.println("Enter the details of the " + names[count]);
			
			name = Checks.checkName("Firstname and surname", input);
			teleNum = Checks.checkTeleNo(input);
			email = Checks.checkEmail(input);
			address = Checks.checkEmpty("Address", input);
			obj[count] = new Person(name, teleNum, email, address);
		}
		
		allContacts.addToContactList(obj[0]);
		allContacts.addToContactList(obj[1]);
		allContacts.addToContactList(obj[2]);
		
		try {
			 Connection connection = DriverManager.getConnection(
	                    "jdbc:mysql://localhost:3306/poisedpms?useSSL=false",
	                    "otheruser",
	                    "swordfish"
	                    );
			 
			 Statement statement = connection.createStatement();
			 
			 statement.executeUpdate("INSERT INTO contacts VALUES (" + obj[0].fileFormat() + ")");
			 statement.executeUpdate("INSERT INTO contacts VALUES (" + obj[1].fileFormat() + ")");
			 statement.executeUpdate("INSERT INTO contacts VALUES (" + obj[2].fileFormat() + ")");
			 
			 statement.close();
			 connection.close();
		 }catch (SQLException e) {
	            // We only want to catch a SQLException - anything else is off-limits for now.
	            e.printStackTrace();
		 }

		// This gets info for the project from user.
		System.out.println("\nEnter the project details:\n");
		System.out.println("Project name:");
		projName = input.nextLine().strip();
		
		while (true) {
			projNum = Checks.checkEmpty("Project number", input);
			if (projNum.length() < 5 ) {
				break;
			}else {
				System.out.println("Project Number cannot be larger than 5 characters."
						+ "Enter in the format 'a1234'.");
			}
		}
		
		projAddress = Checks.checkEmpty("Site Address", input);
		erf = Checks.checkEmpty("ERF number", input);
		buildType = Checks.genBuildType(input);
		fee = Checks.getFee(buildType);
		paidAmount = Checks.checkPayment("Paid to date", fee, input);
		deadline = Checks.formatDate("Enter due date (dd/mm/yyyy)", input);

		projName = setProjName(projName, obj[0].getName(), buildType);
		// Creating an object under the class 'Project'.
		// The object contains all the info gathered from the user.
		// Also contains the 3 objects under 'Person' and all its public info.
		Project newProj =  new Project(projName, projNum, projAddress
				, erf, buildType, fee, paidAmount,deadline , obj[0], obj[1], obj[2]);
		readProj.addToList(newProj);
		
		try {
			 Connection connection = DriverManager.getConnection(
	                    "jdbc:mysql://localhost:3306/poisedpms?useSSL=false",
	                    "otheruser",
	                    "swordfish"
	                    );
			 
			 Statement statement = connection.createStatement();
			 
			 statement.executeUpdate("INSERT INTO proj_data VALUES (" + newProj.fileFormat() + ")");
			 statement.executeUpdate("INSERT INTO proj_contacts VALUES (" + "'" + newProj.getProjNum() + "', '" 
					 				 + newProj.getCustomer().getName() + "', '" + newProj.getContractor().getName() 
					 				 + "', '" + newProj.getArchitect().getName() + "')");
			 
			 statement.close();
			 connection.close();
		 }catch (SQLException e) {
	            // We only want to catch a SQLException - anything else is off-limits for now.
	            e.printStackTrace();
      }

	}
	
	/**
	 * If project name is not given by user this sets it to the
	 * building type and the surname of customer combined.
	 * 
	 * @see Checks
	 * @see Checks#formatString(String)
	 * @param projName string containing name of the project.
	 * @param name name of the customer.
	 * @param buildType building type.
	 * @return combination of building type and surname or the original project name.
	 */
	public static String setProjName(String projName, String name, String buildType) {
		if (projName.isEmpty()) {
			String[] fullName = name.split(" ");
			
			return buildType + " " + fullName[1];
		}else {
			return Checks.formatString(projName);
		}
	}
	
}