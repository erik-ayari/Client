package methods.exceptions;

import javax.swing.*;

public class InvalidLoginArguments extends Exception {

    public InvalidLoginArguments() {
        super("Invalid Login Arguments");
        JFrame error = new JFrame("Invalid Login Arguments");
        JOptionPane.showMessageDialog(error,
                "Your username or password is incorrect.");
    }

}