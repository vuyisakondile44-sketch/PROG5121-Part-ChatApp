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
        System.out.println("====REGISTRATION====");
        
        //Ask the user to enter their first name.
        System.out.print("Enter your first name: ");
        String firstName = input.nextLine();
        //this ensures that the user cannot continue without entering their first name.
        while (firstName.trim().isEmpty()) {
            System.out.println("First name cannot be empty");
            System.out.print("Enter your first name: ");
            firstName = input.nextLine();
            
        }
        
        //Ask the user to enter their last name.
        System.out.print ("Enter your last name: ");
        String lastName = input.nextLine();
        //this ensures that the user cannot continue without entering their last name.
        while (lastName.trim().isEmpty()){
            System.out.println("Last name cannot be empty");
            System.out.print("Enter your last name: ");
            lastName = input.nextLine();
            
        }
        
        //Ask the user to enter their username.
        System.out.print ("Enter your username: ");
        String username = input.nextLine();
        //this ensures that the user cannot continue without entering their username.
        while (username.trim().isEmpty()){
            System.out.println("Username cannot be empty");
            System.out.print("Enter your username: ");
            username = input.nextLine();
        }
        /*
         Create a Login object using the information captured so far.
        The password and cellphone number are temporarily empty because
        they have not been entered yet.
        */
        Login login = new Login(
                firstName,
                lastName,
                username,
                "",
                ""
        );
        
        // Check the username before continuing to the password.
        if (!login.checkUserName()){
            System.out.println(
                    "Username is not correctly formatted; please ensure that your username "
                            + "contains an underscore and is no more than five characters in lenght."
            );
            return;
        }
        
        //Ask the user to enter their password.
        System.out.print ("Enter your password: ");
        String password = input.nextLine();
        
        while (password.trim().isEmpty()) {
            System.out.println("Password can not be empty");
            System.out.print ("Enter your password");
            password = input.nextLine();
        }
        
        //Store the password in the Login object.
        setPassword(password);
        
        //Check the password before continuing to the cellphone number.
        if(checkPasswordComplexity()){
            System.out.println(
                    "password is not correctly formatted please ensure that the password"
                            + "contains at least eight characters, a capital letter, a number,"
                            + "and a special character."
            );
            return;
        }
        
            // Ask the user to enter their cellphone number.
            System.out.print ("Enter your cellphone number: ");
            String phoneNumber = input.nextLine();
            
            while (phoneNumber.trim().isEmpty()){
                System.out.println("Cellphone number cannot be empty");
                System.out.print("Enter your cellphone number");
                phoneNumber = input.nextLine();
            }
            
            //Store the cellphone number in the Login object.
            setPhoneNumber(phoneNumber);
            
            //Check the cellphone number before continuing to login.
            if (checkCellPhoneNumber()){
                System.out.println(
                        "Cellphone number is not correctly formatted; please ensure that the "
                                + "cellphone number starts with +27 and is followed by 9 numbers."
                );
                return;
            }
            
            /*
            All registration requirements have been successfully met.
            Display the registration success messages.
            */
            System.out.println();
            System.out.println(login.registerUser());
            
            //Display the login heading.
            System.out.println();
            System.out.println("=====LOGIN=====");
            
            //Ask the user to enter their username.
            System.out.print ("Enter your username: ");
            String enteredUsername = input.nextLine();
            
            while (enteredUsername.trim().isEmpty()){
                System.out.println("Username cannot be empty.");
                System.out.print ("Enter your username:");
                enteredUsername = input.nextLine();
            }
                
                //check whether entered usernamame matches the register username.
                if (!enteredUsername.equals (getUsername())){
                    System.out.println("Username or password incorrect, please try again.");
                    return;
                }
                
                //Ask the user to enter their password.
                System.out.print ("Enter your password:");
                String enteredPassword = input.nextLine();
                
                while (enteredPassword.trim().isEmpty()) {
                    System.out.println("password cannot be empty.");
                    System.out.print ("Enter your password: ");
                    enteredPassword = input.nextLine();
                }
                
                //Store the username and password entered during login.
                login.setEnteredUsername(enteredUsername);
                login.setEnteredPassword(enteredPassword);
                
                //Check whether the login credentials are correct.
                System.out.println(login.returnLoginStatus());
            }

    private static Object getUsername() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static boolean checkPasswordComplexity() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static void setPassword(String password) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static boolean checkCellPhoneNumber() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static void setPhoneNumber(String phoneNumber) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
            }