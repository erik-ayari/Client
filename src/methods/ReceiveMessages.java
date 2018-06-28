package methods;


import Client.ChatroomGUI;

import javax.swing.*;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ReceiveMessages extends Thread {
	
	//Thread um Nachrichten zu empfangen

    JTabbedPane tabbedPane;
    ChatroomGUI cgi;
    String username;
    UserSession userSession;
    ArrayList<String> chatrooms;
    ServerSocket serverSocket;

    //Konstruktor
    public ReceiveMessages(ChatroomGUI cgi, JTabbedPane tabbedPane, String username, UserSession userSession, ArrayList<String> chatrooms) {
        this.tabbedPane = tabbedPane;
        this.cgi = cgi;
        this.username = username;
        this.userSession = userSession;
        this.chatrooms = chatrooms;
//        try {
//            //////////// KONSTANTER PORT FÃœR TESTEN
//            //serverSocket = new ServerSocket(34568);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void run() {
        while (true) {
            try {
            	//Verbindungsversuch (festgelegter Port)
                serverSocket = new ServerSocket(55555);

                //Auf Nachricht warten und in Array speichern
                Socket socket = serverSocket.accept();
                ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
                String[] in = (String[]) inStream.readObject();
                
                //ServerSocket beenden
                serverSocket.close();

                //Chat-Nachricht (Art des Inputs wird in in[0] angegeben
                if (in[0].equals("msgDistribute")) {
                	//Nachricht, User, Chatroom in weiteren Arrays
                    String message = in[1];
                    String user = in[2];
                    String chatroom = in[3];

                    System.out.println(message+user+chatroom);

                    //Chatroom-Index durch Namen ermitteln
                    int index = cgi.findTabByName(chatroom);
                    //Elemente durch Index ermitteln
                    JTextArea chatHistory = cgi.getChatHistory(index);
                    JScrollBar scrollBar = cgi.getScrollBar(index);
                    //Ggf. eigene Nachrichten highlighten
                    if (user != username) {
                        chatHistory.append(user + ": " + message + "\n");
                    } else {
                        chatHistory.append("<b>" + user + ": " + message + "</b>");
                    }
                    //Mitscrollen
                    scrollBar.setValue(scrollBar.getMaximum());
                    if(index != tabbedPane.getSelectedIndex()) {
                        tabbedPane.setBackgroundAt(index, Color.YELLOW);
                    }
//                } else if (in[0].equals("chatroomadded")) {
//                    String chatroom = in[1];
//                    cgi.addChatroom(chatroom);
               }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}