package classes;

import interfaces.Client;

public class Prestamo {
    private ClientImpl cliente;
    private Double cuantia;
    private Double intereses;
    private int meses;

    public Prestamo(Double cuantia, int meses){

        this.cuantia = cuantia;
        this.meses = meses;
        intereses = generarIntereses();
    }

    public Prestamo(ClientImpl cliente,Double cuantia,int meses){
        this.cliente = cliente;
        this.cuantia = cuantia;
        this.meses = meses;
        intereses = generarIntereses();
    }



    public ClientImpl getCliente() {
        return cliente;
    }

    public Double getCuantia() {
        return cuantia;
    }

    public Double getIntereses() {
        return intereses;
    }

    public int getMeses() {
        return meses;
    }

    public void setCliente(ClientImpl cliente) {
        this.cliente = cliente;
    }

    public void setCuantia(Double cuantia) {
        this.cuantia = cuantia;
    }

    public void setIntereses(Double intereses) {
        this.intereses = intereses;
    }

    public void setMeses(int meses) {
        this.meses = meses;
    }

    public Double generarIntereses(){
        Double intereses;
        if(cuantia>=6000){
            if(meses<12){
                intereses = 0.30;
            } else{
                intereses = 0.70;
            }
        } else{
            if(meses<12){
            intereses = 0.15;}
            else{
                intereses = 0.20;
            }
        }
        return intereses;
    }
}
