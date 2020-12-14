package com.company.lesson6;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class EchoServer {
  static  AtomicInteger clientCount = new AtomicInteger(0);

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8004);
            Socket socket = serverSocket.accept();
            new ClientHandler(socket).start();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

class ClientHandler extends Thread {
    Socket socket;
    Scanner scanner;
    ClientHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {

            EchoServer.clientCount.incrementAndGet();
            System.out.println("Новый клиент №" + EchoServer.clientCount.get());
            DataInputStream dataInputStream = null;
            dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            scanner = new Scanner(System.in);
            Thread writeThread = new Thread(() -> {
               while (true){
                   try {
                       String text = scanner.nextLine();
                       dataOutputStream.writeUTF(text);
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }
            });
            writeThread.setDaemon(true);
            writeThread.start();

            while (true) {
                String text = dataInputStream.readUTF();
                System.out.println(text);
            }
        }
        catch (EOFException e){

        }
        catch (IOException e){

        }
        finally {
            System.out.println("Клиент отключился");
            EchoServer.clientCount.decrementAndGet();
        }
    }
}

class EchoClient{
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8004);

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            String line;
            Scanner scanner = new Scanner(System.in);
            Thread clientThread = new Thread(() -> {
                while (true) {
                    String text = scanner.nextLine();
                    try {
                        dataOutputStream.writeUTF(text);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            clientThread.setDaemon(true);
            clientThread.start();
            while (true){
                line = dataInputStream.readUTF();
                if(line.equals("stop")){
                    break;
                }
                System.out.println("Server: " + line);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}