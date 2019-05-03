package grafo;

import java.util.*;

public class Nodo {
	/**
	 * nome indicativo del nodo
	 */
	private String colore;
	/**
	 * lista contenente le chiavi della mappa degli archi contenenti il nodo
	 */
	private ArrayList<Integer> indiciArchi = new ArrayList<Integer>();
	/**
	 * array contenente tutti i possibili nomi dei nodi in modo da poterli generare
	 * in automatico (massimo 10 nodi)
	 */
	private static String[] colori = new String[] { "giallo", "verde", "rosso", "indaco", "viola", "magenta", "grigio",
			"marrone", "rosa", "ciano" };
	/**
	 * indice del colore attualmente disponibile, utilizzato per generare i nodi in
	 * sequenza automatica
	 */
	private static int coloreAtt = 0;

	/**
	 * costruttroe del nodo che assegna in automatico nome disponibile per il nodo
	 */
	public Nodo() {
		colore = colori[coloreAtt];
		coloreAtt++;
	}

	/**
	 * costruttore utilizzato per creare il nodo vuoto
	 * 
	 * @param i (non ha nessun valore serve solo per differenziare i due
	 *          costruttori)
	 */
	public Nodo(boolean i) {
		colore = "Err";
	}

	/**
	 * ritorna il nome/colore del nodo
	 * 
	 * @return il nome/colore del nodo
	 */
	public String getColore() {
		return colore;
	}

	/**
	 * ritorna la lista delle chiavi degli archi appartenenti al nodo
	 * 
	 * @return la lista delle chiavi degli archi appartenenti al nodo
	 */
	public ArrayList<Integer> getIndiciArchi() {
		return indiciArchi;
	}

	/**
	 * metodo per l'aggiunta dell'indice di un arco
	 * 
	 * @param i (indice del nuovo arco)
	 */
	public void addArco(int i) {
		indiciArchi.add(i);
	}

	/**
	 * metodo per la rimozione dell'arco indicato dall'indice i
	 * 
	 * @param i (indice da rimuovere)
	 */
	public void removeArco(int i) {
		indiciArchi.remove(i);
	}

	/**
	 * metodo per il reset delle variabili statiche per la preparazione della nuova
	 * partita
	 */
	public void reset() {
		coloreAtt = 0;
	}

}
