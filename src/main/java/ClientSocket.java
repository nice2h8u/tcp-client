import Service.MessageGenerator;
import entity.Message;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientSocket {

    private ObjectMapper objectMapper;

    private Scanner in;

    private MessageGenerator messageGenerator;


    ClientSocket() {
        objectMapper = new ObjectMapper();
        objectMapper.setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY);


        messageGenerator = new MessageGenerator();
    }


    public void run() {
        try(Socket socket  = new Socket("localhost", 20205);
                PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {

            //initialization


            in = new Scanner(System.in);

            String userChoice = userChoice = "0";
            boolean isRunning = true;



            System.out.println("Just connected to " + socket.getRemoteSocketAddress());

            do {
                System.out.println();
                System.out.println("Welcome to my tcp client. U can travel in menu, pressing the numbers below:");
                System.out.println("1:Get list of all words from server");
                System.out.println("2:Get description of word");
                System.out.println("3:Get all words that satisfying regular expression");
                System.out.println("4:Add new word to the server");
                System.out.println("5:Change the word");
                System.out.println("6:Delete the word");
                System.out.println("0:To Exit.");


                do {
                    userChoice = in.nextLine();
                    if (userChoice.equals("1")|| userChoice.equals("2")|| userChoice.equals("3")
                            || userChoice.equals("4")|| userChoice.equals("5")
                            || userChoice.equals("6")|| userChoice.equals("0"))
                        break;
                    System.out.println("Wrong character!");
                }while(true);

                isRunning = messageGenerator.userSwitching(userChoice,fromServer,toServer);



            }while (isRunning);


        } catch (IOException e) {
            System.out.println("No response from server");


        }
    }






}