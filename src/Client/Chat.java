package Client;

import methods.ReceiveMessages;
import methods.UserSession;
import methods.exceptions.InvalidLoginArguments;
import methods.exceptions.RegisteredNewUser;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class Chat {

    private static String server, ip, username, password;
    private static int port;
    
    static UserSession userSession;
    
    static JTabbedPane tabbedPane;
    
    static ArrayList<JTextArea> chatHistorys;
    static ArrayList<JTextField> messageFields;
    static ArrayList<JScrollBar> scrollBars;
    
    //Chat-Frame
    private JFrame frame;
    
    static ArrayList<String> chatrooms;
    
    //Standardschrift
    final Font font = new Font("Tahoma", Font.PLAIN, 40);
    
    //ChatroomGUI (Verwaltet die GUI, zeigt Nachrichten im jeweiligen Chatroom an, etc.)
    ChatroomGUI cgi;

    public Chat() throws IOException, ClassNotFoundException {
        initialize();
    }

    public static void main(String[] args) {
    	//Neuer Thread wird gestartet
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    //LOGIN-Fenster initialisieren/konfigurieren und sichtbar machen
                	
                    Login loginWindow = new Login();
                    loginWindow.frame.setVisible(true);
                    loginWindow.btnLogIn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent arg0) {
                        	//LOGIN-Daten werden nach Eingabe in Variablen übernommen
                            server = loginWindow.txtServer.getText();
                            try {
                            	//IP und Port wird getrennt
                                String[] s = server.split(":");
                                ip = s[0];
                                port = Integer.parseInt(s[1]);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            username = loginWindow.txtUsername.getText();
                            password = loginWindow.txtPassword.getText();
                            try {
                            	//Verbindungsversuch zum Server
                                userSession = new UserSession(username, password, ip, port);

                            } catch (InvalidLoginArguments e) {
                            	//Falsche Login-Daten
                            } catch (IOException e) {
                            	//Keine Verbindung
                                e.printStackTrace();
                            } catch (RegisteredNewUser e) {
                            	//User nicht vorhanden, Registration erfolgt und wird angezeigt
                            }
                            //Login-Window wird unsichtbar
                            loginWindow.frame.setVisible(false);

                            //CHAT-Window initalisieren
                            Chat chatWindow = null;
                            try {
                                chatWindow = new Chat();
                            } catch (IOException e) {
                            	//Keine Verbindung
                                e.printStackTrace();
                            } catch (ClassNotFoundException e) {
                            	//Client kennt eingehende �bertragung einer Klasse nicht
                                e.printStackTrace();
                            }
                            //Chat-Window wird sichtbar
                            chatWindow.frame.setVisible(true);


                        }
                    });
                } catch (Exception e) {
                	//Fehlermeldung
                    e.printStackTrace();
                }
            }
        });
    }

    private void initialize() throws IOException, ClassNotFoundException {
    	//Gr��e des Fensters wird an Bildschirm angepasst
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frame = new JFrame();
        frame.setBounds(100, 100, screen.width / 2, screen.height / 2);
        frame.setLocation(screen.width / 2 - frame.getSize().width / 2, screen.height / 2 - frame.getSize().height / 2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatHistorys = new ArrayList<JTextArea>();
        messageFields = new ArrayList<JTextField>();
        scrollBars = new ArrayList<JScrollBar>();

        //Initalisieren des JTabbedPane Elements, um die Chatrooms in Tabs anzuzeigen
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        
        tabbedPane.setFont(font);
        //Standarthintergrundfarbe eines Tabs
        Color normal = tabbedPane.getBackground();
        //Hintergrundfarbe wiederherstellen, sollte ein leuchtender Chatroom eingesehen werden
        tabbedPane.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent arg0) {
            	if (normal != tabbedPane.getBackgroundAt(tabbedPane.getSelectedIndex())) {
                        tabbedPane.setBackground(normal);
            	}
            }
        });

        //Chatrooms des Servers abfragen
        chatrooms = userSession.getCurrentChatRooms();
        System.out.println(chatrooms);

        //ChatroomGUI initialisieren
        cgi = new ChatroomGUI(chatHistorys, messageFields, scrollBars, tabbedPane, userSession, chatrooms);
        //Chatrooms in der GUI darstellen
        cgi.addCurrentChatrooms(chatrooms);
        
        //Tabs dem Frame hinzuf�gen
        frame.setContentPane(tabbedPane);

        //Thread beginnen, um eingehende Nachrichten zu empfangen
        ReceiveMessages rmsg = new ReceiveMessages(cgi, tabbedPane, username, userSession, chatrooms);
        rmsg.start();
    }
}