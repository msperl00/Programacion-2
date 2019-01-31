import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.EventObject;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;

public class PanelJuegoEspañol extends JFrame {

	private JPanel p, panel1, panel2;

	private JMenuBar menu;
	private JMenu archivo, editar, historial, ayuda;
	public JMenuItem nuevo, cargar, guardarComo, salir, guardar, hacer, deshacer, resolver, ficheroestadisticas,
			estadisticas;
	private String nombre = new String("Solitario saltos");
	private GridLayout grid;
	private ArrayList<Stack<JButton>> botones = new ArrayList<Stack<JButton>>();
	private final BarajaEspanola baraja;
	private BarajaEspanola copiaBaraja;
	private JButton saca, atras;
	private Iterator<CartaEspanola> it;
	private ArrayList<Stack<CartaEspanola>> colection = new ArrayList<Stack<CartaEspanola>>();
	private static int ncartassacadas;
	private GridLayout b;
	private File fileEstadisticas;
	private LinkedList<Lista> enlazada = new LinkedList<Lista>();
	private int contadorHacerDeshacer, contadorSacar;
	private Estadisiticas objetoEstadisticas = new Estadisiticas("");
	private int numeroApilacionesPosibles = 40;
	private boolean guardarBooleans;
	private LeerYEscribir objLeerYEscribir;

	private static final long serialVersionUID = 1L;

	public PanelJuegoEspañol() {

		conf();
		jugandoVisible();
		contadorSacar = 0;
		fileEstadisticas = null;
		grid = new GridLayout(6, 8);
		baraja = new BarajaEspanola();
		System.out.println(baraja.toString());
		copiaBaraja = copiaBaraja(baraja);
		setContadorHacerDeshacer(0);
		ncartassacadas = 0;
		it = baraja.iterable();
		cargaInicial();

	}

	public PanelJuegoEspañol(ArrayList<CartaEspanola> baraja, ArrayList<Stack<CartaEspanola>> colection) {
		this.baraja = new BarajaEspanola();
		this.baraja.baraja = baraja;
		this.colection = colection;
		ncartassacadas = (40 - baraja.size());
		 System.out.println(ncartassacadas);
		
		/************************/
		conf();
		this.setVisible(true);
		contadorSacar = ncartassacadas;
		fileEstadisticas = null;
		grid = new GridLayout(6, 8);
		copiaBaraja = copiaBaraja(this.baraja);
		setContadorHacerDeshacer(0);
		it = this.baraja.iterable();
		cargaInicial();
		 actualizarCartas();

	}

	public PanelJuegoEspañol(BarajaEspanola baraja) {

		conf();
		jugandoVisible();
		grid = new GridLayout(6, 8);
		this.baraja = baraja;
		ncartassacadas = 0;
		it = baraja.iterable();
		cargaInicial();
		/* Metodos propios */
		sacarTodasLasCartas();
		jugandoEntero(0);
		panel2.validate();
		panel2.repaint();
		solucion();
	}

	private BarajaEspanola copiaBaraja(BarajaEspanola baraja2) {
		BarajaEspanola aux = new BarajaEspanola();
		aux.baraja.clear();
		for (int i = 0; i < baraja2.baraja.size(); i++) {
			CartaEspanola auxC = new CartaEspanola(baraja2.baraja.get(i).toStringnumero(), baraja2.baraja.get(i).toStringPalo());
			aux.baraja.add(auxC);
		}
	//
	//	System.out.println("En funcion" + aux.toString());
		return aux;
	}

	private void sacarTodasLasCartas() {
		for (int i = 0; i < 40; i++) {
			sacarCarta();
		}
		saca.setVisible(false);
	}

	private void jugandoEntero(int actual) {

		if (actual < colection.size()) {
			if (compTresIzq(actual)) {
				tresIzq(actual);
				// System.out.println(colection.toString());
				jugandoEntero(1);

			} else if (compUnaIzq(actual)) {
				unaIzq(actual);
				// System.out.println(colection.toString());
				jugandoEntero(1);

			}

			jugandoEntero(actual + 1);
		}

	}

	/******************************
	 * Carga inicial de todos los botones que contendrán las cartas
	 * 
	 * @param source
	 * @return void
	 */

