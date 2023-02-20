package classes;

import interfaces.Client;

import java.util.ArrayList;

public class Broker {
    private String fullname;
    private String dni;
    private ArrayList<Client> listaClientes = new ArrayList<Client>(5);
    private int tlf;

    public Broker(){}
    public Broker(String dni){
        this.dni = dni;
    }
    public Broker(String fullname, String dni){
        this.fullname = fullname;
        this.dni = dni;
    }
    public Broker(String fullname, String dni, int tlf){
        this.fullname = fullname;
        this.dni = dni;
        this.tlf = tlf;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setTlf(int tlf) {
        this.tlf = tlf;
    }

    public void setListaClientes(ArrayList<Client> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public int getTlf() {
        return tlf;
    }

    public String getDni() {
        return dni;
    }

    public String getFullname() {
        return fullname;
    }

    public ArrayList<Client> getListaClientes() {
        return listaClientes;
    }
}
