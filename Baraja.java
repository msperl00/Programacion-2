import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class Baraja {

	public String valor_[] = { "AS", "2", "3", "4", "5", "6", "7", "S", "C", "R" };
	public String palo_[] = { "O", "B", "E", "C" };

	public final int numeroCartas = 40;

	public ArrayList<Carta> baraja;

	public Baraja() {

		baraja = new ArrayList<Carta>();
		inicializarBaraja();
		barajar();

	}

	private void inicializarBaraja() {
		// TODO Auto-generated method stub
		for (int i = 0; i < Carta.valorPalo(); i++) {
			for (int j = 0; j < Carta.valorCartas(); j++) {
				baraja.add(new Carta(valor_[j], palo_[i]));
			}

		}
	}

	// Método que baraja la baraja.
	public void barajar() {

		Collections.shuffle(baraja);

	}
	
	public Iterator<Carta> iterable()	{
		return baraja.iterator();
		
	}

}
