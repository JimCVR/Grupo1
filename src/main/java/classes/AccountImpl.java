package classes;

import interfaces.Account;

import java.text.SimpleDateFormat;
import java.util.*;

public class AccountImpl implements Account {
    //Inicializamos todas las variables privadas que vamos a utilizar en la clase y el scanner
    private String numAccount;
    private String user;
    private double salary;
    private String owner;
    private Date date;
    Scanner sc = new Scanner(System.in);

    //Creamos el primer constructor vacío
    public AccountImpl() {
        numAccount = "";
    }

    //Creamos el segundo constructor para recibir el usuario, titular y el saldo
    public AccountImpl(String user, String owner, double salary) {

        this.user = user;
        this.owner = owner;
        this.salary = salary;
        setDate();
    }

    //Creamos el tercer constructor para recibir el usuario y titular
    public AccountImpl(String user, String owner) {
        generateAccountNumber();
        this.user = user;
        this.owner = owner;
        this.salary = 0;
        setDate();
    }


    //GETTERS Y SETTERS
    public String getNumAccount() {
        return numAccount;
    }

    public void setNumAccount(String numAccount) {
        this.numAccount = numAccount;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double dinero) {
        this.salary = dinero;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    //Creamos un método para settear la fecha
    public void setDate() {
        SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        this.date = calendar.getTime();
    }

    //Método autogeneramos el número de cuenta
    public String generateAccountNumber() {
        Random r = new Random();
        numAccount = "ES" + (r.nextInt(99) + 10);
        for (int i = 0; i < 5; i++) {
            numAccount += (r.nextInt(9999) + 1000);
        }
        return numAccount;
    }



    //Creamos un toString para mostrar los valores de los atributos de la cuenta
    @Override
    public String toString() {
        return "Account{" +
                "numCuenta=" + numAccount +
                ", contraseña= ****" +
                ", dinero=" + salary +
                ", titular='" + owner + '\'' +
                ", fecha='" + date + '\'' +
                '}';
    }


    //Método para retirar dinero de la cuenta
    @Override
    public Double withdrawAmount(AccountImpl account,double amount) {
        try {
            //System.out.println("Que cantidad de dinero desea retirar");
           // amount = Math.abs(sc.nextDouble());
            //sc.nextLine();
            if (amount <= salary) {
                setSalary(salary - amount);
                System.out.println("Realizando la operacion.");
                return getSalary();
            } else {
                System.out.println("No se puede realizar la operacion, usted no tiene los fondos suficientes.");
            }
        } catch (InputMismatchException e) {
            System.err.println("El valor introducido no es valido");
            //withdrawAmount(account);
        }
        return getSalary();
    }

    //Método para ingresar dinero en la cuenta
    @Override
    public Double depositAmount(AccountImpl account) {
        System.out.println("Que cantidad de dinero desea ingresar");
        try {
            double amount = Math.abs(sc.nextDouble());
            sc.nextLine();
            setSalary(amount + salary);
            return getSalary();
        } catch (InputMismatchException e) {
            System.err.println("El valor introducido no es valido");
            depositAmount(account);
        }
        return null;
    }
}
