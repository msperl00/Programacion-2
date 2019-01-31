import javax.swing.ImageIcon;

public class CartaFrancesa {

	private String palo;
	private String numero;
	private ImageIcon imagen;
	private static ImageIcon REVERSO;
	private boolean oculta;
	private boolean roja;

	public CartaFrancesa(String num, String pa) {
		this.numero = num;
		this.palo = pa;
		setREVERSO(new ImageIcon(getClass().getResource("francesa/Yellow.png")));
		this.oculta = false;
		this.imagen = null;
		
		this.roja = esRoja();
	}

	public  boolean esRoja() {
		if (palo.equals("H") || palo.equals("D")) {
			return true;
		}
		return false;

	}

	public static boolean mismoColor(CartaFrancesa actual, CartaFrancesa nueva) {
		if (actual.esRoja() && nueva.esRoja()) {
			return true;
		}else if(!actual.esRoja() && ! nueva.esRoja()) {
			return true;
		}
		return false;
	}

	public void buscarImagen() {
		setImagen(new ImageIcon(getClass().getResource("francesa/" + toString() + ".png")));
	}

	/**
	 * @return the palo
	 */
	public String getPalo() {
		return palo;
	}

	/**
	 * @param palo
	 *            the palo to set
	 */
	public void setPalo(String palo) {
		this.palo = palo;
	}

	/**
	 * @return the numero
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * @param numero
	 *            the numero to set
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * @return the imagen
	 */
	public ImageIcon getImagen() {
		return imagen;
	}

	/**
	 * @param imagen
	 *            the imagen to set
	 */
	public void setImagen(ImageIcon imagen) {
		this.imagen = imagen;
	}

	/**
	 * @return the rEVERSO
	 */

	public String toString() {
		String aux = getNumero().concat(getPalo());
		return aux;
	}

	public static int valorPalo() {
		return 4;
	}

	public static int valorCartas() {
		return 13;
	}

	public static boolean esPareja(CartaFrancesa actual, CartaFrancesa carta2) {

		if (actual.getNumero().equals(carta2.getNumero()) || actual.getPalo().equals(carta2.getPalo())) {
			System.out.println(actual.toString() + "/" + carta2.toString());
			return true;
		}
		// System.out.println(actual.toString()+"/"+carta2.toString());

		return false;
	}

	public static boolean esIgual(CartaFrancesa actual, CartaFrancesa carta2) {

		if (actual.getNumero().equals(carta2.getNumero()) && actual.getPalo().equals(carta2.getPalo()))

			return true;

		return false;
	}

public static boolean colocar(CartaFrancesa actual, CartaFrancesa carta2) {
	
	if(mismoColor(actual, carta2) && (Integer.parseInt(carta2.numero) > Integer.parseInt(actual.numero))) {
		return true;
	}
	return false;
}

	/**
	 * @return the oculta
	 */
	public boolean isOculta() {
		return oculta;
	}

	/**
	 * @param oculta
	 *            the oculta to set
	 */
	public void setOculta(boolean oculta) {
		this.oculta = oculta;
	}

	/**
	 * @return the rEVERSO
	 */
	public static ImageIcon getREVERSO() {
		return REVERSO;
	}

	/**
	 * @param rEVERSO
	 *            the rEVERSO to set
	 */
	public static void setREVERSO(ImageIcon rEVERSO) {
		REVERSO = rEVERSO;
	}
}
