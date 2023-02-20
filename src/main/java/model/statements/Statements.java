package model.statements;

import classes.*;
import model.Connector;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.time.format.DateTimeFormatter.ISO_DATE;


public class Statements {

    ATMImpl atm = new ATMImpl();
    AccountImpl account = new AccountImpl();
    ClientVip vip = new ClientVip();
    ClientImpl currentClient = new ClientImpl();
    TransactionsImpl transactions = new TransactionsImpl();
    static Connector connector = new Connector();
    Connection cn = connector.conectar();
    PreparedStatement stmt = null;
    ResultSet rs = null;

    public void createAllTables() {
        try {
            /* CREA UNA TABLA (SOLO EJECUTAR UNA VEZ, SI NO, DA FALLO)*/
            Statement stmt = cn.createStatement();
            //Ejecutamos la acción para crear todas las tablas y sus claves foraneas y cerramos
            stmt.executeUpdate(customerTable());
            stmt.executeUpdate(accountTable());
            stmt.executeUpdate(customerVipTable());
            stmt.executeUpdate(atmTable());
            stmt.executeUpdate(transactionsTable());
            stmt.executeUpdate(prestamosTable());
            stmt.executeUpdate(brokersTable());
            stmt.executeUpdate(carteraTable());

            stmt.executeUpdate(addForeignKeyToAccountTable());
            stmt.executeUpdate(addForeignKeyToCustomerVipTable());
            stmt.executeUpdate(addForeignKeyToTransactionsTable());
            stmt.executeUpdate(addForeignKeyToPrestamosTable());
            stmt.executeUpdate(addForeignKeyToCarteraValoresTable());

            CarteraValores cart = new CarteraValores();
            cart.insertarBrokers();
            stmt.close();
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("--LAS TABLAS YA HAN SIDO CREADAS ANTERIORMENTE--");
        }
    }

