import java.util.ArrayList;

public class Jeu {
	private Grille grilleActuelle;
	private int joueurActuel;
	private int nbDePieces = 0;

	public Jeu(){
		grilleActuelle=new Grille();
		joueurActuel=1;
	}

	public int getJoueurActuel() {
		return joueurActuel;
	}

	@Override
	public String toString(){
		return grilleActuelle.toString();
	}

	public int jouerCoup(int colonne){
		int emplacementDernierePiece = this.grilleActuelle.jouerCoup(joueurActuel,colonne);

		if (emplacementDernierePiece != -1) {
			nbDePieces++;
		}

		return emplacementDernierePiece;
	}

	public void changementJoueur() {
		this.joueurActuel=((joueurActuel-1)^1)+1;
	}

	public int joueurGagnant() {
		if (nbDePieces==(Grille.getNbColonnes()*Grille.getNbLignes()))
			return 0;

		return joueurActuel;
	}

	public boolean estFinal() {
		boolean res = false;

		if (nbDePieces > 6) { // au moins 7 cases ont jouées (ex : le premier joueur gagne au bout de son 4ème coup, cela fait donc 7 coups)
			res = grilleActuelle.estFinal(joueurActuel);
		}

		return res;
	}

	public ArrayList<Grille> getSuccesseurs() {
		return grilleActuelle.getSuccesseurs(joueurActuel);
	}

	public void ordi_joue_mcts(int tempsMax) {
		long start = System.currentTimeMillis();
		long tempsCourant = start;

		Noeud racine = new Noeud(grilleActuelle, ((joueurActuel-1)^1)+1); // ------------> CHANGEMENT ICI,
																		// le joueur qui a 
																		// joué pour arriver à cet état racine
																		// n'est pas le joueur qui joue maintenant
		Noeud courant = racine;
		int res = 0;

		do {

			// noeud n'ayant pas d'enfant développés en passant par les noeuds de plus grande B-valeur
			courant = selection(courant);
			if (!courant.estFinal()) {
				// enfant choisi aléatoirement
				courant = developpement(courant);
				// parcours aléatoire des enfants jusqu'à une fin de partie				
				res = simulation(courant);
			}
			// mise à jour des variables victoires et simulations des noeuds de la branche simulée
			courant = miseAJour(courant, res);

			tempsCourant = System.currentTimeMillis()-start;
		} while (tempsCourant/1000 < tempsMax);

		int meilleurCoup = courant.getPlusGrandeBValeur().getCoup();

		grilleActuelle.jouerCoup(joueurActuel, meilleurCoup/Grille.getNbColonnes());
	}

	public Noeud selection(Noeud courant) {
		if (courant.estFinal() || courant.getNbEnfants() == 0)
			return courant;

		return selection(courant.getPlusGrandeBValeur());
	}

	public Noeud developpement(Noeud courant) {
		return courant.getProchainNoeudADevelopper();
	}

	public int simulation(Noeud courant) {
		Noeud copie = new Noeud(courant);

		return marcheAleatoire(copie);
	}

	public Noeud miseAJour(Noeud courant, int res) {
		// tant qu'on est pas remonté jusqu'à la racine, on met à jour les valeurs de victoires et de simulations et on remonte
		while (!courant.estRacine()) {
			courant.augmenterNbVictoires(res);
			courant.augmenterNbSimulations();

			courant = courant.getParent();
		}

		return courant;
	}

	public int marcheAleatoire(Noeud n) {

		// tant qu'on arrive pas en fin de partie, on continue la branche en passant par un noeud aléatoire
		while (!n.estFinal()) {
			n = n.getEnfantAleatoire();
		}

		int resultatPartie = (n.getJoueur() == 1) ? 1 : 0;
		return resultatPartie;
	}
}