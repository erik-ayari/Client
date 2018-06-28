package Client;

import methods.UserSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;

public class ChatroomGUI {

	//ArrayLists der Chatverlauf-Elemente/Nachrichteneingabe-Elemente/Scroll-Elemente
    ArrayList<JTextArea> chatHistorys;
    ArrayList<JTextField> messageFields;
    ArrayList<JScrollBar> scrollBars;
    
    //Tab-Element für Chatrooms
    JTabbedPane tabbedPane;
    
    //UserSession (sendet u. A. Nachrichten)
    UserSession userSession;
    
    //Liste der Chatrooms
    ArrayList<String> chatrooms;
    
    //Standardschrift
    Font font = new Font("Tahoma", Font.PLAIN, 40);

    //Konstruktor
    public ChatroomGUI(ArrayList<JTextArea> chatHistorys, ArrayList<JTextField> messageFields, ArrayList<JScrollBar> scrollBars, JTabbedPane tabbedPane, UserSession userSession, ArrayList<String> chatrooms) {
        this.chatHistorys = chatHistorys;
        this.messageFields = messageFields;
        this.scrollBars = scrollBars;
        this.tabbedPane = tabbedPane;
        this.userSession = userSession;
        this.chatrooms = chatrooms;
    }

    //Gibt gewünschten Chatverlauf nach Tab-Index zurück
    public JTextArea getChatHistory(int index) {
        return chatHistorys.get(index);
    }

    //Gibt gewünschtes Nachrichtenfeld nach Tab-Index zurück
    public JTextField getMessageField(int index) {
        return messageFields.get(index);
    }
    
    //Gibt gewünschte ScrollBar nach Tab-Index zurück
    public JScrollBar getScrollBar(int index) {
    	return scrollBars.get(index);
    }

    //Gibt Names eines Chatroom nach Tab-Index zurück
    public String findTitlebyIndex(int index) {

        return tabbedPane.getTitleAt(index);

    }

  //Gibt Tab-Index eines Chatrooms zurück
    public int findTabByName(String title) {
        int tabCount = tabbedPane.getTabCount();
        for (int i = 0; i < tabCount; i++) {
            String tabTitle = tabbedPane.getTitleAt(i);
            if (tabTitle.equals(title)) return i;
        }
        return -1;
    }

    //Fügt Chatrooms des Servers hinzu
    public void addCurrentChatrooms(ArrayList<String> chats) {
    	//Counter
        int i = 0;
        //Erstellt einen kompletten 'Chat-Tab' für jeden Chatroom in der ArrayList
        for (String chat: chats) {
        	//Panel wird initialisiert/Layout zugewiesen
            JPanel panel = new JPanel(new BorderLayout());
            //Chatverlauf wird initilisiert/designt
            JTextArea chatHistory = new JTextArea();
            chatHistory.setFont(font);
            chatHistory.setEditable(false);
            //Chatverlauf wird der ArrayList hinzugefügt
            chatHistorys.add(chatHistory);
            //Scroll-Pane/Scroll-Bar wird zum Chatverlauf hinzugefügt
            JScrollPane jsp = new JScrollPane(chatHistorys.get(i));
            JScrollBar vertical = jsp.getVerticalScrollBar();
            //ScrollBar wird der ArrayList hinzugefügt
            scrollBars.add(vertical);
            //Nachrichtenfeld wird designt, der ArrayList hinzugefügt und erhält eine(n) ActionLister/Action, um Nachricht zu senden
            JTextField messageField = new JTextField();
            messageField.setFont(font);
			messageFields.add(messageField);
            final int b = i; 	//Integer für die userSession.sendChatMessageToServer(..) muss final sein

            messageField.addActionListener(new AbstractAction() {

                @Override
                public void actionPerformed(ActionEvent e) {
                	userSession.sendChatMessageToServer(messageField.getText(), findTitlebyIndex(b));
                	messageField.setText("");
                }

            });
            //Elemente werden dem Panel hinzugefügt
            panel.add(jsp, BorderLayout.CENTER);
            panel.add(messageFields.get(i), BorderLayout.PAGE_END);
            //Panel wird dem Tab hinzugefügt, Tab wird benannt
            tabbedPane.addTab(chat, panel);
            i++;
        }
    }
    
    //Ursprünglich um Chatrooms hinzuzufügen; es hat uns jedoch zeitlich nicht gereicht, das komplett umzusetzen

    /*public void addPlus(int plus) {
    	JPanel addPanel = new JPanel(new BorderLayout());
        JTextField chatroomName = new JTextField();
        chatroomName.setFont(font);
        chatroomName.setText("Chatroom Name");
        JButton add = new JButton();
        add.setFont(font);
        add.setText("OK");
        add.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userSession.addNewChatroom(chatroomName.getText());
            }
        });
        addPanel.add(chatroomName, BorderLayout.PAGE_START);
        addPanel.add(add, BorderLayout.PAGE_END);
        tabbedPane.addTab("+", addPanel);
    }

    public void addChatroom(String chatroom) {
    	tabbedPane.removeAll();
    	chatrooms.add(chatroom);
    	addCurrentChatrooms(chatrooms);
    }*/
}