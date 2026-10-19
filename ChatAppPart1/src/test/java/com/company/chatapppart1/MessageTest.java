/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.company.chatapppart1;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Message class (Part 2).
 *
 * Message IDs are random in the real program, so the tests that check
 * the hash supply a known ID that starts with "00".
 *
 * @author Student
 */
public class MessageTest {

    //The official PoE test message and recipient.
    private static final String MIKE_TEXT = "Hi Mike, can you join us for dinner tonight?";
    private static final String MIKE_NUMBER = "+27718693002";

    //JUnit gives each test its own empty folder for the JSON file.
    @TempDir
    Path tempDir;

    @BeforeEach
    public void setUp() {
        Message.clearSentMessages();
        Message.setStorageFile(tempDir.resolve("storedMessages.json").toString());
    }

    @AfterEach
    public void tearDown() {
        Message.clearSentMessages();
        Message.resetStorageFile();
    }

    //Helper: builds a string of exactly 'length' characters.
    private String textOfLength(int length) {
        return "a".repeat(length);
    }

    /*
    *=====================================================
    *TEST 1: main message is sent
    *=====================================================
    */
    @Test
    public void testMainMessageSent() {
        Message message = new Message("0012345678", 0, MIKE_NUMBER, MIKE_TEXT);

        assertEquals("Cell phone number successfully captured.", message.checkRecipientCell());
        assertEquals("Message ready to send.", message.checkMessageLength());
        assertEquals("00:0:HITONIGHT", message.createMessageHash());
        assertEquals("Message successfully sent.", message.SentMessage(Message.CHOICE_SEND));
        assertEquals(1, message.returnTotalMessagess());
    }

    /*
    *=====================================================
    *TEST 2: other message is disregarded
    *=====================================================
    */
    @Test
    public void testOtherMessageDisregarded() {
        Message message = new Message("0098765432", 1, "08575975889",
                "Hi Keegan, did you receive the payment?");

        //The number has no international code, so it is invalid.
        assertEquals("Cell phone number is incorrectly formatted or does not contain "
                + "an international code. Please correct the number and try again.",
                message.checkRecipientCell());

        assertEquals("Press 0 to delete the message.",
                message.SentMessage(Message.CHOICE_DISREGARD));

        //A disregarded message must NOT count as sent.
        assertEquals(0, message.returnTotalMessagess());
    }

    /*
    *=====================================================
    *TEST 3 and 4: message length
    *=====================================================
    */
    @Test
    public void testMessageExactlyMaximumLengthAccepted() {
        Message message = new Message("0012345678", 0, MIKE_NUMBER, textOfLength(250));
        assertTrue(message.isMessageLengthValid());
        assertEquals("Message ready to send.", message.checkMessageLength());
    }

    @Test
    public void testMessageTooLongRejected() {
        Message message = new Message("0012345678", 0, MIKE_NUMBER, textOfLength(257));
        assertFalse(message.isMessageLengthValid());
        assertEquals("Message exceeds 250 characters by 7; please reduce the size.",
                message.checkMessageLength());
    }

    /*
    *=====================================================
    *TEST 5: recipient format
    *=====================================================
    */
    @Test
    public void testRecipientValid() {
        Message message = new Message("0012345678", 0, "+27718693002", MIKE_TEXT);
        assertTrue(message.isRecipientValid());
        assertEquals("Cell phone number successfully captured.", message.checkRecipientCell());
    }

    @Test
    public void testRecipientInvalidFormats() {
        String expected = "Cell phone number is incorrectly formatted or does not contain "
                + "an international code. Please correct the number and try again.";

        String[] badNumbers = {"08575975889", "27718693002", "+27abc", "+2771", "", "+277186930021234567"};
        for (String bad : badNumbers) {
            Message message = new Message("0012345678", 0, bad, MIKE_TEXT);
            assertFalse(message.isRecipientValid(), "Should be invalid: " + bad);
            assertEquals(expected, message.checkRecipientCell());
        }
    }

    @Test
    public void testRecipientNullIsInvalid() {
        Message message = new Message("0012345678", 0, null, MIKE_TEXT);
        assertFalse(message.isRecipientValid());
    }

