package classes;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.time.format.DateTimeFormatter.ISO_DATE;

public class ClientVip extends ClientImpl {

    private ClientImpl cliente;
    private String expiration;
    static Calendar calendar = Calendar.getInstance();
    static LocalDate now = LocalDate.now();
    static LocalDate expirationDate  = now.plusDays(30);

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public static Date addDays(Date fecha, int dias) {
        if (dias == 0) return fecha;
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_YEAR, dias);
        return calendar.getTime();
    }

    //Creamos los constructores uno vacio, otro que reciba (expiration) y otro ultimo que reciba todos los atributos
    public ClientVip() {

    }

    public ClientVip(ClientImpl cliente, String expiration) {
        super(cliente.getDni(), cliente.getName(),cliente.getSurnames());
        this.expiration = expiration;
    }

    public ClientVip(String dni, String name, String apellidos, List<AccountImpl> account) {
        super(dni, name, apellidos);
        LocalDate now = LocalDate.now();
        LocalDate expirationDate  = now.plusDays(30);
        this.expiration = (expirationDate.format(ISO_DATE));
    }


    public ClientVip convertClientVip(ClientImpl client) {
        //Creamos un cliente vip y le seteamos los valores del cliente
        ClientVip newClientVip = new ClientVip(client.getDni(), client.getName(), client.getSurnames(),client.getAccount());
        //Y le seteamos los dias de expiracion llamando la metodo para añadir dias
        newClientVip.setExpiration(expirationDate.format(ISO_DATE));
        return newClientVip;
    }

    //Generamos el metodo toString() y llamamos al toString() de clienteVip
    @Override
    public String toString() {
        return super.toString() + " Eres cliente VIP{" +
                "expiration='" + expiration + '\'' +
                '}';
    }

    public void setCliente(ClientImpl cliente){
        this.cliente = cliente;
    }

    public ClientImpl getCliente(){
        return cliente;
    }
}
