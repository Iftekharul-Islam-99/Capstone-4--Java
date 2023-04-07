import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Contains static methods that are used
 * Throughout the program.
 * 
 * @author Ifekharul Islam
 * @version 4.0, 25/09/2022
 */
public class Checks {
	/**
	 * Contains a private constructor to make sure no objects are created
	 * of this class. 
	 */
	private Checks() {
	}
	
	/**
	 * Gets user input and checks if is of the right format and
	 * returns the formatted input.
	 * 
	 * @see Checks#formatString(String)
	 * @param input a scanner object.
	 * @return the building type (House, Apartment, Store).
	 */
 	public static String genBuildType(Scanner input) {
		String buildType;
		
		while (true) {
			System.out.println("Building type (House, Apartment or Store): ");
			buildType = input.nextLine().strip().toLowerCase();
			
			if (buildType.equals("house") || buildType.equals("apartment") || buildType.equals("store")){
				return Checks.formatString(buildType);
			}else {
				System.err.println("Incorrect building type. Try again.");
			}
		}
	}
	
 	/**
 	 * Gets a string that has the building type and returns
 	 * the fee depending on the building type.
 	 * 
 	 * @param buildType building type.
 	 * @return cost of the building.
 	 */
	public static int getFee(String buildType) {
		int fee = 0;
		
		if (buildType.equalsIgnoreCase("house")) {
			fee = 2000;
		}
		if (buildType.equalsIgnoreCase("apartment")) {
			fee = 1000;
		}
		if (buildType.equalsIgnoreCase("store")){
			fee = 3000;
		}
		
		return fee;
	}
	
	/**
	 * Gets input form the user until an appropriate value is
	 * entered. value is then converted to integer and sent back.
	 * 
	 * @param subject String that is to be printed.
	 * @param fee total cost of the project.
	 * @param input scanner object to get user input.
	 * @return the amount paid to date.
	 */
	public static int checkPayment(String subject, int fee, Scanner input) {
		int paidAmount;
		
		while (true) {
			System.out.println("Fee of the project: " + fee);
			System.out.println(subject + ": ");
			String temp = input.nextLine().strip();
			
			try {
				paidAmount = Integer.parseInt(temp);
				
				if (paidAmount <= fee  && paidAmount >= 0) {
					return paidAmount;
				}else {
					System.err.println("Incorrect value. Try again.");
				}
			}
			catch (NumberFormatException e) {
				System.err.println("Please enter a valid integer.");
			}
		}
		
	}
	
	/**
	 * Takes user input until a valid telephone number is entered.
	 * Returns the checked number.
	 * @param input Scanner to take user input.
	 * @return checked telephone number.
	 */
	public static String checkTeleNo(Scanner input) {
		String teleNum;
		
		while (true) {
			System.out.print("Telephone No.:\n07");
			teleNum = input.nextLine().strip();
			
			try {
				Integer.parseInt(teleNum);
			}
			catch (NumberFormatException e) {
				System.err.println("That is not a valid number."
								    + " Please try again.");
			}
			
			if (teleNum.length() != 9) {
				System.err.println("That is not a valid length." 
						   		    + " Please try again.");
			}else {
				return "07" + teleNum;
			}
		}
	}
	
	/**
	 * Takes user input until a valid email address is entered.
	 * Returns the checked email address.
	 * 
	 * @param input Scanner to take user input.
	 * @return checked email address.
	 */
	public static String checkEmail(Scanner input) {
		String email;
		
		while (true) {
			System.out.println("Email: ");
			email = input.nextLine().strip();
			String[] checkMail = email.split("\\.");
			
			if (! email.contains("@") || email.indexOf('@') == 0){
				System.err.println("That is not a valid format." 
				   		   		    + " Please try again.");
			}else if (! checkMail[checkMail.length - 1].equals("com")) {
				System.err.println("We only allow '.com' as the top-level domain." 
								    + " Please try again.");
			}else if (checkMail[0].endsWith("@")){
				System.err.println("Invalid domain. Please try again.");
			}else {
				return email;
			}		
		}
	}
	
