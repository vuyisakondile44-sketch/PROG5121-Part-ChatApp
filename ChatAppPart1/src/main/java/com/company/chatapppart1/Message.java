/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.company.chatapppart1;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;


public class Message {

    //The maximum number of characters allowed in a message body.
    public static final int MAX_MESSAGE_LENGTH = 250;

    //The three choices the user can make for a finished message.
    public static final int CHOICE_SEND = 1;
    public static final int CHOICE_DISREGARD = 2;
    public static final int CHOICE_STORE = 3;

    //The default file that stored messages are written to.
    private static final String DEFAULT_STORAGE_FILE = "storedMessages.json";

    /*
    * These are static because they belong to the whole program run,
    * not to one single message.
    *
    * sentMessages - every message successfully sent in this run
    * usedIds      - every ID already generated, so IDs stay unique
    */
    private static final List<Message> sentMessages = new ArrayList<>();
    private static final Set<String> usedIds = new HashSet<>();
    private static String storageFile = DEFAULT_STORAGE_FILE;

    //Information that belongs to this one message.
    private final String messageId;
    private final int messageNumber;
    private String recipient;
    private String messageText;
    private String status;

    /*
    *=====================================================
    *CONSTRUCTORS
    *=====================================================
    */

    //Used by the real application: the ID is generated randomly.
    public Message(int messageNumber) {
        this(generateMessageId(), messageNumber, "", "");
    }

    //Used by the real application when the details are already known.
    public Message(int messageNumber, String recipient, String messageText) {
        this(generateMessageId(), messageNumber, recipient, messageText);
    }

    //Used by unit tests: the ID is supplied so the hash is predictable.
    public Message(String messageId, int messageNumber,
                   String recipient, String messageText) {
        this.messageId = messageId;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.status = "CREATED";
    }

    /*
    *=====================================================
    *MESSAGE ID
    *=====================================================
    */

    /**
     * Generates a random ten-digit ID. It is a String so that leading
     * zeroes are kept, and it is checked against usedIds so it is unique.
     */
    private static String generateMessageId() {
        String id;
        do {
            //nextLong(bound) gives 0 up to 9999999999
            long number = ThreadLocalRandom.current().nextLong(10_000_000_000L);
            //%010d pads with leading zeroes until there are ten digits.
            id = String.format("%010d", number);
        } while (usedIds.contains(id));

        usedIds.add(id);
        return id;
    }

    /**
     * Checks that the ID is made of digits only and is no longer
     * than ten characters.
     */
    public boolean checkMessageID() {
        return messageId != null && messageId.matches("\\d{1,10}");
    }

    //The feedback line shown after an ID has been generated.
    public String messageIdGeneratedText() {
        return "Message ID generated: " + messageId;
    }

    /*
    *=====================================================
    *RECIPIENT
    *=====================================================
    */

    /**
     * Regex:
     *
     * ^\\+[1-9]\\d{0,2}\\d{6,10}$
     *
     * ^        = start of the String
     * \\+      = a literal + (the international prefix)
     * [1-9]    = first digit of the country code (never 0)
     * \\d{0,2} = up to two more country-code digits (so 1 to 3 in total)
     * \\d{6,10}= the local number, 6 to 10 digits
     * $        = end of the String
     *
     * Passes:  +27718693002
     * Fails:   08575975889 (no international code)
     *
     * ASSUMPTION: "no more than ten characters" is applied to the local
     * number, not the whole string, because the official valid example
     * +27718693002 is longer than ten characters. Confirm with your lecturer.
     */
    public boolean isRecipientValid() {
        return recipient != null
                && recipient.matches("^\\+[1-9]\\d{0,2}\\d{6,10}$");
    }

    public String checkRecipientCell() {
        if (isRecipientValid()) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain "
                + "an international code. Please correct the number and try again.";
    }

    /*
    *=====================================================
    *MESSAGE LENGTH
    *=====================================================
    */

    public boolean isMessageLengthValid() {
        return messageText != null && messageText.length() <= MAX_MESSAGE_LENGTH;
    }

    public String checkMessageLength() {
        if (isMessageLengthValid()) {
            return "Message ready to send.";
        }
        int length = (messageText == null) ? 0 : messageText.length();
        int excess = length - MAX_MESSAGE_LENGTH;
        return "Message exceeds 250 characters by " + excess
                + "; please reduce the size.";
    }

    /*
    *=====================================================
    *MESSAGE HASH
    *=====================================================
    */

    /**
     * Builds the hash:  first two ID digits : message number : FIRSTLAST
     *
     * Example: ID 0012345678, number 0 and the message
     * "Hi Mike, can you join us for dinner tonight?" gives 00:0:HITONIGHT
     */
    public String createMessageHash() {
        //First two characters of the ID.
        String idStart = messageId.substring(0, Math.min(2, messageId.length()));

        //trim() removes extra spaces, split("\\s+") splits on any spaces.
        String trimmed = (messageText == null) ? "" : messageText.trim();
        String firstWord = "";
        String lastWord = "";

        if (!trimmed.isEmpty()) {
            String[] words = trimmed.split("\\s+");
            firstWord = cleanWord(words[0]);
            lastWord = cleanWord(words[words.length - 1]);
        }

        return (idStart + ":" + messageNumber + ":" + firstWord + lastWord)
                .toUpperCase();
    }

