import java.io.*;
import java.net.*;

public class CSNETWKClient
{
	public static void main(String[] args)
	{
		String sServerAddress = args[0];
		int nPort = Integer.parseInt(args[1]);
		String sUsername = args[2];
		String sMessage = args[3];
		
		System.out.println(sUsername + ": Connecting to server at /" + sServerAddress + ":" + nPort);
		try
		{
			Socket clientEndpoint = new Socket(sServerAddress, nPort);
			
			System.out.println(sUsername + ": Connected to server at " + clientEndpoint.getRemoteSocketAddress());
			
			DataOutputStream dosWriter = new DataOutputStream(clientEndpoint.getOutputStream());
			sendMessage(dosWriter, sUsername, sMessage);

			DataInputStream disReader = new DataInputStream(clientEndpoint.getInputStream());
			receiveMessage(disReader);
			
			clientEndpoint.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			System.out.println(sUsername + ": Connection terminated.");
		}
	}

	private static void sendMessage(DataOutputStream output, String username, String message) throws IOException{
		output.writeUTF(username);
		output.writeUTF(message);
	}

	private static void receiveMessage(DataInputStream input) throws IOException{
		System.out.println(input.readUTF());
	}
}