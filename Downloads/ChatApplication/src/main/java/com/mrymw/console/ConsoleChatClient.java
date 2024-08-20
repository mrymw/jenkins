package com.mrymw.console;
import java.io.Console;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import javax.json.Json;
import javax.json.JsonObject;
import com.mrymw.endpoint.ChatClientEndpoint;

public class ConsoleChatClient {
    public static void main(final String[] args) throws InterruptedException, URISyntaxException {
        Console console = System.console();
        final String userName = console.readLine("Please enter your user name: ");
        final String roomName = console.readLine("Please enter a chat-room name: ");
        System.out.println("connecting to chat-room " + roomName);

        final ChatClientEndpoint clientEndPoint = new ChatClientEndpoint(new URI("ws://0.0.0.0:8080/hascode/chat/" + roomName));
        clientEndPoint.addMessageHandler(responseString -> {
            System.out.println(jsonMessageToString(responseString, roomName));
        });

        while (true) {
            String message = console.readLine();
            clientEndPoint.sendMessage(stringToJsonMessage(userName, message));
        }
    }

    private static String stringToJsonMessage(final String user, final String message) {
        return Json.createObjectBuilder().add("sender", user).add("message", message).build().toString();
    }

    private static String jsonMessageToString(final String response, final String roomName) {
        JsonObject root = Json.createReader(new StringReader(response)).readObject();
        String message = root.getString("message");
        String sender = root.getString("sender");
        String received = root.getString("received");
        return String.format("%s@%s: %s [%s]", sender, roomName, message, received);
    }

}
