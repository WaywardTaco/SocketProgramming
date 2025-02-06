import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CSNETWKServer
{
	private static final String OUTPUT_FILE = "Download.txt";

	public static void main(String[] args)
	{
		int nPort = Integer.parseInt(args[0]);
		System.out.println("Server: Listening on port " + args[0] + "...");
		ServerSocket serverSocket;
		Socket serverEndpoint;

		try 
		{
			serverSocket = new ServerSocket(nPort);
			serverEndpoint = serverSocket.accept();
			
			System.out.println("Server: New client connected: " + serverEndpoint.getRemoteSocketAddress());
			
			DataOutputStream dosWriter = new DataOutputStream(serverEndpoint.getOutputStream());
			sendFile(dosWriter, OUTPUT_FILE);
			
			serverEndpoint.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			System.out.println("Server: Connection is terminated.");
		}
	}
	
	public static void sendFile(DataOutputStream output, String filepath) throws IOException{
		List<String> fileLines = Files.readAllLines(Paths.get(filepath));
		String fileContents = String.join("\n", fileLines);
		
		System.out.println("Server: Sending file \"" + filepath + "\" (" + fileContents.length() + " bytes)");
		output.writeUTF(fileContents);
	}
}