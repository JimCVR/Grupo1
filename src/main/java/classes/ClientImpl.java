package classes;

import interfaces.Account;
import interfaces.Client;
import model.statements.Statements;

import java.util.*;

public class ClientImpl implements Client {
    //Declaramos sus variables privadas y un scanner static
    private String dni="";
    private String name;
    private String password;
    private String surnames;
    private double salary;
    private List<AccountImpl> account;

    private Prestamo prestamo = null;

    public static Scanner sc = new Scanner(System.in);

    //Declaramos los constructores uno vacio, otro con (dni,nnombre y los apellidos),otro que reciba dni,nombre,apellidos y salario
    public ClientImpl() {
    }

    public ClientImpl(String dni, String name, String surnames) {
        this.dni = dni;
        this.name = name;
        this.surnames = surnames;
        this.salary = 0;
        this.account = new ArrayList<AccountImpl>();
        account.add(new AccountImpl(dni,name));
    }

    public ClientImpl(String dni, String password, String name, String surnames, double salary) {
        this.dni = dni;
        this.password = password;
        this.name = name;
        this.surnames = surnames;
        this.salary = salary;
        this.account = new ArrayList<AccountImpl>();
    }

    public ClientImpl(String dni, String password, String name, String surnames, double salary, List<AccountImpl> accountList) {
        this.dni = dni;
        this.password = password;
        this.name = name;
        this.surnames = surnames;
        this.salary = salary;
        this.account = accountList;
    }

    //GETTERS AND SETTERS
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<AccountImpl> getAccount() {
        return account;
    }

    public void setAccount(List<AccountImpl> account) {
        this.account = account;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public String returnUserDni() {
        System.out.println("Introduzca su dni: ");
        return sc.nextLine();
    }

    public String returnUserPassword() {
        System.out.println("Introduzca su contraseña: ");
        return sc.nextLine();
    }

    public String returnNumAccount() {
        System.out.println("Introduzca el numero de cuenta: ");
        return sc.nextLine();
    }


    //Método que devuelve un string con la contraseña descifrada
    private String decrypt() {
        String decrypted = "";
        for (int i = 0; i < password.length(); i++) {
            decrypted += (char) (password.charAt(i) - 3);
        }
        return decrypted;
    }

    //Metodo para pedir presamo
    public void getALoan() {
        try {
            //Mediante un try pedimos la cantidad del presamo (si se introduce en negativo se considera positivo)
            System.out.println("Introduzca importe del prestamo:");
            double prestamo = Math.abs(sc.nextDouble());
            //Se introduce las mensualidades
            System.out.println("Introduzca la cantidad de plazos a pagar (meses):");
            int meses = sc.nextInt();
            //Esto para limpiar el buffer
            sc.nextLine();
            //Guardamos en una variable el resultado de nuestro metodo para calcular el interes
            Prestamo pres = new Prestamo(this,prestamo,meses);
            double mensualidad = calculateDebt(pres);
            //Si la mensualidad es menor que el salario y pintamos esto
            if (mensualidad < salary) {
                this.prestamo = pres;
                System.out.println("Usted cumple los requisitos para adquirir el prestamo \n");
                System.out.println("Su cuantía mensual es: "+mensualidad+"\n");
                Statements s = new Statements();
                ClientImpl cl = new ClientImpl(dni,name,password,surnames,salary);
                pres.setCliente(cl);
                s.insertPrestamo(pres,cl);
                //Sino pintamos lo siquiente
            } else {
                System.out.println("Lo sentimos, usted no cumple los requisitos para adquirir el prestamo");
            }
            //En el caso de no poder hacer lo del try printamos el mensaje de error y ejecutamos el metodo de nuevo
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
            getALoan();
        }
    }
    //Metodo privado para calcular el interes del prestamo (recibe el prestamo y los meses)
    private double calculateDebt(Prestamo prestamo) {
        return (prestamo.getIntereses()* prestamo.getCuantia())/prestamo.getMeses();
    }

    //Declaramos el metodo substring
    @Override
    public String toString() {
        return "ClientImpl{" +
                "dni='" + dni + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", surnames='" + surnames + '\'' +
                ", salary=" + salary +
                ", account=" + account +
                ", prestamo=" + prestamo +
                '}';
    }
}
