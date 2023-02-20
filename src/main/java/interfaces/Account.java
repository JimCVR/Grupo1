package interfaces;

import classes.AccountImpl;
import classes.ClientImpl;

public interface Account {
    //Retirar dinero
    Double withdrawAmount(AccountImpl account,double amount );

    //Ingresar cantidad
    Double depositAmount(AccountImpl account);

}