    //Removes punctuation from the start and end of a word ("tonight?" -> "tonight").
    private static String cleanWord(String word) {
        return word.replaceAll("^[^A-Za-z0-9]+|[^A-Za-z0-9]+$", "");
    }

    /*
    *=====================================================
    *SEND / DISREGARD / STORE
    *=====================================================
    */

    /**
     * Carries out the user's choice and returns the feedback message.
     * (The capital S matches the method name given in the PoE.)
     *
     * 1 = send    -> added to the sent list, so it counts towards the total
     * 2 = disregard -> NOT added to the sent list
     * 3 = store   -> written to the JSON file, NOT counted as sent
     */
    public String SentMessage(int choice) {
        switch (choice) {
            case CHOICE_SEND:
                status = "SENT";
                //Guard against the same message being counted twice.
                if (!sentMessages.contains(this)) {
                    sentMessages.add(this);
                }
                return "Message successfully sent.";

            case CHOICE_DISREGARD:
                status = "DISREGARDED";
                return "Press 0 to delete the message.";

            case CHOICE_STORE:
                status = "STORED";
                if (storeMessage()) {
                    return "Message successfully stored.";
                }
                return "Message could not be stored.";

            default:
                return "Invalid choice. Please choose 1, 2 or 3.";
        }
    }

    //The four lines shown for a sent message, in the required order.
    public String getDetails() {
        return "Message ID: " + messageId + "\n"
                + "Message Hash: " + createMessageHash() + "\n"
                + "Recipient: " + recipient + "\n"
                + "Message: " + messageText;
    }

    //Returns every message sent during this program run.
    public String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages have been sent.";
        }
        StringBuilder output = new StringBuilder();
        for (Message sent : sentMessages) {
            output.append(sent.getDetails()).append("\n\n");
        }
        return output.toString().trim();
    }

    //Returns how many messages were successfully SENT (not stored/disregarded).
    public int returnTotalMessagess() {
        return sentMessages.size();
    }

    /*
    *=====================================================
    *JSON STORAGE
    *=====================================================
    */

    /**
     * Appends this message to the JSON array in the storage file.
     * The file is created if it does not exist yet.
     *
     * @return true if the message was written successfully
     */
    public boolean storeMessage() {
        Path path = Paths.get(storageFile);
        try {
            String existing = "";
            if (Files.exists(path)) {
                existing = new String(Files.readAllBytes(path),
                        StandardCharsets.UTF_8).trim();
            }

            String updated;
            if (existing.isEmpty() || existing.equals("[]")) {
                //First stored message: start a new array.
                updated = "[\n" + toJson() + "\n]";
            } else if (existing.endsWith("]")) {
                //Remove the closing bracket and add this message after a comma.
                String withoutBracket = existing
                        .substring(0, existing.length() - 1).stripTrailing();
                updated = withoutBracket + ",\n" + toJson() + "\n]";
            } else {
                //The file is not a JSON array, so do not damage it.
                return false;
            }

            Files.write(path, updated.getBytes(StandardCharsets.UTF_8));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    //Turns this message into one JSON object.
    private String toJson() {
        return "  {\n"
                + "    \"messageId\": \"" + escapeJson(messageId) + "\",\n"
                + "    \"messageHash\": \"" + escapeJson(createMessageHash()) + "\",\n"
                + "    \"recipient\": \"" + escapeJson(recipient) + "\",\n"
                + "    \"message\": \"" + escapeJson(messageText) + "\",\n"
                + "    \"status\": \"" + escapeJson(status) + "\"\n"
                + "  }";
    }

    //Escapes characters that are not allowed raw inside a JSON string.
    private static String escapeJson(String text) {
        if (text == null) {
            return "";
        }
        StringBuilder escaped = new StringBuilder();
        for (char c : text.toCharArray()) {
            switch (c) {
                case '"':  escaped.append("\\\""); break;
                case '\\': escaped.append("\\\\"); break;
                case '\n': escaped.append("\\n");  break;
                case '\r': escaped.append("\\r");  break;
                case '\t': escaped.append("\\t");  break;
                default:
                    if (c < 0x20) {
                        escaped.append(String.format("\\u%04x", (int) c));
                    } else {
                        escaped.append(c);
                    }
            }
        }
        return escaped.toString();
    }

    /*
    *=====================================================
    *GETTERS, SETTERS AND TEST HELPERS
    *=====================================================
    */

    public String getMessageId()    { return messageId; }
    public int getMessageNumber()   { return messageNumber; }
    public String getRecipient()    { return recipient; }
    public String getMessageText()  { return messageText; }
    public String getStatus()       { return status; }

    public void setRecipient(String recipient)     { this.recipient = recipient; }
    public void setMessageText(String messageText) { this.messageText = messageText; }

    //Lets unit tests write to a temporary file instead of the real one.
    public static void setStorageFile(String fileName) { storageFile = fileName; }

    public static void resetStorageFile() { storageFile = DEFAULT_STORAGE_FILE; }

    //Lets unit tests start each test with an empty sent list.
    public static void clearSentMessages() { sentMessages.clear(); }
}