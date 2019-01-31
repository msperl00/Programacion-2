import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOError;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class LeerYEscribir {

	private String archivo;
	private ArrayList<String> leidas;
	private ArrayList<Stack<JButton>> botones = new ArrayList<Stack<JButton>>();
	private ArrayList<Stack<CartaEspanola>> colection = new ArrayList<Stack<CartaEspanola>>();
	private ArrayList<CartaEspanola> baraja= new ArrayList<CartaEspanola>();


	public LeerYEscribir() {
		this.setArchivo("");
		this.setLeidas(new ArrayList<String>());
		ArrayList<Stack<CartaEspanola>> colection ;
		ArrayList<Stack<JButton>> botones ;
		ArrayList<CartaEspanola> baraja;
	}

	public void abrir() {
		JFileChooser jfc = new JFileChooser();

		int op = jfc.showOpenDialog(null);
		if (op == JFileChooser.APPROVE_OPTION) {
			File file = jfc.getSelectedFile();
			this.setArchivo(file.getAbsolutePath());
			System.out.println(getArchivo());
		}

	}

	public void leer() {
		if (getArchivo().equals("")) {
			JOptionPane.showMessageDialog(null, "No se ha seleccionado ningun fichero");
		} else {
			System.out.println("En leer");
			FileReader f = null;
			try {
				f = new FileReader(getArchivo());
				BufferedReader b = new BufferedReader(f);
				String valor;
				while ((valor = b.readLine()) != null) {
					leidas.add(valor);
				}
				b.close();
			} catch (FileNotFoundException e) {
				// TODO: handle exception
			} catch (IOException e) {
				// TODO: handle exception
			}

		}

	}

	/**
	 * @return the archivo
	 */
	public String getArchivo() {
		return archivo;
	}

	/**
	 * @param archivo
	 *            the archivo to set
	 */
	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	/**
	 * @return the leidas
	 */
	public ArrayList<String> getLeidas() {
		return leidas;
	}

	/**
	 * @param leidas
	 *            the leidas to set
	 */
	public void setLeidas(ArrayList<String> leidas) {
		this.leidas = leidas;
	}

	/**
	 * @return the botones
	 */
	public ArrayList<Stack<JButton>> getBotones() {
		return botones;
	}

	/**
	 * @param botones the botones to set
	 */
	public void setBotones(ArrayList<Stack<JButton>> botones) {
		this.botones = botones;
	}

	/**
	 * @return the colection
	 */
	public ArrayList<Stack<CartaEspanola>> getColection() {
		return colection;
	}

	/**
	 * @param colection the colection to set
	 */
	public void setColection(ArrayList<Stack<CartaEspanola>> colection) {
		this.colection = colection;
	}

	/**
	 * @return the baraja
	 */
	public ArrayList<CartaEspanola> getBaraja() {
		return baraja;
	}

	public boolean actualizar() {
		// TODO Auto-generated method stub
		System.out.println("En actualizar");
		int i = 0;
	for (Iterator<String> iterator = leidas.iterator(); iterator.hasNext();) {
//		Stack<JButton> stack = (Stack<JButton>) iterator.next();
		if(i == 0) {
			String nombre = iterator.next();
			if(!nombre.equals("Solitario saltos")) {
				return false;
			}
		}else if( i == 1) {
			String texto[]	 =  iterator.next().split(" ");
			baraja =  BarajaEspanola.barajaAMano(texto);
			if(!BarajaEspanola.cartaspalo(baraja)){
				return false;
			}
		}else if (i > 1 && i < 41) {
			
			String texto[]	 =  iterator.next().split(" ");
			Stack<CartaEspanola> nueva = new Stack<CartaEspanola>();
			ArrayList<CartaEspanola>probamos = new ArrayList<CartaEspanola>();
			for (int j = 0; j < texto.length; j++) {
				probamos.add(new CartaEspanola(texto[j].substring(0, 1), texto[j].substring(1, 2)));
				nueva.push(new CartaEspanola(texto[j].substring(0, 1), texto[j].substring(1, 2)));
			}
			if(!BarajaEspanola.cartaspalo(probamos)) {
				return false;
			}
			colection.add(nueva);
		}
		
		i++;
	}
	
	System.out.println(colection.toString());
	System.out.println(baraja.toString());
	
	return true;
	}



}
