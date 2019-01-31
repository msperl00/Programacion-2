import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class BarajaFrancesa {
	public static String valor_[] = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "T","J","Q","K" };
	public static String palo_[] = { "C", "D", "H", "S" };
	
	public ArrayList<CartaFrancesa> baraja;
	public static  final int numeroCartas = 52;
	
	public BarajaFrancesa() {
		baraja = new ArrayList<CartaFrancesa>();
		inicializarBaraja();
		barajar();
	}
	
	private void barajar() {
		// TODO Auto-generated method stub
		Collections.shuffle(baraja);
	}

	private void inicializarBaraja() {
		// TODO Auto-generated method stub
		for (int i = 0; i < CartaFrancesa.valorPalo(); i++) {
			for (int j = 0; j < CartaFrancesa.valorCartas(); j++) {
				CartaFrancesa aux = new CartaFrancesa(valor_[j], palo_[i]);
				aux.buscarImagen();
				baraja.add(aux);
			}

		}
		
	}
	public String toString() {
		String st = "";

		for (Iterator<CartaFrancesa> iterator = baraja.iterator(); iterator.hasNext();) {
			CartaFrancesa carta = (CartaFrancesa) iterator.next();
			st = st + carta.toString() + " ";
		}
		return st;
	}
	
	public static boolean cartaspalo(ArrayList<CartaEspanola> baraja) {

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
