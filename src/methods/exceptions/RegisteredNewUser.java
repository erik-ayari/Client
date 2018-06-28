package methods.exceptions;

import javax.swing.*;

public class RegisteredNewUser extends Throwable {

    public RegisteredNewUser() {
        JFrame error = new JFrame("Neue Registration");
        JOptionPane.showMessageDialog(error,
                "Ein neuer Account wurde erstellt!");
    }

}