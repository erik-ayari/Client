package Client;

import javax.swing.*;
import java.awt.*;

public class Login {
	
	//Login-Frame
    JFrame frame;
    //GUI-Elemente für Anmeldedaten und Login
    JTextField txtServer, txtUsername;
    JPasswordField txtPassword;
    JButton btnLogIn;
    Font font = new Font("Tahoma", Font.PLAIN, 40);
    Font font2 = new Font("Tamoha", Font.PLAIN, 30);

    public Login() {
        initialize();
    }

    private void initialize() {
    	//Fenster initialisieren/designen
        frame = new JFrame();
        frame.setResizable(false);
        frame.setBounds(100, 100, 300, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));

        //Überschrift
        JLabel lblLogIn = new JLabel("Log In");
        lblLogIn.setFont(font);
        lblLogIn.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(lblLogIn);
        
        //Server-Label
        JLabel lblServer = new JLabel("Server:");
        lblServer.setFont(font2);
        frame.getContentPane().add(lblServer);

        //Server-Feld
        txtServer = new JTextField();
        txtServer.setFont(font2);
        frame.getContentPane().add(txtServer);
        txtServer.setColumns(10);
        
        //Username-Label
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(font2);
        frame.getContentPane().add(lblUsername);

        //Username-Feld
        txtUsername = new JTextField();
        txtUsername.setFont(font2);
        frame.getContentPane().add(txtUsername);
        txtUsername.setColumns(10);

        //Password-Label
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(font2);
        frame.getContentPane().add(lblPassword);
        
        //Password-Feld
        txtPassword = new JPasswordField();
        txtPassword.setFont(font2);
        frame.getContentPane().add(txtPassword);
        txtPassword.setColumns(10);

        //Enter-Button
        btnLogIn = new JButton("Enter");
        btnLogIn.setFont(new Font("Tahoma", Font.PLAIN, 40));
        frame.getContentPane().add(btnLogIn);

    }

}