package jghjgjghj;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;



public class Main {
    static Emisora[] listado_emisoras;
    static ArrayList<Emisora> array_list_emisora=new ArrayList<>();
    static Emisora e = null;
    static Connection conn=null;

    public static void main(String[] args) {
        int opcion=0;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("0.Salir del programa");
            System.out.println("1.Crear la base de datos SimulacroExamen");
            System.out.println("2.Crear la tabla emisoraonline");
            System.out.println("3.Insertar emisora online");
            System.out.println("4.Buscar emisora por url introducida por teclado y añadirlo a un array estático");
            System.out.println("5.Almacenar en el arraylist emisoras con beneficios superiores a 4k €");
            System.out.println("6.Sacar la versión del sistema gestor de bases de datos");
            opcion=sc.nextInt();
            switch (opcion){
                case 0:
                    System.out.println("Gracias por usar el programa.");
                    break;
                case 1:
                    crear_bd();
                    break;
                case 2:
                    crear_tabla();
                    break;
                case 3:
                    insertar_emisora(e);
                    break;
                case 4:
                    buscar_emisora_por_url(url);
                    break;
                case 5:
                    alamcenar_en_arraylist();
                    break;
                case 6:
                    String version= sacar_version_sgbd();
                    System.out.println("La versión es "+version);
                    break;
            }
        }while(opcion!=0);
    }

    private static String sacar_version_sgbd() throws SQLException {
        DatabaseMetaData databaseMetaData=conn.getMetaData();
        return databaseMetaData.getDatabaseProductVersion();
    }

    private static void insertar_emisora(Emisora e) {
    }

    private static void crear_tabla() throws SQLException {
        asignar_bd();
        String query = "create table  jugador("+
                "num_emisora in not null primary key,"+
                "nombre varchar(50) not null,"+
                "emitiendo boolean,"+
                "beneficios double," +
                "num_oyentes int,"+
                "url varchar(100))";
        Statement st= conn.createStatement();
        st.executeUpdate(query);
    }

    private static void establecer_conexion() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/";
        String user = "root";
        String pwd="admin";
        conn = DriverManager.getConnection(url,user,pwd);
        System.out.println("Conexión establecida.");
    }
    private static void asignar_bd() throws SQLException {
        String query="use EjercicioPractico;";
        Statement st = conn.createStatement();
        st.executeUpdate(query);
        System.out.println("Base de datos asignada correctamente.");
    }

    private static void crear_bd() throws SQLException {

        establecer_conexion();
        asignar_bd();
        String query="create database simulacroexamen";
        Statement st=conn.createStatement();
        st.executeUpdate(query);
        System.out.println("La base de datos ha sido creada creada correctamente.");
    }

}
