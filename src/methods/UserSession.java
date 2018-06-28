package methods;

import methods.exceptions.InvalidLoginArguments;
import methods.exceptions.RegisteredNewUser;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class UserSession {

    private String username, password, serverIP;
    private int serverPort;

    public UserSession(String username, String password, String serverIP, int serverPort) throws InvalidLoginArguments, IOException, RegisteredNewUser {

        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.username = username;
        this.password = password;
        //Anmeldedaten an Server schicken und Rückgabewert bekommen
        int login = loginToServer(username, password);


        //Falsches Passwort
        if (login == 1) {
            throw new InvalidLoginArguments();
        }
        //neuer Benutzer anlegen
        else if (login == 0) {

            new RegisteredNewUser();

        }

    }


    private int loginToServer(String username, String password) throws IOException {


        sendInfoToServer(new String[]{"login", username, password});

        //AntwortSocket aufmachen & auf Antwort warten
        ServerSocket serverSocket = new ServerSocket(34568);
        Socket socket = serverSocket.accept();

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        return Integer.parseInt(in.readLine());


    }

    public ArrayList<String> getCurrentChatRooms() throws IOException, ClassNotFoundException {
        sendInfoToServer(new String[]{"getChatrooms"});

        //AntwortSocket aufmachen und auf Antwort warten
        ServerSocket serverSocket = new ServerSocket(45450);
        Socket socket = serverSocket.accept();

        //Objekt bekommen (ArrayList)
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        ArrayList<String> input = (ArrayList<String>) in.readObject();

        serverSocket.close();


        return input;
    }


    public void sendChatMessageToServer(String message, String channel) {
        sendInfoToServer(new String[]{"msg", message, this.username, channel});
    }

    public void addNewChatroom(String name) {
        //Zeit war zu knapp für diese Methode
        sendInfoToServer(new String[]{"addNewChatroom", name});
    }


    public void sendInfoToServer(String[] message) {
        try {
            //Verbindung mit Server aufbauen
            Socket socket = new Socket(this.serverIP, this.serverPort);

            //messageObjekt senden
            ObjectOutput out = new ObjectOutputStream(socket.getOutputStream());

            out.writeObject(message);
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
