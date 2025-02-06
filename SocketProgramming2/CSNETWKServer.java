import java.io.*;
import java.net.*;

public class CSNETWKServer
{
	public static void main(String[] args)
	{
		int nPort = Integer.parseInt(args[0]);
		ServerSocket serverSocket;
		Socket serverEndpoint1;
		Socket serverEndpoint2;

		try 
		{
			serverSocket = new ServerSocket(nPort);

			serverEndpoint1 = awaitConnection(serverSocket, nPort);
			serverEndpoint2 = awaitConnection(serverSocket, nPort);
			
			DataInputStream disReader1 = new DataInputStream(serverEndpoint1.getInputStream());
			String message1 = receiveMessage(disReader1);
			
			DataInputStream disReader2 = new DataInputStream(serverEndpoint2.getInputStream());
			String message2 = receiveMessage(disReader2);
			
			DataOutputStream dosWriter1 = new DataOutputStream(serverEndpoint1.getOutputStream());
			DataOutputStream dosWriter2 = new DataOutputStream(serverEndpoint2.getOutputStream());
			sendMessage(dosWriter1, message2);
			sendMessage(dosWriter2, message1);
			
			serverEndpoint1.close();
			serverEndpoint2.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			System.out.println("Server: Connection terminated.");
		}
	}
	
	public static Socket awaitConnection(ServerSocket serverSocket, int portNumber) throws IOException {
		System.out.println("Server: Listening on port " + portNumber + "...");
		Socket endpoint = serverSocket.accept();
		System.out.println("Server: New client connected: " + endpoint.getRemoteSocketAddress());
		
		return endpoint;
	}

	public static String receiveMessage(DataInputStream input) throws IOException {
		String username = input.readUTF();
		String message = input.readUTF();

		String fullText = "Message from " + username + ": " + message;
		return fullText;
	}

	public static void sendMessage(DataOutputStream output, String message) throws IOException {
		output.writeUTF(message);
	}
}