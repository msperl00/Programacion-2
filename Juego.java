import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

public class Juego {

	private ArrayList<Stack<Carta>> colection = new ArrayList<Stack<Carta>>();
	private ArrayList<Baraja> variasBarajas = new ArrayList<Baraja>();
	private Scanner scan = new Scanner(System.in);
	private Carta carta;
	private int entradas;
	private int contadorCartas;
	private Baraja aux;

	public Juego() {
		/* Constructor */
		entradas = 0;
		contadorCartas = 0;

		entrada();
		dividirBarajas();

	}

	private void dividirBarajas() {

		if (!variasBarajas.isEmpty()) {
			for (Baraja actual : variasBarajas) {
				// System.out.println(actual.baraja.toString());
				apilar(actual);
				solucion();

			}
		} else {
			// System.out.println("DividirBarajas");

			error();
		}

	}

	// SOLO LO HACE CON UNA COMPROBAR ALGO EN CARTASPALO O BARAJADISTINTAS

	private void comprobarBarajas(Baraja baraja) {

		/*
		 * Comrpueba el numero de cartas, s
		 * 
		 * /* Comprueba si por algun caso, las entradas estan mal.
		 */
		// Si es impar, significa que nos han metido media baraja.

		if (cartaspalo(baraja) && barajasDistintas(baraja) && baraja.baraja.size() == 52) {
			// System.out.println("Vamos bien");
		} else {
			// System.out.println("ComprobarBarajas");

			error();
		}

	}

	private boolean barajasDistintas(Baraja baraja) {

		/* Comprobacion en la misma baraja de opciones */

		aux = new Baraja();
		aux.baraja = baraja.baraja;

		// Compruebo el primero con el 26 y los siguientes.

		for (int i = 0; i < baraja.numeroCartas - 1; i++) {
			for (int j = i; j < baraja.numeroCartas - 1; j++) {
				if (Carta.esIgual(aux.baraja.get(i), baraja.baraja.get(j + 1)))
					return false;

			}
		}

		return true;

	}

	private void error() {
		System.out.println("Entrada incorrecta.");
		System.exit(0);
	}

	private boolean cartaspalo(Baraja baraja) {

		/* Comprueba los valores dentro de las cartas */

		while (baraja.baraja.iterator().hasNext()) {
			Carta now = baraja.baraja.iterator().next();

			for (int i = 0; i < baraja.palo.length; i++) {

				if (now.toStringPalo().equals(baraja.palo[i])) {

					for (int j = 0; j < baraja.valor.length; j++) {

						if (now.toStringnumero().equals(baraja.valor[j])) {

							return true;
						}

					}

				}
			}
			return false;

		}

		return false;
	}

	private void entrada() // Meteremos los valores
	{
		/* Donde se gestiona la entrada de los valores */

		String ahora = null;
		String comparable = null;
		Baraja mia = new Baraja();
		try {
			do {

				ahora = scan.nextLine();
				comparable = ahora;

				if (!comparable.equals("#")) {

					String array[] = ahora.split(" ");
					++entradas;

					for (int i = 0; i < array.length; i++) {

						try {
							carta = new Carta(array[i].substring(0, 1), array[i].substring(1, 2));
						} catch (StringIndexOutOfBoundsException e) {
							error();
						}
						mia.baraja.add(carta);
						++contadorCartas;
						carta = null;

						/* Cada dos entradas comprobamos */

					}
					if (contadorCartas % (mia.numeroCartas / 2) == 0) {
						if (entradas % 2 == 0) {
							// System.out.println(mia.baraja.toString());
							comprobarBarajas(mia);
							variasBarajas.add(mia);
							mia = new Baraja();
						}

					} else {
						// System.out.println("En entrada");
						error();
					}

				}

			} while (!comparable.equals("#"));

		} catch (NullPointerException e) {
			error();

		} catch (NoSuchElementException e) {
			error();

		}

	}

