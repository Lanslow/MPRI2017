import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.Random;
import java.lang.Float;


public class Noeud {
	// instance utilisée pour la marche aléatoire
	final private static Random random = new Random();

	final private int joueur; // joueur qui a joué pour arriver dans l'état courant
	final private int coup; // coup joué par ce joueur pour arriver dans l'état courant

	final private Grille etat;

	private Noeud parent; // Etat d'où vient le noeud courant
	private ArrayList<Noeud> enfants = new ArrayList<>(); // liste des coups possibles à partir de ce noeud

	private int nb_victoires; // nombre de victoire à partir de ce noeud
	private int nb_simulations; // nombre de simulations à partir de ce noeud

	// constructeur utilisé pour la création du noeud racine de l'arbre
	public Noeud(Grille g, int j) {
		etat = new Grille(g);
		parent = null;
		coup = -1; // aucun coup n'a été joué avant

		joueur = j; // <----- CHANGEMENT ICI, lorsque deux machines s'affronteront
							// joueur ne sera pas forcément 1

		nb_victoires = 0;
		nb_simulations = 0;
	}

	public Noeud(Noeud p, Grille g) {
		etat = new Grille(g);
		parent = p;
		coup = etat.getEmplacementDernierePiece();

		joueur = ((p.getJoueur()-1)^1)+1;

		nb_victoires = 0;
		nb_simulations = 0;	
	}

	public Noeud(Noeud n) {
		etat = new Grille(n.getEtat());
		parent = null;
		coup = n.getCoup();
		joueur = n.getJoueur();
		nb_victoires = n.getNbVictoires();
		nb_simulations = n.getNbSimulations();

		for (int i=0; i<n.getNbEnfants(); i++) {
			// copie?
			enfants.add(n.getEnfant(i));
		}
	}

	public void ajouterEnfant(Noeud n) {
		enfants.add(n);
	}

	public Noeud getEnfant(int index) {
		return enfants.get(index);
	}

	public Grille getEtat() {
		return etat;
	}

	public int getJoueur() {
		return joueur;
	}

	public boolean estFinal() {
		return etat.estFinal(joueur);
	}

	public int nbEnfants() {
		return enfants.size();
	}

	public Noeud getParent() {
		return parent;
	}

	public Noeud getPlusGrandeBValeur() {
		Noeud noeudSelectionne = null;

		float bValeurMax = Float.NEGATIVE_INFINITY;
		float bValeurEnfantCourant;

		for (Noeud n : enfants) {
			float a = (n.getNbSimulations()==0 || n.getNbVictoires()==0) ? 0 : (float)n.getNbVictoires() / (float)n.getNbSimulations();
			if (joueur == 2) {
				a *= -1;
			}

			float b = (n.getNbSimulations()==0 || nb_simulations==0) ? 0 : (float)((Math.sqrt(2)*Math.sqrt(Math.log(nb_simulations) / n.getNbSimulations())));

			bValeurEnfantCourant = a + b;

			if (bValeurEnfantCourant > bValeurMax) {
				bValeurMax = bValeurEnfantCourant;
				noeudSelectionne = n;
			}			
		}

		return noeudSelectionne;
	}

	public Noeud getProchainNoeudADevelopper() {
		Noeud res = null;

		if (enfants.size() == 0) {
			// getSuccesseurs nécessite le joueur actuel qui doit jouer, donc on récupère le joueur qui avait jouer pour arriver au parent
			for (Grille g : etat.getSuccesseurs(((joueur-1)^1)+1)) { // <------- CHANGEMENT ICI,
																		// on veut les successeurs du joueur 
																		// qui doit jouer maintenant, pas de celui qui a joué
																		// pour arriver à cet état
				ajouterEnfant(new Noeud(this, g));
			}
		}

		ArrayList<Noeud> enfantsPasDeveloppes = new ArrayList<>();
		for (Noeud n : enfants) {
			if (n.getNbSimulations() == 0)
				enfantsPasDeveloppes.add(n);
		}

		if (enfantsPasDeveloppes.size() == 0)
			res = enfants.get(random.nextInt(enfants.size()));
		else
			res = enfantsPasDeveloppes.get(random.nextInt(enfantsPasDeveloppes.size()));

		return res;
	}

	public Noeud getEnfantAleatoire() {
		if (enfants.size() == 0) {
			// getSuccesseurs nécessite le joueur actuel qui doit jouer, donc on récupère le joueur qui avait jouer pour arriver au parent
			for (Grille g : etat.getSuccesseurs(((joueur-1)^1)+1)) { // <--- CHANGEMENT ICI, on veut les successeurs du joueur 
																		// qui doit jouer maintenant, pas de celui qui a joué
																		// pour arriver à cet état
				ajouterEnfant(new Noeud(this, g));
			}
		}


		return enfants.get(random.nextInt(enfants.size()));
	}

	public boolean estRacine() {
		return parent == null;
	}

	public void augmenterNbVictoires(int res) {
		nb_victoires += res;
	}

	public void augmenterNbSimulations() {
		nb_simulations++;
	}

	public int getCoup() {
		return coup;
	}

	public int getNbSimulations() {
		return nb_simulations;
	}

	public int getNbVictoires() {
		return nb_victoires;
	}

	public int getNbEnfants() {
		return enfants.size();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("Caractéristiques : \n");
		builder.append("Joueur ayant joué avant : " + joueur + ", coup ayant été joué avant : " + coup + ", nb de victoires : " + nb_victoires
			 + ", nb de simulations : " + nb_simulations + ", nb d'enfants : " + enfants.size() + "\n");

		if (parent != null) 
			builder.append("Fils\n");
		else 
			builder.append("Racine\n");

		builder.append("Grille actuelle : \n" + etat.toString() + "\n");

		return builder.toString();
	}
}