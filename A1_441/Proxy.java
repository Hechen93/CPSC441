// Add your full name, UCID, and other optional info here
//Harrison Chen, 10075185, CPSC441, F2020 Assignment 1

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.*;


public class Proxy {
    /** Port for the proxy */
    private static int port;
    /** Socket for client connections */
    private static ServerSocket socket;

    /** Create the Proxy object and the socket */
    public static void init(int p) {
	port = p;
	try {
	    socket = new ServerSocket(port);
	} catch (IOException e) {
	    System.out.println("Error creating socket: " + e);
	    System.exit(-1);
	}
    }

    public static void handle(Socket client) {
	Socket server = null;	
	HttpRequest request = null;
	HttpResponse response = null;

	/* Process request. If there are any exceptions, then simply
	 * return and end this request. This unfortunately means the
	 * client will hang for a while, until it timeouts. */

	/* Read request */
	try {
	    BufferedReader fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));		//Initialize fromClient BufferedReader to get browser data
	    request = new HttpRequest(fromClient);																//Initialize new HTTPRequest object using the fromClient BufferedReader
	} catch (IOException e) {
	    System.out.println("Error reading request from client: " + e);
	    return;
	}
	/* Send request to server */
	try {
	    /* Open socket and write request to socket */
	    server = new Socket(request.getHost(), request.getPort()); 											//Initialize a new socket to original CPSC server. Use the predefined getHost and getPort methods to get server IP address and port number
		DataOutputStream toServer = new DataOutputStream(server.getOutputStream());							//Initialize a DataOutputStream object to send data to original CPSC server.
		
		if(request.URI.contains("NBA")){																	//Check HTTPRequest URI for NBA string and change the text to TBA. This will change the URI path to get the new robot images.
			request.URI = request.URI.replaceAll("NBA", "TBA");												//Uses the replaceAll method to replace NBA to TBA.
		}
		toServer.write(request.toString().getBytes());														//Write out the new HTTPRequest with the modified URI to the server.
	
	} catch (UnknownHostException e) {
	    System.out.println("Unknown host: " + request.getHost());
	    System.out.println(e);
	    return;
	} catch (IOException e) {
	    System.out.println("Error writing request to server: " + e);
	    return;
	}
	/* Read response and forward it to client */
	try {
	    DataInputStream fromServer = new DataInputStream(server.getInputStream());							//Initialize DataInputStream back from Server to proxy
	    response = new HttpResponse(fromServer);															//Initialize new HTTPResponse object using DataInputStream FromServer 
	    DataOutputStream toClient = new DataOutputStream(client.getOutputStream());							//Initialize new OutputStream back to Client Browser
		/* Fill in */
		
		if(response.headers.contains("text")){																//Check to see if response is html response or image response
		String tmp = new String(response.body);																//Initialize a new string to manipulate text response. If it's a text-based response, we change it according to the requirements.
		
				tmp = tmp.replace("2019", "2219");															//Replace 2019 to 2219
				tmp = tmp.replace("NBA", "TBA");															//Replace NBA to TBA
				tmp = tmp.replace("World", "Titan");														//Replace World to Titan
				tmp = tmp.replace("Drummond", "Kobe-B24");													//Replace Drummond to Kobe
			
			
			toClient.writeBytes(tmp);																		//Write out the modified text response to client browser
		
		}else toClient.write(response.body);																//Write out all other server responses otherwise (e.g., images)

	    /* Write response to client. First headers, then body */
	    client.close();
	    server.close();
	    /* Insert object into the cache */
	    /* Fill in (optional exercise only) */
	} catch (IOException e) {
	    System.out.println("Error writing response to client: " + e);
	}
    }


    /** Read command line arguments and start proxy */
    public static void main(String args[]) {
	int myPort = 0;
	
	try {
	    myPort = Integer.parseInt(args[0]);
	} catch (ArrayIndexOutOfBoundsException e) {
	    System.out.println("Need port number as argument");
	    System.exit(-1);
	} catch (NumberFormatException e) {
	    System.out.println("Please give port number as integer.");
	    System.exit(-1);
	}
	
	init(myPort);

	/** Main loop. Listen for incoming connections and spawn a new
	 * thread for handling them */
	Socket client = null;
	
	
	while (true) {
	    try {
		client = socket.accept();																		//Initialize server socket to listen to client browser
		handle(client);
	    } catch (IOException e) {
		System.out.println("Error reading request from client: " + e);
		/* Definitely cannot continue processing this request,
		 * so skip to next iteration of while loop. */
		continue;
	    }
	}

    }
}
