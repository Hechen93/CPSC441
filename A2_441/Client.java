/**Harrison Chen - 10075185
 * CPSC441 - Assignment #2
 * 
 * Sources: Navid Alipour (Tutorial Examples: https://gist.github.com/BeardedDonut/0f08daa701781d26dab96a64de226342)
 * 
 */


import java.io.*; 
import java.net.*; 

class Client { 

    public static void main(String args[]) throws Exception 
    { 
        //Check if user accurately inputs the server address and port number
        if (args.length != 2)
        {
            System.out.println("Usage: TCPClient <Server IP> <Server Port>");
            System.exit(1);
        }

        // create socket and establish connection with server
        Socket clientSocket = new Socket(args[0], Integer.parseInt(args[1])); 

        // Initialize input and an output streams to send and receive data to/from socket
        DataOutputStream outBuffer = 
          new DataOutputStream(clientSocket.getOutputStream()); 
        BufferedReader inBuffer = 
          new BufferedReader(new
          InputStreamReader(clientSocket.getInputStream())); //Should this BufferedReader be a pure DataInputStream
        

        // Initialize user input stream
        String line; 
        String line2;
        BufferedReader inFromUser = 
        new BufferedReader(new InputStreamReader(System.in));       

        // Request action from user using menu
        System.out.print("Please choose an option: \n 1: Enter a Message \n 2: Transform Message \n 7: Exit \n Program will time out after idle time. \n");
        line = inFromUser.readLine();

        /**Create loop for various actions from the user */
        while(!line.equals(7)){

          /**Switch statement for different types of user commands */
          switch(line){

            /**Input message to be transformed */
            case "1":
              System.out.println("Enter a Message: \n");
              line = inFromUser.readLine();
              outBuffer.writeBytes(line + '\n');
              break; //Stops from going to Case #2

            /**Transformation command options. Here the user will input the list of command executions in sequence. */  
            case "2":
              System.out.println("Enter Transformation Commands: ");
              line = inFromUser.readLine();
              outBuffer.writeBytes(line + '\n');
              System.out.println("Waiting for Service Response....");
              line2 = inBuffer.readLine();   //Transformed msg coming back from services
              System.out.println("New Message: " + line2 + "\n");
              break;
            
            /**Exit command to end program. The program will return the last transformed message. */ 
            case "7":
            outBuffer.writeBytes(line + '\n');
            System.out.println("Exiting Program....");
            line2 = inBuffer.readLine();   //Transformed msg coming back from services
            System.out.println("Terminating Message: " + line2 + "\n");
            System.exit(-1);
              
          }

          //Re-prompts the user to enter the new command
          System.out.print("Select another option: \n 1: Enter a Message \n 2: Transform Message \n 7: Exit \n Program will time out after idle time. \n");
          line = inFromUser.readLine();
        }
        // close the socket
        clientSocket.close();           
    } 
} 






