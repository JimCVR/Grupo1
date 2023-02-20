package ui.carteraValores;

import ui.withdraw.PanelWithdraw;
import ui.withdraw.VentanaWithdraw;

import javax.swing.*;

public class VentanaCartera extends JFrame {

    private PanelCartera panelCartera;

    public VentanaCartera() {
        configurarVentana();
        panelCartera = new PanelCartera();
        getContentPane().add(panelCartera);
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
        VentanaCartera v = new VentanaCartera();
    }
}
