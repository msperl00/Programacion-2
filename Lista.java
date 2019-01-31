import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JButton;



public class Lista {

	private ArrayList<Stack<JButton>> botones;
	private  ArrayList<Stack<CartaEspanola>> colection;
	private int contador;
	
	public Lista() {
	this.setBotones( new ArrayList<Stack<JButton>>());
	this.setColection(new ArrayList<Stack<CartaEspanola>>());
	contador = 0;
	}

	public ArrayList<Stack<JButton>> getBotones() {
		return botones;
	}

	public void setBotones(ArrayList<Stack<JButton>> botones) {
		this.botones = botones;
	}

	public ArrayList<Stack<CartaEspanola>> getColection() {
		return colection;
	}

	public void setColection(ArrayList<Stack<CartaEspanola>> colection) {
		this.colection = colection;
	}

	/**
	 * @return the contador
	 */
	public int getContador() {
		return contador;
	}

	/**
	 * @param contador the contador to set
	 */
	public void setContador(int contador) {
		this.contador = contador;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}


}