    /*
    *=====================================================
    *TEST 6: ID creation
    *=====================================================
    */
    @Test
    public void testGeneratedIdIsTenDigits() {
        Message message = new Message(0, MIKE_NUMBER, MIKE_TEXT);

        assertEquals(10, message.getMessageId().length());
        assertTrue(message.getMessageId().matches("\\d{10}"));
        assertTrue(message.checkMessageID());
        assertEquals("Message ID generated: " + message.getMessageId(),
                message.messageIdGeneratedText());
    }

    @Test
    public void testGeneratedIdsAreUnique() {
        java.util.Set<String> ids = new java.util.HashSet<>();
        for (int i = 0; i < 500; i++) {
            ids.add(new Message(i).getMessageId());
        }
        assertEquals(500, ids.size());
    }

    @Test
    public void testCheckMessageIdRejectsBadIds() {
        assertFalse(new Message("12345678901", 0, MIKE_NUMBER, MIKE_TEXT).checkMessageID());
        assertFalse(new Message("12345abc90", 0, MIKE_NUMBER, MIKE_TEXT).checkMessageID());
        assertFalse(new Message("", 0, MIKE_NUMBER, MIKE_TEXT).checkMessageID());
        assertFalse(new Message(null, 0, MIKE_NUMBER, MIKE_TEXT).checkMessageID());
    }

    /*
    *=====================================================
    *TEST 7: send / disregard / store
    *=====================================================
    */
    @Test
    public void testSendChoices() {
        Message message = new Message("0012345678", 0, MIKE_NUMBER, MIKE_TEXT);

        assertEquals("Message successfully sent.", message.SentMessage(Message.CHOICE_SEND));
        assertEquals("Press 0 to delete the message.", message.SentMessage(Message.CHOICE_DISREGARD));
        assertEquals("Message successfully stored.", message.SentMessage(Message.CHOICE_STORE));
    }

    @Test
    public void testInvalidChoiceIsRejected() {
        Message message = new Message("0012345678", 0, MIKE_NUMBER, MIKE_TEXT);
        assertEquals("Invalid choice. Please choose 1, 2 or 3.", message.SentMessage(9));
        assertEquals(0, message.returnTotalMessagess());
    }

    @Test
    public void testStoredMessageIsNotCountedAsSent() {
        Message message = new Message("0012345678", 0, MIKE_NUMBER, MIKE_TEXT);
        message.SentMessage(Message.CHOICE_STORE);
        assertEquals(0, message.returnTotalMessagess());
    }

    @Test
    public void testSendingSameMessageTwiceCountsOnce() {
        Message message = new Message("0012345678", 0, MIKE_NUMBER, MIKE_TEXT);
        message.SentMessage(Message.CHOICE_SEND);
        message.SentMessage(Message.CHOICE_SEND);
        assertEquals(1, message.returnTotalMessagess());
    }

    @Test
    public void testOnlySentMessagesAreCounted() {
        Message sent = new Message("0012345678", 0, MIKE_NUMBER, MIKE_TEXT);
        Message disregarded = new Message("0098765432", 1, "+27718693002", "Hi Keegan, did you receive the payment?");
        Message stored = new Message("0055555555", 2, "+27718693002", "See you soon friend");

        sent.SentMessage(Message.CHOICE_SEND);
        disregarded.SentMessage(Message.CHOICE_DISREGARD);
        stored.SentMessage(Message.CHOICE_STORE);

        //3 entered, only 1 sent.
        assertEquals(1, sent.returnTotalMessagess());
    }

    /*
    *=====================================================
    *TEST 8: several messages in a loop
    *=====================================================
    */
    @Test
    public void testHashAndNumberOnEveryIteration() {
        for (int i = 0; i < 5; i++) {
            Message message = new Message("00" + String.format("%08d", i), i, MIKE_NUMBER, MIKE_TEXT);

            assertEquals(i, message.getMessageNumber());
            assertEquals("00:" + i + ":HITONIGHT", message.createMessageHash());
        }
    }

    /*
    *=====================================================
    *Hash edge cases
    *=====================================================
    */
    @Test
    public void testHashIgnoresExtraSpacesAndPunctuation() {
        Message message = new Message("0012345678", 3, MIKE_NUMBER, "   hello   there, friend!!!   ");
        assertEquals("00:3:HELLOFRIEND", message.createMessageHash());
    }

