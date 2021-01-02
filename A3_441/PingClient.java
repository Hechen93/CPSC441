/**Harrison Chen 10075185 - CPSC441 Assignment 3, Fall 2020 */
/**Code Template taken from Navid Alipour (TA) - https://gist.github.com/BeardedDonut/0f08daa701781d26dab96a64de226342 */
/**School IP Address: 136.159.5.25 */

import java.io.*; 
import java.net.*; 

class PingClient { 
    public static int BUFFERSIZE = 32;
    public static void main(String args[]) throws Exception 
    { 
        if (args.length != 2)
        {
            System.out.println("Usage: UDPClient <Server IP> <Server Port>");
            System.exit(1);
        }

        // Initialize a UDP server socket and declare a datagram packet
        DatagramSocket clientSocket = new DatagramSocket();
        DatagramPacket myPacket = null;

        /**Variable declarations */
        String ping = "PING";
        int seqNumber;
        int sumValue = 0;
        long maxCount = 0;
        long minCount = 0;
        String fullMsg = null;
        
        byte[] msgBytes = null;
        

        // Initialize input and an output stream for the connection(s)
        byte[] outBuffer = null;
        byte[] inBuffer = null;

        // Initialize user input stream
        String line; 
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in)); 

        // Get user input and send to the server
        // Display the echo meesage from the server
        try {
  
            for(seqNumber = 0; seqNumber < 10; seqNumber++)
            {
               
                long epoch1 = System.currentTimeMillis();

               /**Create the header for UDP packet */
               fullMsg = "PING " + seqNumber + " " + epoch1 + "\r\n";

               /**Convert UDP message to bytes */
               msgBytes = fullMsg.getBytes();

               /**Create new UDP Packet with Message. Direct it to the proper IP address and port number */ 
               myPacket = new DatagramPacket(msgBytes, msgBytes.length, InetAddress.getByName(args[0]), Integer.parseInt(args[1]));
                
                /**Confirmation Message and UDP packet send */
                System.out.println("Sending: PING " + seqNumber + " " + epoch1 + "\r\n");
                clientSocket.send(myPacket); 
            
                /**try{
                    Thread.sleep(1000);
                } catch(InterruptedException e){
                    System.out.println(e);
                    } */
                
                /**Set timer on socket */
                clientSocket.setSoTimeout(1000);

                /**Receiving UDP response back from server */
                try {
                    
                    // Getting response from the server
                inBuffer = new byte[BUFFERSIZE];
                myPacket = new DatagramPacket(inBuffer, inBuffer.length);
            
                // Receive from the UDP socket
                clientSocket.receive(myPacket);
            
                // Convert the packet to a string
                fullMsg = new String(myPacket.getData());
            
                // Trim the buffer data and get the actual received data
                fullMsg = fullMsg.substring(0, myPacket.getLength());
                
                    /**Capture the time when packet returns */
                    long epoch2 = System.currentTimeMillis();

                    /**Calculate RTT time */
                    epoch2 = epoch2 - epoch1;
                             
                    /**Output RTT of packet to screen */
                    System.out.println("Successful Return: PING " + seqNumber + "  - RTT: " + epoch2 + " ms" + "\r\n");

                    /**Keeps track of RTT times */
                    sumValue += epoch2;

                    /**Updates max RTT time */
                if(maxCount == 0){
                    maxCount = epoch2;
                } else if(epoch2 > maxCount){
                    maxCount = epoch2;
                }

                    /**Updates min RTT time */
                if(minCount == 0){
                    minCount = epoch2;
                } else if(epoch2 < minCount){
                    minCount = epoch2;
                }
                
                    /**Outputs UDP packet timeout message and updates RTT average times with 1000ms to take into account the 1000ms we waited before the program moved on */
                } catch (SocketTimeoutException e) {
                    System.out.println("Dropped packet " + seqNumber + " \r\n");
                    sumValue += 1000;
                }
                

               
            } //End For-loop

            /**Output RTT analysis */
            System.out.print("RTT Analysis Results - Average: " + sumValue / 10 + " ms, Maximum RTT: "+ maxCount + " ms, Minimum RTT: " + minCount + " ms");
        }
        catch (UnknownHostException ex) { 
            System.err.println(ex);
        }
        catch (IOException ex) {
            System.err.println(ex);
        }
        
        // Close the socket
        clientSocket.close();           
    } 
} 