import java.io.*;
import java.net.*;

public class CSNETWKClient
{
	private static String OUTPUT_FILE = "Received.txt";

	public static void main(String[] args)
	{
		String sServerAddress = args[0];
		int nPort = Integer.parseInt(args[1]);
		
		System.out.println("Client: Connecting to server at /" + sServerAddress + ":" + nPort);

		try
		{
			Socket clientEndpoint = new Socket(sServerAddress, nPort);
			
			System.out.println("Client: Connected to server at " + clientEndpoint.getRemoteSocketAddress());
		
			DataInputStream disReader = new DataInputStream(clientEndpoint.getInputStream());
			readFile(disReader, OUTPUT_FILE);

			clientEndpoint.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			System.out.println("Client: Connection is terminated.");
		}
	}

	public static void readFile(DataInputStream input, String outputFile) throws IOException{
		String fileContents = input.readUTF();
		try(PrintWriter fileWriter = new PrintWriter(outputFile)){
			fileWriter.print(fileContents);
		}
		System.out.println("Downloaded file \"" + OUTPUT_FILE + "\"");
	}
}