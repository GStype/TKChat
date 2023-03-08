//I made this library to have the Encryption and Decryption method.
package krypt;

import java.util.regex.Pattern; // Regex is required in order to use the "splitting" method in lines 28 and 44.

public class Libracry
{
    int key;

    public Libracry(int key)
    {
        this.key = key; //Turns the key in this library into the key that the user inserted in Main.
    }

    /**
     * This is where the encryption takes place. The encryption process does the following:
     * From the message sent by the user, every single character is taken separately, and get encrypted.
     * @param msg
     * @return
     */
    public String Encrypt(String msg)
    {
        String result = "";

        for (int i = 0; i < msg.length(); i++)
        {
            char a = (char)((int)msg.charAt(i) + i + key); // Converts the char into an int, takes its ASCII code, modifies it (+i +key), then turns it back into a char.
            result += (int)a + "|"; // in order to keep each encrypted character separated from each other, they are split by |. This is necessary for the Decryption to work properly.
        }
        return result;
    }
    
    /**
     * This is where the Decryption takes place.
     * Basically it's the Encryption process from above, but inverted.
     * It reads every character separately, and when it reaches a "|", it starts encrypting the character.
     * @param msg
     * @return
     */
    public String Decrypt(String msg)
    {
        String result = "";

        String[] arrayOfIntChars = msg.split(Pattern.quote("|")); 

        for(int i = 0; i < arrayOfIntChars.length; i++)
        {
            char a = (char)(Integer.parseInt(arrayOfIntChars[i]) - i - key);
            result += a;
        }

        return result;

    }
}