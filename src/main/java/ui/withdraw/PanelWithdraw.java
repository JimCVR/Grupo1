package ui.withdraw;

import classes.AccountImpl;
import classes.ClientImpl;
import model.statements.Statements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelWithdraw extends JPanel implements ActionListener {
    private Statements stmt;
    private JButton confirm;
    private JButton atras;
    private JLabel labelAmmount;
    private JLabel labelAccount;
    private JTextField cajaAmmount;
    private JTextField cajaAccount;
    private JLabel mensajeInicio;
    private JLabel mensajeError;


    public PanelWithdraw() {
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
        labelAmmount = new JLabel();
        labelAccount = new JLabel();

        mensajeInicio.setText("Retirar Dinero");
        mensajeInicio.setBounds(50, 1, 500, 100);
        mensajeInicio.setFont(new Font("source serif pro", Font.PLAIN, 30));

        mensajeError.setText("*Error: Cantidad Invalida*");
        mensajeError.setBounds(200, 400, 500, 100);
        mensajeError.setFont(new Font("Arial", Font.PLAIN, 18));
        mensajeError.setForeground(Color.RED);
        mensajeError.setVisible(false);
        mensajeError.setFont(new Font("source serif pro", Font.PLAIN, 24));

        labelAccount.setText("Indique el numero de cuenta");
        labelAccount.setBounds(225, 100, 500, 100);
        labelAccount.setFont(new Font("Arial", Font.PLAIN, 18));
        labelAccount.setFont(new Font("source serif pro", Font.PLAIN, 24));

        labelAmmount.setText("Indique la cantidad a retirar");
        labelAmmount.setBounds(225, 200, 500, 100);
        labelAmmount.setFont(new Font("Arial", Font.PLAIN, 18));
        labelAmmount.setFont(new Font("source serif pro", Font.PLAIN, 24));

        add(mensajeInicio);
        add(mensajeError);
        add(labelAccount);
        add(labelAmmount);

    }

    private void configTextField() {
        cajaAmmount = new JTextField();
        cajaAccount = new JTextField();

        cajaAmmount.setBounds(275, 175, 200, 30);
        cajaAmmount.setFont(new Font("source serif pro", Font.PLAIN, 18));

        cajaAccount.setBounds(275, 275, 200, 30);
        cajaAccount.setFont(new Font("source serif pro", Font.PLAIN, 18));

        add(cajaAccount);
        add(cajaAmmount);

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
                    String input = cajaAmmount.getText();
                    double ammount = Double.parseDouble(input);

                    if (stmt.withdrawMoney(acc, new ClientImpl("1", "1", "1"), ammount)) {
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
