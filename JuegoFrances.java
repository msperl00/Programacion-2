import java.util.ArrayList;
import java.util.Stack;

public class JuegoFrances {

	private ArrayList<CartaFrancesa> visible;
	private ArrayList<CartaFrancesa> tapada;
	private ArrayList<ArrayList<CartaFrancesa>> colection;
	private BarajaFrancesa miBaraja;

	private Stack<CartaFrancesa> clubs;
	private Stack<CartaFrancesa> diamons;
	private Stack<CartaFrancesa> hearts;
	private Stack<CartaFrancesa> spades;

	public JuegoFrances() {

		setVisible(new ArrayList<CartaFrancesa>());
		setTapada(new ArrayList<CartaFrancesa>());
		setColection(new ArrayList<ArrayList<CartaFrancesa>>());
		setClubs(new Stack<CartaFrancesa>());
		setDiamons(new Stack<CartaFrancesa>());
		setHearts(new Stack<CartaFrancesa>());
		setSpades(new Stack<CartaFrancesa>());
		miBaraja = new BarajaFrancesa();

		// for(int i = 0; i < BarajaFrancesa.numeroCartas; i++) {
		// visible.add(miBaraja.baraja.get(i));
		// }
		jugar();

	}

	/* Para resolver el juego */
	public JuegoFrances(BarajaFrancesa baraja) {
		this.miBaraja.baraja.clear();
		this.miBaraja.baraja = baraja.baraja;
		jugar();
	}

	public void moverPila(int n, ArrayList<CartaFrancesa> pila) {

		System.out.println(colection.size());
		ArrayList<CartaFrancesa> aux = colection.get(n);
		colection.get(n).clear();
		colection.set(n, aux);

	}

	private void jugar() {

		int i = 0;
		System.out.println(miBaraja.baraja.size());
		while (i < 28) {
			// Metemos una carta en la primera pila, ya que es la de la descubierta
			CartaFrancesa aux = miBaraja.baraja.get(i);
			System.out.println(aux.toString());
			ArrayList<CartaFrancesa> pila = new ArrayList<CartaFrancesa>();
			if (i == 0) {
				aux.setOculta(false);
			} else if (i >= 1 && i <= 2) {
				if (i == 1) {
					aux.setOculta(true);
				} else {
					aux.setOculta(false);
				}
			} else if (i >= 3 && i <= 5) {
				if (i == 5) {
					aux.setOculta(false);

				} else {
					aux.setOculta(true);

				}
				pila.add(aux);
			} else if (i >= 6 && i <= 9) {
				if (i == 9) {
					aux.setOculta(false);
				} else {
					aux.setOculta(true);

				}
			} else if (i > 9 && i < 15) {
				if (i == 14) {
					aux.setOculta(false);
				} else {
					aux.setOculta(true);
				}
			} else if (i >= 15 && i <= 20) {
				if (i == 20) {
					aux.setOculta(false);
				} else {
					aux.setOculta(true);
				}
			} else if (i >= 21 && i <= 27) {
				if (i == 27) {
					aux.setOculta(false);
				} else {
					aux.setOculta(true);
				}
			}
			pila.add(aux);
			i++;
			colection.add(pila);
		}
		System.out.println(colection.toString());
		CartaFrancesa aux = miBaraja.baraja.get(i);
		aux.setOculta(false);
		this.visible.add(aux);

		System.out.println(visible.toString());
		while (i < 52) {
			CartaFrancesa aux1 = miBaraja.baraja.get(i);
			aux.setOculta(true);
			tapada.add(aux1);
			i++;
		}
		System.out.println(tapada.toString());
	}

	/**
	 * @return the tapada
	 */
	public ArrayList<CartaFrancesa> getTapada() {
		return tapada;
	}

	/**
	 * @param tapada
	 *            the tapada to set
	 */
	public void setTapada(ArrayList<CartaFrancesa> tapada) {
		this.tapada = tapada;
	}

	/**
	 * @return the colection
	 */
	public ArrayList<ArrayList<CartaFrancesa>> getColection() {
		return colection;
	}

	/**
	 * @param colection
	 *            the colection to set
	 */
	public void setColection(ArrayList<ArrayList<CartaFrancesa>> colection) {
		this.colection = colection;
	}

	/**
	 * @return the visible
	 */
	public ArrayList<CartaFrancesa> getVisible() {
		return visible;
	}

	/**
	 * @param baraja
	 *            the baraja to set
	 */
	public void setVisible(ArrayList<CartaFrancesa> baraja) {
		this.visible = baraja;
	}

	/**
	 * @return the miBaraja
	 */
	public BarajaFrancesa getMiBaraja() {
		return miBaraja;
	}

	/**
	 * @param miBaraja
	 *            the miBaraja to set
	 */
	public void setMiBaraja(BarajaFrancesa miBaraja) {
		this.miBaraja = miBaraja;
	}

	/**
	 * @return the clubs
	 */
	public Stack<CartaFrancesa> getClubs() {
		return clubs;
	}

	/**
	 * @param clubs
	 *            the clubs to set
	 */
	public void setClubs(Stack<CartaFrancesa> clubs) {
		this.clubs = clubs;
	}

	/**
	 * @return the diamons
	 */
	public Stack<CartaFrancesa> getDiamons() {
		return diamons;
	}

	/**
	 * @param diamons
	 *            the diamons to set
	 */
	public void setDiamons(Stack<CartaFrancesa> diamons) {
		this.diamons = diamons;
	}

	/**
	 * @return the hearts
	 */
	public Stack<CartaFrancesa> getHearts() {
		return hearts;
	}

	/**
	 * @param hearts
	 *            the hearts to set
	 */
	public void setHearts(Stack<CartaFrancesa> hearts) {
		this.hearts = hearts;
	}

	/**
	 * @return the spades
	 */
	public Stack<CartaFrancesa> getSpades() {
		return spades;
	}

	/**
	 * @param spades
	 *            the spades to set
	 */
	public void setSpades(Stack<CartaFrancesa> spades) {
		this.spades = spades;
	}

	@Override
	public String toString() {
		return "Juego Clasico [baraja boca abajo=" + tapada.toString() + ", barajaVisible=" + visible.toString()
				+ ", listaPilas=" + colection.toString() + ", Clubs=" + clubs.toString() + ", Diamonds="
				+ diamons.toString() + ", Hearts=" + hearts.toString() + ", Spades=" + spades.toString() + "]";
	}

}
