package Service;

import entity.Dictionary;
import entity.Message;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MessageGenerator {

    private ObjectMapper mapper;
    private List<Dictionary> dictionaries;
    private Scanner in;
    private String userChoice;
    private String wordDescription;


    public MessageGenerator() {
        mapper = new ObjectMapper();
        mapper.setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY);
        in = new Scanner(System.in);
        userChoice = "0";
        wordDescription = "";


    }

    public boolean userSwitching(String menuItem, BufferedReader fromServer, PrintWriter toServer) throws IOException {

        boolean isRunning = true;
        String getDescriptionJson;
        String responseFromServer;
        Message message;
        String pressKeyMessage = "Press random key to exit into the main menu";
        switch (menuItem) {
            case "1":
                getDescriptionJson = getAllDictionaries();
                toServer.println(getDescriptionJson);

                //receive result
                responseFromServer = fromServer.readLine();
                message = mapper.readValue(responseFromServer, Message.class);

                if (message.getTypeOfMessage().equals("getAllDictionaries")) {
                    dictionaries = message.getDictionaries();
                    dictionaries.forEach(i -> System.out.println(i.getWord()));

                } else
                    System.out.println(message.getTypeOfMessage());



                break;

            case "2":
                do {
                    System.out.println("Enter the word which description do u wanna get:");
                    userChoice = in.nextLine();
                } while (userChoice.equals("") || userChoice.length() < 2 || userChoice.length() > 30);

                getDescriptionJson = messageGetDescriptionByWord(userChoice);
                toServer.println(getDescriptionJson);

                //receive result
                responseFromServer = fromServer.readLine();
                message = mapper.readValue(responseFromServer, Message.class);


                //user output
                if (message.getTypeOfMessage().equals("getDescription")) {
                    System.out.println("Description for word: " + userChoice + " is "
                            + message.getDictionaries().get(0).getDescription());

                } else
                    System.out.println(message.getTypeOfMessage());


                break;

            case "3":
                System.out.println("Enter the mask to find words from db.");
                System.out.println("For example: mask ^S.+g$ will return Sing and Song words.");
                userChoice = in.nextLine();

                getDescriptionJson = getWordsByMask(userChoice);
                toServer.println(getDescriptionJson);

                //receive result
                responseFromServer = fromServer.readLine();
                message = mapper.readValue(responseFromServer, Message.class);

                if (message.getTypeOfMessage().equals("getWordsByMask")) {
                    dictionaries = message.getDictionaries();
                    dictionaries.forEach(i -> System.out.println(i.getWord()));

                } else
                    System.out.println(message.getTypeOfMessage());


                break;
            case "4":


                do {
                    System.out.println("Enter the word:");
                    userChoice = in.nextLine();
                    System.out.println("Enter the new word description:");
                    wordDescription = in.nextLine();

                } while ((userChoice.equals("") || userChoice.length() < 2 || userChoice.length() > 30) &&
                        (wordDescription.equals("") || wordDescription.length() < 2 || wordDescription.length() > 30));

                getDescriptionJson = addNewDictionary(userChoice, wordDescription);
                toServer.println(getDescriptionJson);

                //receive result
                responseFromServer = fromServer.readLine();
                message = mapper.readValue(responseFromServer, Message.class);


                //user output
                if (message.getTypeOfMessage().equals("addNewDictionary")) {
                    System.out.println("New word  has been added ");


                } else
                    System.out.println(message.getTypeOfMessage());


                break;
            case "5":
                String word;
                do {
                    System.out.println("Enter the word which u wanna to change:");
                    word = in.nextLine();

                    System.out.println("Enter the new word(Just press enter, if u don't wanna change the word name):");
                    userChoice = in.nextLine();

                    System.out.println("Enter the word description (Just press enter, if u don't wanna change the description):");
                    wordDescription = in.nextLine();

                } while ((userChoice.length() < 2 || userChoice.length() > 30) &&
                        (wordDescription.length() < 2 || wordDescription.length() > 30) &&
                        (word.equals("") || word.length() < 2 || word.length() > 30));

                getDescriptionJson = changeDictionary(word, userChoice, wordDescription);
                toServer.println(getDescriptionJson);

                //receive result
                responseFromServer = fromServer.readLine();
                message = mapper.readValue(responseFromServer, Message.class);


                //user output
                if (message.getTypeOfMessage().equals("changeDictionary")) {
                    System.out.println("Word : has been changed ");


                } else
                    System.out.println(message.getTypeOfMessage());



                break;


            case "6":
                do {
                    System.out.println("Enter the word which u wanna to delete:");
                    userChoice = in.nextLine();
                } while (userChoice.equals("") || userChoice.length() < 2 || userChoice.length() > 30);

                getDescriptionJson = deleteDictionary(userChoice);
                toServer.println(getDescriptionJson);

                //receive result
                responseFromServer = fromServer.readLine();
                message = mapper.readValue(responseFromServer, Message.class);


                //user output
                if (message.getTypeOfMessage().equals("deleteDictionary")) {
                    System.out.println("The word has been deleted " );

                } else
                    System.out.println(message.getTypeOfMessage());


                break;

            case "0":
                isRunning = false;
                getDescriptionJson = exit();
                toServer.println(getDescriptionJson);
                break;
        }

        return isRunning;
    }


    public String getAllDictionaries() {
        dictionaries = new ArrayList<>();

        Message message = new Message("getAllDictionaries", dictionaries);

        return jsonSerialization(message);
    }

    public String messageGetDescriptionByWord(String word) {
        dictionaries = new ArrayList<>();
        dictionaries.add(new Dictionary(1L, word, "test"));
        Message message = new Message("getDescription", dictionaries);

        return jsonSerialization(message);
    }

    public String addNewDictionary(String word, String description) {
        dictionaries = new ArrayList<>();
        dictionaries.add(new Dictionary(1L, word, description));
        Message message = new Message("addNewDictionary", dictionaries);

        return jsonSerialization(message);
    }


    public String changeDictionary(String word, String newWord, String newDescription) {
        dictionaries = new ArrayList<>();
        dictionaries.add(new Dictionary(1L, word, "test"));
        dictionaries.add(new Dictionary(2L, newWord, newDescription));
        Message message = new Message("changeDictionary", dictionaries);

        return jsonSerialization(message);
    }

    public String deleteDictionary(String word) {
        dictionaries = new ArrayList<>();
        dictionaries.add(new Dictionary(1L, word, "test"));

        Message message = new Message("deleteDictionary", dictionaries);

        return jsonSerialization(message);
    }

    public String getWordsByMask(String word) {
        dictionaries = new ArrayList<>();
        dictionaries.add(new Dictionary(1L, word, "test"));

        Message message = new Message("getWordsByMask", dictionaries);

        return jsonSerialization(message);
    }

    public String exit() {
        dictionaries = new ArrayList<>();


        Message message = new Message("exit", dictionaries);

        return jsonSerialization(message);
    }


    //just serialize entity to json
    private String jsonSerialization(Message message) {
        String result = null;
        try {
            result = mapper.writeValueAsString(message);
        } catch (IOException e) {
            e.printStackTrace();

        }
        return result;
    }
}