    //CAMBIA EL SALDO DE UNA CUENTA
    public void updateAccount(String numCuenta, Double ammount) {
        try {

            stmt = cn.prepareStatement("UPDATE Cuentas SET salario=? WHERE numeroCuenta=?");
            stmt.setDouble(1, ammount);
            stmt.setString(2, numCuenta);
            // asociamos los valores introducidos a los campos de la tabla

            //Ejecutamos la acción mostramos el número de filas actualizadas y cerramos
            int lines = stmt.executeUpdate();
            if (lines > 0) {
                System.out.println("Se ha actualizado el saldo correctamente");
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //CREA LA TABLA CLIENTES
    private String customerTable() {
        String query = "CREATE TABLE Clientes " +
                "(dni VARCHAR(255) not NULL, " +
                " nombre VARCHAR(255), " +
                " apellido VARCHAR(255), " +
                " contrasena VARCHAR(255), " +
                " salario DOUBLE, " +
                " PRIMARY KEY (dni));";
        return query;
    }

    //CREA LA TABLA ATM
    private String atmTable() {
        String query = "CREATE TABLE ATM " +
                "(id INTEGER auto_increment, " +
                " nombre VARCHAR(255), " +
                " ciudad VARCHAR(255), " +
                " PRIMARY KEY (id));";
        return query;
    }

    //CREA LA TABLA CUENTAS
    private String accountTable() {
        String query = "CREATE TABLE Cuentas " +
                " (numeroCuenta VARCHAR(255) not NULL, " +
                " salario DOUBLE, " +
                " nombreTitular VARCHAR(255)," +
                " usuario VARCHAR(255)," +
                " fecha DATE, " +
                " PRIMARY KEY(numeroCuenta));";
        return query;
    }

    //CREA LA TABLA CLIENTEVIP
    private String customerVipTable() {
        String query = "CREATE TABLE ClientesVip " +
                "(dni VARCHAR(255)," +
                " expiration DATE," +
                " PRIMARY KEY (dni));";
        return query;
    }

    //CREA LA TABLA TRANSACCIONES
    private String transactionsTable() {
        String query = "CREATE TABLE Transacciones " +
                "(id INTEGER auto_increment, " +
                " concepto VARCHAR(255), " +
                " fecha DATE, " +
                " cantidad DOUBLE," +
                " numeroCuenta VARCHAR(255)," +
                "idATM INTEGER," +
                " PRIMARY KEY (id));";
        return query;
    }

    //CREA LA TABLA PRÉSTAMOS
    private String prestamosTable() {
        String query = "CREATE TABLE Prestamos " +
                "(dniCliente VARCHAR(255), " +
                " meses INTEGER, " +
                " cuantia DOUBLE," +
                " fechaPedido DATE, " +
                "intereses DOUBLE," +
                " PRIMARY KEY (dniCLiente));";
        return query;
    }

    //CREA LA TABLA BRÓKERS
    private String brokersTable() {
        String query = "CREATE TABLE Brokers " +
                "(dni VARCHAR(255), " +
                " fullname VARCHAR(255), " +
                " tlf INTEGER, " +
                " PRIMARY KEY (dni));";
        return query;
    }

    //CREA LA TABLA CARTERAVALORES
    private String carteraTable() {
        String query = "CREATE TABLE CarteraValores " +
                "(id INTEGER auto_increment, " +
                "dniCliente VARCHAR(255), " +
                " dniBroker VARCHAR(255), " +
                " activos VARCHAR(255), " +
                " PRIMARY KEY (id));";
        return query;
    }


    // AGREGA LAS CLAVES FORANEAS A LA TABLA CUENTAS
    private String addForeignKeyToAccountTable() {
        String query = "ALTER TABLE Cuentas " +
                "ADD CONSTRAINT Cuentas_ibfk_1 FOREIGN KEY (usuario) REFERENCES Clientes(dni);";
        return query;
    }

    // AGREGA LAS CLAVES FORANEAS A LA TABLA CARTERAVALORES
    private String addForeignKeyToCarteraValoresTable() {
        String query = "ALTER TABLE CarteraValores " +
                "ADD CONSTRAINT Cartera_ibfk_1  FOREIGN KEY (dniCliente) REFERENCES Clientes(dni)," +
                "ADD CONSTRAINT Cartera_ibfk_2 FOREIGN KEY (dniBroker) REFERENCES Brokers(dni);";
        return query;
    }

    // AGREGA LA CLAVE FORANEA A LA TABLA PRESTAMOS
    private String addForeignKeyToPrestamosTable() {
        String query = "ALTER TABLE Prestamos " +
                "ADD CONSTRAINT Prestamos_ibfk_1 FOREIGN KEY (dniCliente) REFERENCES Clientes(dni);";
        return query;
    }

    // AGREGA LA CLAVE FORANEA A LA TABLA CLIENTES VIP
    private String addForeignKeyToCustomerVipTable() {
        String query = "ALTER TABLE ClientesVip " +
                "ADD CONSTRAINT customerVip_ibfk_1 FOREIGN KEY (dni) REFERENCES Clientes(dni);";
        return query;
    }

    // AGREGA LAS CLAVES FORANEAS A LA TABLA TRANSACCIONES
    private String addForeignKeyToTransactionsTable() {
        String query = "ALTER TABLE Transacciones " +
                "ADD CONSTRAINT trans_ibfk_1 FOREIGN KEY (numeroCuenta) REFERENCES Cuentas(numeroCuenta)," +
                "ADD CONSTRAINT trans_ibfk_2 FOREIGN KEY (id) REFERENCES ATM(id);";
        return query;
    }

    //----------------------------------------------------------------------------------------------------------

    //INSERTA UN CLIENTE EN LA BBDD
    public void insertCustomer() {
        ClientImpl newClient = atm.createClient();
        ATMImpl atm = new ATMImpl();
        try {
            cn = connector.conectar();
            PreparedStatement stmt = cn.prepareStatement("INSERT INTO Clientes VALUES (?,?,?,?,?)");
            stmt.setString(1, newClient.getDni());
            stmt.setString(2, newClient.getName());
            stmt.setString(3, newClient.getSurnames());
            stmt.setString(4, newClient.getPassword());
            stmt.setDouble(5, newClient.getSalary());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        atm.mainMenu();
    }

    //INSERTAR UN PRESTAMO EN LA BBDD
    public void insertPrestamo(Prestamo prestamo, ClientImpl cliente) {
        try {
            cn = connector.conectar();
            LocalDate date = LocalDate.now();
            PreparedStatement stmt = cn.prepareStatement("INSERT INTO Prestamos VALUES (?,?,?,?,?)");
            stmt.setString(1, prestamo.getCliente().getDni());
            stmt.setInt(2, prestamo.getMeses());
            stmt.setDouble(3, prestamo.getCuantia());
            stmt.setDate(4, java.sql.Date.valueOf(date.format(ISO_DATE)));
            stmt.setDouble(5, prestamo.getIntereses());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        atm.sessionMenu(cliente);
    }

    //INSERTAR UNA CARTERA DE VALORES EN LA BBDD
    public void insertCarteraValores(ClientImpl cliente, String activos, Broker broker) {
        try {
            cn = connector.conectar();
            PreparedStatement stmt = cn.prepareStatement("INSERT INTO CarteraValores(dniCliente,dniBroker,activos) VALUES (?,?,?)");
            stmt.setString(1, cliente.getDni());
            stmt.setString(2, broker.getDni());
            stmt.setString(3, activos);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        atm.sessionMenu(cliente);
    }

    // INSERTA UNA CUENTA EN LA BASE DE DATOS
    //RECIBE UN PARAMETRO CLIENTE QUE ES EL DUEÑO DE LA CUENTA
    public void insertAccount(ClientImpl cliente) {
        AccountImpl newAccount = new AccountImpl(cliente.getDni(), cliente.getName(), cliente.getSalary());
        ATMImpl atm = new ATMImpl();
        try {
            cn = connector.conectar();
            LocalDate date = LocalDate.now();
            PreparedStatement stmt = cn.prepareStatement("INSERT INTO Cuentas VALUES (?,?,?,?,?)");
            stmt.setString(1, newAccount.generateAccountNumber());
            stmt.setDouble(2, newAccount.getSalary());
            stmt.setString(3, newAccount.getOwner());
            stmt.setString(4, newAccount.getUser());
            stmt.setDate(5, java.sql.Date.valueOf(date.format(ISO_DATE)));
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error en la creación de la cuenta");
            atm.sessionMenu(cliente);
            throw new RuntimeException(e);
        }
    }

    //INSERTAR UN BROKER EN LA BBDD
    public void insertBroker(Broker broker) {
        try {
            cn = connector.conectar();
            PreparedStatement stmt = cn.prepareStatement("INSERT INTO Brokers VALUES (?,?,?)");
            stmt.setString(1, broker.getDni());
            stmt.setString(2, broker.getFullname());
            stmt.setInt(3, broker.getTlf());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //INSERTA UN ATM EN LA BBDD
    public void insertAtmTable() {
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        System.out.println("Introduzca el nombre del ATM");
        String name = sc.nextLine();
        System.out.println("Introduzca la ciudad");
        String city = sc.nextLine();
        try {
            cn = connector.conectar();
            PreparedStatement stmt = cn.prepareStatement("INSERT INTO ATM VALUES (?,?,?)");
            stmt.setString(2, name);
            stmt.setString(3, city);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //INSERTA UN CLIENTE VIP
    //RECIBE EL CLIENTE QUE SE CONVERTIRA EN CLIENTE VIP
    public void insertCustomerVip(ClientImpl cliente) {
        ClientVip newClientVip = new ClientVip();
        cn = connector.conectar();
        newClientVip = newClientVip.convertClientVip(cliente);
        LocalDate now = LocalDate.now();
        LocalDate expirationDate = now.plusDays(30);
        String dni="";
        try {
            stmt = cn.prepareStatement("SELECT * FROM ClientesVip WHERE dni =?");
            stmt.setString(1, newClientVip.getDni());
            rs = stmt.executeQuery();
            while (rs.next()) {
                dni = rs.getString(1);
            }
            if (dni.equals("")) {
                try {
                    stmt = cn.prepareStatement("INSERT INTO ClientesVip VALUES (?,?)");
                    stmt.setString(1, cliente.getDni());
                    stmt.setDate(2, java.sql.Date.valueOf(expirationDate.format(ISO_DATE)));
                    stmt.executeUpdate();
                    stmt.close();
                    System.out.println("El cliente ahora es vip,fecha de expiracion: " + newClientVip.getExpiration());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("Este cliente ya es vip");
                atm.sessionMenu(cliente);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //INSERTA UNA TRANSACCION
    //RECIBE LA CUENTA QUE REALIZA LA TRANSACCION Y EL CAJERO DESDE DONDE SE REALIZA
    public void insertTransaction(AccountImpl account, ATMImpl atm) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca el concepto de la transacción:");
        String concept = sc.nextLine();
        sc.nextDouble();
        System.out.println("Introduzca la cantidad de dinero a enviar:");
        Double amount = sc.nextDouble();
        LocalDate date = LocalDate.now();

        try {
            cn = connector.conectar();
            PreparedStatement stmt = cn.prepareStatement("INSERT INTO ATM VALUES (?,?,?,?,?,?)");
            stmt.setString(2, concept);
            stmt.setDate(3, java.sql.Date.valueOf(date));
            stmt.setDouble(4, amount);
            stmt.setString(5, account.getNumAccount());
            stmt.setInt(6, atm.getId());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //MUESTRA TODAS LAS CUENTAS DEL CLIENTE
    //RECIBE UN CLIENTE
    public void showAccount(ClientImpl currentClient) {
        List<AccountImpl> accountList = searchAccounts(currentClient.getDni());
        if (accountList.size() != 0) {
            for (AccountImpl ac : accountList) {
                System.out.println("[Número de cuenta: " + ac.getNumAccount() + " - Usuario: " + ac.getUser() + " - Salario: " + ac.getSalary() + " - Fecha de creación: " + ac.getDate() + "]");
            }
        } else {
            System.out.println("El cliente " + currentClient.getName() + " no tiene ninguna cuenta asociada");
        }
        atm.sessionMenu(currentClient);
    }

    //ELIMINA UNA CUENTA DE LA TABLA
    //RECIBE EL NUMERO DE CUENTA DE LA TABLA A DEVOLVER
    public String deleteAccount(ClientImpl currentClient) {
        String num_cuenta = currentClient.returnNumAccount();
        ATMImpl atm = new ATMImpl();
        String sql = "DELETE FROM Cuentas WHERE numeroCuenta=? AND usuario=?";
        try {
            stmt = cn.prepareStatement(sql);
            stmt.setString(1, num_cuenta);
            stmt.setString(2, currentClient.getDni());
            int lines = stmt.executeUpdate();
            if (lines > 0) {
                System.out.println("La cuenta con numero[" + num_cuenta + "] se ha borrado correctamente");
            } else {
                System.out.println("La cuenta no existe o no te pertenece.");
            }
            atm.sessionMenu(currentClient);
        } catch (SQLException e) {
            atm.sessionMenu(currentClient);
            throw new RuntimeException(e);
        }
        return sql;
    }

    //BUSCA EL CLIENTE EN LA BBDD Y LO DEVUELVE
    //RECIBE DOS STRINGS, EL DNI Y LA CONTRASEÑA
    public ClientImpl login(String dni, String password) {
        ClientImpl client = new ClientImpl();
        String sql = "SELECT * FROM Clientes WHERE dni =? AND contrasena =?";
        String dni2 = "";
        String name = "";
        String password2 = "";
        Double salary = 0.0;
        String surnames = "";

        try {
            stmt = cn.prepareStatement(sql);
            stmt.setString(1, dni);
            stmt.setString(2, password);
            rs = stmt.executeQuery();

            while (rs.next()) {
                dni2 = rs.getString(1);
                name = rs.getString(2);
                surnames = rs.getString(3);
                password2 = rs.getString(4);
                salary = rs.getDouble(5);
            }

            client.setDni(dni2);
            client.setName(name);
            client.setSurnames(surnames);
            client.setPassword(password2);
            client.setSalary(salary);

            if (("").equals(client.getDni())) {
                System.out.println("Usuario o contraseña incorrectos");
                //atm.mainMenu();

            } else {
                System.out.println("Se ha iniciado sesión correctamente");
                return client;
            }

        } catch (SQLException e) {
           // atm.mainMenu();
            throw new RuntimeException(e);
        }
        return null;
    }

    //BUSCA EN LAS CUENTAS DEL CLIENTE Y RETIRA UNA CANTIDAD DE DINERO
    //RECIBE LA CUENTA A BUSCAR
    public boolean withdrawMoney(AccountImpl account, ClientImpl currentClient, double ammount) {
        if (existAccountNumber(account.getNumAccount())) {
            updateAccount(account.getNumAccount(), account.withdrawAmount(account,ammount));
            return true;
        } else {
            //atm.sessionMenu(currentClient);
        }
        return false;
    }

    //BUSCA EN LAS CUENTAS DEL CLIENTE Y DEPOSITA UNA CANTIDAD DE DINERO
    //RECIBE LA CUENTA A BUSCAR
    public void depositMoney(AccountImpl account, ClientImpl currentClient) {
        if (existAccountNumber(account.getNumAccount())) {
            updateAccount(account.getNumAccount(), account.depositAmount(account));
        } else {
            atm.sessionMenu(currentClient);
        }

    }

    //DEVUELVE UNA CUENTA DE LA LISTA DE CUENTAS DEL CLIENTE
    //RECIBE EL DNI DEL CLIENTE Y EL NUMERO DE CUENTA OBJETIVO
    public AccountImpl getAccount(ClientImpl currentClient) {
        List<AccountImpl> accounts = searchAccounts(currentClient.getDni());
        ATMImpl atm = new ATMImpl();
        if (accounts != null) {
            for (int i = 0; i < accounts.size(); i++) {
                if (accounts.get(i).getNumAccount().equals(currentClient.returnNumAccount())) {
                    AccountImpl currentAccount = accounts.get(i);
                    return currentAccount;
                }
            }
            atm.sessionMenu(currentClient);
        } else {
            System.out.println("Cuenta no encontrada correctamente");
            atm.sessionMenu(currentClient);
        }
        return null;
    }

    //COMPRUEBA SI LA CUENTA EXISTE
    //RECIBE COMO PARAMETRO EL NUMERO DE CUENTA OBJETIVO
    public boolean existAccountNumber(String accountNumber) {
        String sql = "SELECT * FROM Cuentas WHERE numeroCuenta =?";
        try {
            stmt = cn.prepareStatement(sql);
            stmt.setString(1, accountNumber);
            rs = stmt.executeQuery();
            if (rs.next()) {
                if (rs.getRow() > 0) {
                    System.out.println("El numero de cuenta se ha localizado correctamente");
                    return true;
                }
            } else {
                System.out.println("El numero de cuenta introducido no existe");
            }
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return false;
    }


    //ENVIA DINERO DE UNA CUENTA A OTRA
    //RECIBE LA CUENTA EMISORA DEL DINERO, LA RECEPTORA DE LA CANTIDAD Y LA CANTIDAD DE DINERO A ENVIAR
    public void sendMoney(AccountImpl currentAccount, AccountImpl targetAccount, Double amount) {
        transactions.makeTransactions(currentAccount, targetAccount);
        currentAccount = searchAccount(currentAccount.getNumAccount());
        updateAccount(currentAccount.getNumAccount(), (currentAccount.getSalary() - amount));
        updateAccount(targetAccount.getNumAccount(), (targetAccount.getSalary() + amount));
    }

    //BUSCA UN CLIENTE Y DEVUELVE UNA LISTA DE CUENTAS
    //RECIBE EL DNI DEL CLIENTE OBJETIVO
    public List<AccountImpl> searchAccounts(String clientDni) {
        String sql = "SELECT * FROM Cuentas WHERE usuario =?";
        List<AccountImpl> accountList = new ArrayList<>();
        try {
            stmt = cn.prepareStatement(sql);
            stmt.setString(1, clientDni);
            rs = stmt.executeQuery();
            while (rs.next()) {
                AccountImpl account = new AccountImpl();
                account.setNumAccount(rs.getString(1));
                account.setSalary(rs.getDouble(2));
                account.setUser(rs.getString(3));
                account.setOwner(rs.getString(4));
                account.setDate(rs.getDate(5));
                accountList.add(account);
            }
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return accountList;
    }

    //BUSCA UNA CUENTA EN LA BBDD
    //RECIBE EL NUMERO DE CUENTA OBJETIVO
    public AccountImpl searchAccount(String num_cuenta) {
        String sql = "SELECT * FROM Cuentas WHERE numeroCuenta =?";
        try {
            stmt = cn.prepareStatement(sql);
            stmt.setString(1, num_cuenta);
            rs = stmt.executeQuery();
            while (rs.next()) {
                account.setNumAccount(rs.getString(1));
                account.setSalary(rs.getDouble(2));
                account.setOwner(rs.getString(3));
                account.setUser(rs.getString(4));
                account.setDate(rs.getDate(5));
            }
            stmt.close();
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return account;
    }

    //BUSCA UN CLIENTE EN LA BBDD Y LO DEVUELVE
    //RECIBE EL DNI DEL CLIENTE OBJETIVO
    public ClientImpl searchClient(String clientDni) {
        String sql = "SELECT * FROM Clientes WHERE dni = ?";
        ClientImpl client = new ClientImpl();
        try {
            stmt = cn.prepareStatement(sql);
            stmt.setString(1, clientDni);
            rs = stmt.executeQuery();
            while (rs.next()) {
                client = new ClientImpl(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDouble(5),
                        searchAccounts(clientDni));
                stmt.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return client;
    }

    //COMPRUEBA SI LA CUENTA PERTENECE AL USUARIO QUE ESTÁ INTENTANDO USARLA O NO
    public AccountImpl validateOwnerAccount(AccountImpl currentAccount, ClientImpl currentClient) {
        String sql = "SELECT * FROM Cuentas WHERE numeroCuenta = ? AND usuario = ?";
        AccountImpl account = new AccountImpl();
        try {
            stmt = cn.prepareStatement(sql);
            stmt.setString(1, currentAccount.getNumAccount());
            stmt.setString(2, currentClient.getDni());
            rs = stmt.executeQuery();

            while (rs.next()) {

                account.setNumAccount(rs.getString(1));
                account.setUser(rs.getString(2));
                account.setSalary(rs.getDouble(3));
                account.setOwner(rs.getString(4));
                account.setDate(rs.getDate(5));
            }
            stmt.close();

            if (("").equals(account.getNumAccount())) {
                System.out.println("Esta cuenta no pertenece a tu usuario (ACCESO DENEGADO 403)");
                atm.sessionMenu(currentClient);
            } else {
                return account;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return account;
    }

}
