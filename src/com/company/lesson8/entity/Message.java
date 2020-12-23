package com.company.lesson8.entity;


public class Message {
   public Client client;
   public String text;

    public Message(Client client, String text) {
        this.client = client;
        this.text = text;
    }
}
