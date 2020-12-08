package com.company.lesson4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ChatHomeWork {
    public static void main(String[] args) {
        new Chatt();
    }
}

class Chatt extends JFrame {
    private final JTextArea log;
    private final JPanel panelTop;
    private final JPanel panelBottom;
    private final JTextField textFieldMessage;
    private final JButton buttonSend;

   public Chatt(){

       panelTop = new JPanel(new GridLayout(2, 2));
       panelBottom = new JPanel(new BorderLayout());
       textFieldMessage = new JTextField();
       buttonSend = new JButton("Send");
       log = new JTextArea();
       setTitle("Test Window");
       setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
       setSize(500, 400);
       JScrollPane scrollLog = new JScrollPane(log);
       buttonSend.addActionListener(res->{
           if(!textFieldMessage.getText().equals("")){
               log.append(textFieldMessage.getText() + "\n");
               textFieldMessage.setText("");
           }
       });
       textFieldMessage.addKeyListener(new KeyAdapter() {
           @Override
           public void keyPressed(KeyEvent e) {
               if(!textFieldMessage.getText().equals("") && e.getKeyCode() == KeyEvent.VK_ENTER){
                   log.append(textFieldMessage.getText() + "\n");
                   textFieldMessage.setText("");
               }
           }
       });

       panelBottom.add(textFieldMessage, BorderLayout.CENTER);
       panelBottom.add(buttonSend, BorderLayout.EAST);
       add(scrollLog, BorderLayout.CENTER);
       add(panelTop, BorderLayout.NORTH);
       add(panelBottom, BorderLayout.SOUTH);
       log.setEditable(false);




       setVisible(true);
   }

}


