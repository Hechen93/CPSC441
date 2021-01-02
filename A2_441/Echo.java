/**Harrison Chen - 10075185
 * CPSC441 - Assignment #2
 * 
 * Sources: Navid Alipour (Tutorial Example: https://gist.github.com/BeardedDonut/0f08daa701781d26dab96a64de226342)
 * 
 */

import java.io.*;
import java.net.*;

public class Echo{
    public static int BUFFERSIZE = 32;

    public static void main(String[] args) {
        DatagramSocket echoServer = null;
        DatagramPacket myPacket = null;

        // Initialize an input and an output stream
        String line = "";
        byte[] inBuffer = null;

        if (args.length != 1)
        {
            System.out.println("Usage: UDPServer <Listening Port>");
            System.exit(1);
        }
        
        // Try to open a server socket on the given port
        // Note that we can't choose a port less than 1023 if we are not
        // privileged users (root)
        try {
            echoServer = new DatagramSocket(Integer.parseInt(args[0]));
        }
        catch (IOException e) {
            System.out.println(e);
        }
    
        try {
            // As long as we receive data, echo that data back to the client.
            while (!line.equals("terminate")) {
                inBuffer = new byte[BUFFERSIZE];

                // Initlize a datagram packet for the receive operation
                myPacket = new DatagramPacket(inBuffer, inBuffer.length);

                // Receive data into myPacket from the socket
                echoServer.receive(myPacket);
                

                // Convert the packet to a string
                line = new String(myPacket.getData());

                //Confirms that the Echo service has received the message
                System.out.println("Echo Microservice received msg: " + line);

                // Trim the buffer data and get the actual received data
                line = line.substring(0, myPacket.getLength());
                System.out.println("Client " + myPacket.getAddress() + ":" + myPacket.getPort() + ": " + line);

                 // Echo the packet back to the client
                echoServer.send(myPacket);
            }

            // Close the socket
            echoServer.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    
    
    
    }

}