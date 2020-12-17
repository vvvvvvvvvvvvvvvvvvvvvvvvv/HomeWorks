package com.company.lesson7;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    AuthService authService;
    Server server;
    Socket socket;
    DataOutputStream out;
    DataInputStream in;
    Client client;

     ClientHandler(AuthService authService, Server server, Socket socket) {
        this.authService = authService;
        this.server = server;
        this.socket = socket;

        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            if(!auth(out, in)){
                // Удаление пользоавтеля из сервера
                socket.close();
                in.close();
                out.close();
                server.onClientDisconnected(this);
                return;
            }
            server.onNewClient(this);
            messageListener(in);



        } catch (IOException e) {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            server.onClientDisconnected(this);
            e.printStackTrace();
        }

    }


    public void sendMessage(Client client, String text){
        try {
            out.writeUTF(client.name + ": " + text);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private boolean auth(DataOutputStream out, DataInputStream in) throws IOException {
        out.writeUTF("Пожалуйста, введите логин и пароль через пробел!");
        // Ожидание авторизации
        int tryCount = 0;
        int maxTryCount = 5;
        while (true){
            String message = in.readUTF();
            String[] messData = message.split("\\s");
            //Проверяем корректность данных
            if(messData.length == 3 && messData[0].equals("/auth")){
                tryCount++;
                String login = messData[1];
                String password = messData[2];
                client = authService.auth(login, password);
                if(client != null){
                    break;
                }else {
                    out.writeUTF("Неправильный логин и пароль");
                }
            }else {
                out.writeUTF("Ошибка авторизации");
            }
            if (tryCount == maxTryCount){
                out.writeUTF("Превышен лимит попыток");
                in.close();
                out.close();
                socket.close();
                return false;
            }

        }
        return true;
    }


    private void messageListener(DataInputStream in) throws IOException {
        while (true){
            String newMessage = in.readUTF();
            String[] messArr = newMessage.split("\\s");
            if (newMessage.equals("/exit")){
                in.close();
                out.close();
                socket.close();
                server.onClientDisconnected(this);
            }else if(messArr[0].equals("/w")){
                String receiver = messArr[1];
                server.onNewPersonalMessage(client, newMessage, receiver);
            }
            else {
                server.onNewMessage(client, newMessage);
            }
        }
    }



}
