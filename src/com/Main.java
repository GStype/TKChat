package com;

import krypt.Libracry;
import java.io.IOException;
import java.util.Scanner;
/**
 * 
 * @author gKal
 *
 */
public class Main
{
    private static Scanner scanner = new Scanner(System.in);   // Scanner is an object used to read what the user inserts.

    public static Libracry libracry = new Libracry(100);       // Must be public because we need it in "Channel".
                                                               // The current key is 100, this can be changed by the user manually.
    public static void main(String[] args)  throws IOException // Method might throw IOException and prints the error if there is one.
    {

        int port;
        String username;

        // The user is requested to insert their username, the port in which they will connect, and the other peer's IP address.
        System.out.println("Insert your port."); 
        port = Integer.parseInt(scanner.nextLine());     

        System.out.println("Insert your username."); 
        username = scanner.nextLine();

        System.out.println("Insert the other user's IP Address."); 
        String desIP = scanner.nextLine();

        System.out.println("Insert the other user's Port.");
        int desPort = Integer.parseInt(scanner.nextLine());

        Channel channel = new Channel(port); // Creates a new channel instance binded to the port.
        channel.Start();
        System.out.println("Connected Successfully!"); //If the user connected successfully, they are notified.
        channel.SendMessage(username + " is now online.", desIP, desPort); //The other users are also notified.

        while (true)
        {
            String msg = scanner.nextLine();

            if (msg.equals("&leaveChat&")) // If the user types "&leaveChat&", they disconnect from the server.
            {
                channel.SendMessage(username + " is now offline.", desIP, desPort); //The other user(s) get notified
                break;                                                              //that this user disconnected

            }
            else if (msg == "") 
            {
            	continue; //If the user presses enter without typing anything beforehand, the program doesn't send a null message.
            }
            else
            {
                channel.SendMessage(username + ": " + msg, desIP, desPort); //Otherwise the message typed by the user is sent.
            }
        }

        scanner.close();
        channel.Stop();

        System.out.println("Disconnected.");

    }
}