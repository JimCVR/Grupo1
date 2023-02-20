package ui.login;

import classes.ClientImpl;
import model.statements.Statements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelLogin extends JPanel implements ActionListener {
    private Statements stmt;
    private JButton iniciar;
    private JButton atras;
    private JLabel labelId;
    private JLabel labelPass;
    private JTextField cajaId;
    private JTextField cajaPass;
    private JLabel mensajeInicio;
    private JLabel mensajeError;

    public PanelLogin() {
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
        iniciar = new JButton();
        atras = new JButton();

        iniciar.setText("Login");
        iniciar.setFont(new Font("source serif pro", Font.PLAIN, 16));
        iniciar.setBounds(300, 350, 150, 50);
        iniciar.addActionListener(this);

        atras.setText("Atrás");
        atras.setFont(new Font("source serif pro", Font.PLAIN, 16));
        atras.setBounds(50, 470, 100, 50);
        atras.addActionListener(this);

        add(atras);
        add(iniciar);

    }

    private void configLabels() {
        mensajeInicio = new JLabel();
        mensajeError = new JLabel();
        labelId = new JLabel();
        labelPass = new JLabel();

        mensajeInicio.setText("Iniciar Sesión");
        mensajeInicio.setBounds(50, 1, 500, 100);
        mensajeInicio.setFont(new Font("source serif pro", Font.PLAIN, 30));

        mensajeError.setText("*Error: Usuario o Constraseña incorrectos*");
        mensajeError.setBounds(200, 400, 500, 100);
        mensajeError.setFont(new Font("Arial", Font.PLAIN, 18));
        mensajeError.setForeground(Color.RED);
        mensajeError.setVisible(false);
        mensajeError.setFont(new Font("source serif pro", Font.PLAIN, 24));

        labelId.setText("Usuario");
        labelId.setBounds(325, 100, 500, 100);
        labelId.setFont(new Font("Arial", Font.PLAIN, 18));
        labelId.setFont(new Font("source serif pro", Font.PLAIN, 24));

        labelPass.setText("Contraseña");
        labelPass.setBounds(300, 200, 500, 100);
        labelPass.setFont(new Font("Arial", Font.PLAIN, 18));
        labelPass.setFont(new Font("source serif pro", Font.PLAIN, 24));

        add(mensajeInicio);
        add(mensajeError);
        add(labelId);
        add(labelPass);

    }

    private void configTextField() {
        cajaId = new JTextField();
        cajaPass = new JPasswordField();

        cajaId.setBounds(275, 175, 200, 30);
        cajaId.setFont(new Font("source serif pro", Font.PLAIN, 18));
        cajaPass.setBounds(275, 275, 200, 30);

        add(cajaId);
        add(cajaPass);

    }

    public JButton getIniciar() {
        return iniciar;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComponent comp = (JComponent) e.getSource();
        JButton source = (JButton) e.getSource();
        Window win = SwingUtilities.getWindowAncestor(comp);
        stmt = new Statements();

        try {
            switch (source.getText()) {
                case "Login":
                    ClientImpl client = stmt.login(cajaId.getText(), cajaPass.getText());
                    if (client != null) {
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
            System.err.println("Usuario o contraseña incorrectos");
        }
    }
}

