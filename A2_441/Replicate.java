/**Harrison Chen - 10075185
 * CPSC441 - Assignment #2
 * 
 * Sources: 1) Navid Alipour (Tutorial Example: https://gist.github.com/BeardedDonut/0f08daa701781d26dab96a64de226342)
 *          
 * 
 * 
 * My Yours File creates a double of the original word (hence the file name 'Replicate')
 */

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Replicate{
    public static int BUFFERSIZE = 32;

    public static void main(String[] args) {
        DatagramSocket caesarServer = null;
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
            caesarServer = new DatagramSocket(Integer.parseInt(args[0]));
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
                caesarServer.receive(myPacket);

                InetAddress keepAddress = myPacket.getAddress();
                int keepPort = myPacket.getPort();

                // Convert the packet to a string
                line = new String(myPacket.getData());

                // Trim the buffer data and get the actual received data
                line = line.substring(0, myPacket.getLength());
                System.out.println("Client " + myPacket.getAddress() + ":" + myPacket.getPort() + ": " + line);

                //Creates and returns a duplicate of the input
                StringBuilder newWord = new StringBuilder(line);
                
                //Iterates through the length of the original input message
                for(int i = 0; i < line.length(); i++){
                    
                    //Checks to see if the character is an appropriate character and not a space
                    char checker = line.charAt(i);

                    //Finishes check. If it's not a space, add it back to the original string
                    if (checker != ' ') {    
                        newWord.append(line.charAt(i));
                    }
                }

                //Output the new transformed string to go back to the UDP socket
                line = newWord.toString();
                
                //Reprocess string to bytes
                byte[] outValue = line.getBytes();

                //Create new UDP packet to resend back to the master server
                myPacket = new DatagramPacket(inBuffer, inBuffer.length, keepAddress, keepPort);
                myPacket.setData(outValue);
                caesarServer.send(myPacket);
            }

            // Close the socket
            caesarServer.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    
    
    
    }
}

















