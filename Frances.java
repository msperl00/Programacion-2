import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Frances  extends JFrame{

	private JPanel p;
	private JPanel pAbajo;
	private JPanel pArriba;
	private JPanel pCentro;

	public Frances() {
		configuracion();
	}

	private void configuracion() {
		// TODO Auto-generated method stub
		this.setTitle("Solitario Clásico");
		this.setSize(1350, 700);
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setBackground(new Color(0, 99, 99));
		this.menu();
		this.addWindowListener(new WindowAdapter() {

			private boolean guardarBooleans;

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
		p.setBackground(new Color(0, 99, 99));
		pAbajo = new JPanel();
		pAbajo.setLayout(new FlowLayout());
		pArriba = new JPanel();
		pArriba.setLayout(new FlowLayout());
		pCentro = new JPanel();
		pCentro.setLayout(new GridLayout(1, 7));
		this.add(p);
	}

	private void menu() {
		// TODO Auto-generated method stub
		
	}
	
}
