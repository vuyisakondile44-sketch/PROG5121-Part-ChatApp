/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.company.chatapppart1;

/**
 *
 * @author Student
 */
public class Login {

//The users first and last name are required for the welcoming message.
    private String firstName;
    private String lastName;
    
 
//Declaring variables that are going to used by the user.
    private String username;
    private String password;
    private String cellPhoneNumber;
//These are entered later when the user attemps to log in.
    private String enteredUsername;
    private String enteredPassword;
//Stores whether registration was successful.
    private boolean registered;
//Stores whether the most recent login attempt was successful.
    private boolean loginSuccessful; 
/*
*===========================================================
*CONSTRUCTOR
*===========================================================
*
*The constructor recieves the registration information
*when a new Login object is created
*/
public Login(String firstName,
             String lastName,
             String username,
             String password,
             String cellPhoneNumber) {
 
//"This" refers to the fields belonging to this object.
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
    this.cellPhoneNumber = cellPhoneNumber;


    //A new user has not yet been registered or logged in.
    this.registered = false;
    this.loginSuccessful = false;
    
}


//Username validation
public boolean checkUserName(){
    /*
    *checks if the username has
    *
    *checks if the username has
    *
    *checks if the username has
    *
    *&& means AND, so every condition must be true
    */
    return username != null
            && username.contains("_") 
            && username.length()<=5;
}
    
/**
 * ===================================================
 * Password validation
 * ===================================================
*/
 public boolean checkPasswordComplexity(String password){
     
     //A null password can't be valid.
     if (password == null) {
         return false;
     }
     
     //Checks the minimum length.
     boolean hasMinimumLength = password.length() >= 8;
     
     /*
     *These begin as false because we have not yet found
     *the required characters
     */
    boolean hasCapitalLetter = false;
    boolean hasNumber = false;
    boolean hasSpecialCharacter = false;
    
    /*
    *This loop examines every character in the password.
    *
    *
    */
    
    for (int i = 0; i < password.length(); i++){
        //Get one Character from the password
        char currentCharacter = password.charAt(i);
        
        //Checks whether the character is an uppercase letter.
        if (Character.isUpperCase(currentCharacter)){
            hasCapitalLetter = true;
        }
            
     /*
      *
      *isDigit() checks specifically for a number
      *
      *
     */
        if (Character.isDigit(currentCharacter)){
                hasNumber = true;
        }
    
        /*
        *is LetterOrDigit() is true for letters and numbers
        *
        *! means NOT
        *
        *Therefore this condition is true when the character
        *is either: ! @ # $ % * &
        */
        
        if (!Character.isLetterOrDigit(currentCharacter)){
            hasSpecialCharacter = true;
        }
        
    }
    
    // All four password requirements must be true
    return hasMinimumLength  
            && hasCapitalLetter
            && hasNumber 
            && hasSpecialCharacter;
 }
 
/*
*=============================================================
*checkCellPhoneNumber ()
*=============================================================
**Regex adapted from: Stack Overflow
*Author/Website:
*Page/Article: Validate South Africa Cell Phone Number
*URL:"https://stackoverflow.com/questions/4058001/validate-south-africa-cell-phone-number?newreg=fda3e9f51e5249cda908314ce64a5741"
*Accessed:18 September 2026
*/
 

 public boolean checkCellPhoneNumber(String phoneNumber){
     /*
     *Regex:
     *
     *^\\+27\\d{9}$
     *
     *^     = Beginning of the string
     *\\+   =literal + symbol
     *27    =South African international country code
     *\\d   =a digit from 0 to 9
     *{9}   =exactly 9 digits
     *$     =end of the String
     *
     *End that passes:
     *+27737915345
     */
     return phoneNumber != null
             && phoneNumber.matches("^\\+27\\d{9}$");
 }
 
/*
*=========================================================         
*RegisterUser()
*=========================================================
*
*This method calls the validation methods.
* 
*This method calls the validation methods.
*/
public String registerUser(){
		
		//String used to store all registration messages.
		String message = "";
		
		
		boolean userNameValid = checkUserName();
		boolean passwordValid = checkPasswordComplexity(this.password);
		boolean cellPhoneValid = checkCellPhoneNumber(this.cellPhoneNumber);
		
		//Check whether the username is correctly formatted.
		if (userNameValid){
			message += "Username successfully captured.\n";
		} else {
			message += "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.\n";
		}
		
		//Check whether the password meets the complexity requirements.
		if (passwordValid){
			message += "Password successfully captured.\n";
		} else {
			message += "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.\n";
		}
		
		//Check whether the cellphone number is correctly formatted.
		if (cellPhoneValid){
			message += "Cell phone number successfully added.\n";
		}else {
			message += "Cell phone number incorrectly formatted or does not contain international code.\n";
		}
		
		/*
		*Registration only counts as successful when every field passed
		*validation. This flag is what isRegistered() reports, and what
		*loginUser() checks before allowing a login attempt.
		*/
		this.registered = userNameValid && passwordValid && cellPhoneValid;
		
		//Return all the registration messages to the user.
		return message;
	}
/*
*==============================================================
*loginUser()
*==============================================================
*
*the login username and password must match the details
*stored
*
*/
public boolean loginUser(){
    /*
    *do not allow login if registration was unsuccessful.
    */
    if (!registered) {
    loginSuccessful = false;
    return false;
}
/*
*&These checks prevent NullPointerException    
*/

    if (enteredUsername == null ||  enteredPassword == null) {
        loginSuccessful = false;
        return false;
    }
    
    /*
    *
    *.equals() compares the CONTENT of Strings
    *
    *We use:
    *
    *username.equals(enteredUsername
    *
    *instead of:
    *
    *username == enteredUsername
    *
    *because == compares object references rather tha
    *the actual text stored inside the Strings
    *
    */
    loginSuccessful
            = username.equals(enteredUsername)
            && password.equals(enteredPassword);
    return loginSuccessful;
}
/*
*==============================================================
*returnLoginStatus()
*==============================================================
*
*
**/
  public String returnLoginStatus(){
    

    if (loginSuccessful){

        return "Welcome " + firstName + lastName + ", "
                + "it is great to see you again";
    } 
    
    //Failed Login
    return "username or password incorrect, please try again.";
    }
/*
 *======================================================================
 *SETTERS FOR LOGIN DETAILS
 *======================================================================
 *
 *These methods allow Main.java to store the username and
 *password entered during LOGIN without directly accessing
 *the private fields
 *
 *
 *
 */
  
public void setEnteredUsername (String enteredUsername) {
    this.enteredUsername = enteredUsername;
}

public void setEnteredPassword (String enteredPassword) {
    this.enteredPassword = enteredPassword;   
}

/*
*This helper method allows Main to determine whether the 
*registration succeded
*
*It avoids checking the text of a message to take decision.
*
*/
public boolean isRegistered() {
    return registered;
}

}