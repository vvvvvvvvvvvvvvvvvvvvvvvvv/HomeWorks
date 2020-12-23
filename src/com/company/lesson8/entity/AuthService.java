package com.company.lesson8.entity;





import java.util.ArrayList;
import java.util.List;

public class AuthService {
    public List<Client> clients = new ArrayList<>();

    public AuthService(){
        clients.add(new Client("oleg", "1234"));
        clients.add(new Client("ali", "1234"));
        clients.add(new Client("petr", "1234"));
    }

   public synchronized Client auth(String login, String password){
        for (int i = 0; i < clients.size(); i++) {
            Client client = clients.get(i);
            if (client.login.equals(login) && client.password.equals(password)){
                return client;
            }
        }
        return null;
    }
}
