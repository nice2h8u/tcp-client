import org.json.JSONObject;

import java.io.*;
import java.net.*;

public class ClientSocket {

    public void run()  {
        try {
            int serverPort = 20205;
            InetAddress host = InetAddress.getByName("localhost");
            System.out.println("Connecting to server on port " + serverPort);

            Socket socket = new Socket(host,serverPort);
            JSONObject json = new JSONObject();
            json.put("key", new Dictionary(1L,"sing","music+voice"));

            System.out.println("Just connected to " + socket.getRemoteSocketAddress());


            BufferedReader fromServer =
                    new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
            PrintWriter toServer =
                    new PrintWriter(socket.getOutputStream(),true);

            for (int i=0;i<2;i++) {


                toServer.println("{\"id\":4,\"word\":\"sing\",\"description\":[\"subscriber\"]}");


                //toServer.println("Hello 2 " );
                Thread.sleep(100);
                String line = fromServer.readLine();
                System.out.println("Client received: " + line + " from Server");
                Thread.sleep(100);
            }
            toServer.close();
            fromServer.close();
            socket.close();
        }
        catch(UnknownHostException ex) {
            ex.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(InterruptedException e){
           e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ClientSocket client = new ClientSocket();
        client.run();
    }
}