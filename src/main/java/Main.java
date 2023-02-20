import classes.ATMImpl;
import model.statements.Statements;

public class Main {
    public static void main(String[] args) {
        //INSTANCIAMOS LA CALSE ATM PARA LLAMAR AL MAIN MENU
        ATMImpl atm = new ATMImpl();
        //INSTANCIAMOS LA CLASE STATEMENTS PARA LLAMAR AL METODO DONDE CREAREMOS TODAS LAS TABLAS Y SUS RELACIONES DESDE UN INICIO
        Statements sentences = new Statements();

        sentences.createAllTables();

        atm.mainMenu();
    }
}
