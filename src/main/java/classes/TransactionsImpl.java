package classes;

import interfaces.Transactions;
import model.statements.Statements;

import static classes.ClientImpl.sc;

public class TransactionsImpl implements Transactions {
    private String id;
    private String paymentConcept;
    private String date;
    private double amount;

    //Declaramos los constructores uno vacio,otro que recibe todos los atributos, otro que reciba el concepto y el dinero
    public TransactionsImpl() {

    }

    public TransactionsImpl(String id, String date, String paymentConcept, double amount, String numCuenta) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.paymentConcept = paymentConcept;
    }

    public TransactionsImpl(String concept, double amount) {
        this.paymentConcept = concept;
        this.amount = amount;

    }

    //LLamar en el menu
    public Double askForAmount (){
        //Preguntamos la cantidad y la guardamos en una variable (Si se introduce en negativo, cuenta como positivo)
        System.out.println("Introduzca la cantidad de dinero para realizar la transacción:");
        double amount = Math.abs(sc.nextDouble());
        sc.nextLine();
        return amount;
    }

    public String returnAccountTransaction(){
        Statements sentences= new Statements();
        String num_cuenta = "";
        while (("").equals(num_cuenta)) {
            System.out.println("Introduzca el numero de la cuenta a la que quiere realizar la transacción:");
            num_cuenta = sc.nextLine();
            if (sentences.existAccountNumber(num_cuenta)) {
                return num_cuenta;
            }else{
                returnAccountTransaction();
            }
        }
        return num_cuenta;
    }

    //Declaramos el metodo para realizar transaciones que reciba la cuenta actual y la cuenta objetivo
    public void makeTransactions(AccountImpl currentAccount, AccountImpl targetAccount) {
        //Si la cantidad de dinero del usuario es mayor que la cantidad introducida se seteara el valor a la cuenta objetivo y a la actual
        if (currentAccount.getSalary() >= amount) {
            targetAccount.setSalary(targetAccount.getSalary() + amount);
            currentAccount.setSalary(currentAccount.getSalary() - amount);
            //Pintamos los datos del recibo de la cuenta objetiva
            System.out.println("El dinero se envio correctamente a " + targetAccount.getOwner() + " con número de cuenta: " + targetAccount.getNumAccount());
        } else {
            System.out.println("No dispone de los fondos necesarios para realizar esta transacción");
        }
    }
    //Declaramos el metodo toString para pintar los datos del objeto
    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", concept='" + paymentConcept + '\'' +
                ", date='" + date + '\'' +
                ", amount=" + amount +
                ", receptor=" + +
                '}';
    }
}
