import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

public class JuegoEspañol {

	private JPanel p1, p2;
	private GridLayout grid;
	private ArrayList<JButton> botones = new ArrayList<JButton>();
	private BarajaEspanola baraja;
	private JButton saca, bsoluciones;
	private int contador;
	private CartaEspanola reverso, carta;
	private Iterator<CartaEspanola> it;
	private boolean empezar, sol;
	private ArrayList<Stack<CartaEspanola>> colection = new ArrayList<Stack<CartaEspanola>>();
	private int numeroSol;
	private static int ncartassacadas;
	private BorderLayout b;

	/*
	 * Cada carta de la baraja tiene que estar asignada a un boton.
	 */

	public JuegoEspañol(JPanel p1, JPanel p2) {

		this.p1 = p1;
		this.p2 = p2;

		grid = new GridLayout(6, 8);
		baraja = new BarajaEspanola();
		sol = false;
		empezar = false;
		contador = 0;
		ncartassacadas = 0;
		reverso = new CartaEspanola();
		it = baraja.iterable();
		cargaInicial();

	}





	private void cargaInicial() {

		for (int i = 0; i < 40; i++) {

			botones.add(new JButton(new ImageIcon(getClass().getResource("Botones/reverso.jpg"))));
			botones.get(i).setBackground(new Color(0, 180, 50));
			botones.get(i).addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (empezar == true) {
						int pos = buscarCarta(e.getSource()); // Misma posicion que la dejaron
						jugando(pos);
					} else {
						JOptionPane.showMessageDialog(p2, "Todavia no se han sacado todas las cartas");
					}
				}

			});
			p2.add(botones.get(i));

		}
		grid.setHgap(60);
		grid.setVgap(30);
		p2.setLayout(grid);
		p2.setBackground(new Color(0, 180, 50));

		b = new BorderLayout(10, 10);

		p1.setLayout(b);
		p1.setBackground(new Color(0, 180, 50));

		saca = new JButton("Sacar carta");
		saca.setFont(new Font("Arial", Font.BOLD, 20));
		saca.setBackground(new Color(0, 180, 50));
		saca.setBorderPainted(false);
		saca.setVisible(true);
		saca.setBounds(50, 300, 150, 150);
		p1.add(saca, BorderLayout.NORTH);

		saca.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (empezar == false) {
					sacarCarta();
				} else {
					JOptionPane.showMessageDialog(p2, "Todas las cartas sacadas");
				}
			}

		});
		bsoluciones = new JButton("Nº cartas movibles");
		bsoluciones.setFont(new Font("Arial", Font.BOLD, 20));
		bsoluciones.setBackground(new Color(0, 180, 50));
		bsoluciones.setBorderPainted(false);
		bsoluciones.setVisible(true);
		bsoluciones.setBounds(50, 300, 150, 150);

		bsoluciones.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// Bucle comprobando cuantas cartas se pueden mover
				if (empezar == true) {
					int i = 0,  con = 0;
					boolean si = false;
					while (i < colection.size() || sol == true) {

						if (compTresIzq(i)) {
							si = true;
						}else if (compUnaIzq(i)) {
							si = true;
						}
						if(si == true) {
							++con;
						}
						i++;
					}
					String ac = String.valueOf(con);
					JOptionPane.showMessageDialog(p2, "Todavia no se han sacado todas las cartas: "+ac);

				} else {
					JOptionPane.showMessageDialog(p2, "Todavia no se han sacado todas las cartas");
				}
			}
		});

		p1.add(bsoluciones, BorderLayout.CENTER);

	}

	protected int buscarCarta(Object source) {

		/*
		 * Buscamos en el array de botones la posicion que ocupa el boton, y la
		 * devolvemos
		 */
		JButton b = new JButton();

		if (source instanceof JButton) {
			b = (JButton) source;
		}
		for (int i = 0; i < botones.size(); i++) {

			if (botones.get(i).equals(b)) {
				return i;
			}

		}

		return 0;
	}

	protected void iniciar() {

		/*
		 * Metemos todas las cartas que han sido sacadas, en un arraylist de cartas,
		 * para poder jugar
		 */

		for (int i = 0; i < baraja.numeroCartas; i++) {

			Stack<CartaEspanola> pila1 = new Stack<CartaEspanola>();
			CartaEspanola actual = baraja.baraja.get(i);
			pila1.push(actual);
			colection.add(pila1);

		}

		System.out.println(colection.size());

	}

	private boolean compTresIzq(int actual) {
		// TODO Auto-generated method stub
		if (colection.size() > 3 && actual >= 3) {
			return CartaEspanola.esPareja(colection.get(actual - 3).peek(), colection.get(actual).peek());

		}
		return false;
	}

	private void TresIzq(int actual) {
		System.out.println("Entra a ver si es pareja -3 ->" + colection.size() + "/" + actual);
		// Muevo tanto en la colection, como en
		CartaEspanola c = colection.get(actual).peek(); // Cojo la imagen
		colection.get(actual - 3).push(colection.get(actual).pop());
		botones.get(actual - 3).setIcon(buscarImagen(c)); // Meto la imagen en el boton nuevo
		p2.remove(botones.get(actual));
		p2.validate();
		p2.repaint();
		botones.remove(actual); // Elimino el boton que ya ha sido apilado

		siVacio(actual);

	}

	private void siVacio(int actual) throws EmptyStackException {
		/* Comprueba si la pila del elemento que acabamos de sacar esta vacia */

		if (colection.get(actual).isEmpty()) {
			colection.remove(actual);
		}
		System.out.println(colection.toString());
	}

	protected void jugando(int pos) {
		// TODO Auto-generated method stub
		if (compTresIzq(pos)) {
			JOptionPane.showMessageDialog(p2, "Movimiento -3  válido");
			TresIzq(pos);
		} else if (compUnaIzq(pos)) {
			JOptionPane.showMessageDialog(p2, "Movimiento -1  válido");
			unaIzq(pos);
		} else {
			JOptionPane.showMessageDialog(p2, "Movimiento NO válido");
		}

		comprobar();

	}

	private void comprobar() {
		// TODO Auto-generated method stub
		int malcontador = 0;
		for (int i = 0; i < colection.size(); i++) {
			if (!compTresIzq(i) && !compUnaIzq(i)) {
				malcontador++;
			}
		}

		if (malcontador == colection.size()) {
			JOptionPane.showMessageDialog(p2, "Ohhh! Ha perdido, intentelo de nuevo");
		}

		numeroSol = colection.size() - contador;

	}

	private boolean compUnaIzq(int actual) {

		if (colection.size() >= 2 && actual >= 1) { // Nos pasan un valor que ya es el de 3
			// posiciones atras

			return CartaEspanola.esPareja(colection.get(actual - 1).peek(), colection.get(actual).peek()); // fallo
		}
		return false;
	}

	private void unaIzq(int actual) {
		System.out.println("Entra a ver si es pareja -1 -> " + colection.size() + "/" + actual);
		CartaEspanola c = colection.get(actual).peek(); // Cojo la imagen
		colection.get(actual - 1).push(colection.get(actual).pop()); // Apilo
		botones.get(actual - 1).setIcon(buscarImagen(c)); // Meto la imagen en el boton nuevo
		p2.remove(botones.get(actual));
		p2.validate();
		p2.repaint();
		botones.remove(actual); // Elimino el boton que ya ha sido apilado
		System.out.println(colection.toString());
		siVacio(actual);

	}

	protected void sacarCarta() {

		if (it.hasNext()) {
			setNCartasSacadas();
			CartaEspanola tmp = it.next();
			tmp.setImagenNueva(buscarImagen(tmp)); // La imagen ya la tiene la carta, así que se la meto a la primera
													// posicion del
			// boton
			botones.get(contador).setIcon(tmp.getImagen());
			p2.repaint();
			++contador;

			if (contador == 40 && empezar == false) { // No vuelve a sacar Carta cuando

				empezar = true;
				contador = 0;
				iniciar();
			}

		}

	}
	private void setNCartasSacadas() {
		// TODO Auto-generated method stub
		++ncartassacadas;
	}
	public static int getNCartasSacadas() {
		return ncartassacadas;
	}

	private ImageIcon buscarImagen(CartaEspanola tmp) {
		// TODO Auto-generated method stub
		String name = tmp.toString();
		System.out.println(tmp.toString());
		ImageIcon nueva = new ImageIcon(getClass().getResource("española/" + name + ".jpg"));
		return nueva; // Cada carta va a tener su imagen asociada

	}

	/*
	 * ANOTACIONES
	 * 
	 * HACER EL MENU CON TODOS SUS PUNTOS HACER RESOLVER HACER GUARDAR Y CARGAR
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

}
