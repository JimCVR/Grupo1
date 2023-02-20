package interfaces;

import classes.AccountImpl;
import classes.ClientImpl;

public interface ATM {
    //Crear una nueva cuenta
    ClientImpl createAccount();

    //Crear un nuevo cliente
    ClientImpl createClient();

    //devuelve array con todas las cuentas existentes
    //String getAccount(ClientImpl client);

    //Permite logearnos en el banco con una cuenta ya existente
    ClientImpl login(String dni, String password);

    //Borrar cuenta existente
    void deleteAccount(ClientImpl client);

    //Método para poder encontrar al otro cliente al que se quiera realizar la transacción
    AccountImpl searchAccount(String dni);

}
