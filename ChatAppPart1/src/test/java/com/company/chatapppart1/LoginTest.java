/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.company.chatapppart1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *The LoginTest class contains JUnit tests used to test
 * the methods in the Login class.
 *
 * Each test checks whether a method produces the
 * expected results.
 * @author Student
 */
public class LoginTest {

    /**
     * Test of checkUserName() method, of class Login.
     *
     * This test uses a valid username, which contains
     * an underscore and is no more than five characters long.
     *
     * assertTrue() checks that the method returns true.
     */
    @Test
    public void testValidUserName() {

        //Create a Login object with valid registration information.
        Login login = new Login(
        "Kyle",
        "Masoka",
        "kyl_1",
        "Ch&&sec@ke99!",
        "+27838968976");

        //Check that the username validation returns true.
        assertTrue(login.checkUserName());
    }

    /**
     * Test of checkPasswordComplexity() method, of class Login.
     *
     * This test checks whether a password containing the required
     * uppercase letter, number and special character  is accepted.
     */
    @Test
    public void testValidPasswordComplexity() {

        //Create a Login object with a valid password.
        Login login = new Login(
        "Kyle",
        "Kondile",
        "kyl_1",
        "Ch&&sec@ke99!",
        "+27838968976");

        /*
        *Check that the password complexity method returns true.
        */
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    /**
     * Test of checkCellPhoneNumber() method, of class Login.
     *
     * The cellphone number starts with +27 and contains the
     * required number of digits.
     */
    @Test
    public void testValidCellPhoneNumber() {

        //Creates a Login object with a valid cellphone number.
        Login login = new Login(
        "Kyle",
        "Kondile",
        "kyl_1",
        "Ch&&sec@ke99!",
        "+27838968976");
        //Check that the cellphone number validation returns true.
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    /**
     * Test of registerUser() method, of class Login.
     *
     * This test checks whether the method returns the correct
     * success messages when all registration information is valid.
     */
    @Test
    public void testRegisterUser() {

        //Creates a Login object using valid registration details.
        Login login = new Login(
        "Kyle",
        "Kondile",
        "kyl_1",
        "Ch&&sec@ke99!",
        "+27838968976");

        //Store the message returned by registerUser().
        String result = login.registerUser();

        /*
        *Compare the expected registration message with
        *the actual message returned by the method.
        */
        assertEquals("""
                        Username successfully captured.
                        Password successfully captured.
                        Cell phone number successfully added.
                        """,
                result);
    }

    /**
     * Test of loginUser() method, of class Login.
     *
     * This test registers a user, then enters the correct username
     * and password and checks that loginUser() returns true.
     */
    @Test
    public void testLoginUser() {

        //Creates a Login object using registered credentials.
        Login login = new Login(
        "Kyle",
        "Kondile",
        "kyl_1",
        "Ch&&sec@ke99!",
        "+27838968976");

        //Register the user first; loginUser() blocks login until this succeeds.
        login.registerUser();

        //Store the username entered during login.
        login.setEnteredUsername("kyl_1");

        //Store the password entered during login
        login.setEnteredPassword("Ch&&sec@ke99!");

        //Check that the login is successful.
        assertTrue(login.loginUser());
    }

    /**
     * Test of returnLoginStatus() method, of class Login.
     *
     * This test checks whether the correct welcome message
     * is returned after a successful login.
     */
    @Test
    public void testReturnLoginStatus() {

        //Creates a Login object using valid registration details.
        Login login = new Login(
        "Kyle",
        "Kondile",
        "kyl_1",
        "Ch&&sec@ke99!",
        "+27838968976");

        //Register the user first, so that loginUser() is allowed to succeed.
        login.registerUser();

        //Enter the correct username for login.
        login.setEnteredUsername ("kyl_1");

        //Enter the correct password for login.
        login.setEnteredPassword ("Ch&&sec@ke99!");

        //Actually attempt the login so loginSuccessful gets set.
        login.loginUser();

        /*
        *Check that the expected welcome message is returned
        *when the login credentials are correct.
        *
        *NOTE: this matches Login.java's ACTUAL returnLoginStatus() output:
        *"Welcome " + firstName + lastName + ", it is great to see you again"
        *(no space between first/last name, no trailing period). If you fix
        *Login.java to add the missing space and period, update this string too.
        */
        assertEquals(
        "Welcome KyleKondile, it is great to see you again", login.returnLoginStatus());
    }
    /**
     * Test of checkUserName() method with an invalid username.
     *
     * This test uses a username that does not contain an underscore
     * and is therefore invalid.
     */
    @Test
    public void testInvalidUserName(){

        //creates a login object using invalid registration details.
        Login login = new Login(
        "Kyle",
        "Kondile",
        "kyle!!!!!!",
        "Ch&&sec@ke99!",
        "+27838968976"
        );
        //checks that the username validation returns false.
        assertFalse(login.checkUserName());
    }
    /**
     * Test of checkPasswordComplexity() method with an invalid password.
     *
     * This test uses a password that does not contain an uppercase
     * letter, number, and special character as per requirements.
     */
    @Test
    public void testInvalidPasswordComplexity(){

        //Creates a login object using invalid registration details
        Login login = new Login(
        "Kyle",
        "Kondile",
        "kyl_1",
        "password",
        "+27838968976"
        );

        //Check that the password validation will return false.
        assertFalse(login.checkPasswordComplexity("password"));
    }
    /**
     * Test of checkCellPhoneNumber () method with an invalid cellphone number.
     *
     * This test uses a cellphone number that does not start with +27.
     * Which is the required South African international code.
     */
    @Test
    public void testInvalidPhoneNumber(){

        //Creates a login object using invalid registration details
        Login login = new Login(
        "Kyle",
        "Kondile",
        "kyl_1",
        "Ch&&sec@ke99!",
        "08966553"
        );

        //checks that the cellphone number validation returns false.
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }
}