	private void cargaInicial() {

		for (int i = 0; i < 40; i++) {
			Stack<JButton> pila = new Stack<JButton>();
			JButton botonNuevo = new JButton();
			botonNuevo.setBackground(new Color(0, 180, 50));
			botonNuevo.setBorderPainted(false);
			botonNuevo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					accionBoton(e);
				}
			});
			pila.add(botonNuevo);
			botones.add(pila);
			panel2.add(botones.get(i).peek());
		}

		grid.setHgap(90);
		grid.setVgap(30);
		panel2.setLayout(grid);
		panel2.setBackground(new Color(0, 180, 50));

		// b = new BorderLayout(, 10);
		b = new GridLayout(7, 2);
		b.setHgap(30);
		panel1.setLayout(b);
		panel1.setBackground(new Color(0, 180, 50));

		saca = new JButton(CartaEspanola.getREVERSO());
		saca.setFont(new Font("Arial", Font.PLAIN, 20));
		saca.setBackground(new Color(0, 180, 50));
		saca.setBorderPainted(false);
		saca.setOpaque(true);
		saca.setVisible(true);
		// panel1.add(saca, BorderLayout.NORTH);
		panel1.add(saca);
		saca.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (ncartassacadas < 40) {
					sacarCarta();
					actualizarCartas();
					if (ncartassacadas == 40) {
						saca.setVisible(false);
					}
				} else {
					JOptionPane.showMessageDialog(panel2, "Todas las cartas sacadas");
				}
			}

		});

		atras = new JButton("Menú");
		atras.setFont(new Font("Arial", Font.PLAIN, 20));
		atras.setBackground(new Color(0, 150, 200));
		atras.setBorderPainted(false);
		atras.setOpaque(true);
		atras.setVisible(true);
		atras.setBounds(50, 300, 150, 150);
		// panel1.add(atras, BorderLayout.SOUTH);
		panel1.add(atras);
		atras.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				VentanaP v = new VentanaP();
				v.setVisible(true);
			}
		});

	}

	protected void accionBoton(EventObject e) {
		// System.out.println(ncartassacadas);
		if (ncartassacadas <= 40) {
			int pos = buscarBoton(e.getSource()); // Misma posicion del boton
			if (pos >= 0) {
				jugando(pos);
			} else {
				// System.out.println("HAY ALGO MUY MAL");
			}

		}
	}

	/******************************
	 * Busca la carta en la lista de pilas de botones
	 * 
	 * @param source
	 * @return posicion de la carta
	 */

	protected int buscarBoton(Object source) {

		/*
		 * Buscamos en el array de botones la posicion que ocupa el boton, y la
		 * devolvemos
		 */
		JButton b = new JButton();

		if (source instanceof JButton) {
			b = (JButton) source;
			for (int i = 0; i < botones.size(); i++) {

				if (botones.get(i).peek().equals(b)) {
					return i;
				}

			}

		}

		return -1;
	}

	/***************************
	 * Metemos todas las cartas que han sido sacadas, en un arraylist de cartas con
	 * la cuales podremos jugar
	 * 
	 * @param source
	 * @return void
	 *
	 ****************************/

	protected void iniciar(int i) {

		Stack<CartaEspanola> pila1 = new Stack<CartaEspanola>();
		
		CartaEspanola actual = baraja.baraja.get(i);
		// ASi se las que quedan en la baraja
		copiaBaraja.baraja.remove(0);
//		System.out.println(actual.toString());
//		System.out.println(copia.toString());
		pila1.push(actual);
		colection.add(pila1);
//		
//		System.out.println("Baraja 2 "+baraja.baraja.toString());
//		System.out.println("Copia 2"+copiaBaraja.baraja.toString());
	}

	private boolean compTresIzq(int actual) {
		if (colection.size() > 3 && actual >= 3) {
			return CartaEspanola.esPareja(colection.get(actual - 3).peek(), colection.get(actual).peek());

		}
		return false;
	}

	private void tresIzq(int actual) {
		// System.out.println("Entra a ver si es pareja -3 ->" + colection.size() + "/"
		// + actual);
		// Muevo tanto en la colection, como en
		colection.get(actual - 3).push(colection.get(actual).pop());
		// botones.get(actual - 3).setIcon(buscarImagen(c)); // Meto la imagen en el
		// boton nuevo
		botones.get(actual - 3).push(botones.get(actual).pop());
		contadorHacerDeshacer++;
		--numeroApilacionesPosibles;
		siVacio(actual);
		actualizarCartas();

	}

	private void meterEnLista() {

		Lista nueva = new Lista();
		ArrayList<Stack<JButton>> bot = new ArrayList<Stack<JButton>>();
		ArrayList<Stack<CartaEspanola>> col = new ArrayList<Stack<CartaEspanola>>();
		Stack<JButton> m = new Stack<JButton>();
		Stack<CartaEspanola> n = new Stack<CartaEspanola>();

		for (int i = 0; i < colection.size(); i++) {
			m = botones.get(i);
			bot.add(m);
			n = colection.get(i);
			col.add(n);
		}
		nueva.setBotones(bot);
		nueva.setColection(col);
		enlazada.add(nueva);

		// System.out.println(nueva.toString());
	}

	private void actualizarCartas() {

		panel2.removeAll();
		for (int i = 0; i < baraja.numeroCartas; i++) {
			if (i < colection.size()) {
				panel2.add(botones.get(i).peek());
			} else {

				JButton hueco = new JButton();
				hueco.setBackground(new Color(0, 180, 50));
				hueco.setBorderPainted(false);
				panel2.add(hueco);
			}

		}

		panel2.validate();
		panel2.repaint();
		System.out.println("En acutualz8r");
		System.out.println(colection.toString());
		// System.out.println(" SIZE" + botones.size());

	}

	private void siVacio(int actual) throws EmptyStackException {
		/* Comprueba si la pila del elemento que acabamos de sacar esta vacia */

		if (colection.get(actual).isEmpty() && botones.get(actual).isEmpty()) {
			colection.remove(actual);
			botones.remove(actual);
			if (ncartassacadas < 40)
				contadorSacar--;
		}

		// System.out.println(colection.toString());

	}

	protected boolean jugando(int actual) {

		boolean si = esMovimiento(actual);
		// System.out.println(si);
		if (si) {
			objetoEstadisticas.setSaltosOk(objetoEstadisticas.getSaltosOk() + 1);

			if (compTresIzq(actual)) {
				// JOptionPane.showMessageDialog(panel2, "Movimiento -3 válido");
				tresIzq(actual);
			} else if (compUnaIzq(actual)) {
				// JOptionPane.showMessageDialog(panel2, "Movimiento -1 válido");
				unaIzq(actual);
			}
		} else {
			// JOptionPane.showMessageDialog(panel2, "Movimiento no válido");
			objetoEstadisticas.setSaltosFail(objetoEstadisticas.getSaltosFail() + 1);
		}
		comprobar();
		return si;
	}

	private boolean esMovimiento(int actual) {
		if (actual < colection.size()) {
			// System.out.println("Vamos a comparar" + actual);
			int i = 0;
			while (i <= actual) {
				// System.out.println("Dentro del while");
				if (compTresIzq(i) || compUnaIzq(i)) {
					// System.out.println("Carta movible" + i);
					if (i == actual)
						return true;
					else
						return false;
				}
				i++;

			}

		}
		return false;
	}

	private boolean comprobar() {
		boolean bandera = false;
		if (colection.size() == 1 && ncartassacadas == 40) {
			JOptionPane.showMessageDialog(panel2, "Has ganado!!!");
			solucion();
		} else if (ncartassacadas == 40 && colection.size() > 1) {
			for (int i = 0; i < colection.size(); i++) {
				if (compTresIzq(i) || compUnaIzq(i)) {
					return bandera;
				}
			}

		} else {
			bandera = false;
		}
		return bandera;

	}

	private boolean compUnaIzq(int actual) {

		if (colection.size() >= 2 && actual >= 1) { // Nos pasan un valor que ya es el de 3
			// posiciones atras

			return CartaEspanola.esPareja(colection.get(actual - 1).peek(), colection.get(actual).peek()); // fallo
		}
		return false;
	}

	private void unaIzq(int actual) {

		// System.out.println("Entra a ver si es pareja -1 -> " + colection.size() + "/"
		// + actual);
		colection.get(actual - 1).push(colection.get(actual).pop()); // Apilo
		botones.get(actual - 1).push(botones.get(actual).pop());
		numeroApilacionesPosibles = numeroApilacionesPosibles - 1;
		contadorHacerDeshacer++;
		siVacio(actual);
		actualizarCartas();

	}

	/************
	 * 
	 * @param
	 * @return
	 */

	protected void sacarCarta() {

		if (it.hasNext()) {
			CartaEspanola tmp = it.next();
			System.out.println("Original"+tmp.toString());
			tmp.setImagenNueva(buscarImagen(tmp));
			// La imagen ya la tiene la carta, así que se la meto a la primera posicion del
			// boton
			botones.get(posicionUltimoBoton()).peek().setIcon(tmp.getImagen());
			panel2.repaint();
			++contadorSacar;
			iniciar(ncartassacadas);
		++ncartassacadas;
		}

	}

	/**********
	 * Busco el último boton que se ha cambiado para meter la nueva carta
	 * 
	 * @return
	 */

	private int posicionUltimoBoton() {
		return contadorSacar;
	}

	private ImageIcon buscarImagen(CartaEspanola tmp) {

		String name = tmp.toString();
		ImageIcon nueva = new ImageIcon(getClass().getResource("española/" + name + ".jpg"));
		return nueva; // Cada carta va a tener su imagen asociada

	}

	private void menu() {

		menu = new JMenuBar();
		archivo = new JMenu("Archivo");

		nuevo = new JMenuItem("Nuevo");
		nuevo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				new PanelJuegoEspañol();
				dispose();
			}
		});
		cargar = new JMenuItem("Cargar");
		cargar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				wantCharge();
			}
		});
		guardar = new JMenuItem("Salvar");
		guardar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (!guardarBooleans) {
						guardarComo();
					} else {

						guardar();
					}

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		guardarComo = new JMenuItem("Salvar como");
		guardarComo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					guardarComo();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		salir = new JMenuItem("Salir");
		salir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				System.exit(0);
			}
		});

		archivo.add(nuevo);
		archivo.add(cargar);
		archivo.add(guardar);
		archivo.add(guardarComo);
		archivo.add(salir);
		archivo.addSeparator();
		menu.add(archivo);

		editar = new JMenu("Editar");
		deshacer = new JMenuItem("Deshacer");
		deshacer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				accionesDeshacer();

			}
		});
		hacer = new JMenuItem("Hacer");
		hacer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				accionHacer();
			}
		});
		resolver = new JMenuItem("Resolver");
		resolver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				new PanelJuegoEspañol(baraja);
			}

		});
		editar.add(deshacer);
		editar.add(hacer);
		editar.add(resolver);
		editar.addSeparator();
		menu.add(editar);

		historial = new JMenu("Historial");
		estadisticas = new JMenuItem("Estadísticas");

		estadisticas.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (objetoEstadisticas.getArchivo().equals("")) {
					try {
						creoEstadisticas();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					objetoEstadisticas.actualizar();
					JOptionPane.showMessageDialog(null, objetoEstadisticas.toString(), "\"Estadisticas\"",
							JOptionPane.INFORMATION_MESSAGE);
				}

			}
		});
		ficheroestadisticas = new JMenuItem("Fichero estadísticas");
		ficheroestadisticas.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					creoEstadisticas();
					escribir(fileEstadisticas);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
		historial.add(estadisticas);
		historial.add(ficheroestadisticas);
		historial.addSeparator();
		menu.add(historial);

		ayuda = new JMenu("Ayuda");

		JMenuItem rulesText = new JMenuItem("Explicación!");
		ayuda.add(rulesText);
		rulesText.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(p,
						"- Juego del solitario de saltos \n" + "-" + "Tras sacar todas las cartas, mueve pares  \n"
								+ "- Ya sean de palos, o de numeros\n" + "-Una posicion o tres a la izquierda \n"
								+ "-Buena suerte\n" + "-",
						"Rules", JOptionPane.PLAIN_MESSAGE);
			}
		});
		menu.add(ayuda);

		this.setJMenuBar(menu);

		// Añado la barra al frame
		menu.setVisible(true);
	}

	protected void cargarSol() {
		objLeerYEscribir = new LeerYEscribir();
		objLeerYEscribir.abrir();
		objLeerYEscribir.leer();

		if (!objLeerYEscribir.getArchivo().equals("")) {
			if (objLeerYEscribir.getLeidas().get(0).equals("Solitario saltos")) {
				if (objLeerYEscribir.actualizar()) {
					new PanelJuegoEspañol(objLeerYEscribir.getBaraja(), objLeerYEscribir.getColection());
					this.dispose();
				} else {
					JOptionPane.showMessageDialog(rootPane, "Error al cargar el archivo", "Actualizar valores",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			System.out.println("Vacio");
		}
	
	}

	/***************
	 * Creo el fichero de estadisticas, si este no lo tiene. En caso contrario, se
	 * actualiza
	 * 
	 * @throws IOException
	 */

	public void creoEstadisticas() throws IOException {

		if (this.objetoEstadisticas.getArchivo().equals("")) {
			JFileChooser chooser = new JFileChooser();
			int c = chooser.showOpenDialog(null);
			if (c == JFileChooser.APPROVE_OPTION) {
				fileEstadisticas = chooser.getSelectedFile();
				String direccion = fileEstadisticas.getAbsolutePath().concat(".txt");
				objetoEstadisticas.setArchivo(direccion);
				// System.out.println(objetoEstadisticas.getArchivo());
			} else {

			}
			if (objetoEstadisticas.getArchivo().equals("")) {
				JOptionPane.showMessageDialog(rootPane, "No has abierto ningun fichero");
			} else {
				escribir(fileEstadisticas);
			}

		} else {
			int i = JOptionPane.showConfirmDialog(null, "Ya existe un archivo con este nombre, ¿Desea sobreescribirlo?",
					"guardar estadisticas", JOptionPane.YES_NO_OPTION);
			if (i == 0) {
				escribir(fileEstadisticas);
			} else {
				objetoEstadisticas.setArchivo("");
				creoEstadisticas();
			}
		}

	}

	public void escribir(File fileEstadisticas) throws IOException {
		System.out.println("En escribir" + fileEstadisticas.getAbsolutePath());
		FileWriter f = new FileWriter(fileEstadisticas.getAbsolutePath());
		PrintWriter p = new PrintWriter(f);
		fileEstadisticas.delete();

		p.println("Solitario Saltos");
		p.println(String.valueOf(objetoEstadisticas.getSaltosOk()));
		p.println(String.valueOf(objetoEstadisticas.getSaltosFail()));
		p.println("Solitario Clasico");
		p.println(String.valueOf(objetoEstadisticas.getClasicoOk()));
		p.println(String.valueOf(objetoEstadisticas.getClasicoFail()));

		p.close();
		JOptionPane.showMessageDialog(null, "Estadisticas guardadas!", "guardar estadisticas",
				JOptionPane.INFORMATION_MESSAGE);

	}

	protected void accionesDeshacer() {
		// System.out.println("En acciones deshacer");
		if (ncartassacadas < 40) {
			JOptionPane.showMessageDialog(rootPane, "No se pueden deshacer cartas");
		} else if (getContadorHacerDeshacer() > 0) {
			// actualizarCartas();
		} else {
			JOptionPane.showMessageDialog(rootPane, "Nada que deshacer");
		}
	}

	protected void accionHacer() {
		// System.out.println("En accion hacer");

		if (ncartassacadas == 0) {
			sacarCarta();
			actualizarCartas();
			return;
		}

		if (ncartassacadas < 40) {

			int i = 0;
			boolean bandera = false;
			while (i < colection.size() && !bandera) {
				bandera = jugando(i);
				i++;
			}
			if (!bandera) {
				sacarCarta();
				actualizarCartas();
			}
		} else {
			int i = 0;
			boolean bandera = false;
			while (i < colection.size() && !bandera) {
				bandera = jugando(i);
				i++;
			}
		}
	}

	protected void solucion() {

		JOptionPane.showMessageDialog(p, colection.toString());
		int op = JOptionPane.showConfirmDialog(rootPane, "Quiere jugar de nuevo?");
		if (JOptionPane.OK_OPTION == op) {
			JOptionPane.showMessageDialog(rootPane, "Pefecto!!!");
			new PanelJuegoEspañol();
			dispose();
		} else {
			JOptionPane.showMessageDialog(rootPane, "Perfecto! Nos vemos pronto");
			System.exit(0);
		}
	}

	public void guardarComo() throws IOException {

		JFileChooser guardar = new JFileChooser();

		guardar.showSaveDialog(null);
		guardar.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		File f = guardar.getSelectedFile();
		objetoEstadisticas.setArchivo(f.getAbsolutePath() + ".txt");

		if (f.exists()) {
			if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(null, "El fichero existe,deseas reemplazarlo?",
					"Titulo", JOptionPane.YES_NO_OPTION)) {
				guardar();
			} else {
				objetoEstadisticas.setArchivo(" ");
				guardarComo();
			}
		} else {
			guardar();
		}

	}

	public void guardar() throws IOException {
		// TODO Auto-generated method stub

		if (objetoEstadisticas.getArchivo().equals("")) {
			guardarComo();
		} else {
			FileWriter fw = new FileWriter(objetoEstadisticas.getArchivo());
			PrintWriter pw = new PrintWriter(fw);
			pw.println("Solitario saltos");

			for (int i = 0; i < copiaBaraja.baraja.size(); i++) {

				CartaEspanola aux = new CartaEspanola(copiaBaraja.baraja.get(i).toStringnumero(),
						copiaBaraja.baraja.get(i).toStringPalo());
				pw.print(aux.toString() + " ");
			}
			//System.out.println("Copia baraja" + copiaBaraja.baraja.toString());

			pw.println();
			for (int i = 0; i < colection.size(); i++) {
				Stack<CartaEspanola> pila = colection.get(i);
				 System.out.println(pila.toString());
				for (int m = 0; m < colection.get(i).size(); m++) {

					CartaEspanola aux = new CartaEspanola(pila.get(m).toStringnumero(), pila.get(m).toStringPalo());

					pw.print(aux.toString() + " ");
				}

				pw.println();
			}

			pw.close();
			JOptionPane.showMessageDialog(null, "Partida guardada correctamente", "Salvar", JOptionPane.PLAIN_MESSAGE);
		}

	}

	private void wantCharge() {
		int sel = JOptionPane.showOptionDialog(rootPane, "Quieres cargar una partida anterior?", "Solitario saltos",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

		if (sel == JOptionPane.YES_OPTION) {
			cargarSol();
		} else {
			JOptionPane.showMessageDialog(rootPane, "Seguimos jugando", "Cargar", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/*****************
	 * Visibilizar el juego
	 * 
	 * @param
	 * @return void
	 */

	private void jugandoVisible() {
		this.setVisible(true);
	}

	private void conf() {
		this.setSize(1350, 750);
		this.setResizable(false);
		// this.setLocationRelativeTo(null); // Coloca la ventana en el centro
		this.setVisible(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.guardarBooleans = false;
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent arg0) {
				int op = JOptionPane.showConfirmDialog(rootPane, "Seguro que quieres salir?");
				if (JOptionPane.OK_OPTION == op) {
					if (!guardarBooleans) {

						int op1 = JOptionPane.showConfirmDialog(rootPane, "Quieres guardar la partida?");

						if (JOptionPane.OK_OPTION == op1) {
							try {
								guardarComo();
								JOptionPane.showMessageDialog(rootPane, "Partida guardada", null,
										JOptionPane.INFORMATION_MESSAGE);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (NullPointerException e) {
								JOptionPane.showMessageDialog(rootPane, "La partida no se ha guardado", null,
										JOptionPane.INFORMATION_MESSAGE);
							}

						}

					}
					JOptionPane.showMessageDialog(rootPane, "Hasta luego!!!", "Fin juego",
							JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
				} else {
					JOptionPane.showMessageDialog(rootPane, "Perfecto! Seguimos jugando", "Continuamos",
							JOptionPane.INFORMATION_MESSAGE);

				}

			}

		});

		p = new JPanel();
		p.setVisible(true);
		p.setLayout(new BorderLayout());
		p.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		this.setContentPane(p);

		panel1 = new JPanel();
		panel2 = new JPanel();

		p.add(panel1, BorderLayout.WEST);
		p.add(panel2, BorderLayout.CENTER);

		menu();

		panel2.setVisible(true);
		panel1.setVisible(true);

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getContadorHacerDeshacer() {
		return contadorHacerDeshacer;
	}

	public void setContadorHacerDeshacer(int contadorHacerDeshacer) {
		this.contadorHacerDeshacer = contadorHacerDeshacer;
	}

}