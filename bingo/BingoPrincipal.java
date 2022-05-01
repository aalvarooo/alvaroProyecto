package bingo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class BingoPrincipal {
	static int numjugadores;
	static Scanner in = new Scanner(System.in);
	public void numJugadores (int numJugadores)throws IllegalArgumentException {
			this.numjugadores=numJugadores;
			int numCartones=0;
		
	}		

	public static void main(String[] args) {
		// se pregunta por teclado cuantos jugadores van a jugar
		
		System.out.println("SE ABRE EL BINGO");
		System.out.println("�CUANTOS JUGADORES VAN A JUGAR?");
		numjugadores = in.nextInt();
		
	if (numjugadores<5) {
		throw new IllegalArgumentException("el numero de jugadores deben ser mayor que 5");
	}
	System.out.print(" pon los nombres de los jugadores"
		);

		// Declaración el ArrayList
		ArrayList<String> nombreArrayList = new ArrayList<String>();
		// Añadimos 10 Elementos en el ArrayList
		for (int i=1; i<=numjugadores; i++){
			nombreArrayList.add("Elemento "+i); 
		}

		 System.out.println("*********************");
	        System.out.println("*       BINGO       *");
	        System.out.println("*********************");
	        System.out.println();


	        /**
	         * Se pide al usuario cuantos cartones va a jugar y
	         * se guarda en la 1ª dimension del array cartones, tambien la copia.
	         * El número no puede ser mayor que 1.p
	         */
	     // Declaramos el Iterador e imprimimos los Elementos del ArrayList
	     			Iterator<String> nombreIterator = nombreArrayList.iterator();
	     			while(nombreIterator.hasNext()){
	     				String elemento = nombreIterator.next();
	     				String nombre =in.next();
	     				

	     				System.out.print(nombre + " tiene el " + elemento);

	     				System.out.print(" / ");
	     				System.out.print(" numero de cartones que quieres " + nombre + " ?");
	     				
	     				
						int[] lista = new int[in.nextInt()]; // Lista de números enteros que supondremos llena.

 	         			
	     				System.out.print(" pon el nombre del siguiente jugador");
	     				

	     			}      			
	     

	     			

		Bombo bomb = new Bombo();// se crea el bombo, llenandose el mismo
		Thread jugador;

	     			
		// se crean los huilos jugadores
		for (int i = 1; i <15; i++) {
			jugador = new Jugador(i, bomb);
			System.out.println("el jugador" + i + "esta preparado");
			jugador.start();// SE LANZAN
		}

		// se crea el hilo presentador, el cuaL cominza sacando un n�mero
		Thread present = new Presentador(bomb);
		present.start();// SE LANZAN

			}
}

