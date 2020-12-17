package com.company.lesson7;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Chat {
    Socket socket;
    DataInputStream in;
    DataOutputStream out;
    Scanner scanner;
    Chat(){
        try {
            socket = new Socket("localhost", 8087);
            in = new DataInputStream(socket.getInputStream());
            new Thread(() ->{
                while (true){
                    try {
                        String newMessage = in.readUTF();
                        System.out.println(newMessage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            out = new DataOutputStream(socket.getOutputStream());
            scanner = new Scanner(System.in);
            new Thread(() ->{
                while (true){
                    String newMessage = scanner.nextLine();
                    try {
                        out.writeUTF(newMessage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Chat();
    }
}
