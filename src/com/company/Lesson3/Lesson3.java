package com.company.Lesson3;

import java.util.*;

class Lesson3 {
    static String[] words = {"Jack", "Connor", "Harry", "Michael", "Thomas", "Jack",
            "Harry", "Thomas", "William", "Oscar", "Rhys"};
    static HashSet<String> unique;
    public static void main(String[] args) {
        System.out.println(Arrays.asList(words));
        unique = new HashSet<>(Arrays.asList(words));
        System.out.println(unique);
        Map<String, Integer> repeatWords = new HashMap<>();
        for (String word : words) {
            if(repeatWords.containsKey(word)){
                repeatWords.put(word, repeatWords.get(word) + 1);
            }else {
                repeatWords.put(word, 1);
            }
        }
        System.out.println("Each word " + repeatWords);

        Data data = new Data();
        data.add("Ivan", "+8928883213");
        data.add("Ivan", "+89464644674");
        data.add("Petr", "+8935434535");
        data.add("Michail", "+892234224");
        System.out.println(data.get("Ivan"));
        System.out.println(data.get("Petr"));

    }
}

class Data {
    private final Map<String, ArrayList<String>> phoneBook = new HashMap<>();
    public void add(String name, String number){
        ArrayList<String> numberList = new ArrayList<>();
        if(phoneBook.containsKey(name)){
            numberList = phoneBook.get(name);
            numberList.add(number);
        } else {
            numberList.add(number);
            phoneBook.put(name, numberList);
        }

    }
    public String get(String name){
        return name + " " + phoneBook.get(name);
    }
}
