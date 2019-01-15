package entity;

import entity.Dictionary;

import java.util.ArrayList;

import java.util.List;

public class Message {
    private String typeOfMessage;

    private List<Dictionary> dictionaries = new ArrayList<>();

    public Message() {
    }

    public Message(String typeOfMessage, List<Dictionary> dictionaries) {
        this.typeOfMessage = typeOfMessage;
        this.dictionaries = dictionaries;
    }

    public String getTypeOfMessage() {
        return typeOfMessage;
    }

    public void setTypeOfMessage(String typeOfMessage) {
        this.typeOfMessage = typeOfMessage;
    }

    public List<Dictionary> getDictionaries() {
        return dictionaries;
    }

    public void setDictionaries(List<Dictionary> dictionaries) {
        this.dictionaries = dictionaries;
    }
}
