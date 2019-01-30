import java.awt.EventQueue;

public class MainCartas {

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				
				try {

					VentanaP inicio = new VentanaP();

					inicio.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
