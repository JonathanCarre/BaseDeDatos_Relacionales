import java.sql.*;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    public static Connection conn=null;

    public static void main(String[] args) throws SQLException {
        int opcion=0;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("0.Salir del programa");
            System.out.println("1.Establecer la conexión");
            System.out.println("2.Crear la base de datos ut14");
            System.out.println("3.Crear la tabla pacientes");
            System.out.println("4.Borrar tabla paciente");
            System.out.println("5.Asignar base de datos");
            System.out.println("6.Insertar registro en la tabla pacientes.");
            System.out.println("7.Vaciar la tabla paciente");
            System.out.println("8.Eliminar por dni ");
            System.out.println("9.Actualizar el numero de operaciones cuyo dni es introducido por teclado");
            opcion=sc.nextInt();
            switch (opcion){
                case 0:
                    System.out.println("Gracias por usar el programa.");
                    break;
                case 1:
                    establecer_conexion();
                    break;
                case 2:
                    crear_bd();
                    break;
                case 3:
                    crear_tabla();
                    break;
                case 4:
                    borrar_tabla();
                    break;
                case 5:
                    asignar_bd();
                    break;
                case 6:
                    insertar_registro();
                    break;
                case 7:
                    truncar_tabla();
                    break;
                case 8:
                    borrar_registro();
                    break;
                case 9:
                    actualizar_registro();
                    break;
            }
        }while(opcion!=0);

    }

    private static  void mostrarbd2() throws SQLException {

    }

    private static void actualizar_registro() throws SQLException {
        PreparedStatement ps = conn.prepareStatement("update paciente set n_operaciones=? where dni = ?;");
        System.out.println("Introduce el dni del paciente a actualizar:");
        Scanner sc = new Scanner(System.in);
        String dni=sc.next();
        System.out.println("Introduzca el numero de operaciones:");
        int n_operaciones = sc.nextInt();
        ps.setInt(1,n_operaciones);
        ps.setString(2,dni);
        ps.executeUpdate();
        System.out.println("Paciente actalizado correctamente.");
    }

    private static void borrar_registro() throws SQLException {
        System.out.println("Introduzca el dni del paciente al que quiere borrar:");
        Scanner sc = new Scanner(System.in);
        String dni=sc.next();
        PreparedStatement ps = conn.prepareStatement("delete from paciente where dni=?;");
        ps.setString(1,dni);
        ps.executeUpdate();
        System.out.println("El paciente ha sido eliminado correctamente.");
    }

    private static void insertar_registro() throws SQLException {
        Scanner sc = new Scanner(System.in);
        PreparedStatement ps = conn.prepareStatement("insert into paciente values (?,?,?,?);");
        System.out.println("Intrduzca el dni:");
        String dni=sc.next();
        System.out.println("Introduzca el nombre:");
        String nombre=sc.next();
        System.out.println("Introduzca el apellido:");
        String apellido=sc.next();
        System.out.println("Introduzca el numero de operaciones:");
        int n_o=sc.nextInt();
        ps.setString(1,dni);
        ps.setString(2,nombre);
        ps.setString(3,apellido);
        ps.setInt(4,n_o);
        ps.executeUpdate();
        System.out.println("Registro insertado correctamente.");

    }

    private static void truncar_tabla() throws SQLException {
        String query ="truncate table paciente;";
        Statement st = conn.createStatement();
        st.executeUpdate(query);
        System.out.println("La tabla ha sido vaciada correctamente.");
    }

    private static void borrar_tabla() throws SQLException {
        String query="drop table paciente;";
        Statement st = conn.createStatement();
        st.executeUpdate(query);
        System.out.println("La tabla ha sido borrada correctamente.");
    }

    private static void asignar_bd() throws SQLException {
        String query = "use ut14;";
        Statement st = conn.createStatement();
        st.executeUpdate(query);
        System.out.println("Base de datos aasignada correctamente.");
    }

    private static void crear_tabla() throws SQLException {
        //Crear la tabla

        String query = "create table paciente("+
                "dni varchar(9) primary key,"+
                "nombre varchar(30) not null,"+
                "apellidos varchar(100),"+
                "n_operaciones int)";
        Statement st = conn.createStatement();
        st.executeUpdate(query);
        System.out.println("Tabla creada correctamente.");
    }

    private static void crear_bd() throws SQLException {
        //Crear la base de datos

        String query = "create database ut14;";
        Statement st = conn.createStatement();
        st.executeUpdate(query);
        System.out.println("Base de datos creada correctamente.");
        //Asignar la base de datos por defecto


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