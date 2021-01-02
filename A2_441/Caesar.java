/**Harrison Chen - 10075185
 * CPSC441 - Assignment #2
 * 
 * Sources: 1) Navid Alipour (Tutorial Example: https://gist.github.com/BeardedDonut/0f08daa701781d26dab96a64de226342)
 *          2) Java isLetter() Method https://www.javatpoint.com/post/java-character-isletter-method
 * 
 */

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Caesar{
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

                //Create new variable for output transformation
                StringBuilder newWord = new StringBuilder();
                
               //Caesar cipher transformation
                for(int i = 0; i < line.length(); i++){
                    
                    //Checks if letter in input string is a valid character. Adds offset of 2 to its ASCII value and modulo wraps it
                    if(Character.isLetter(line.charAt(i))){
                        int newAlphabetPosition = (line.charAt(i) + 2 - 97) % 26;
                        char newCharacter = (char) (97 + newAlphabetPosition);
                        newWord.append(newCharacter);
                    }
                   

                }
               
                //Reprocess the new string to be sent back to the master server
                line = newWord.toString();
                byte[] outValue = line.getBytes();

                //Create new UDP packet to be sent back to the master server
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

















