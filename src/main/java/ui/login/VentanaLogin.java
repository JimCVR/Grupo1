package ui.login;

import javax.swing.*;

public class VentanaLogin extends JFrame {
    private PanelLogin panelLogin;

    public VentanaLogin() {
        configurarVentana();
        panelLogin = new PanelLogin();
        getContentPane().add(panelLogin);
        setVisible(true);
    }

    public void configurarVentana() {
        setTitle("Inicio de sesión");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        VentanaLogin v = new VentanaLogin();
    }
}
