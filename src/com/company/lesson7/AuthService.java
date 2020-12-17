package com.company.lesson7;

import java.util.ArrayList;
import java.util.List;

public class AuthService {
    List<Client> clients = new ArrayList<>();

    AuthService(){
        clients.add(new Client("Pavel", "oleg", "1234"));
        clients.add(new Client("Oleg", "ali", "1234"));
        clients.add(new Client("Nick", "petr", "1234"));
    }

    synchronized Client auth(String login, String password){
        for (int i = 0; i < clients.size(); i++) {
            Client client = clients.get(i);
            if (client.login.equals(login) && client.password.equals(password)){
                return client;
            }
        }
        return null;
    }
}
