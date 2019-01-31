import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PanelJuegoFrances extends JFrame implements ActionListener {

	private String nombre = new String("SOLITARIO FRANCÉS");
	/**
	 * 
	 */
	private JPanel p, pArriba, pCentro, pAbajo;
	private JMenuBar menu;
	private JMenu archivo, editar, historial, ayuda;
	public JMenuItem nuevo, cargar, guardarComo, salir, guardar, hacer, deshacer, resolver, ficheroestadisticas,
			estadisticas;
	private ArrayList<ArrayList<JButton>> botones = new ArrayList<ArrayList<JButton>>();
	private ArrayList<Stack<CartaFrancesa>> colection = new ArrayList<Stack<CartaFrancesa>>();
	private ArrayList<CartaFrancesa> visible;
	private ArrayList<CartaFrancesa> tapada;
	private final BarajaFrancesa baraja = new BarajaFrancesa();
	private File fileEstadisticas;
	private LinkedList<Lista> enlazada = new LinkedList<Lista>();
	private JLabel espacio = new JLabel("                                       ");
	private JButton carta = new JButton();
	private JButton oculta = new JButton();
	private JButton Clubs = new JButton();
	private JButton Hearts = new JButton();
	private JButton Diamonds = new JButton();
	private JButton Spades = new JButton();
	protected boolean guardarBooleans = false;
	Estadisiticas obEstadisiticas;
	public JuegoFrances juego;
	private JMenu atras;
	private JMenuItem menu1;

	public PanelJuegoFrances() {

		configuracion();
		principalP();
		arribaP();
		juego();
		configurarTapada();
		configurarVisible();
		configurarClubs();
		configurarHearts();
		configurarDiamons();
		configurarSpades();
		centroP();
		abajoP();
		p.setVisible(true);

	}

	public PanelJuegoFrances(BarajaFrancesa baraja2) {

	}

	private void juego() {

		this.juego = new JuegoFrances();
		tapada = this.juego.getTapada();
		System.out.println(tapada.toString());
		visible = this.juego.getVisible();
		System.out.println(visible.toString());

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		for (int i = 0; i < botones.size(); i++) {
			for (int j = 0; j < botones.get(i).size(); j++) {
				if (e.getSource() == botones.get(i).get(j)) {
					if (!juego.getColection().get(i).get(j).isOculta()) {
						comprobarPilas(juego.getColection().get(i).get(j));

					}
				}
			}
		}
	}

	private void comprobarPilas(CartaFrancesa carta) {

	}

	private void configurarSpades() {
		// TODO Auto-generated method stub
		Stack<CartaFrancesa> espadas = this.juego.getSpades();
		if (espadas.size() >= 1) {
			this.Spades.setIcon(espadas.get(espadas.size() - 1).getImagen());

		} else {
			this.Spades.setIcon((new ImageIcon(getClass().getResource("francesa/cartavacia.jpg"))));
		}
		this.Spades.setBackground(new Color(0, 180, 50));
		Spades.setVisible(true);
	}

	private void configurarDiamons() {
		// TODO Auto-generated method stub
		Stack<CartaFrancesa> diamantes = this.juego.getDiamons();
		if (diamantes.size() >= 1) {

			this.Diamonds.setIcon(diamantes.get(diamantes.size() - 1).getImagen());
		} else {
			this.Diamonds.setIcon((new ImageIcon(getClass().getResource("francesa/cartavacia.jpg"))));

		}
		this.Diamonds.setBackground(new Color(0, 180, 50));
		Diamonds.setVisible(true);
	}

	private void configurarHearts() {
		// TODO Auto-generated method stub
		Stack<CartaFrancesa> corazones = this.juego.getHearts();
		if (corazones.size() >= 1) {

			this.Hearts.setIcon(corazones.get(corazones.size() - 1).getImagen());
		} else {
			this.Hearts.setIcon((new ImageIcon(getClass().getResource("francesa/cartavacia.jpg"))));

		}
		this.Hearts.setBackground(new Color(0, 180, 50));
		Hearts.setVisible(true);
	}

	private void configurarClubs() {
		// TODO Auto-generated method stub
		Stack<CartaFrancesa> picas = this.juego.getClubs();
		if (picas.size() >= 1) {

			this.Clubs.setIcon(picas.get(picas.size() - 1).getImagen());
		} else {
			this.Clubs.setIcon((new ImageIcon(getClass().getResource("francesa/cartavacia.jpg"))));

		}
		this.Clubs.setBackground(new Color(0, 180, 50));
		Clubs.setVisible(true);
	}

	private void configurarVisible() {
		// TODO Auto-generated method stub
		if (visible.size() >= 1) {
			CartaFrancesa aux = visible.get(visible.size() - 1);
			carta.setIcon(aux.getImagen());

		} else {
			this.carta.setIcon((new ImageIcon(getClass().getResource("francesa/cartavacia.jpg"))));
		}
		this.carta.setBackground(new Color(0, 180, 50));
		carta.setVisible(true);
	}

	private void configurarTapada() {
		// TODO Auto-generated method stub
		if (tapada.size() >= 1) {
			this.oculta.setIcon(CartaFrancesa.getREVERSO());

		} else {
			this.Spades.setIcon((new ImageIcon(getClass().getResource("francesa/cartavacia.jpg"))));
		}
		this.oculta.setBackground(new Color(0, 180, 50));
		oculta.setVisible(true);
	}

	private void configuracion() {
		// TODO Auto-generated method stub
		this.setTitle("Solitario Clásico");
		this.setSize(1350, 750);
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.menu();
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent arg0) {
				int op = JOptionPane.showConfirmDialog(rootPane, "Seguro que quieres salir?");
				if (JOptionPane.OK_OPTION == op) {
					if (!guardarBooleans) {

						int op1 = JOptionPane.showConfirmDialog(rootPane, "Quieres guardar la partida?");

						if (JOptionPane.OK_OPTION == op1) {
							try {
								// guardarComo();
								JOptionPane.showMessageDialog(rootPane, "Partida guardada", null,
										JOptionPane.INFORMATION_MESSAGE);
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
		this.setVisible(true);
		p = new JPanel();
		p.setLayout(new BorderLayout());
		pAbajo = new JPanel();
		pAbajo.setLayout(new FlowLayout());
		pArriba = new JPanel();
		pArriba.setLayout(new FlowLayout());
		pCentro = new JPanel();
		pCentro.setLayout(new GridLayout(1, 7));

	}

	private void principalP() {
		// TODO Auto-generated method stub

		p.add(pArriba, BorderLayout.NORTH);
		p.add(pAbajo, BorderLayout.SOUTH);
		p.add(pCentro, BorderLayout.CENTER);
		p.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		p.setBackground(Color.BLUE);
		this.add(p);

	}

	private void arribaP() {

		pArriba.removeAll();
		pArriba.setBackground(new Color(80, 200, 50));
		pArriba.add(carta);
		pArriba.add(oculta);
		pArriba.add(espacio);
		pArriba.add(Clubs);
		pArriba.add(Hearts);
		pArriba.add(Diamonds);
		pArriba.add(Spades);
		pArriba.setVisible(true);

	}

	private void centroP() {

		pCentro.setBackground(new Color(0, 180, 50));
		movimientosCartas();
		pCentro.setVisible(true);
	}

	/*****************
	 * En este método lo que hacemos es crear un panel por pila de cartas, las
	 * cuales se mueven de arriba y abajo.
	 * 
	 * @param
	 * 
	 */

	private void movimientosCartas() {

		for (int i = 0; i < 7; i++) {
			ArrayList<JButton> auxBotones = new ArrayList<JButton>();
			JPanel paux = new JPanel();
			paux.setBackground(new Color(0, 180, 50));
			paux.setLayout(null);

			int ejeY = 10;

			if (this.juego.getColection().get(i).size() == 0) {
				JButton nuevo = new JButton();
				nuevo.setBackground(new Color(0, 180, 50));
				nuevo.setIcon(new ImageIcon(getClass().getResource("francesa/cartaVacia.jpg")));
				nuevo.setBounds(10, ejeY, 95, 145);
				ejeY += 35;
				nuevo.addActionListener(this);

				auxBotones.add(nuevo);
				paux.add(nuevo);

			}
			int j = 0;
			while (j < juego.getColection().get(i).size()) {
				JButton nuevo = new JButton();
				if(juego.getColection().get(i).get(j).isOculta()) {
					nuevo.setIcon(new ImageIcon(getClass().getResource("francesa/Yellow.png")));
					nuevo.setBackground(new Color(0,180,50));
					nuevo.setBounds(10, ejeY, 95, 145);
				}else {
					
				}

			}
		}

	}

	private void abajoP() {

		pAbajo.setBackground(new Color(0, 90, 50));
		pCentro.setVisible(true);
	}

	private void menu() {

		menu = new JMenuBar();
		archivo = new JMenu("Archivo");
		atras = new JMenu("Menu");
		menu1 = new JMenuItem("Atrás");
		menu1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				VentanaP p = new VentanaP();
				p.setVisible(true);
			}
		});
		atras.add(menu1);
		nuevo = new JMenuItem("Nuevo");
		nuevo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				new PanelJuegoFrances();
				dispose();
			}
		});
		cargar = new JMenuItem("Cargar");
		cargar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				wantCharge();
			}

			private void wantCharge() {
				// TODO Auto-generated method stub

			}
		});
		guardar = new JMenuItem("Salvar");
		guardar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// try {
				// if (!guardarBooleans) {
				// guardarComo();
				// } else {
				//
				// guardar();
				// }
				//
				// } catch (IOException e1) {
				// // TODO Auto-generated catch block
				// e1.printStackTrace();
				// }

			}

			private void guardar() {
				// TODO Auto-generated method stub

			}

			private void guardarComo() {
				// TODO Auto-generated method stub

			}
		});

		guardarComo = new JMenuItem("Salvar como");
		guardarComo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// try {
				// guardarComo();
				// } catch (IOException e1) {
				// // TODO Auto-generated catch block
				// e1.printStackTrace();
				// }
			}

			private void guardarComo() {
				// TODO Auto-generated method stub

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

			private void accionesDeshacer() {
				// TODO Auto-generated method stub

			}
		});
		hacer = new JMenuItem("Hacer");
		hacer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				accionHacer();
			}

			private void accionHacer() {
				// TODO Auto-generated method stub

			}
		});
		resolver = new JMenuItem("Resolver");
		resolver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				new PanelJuegoFrances(baraja);
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

			private Object objetoEstadisticas;

			@Override
			public void actionPerformed(ActionEvent e) {

				// if (objetoEstadisticas.getArchivo().equals("")) {
				// try {
				// creoEstadisticas();
				// } catch (IOException e1) {
				// // TODO Auto-generated catch block
				// e1.printStackTrace();
				// }
				// } else {
				// objetoEstadisticas.actualizar();
				// JOptionPane.showMessageDialog(null, objetoEstadisticas.toString(),
				// "\"Estadisticas\"",
				// JOptionPane.INFORMATION_MESSAGE);
				// }

			}
		});
		ficheroestadisticas = new JMenuItem("Fichero estadísticas");
		ficheroestadisticas.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// try {
				// creoEstadisticas();
				// escribir(fileEstadisticas);
				// } catch (IOException e) {
				// e.printStackTrace();
				// }
				//
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
						"- Juego del solitario clásico \n" + "-" + "Tras sacar todas las cartas, mueve pares  \n"
								+ "- Ya sean de palos, o de numeros\n" + "-Una posicion o tres a la izquierda \n"
								+ "-Buena suerte\n" + "-",
						"Rules", JOptionPane.PLAIN_MESSAGE);
			}
		});
		menu.add(ayuda);

		menu.add(atras);
		atras.setVisible(true);
		this.setJMenuBar(menu);

		// Añado la barra al frame
		menu.setVisible(true);

	}

	protected void escribir(File fileEstadisticas2) {
		// TODO Auto-generated method stub

	}

	protected void creoEstadisticas() {
		// TODO Auto-generated method stub

	}
}
