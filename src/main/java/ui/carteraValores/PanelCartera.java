package ui.carteraValores;

import classes.AccountImpl;
import classes.ClientImpl;
import model.statements.Statements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelCartera extends JPanel implements ActionListener {
    private Statements stmt;
    private JButton confirm;
    private JButton atras;
    private JLabel labelActive;
    private JTextField cajaActive;
    private JLabel mensajeInicio;
    private JLabel mensajeError;


    public PanelCartera() {
        iniciarComponentes();
    }

    public void iniciarComponentes() {
        configPanels();
        configButtons();
        configTextField();
        configLabels();

    }


    private void configPanels() {
        setLayout(null);
    }

    private void configButtons() {
        confirm = new JButton();
        atras = new JButton();

        confirm.setText("Confirmar");
        confirm.setFont(new Font("source serif pro", Font.PLAIN, 16));
        confirm.setBounds(300, 350, 150, 50);
        confirm.addActionListener(this);

        atras.setText("Atrás");
        atras.setFont(new Font("source serif pro", Font.PLAIN, 16));
        atras.setBounds(50, 470, 100, 50);
        atras.addActionListener(this);

        add(atras);
        add(confirm);

    }

    private void configLabels() {
        mensajeInicio = new JLabel();
        mensajeError = new JLabel();
        labelActive = new JLabel();


        mensajeInicio.setText("Comprar Activo");
        mensajeInicio.setBounds(50, 1, 500, 100);
        mensajeInicio.setFont(new Font("source serif pro", Font.PLAIN, 30));

        mensajeError.setText("*Error: Cantidad Invalida*");
        mensajeError.setBounds(200, 400, 500, 100);
        mensajeError.setFont(new Font("Arial", Font.PLAIN, 18));
        mensajeError.setForeground(Color.RED);
        mensajeError.setVisible(false);
        mensajeError.setFont(new Font("source serif pro", Font.PLAIN, 24));

        labelActive.setText("Indique el activo a comprar");
        labelActive.setBounds(250, 130, 500, 100);
        labelActive.setFont(new Font("Arial", Font.PLAIN, 18));
        labelActive.setFont(new Font("source serif pro", Font.PLAIN, 24));


        add(mensajeInicio);
        add(mensajeError);
        add(labelActive);

    }

    private void configTextField() {
        cajaActive = new JTextField();
        cajaActive.setBounds(275, 225, 200, 30);
        cajaActive.setFont(new Font("source serif pro", Font.PLAIN, 18));

        add(cajaActive);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComponent comp = (JComponent) e.getSource();
        JButton source = (JButton) e.getSource();
        Window win = SwingUtilities.getWindowAncestor(comp);
        stmt = new Statements();
        //CUENTA DE PRUEBA
        AccountImpl acc = new AccountImpl("1", "jaime", 1000);
        acc.setNumAccount("ES2259846129383011979874");
        try {
            switch (source.getText()) {
                case "Confirmar":
                    String input = cajaActive.getText();
                    double ammount = Double.parseDouble(input);

                    if (true) {
                        mensajeError.setVisible(false);
                        win.dispose();
                        setVisible(false);
                    } else {
                        mensajeError.setVisible(true);
                        throw new NullPointerException();
                    }
                    break;
                case "Atrás":
                    //vuelve a la ventana anterior
                    break;
            }
        } catch (NullPointerException ex) {
            System.err.println("");
        }
    }
}