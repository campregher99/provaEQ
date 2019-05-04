package grafo;

import java.util.*;

public class Grafo {
	/**
	 * lista che contiene tutti i nodi appartenenti al grafo
	 */
	private ArrayList<Nodo> nodi = new ArrayList<Nodo>();
	/**
	 * mappa contenente tutti gli archi che collegano i vari nodi
	 */
	private Map<Integer, Arco> archi = new HashMap<>();
	/**
	 * valore massimo della potenza di un arco
	 */
	private int v;
	/**
	 * numero di nodi/pietre
	 */
	private int n;
	/**
	 * nodo privo di dati utilizzato in caso di errore
	 */
	private Nodo vuoto = new Nodo(false);
	/**
	 * indice dell'arco che è stato aggiunto per ultimo nella mappa
	 */
	private int indiceArchiAtt = 0;

	/**
	 * costruttore del grafo che autocompila il tutto creando anche l'equilibrio
	 * 
	 * @param v (potenza massima pietra)
	 * @param n (numero delle pietre)
	 */
	public Grafo(int v, int n) {
		this.v = v;
		this.n = n;

		// creo tutti i nodi
		for (int i = 0; i < n; i++) {
			Nodo newNodo = new Nodo();
			nodi.add(newNodo);
		}
		// creo tutti gli archi
		for (int i = 0; i < nodi.size(); i++) {
			for (int c = i + 1; c < nodi.size(); c++) {
				Arco newArco = new Arco(nodi.get(i), nodi.get(c));
				archi.put(indiceArchiAtt, newArco);
				nodi.get(i).getIndiciArchi().add(indiceArchiAtt);
				nodi.get(c).getIndiciArchi().add(indiceArchiAtt);
				indiceArchiAtt++;
			}
		}

		generaEq();
	}

	/**
	 * ritorna la lista completa dei nodi
	 * 
	 * @return la lista completa dei nodi
	 */
	public ArrayList<Nodo> getNodi() {
		return nodi;
	}

	/**
	 * ritorna il nodo richiesto cercandolo in base al nome/colore, nel caso un cui
	 * non si trovasse il nodo ritorna il nodo "vuoto"
	 * 
	 * @param colore (indice per la ricerca)
	 * @return il nodo trovato
	 */
	public Nodo getNodo(String colore) {
		for (int i = 0; i < nodi.size(); i++) {
			if (nodi.get(i).getColore().equals(colore)) {
				return nodi.get(i);
			}
		}
		return vuoto;
	}

	/**
	 * ritorna il nodo appartenente all'indice, se non viene trovato viene ritornato
	 * l'insieme vuoto
	 * 
	 * @param i (indice del nodo da cercare)
	 * @return il nodo richiesto
	 */
	public Nodo getNodo(int i) {
		if (i < nodi.size() && i >= 0) {
			return nodi.get(i);
		}
		return vuoto;
	}

	/**
	 * ritorna la mappa contenente gli archi del grafo
	 * 
	 * @return la mappa degli archi
	 */
	public Map<Integer, Arco> getArchi() {
		return archi;
	}

	/**
	 * ritorna l'arco appartenente alla chiave i, nel caso in cui non ci siano archi
	 * appartenenti alla chiave i ritorna l'arco vuoto
	 * 
	 * @param i (chiave di ricerca)
	 * @return l'arco richiesto
	 */
	public Arco getArco(int i) {
		if (i < archi.size() && i >= 0) {
			return archi.get(i);
		}
		Arco vuoto = new Arco(this.vuoto, this.vuoto);
		return vuoto;
	}

	/**
	 * ritorna il valore di potenza massima delle pietre
	 * 
	 * @return il valore di potenza massima delle pietre
	 */
	public int getV() {
		return v;
	}

	/**
	 * ritorna il numero di nodi/pietre
	 * 
	 * @return il numero di nodi/pietre
	 */
	public int getN() {
		return n;
	}

