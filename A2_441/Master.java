/**Harrison Chen - 10075185
 * CPSC441 - Assignment #2
 * 
 * Sources: Navid Alipour (Tutorial Example: https://gist.github.com/BeardedDonut/0f08daa701781d26dab96a64de226342)
 * 
 */

import java.io.*;
import java.net.*;

public class Master {

    public static int BUFFERSIZE = 32;

    public static void main(String args[]) {
        
       /**Initializing TCP sockets and input/output readers */
        ServerSocket masterServer = null;
        Socket clientSocket = null;

        String line = "";
        String line2 = "";
        String clientMsgLine = "";
        char[] inputStore;
        BufferedReader inBuffer;
        DataOutputStream outBuffer;

        int microPort1 = 8788;
        int microPort2 = 8789;
        int microPort3 = 8790;
        int microPort4 = 8791;
        int microPort5 = 8792;
        int microPort6 = 8793;


        

        // Initialize input and an output stream for the UDP connection(s)
        byte[] outBuffer2 = null;
        byte[] inBuffer2 = null;

        if (args.length != 1)
        {
            System.out.println("Usage: TCPServer <Listening Port>");
            System.exit(1);
        }
        
        // open a server socket on the given port
       try {
            masterServer = new ServerSocket(Integer.parseInt(args[0]));
        }
        catch (IOException e) {
            // usually if port is taken
            System.out.println(e);
        }
   
        try {
               
                // accept  TCP connections
                clientSocket = masterServer.accept();
                System.out.println("...");

                // create input and output streams
                inBuffer = new BufferedReader(new
                InputStreamReader(clientSocket.getInputStream()));
                outBuffer = new DataOutputStream(clientSocket.getOutputStream());
   

                    /**Initialize new client socket, packets and in/out buffers for UDP services */
                    DatagramSocket clientSocket2 = new DatagramSocket();
                    DatagramPacket myPacket = null;
                
                    /**Get input from client */
                    line = inBuffer.readLine();

                    /**Store client message as a saved variable */
                    String saveMessage = line;

                    /**Write back to console confirming the message has been received */
                    System.out.print("Message Received: " + line + '\n');
                    System.out.print("Message Stored: " + saveMessage + '\n');

                    /**Read in the transformation commands */
                    line = inBuffer.readLine();

                    /**Write to console confirming the message transformation commands */
                    System.out.print("Transformation Instructions Received: " + line + '\n');
                
                    /**Timeout on socket in case the user sits idle */
                    clientSocket.setSoTimeout(2000);
                    
                    /**Transformation commands processed in the following for-loop and switch statement */
                    while(!line.equals(7)){

                        /**Read through the length of the transformation commands */
                        for(int i = 0; i < line.length(); i++){

                            /**Analyze each character in the command string and send them to the appropriate microservers */
                            switch(line.charAt(i)){

                                    case '1':           
                                        // Send to the microserver 1 (ECHO)
                                        outBuffer2 = saveMessage.getBytes();
                                        myPacket = new DatagramPacket(outBuffer2, outBuffer2.length, InetAddress.getByName("localhost"), microPort1);
                                        clientSocket2.send(myPacket); 
                                        System.out.println("Executing A_Instruction: " + line.charAt(i));
                                        System.out.println("Sent to microservice A: " + saveMessage);
                                        try{
                                            Thread.sleep(1000);
                                        } catch(InterruptedException e){
                                            System.out.println(e);
                                        } break;
                                    

                                    case '2':
                                        // Send to the microserver 2 (REVERSE)
                                    
                                        outBuffer2 = saveMessage.getBytes();
                                        myPacket = new DatagramPacket(outBuffer2, outBuffer2.length, InetAddress.getByName("localhost"), microPort2);
                                        clientSocket2.send(myPacket); 
                                        System.out.println("Executing B_Instruction: " + line.charAt(i));
                                        System.out.println("Sent to microservice B: " + saveMessage);
                                        try{
                                            Thread.sleep(1000);
                                        } catch(InterruptedException e){
                                            System.out.println(e);
                                        } break;
                                    

                                    case '3':
                                        // Send to the microserver 3 (UPPER)
                                    
                                        outBuffer2 = saveMessage.getBytes();
                                        myPacket = new DatagramPacket(outBuffer2, outBuffer2.length, InetAddress.getByName("localhost"), microPort3);
                                        clientSocket2.send(myPacket); 
                                        System.out.println("Executing C_Instruction: " + line.charAt(i));
                                        System.out.println("Sent to microservice 3: " + saveMessage);
                                        try{
                                            Thread.sleep(1000);
                                        } catch(InterruptedException e){
                                            System.out.println(e);
                                        } break;
                                    

                                    case '4':
                                        // Send to the microserver 4 (LOWER)
                                   
                                        outBuffer2 = saveMessage.getBytes();
                                        myPacket = new DatagramPacket(outBuffer2, outBuffer2.length, InetAddress.getByName("localhost"), microPort4);
                                        clientSocket2.send(myPacket); 
                                        System.out.println("Executing D_Instruction: " + line.charAt(i));
                                        System.out.println("Sent to microservice 4: " + saveMessage);
                                        try{
                                            Thread.sleep(1000);
                                        } catch(InterruptedException e){
                                            System.out.println(e);
                                        } break;

                                    case '5':
                                        // Send to the microserver 5 (CAESAR)
                                    
                                        outBuffer2 = saveMessage.getBytes();
                                        myPacket = new DatagramPacket(outBuffer2, outBuffer2.length, InetAddress.getByName("localhost"), microPort5);
                                        clientSocket2.send(myPacket); 
                                        System.out.println("Executing E_Instruction: " + line.charAt(i));
                                        System.out.println("Sent to microservice E: " + saveMessage);
                                        try{
                                            Thread.sleep(1000);
                                        } catch(InterruptedException e){
                                            System.out.println(e);
                                        } break;
                                    
                                    case '6':
                                        // Send to the microserver 6 (YOURS - REPLICATE)
                                    
                                        outBuffer2 = saveMessage.getBytes();
                                        myPacket = new DatagramPacket(outBuffer2, outBuffer2.length, InetAddress.getByName("localhost"), microPort6);
                                        clientSocket2.send(myPacket); 
                                        System.out.println("Executing E_Instruction: " + line.charAt(i));
                                        System.out.println("Sent to microservice E: " + saveMessage);
                                        try{
                                            Thread.sleep(1000);
                                        } catch(InterruptedException e){
                                            System.out.println(e);
                                        } break;
                                }//End switch
                            
                            /**Receives packet response */
                            inBuffer2 = new byte[BUFFERSIZE];
                            myPacket = new DatagramPacket(inBuffer2, inBuffer2.length);
                    
                            // Receive from the UDP socket
                            clientSocket2.receive(myPacket);

                            // Convert the packet to a string
                            saveMessage = new String(myPacket.getData());
                    
                            // Trim the buffer data and get the actual received data
                            saveMessage = saveMessage.substring(0, myPacket.getLength());
                            System.out.println("Response from Microservice at Master Server: " + saveMessage);

                        }//End For
                        
                        //Output transformation back to client and close UDP socket
                        outBuffer.writeBytes(saveMessage + "\n"); 
                        clientSocket2.close();
                        
                     
                    } //End While

                //Close the TCP readers
                inBuffer.close();
                outBuffer.close();
                
                // close the connections
                clientSocket.close();
            
            //Close server socket
            masterServer.close();

        }   catch(IOException e){
            System.out.println(e);
        }
    }
}

