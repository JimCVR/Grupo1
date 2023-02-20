package interfaces;

import classes.AccountImpl;

public interface Transactions {

    //Metodo para realizar los cambios monetarios necesarios en cada una de las cuentas involucradas
    public void makeTransactions(AccountImpl currentAccount, AccountImpl targetAccount);
}