	/**
	 * Takes user input and checks if the input is empty or not.
	 * @param subject string that needs to be printed.
	 * @param input Scanner object to take user inputs.
	 * @return the non empty string value.
	 */
	public static String checkEmpty(String subject, Scanner input) {
		String output;
		
		while (true) {
			System.out.println(subject + ": ");
			output = input.nextLine().strip();
			
			if (! output.isEmpty()) {
				return output;
			}else {
				System.err.println(subject + "cannot be empty. Try again.");
			}
		}
	}
	
	/**
	 * Asks users to enter a date until a valid date format is entered.
	 * This input is then formatted into a proper date format and returned.
	 * 
	 * @param subject string to be printed.
	 * @param input Scanner object to take user inputs.
	 * @return the formatted date.
	 */
	public static Date formatDate(String subject, Scanner input) {
		String date;
		
		while (true) {
			System.out.println(subject + ": ");
			date = input.nextLine().strip();
			
			try {
				LocalDate localDate = LocalDate.parse(date);
				return java.sql.Date.valueOf(localDate);
			}
			catch (DateTimeParseException e) {
				System.err.println("Incorrect format. try in the format: 2022-12-12");
			}
		}
	}
	
	/**
	 * Takes a date object and returns the string version of the date.
	 * 
	 * @param date received date object.
	 * @return formatted string version of the date.
	 */
	public static String stringDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}
	
	
	/**
	 * Returns the formatted Date object of todays date.
	 * 
	 * @return Todays date in SQL date format.
	 */
	public static Date getDate() {
		return java.sql.Date.valueOf(LocalDate.now());
	}
	
	/**
	 * Takes input until a proper first name and surname is entered.
	 * The name is formatted and returned.
	 * <p>
	 * Checks if the input is empty.
	 * <br>
	 * Checks if both first name and surname is entered.
	 * <br>
	 * Checks to make sure no middle name or anything else 
	 * is not entered.
	 * 
	 * @see Checks#charCount(String, int, char)
	 * @see Checks#formatName(String)
	 * @param subject string to be printed.
	 * @param input Scanner object to take user inputs.
	 * @return the formatted name.
	 */
	public static String checkName(String subject, Scanner input) {
		String name;
		
		while (true) {
			System.out.println(subject + ": ");
			name = input.nextLine().strip();
			
			if (name.isEmpty()) {
				System.err.println("Name cannot be empty. Try again.");
			}else if (name.contains(" ")){
				if (charCount(name, name.indexOf(" "), ' ') > 1) {
					System.err.println("Please only enter your firstname and surname");
				}else {
					return formatName(name);
				}
			}else {
				System.err.println("Please enter both your firstname and surname");
			}
		}
	}
	
	/**
	 * Counts the number of non leading of tailing '/s' characters
	 * using recursion.
	 * 
	 * @param name string value that is being checked.
	 * @param index index of the '/s' character in 'name'.
	 * @param checkChar character to count in 'name'.
	 * @return the total number of occurrence of the character '/s' in string 'name'
	 */
	public static int charCount(String name, int index, char checkChar) {
		if (index >= name.length()) {
			return 0;
		}
		
		int count = 0;
		if(name.charAt(index) == checkChar) {
			count ++;
		}
		return count + charCount(name, index + 1, checkChar);
	}
	
	/**
	 * Formats a name so that the leading letter of the first name and surname
	 * are both capital.
	 * @param name name of the person.
	 * @return formatted name.
	 */
	public static String formatName(String name) {
		String[] fullName =  name.split(" ");
		String firstName = fullName[0];
		String surName = fullName[1];
		
		if (firstName.length() > 1) {
			firstName = firstName.substring(0,1).toUpperCase() + firstName.substring(1).toLowerCase();
		}else {
			firstName = firstName.toUpperCase();
		}
		if (surName.length() > 1) {
			surName = surName.substring(0,1).toUpperCase() + surName.substring(1).toLowerCase();
		}else {
			surName = surName.toUpperCase();
		}
		
		return firstName + " " + surName;
	}
	
	/**
	 * Formats a string to have the first letter capitalised.
	 * 
	 * @param string sent string.
	 * @return formatted string.
	 */
	public static String formatString(String string) {
		if (! Character.isDigit(string.charAt(0))) {
			return string.substring(0,1).toUpperCase() + string.substring(1).toLowerCase();
		}else {
			return string;
		}
	}
}
