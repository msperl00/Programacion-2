import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public  class CartaEspanola {

	private String palo;
	private String numero;
	private ImageIcon imagen;
	private static ImageIcon REVERSO ;


	public CartaEspanola(String num, String pa) {

		this.numero = num;
		this.palo = pa;
		setREVERSO(new ImageIcon(getClass().getResource("Botones/reverso.jpg")));
		this.imagen = getREVERSO();

	}

	public CartaEspanola() {
		// TODO Auto-generated constructor stub
		setREVERSO(new ImageIcon(getClass().getResource("Botones/reverso.jpg")));
		this.imagen = getREVERSO();
		this.numero = null;
		this.palo = null;

	}

	public String toString() {
		String aux = toStringnumero().concat(toStringPalo());
		return aux;
	}

	public String toStringnumero() { // getter

		return numero;
	}

	public String toStringPalo() { // getter

		return palo;
	}
	



	public static  boolean esPareja(CartaEspanola actual, CartaEspanola carta2) {
		
		if(actual.toStringnumero().equals(carta2.toStringnumero()) || actual.toStringPalo().equals(carta2.toStringPalo())) {
			System.out.println(actual.toString()+"/"+carta2.toString());
			return true;
		}
	//System.out.println(actual.toString()+"/"+carta2.toString());

		return false;
	}
	
	public static int valorPalo() {
		return 4 ;
	}
	public static int valorCartas() {
		return 10;
	}
	
	public static boolean esIgual(CartaEspanola actual, CartaEspanola carta2) {
		
		if(actual.toStringnumero().equals(carta2.toStringnumero()) && actual.toStringPalo().equals(carta2.toStringPalo()))
			
			return true;
		
		return false;	}



public  static boolean esReverso(CartaEspanola actual) {
	
	return actual.imagen.equals(getREVERSO());

}

public void setImagenNueva(ImageIcon nueva) {
	// TODO Auto-generated method stub
	this.imagen = nueva;
}

public ImageIcon getImagen() {
		
		if(!imagen.equals(getREVERSO())) {
			return imagen;
		}
		return null;
}

public static ImageIcon getREVERSO() {
	return REVERSO;
}

public static void setREVERSO(ImageIcon rEVERSO) {
	REVERSO = rEVERSO;
}
}