package ejercicio_practico;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;



public class Main {
    public static Connection conn=null;
    static Jugador[] array;
    static Jugador j=null;
    static ArrayList<Jugador> array_dinamico=new ArrayList<>();


    public static void main(String[] args) throws SQLException {
        Jugador j=null;
        int opcion=0;
        Jugador[] array;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("0.Salir del programa");
            System.out.println("1.Crear base de datos EjercicioPráctico");
            System.out.println("2.Crear la tabla jugador");
            System.out.println("3.Vaciar tabla jugador");
            System.out.println("4.Eliminar tabla jugador");
            System.out.println("5.Insertar jugador");
            System.out.println("6.Buscar jugador por dorsal y lo almacenamos en un objeto jugador.");
            System.out.println("7.Guardar registros en array estático.");
            System.out.println("8.Guardar registros en array dinámico. ");
            System.out.println("9.Mostrar tablas de la base de datos.");
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
                    vaciar_tabla();
                    break;
                case 4:
                    borrar_tabla();
                    break;
                case 5:
                    System.out.println("Introduzca el dni:");
                    String dni=sc.next();
                    System.out.println("Introduzca el nombre:");
                    String nombre=sc.next();
                    System.out.println("Introduzca el dorsal:");
                    int dorsal=sc.nextInt();
                    System.out.println("Introduzca el salario:");
                    double salario=sc.nextDouble();
                    System.out.println("Introduzca la edad:");
                    int edad=sc.nextInt();
                    j=new Jugador(dni,nombre,dorsal,salario,edad);
                    insertar_jugador(j);
                    break;
                case 6:
                    System.out.println("Introduzca el dorsal del jugador a buscar:");
                    dorsal=sc.nextInt();
                    j=buscar_por_dorsal(dorsal);
                    System.out.println(j.toString());
                    break;
                case 7:
                    array=insertar_registros_array_estatico();
                    System.out.println(Arrays.toString(array));

                    break;
                case 8:
                    array_dinamico=almacenar_array_dinamico();
                    for (Jugador p : array_dinamico){
                        System.out.println(p.toString());
                    }

                    break;
                case 9:

                    break;
            }
        }while(opcion!=0);
    }

    private static ArrayList<Jugador> almacenar_array_dinamico() throws SQLException {
        establecer_conexion();
        asignar_bd();
        PreparedStatement ps= conn.prepareStatement("Select * from jugador;");
        ResultSet rs=ps.executeQuery();
        while(rs.next()){
            j= new Jugador(rs.getString(1),rs.getString(2),rs.getInt(3),rs.getDouble(4),rs.getInt(5));
            array_dinamico.add(j);
        }
        return array_dinamico;
    }

    private static Jugador[] insertar_registros_array_estatico() throws SQLException {
        establecer_conexion();
        asignar_bd();


        PreparedStatement ps= conn.prepareStatement("select count(*) from jugador;");
        ResultSet rs=ps.executeQuery();
        int dimension=0;
        while(rs.next()){
            dimension=rs.getInt(1);
        }
        array= new Jugador[dimension];
        ps= conn.prepareStatement("select * from jugador");
        rs=ps.executeQuery();
        int i=0;
        while(rs.next()){
            j=new Jugador(rs.getString(1),rs.getString(2),rs.getInt(3),rs.getDouble(4),rs.getInt(5) );
            array[i]=j;
            i++;

        }
        return array;
    }

    private static void mostrar_tablas() throws SQLException {
        DatabaseMetaData databaseMetaData=conn.getMetaData();
        ResultSet rs=databaseMetaData.getTables("EjercicioPractico","EjercicioPractico",null,null);
        while(rs.next()){
            System.out.println(rs.getString(3));
        }
    }

    private static void borrar_registro() {
    }

    private static void truncar_tabla() {
    }

    private static Jugador buscar_por_dorsal(int dorsal) throws SQLException {

        Jugador p=null;
        PreparedStatement ps= conn.prepareStatement("select * from jugador where dorsal_camiseta=?;");
        ps.setInt(1,dorsal);
        ResultSet rs=ps.executeQuery();
        while (rs.next()){
            p=new Jugador(rs.getString(1),rs.getString(2),rs.getInt(3),
                    rs.getDouble(4),rs.getInt(5));
        }
        System.out.println("Búsqueda realizada correctamente.");
        return p;
    }

    private static void insertar_jugador(Jugador j) throws SQLException {

        PreparedStatement ps= conn.prepareCall("insert into jugador values(?,?,?,?,?);");
        ps.setString(1, j.getDni());
        ps.setString(2,j.getNombre());
        ps.setInt(3,j.getDorsal_camiseta());
        ps.setDouble(4,j.getSalario());
        ps.setInt(5,j.getEdad());
        ps.executeUpdate();
        System.out.println("Jugador insertado correctamente.");
    }

    private static void borrar_tabla() {
    }

    private static void vaciar_tabla() throws SQLException {
        String query="truncate table jugador;";
        Statement st= conn.createStatement();
        st.executeUpdate(query);
        System.out.println("Tabla vaciada correctamente.");
    }

    private static void crear_tabla() throws SQLException {

        asignar_bd();
        String query = "create table  jugador("+
                "dni varchar(9) not null primary key,"+
                "nombre varchar(30) not null,"+
                "dorsal_camiseta int,"+
                "salario double," +
                "edad int)";
        Statement st = conn.createStatement();
        st.executeUpdate(query);
        System.out.println("Tabla creada correctamente.");
    }

    private static void crear_bd() throws SQLException {
        establecer_conexion();
        String query = "create database EjercicioPractico;";
        Statement st = conn.createStatement();
        st.executeUpdate(query);
        System.out.println("Base de datos creada correctamente.");
    }

    private static void asignar_bd() throws SQLException {
        String query="use EjercicioPractico;";
        Statement st = conn.createStatement();
        st.executeUpdate(query);
        System.out.println("Base de datos asignada correctamente.");
    }
    public static void establecer_conexion() throws SQLException {
        //Conectar al sistema gestor de bases de datos

        String url = "jdbc:mysql://localhost:3306/";
        String user = "root";
        String pwd="admin";
        conn = DriverManager.getConnection(url,user,pwd);
        System.out.println("Conexión establecida.");
    }

}
