import java.util.ArrayList;


public class Estadisiticas {

	private int saltosOk;
	private int saltosFail;
	private int clasicoOk;
	private int clasicoFail;

	private String archivo;

	public ArrayList<String> leer;

	public Estadisiticas(String solitario) {
		this.saltosOk = 0;
		this.saltosFail = 0;
		this.clasicoOk = 0;
		this.clasicoFail = 0;
		this.setArchivo("");
		this.leer = new ArrayList<String>();

		leer.add(solitario);
		leer.add(String.valueOf(getSaltosOk()));
		leer.add(String.valueOf(getSaltosFail()));
		leer.add(String.valueOf(getClasicoOk()));
		leer.add(String.valueOf(getClasicoFail()));

	}

	public void actualizar() {
		// TODO Auto-generated method stub
		leer.set(1, String.valueOf(getSaltosOk()));
		leer.set(2, String.valueOf(getSaltosFail()));
		leer.set(3, String.valueOf(getClasicoOk()));
		leer.set(4, String.valueOf(getClasicoFail()));
	}

	/**
	 * @return the saltosOk
	 */
	public int getSaltosOk() {
		return saltosOk;
	}

	/**
	 * @param saltosOk
	 *            the saltosOk to set
	 */
	public void setSaltosOk(int saltosOk) {
		this.saltosOk = saltosOk;
	}

	/**
	 * @return the saltosFail
	 */
	public int getSaltosFail() {
		return saltosFail;
	}

	/**
	 * @param saltosFail
	 *            the saltosFail to set
	 */
	public void setSaltosFail(int saltosFail) {
		this.saltosFail = saltosFail;
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

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Estadisticas Saltos [Intentos con exito =" + getSaltosOk() + ", intentos fallidos = " + getSaltosFail() + "]"+"\n"
				+"Estadisticas Saltos [Intentos con exito =" + getClasicoOk() + ", intentos fallidos = " + getClasicoFail() +" ]";
	}

	/**
	 * @return the clasicoOk
	 */
	public int getClasicoOk() {
		return clasicoOk;
	}

	/**
	 * @param clasicoOk the clasicoOk to set
	 */
	public void setClasicoOk(int clasicoOk) {
		this.clasicoOk = clasicoOk;
	}

	/**
	 * @return the clasicoFail
	 */
	public int getClasicoFail() {
		return clasicoFail;
	}

	/**
	 * @param clasicoFail the clasicoFail to set
	 */
	public void setClasicoFail(int clasicoFail) {
		this.clasicoFail = clasicoFail;
	}
	
}
