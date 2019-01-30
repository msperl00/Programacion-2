import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.TextField;
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
import java.nio.channels.spi.AbstractInterruptibleChannel;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.EventObject;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;
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
import javax.swing.JTextArea;

public class PanelJuegoEspa�ol extends JFrame {

	private JPanel p, panel1, panel2;

	private JMenuBar menu;
	private JMenu archivo, editar, historial, ayuda;
	public JMenuItem nuevo, cargar, salvarcomo, salir, salvar, hacer, deshacer, resolver, ficheroestadisticas,
			estadisticas;
	private String nombre = new String("Solitario espa�ol");
	private TextArea ta;
	private GridLayout grid;
	private ArrayList<Stack<JButton>> botones = new ArrayList<Stack<JButton>>();
	private Baraja baraja;
	private JButton saca, atras;
	private Iterator<Carta> it;
	private ArrayList<Stack<Carta>> colection = new ArrayList<Stack<Carta>>();
	private static int ncartassacadas;
	private BorderLayout b;
	private File fileEstadisticas;
	private LinkedList<Lista> enlazada = new LinkedList<Lista>();
	private int contadorHacerDeshacer, contadorSacar;
	private Estadisiticas objetoEstadisticas = new Estadisiticas("");
	private Object nombreFichero;

	private static final long serialVersionUID = 1L;

	public PanelJuegoEspa�ol() {

		conf();
		jugandoVisible();
		contadorSacar = 0;
		fileEstadisticas = null;
		grid = new GridLayout(6, 8);
		baraja = new Baraja();
		setContadorHacerDeshacer(0);
		ncartassacadas = 0;
		it = baraja.iterable();
		cargaInicial();

	}

	public PanelJuegoEspa�ol(Baraja baraja) {

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

	private void sacarTodasLasCartas() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 40; i++) {
			sacarCarta();
		}
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
	 * Carga inicial de todos los botones que contendr�n las cartas
	 * 
	 * @param source
	 * @return void
	 */

