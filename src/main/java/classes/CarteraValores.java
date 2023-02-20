package classes;

import interfaces.Client;
import model.statements.Statements;

import java.util.Scanner;

public class CarteraValores {
    public static Broker[] brokers = {new Broker("65746357H"), new Broker("7573836Y"), new Broker("5348372P"),
            new Broker("7478375R"), new Broker("747382T"), new Broker("3736289G"), new Broker("7483648G")};
    private Client cliente = null;
    private Broker broker = null;
    private String activos;
    public CarteraValores(){}
    Statements sentences = new Statements();
    public CarteraValores(String activos){
        this.activos = activos;
    }

    public void setActivos(String activos) {
        this.activos = activos;
    }

    public void setBroker(Broker broker) {
        this.broker = broker;
    }

    public void setCliente(Client cliente) {
        this.cliente = cliente;
    }

    public Broker getBroker() {
        return broker;
    }

    public Client getCliente() {
        return cliente;
    }

    public String getActivos() {
        return activos;
    }

    public boolean comprarCartera(ClientImpl cliente,String activo){
        boolean flag = false;
        for(int i = 0;i<brokers.length;i++){
            if(brokers[i].getListaClientes().size()<6){
                sentences.insertCarteraValores(cliente,activo,brokers[i]);
                brokers[i].getListaClientes().add(cliente);
                flag = true;
                //sentences.updateAccount(currentClient.getAccount(),500.00);
            }
        }
        return flag;
        //System.out.println("El id de tu nueva cartera de valores es: "+ idCartera);

    }

    public void insertarBrokers(){
        for(int i = 0;i<brokers.length;i++){
            sentences.insertBroker(brokers[i]);
        }
    }
}