    @Test
    public void testHashIsUppercase() {
        Message message = new Message("0012345678", 0, MIKE_NUMBER, "good morning everyone");
        assertEquals(message.createMessageHash(), message.createMessageHash().toUpperCase());
    }

    /*
    *=====================================================
    *Display order and printMessages()
    *=====================================================
    */
    @Test
    public void testDetailsAreInRequiredOrder() {
        Message message = new Message("0012345678", 0, MIKE_NUMBER, MIKE_TEXT);

        String expected = "Message ID: 0012345678\n"
                + "Message Hash: 00:0:HITONIGHT\n"
                + "Recipient: +27718693002\n"
                + "Message: Hi Mike, can you join us for dinner tonight?";
        assertEquals(expected, message.getDetails());
    }

    @Test
    public void testPrintMessagesOnlyShowsSentMessages() {
        Message sent = new Message("0012345678", 0, MIKE_NUMBER, MIKE_TEXT);
        Message disregarded = new Message("0098765432", 1, "+27718693002", "Hi Keegan, did you receive the payment?");

        sent.SentMessage(Message.CHOICE_SEND);
        disregarded.SentMessage(Message.CHOICE_DISREGARD);

        String printed = sent.printMessages();
        assertTrue(printed.contains("0012345678"));
        assertFalse(printed.contains("0098765432"));
    }

    @Test
    public void testPrintMessagesWhenNothingSent() {
        Message message = new Message("0012345678", 0, MIKE_NUMBER, MIKE_TEXT);
        assertEquals("No messages have been sent.", message.printMessages());
    }

    /*
    *=====================================================
    *JSON storage
    *=====================================================
    */
    @Test
    public void testStoreMessageWritesJsonFile() throws Exception {
        Message message = new Message("0012345678", 0, MIKE_NUMBER, MIKE_TEXT);
        message.SentMessage(Message.CHOICE_STORE);

        Path file = tempDir.resolve("storedMessages.json");
        assertTrue(Files.exists(file));

        String json = new String(Files.readAllBytes(file), StandardCharsets.UTF_8);
        assertTrue(json.startsWith("["));
        assertTrue(json.endsWith("]"));
        assertTrue(json.contains("\"messageId\": \"0012345678\""));
        assertTrue(json.contains("\"messageHash\": \"00:0:HITONIGHT\""));
        assertTrue(json.contains("\"recipient\": \"+27718693002\""));
        assertTrue(json.contains("\"message\": \"Hi Mike, can you join us for dinner tonight?\""));
        assertTrue(json.contains("\"status\": \"STORED\""));
    }

    @Test
    public void testStoringTwoMessagesKeepsBoth() throws Exception {
        new Message("0012345678", 0, MIKE_NUMBER, MIKE_TEXT).SentMessage(Message.CHOICE_STORE);
        new Message("0098765432", 1, MIKE_NUMBER, "Second stored message").SentMessage(Message.CHOICE_STORE);

        String json = new String(Files.readAllBytes(tempDir.resolve("storedMessages.json")), StandardCharsets.UTF_8);
        assertTrue(json.contains("0012345678"));
        assertTrue(json.contains("0098765432"));
        //One comma between the two objects, array still closed correctly.
        assertTrue(json.endsWith("}\n]"));
        assertEquals(2, json.split("\"messageId\"", -1).length - 1);
    }

    @Test
    public void testJsonEscapesQuotesAndBackslashes() throws Exception {
        Message message = new Message("0012345678", 0, MIKE_NUMBER, "She said \"hi\" at C:\\temp");
        message.SentMessage(Message.CHOICE_STORE);

        String json = new String(Files.readAllBytes(tempDir.resolve("storedMessages.json")), StandardCharsets.UTF_8);
        assertTrue(json.contains("She said \\\"hi\\\" at C:\\\\temp"));
    }

    @Test
    public void testStoreFailsWhenFileIsNotAJsonArray() throws Exception {
        Path file = tempDir.resolve("storedMessages.json");
        Files.write(file, "not json".getBytes(StandardCharsets.UTF_8));

        Message message = new Message("0012345678", 0, MIKE_NUMBER, MIKE_TEXT);
        assertFalse(message.storeMessage());
        assertEquals("Message could not be stored.", message.SentMessage(Message.CHOICE_STORE));
        //The existing file must not be damaged.
        assertEquals("not json", new String(Files.readAllBytes(file), StandardCharsets.UTF_8));
    }
}