	/**
	 * metodo che genera l'equilibrio della parita
	 */
	private void generaEq() {
		Random rnd = new Random();
		int d = 0, ingressi = 0, uscite = 0;
		// creazione random dei nodi auto equilibranti
		for (int i = 0; i < Math.floor((nodi.size() - 1) / 2); i++) {
			// creazione random archi
			for (int c = 0; c < nodi.size() - i - 2; c++) {
				archi.get(d).setValore(rnd.nextInt(v - 1) + 1, rnd.nextBoolean());
				d++;
			}

			// azzeramento delle variabili per il prossimo ciclo
			uscite = 0;
			ingressi = 0;
			// calcolo totale ingressi e uscite
			for (int f = 0; f < nodi.size() - 2; f++) {
				if (archi.get(nodi.get(i).getIndiciArchi().get(f)).getNodo1().getColore()
						.equals(nodi.get(i).getColore())) {
					if (archi.get(nodi.get(i).getIndiciArchi().get(f)).getDirezione()) {
						ingressi += archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore();
					} else {
						uscite += archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore();
					}
				} else {
					if (!archi.get(nodi.get(i).getIndiciArchi().get(f)).getDirezione()) {
						ingressi += archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore();
					} else {
						uscite += archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore();
					}
				}
			}
			// inizio bilanciamento archi calcolati in modo randomico in funzione
			// dell'equilibrio
			if (Math.abs(ingressi - uscite) <= v) {
				// caso in cui basti creare l'arco con un valore dato poichè il valore da
				// correggere è inferiore a v
				if (ingressi < uscite) {
					archi.get(d).setValore(Math.abs(ingressi - uscite), true);

				} else {
					archi.get(d).setValore(Math.abs(ingressi - uscite), false);

				}
				// casi in cui non basta creare solo un arco ma bisogna mettere a posto anche
				// gli altri allora metto a posto solo gli archi creati dal nodo così da non
				// sballare gli altri
			} else if (ingressi < uscite) {
				archi.get(nodi.get(i).getIndiciArchi().get(3)).setValore(v, true);
				// scorro tutte le uscite per compensare
				for (int f = i; f < nodi.size() - 2; f++) {
					if (archi.get(nodi.get(i).getIndiciArchi().get(f)).getNodo1().getColore()
							.equals(nodi.get(i).getColore())) {
						if (!archi.get(nodi.get(i).getIndiciArchi().get(f)).getDirezione()) {
							if (archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore() > Math.abs(ingressi - uscite)
									- v) {
								// toglie i soldi alle uscite abbastanza abbienti per compensare il debito
								archi.get(nodi.get(i).getIndiciArchi().get(f))
										.setValore(archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore()
												- (Math.abs(ingressi - uscite) - v), false);
								uscite = ingressi;
								break;
							} else {
								// altrimenti massacra le uscite che non hanno abbastanza soldi per pagarsi
								// l'immunità
								uscite -= archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore() - 1;
								archi.get(nodi.get(i).getIndiciArchi().get(f)).setValore(1, false);
							}
						}
					} else {
						if (archi.get(nodi.get(i).getIndiciArchi().get(f)).getDirezione()) {
							if (archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore() > Math.abs(ingressi - uscite)
									- v) {
								// toglie i soldi alle uscite abbastanza abbienti per compensare il debito
								archi.get(nodi.get(i).getIndiciArchi().get(f))
										.setValore(archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore()
												- (Math.abs(ingressi - uscite) - v), false);
								uscite = ingressi;
								break;
							} else {
								// altrimenti massacra le uscite che non hanno abbastanza soldi per pagarsi
								// l'immunità
								uscite -= archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore() - 1;
								archi.get(nodi.get(i).getIndiciArchi().get(f)).setValore(1, false);
							}
						}
					}
				}
				if (uscite - ingressi != 0) {
					// se le uscite non bastano scorro anche gli ingressi per ingrassarli
					for (int f = i; f < nodi.size() - 2; f++) {
						if (archi.get(nodi.get(i).getIndiciArchi().get(f)).getNodo1().getColore()
								.equals(nodi.get(i).getColore())) {
							if (archi.get(nodi.get(i).getIndiciArchi().get(f)).getDirezione()) {
								if (v - archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore() < Math
										.abs(ingressi - uscite) - v) {
									// se basta ne ingrassa solo uno
									archi.get(nodi.get(i).getIndiciArchi().get(f))
											.setValore(archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore()
													+ (Math.abs(ingressi - uscite) - v), true);
									break;
								} else {
									// altrimenti inizia ad ingrassarne uno alla volta del tutto decrementando il
									// debito
									ingressi -= v - archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore();
									archi.get(nodi.get(i).getIndiciArchi().get(f)).setValore(v, false);
								}
							}
						} else {
							if (!archi.get(nodi.get(i).getIndiciArchi().get(f)).getDirezione()) {
								if (v - archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore() < Math
										.abs(ingressi - uscite) - v) {
									// se basta ne ingrassa solo uno
									archi.get(nodi.get(i).getIndiciArchi().get(f))
											.setValore(archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore()
													+ (Math.abs(ingressi - uscite) - v), true);
									break;
								} else {
									// altrimenti inizia ad ingrassarne uno alla volta del tutto decrementando il
									// debito
									ingressi -= v - archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore();
									archi.get(nodi.get(i).getIndiciArchi().get(f)).setValore(v, false);
								}
							}
						}

					}
				}
			} else if (ingressi > uscite) {
				archi.get(nodi.get(i).getIndiciArchi().get(3)).setValore(v, false);
				// scorro tutte le uscite per compensare
				for (int f = i; f < nodi.size() - 2; f++) {
					if (archi.get(nodi.get(i).getIndiciArchi().get(f)).getNodo1().getColore()
							.equals(nodi.get(i).getColore())) {
						if (!archi.get(nodi.get(i).getIndiciArchi().get(f)).getDirezione()) {
							if (v - archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore() < Math
									.abs(ingressi - uscite) - v) {
								// aggiunge alle uscite per colmare il debito
								archi.get(nodi.get(i).getIndiciArchi().get(f))
										.setValore(archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore()
												+ (Math.abs(ingressi - uscite) - v), false);
								ingressi = uscite;
								break;
							} else {
								// altrimenti aggiunge parte del debito degli ingressi
								uscite -= v - archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore();
								archi.get(nodi.get(i).getIndiciArchi().get(f)).setValore(v, false);
							}
						}
					} else {
						if (archi.get(nodi.get(i).getIndiciArchi().get(f)).getDirezione()) {
							if (v - archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore() < Math
									.abs(ingressi - uscite) - v) {
								// aggiunge alle uscite per colmare il debito
								archi.get(nodi.get(i).getIndiciArchi().get(f))
										.setValore(archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore()
												+ (Math.abs(ingressi - uscite) - v), false);
								ingressi = uscite;
								break;
							} else {
								// altrimenti aggiunge parte del debito degli ingressi
								uscite -= v - archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore();
								archi.get(nodi.get(i).getIndiciArchi().get(f)).setValore(v, false);
							}
						}
					}

				}
				if (ingressi - uscite != 0) {
					// se le uscite non bastano scorro anche gli ingressi per massacrarli
					for (int f = i; f < nodi.size() - 2; f++) {
						if (archi.get(nodi.get(i).getIndiciArchi().get(f)).getNodo1().getColore()
								.equals(nodi.get(i).getColore())) {
							if (archi.get(nodi.get(i).getIndiciArchi().get(f)).getDirezione()) {
								if (archi.get(nodi.get(i).getIndiciArchi().get(f))
										.getValore() > Math.abs(ingressi - uscite) - v) {
									// se può massacra un solo ingresso
									archi.get(nodi.get(i).getIndiciArchi().get(f))
											.setValore(archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore()
													- (Math.abs(ingressi - uscite) - v), true);
									break;
								} else {
									// altrimenti ne massacra un po' fino a che non è finito il debito
									ingressi -= archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore() - 1;
									archi.get(nodi.get(i).getIndiciArchi().get(f)).setValore(1, false);
								}
							}
						} else {
							if (!archi.get(nodi.get(i).getIndiciArchi().get(f)).getDirezione()) {
								if (archi.get(nodi.get(i).getIndiciArchi().get(f))
										.getValore() > Math.abs(ingressi - uscite) - v) {
									// se può massacra un solo ingresso
									archi.get(nodi.get(i).getIndiciArchi().get(f))
											.setValore(archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore()
													- (Math.abs(ingressi - uscite) - v), true);
									break;
								} else {
									// altrimenti ne massacra un po' fino a che non è finito il debito
									ingressi -= archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore() - 1;
									archi.get(nodi.get(i).getIndiciArchi().get(f)).setValore(1, false);
								}
							}
						}

					}
				}
			}

			d++;
		}

		// creazione dei nodi equilibranti in società
		for (int i = (int) (Math.floor((nodi.size() - 1) / 2)); i < nodi.size(); i++) {
			boolean isCreato = false;
			// creazione archi in modo randomico
			for (int c = 0; c < nodi.size() - i - 2; c++) {
				archi.get(d).setValore(rnd.nextInt(v - 1) + 1, rnd.nextBoolean());
				d++;
			}
			if (i != nodi.size() - 1) {
				// azzeramento delle variabili per il prossimo ciclo
				uscite = 0;
				ingressi = 0;
				// calcolo ingressi e uscite totali
				for (int f = 0; f < nodi.size() - 2; f++) {
					if (archi.get(nodi.get(i).getIndiciArchi().get(f)).getNodo1().getColore()
							.equals(nodi.get(i).getColore())) {
						if (archi.get(nodi.get(i).getIndiciArchi().get(f)).getDirezione()) {
							ingressi += archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore();
						} else {
							uscite += archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore();
						}
					} else {
						if (!archi.get(nodi.get(i).getIndiciArchi().get(f)).getDirezione()) {
							ingressi += archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore();
						} else {
							uscite += archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore();
						}
					}
				}
				// ripete il procedimento sopra indicato solo che mette in conto che potrebbe
				// essere che l'equilibrio non potrebbe essere creato allora introduce una
				// variabile booleana per tenere traccia del fatto che sia stato creato o no
				// l'equilibrio
				if (Math.abs(ingressi - uscite) <= v) {
					// se riesce a bilanciarlo subito con l'arco da creare tutto ok
					if (ingressi < uscite) {
						archi.get(d).setValore(Math.abs(ingressi - uscite), true);
						isCreato = true;
					} else {
						archi.get(d).setValore(Math.abs(ingressi - uscite), false);
						isCreato = true;
					}
					// altrimenti ripete il procedimento precedente
				} else if (ingressi < uscite) {
					// creazione ultimo arco del nodo
					archi.get(nodi.get(i).getIndiciArchi().get(3)).setValore(v, true);
					// scorro tutte le uscite per compensare
					for (int f = i; f < nodi.size() - 2; f++) {
						if (archi.get(nodi.get(i).getIndiciArchi().get(f)).getNodo1().getColore()
								.equals(nodi.get(i).getColore())) {
							if (!archi.get(nodi.get(i).getIndiciArchi().get(f)).getDirezione()) {
								if (archi.get(nodi.get(i).getIndiciArchi().get(f))
										.getValore() > Math.abs(ingressi - uscite) - v) {
									// toglie i soldi alle uscite abbastanza abbienti per compensare il debito
									archi.get(nodi.get(i).getIndiciArchi().get(f))
											.setValore(archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore()
													- (Math.abs(ingressi - uscite) - v), false);
									isCreato = true;
									break;
								} else {
									// massacra le uscite che non hanno abbastanza soldi per pagarsi l'immunità
									uscite -= archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore() - 1;
									archi.get(nodi.get(i).getIndiciArchi().get(f)).setValore(1, false);
								}
							}
						} else {
							if (archi.get(nodi.get(i).getIndiciArchi().get(f)).getDirezione()) {
								if (archi.get(nodi.get(i).getIndiciArchi().get(f))
										.getValore() > Math.abs(ingressi - uscite) - v) {
									// toglie i soldi alle uscite abbastanza abbienti per compensare il debito
									archi.get(nodi.get(i).getIndiciArchi().get(f))
											.setValore(archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore()
													- (Math.abs(ingressi - uscite) - v), false);
									isCreato = true;
									break;
								} else {
									// massacra le uscite che non hanno abbastanza soldi per pagarsi l'immunità
									uscite -= archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore() - 1;
									archi.get(nodi.get(i).getIndiciArchi().get(f)).setValore(1, false);
								}
							}
						}

					}
					if (!isCreato) {
						// se le uscite non bastano scorro anche gli ingressi per ingrassarli
						for (int f = i; f < nodi.size() - 2; f++) {
							if (archi.get(nodi.get(i).getIndiciArchi().get(f)).getNodo1().getColore()
									.equals(nodi.get(i).getColore())) {
								if (archi.get(nodi.get(i).getIndiciArchi().get(f)).getDirezione()) {
									if (v - archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore() < Math
											.abs(ingressi - uscite) - v) {
										archi.get(nodi.get(i).getIndiciArchi().get(f))
												.setValore(archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore()
														+ (Math.abs(ingressi - uscite) - v), true);
										isCreato = true;
										break;
									} else {
										ingressi -= v - archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore();
										archi.get(nodi.get(i).getIndiciArchi().get(f)).setValore(v, false);
									}
								}
							} else {
								if (!archi.get(nodi.get(i).getIndiciArchi().get(f)).getDirezione()) {
									if (v - archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore() < Math
											.abs(ingressi - uscite) - v) {
										archi.get(nodi.get(i).getIndiciArchi().get(f))
												.setValore(archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore()
														+ (Math.abs(ingressi - uscite) - v), true);
										isCreato = true;
										break;
									} else {
										ingressi -= v - archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore();
										archi.get(nodi.get(i).getIndiciArchi().get(f)).setValore(v, false);
									}
								}
							}

						}
					}
				} else if (ingressi > uscite) {
					// creazione ultimo arco del nodo
					archi.get(nodi.get(i).getIndiciArchi().get(3)).setValore(v, false);
					// scorro tutte le uscite per compensare
					for (int f = i; f < nodi.size() - 2; f++) {
						if (archi.get(nodi.get(i).getIndiciArchi().get(f)).getNodo1().getColore()
								.equals(nodi.get(i).getColore())) {
							if (!archi.get(nodi.get(i).getIndiciArchi().get(f)).getDirezione()) {
								if (v - archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore() < Math
										.abs(ingressi - uscite) - v) {
									// aggiunge alle uscite per colmare il debito
									archi.get(nodi.get(i).getIndiciArchi().get(f))
											.setValore(archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore()
													+ (Math.abs(ingressi - uscite) - v), false);
									isCreato = true;
									break;
								} else {
									// aggiunge parte del debito degli ingressi
									uscite -= v - archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore();
									archi.get(nodi.get(i).getIndiciArchi().get(f)).setValore(v, false);
								}
							}
						} else {
							if (archi.get(nodi.get(i).getIndiciArchi().get(f)).getDirezione()) {
								if (v - archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore() < Math
										.abs(ingressi - uscite) - v) {
									// aggiunge alle uscite per colmare il debito
									archi.get(nodi.get(i).getIndiciArchi().get(f))
											.setValore(archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore()
													+ (Math.abs(ingressi - uscite) - v), false);
									isCreato = true;
									break;
								} else {
									// aggiunge parte del debito degli ingressi
									uscite -= v - archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore();
									archi.get(nodi.get(i).getIndiciArchi().get(f)).setValore(v, false);
								}
							}
						}

					}
					if (!isCreato) {
						// se le uscite non bastano scorro anche gli ingressi per massacrarli
						for (int f = i; f < nodi.size() - 2; f++) {
							if (archi.get(nodi.get(i).getIndiciArchi().get(f)).getNodo1().getColore()
									.equals(nodi.get(i).getColore())) {
								if (archi.get(nodi.get(i).getIndiciArchi().get(f)).getDirezione()) {
									if (archi.get(nodi.get(i).getIndiciArchi().get(f))
											.getValore() > Math.abs(ingressi - uscite) - v) {
										archi.get(nodi.get(i).getIndiciArchi().get(f))
												.setValore(archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore()
														- (Math.abs(ingressi - uscite) - v), true);
										isCreato = true;
										break;
									} else {
										ingressi -= archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore() - 1;
										archi.get(nodi.get(i).getIndiciArchi().get(f)).setValore(1, false);
									}
								}
							} else {
								if (!archi.get(nodi.get(i).getIndiciArchi().get(f)).getDirezione()) {
									if (archi.get(nodi.get(i).getIndiciArchi().get(f))
											.getValore() > Math.abs(ingressi - uscite) - v) {
										archi.get(nodi.get(i).getIndiciArchi().get(f))
												.setValore(archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore()
														- (Math.abs(ingressi - uscite) - v), true);
										isCreato = true;
										break;
									} else {
										ingressi -= archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore() - 1;
										archi.get(nodi.get(i).getIndiciArchi().get(f)).setValore(1, false);
									}
								}
							}

						}
					}
				}
			} else {
				// azzeramento delle variabili per il prossimo ciclo
				uscite = 0;
				ingressi = 0;
				// calcolo ingressi e uscite totali
				for (int f = 0; f < nodi.size() - 1; f++) {
					if (archi.get(nodi.get(i).getIndiciArchi().get(f)).getNodo1().getColore()
							.equals(nodi.get(i).getColore())) {
						if (archi.get(nodi.get(i).getIndiciArchi().get(f)).getDirezione()) {
							ingressi += archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore();
						} else {
							uscite += archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore();
						}
					} else {
						if (!archi.get(nodi.get(i).getIndiciArchi().get(f)).getDirezione()) {
							ingressi += archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore();
						} else {
							uscite += archi.get(nodi.get(i).getIndiciArchi().get(f)).getValore();
						}
					}
				}
				if (ingressi - uscite == 0) {
					isCreato = true;
				}
			}

			int r = 0; // livello di arretramento nei nodi ovvero quanti nodi deve retrocedere per
						// aggiustare l'equilibrio
			// se non è stato ancora compensato il nodo torna al nodo precedente, ovvero la
			// variabile booleana è false
			while (!isCreato) {
				uscite = 0;
				ingressi = 0;
				for (int f = 0; f < nodi.size() - 1; f++) {
					if (archi.get(nodi.get(i - r).getIndiciArchi().get(f)).getNodo1().getColore()
							.equals(nodi.get(i - r).getColore())) {
						if (archi.get(nodi.get(i - r).getIndiciArchi().get(f)).getDirezione()) {
							ingressi += archi.get(nodi.get(i - r).getIndiciArchi().get(f)).getValore();
						} else {
							uscite += archi.get(nodi.get(i - r).getIndiciArchi().get(f)).getValore();
						}
					} else {
						if (!archi.get(nodi.get(i - r).getIndiciArchi().get(f)).getDirezione()) {
							ingressi += archi.get(nodi.get(i - r).getIndiciArchi().get(f)).getValore();
						} else {
							uscite += archi.get(nodi.get(i - r).getIndiciArchi().get(f)).getValore();
						}
					}

				}
				r++;// incremento la variabile per lavorare sul nodo precedente bilanciamento del
					// nodo precedente
				// fine bilanciamento del nodo attraverso l'arco del nodo precedente
				if (ingressi < uscite) {
					// controlla che sia un uscita o un entrata per il nodo in questione
					if (archi.get(nodi.get(i - r).getIndiciArchi().get(i - r - 1)).getDirezione()) {
						if (archi.get(nodi.get(i - r).getIndiciArchi().get(i - r - 1)).getValore()
								- (Math.abs(ingressi - uscite) - v) > 0) {
							// se basta il valore dell'arco mantengo il suo verso e lo correggo
							archi.get(nodi.get(i - r).getIndiciArchi().get(i - r - 1))
									.setValore(archi.get(nodi.get(i - r).getIndiciArchi().get(i - r - 1)).getValore()
											- (Math.abs(ingressi - uscite) - v), true);
						} else {
							// altrimenti gli cambio il verso
							archi.get(nodi.get(i - r).getIndiciArchi().get(i - r - 1)).setValore(
									Math.abs(archi.get(nodi.get(i - r).getIndiciArchi().get(i - r - 1)).getValore()
											- (Math.abs(ingressi - uscite) - v)),
									false);
						}
					}
				} else {
					if (!archi.get(nodi.get(i - r).getIndiciArchi().get(i - r - 1)).getDirezione()) {
						if (archi.get(nodi.get(i - r).getIndiciArchi().get(i - r - 1)).getValore()
								- (Math.abs(ingressi - uscite) - v) > 0) {
							// se basta il valore dell'arco mantengo il suo verso e lo correggo
							archi.get(nodi.get(i - r).getIndiciArchi().get(i - r - 1))
									.setValore(archi.get(nodi.get(i - r).getIndiciArchi().get(i - r - 1)).getValore()
											- (Math.abs(ingressi - uscite) - v), false);
						} else {
							// altrimenti gli cambio il verso
							archi.get(nodi.get(i - r).getIndiciArchi().get(i - r - 1)).setValore(
									Math.abs(archi.get(nodi.get(i - r).getIndiciArchi().get(i - r - 1)).getValore()
											- (Math.abs(ingressi - uscite) - v)),
									true);
						}
					}

				}

				uscite = 0;
				ingressi = 0;
				// calcolo ingressi e uscite totali del nodo
				for (int f = 0; f < nodi.size() - 2; f++) {
					if (archi.get(nodi.get(i - r).getIndiciArchi().get(f)).getNodo1().getColore()
							.equals(nodi.get(i - r).getColore())) {
						if (archi.get(nodi.get(i - r).getIndiciArchi().get(f)).getDirezione()) {
							ingressi += archi.get(nodi.get(i - r).getIndiciArchi().get(f)).getValore();
						} else {
							uscite += archi.get(nodi.get(i - r).getIndiciArchi().get(f)).getValore();
						}
					} else {
						if (!archi.get(nodi.get(i - r).getIndiciArchi().get(f)).getDirezione()) {
							ingressi += archi.get(nodi.get(i - r).getIndiciArchi().get(f)).getValore();
						} else {
							uscite += archi.get(nodi.get(i - r).getIndiciArchi().get(f)).getValore();
						}
					}
				}

				// inizio a vedere quali degli archi devo ridimensionare, se in uscita o in
				// ingresso e poi modifico solo gli archi creati dal nodo meno quello modificato
				// precedentemente per creare l'equilibrio
				if (ingressi < uscite) {
					// scorro tutte le uscite per compensare
					for (int f = i - r + 1; f < nodi.size() - 1; f++) {
						if (!archi.get(nodi.get(i - r).getIndiciArchi().get(f)).getDirezione()) {
							if (archi.get(nodi.get(i - r).getIndiciArchi().get(f))
									.getValore() > Math.abs(ingressi - uscite) - v) {
								// toglie i soldi alle uscite abbastanza abbienti per compensare il debito
								archi.get(nodi.get(i - r).getIndiciArchi().get(f))
										.setValore(archi.get(nodi.get(i - r).getIndiciArchi().get(f)).getValore()
												- (Math.abs(ingressi - uscite) - v), false);
								isCreato = true;
								break;
							} else {
								// massacra le uscite che non hanno abbastanza soldi per pagarsi l'immunità
								uscite -= archi.get(nodi.get(i - r).getIndiciArchi().get(f)).getValore() - 1;
								archi.get(nodi.get(i - r).getIndiciArchi().get(f)).setValore(1, false);
								isCreato = true;
							}
						}
					}
					// se le uscite non bastano scorro anche gli ingressi per ingrassarli
					for (int f = i - r + 1; f < nodi.size() - 1; f++) {
						if (archi.get(nodi.get(i - r).getIndiciArchi().get(f)).getDirezione()) {
							if (v - archi.get(nodi.get(i - r).getIndiciArchi().get(f)).getValore() < Math
									.abs(ingressi - uscite) - v) {
								archi.get(nodi.get(i - r).getIndiciArchi().get(f))
										.setValore(archi.get(nodi.get(i - r).getIndiciArchi().get(f)).getValore()
												+ (Math.abs(ingressi - uscite) - v), true);
								isCreato = true;
								break;
							} else {
								ingressi += v - archi.get(nodi.get(i - r).getIndiciArchi().get(f)).getValore();
								archi.get(nodi.get(i - r).getIndiciArchi().get(f)).setValore(v, false);
								isCreato = true;
							}
						}
					}

				} else if (ingressi > uscite) {
					// scorro tutte le uscite per compensare
					for (int f = i - r + 1; f < nodi.size() - 1; f++) {
						if (!archi.get(nodi.get(i - r).getIndiciArchi().get(f)).getDirezione()) {
							if (v - archi.get(nodi.get(i - r).getIndiciArchi().get(f)).getValore() < Math
									.abs(ingressi - uscite) - v) {
								// aggiunge alle uscite per colmare il debito
								archi.get(nodi.get(i - r).getIndiciArchi().get(f))
										.setValore(archi.get(nodi.get(i - r).getIndiciArchi().get(f)).getValore()
												+ (Math.abs(ingressi - uscite) - v), false);
								isCreato = true;
								break;
							} else {
								// aggiunge parte del debito degli ingressi
								uscite += v - archi.get(nodi.get(i - r).getIndiciArchi().get(f)).getValore();
								archi.get(nodi.get(i - r).getIndiciArchi().get(f)).setValore(v, false);
								isCreato = true;
							}
						}
					}
					// se le uscite non bastano scorro anche gli ingressi per massacrarli
					for (int f = i - r + 1; f < nodi.size() - 1; f++) {
						if (archi.get(nodi.get(i - r).getIndiciArchi().get(f)).getDirezione()) {
							if (archi.get(nodi.get(i - r).getIndiciArchi().get(f))
									.getValore() > Math.abs(ingressi - uscite) - v) {
								archi.get(nodi.get(i - r).getIndiciArchi().get(f))
										.setValore(archi.get(nodi.get(i - r).getIndiciArchi().get(f)).getValore()
												- (Math.abs(ingressi - uscite) - v), true);
								isCreato = true;
								break;
							} else {
								ingressi -= archi.get(nodi.get(i - r).getIndiciArchi().get(f)).getValore() - 1;
								archi.get(nodi.get(i - r).getIndiciArchi().get(f)).setValore(1, false);
								isCreato = true;
							}
						}
					}

				}
				if (uscite - ingressi == 0)
					isCreato = true;
			}
			d++;
		}
	}

	/**
	 * metodo per l'aggiunta di un arco
	 * 
	 * @param nodo1 (primo nodo appartenente all'arco)
	 * @param nodo2 (secondo nodo appartenente all'arco)
	 * @param v     (valore dell'arco)
	 */
	private void addArco(Nodo nodo1, Nodo nodo2, int v) {

	}

	/**
	 * metodo per l'aggiunta di un nodo che viene creato in automatico in mniera
	 * sequenziale
	 */
	private void addNodo() {

	}

	/**
	 * resetta tutto il grafo
	 */
	public void reset() {

	}
}
