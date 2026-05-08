package taller2;

import java.util.Scanner;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

public class Menus {
	
	public static void MenuDefault() {
		System.out.println("1) Continuar");
		System.out.println("2) Nueva Partida");
		System.out.println("3) Salir");
		System.out.println("Seleccione una opción:");
	}
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		boolean continuar = true;
		
		while (continuar) {
            MenuDefault();
            
            if (sc.hasNextInt()) {
                int opcion = sc.nextInt();

                switch (opcion) {
                    case 1:
                        System.out.println("Soy la opcion 1");
                        break;
                    case 2:
                        System.out.println("Soy la opcion 2");
                        
                        break;
                    case 3:
                        System.out.println("Saliendo del sistema...");
                        continuar = false; 
                        break;
                    default:
                        System.out.println("Opción no válida. Intente nuevamente.");
                }
            } else {
                System.out.println("Error: Por favor, ingrese un número.");
                sc.next(); 
            }
        }

        sc.close(); 
    }
}
