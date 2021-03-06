package bingo;

import java.util.HashSet;
import java.util.Scanner;

import bingo2.Bingo;

/**
 * @author Alvaro Mateos
 * @date 28/4/2022
 */

//----------------------------------------------------------------------------------------------------//

/**
 * Clase que sirve para crear cada uno de los hilos de los jugadores
 */
class Jugador extends Thread {
	final int TOTAL_CARTON = 15; // Cantidad de n�meros por cart�n
	final int TOTAL_BOMBO = 89; // N�meros posibles del bombo
	int idJugador; // Identificador del jugador
	HashSet<Integer> carton; // Para almacenar los n�meros pendientes de acertar
	Bombo b;

	/**
	 * @param identificador
	 *            del jugador
	 */
	
	//el numero de jugadores debe ser mayor que 5
	Jugador(int idJugador, Bombo b) {
		this.b = b;
		this.idJugador = idJugador;
		carton = new HashSet<Integer>();
		while (carton.size() < TOTAL_CARTON)
			carton.add((int) Math.floor(Math.random() * 89) + 1);
		System.out.println("CARTON JUGADOR: " + idJugador + carton);
	}
	
	/**
	 * Muestra el cart�n por pantalla con los n�meros pendientes
	 */
	void imprimeCarton() {
		System.out.print("Pendientes jugador " + idJugador + ": ");
		for (Integer integer : carton)
			System.out.print(integer + " ");
		System.out.println();
	}

	/**
	 * Tacha el n�mero del cart�n en caso de que exista
	 * 
	 * @param numero
	 *            a tachar
	 */
	void tacharNum(Integer numero) {
		carton.remove(numero);
	}

	/**
	 * M�todo encargado de las acciones del jugador.
	 */
	public void run() {
		while (carton.size() > 0) {// mientras que el cart�n no tenga tachados
									// todos los n�meros

			// imprimeCarton();
			b.consultar();// se consulta
			System.out.println("El jugador  " + idJugador + "  va ha jugar ");
			tacharNum(b.ultNumero);// se tacha el ultimo numero que ha salido
			for (Integer integer : b.bombo) {// se comprueba que hayamos tachado
												// todos los n�meros que han
												// salido, en su defecto se
												// tachan.
				carton.remove(integer);
			}

			System.out.println("El jugador  " + idJugador + "  ha jugado ");
			imprimeCarton();// SE IMPRIMEN LOS numeros PENDIENTES DE DICHO JUGADOR

		}
		System.out.println("el jugador " + idJugador + " ha hecho BINGO! <---------------BINGO--------------->");
		// si hace bingo termina la ejecucion del programa
		try {
			finalize();
			System.exit(0);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

// ----------------------------------------------------------------------------------------------------//

/**
 * Clase que sirve para el hilo del presentador
 */
class Presentador extends Thread {
	Bombo c;
	// Bingo bing;
	//int aux;

	Presentador(Bombo c) {
		this.c = c;

	}

	/**
	 * El presentador saca un n�mero cada 2 segundos
	 */
	public void run() {
		try {

			for (int i = 0; i < c.TOTAL_BOMBO; i++) {// va sacando numeros hasta llegar al total
				Thread.sleep(2000);
				c.sacarNum();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	
	}

}

// ----------------------------------------------------------------------------------------------------//

/**
 * Clase que se utiliza para crear el objeto compartido entre todos los hilos
 * del programa
 */
class Bombo {
	int numvecJugado = 0;//numero de veces que se consulto
	int hayBola = 0;// indica si se ha repartido
	Jugador jug;
	final int TOTAL_BOMBO = 89; // N�meros posibles del bombo
	HashSet<Integer> bombo; // Para almacenar los valores que van saliendo
	Integer ultNumero; // �ltimo n�mero del bombo

	int aux = Bingo.getNumjugadores();

	/**
	 * Inicializa vac�o el bombo
	 */
	Bombo() {

		bombo = new HashSet<Integer>();

	}

	/**
	 * m�todo sincronizado que comprueba si se ha sacado bola
	 */
	synchronized void sacarNum() {

		while (hayBola != 0 /* && numvecJugado!= aux */) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		sacarNum2();
		imprimirBombo();
		hayBola++;
		// hayBola=0;
		notify();

	}

	/**
	 * Metodo encargado de sacar una bola, es llamado en sacarNum
	 * @return
	 */
	Integer sacarNum2() {
		Integer bolita = 0;
		int cantidadBolas = bombo.size();
		if (cantidadBolas < TOTAL_BOMBO) {
			do {
				ultNumero = (int) Math.floor(Math.random() * TOTAL_BOMBO) + 1;
				bombo.add(ultNumero);
				bolita = ultNumero;
			} while (cantidadBolas == bombo.size());
			System.out.println("Ha salido el n�mero: " + ultNumero);
		} else
			System.out.println("Ya han salido todas las bolas");
		return bolita;
	}

	/**
	 * metodo sincronizado accedido por los jugadores
	 */
	synchronized void consultar() {
		while (hayBola == 0 /* && numvecJugado>= aux */) {// si no se ha repartido, espera
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		numvecJugado++;
		hayBola--;
		notify();

	}

	/**
	 * Muestra todas las bolas que han salido hasta el momento
	 */
	void imprimirBombo() {
		System.out.print("Bolas sacadas hasta el momento: ");
		for (Integer integer : bombo)
			System.out.print(integer + " ");
		System.out.println();
	}

}

// ----------------------------------------------------------------------------------------------------//
// ----------------------------------------------------------------------------------------------------//

/**
 * Clase principal desde la que se inicializa el juego del Bingo
 */