	private void cargaInicial() {

		for (int i = 0; i < 40; i++) {
			Stack<JButton> pila = new Stack<JButton>();
			JButton botonNuevo = new JButton(Carta.getREVERSO());
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

		grid.setHgap(60);
		grid.setVgap(30);
		panel2.setLayout(grid);
		panel2.setBackground(new Color(0, 180, 50));

		b = new BorderLayout(10, 10);

		panel1.setLayout(b);
		panel1.setBackground(new Color(0, 180, 50));

		saca = new JButton("Sacar carta");
		saca.setFont(new Font("Arial", Font.PLAIN, 20));
		saca.setBackground(new Color(0, 180, 50));
		saca.setBorderPainted(false);
		saca.setOpaque(true);
		saca.setVisible(true);
		saca.setBounds(50, 300, 150, 150);
		panel1.add(saca, BorderLayout.NORTH);

		saca.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (ncartassacadas < 40) {
					sacarCarta();
					actualizarCartas();
				} else {
					JOptionPane.showMessageDialog(panel2, "Todas las cartas sacadas");
				}
			}

		});

		atras = new JButton("Men�");
		atras.setFont(new Font("Arial", Font.PLAIN, 20));
		atras.setBackground(new Color(0, 100, 200));
		atras.setBorderPainted(false);
		atras.setOpaque(true);
		atras.setVisible(true);
		atras.setBounds(50, 300, 150, 150);
		panel1.add(atras, BorderLayout.CENTER);

		atras.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				VentanaP v = new VentanaP();
				v.setVisible(true);
			}
		});

	}

	protected void accionBoton(EventObject e) {
		System.out.println(ncartassacadas);
		if (ncartassacadas <= 40) {
			int pos = buscarBoton(e.getSource()); // Misma posicion del boton
			if (pos >= 0) {
				jugando(pos);
			} else {
				System.out.println("HAY ALGO MUY MAL");
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

		Stack<Carta> pila1 = new Stack<Carta>();
		Carta actual = baraja.baraja.get(i);
		pila1.push(actual);
		colection.add(pila1);

		// System.out.println(baraja.baraja.toString());

	}

	private boolean compTresIzq(int actual) {
		if (colection.size() > 3 && actual >= 3) {
			return Carta.esPareja(colection.get(actual - 3).peek(), colection.get(actual).peek());

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
		System.out.println(" SIZE" + botones.size());
		siVacio(actual);
		actualizarCartas();

	}

	private void meterEnLista() {

		Lista nueva = new Lista();
		ArrayList<Stack<JButton>> bot = new ArrayList<Stack<JButton>>();
		ArrayList<Stack<Carta>> col = new ArrayList<Stack<Carta>>();
		Stack<JButton> m = new Stack<JButton>();
		Stack<Carta> n = new Stack<Carta>();

		for (int i = 0; i < colection.size(); i++) {
			m = botones.get(i);
			bot.add(m);
			n = colection.get(i);
			col.add(n);
		}
		nueva.setBotones(bot);
		nueva.setColection(col);
		enlazada.add(nueva);

		System.out.println(nueva.toString());
	}

	private void sacarEnLista() {

	}

	private void actualizarCartas() {

		panel2.removeAll();
		for (int i = 0; i < baraja.numeroCartas; i++) {
			if (i < colection.size()) {
				panel2.add(botones.get(i).peek());
			} else {
				JButton hueco = new JButton(Carta.getREVERSO());
				hueco.setBackground(new Color(0, 180, 50));
				hueco.setBorderPainted(false);
				panel2.add(hueco);
			}
		}

		panel2.validate();
		panel2.repaint();

		System.out.println(colection.toString());
		System.out.println(" SIZE" + botones.size());

	}

	private void siVacio(int actual) throws EmptyStackException {
		/* Comprueba si la pila del elemento que acabamos de sacar esta vacia */

		if (colection.get(actual).isEmpty() && botones.get(actual).isEmpty()) {
			colection.remove(actual);
			botones.remove(actual);
			if (ncartassacadas < 40)
				contadorSacar--;
		}

		System.out.println(colection.toString());

	}

	protected boolean jugando(int actual) {

		boolean si = esMovimiento(actual);
		System.out.println(si);
		if (si) {
			objetoEstadisticas.setSaltosOk(objetoEstadisticas.getSaltosOk() + 1);

			if (compTresIzq(actual)) {
				// JOptionPane.showMessageDialog(panel2, "Movimiento -3 v�lido");
				tresIzq(actual);
			} else if (compUnaIzq(actual)) {
				// JOptionPane.showMessageDialog(panel2, "Movimiento -1 v�lido");
				unaIzq(actual);
			}
		} else {
			// JOptionPane.showMessageDialog(panel2, "Movimiento no v�lido");
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

			return Carta.esPareja(colection.get(actual - 1).peek(), colection.get(actual).peek()); // fallo
		}
		return false;
	}

	private void unaIzq(int actual) {

		// System.out.println("Entra a ver si es pareja -1 -> " + colection.size() + "/"
		// + actual);
		colection.get(actual - 1).push(colection.get(actual).pop()); // Apilo
		botones.get(actual - 1).push(botones.get(actual).pop());
		siVacio(actual);
		actualizarCartas();
		contadorHacerDeshacer++;

	}

	/************
	 * 
	 * @param
	 * @return
	 */
	/*
	 * ENCONTRADO PEL PROBLEMA DE LA CARTA FANTASMA
	 */
	protected void sacarCarta() {

		if (it.hasNext()) {
			Carta tmp = it.next();
			tmp.setImagenNueva(buscarImagen(tmp));
			// La imagen ya la tiene la carta, as� que se la meto a la primera posicion del
			// boton
			botones.get(posicionUltimoBoton()).peek().setIcon(tmp.getImagen());
			// Enocntroado problema
			panel2.repaint();
			++contadorSacar;
			iniciar(ncartassacadas++);

		}

	}

	/**********
	 * Busco el �ltimo boton que se ha cambiado para meter la nueva carta
	 * 
	 * @return
	 */

	private int posicionUltimoBoton() {
		return contadorSacar;
	}

	private ImageIcon buscarImagen(Carta tmp) {

		String name = tmp.toString();
		ImageIcon nueva = new ImageIcon(getClass().getResource("espa�ola/" + name + ".jpg"));
		return nueva; // Cada carta va a tener su imagen asociada

	}

	private void menu() {

		menu = new JMenuBar();
		archivo = new JMenu("Archivo");
		archivo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

			}
		});

		nuevo = new JMenuItem("Nuevo");
		nuevo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				new PanelJuegoEspa�ol();
				dispose();
			}
		});
		cargar = new JMenuItem("Cargar");
		cargar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String texto = abrir();
			}
		});
		salvar = new JMenuItem("Salvar");
		salvar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		salvarcomo = new JMenuItem("Salvar como");
		salvarcomo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				salvarcomo();
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
		archivo.add(salvar);
		archivo.add(salvarcomo);
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

				new PanelJuegoEspa�ol(baraja);
			}

		});
		editar.add(deshacer);
		editar.add(hacer);
		editar.add(resolver);
		editar.addSeparator();
		menu.add(editar);

		historial = new JMenu("Historial");
		historial.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				abrir();
			}
		});
		estadisticas = new JMenuItem("Estad�sticas");

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
		ficheroestadisticas = new JMenuItem("Fichero estad�sticas");
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

		JMenuItem rulesText = new JMenuItem("Explicaci�n!");
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

		// A�ado la barra al frame
		menu.setVisible(true);
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
				System.out.println(objetoEstadisticas.getArchivo());
			} else {

			}
			if (objetoEstadisticas.getArchivo().equals("")) {
				JOptionPane.showMessageDialog(rootPane, "No has abierto ningun fichero");
			}else {
				escribir(fileEstadisticas);
			}
			
		} else {
			int i = JOptionPane.showConfirmDialog(null, "Ya existe un archivo con este nombre, �Desea sobreescribirlo?",
					"Salvar estadisticas", JOptionPane.YES_NO_OPTION);
			if (i == 0) {
				escribir(fileEstadisticas);
			} else {
				objetoEstadisticas.setArchivo("");
				creoEstadisticas();
			}
		}

	}


	public void escribir(File fileEstadisticas) throws IOException {
		System.out.println("En escribir"+fileEstadisticas.getAbsolutePath());
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
		JOptionPane.showMessageDialog(null, "Estadisticas guardadas!", "Salvar estadisticas",
				JOptionPane.INFORMATION_MESSAGE);

	}

	protected void accionesDeshacer() {
		// System.out.println("En acciones deshacer");
		if (ncartassacadas < 40) {
			JOptionPane.showMessageDialog(rootPane, "No se pueden deshacer cartas");
		} else if (getContadorHacerDeshacer() > 0) {
			sacarEnLista();
			// actualizarCartas();
		} else {
			JOptionPane.showMessageDialog(rootPane, "Nada que deshacer");
		}
	}

	protected void accionHacer() {
		// System.out.println("En accion hacer");

		if (ncartassacadas < 40) {
			sacarCarta();
			actualizarCartas();
			// System.out.println(ncartassacadas);
		} else if (getContadorHacerDeshacer() >= 0) {
			// System.out.println("Haciendo");
			int i = 0;
			boolean bandera = false;
			while (!bandera) {
				bandera = jugando(i);
				System.out.println(bandera);
				i++;
			}
		}

	}

	protected void solucion() {

		JOptionPane.showMessageDialog(p, colection.toString());
		int op = JOptionPane.showConfirmDialog(rootPane, "Quiere jugar de nuevo?");
		if (JOptionPane.OK_OPTION == op) {
			JOptionPane.showMessageDialog(rootPane, "Pefecto!!!");
			new PanelJuegoEspa�ol();
			dispose();
		} else {
			JOptionPane.showMessageDialog(rootPane, "Perfecto! Nos vemos pronto");
			System.exit(0);
		}
	}

	protected String abrir() {
		String aux = "";
		String texto = "";
		try {
			/** llamamos el metodo que permite cargar la ventana */
			JFileChooser file = new JFileChooser();
			file.showOpenDialog(this);
			/** abrimos el archivo seleccionado */
			File abre = file.getSelectedFile();

			/**
			 * recorremos el archivo, lo leemos para plasmarlo en el area de texto
			 */
			if (abre != null) {
				FileReader archivos = new FileReader(abre);
				BufferedReader lee = new BufferedReader(archivos);
				while ((aux = lee.readLine()) != null) {
					texto += aux + "\n";
				}
				lee.close();
			}
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, ex + "" + "\nNo se ha encontrado el archivo", "ADVERTENCIA!!!",
					JOptionPane.WARNING_MESSAGE);
		}
		return texto;// El texto se almacena en el JTextArea
	}

	private void salvarcomo() {
		String entradaSalida = null;
		JFileChooser guardar = new JFileChooser();
		guardar.showSaveDialog(this);
		guardar.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		if (guardar != null) {
			try {

				FileWriter save = new FileWriter(guardar + ".txt");
				save.write(ta.getText());
				save.close();
				nombre = guardar.getSelectedFile().getName();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Su archivo no se ha guardado", "Advertencia",
						JOptionPane.WARNING_MESSAGE);
			}
		}

		if (new File(entradaSalida).exists()) {
			if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(this, "El fichero existe,deseas reemplazarlo?",
					"Titulo", JOptionPane.YES_NO_OPTION)) {
				// Has aceptado...has lo kieras......
			}
		}

	}

	private void salvar() {
		if (nombreFichero != null) {

		} else {
			salvarcomo();
		}
	}

	private void jugandoVisible() {
		this.setVisible(true);

	}

	private void conf() {
		this.setSize(1200, 750);
		this.setResizable(false);
		// this.setLocationRelativeTo(null); // Coloca la ventana en el centro
		this.setVisible(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent arg0) {
				int op = JOptionPane.showConfirmDialog(rootPane, "Seguro que quieres salir?");
				if (JOptionPane.OK_OPTION == op) {
					JOptionPane.showMessageDialog(rootPane, "Hasta luego!!!");
					System.exit(0);
				} else {
					JOptionPane.showMessageDialog(rootPane, "Perfecto! Seguimos jugando");

				}

			}

		});

		p = new JPanel();
		p.setVisible(true);
		p.setLayout(new BorderLayout());
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