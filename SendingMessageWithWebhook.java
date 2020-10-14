package com.mg.core.messaging;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendingMessageWithWebhook {
	
	public static void sendSlackMessage(String message, String webhook)
    {
		
    //For information on how to create a webhook, go to https://api.slack.com/messaging/webhooks
		try {
		URL webhook = new URL(webhook);
		
		HttpURLConnection con = (HttpURLConnection)webhook.openConnection();

		con.setRequestMethod("POST");
		con.setRequestProperty("Accept", "application/json");
		con.setDoOutput(true);

		String jsonMessage = "{ \"text\": \"" + message + "\" }";
		
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
		}
    }
}
