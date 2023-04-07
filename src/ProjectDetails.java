import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Reads and writes to files and creates lists of objects
 * using the data.
 * <p>
 * Reading from files:
 * <br>
 * reads from files "All projects.txt" and generates a list of objects
 * under the 'Project' class.
 * <p>
 * Writing to files:
 * <br>
 * Takes all the information stored in the list and writes all the information
 * to "All projects.txt", "Completed projects.txt", "customerContacts.txt", "contractorContacts.txt"
 * and the "architectContacts.txt" files.
 * 
 * @author Ifekharul Islam
 * @version 4.0, 25/09/2022
 */
public class ProjectDetails {
	private ArrayList<Project> projList = new ArrayList<>();
	private ArrayList<Person> contactList;

	
	/**
	 * Constructs 3 list of 'Person' objects.
	 * <p>
	 * The 3 lists are separated into the customer,
	 * contractor and the architect.
	 * 
	 * @see Person
	 * @param contactList list of objects that holds details of all individuals.
	 */
	public ProjectDetails(ArrayList<Person> contactList) {
		this.contactList = contactList;
	}
	
	/**
	 * Returns the list of generated Project object.
	 * 
	 * @return the list of Project objects.
	 */
	public ArrayList<Project> getProjList(){
		return projList;
	}
	
	/**
	 * Adds received objects to the projList.
	 * 
	 * @param newProj new object to be added to the list.
	 */
	public void addToList(Project newProj) {
		projList.add(newProj);
	}
	
	/**
	 * Reads from the 'poisedpms' database, queries the table 'proj_data'
	 * with 'proj_fee', 'finalised_projs' and 'proj_contacts' joined together.
	 * and uses the content to create objects of 'Project' and appends to
	 * an arrayList.
	 * 
	 * @see Project
	 * @see ProjectDetails#getObject(ArrayList, String)
	 */
	public void setProjList() {
		try {
			Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/poisedpms?useSSL=false",
                    "otheruser",
                    "swordfish"
                    );
			
			Statement statement = connection.createStatement();
			
			ResultSet results = statement.executeQuery("SELECT * from proj_data  NATURAL JOIN proj_fee "
					+ "LEFT JOIN finalised_projs ON proj_data.Proj_No = finalised_projs.Proj_No"
					+ " LEFT JOIN proj_contacts On proj_data.Proj_No = proj_contacts.Proj_No");
			
			while (results.next()) {
				Person customer, contractor, architect;
				
				customer = getObject(contactList, results.getString("Customer"));
				contractor = getObject(contactList, results.getString("Contractor"));
				architect = getObject(contactList, results.getString("Architect"));
				
				Project obj = new Project(results.getString("Proj_name"), results.getString("Proj_No")
						,results.getString("Proj_address"), results.getString("ERF_No"),  results.getString("Building_type")
						,  results.getInt("fee"),  results.getInt("Paid_to_date"),  results.getDate("Deadline")
						, customer, contractor, architect);
				
				if (results.getString("Completion_state").equalsIgnoreCase("Yes")) {
					obj.setFinalised(true);
					obj.setDateCompleted(results.getDate("Completion_date"));
				}
				
				projList.add(obj);
			}
			
			connection.close();
			results.close();
			statement.close();
		}catch (SQLException e) {
            // We only want to catch a SQLException - anything else is off-limits for now.
            e.printStackTrace();
		}
	}
	
	/**
	 * Takes a list of Person objects and compares each elements
	 * name to a sent string. Returns the object that matches the string.
	 * 
	 * @see Person
	 * @see Person#getName()
	 * @param objList List of 'Person' objects.
	 * @param name string to compare.
	 * @return object that matches the string value of 'name' or null in not matched.
	 */
	public Person getObject(ArrayList<Person> objList, String name) {
		
		for (Person element : objList) {
			if (element.getName().equalsIgnoreCase(name)) {
				return element;
			}
		}
		return null;
	}
}
