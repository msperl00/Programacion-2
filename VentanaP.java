
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * 	 La clase ventana contendra a un JPanel de intermediario que recibiran las cartas que se prcesaran
 *  a los largo del programa, las cuales son botones.
 * 
 * 
 */
public class VentanaP extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton juego1, juego2;
	private JPanel p, subPanelNorte, subPanelCentro, subPanelSur;
	private JLabel texto;
	private JMenuBar menu;
	private JMenu archivo, editar, historial, ayuda;
	private JMenuItem nuevo, cargar, salvar, salvarcomo, salir, deshacer, hacer, resolver, estadisticas,
			ficheroestadisticas;

	public VentanaP() {
		super(); // Constructor de la clase JFrame
		conf();
		inicializacion();
		// El pack no se utiliza porque es necsario tener un mismo tamaño siempre.
	}

	private void inicializacion() {

		// Menu
		menuCo();

		// JLabel Principal
		texto = new JLabel("                 ¡Bienvenido al juego de los solitarios!");
		texto.setFont(new Font("Arial", Font.PLAIN, 40));
		texto.setForeground(Color.black);

		// Panel new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS)
		p = new JPanel();
		p.setVisible(true);
		// p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS)); // El primer parametro tiene
		// que ser el mismo que el
		GridLayout nueva = new GridLayout(3, 1);
		p.setLayout(nueva);
		p.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		nueva.setHgap(200);
		nueva.setHgap(10);
		// primercontenedor

		p.setBackground(new Color(0, 180, 50));
		this.setContentPane(p); // Se lo asigno al JFrame

		// Paneles secundarioss
		subPanelNorte = new JPanel();
		subPanelNorte.setLayout(new GridLayout(1, 2));
		subPanelNorte.setBackground(new Color(0, 180, 50));
		subPanelNorte.add(texto);

		subPanelCentro = new JPanel();
		FlowLayout m = new FlowLayout();
		m.setVgap(50);
		m.setHgap(160);
		subPanelCentro.setLayout(m);
		subPanelCentro.setBackground(new Color(0, 180, 50));

		subPanelSur = new JPanel();
		FlowLayout n = new FlowLayout();
		n.setVgap(0);
		n.setHgap(250);
		subPanelSur.setLayout(n);
		subPanelSur.setBackground(new Color(0, 180, 50));

		JLabel LabelJuegoSaltos = new JLabel("Solitario saltos");
		LabelJuegoSaltos.setFont(new Font("Arial", Font.PLAIN, 35));
		LabelJuegoSaltos.setForeground(Color.BLACK);
		JLabel LabelJuegoClasico = new JLabel("Solitario clásico");
		LabelJuegoClasico.setFont(new Font("Arial", Font.PLAIN, 35));
		LabelJuegoClasico.setForeground(Color.BLACK);

		subPanelSur.add(LabelJuegoSaltos);
		subPanelSur.add(LabelJuegoClasico);

		// Inicializacion de los botones y texto que contendran los dos juegos

		juego1 = new JButton(new ImageIcon(getClass().getResource("Botones/BarajaEspañola.jpg")));
		juego1.setFont(new Font("Arial", Font.BOLD, 20));
		juego1.setBackground(new Color(0, 180, 50));
		juego1.setBorderPainted(false);

		juego2 = new JButton(new ImageIcon(getClass().getResource("Botones/BarajaFrancesa.jpg")));
		juego2.setFont(new Font("Arial", Font.BOLD, 20));
		juego2.setBackground(new Color(0, 180, 50));
		juego2.setBorderPainted(false);

		paintComponents(getGraphics()); // Pinta los componentes

		juego1.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				try {
				p.add(new PanelJuegoEspañol());
			
				} catch (IllegalArgumentException e) {

				}
				setVisible(true); // Para que sigamos creando nuevos solitarios, si así lo deseamos
				dispose();
			}
		});

		juego2.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					System.out.println("JODER");
					new PanelJuegoFrances();
					p.add(nuevo);
		
					} catch (IllegalArgumentException e) {

					}
				setVisible(true);
				dispose();

			}

		});

		p.add(subPanelNorte);
		p.add(subPanelCentro); // Añadimos al contenedor del JFrame la capa que contendra los botones.
		p.add(subPanelSur);

		subPanelCentro.add(juego1);
		subPanelCentro.add(juego2);
	}

	private void menuCo() {
		menu = new JMenuBar();

		archivo = new JMenu("Archivo");

		nuevo = new JMenuItem("Nuevo");
		nuevo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int op = JOptionPane.showOptionDialog(null, "Elección de dos de los juegos\\n\"\r\n", null, JOptionPane.YES_NO_OPTION,  JOptionPane.QUESTION_MESSAGE, null, new Object[] {"								+ \"-El primero es el solitario de saltos \\n\"\r\n"
						+ "								+ \"-El segundo es el solitario clásico \\n\",\r\n"}, null	);
				if (JOptionPane.YES_OPTION == op) {
					p.add(new PanelJuegoEspañol());
				} else if(JOptionPane.NO_OPTION == op) {
					p.add(new PanelJuegoFrances());
				}else {
					
				}
			}
		});
	

		cargar = new JMenuItem("Cargar");
		cargar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JOptionPane.showMessageDialog(rootPane,
						"Necesitas estar jugando en uno de nuestros juevos, para poder cargar");

			}
		});
		salvar = new JMenuItem("Salvar");
		salvar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(rootPane,
						"Necesitas estar jugando en uno de nuestros juevos, para poder savar la partida");

			}
		});
		salvarcomo = new JMenuItem("Salvar como");
		salvarcomo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(rootPane,
						"Necesitas estar jugando en uno de nuestros juevos, para poder savar  la partida");
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
				JOptionPane.showMessageDialog(rootPane,
						"Necesitas estar jugando en uno de nuestros juevos, para deshacer");

			}
		});
		hacer = new JMenuItem("Hacer");
		hacer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(rootPane,
						"Necesitas estar jugando en uno de nuestros juevos, para hacer un movimiento");

			}
		});
		resolver = new JMenuItem("Resolver");
		resolver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(rootPane,
						"Necesitas estar jugando en uno de nuestros juevos, para resolver");
			}
		});
		editar.add(deshacer);
		editar.add(hacer);
		editar.add(resolver);
		editar.addSeparator();
		menu.add(editar);

		historial = new JMenu("Historial");
		estadisticas = new JMenuItem("Estadísticas");
		ficheroestadisticas = new JMenuItem("Fichero estadísticas");

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
						"- Elección de dos de los juegos\n"
								+ "-El primero es el juego del solitario de saltos que hicimos en la práctica pasada \n"
								+ "-El segundo es el nuevo juego de solitario clásico"
								+ "-Dentro de cada juego, hay una explicación más detallada de la misma \n",
						"Rules", JOptionPane.PLAIN_MESSAGE);
			}

		});
		menu.add(ayuda);

		this.setJMenuBar(menu);
		// Añado la barra al frame
		menu.setVisible(true);
	}

	private void conf() {

		this.setTitle("Juego de cartas");
		this.setSize(1024, 768);
		this.setResizable(false);
		this.setLocationRelativeTo(null); // Coloca la ventana en el centro
		this.setVisible(false); // POrque podemos jugar a más de un solitario a la vez
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		// this.setOpacity();
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				int op = JOptionPane.showConfirmDialog(null, "Seguro que quieres salir?");
				if (JOptionPane.OK_OPTION == op) {
					JOptionPane.showMessageDialog(null, "Hasta luego!!!");
					System.exit(0);

				} else {
					JOptionPane.showMessageDialog(null, "Perfecto! Seguimos jugando");
				}
			}

		});

	}
}
