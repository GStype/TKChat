package com;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import krypt.Libracry;

public class Channel implements Runnable
{
    private DatagramSocket socket; //Sockets store the connection with the other user.
    
    /**
     * Creates a new instance of DatagramSocket and assigns it to the socket field.
     * 
     * @param port Takes the port as a parameter that will be used by the Channel.
     * @throws SocketException 
     */
    public Channel(int port) throws SocketException
    {
        socket = new DatagramSocket(port); 
    }

    /**
     * Runs Channel instance in a parallel thread.
     */
    public void Start()
    {
        Thread thread = new Thread(this);
        thread.start();
    }

    public void Stop()
    {
        socket.close(); //Terminates the connection.
    }

    @Override
    public void run() //TODO: Try throw
    {
        byte[] buffer = new byte[1024];

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        while(!socket.isClosed())                                       // As long as the socket is not closed...
        {

            try
            {
                socket.receive(packet);                                 // ...all messages sent by the user are being received by the other(s).
                String msg = new String(buffer, 0, packet.getLength()); // The message is received Encrypted.
                msg = Main.libracry.Decrypt(msg);                       // Calls the "Decrypt" function and decrypts the message.
                System.out.println(msg);                                // Prints the message.
            }
            catch (IOException e) //if there is an Error, the while loop stops.
            {
                e.printStackTrace(); //The error is printed out.
            }


        }
    }


    /**
     * 
     * @param message This is where the message is stored.
     * @param desIP holds the IP of the other user.
     * @param desPort Holds the port in which the users will connect to
     * @throws IOException If there is an error, it will be printed to the user.
     */
    public void SendMessage(String message, String desIP, int desPort) throws IOException
    {   	
    	message = Main.libracry.Encrypt(message); // Calls the "Encrypt" function and encrypts the message.
        InetSocketAddress address = new InetSocketAddress(desIP, desPort); //Assigns the port the the other peer's IP as the address the messages go to.
                                                                          // By using "InetSocketAddress".
        byte[] buffer = message.getBytes();                               //In order to send something to the remote host it needs to be in bytes.
                                                                          // This creates a byte array for the message.
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        packet.setSocketAddress(address);        //Packets can hold an IP and port value. This stores the IP and port into the packet...          

        socket.send(packet);                     // ...and the packet is being sent to that IP.


    }
}