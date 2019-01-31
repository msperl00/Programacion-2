import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class BarajaEspanola {

	public static String valor_[] = { "AS", "2", "3", "4", "5", "6", "7", "S", "C", "R" };
	public static String palo_[] = { "O", "B", "E", "C" };

	public final int numeroCartas = 40;

	public ArrayList<CartaEspanola> baraja;

	public BarajaEspanola() {

		baraja = new ArrayList<CartaEspanola>();
		inicializarBaraja();
		barajar();

	}

	private void inicializarBaraja() {
		// TODO Auto-generated method stub
		for (int i = 0; i < CartaEspanola.valorPalo(); i++) {
			for (int j = 0; j < CartaEspanola.valorCartas(); j++) {
				baraja.add(new CartaEspanola(valor_[j], palo_[i]));
			}

		}
	}

	// Método que baraja la baraja.
	public void barajar() {

		Collections.shuffle(baraja);

	}

	public Iterator<CartaEspanola> iterable() {
		return baraja.iterator();
	}

	public String toString() {
		String st = "";

		for (Iterator<CartaEspanola> iterator = baraja.iterator(); iterator.hasNext();) {
			CartaEspanola carta = (CartaEspanola) iterator.next();
			st = st + carta.toString() + " ";
		}
		return st;
	}

	public static ArrayList<CartaEspanola> barajaAMano(String texto[]) {

		ArrayList<CartaEspanola> nueva = new ArrayList<CartaEspanola>();

		for (int i = 0; i < texto.length; i++) {
			nueva.add(new CartaEspanola(texto[i].substring(0, 1), texto[i].substring(1, 2)));
		}

		return nueva;
	}

	public  static boolean cartaspalo(ArrayList<CartaEspanola> baraja) {

		/* Comprueba los valores dentro de las cartas */

		while (baraja.iterator().hasNext()) {
			CartaEspanola now = baraja.iterator().next();

			for (int i = 0; i < palo_.length; i++) {

				if (now.toStringPalo().equals(palo_[i])) {

					for (int j = 0; j < valor_.length; j++) {

						if (now.toStringnumero().equals(valor_[j])) {

							return true;
						}

					}

				}
			}
			return false;

		}
		return false;
	}

}
