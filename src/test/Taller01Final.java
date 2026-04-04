package test;

//Nombre: Jesús Muñoz Cortes, RUT: 22.327.129-4, Carrera: Ingeniería Civil en Computación e Informatica

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class Taller01Final {

    static String[] usuariosNombres = new String[300];
    static String[] usuariosPasswords = new String[300];
    static int cantUsuarios = 0;

    static String[] regNombres = new String[300];
    static String[] regFechas = new String[300];
    static int[] regHoras = new int[300];
    static String[] regActividades = new String[300];
    static int cantRegistros = 0;

    static Scanner leer = new Scanner(System.in);

    public static void main(String[] args) {
        cargarUsuarios();
        cargarRegistros();

        int opcion = 0;
        while (opcion != 3) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1) Menu de Usuarios");
            System.out.println("2) Menu de Analisis");
            System.out.println("3) Salir");
            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1:
                    menuUsuarios();
                    break;
                case 2:
                    menuAnalisis();
                    break;
                case 3:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }

    public static void menuUsuarios() {
        System.out.print("Usuario: ");
        String user = leer.nextLine();
        System.out.print("Contrasena: ");
        String pass = leer.nextLine();

        int indexUser = -1;
        for (int i = 0; i < cantUsuarios; i++) {
            if (usuariosNombres[i].equals(user) && usuariosPasswords[i].equals(pass)) {
                indexUser = i;
                break;
            }
        }

        if (indexUser != -1) {
            System.out.println("\nAcceso correcto! Bienvenido " + user);
            submenuSesion(indexUser);
        } else {
            System.out.println("Credenciales incorrectas.");
        }
    }

    public static void submenuSesion(int indexLogueado) {
        int opcion = 0;
        String nombreUser = usuariosNombres[indexLogueado];

        while (opcion != 5) {
            System.out.println("\nQue deseas realizar?");
            System.out.println("1) Registrar actividad");
            System.out.println("2) Modificar actividad");
            System.out.println("3) Eliminar actividad");
            System.out.println("4) Cambiar contrasena");
            System.out.println("5) Salir");
            opcion = leerEntero("Opcion: ");

            switch (opcion) {
                case 1:
                    registrarActividad(nombreUser);
                    break;
                case 2:
                    modificarActividad(nombreUser);
                    break;
                case 3:
                    eliminarActividad(nombreUser);
                    break;
                case 4:
                    cambiarPassword(indexLogueado);
                    break;
            }
        }
    }

    public static void registrarActividad(String usuario) {
        if (cantRegistros >= 300) {
            System.out.println("Limite de registros alcanzado.");
            return;
        }
        System.out.print("Ingrese fecha (DD/MM/AAAA): ");
        String fecha = leer.nextLine();
        int horas = leerEntero("Ingrese horas: ");
        System.out.print("Ingrese actividad: ");
        String actividad = leer.nextLine();

        regNombres[cantRegistros] = usuario;
        regFechas[cantRegistros] = fecha;
        regHoras[cantRegistros] = horas;
        regActividades[cantRegistros] = actividad;
        cantRegistros++;

        guardarRegistros();
        System.out.println("Actividad registrada con exito!");
    }

    public static void modificarActividad(String usuario) {
        int[] indicesMios = mostrarYObtenerActividades(usuario);
        if (indicesMios[0] == -1) return;

        int seleccion = leerEntero("Cual actividad deseas modificar? (0 para regresar): ");
        if (seleccion > 0 && seleccion <= 300 && indicesMios[seleccion - 1] != -1) {
            int indexReal = indicesMios[seleccion - 1];
            System.out.println("1) Fecha\n2) Duracion\n3) Tipo");
            int queModificar = leerEntero("Opcion: ");

            switch (queModificar) {
                case 1:
                    System.out.print("Nueva fecha: ");
                    regFechas[indexReal] = leer.nextLine();
                    break;
                case 2:
                    regHoras[indexReal] = leerEntero("Nuevas horas: ");
                    break;
                case 3:
                    System.out.print("Nueva actividad: ");
                    regActividades[indexReal] = leer.nextLine();
                    break;
            }
            guardarRegistros();
            System.out.println("Actividad modificada!");
        }
    }

    public static void eliminarActividad(String usuario) {
        int[] indicesMios = mostrarYObtenerActividades(usuario);
        if (indicesMios[0] == -1) return;

        int seleccion = leerEntero("Cual eliminar? (0 para regresar): ");
        if (seleccion > 0 && indicesMios[seleccion - 1] != -1) {
            int indexReal = indicesMios[seleccion - 1];
            
            for (int i = indexReal; i < cantRegistros - 1; i++) {
                regNombres[i] = regNombres[i + 1];
                regFechas[i] = regFechas[i + 1];
                regHoras[i] = regHoras[i + 1];
                regActividades[i] = regActividades[i + 1];
            }
            cantRegistros--;
            guardarRegistros();
            System.out.println("Actividad eliminada!");
        }
    }

    public static void cambiarPassword(int index) {
        System.out.print("Nueva contrasena: ");
        usuariosPasswords[index] = leer.nextLine();
        guardarUsuarios();
        System.out.println("Contrasena actualizada.");
    }

    public static void menuAnalisis() {
        int opcion = 0;
        while (opcion != 5) {
            System.out.println("\n--- MENU DE ANALISIS ---");
            System.out.println("1) Actividad mas realizada (Global)");
            System.out.println("2) Actividad mas realizada por cada usuario");
            System.out.println("3) Usuario con mayor procrastinacion");
            System.out.println("4) Ver todas las actividades");
            System.out.println("5) Salir");
            opcion = leerEntero("Opcion: ");

            switch (opcion) {
                case 1:
                    calcularActividadMasFrecuente(null);
                    break;
                case 2:
                    for (int i = 0; i < cantUsuarios; i++) {
                        System.out.print("* " + usuariosNombres[i] + " -> ");
                        calcularActividadMasFrecuente(usuariosNombres[i]);
                    }
                    break;
                case 3:
                    usuarioMasFlojo();
                    break;
                case 4:
                    verTodo();
                    break;
            }
        }
    }

    private static void calcularActividadMasFrecuente(String soloUsuario) {
        String masRepetida = "N/A";
        int maxContador = 0;

        for (int i = 0; i < cantRegistros; i++) {
            if (soloUsuario != null && !regNombres[i].equals(soloUsuario)) continue;

            String actual = regActividades[i];
            int contador = 0;
            for (int j = 0; j < cantRegistros; j++) {
                if (soloUsuario != null && !regNombres[j].equals(soloUsuario)) continue;
                if (regActividades[j].equals(actual)) contador++;
            }

            if (contador > maxContador) {
                maxContador = contador;
                masRepetida = actual;
            }
        }
        
        if (soloUsuario == null) {
            System.out.println("Actividad mas frecuente global: " + masRepetida + " (" + maxContador + " veces)");
        } else {
            System.out.println(masRepetida + " (" + maxContador + " veces)");
        }
    }

    private static void usuarioMasFlojo() {
        String masFlojo = "";
        int maxHoras = -1;

        for (int i = 0; i < cantUsuarios; i++) {
            int suma = 0;
            for (int j = 0; j < cantRegistros; j++) {
                if (regNombres[j].equals(usuariosNombres[i])) {
                    suma += regHoras[j];
                }
            }
            if (suma > maxHoras) {
                maxHoras = suma;
                masFlojo = usuariosNombres[i];
            }
        }
        System.out.println("El usuario con mas horas es: " + masFlojo + " con " + maxHoras + " horas.");
    }

    private static void verTodo() {
        System.out.println("\n--- TODOS LOS REGISTROS ---");
        for (int i = 0; i < cantRegistros; i++) {
            System.out.println(regNombres[i] + " | " + regFechas[i] + " | " + regHoras[i] + "h | " + regActividades[i]);
        }
    }

    public static void cargarUsuarios() {
        try {
            File f = new File("bin/test/Usuarios.txt");
            if (!f.exists()) f = new File("Usuarios.txt");
            if (!f.exists()) return;
            BufferedReader br = new BufferedReader(new FileReader(f));
            String linea;
            while ((linea = br.readLine()) != null && cantUsuarios < 300) {
                String[] partes = linea.split(";");
                if (partes.length >= 2) {
                    usuariosNombres[cantUsuarios] = partes[0];
                    usuariosPasswords[cantUsuarios] = partes[1];
                    cantUsuarios++;
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error al cargar Usuarios.txt");
        }
    }

    public static void cargarRegistros() {
        try {
            File f = new File("bin/test/Registros.txt");
            if (!f.exists()) f = new File("Registros.txt");
            if (!f.exists()) return;
            BufferedReader br = new BufferedReader(new FileReader(f));
            String linea;
            cantRegistros = 0;
            while ((linea = br.readLine()) != null && cantRegistros < 300) {
                String[] partes = linea.split(";");
                if (partes.length >= 4) {
                    regNombres[cantRegistros] = partes[0];
                    regFechas[cantRegistros] = partes[1];
                    try {
                        regHoras[cantRegistros] = Integer.parseInt(partes[2]);
                    } catch (Exception ex) {
                        regHoras[cantRegistros] = 0;
                    }
                    regActividades[cantRegistros] = partes[3];
                    cantRegistros++;
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error al cargar Registros.txt");
        }
    }

    public static void guardarUsuarios() {
        try {
            File f = new File("bin/test/Usuarios.txt");
            if (!f.exists()) f = new File("Usuarios.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            for (int i = 0; i < cantUsuarios; i++) {
                bw.write(usuariosNombres[i] + ";" + usuariosPasswords[i]);
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            System.out.println("Error al guardar Usuarios.txt");
        }
    }

    public static void guardarRegistros() {
        try {
            File f = new File("bin/test/Registros.txt");
            if (!f.exists()) f = new File("Registros.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            for (int i = 0; i < cantRegistros; i++) {
                bw.write(regNombres[i] + ";" + regFechas[i] + ";" + regHoras[i] + ";" + regActividades[i]);
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            System.out.println("Error al guardar Registros.txt");
        }
    }

    public static int leerEntero(String msj) {
        while (true) {
            try {
                System.out.print(msj);
                return Integer.parseInt(leer.nextLine());
            } catch (Exception e) {
                System.out.println("Error: Ingrese un numero valido.");
            }
        }
    }

    public static int[] mostrarYObtenerActividades(String usuario) {
        int[] indices = new int[300];
        for (int i = 0; i < 300; i++) indices[i] = -1;
        
        int contadorLocal = 0;
        System.out.println("\n0) Regresar");
        for (int i = 0; i < cantRegistros; i++) {
            if (regNombres[i].equals(usuario)) {
                indices[contadorLocal] = i;
                System.out.println((contadorLocal + 1) + ") " + regFechas[i] + " - " + regActividades[i]);
                contadorLocal++;
            }
        }
        if (contadorLocal == 0) {
            System.out.println("No tienes actividades registradas.");
            indices[0] = -1;
        }
        return indices;
    }
}