	private void apilar(Baraja baraja) {
		/*
		 * Funcion donde jugamos
		 * 
		 * Metemos la primera carta y la segunda en dos pilas !=, y iteramos hasta
		 * quedarnos sin cartas pasando a juego la carta actual que o añadimos a una
		 * pila != o se la pasamos a otra anterior
		 * 
		 */

		Carta actual = new Carta();
		Stack<Carta> pila = new Stack<Carta>();
		Stack<Carta> pila1;

		// Metemos el primer valor de la baraja
		actual = baraja.baraja.get(0);
		pila.push(actual);
		colection.add(pila);

		for (int i = 1; i < baraja.numeroCartas; i++) {

			pila1 = new Stack<Carta>();
			actual = baraja.baraja.get(i);
			pila1.push(actual);
			// System.out.println(actual.toString());
			colection.add(pila1);
		//	System.out.println(colection.toString());
			jugando();
		}

	}

	/**********************************************************
	 * Método que recorre toda la baraja buscando en primera instancia la carta que
	 * más a la iquierda se pueda desplazar. Cuando la carta a desplazar sea la
	 * posicion 0, no se comprueba más y se vuelve al inicio.
	 * 
	 * 
	 ***********************************************************/

	private void jugando() {

		/*
		 * Se tendra que ver en este caso que carta es la que se desplaza más a la
		 * izquierda. Por lo tanto guardaremos la posicion y comporbaremos en las demás.
		 */

		int posicion = 99;
		boolean uno = false;
		int actual = 0;

		while (actual < colection.size()) {
			
			if (compTresIzq(actual)) {
				tresIzq(actual);
	//			System.out.println(colection.toString());
				jugando();

			} else if (compUnaIzq(actual)) {
				posicion = actual - 1;
				for (int i = actual + 1; i < colection.size() && i < posicion +3; i++) {
		//			System.out.println("EWntro primer nv");
					if (compTresIzq(i)) {
	//					System.out.println("Entro nivel final");
						tresIzq(i);
						uno = true;
	//					System.out.println(colection.toString());
						jugando();
					}
				}
				/*
				 * Si no se ha encontrado uno que lo supere,
				 */
				if (!uno) {
					unaIzq(actual);
	//				System.out.println(colection.toString());
					jugando();
				}
				
			}
			actual++;
		}

	}

	private boolean compTresIzq(int actual) {
		/* Comprueba si hay más de 3 pilas, y que la pila no este vacia */

		if (colection.size() > 3 && actual >= 3) { // Nos pasan un valor que ya es el de 3
			// posiciones atras
			return Carta.esPareja(colection.get(actual - 3).peek(), colection.get(actual).peek());
		}
		return false;

	}

	private void tresIzq(int actual) {
		/* Elimina el elemento de la pila, y lo inserta en el de su pareja */
	//	System.out.println("Muevo " + actual + "- > " + 3 + " atrás");
		colection.get(actual - 3).push(colection.get(actual).pop());

		siVacio(actual);
	}

	private boolean compUnaIzq(int actual) {
		/* Comprueba si hay más de 3 pilas, y que la pila no este vacia */

		if (colection.size() >= 2 && actual >= 1) { // Nos pasan un valor que ya es el de 3
			// posiciones atras
			return Carta.esPareja(colection.get(actual - 1).peek(), colection.get(actual).peek()); // fallo
		}
		return false;
	}

	private void unaIzq(int actual) {
		/* Elimina el elemento de la pila, y lo inserta en el de su pareja */
//		System.out.println("Muevo " + actual + "- > " + 1 + " atrás");
		colection.get(actual - 1).push(colection.get(actual).pop());
		siVacio(actual);

	}

	private void siVacio(int contador) throws EmptyStackException {
		/* Comprueba si la pila del elemento que acabamos de sacar esta vacia */
		if (colection.get(contador).isEmpty()) {
			colection.remove(contador);
		}
	}

	private void solucion() {

		/*
		 * Recogo los valores que pueden quedar en una pila. Si hay solo una pila, la
		 * solucion final se ha conseguido Si hay dos o + pilas, se tiene que contar el
		 * numero de elementos que contiene y mostrarlo por pantalla
		 */
		// System.out.println(" Detro de solucionado");

		String[] array = new String[colection.size()];
		StringBuffer cadena = new StringBuffer();

		for (int i = 0; i < array.length; i++) {
			array[i] = Integer.toString(colection.get(i).size());
			cadena.append(array[i]);
			cadena.append(" ");
		}
		if (colection.size() > 1) {
			System.out.println("Han quedado " + colection.size() + " pilas: " + cadena.toString());
		} else {
			System.out.println("Ha quedado " + colection.size() + " pila: " + cadena.toString());

		}
		colection.clear();
	}
}
