package classes;

import interfaces.ATM;
import model.statements.Statements;

import java.util.InputMismatchException;
import java.util.Scanner;


public class ATMImpl implements ATM {
    // declaramos atributos y objeto estatico scanner
    private static Scanner sc = new Scanner(System.in);
    private int id;
    private String name;
    private String city;

    TransactionsImpl transactions = new TransactionsImpl();

    //Contructores
    public ATMImpl() {
    }


    public ATMImpl(int id, String name) {
        this.id = id;
        this.name = name;
        this.city = "Cadiz";
    }

    //getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBank() {
        return city;
    }

    public void setBank(String pais) {
        this.city = city;
    }

    //Se crea un usuario y una cuenta asociada y se guarda en una lista y aparte se devuelve el objeto

    @Override
    public ClientImpl createAccount() {
        return null;
    }

    public ClientImpl createClient() {
        Scanner sc = new Scanner(System.in);
        ClientImpl newClient = new ClientImpl();
        System.out.println("Introduzca su dni:");
        newClient.setDni(sc.nextLine());
        System.out.println("Introduzca su nombre:");
        newClient.setName(sc.nextLine());
        System.out.println("Introduzca sus apellidos:");
        newClient.setSurnames(sc.nextLine());
        System.out.println("Introduzca su contraseña:");
        newClient.setPassword(sc.nextLine());
        System.out.println("Introduzca su salario");
        newClient.setSalary(sc.nextDouble());
        sc.nextLine();
        return newClient;
    }

    //Método para encriptar la contraseña y devuelve asteriscos para mayor seguridad
    private String encrypt(String pass) {
        for (int i = 0; i < pass.length(); i++) {
            pass += (char) (pass.charAt(i) + 3);
        }
        return pass;
    }

    @Override
    public ClientImpl login(String dni, String password) {
        return null;
    }

    @Override
    public void deleteAccount(ClientImpl client) {

    }

    @Override
    public AccountImpl searchAccount(String dni) {
        return null;
    }


    //Muestra el menú principal y da la opción a acceder al menú interno del cliente para logearmos,crear una cuenta o salir
    public void mainMenu() {
        ClientImpl currentClient = new ClientImpl();
        Statements sentences = new Statements();
        Scanner sc = new Scanner(System.in);

        int option = 0;
        try {
            System.out.println("****Bienvenido****");
            System.out.println("****Introduzca una opcion****");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Crear un cliente");
            System.out.println("0. Salir");
            option = sc.nextInt();
            sc.nextLine();
            switch (option) {
                case 1:
                    String dni = currentClient.returnUserDni();
                    String password = currentClient.returnUserPassword();
                    currentClient = sentences.login(dni, password);
                    sessionMenu(currentClient);
                    break;
                case 2:
                    sentences.insertCustomer();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    System.exit(200);
            }
            //Si se introduce algo que no sea un int saltara el mensaje de la excepcion y se vuelve a llamar al metodo
        } catch (InputMismatchException e) {
            System.err.println(e.getMessage());
            mainMenu();
        }
    }

    //Creación del menú del cliente donde podemos realizar las diferentes funcionalidades del programa
    public void sessionMenu(ClientImpl currentClient) {
        Scanner sc = new Scanner(System.in);
        Statements sentences = new Statements();
        CarteraValores cart = new CarteraValores();
        int option = 1;
        try {
            while (option != 0) {
                System.out.println("1. Ver cuenta/s");
                System.out.println("2. Borrar cuenta");
                System.out.println("3. Ingresar dinero");
                System.out.println("4. Retirar dinero");
                System.out.println("5. Pedir prestamo");
                System.out.println("6. Realizar transferencia");
                System.out.println("7. Convertirse en cliente vip");
                System.out.println("8. Crear una cuenta");
                System.out.println("9. Comprar cartera de valores");
                System.out.println("0. Atrás");

                System.out.println("Escribe una de las opciones:");
                option = sc.nextInt();
                sc.nextLine();
                switch (option) {
                    case 1:
                        //Si el cliente no tiene cuentas se cierra (Arreglar)
                        sentences.showAccount(currentClient);
                        break;
                    case 2:
                        sentences.deleteAccount(currentClient);
                        break;
                    case 3:
                        sentences.depositMoney(sentences.searchAccount((currentClient.returnNumAccount())), currentClient);
                        break;
                    case 4:
                        //sentences.withdrawMoney(sentences.searchAccount(currentClient.returnNumAccount()), currentClient);
                        break;
                    case 5:
                        currentClient.getALoan();
                        break;
                    case 6:
                        sentences.sendMoney(sentences.validateOwnerAccount(sentences.searchAccount(currentClient.returnNumAccount()), currentClient),sentences.searchAccount(transactions.returnAccountTransaction()),transactions.askForAmount());
                        break;
                    case 7:
                        sentences.insertCustomerVip(currentClient);
                        break;
                    case 8:
                        sentences.insertAccount(currentClient);
                        break;
                    case 9:
                        cart.comprarCartera(currentClient,"");
                        break;
                    case 0:
                        mainMenu();
                        break;
                    default:
                        System.out.println("Opción no existente");
                }
            }
            //Si se introduce algo que no sea un int saltara el mensaje de la excepcion y se vuelve a llamar al metodo
        } catch (InputMismatchException e) {
            System.err.println(e.getMessage());
            sessionMenu(currentClient);
        }
    }



}
