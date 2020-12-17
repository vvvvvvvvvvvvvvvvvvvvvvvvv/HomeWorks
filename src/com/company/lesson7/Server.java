package com.company.lesson7;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    List<ClientHandler> clients= new ArrayList<>();
    List<Message> messages = new ArrayList<>();

    Server(){
        try {
            ServerSocket serverSocket = new ServerSocket(8087);
            AuthService authService = new AuthService();
            while (true){
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    new ClientHandler(authService, this, socket);
                }).start();
            }
        } catch (IOException e) {
            System.out.println("Сервер прекратил работу с ошибкой");
            e.printStackTrace();
        }
    }

    synchronized void onClientDisconnected(ClientHandler clientHandler){
        clients.remove(clientHandler);
        onNewMessage(clientHandler.client, "Покинул чат");
    }
    synchronized void onNewMessage(Client client, String message){
        messages.add(new Message(client, message));
        //Рассылка
        for (int i = 0; i < clients.size(); i++) {
            ClientHandler recipient = clients.get(i);
            if(!recipient.client.login.equals(client.login)){
                recipient.sendMessage(client, message);
            }

        }
    }
    synchronized void onNewClient(ClientHandler clientHandler){
        onNewMessage(clientHandler.client, "Вошел в чат");
        clients.add(clientHandler);
        for (int i = 0; i <messages.size(); i++) {
            Message message = messages.get(i);
            clientHandler.sendMessage(message.client, message.text);
        }
    }


    public static void main(String[] args) {
        new Server();
    }

    public void onNewPersonalMessage(Client client, String message, String receiver) {
        messages.add(new Message(client, message));
       for (int i = 0; i < clients.size(); i++) {
            ClientHandler recipient = clients.get(i);
            if(!recipient.client.login.equals(client.login) && recipient.client.login.equals(receiver)){
                recipient.sendMessage(client, message);
            }
        }

    }
}
