package ui.console;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import sistema.model.*;

public class Vista {
    protected Scanner input = new Scanner(System.in);
    private String titulo = "";
    private String credencial = "";
    private ModoTerminal modo;
    private String opcBASICAS = "EFL"; // FIN y LOGIN
    private String opcNormal = opcBASICAS + "123"; // OPC MINIMAS
    private String opcUser = opcNormal + "45"; // OPC DE US.REGISTRADO
    private String opcAdmin = opcUser + "01234567890ABCDEFGHIJLMNOPQRSRTUWXYZ"; // TODO LO DEMAS

    public Vista(String titulo) {
        this.titulo = titulo;
        this.modo = ModoTerminal.NORMAL;
    }

    // == OPCIONES DE PRESENTACION ==
    public ModoTerminal mostrarMenu() {
        pln("   MENU «" + titulo + "»");
        pln("   1.- Consultar Categorías");
        pln("   2.- Consultar SubCategorías");
        pln("   3.- Consultar Apuntes");
        if (modo.ordinal() > ModoTerminal.NORMAL.ordinal() ) {
            // USER
            pln("   4.- Registrar Apunte");
            pln("   5.- Consultar Importes");
            if (modo.ordinal() > ModoTerminal.USER.ordinal()) {
                // ADMIN
                pln("   6.- Administrar Categorías «CRUD»");
                pln("   7.- Registrar SubCategoría");
                pln("   9.- Reset");
            }
        }
        pln("   L.- Login/Logout " + credencial);
        pln("   E.- Exit/F)IN");
        pln();
        return modo;
    }

    public void establecerModo(ModoTerminal nuevoModo) {
        modo = nuevoModo;
    }

    // == DATOS INPUT ==
    public char leerOpcionMenu() {
        char opcion;
        while (true) {
            p("   Seleccione una opción: ");
            opcion = input.next().toUpperCase().charAt(0);
            if (modo == ModoTerminal.NORMAL && opcNormal.indexOf(opcion) > -1)
                break;
            if (modo == ModoTerminal.USER && opcUser.indexOf(opcion) > -1)
                break;
            if (modo == ModoTerminal.ADMIN && opcAdmin.indexOf(opcion) > -1)
                break;
        }
        return opcion;
    }

    public String leerCredenciales() {
        credencial = leerDatoString("Ingresa tus credenciales");
        return credencial;
    }

    // INPUT GENERICOS -- MEJOR HACER MÁS ESPECIFICOS Y VALIDADOS
    public String leerDatoString(String msg) {
        System.out.print(msg + ": ");
        String s = input.nextLine(); // No sé por qué necesito dos nextLine
        return input.nextLine();
    }

    public String editDatoString(String msg, String valor) {
        System.out.print(msg + " (" + valor + "): ");
        String s = input.nextLine();
        s = input.nextLine();
        if (s.equals(""))
            s = valor;
        return s;
    }

    public int leerDatoInt(String msg) {
        System.out.print(msg + ": ");
        return input.nextInt();
    }

    public float leerDatoFloat(String msg) {
        System.out.print(msg + ": ");
        return input.nextFloat();
    }

    public int seleccionarCategoria(List<Categoria> lista, String msg, Boolean show) {
        List<Integer> valores = new ArrayList<Integer>();
        for (Categoria c : lista)
            valores.add(c.id);
        valores.add(-1);
        if (show) mostrarMsgs(lista);
        int input = -2;
        while (valores.indexOf(input) < 0 && input != 0)
            input = leerDatoInt(msg + " (-1=cancelar)");
        return input;    }

    public int seleccionarCategoria(List<Categoria> lista, String msg) {
        return seleccionarCategoria(lista, msg, true);
    }

    // == MENSAJES OUT ==
    public void mostrarMsgs(Object qry) {
        for (Object o : (List) qry)
            pln(o.toString().replace(';', ' '));
    }

    public void mostrarMsg(String string) {
        pln(string);
    }

    public void mostrarMsg(String format, Object... args) {
        mostrarMsg(String.format(format, args));
    }

    // === UTILIDADES PRIVADAS ===
    private void pln(Object l) {
        System.out.println(l);
    }

    private void pln() {
        System.out.println();
    }

    private void p(Object l) {
        System.out.print(l);
    }

    private void cls() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
