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
            *PART 2: we keep the result in "loggedIn" because it decides
            *whether the QuickChat menu may be opened.
            */
            boolean loggedIn = login.loginUser();
            
            
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
            
            /*
            *PART 2: only a successfully logged-in user may use QuickChat.
            */
            if (loggedIn) {
                runQuickChat(input);
            }
            
            //Close the Scanner  once all input is finished
            input.close();
            
    }
    
    /*
    *=====================================================================
    *runQuickChat()
    *=====================================================================
    *
    *Shows the menu repeatedly with a WHILE loop until the user chooses 3.
    *The user decides up front how many messages they may enter.
    */
    private static void runQuickChat(Scanner input) {

        System.out.println();
        System.out.println("Welcome to QuickChat.");

        //The message allowance: the user may not enter more than this.
        int messageLimit = readPositiveNumber(input,
                "Enter the number of messages you wish to send: ");

        //Becomes true once the allowance has been used up.
        boolean limitReached = false;

        int choice = 0;

        //WHILE loop: keeps the menu running until Quit (3) is chosen.
        while (choice != 3) {

            System.out.println();
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Enter your choice: ");

            choice = readMenuChoice(input);

            if (choice == 1) {
                if (limitReached) {
                    System.out.println("Message limit reached. "
                            + "You cannot enter any more messages.");
                } else {
                    enterMessages(input, messageLimit);
                    limitReached = true;
                }
            } else if (choice == 2) {
                //Intentionally not built in Part 2.
                System.out.println("Coming Soon.");
            } else if (choice == 3) {
                System.out.println("Goodbye.");
            } else {
                System.out.println("Invalid option. Please choose 1, 2 or 3.");
            }
        }
    }

    /*
    *=====================================================================
    *enterMessages()
    *=====================================================================
    *
    *Uses a FOR loop so the user can enter ONLY the number of messages
    *they chose. i is the message number and is used in the hash.
    *
    *Counter convention: i starts at 0 so the first message matches the
    *PoE sample hash 00:0:HITONIGHT.
    */
    private static void enterMessages(Scanner input, int messageLimit) {

        //Remembered so we can ask it for the total sent after the loop.
        Message lastMessage = null;

        for (int i = 0; i < messageLimit; i++) {

            System.out.println();
            System.out.println("Creating message " + (i + 1)
                    + " of " + messageLimit + " ...");

            //The constructor generates the random ten-digit ID.
            Message message = new Message(i);
            lastMessage = message;
            System.out.println(message.messageIdGeneratedText());

            //Keep asking until the recipient is valid.
            do {
                System.out.print("Enter the recipient cell number "
                        + "(with international code, e.g. +27...): ");
                message.setRecipient(input.nextLine().trim());
                System.out.println(message.checkRecipientCell());
            } while (!message.isRecipientValid());

            //Keep asking until the message is 250 characters or fewer.
            do {
                System.out.println("Please enter a message of less than 250 characters.");
                message.setMessageText(input.nextLine());
                System.out.println(message.checkMessageLength());
            } while (!message.isMessageLengthValid());

            askSendDisregardOrStore(input, message);
        }

        System.out.println();
        System.out.println("Message limit reached.");

        //This is the number SENT, not the number entered.
        System.out.println("Total messages sent: "
                + lastMessage.returnTotalMessagess());
    }

    /*
    *Lets the user send, disregard or store one finished message.
    *A disregarded or stored message is never counted as sent.
    */
    private static void askSendDisregardOrStore(Scanner input, Message message) {

        boolean finished = false;

        while (!finished) {
            System.out.println();
            System.out.println("1) Send Message");
            System.out.println("2) Disregard Message");
            System.out.println("3) Store Message to send later");
            System.out.print("Enter your choice: ");

            int choice = readMenuChoice(input);
            String feedback = message.SentMessage(choice);
            System.out.println(feedback);

            if (choice == Message.CHOICE_SEND) {
                //Details in the required order: ID, Hash, Recipient, Message.
                System.out.println(message.getDetails());
                finished = true;

            } else if (choice == Message.CHOICE_DISREGARD) {
                //The user must enter 0 to confirm the deletion.
                System.out.print("Enter 0 to confirm: ");
                if (input.nextLine().trim().equals("0")) {
                    System.out.println("Message deleted.");
                    finished = true;
                } else {
                    System.out.println("Message was not deleted.");
                }

            } else if (choice == Message.CHOICE_STORE) {
                finished = true;
            }
            //Any other number: feedback explained it, so ask again.
        }
    }

    /*
    *Reads one menu choice. Anything that is not a whole number returns -1
    *so the program shows an "invalid" message instead of crashing.
    */
    private static int readMenuChoice(Scanner input) {
        try {
            return Integer.parseInt(input.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    //Keeps asking until the user enters a whole number greater than 0.
    private static int readPositiveNumber(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int number = Integer.parseInt(input.nextLine().trim());
                if (number > 0) {
                    return number;
                }
            } catch (NumberFormatException e) {
                //fall through to the message below
            }
            System.out.println("Please enter a whole number greater than 0.");
        }
    }
}