/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.company.chatapppart1;

import java.util.Scanner;



/**
 * The Main class runs the ChatApp application.
 * It accepts keyboard input from the user and controls
 * the registration and login process.
 * 
 * @author Student
 */



public class Main {
    /*
    The main method is the starting point of the application.
    */
    public static void main(String[] args){
        
        //create a scanner object to accept keyboard input from the user.
        Scanner input =  new Scanner(System.in);
        
        //Display the registration heading
        System.out.println("=== WELCOME TO CHATAPP ===");
        
        //Ask the user to enter their first name.
        System.out.print("Enter your first name: ");
        String firstName = input.nextLine();
        //this ensures that the user cannot continue without entering their first name.
        
            
       
        
        //Ask the user to enter their last name.
        System.out.print("Enter your last name: ");
        String lastName = input.nextLine();
        
        
        
        //Ask the user to enter their username.
        System.out.print ("Enter your username: ");
        String username = input.nextLine();
        
        
       
        
        
        //Ask the user to enter their password.
        System.out.print ("Enter your password: ");
        String password = input.nextLine();
        
        
    
         // Ask the user to enter their South African cellphone number.
         System.out.print ("Enter your cellphone number: ");
         String cellPhoneNumber = input.nextLine();
            
            /*
         Create a Login object using the information captured so far.
        The password and cellphone number are temporarily empty because
        they have not been entered yet.
        */
        Login login = new Login(
                firstName,
                lastName,
                username,
                password,
                cellPhoneNumber
                
        );
        
        /*
        *
        *registerUser() performs the registration validation
        *inside the Login class.
        *
        */
        String registrationMessage = login.registerUser();
            
          
            
            //Display the registration success messages.
            System.out.println();
            System.out.println(registrationMessage);
            
            
            /*
            * If registration failed, Part 1 does not require us
            *to keep asking the user again
            *
            *Therefore we stop here
            *
            */
            
            if (!login.isRegistered()) {
                
                System.out.println();
                System.out.println("Registration was not completed.");
                
                input.close();
                
                //Return ends the main() method.
                return;
            }

            /*
            *==================================================================
            *LOGIN
            *==================================================================
            *
            *This section is reached ONLY when registration succeeds.
            */

            
            //Display the login heading.
            System.out.println();
            System.out.println("=====LOGIN=====");
            
            //Ask the user to enter their username.
            System.out.print ("Enter your username to log in: ");
            String loginUsername = input.nextLine();
            
            //Ask the user to enter their password.
            System.out.print ("Enter your password:");
            String loginPassword = input.nextLine();
            
            
            /*
            *Store the login attempt inside the Login object.
            */
            
            login.setEnteredUsername(loginUsername);
            login.setEnteredPassword(loginPassword);
            
            /*
            *loginUser() performs the comparison
            *
            *true means the username/password matched
            *false means they did not match
            *
            */
            login.loginUser();
            
            
            /*
            *returningLoginStatus() returns either:
            *
            *Welcome <first name>, <last name> ...
            *
            *Username or password incorrect....
            */
            String loginMessage = login.returnLoginStatus();
            
            
            //Display the final authentication message.
            System.out.println();
            System.out.println(loginMessage);
            
            //Close the Scanner  once all input is finished
            input.close();
            
    }
    
}
                
                