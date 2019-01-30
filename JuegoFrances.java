import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class JuegoFrances extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel p;
	private JPanel panel1;
	private JPanel panel2;
	private JMenuBar menu;
	private JMenu archivo, editar, historial, ayuda;
	public JMenuItem nuevo, cargar, salvarcomo, salir, salvar;
	private static JMenuItem hacer;
	public JMenuItem deshacer;
	public JMenuItem resolver;
	public JMenuItem ficheroestadisticas;
	public JMenuItem estadisticas;
	private String juego;

	public JuegoFrances(String juego) {
		// TODO Auto-generated constructor stub
		this.juego = juego;
		conf(juego);
	}
	
	private void conf(String juego1) {
		// TODO Auto-generated method stub
		this.setTitle(juego1);
		this.setSize(1024, 768);
		this.setResizable(false);
		this.setLocationRelativeTo(null); // Coloca la ventana en el centro
		this.setVisible(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub
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
		p.setBackground(Color.green);
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

		private void menu() {
		// TODO Auto-generated method stub
		menu = new JMenuBar();

		archivo = new JMenu("Archivo");
		archivo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		nuevo = new JMenuItem("Nuevo");
		nuevo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new JuegoFrances(juego);
				dispose();
			}
		});
		cargar = new JMenuItem("Cargar");
		cargar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		salvar = new JMenuItem("Salvar");
		salvar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		salvarcomo = new JMenuItem("Salvar como");
		salvarcomo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		salir = new JMenuItem("Salir");
		salir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
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
				// TODO Auto-generated method stub

			}
		});
		hacer = new JMenuItem("Hacer");
		hacer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(JuegoEspañol.getNCartasSacadas() < 40) {
					
				}
			}
		});
		resolver = new JMenuItem("Resolver");
		resolver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

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
				// TODO Auto-generated method stub
				abrir();
			}

			private void abrir() {
				// TODO Auto-generated method stub
				
			}
		});
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
				// TODO Auto-generated method stub
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
}
