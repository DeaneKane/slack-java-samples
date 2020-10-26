package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendingMessageWithAPIChatPostMessage {

public static void sendSlackMessage(String message, String channel, String token)
    {
		
		try {
		URL webhook = new URL("https://slack.com/api/chat.postMessage");
		
		HttpURLConnection con = (HttpURLConnection)webhook.openConnection();

		con.setRequestMethod("POST");
		con.setRequestProperty("Authorization", "Bearer " + token);
		con.setRequestProperty("Content-Type", "application/json; utf-8");
		con.setDoOutput(true);

		String jsonMessage = "{ \"text\": \"" + message + "\", \"channel\": \"" + channel + "\", }";
		
		try(OutputStream os = con.getOutputStream()) {
		    byte[] input = jsonMessage.getBytes("utf-8");
		    os.write(input, 0, input.length);			
		}
		
		try(BufferedReader br = new BufferedReader(
				  new InputStreamReader(con.getInputStream(), "utf-8"))) {
				    StringBuilder response = new StringBuilder();
				    String responseLine = null;
				    while ((responseLine = br.readLine()) != null) {
				        response.append(responseLine.trim());
				    }
				    System.out.println(response.toString());
				}	
		} catch(IOException e) {
			System.out.println("Error sending message to slack.");
			e.printStackTrace();
		}
}
ˇ
