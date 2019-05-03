package grafo;

public class Arco {
	/**
	 * prmio nodo di appartenenza dell'Arco
	 */
	private Nodo nodo1;
	/**
	 * secondo nodo di appartenenza dell'arco
	 */
	private Nodo nodo2;
	/**
	 * valore dell'arco
	 */
	private int valore;
	/**
	 * direzione dell'arco, 0 da nodo1 a nodo2, 1 da nodo2 a nodo1
	 */
	private boolean direzione;

	/**
	 * costruttore dove vengono inseriti i nodi di appartenenza
	 * 
	 * @param nodo1 (primo nodo di appartenenza)
	 * @param nodo2 (secondo nodo di appartenenza)
	 */
	public Arco(Nodo nodo1, Nodo nodo2) {
		this.nodo1 = nodo1;
		this.nodo2 = nodo2;
	}

	/**
	 * set del valroe dell'arco con la sua direzione
	 * 
	 * @param v (valore da assegnare all'arco)
	 * @param d (direzione dell'arco)
	 */
	public void setValore(int v, boolean d) {
		valore = v;
		direzione = d;
	}

	/**
	 * ritorna il primo nodo dell'arco
	 * 
	 * @return il primo nodo dell'arco
	 */
	public Nodo getNodo1() {
		return nodo1;
	}

	/**
	 * ritorna il secondo nodo dell'arco
	 * 
	 * @return il secondo nodo dell'arco
	 */
	public Nodo getNodo2() {
		return nodo2;
	}

	/**
	 * ritorna il valore dell'arco
	 * 
	 * @return il valore dell'arco
	 */
	public int getValore() {
		return valore;
	}

	/**
	 * ritorna la direzione dell'arco
	 * 
	 * @return la direzione dell'arco
	 */
	public boolean getDirezione() {
		return direzione;
	}

}
