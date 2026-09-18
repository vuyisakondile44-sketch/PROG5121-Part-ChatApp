# PROG5121-Part-ChatApp
<h2>Student Detail</h2>
<p>Student Name: Vuyisa Onke Kondile</p>
<p>student Number:ST10533017</p>
<p>Module:PROG5121-Programming 1A</p>
<p>Assessment: Part 1-Restration and Login Feature</p>
<hr/>
<h2>Project Overview</h2>
<p>This project is a Java console application that implements the registration and login functionality required for PROG5121 Part 1. The application allows a user to register using a username, password and South African cell phone number, and then log in using the registered details</p>
<hr/>
<h2>Features</h2>
<ul><p>Username validation</p></ul>
<ul><p>Password complexity validation</p></ul>
<ul><p>South African cell phone number validation using a regular expression</p></ul>
<ul><p>User registration</p></ul>
<ul><p>User login authentication</p></ul>
<ul><p>First name and last name welcome message</p></ul>
<hr/>
<h2>Technologies Used</h2>
<ul><p>Java</p></ul>
<ul><p>Apache Maven</p></ul>
<ul><p>NetBeans</p></ul>
<ul><p>JUnit</p></ul>
<ul><p>Git and GitHub</p></ul>
<hr/>
<h2>Project Structure</h2>
<ol><p>Main.java - Handles user input and runs the console application.</p></ol>
<ul><p>Login.java - Contains the registration, validation and login logic.</p></ul>
<ul><p>LoginTest.java - Contains the JUnit tests for Part 1 functionality.</p></ul>

<hr/>
<h2>How To Run</h2>
<ol>
  <li>Open the Maven project in NetBeans</li>
  <li>Run Main.java</li>
  <li>Follow the registration and login prompts in the console</li>
</ol>

<h2>Testing</h2>
<hr/>
<p>Unit tests are contained in LogInTest.java. The tests check valid and invalid usernames, passwords and cellphone numbers, as well as successful and failed login functionality and the required system messages.</p>

<h2>Regex Reference</h2>
<hr/>
<p>The South African cell phone number regular expression used in this project is: </p>
<p>^\\+27\\d{9}$</p>
<p>The OP was for a South African cell phone (mobile) number not just any South African telephone number.

The following regex is what worked for me:

^(\+27|0)[6-8][0-9]{8}$
Allow for international dialling code or starting with zero.
South African cell numbers start with 08 or 07 or 06. </p>
<p>Source: Stack Overflow, Validate South African Cell Phone Number.Accessed 18 September 2026.</p>
<h2>Video Presentation</h2>
<hr/>
<p>Part 1 Video: </p>





