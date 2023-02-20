package ui.withdraw;

import ui.login.PanelLogin;
import ui.login.VentanaLogin;

import javax.swing.*;

public class VentanaWithdraw extends JFrame {
    private PanelWithdraw panelWithdraw;

    public VentanaWithdraw() {
        configurarVentana();
        panelWithdraw = new PanelWithdraw();
        getContentPane().add(panelWithdraw);
        setVisible(true);
    }

    public void configurarVentana() {
        setTitle("Retirar Dinero");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        VentanaWithdraw v = new VentanaWithdraw();
    }
}
