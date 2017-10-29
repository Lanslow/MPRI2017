import java.lang.StringBuilder;
import java.lang.Math;
import java.util.ArrayList;

public class Grille {
	final private static int NBCOLONNES = 7;
	final private static int NBLIGNES = 6;

	private int nbDePieces = 0; // <----------- CHANGEMENT ICI, nbDePieces était dans la classe Jeu avant
											// Maintenant elle est ici pour estFinal() (voir changement estFinal())
	private int emplacementDernierePiece = -1;

	public static int getNbColonnes() {
		return NBCOLONNES;
	}

	public static int getNbLignes() {
		return NBLIGNES;
	}


	//pieces est le tableau représentant la grille de jeu 
	//0 vide
	//1 joueur 1 jaune
	//2 joueur 2 rouge
	private int[] pieces = new int[NBCOLONNES*NBLIGNES];
	

	public Grille() {
		for (int i =0;i<this.pieces.length;i++){
			this.pieces[i]=0;					//la grille de départ est vide
		}
	}

	public Grille(Grille g) {
		for (int i = 0; i<this.pieces.length; i++) {
			this.pieces[i] = g.getPiece(i);
		}
		emplacementDernierePiece = g.getEmplacementDernierePiece();
		nbDePieces = g.getNbDePieces();
	}

	public int getPiece(int i) {
		return pieces[i];
	}

	public void setEmplacementDernierePiece(int empPiece) {
		emplacementDernierePiece = empPiece;
	}

	public int getEmplacementDernierePiece() {
		return emplacementDernierePiece;
	}

	public int getNbDePieces() {
		return nbDePieces;
	}
	
	/**
	* joueur est le joueur qui joue ( 1 ou 2 )
	* colonne est l'entier qui représente la colonne joué (1,2,3,4,5,6,7) - 1 (pour le tableau)
	*/
	public int jouerCoup(int joueur, int colonne){
		int valeur = -1;
		int i = 0;
		//on parcours les cellules vide de la grille pour recuperer l'indice de de la première case libre de la colonne joué
		while (i<=NBLIGNES-1 && pieces[colonne+(i*NBCOLONNES)] == 0) {
			i++;
		}
		if(i>0){ // on place la piece à la ligne qui précéde i, donc i au moins 1
			valeur = colonne+((i-1)*NBCOLONNES);
			pieces[valeur] = joueur;
			emplacementDernierePiece = valeur;
			nbDePieces++;
		}
		
		return valeur;
	}

	/**
	* emplacementDernierePiece permet de ne regarder que autour du dernier jeton joué, au lieu de toute la grille
	*/
	public boolean estFinal(int joueur){ // <------- CHANGEMENT ICI, il faut vérifier s'il y a égalité 
												// -> Aucun joueur n'a aligné 4 pièces, et il ne reste plus de place sur
												// le plateau
		return nbDePieces==NBLIGNES*NBCOLONNES || analyseVerticale(joueur) || analyseHorizontale(joueur) || analyseDiagonaleDescendante(joueur) || analyseDiagonaleMontante(joueur);
	}
	public boolean analyseVerticale(int joueur){
		int caseCourante = emplacementDernierePiece+NBCOLONNES;
		int cpt = 1;

		if ((NBLIGNES-1)-(emplacementDernierePiece / NBCOLONNES) > 2) { // il faut qu'il y ait au moins 2 cases qui séparent la
														// dernière case jouée de la dernière ligne
			while (cpt < 4 && pieces[caseCourante] == joueur) { // tant qu'on a pas atteint un compteur de 4
																// ou que la case courante est une pièce du joueur courant
				cpt++;
				caseCourante += NBCOLONNES;
			}
		}
		return cpt==4;

	}

	public boolean analyseHorizontale(int joueur){
		int ligne =emplacementDernierePiece/NBCOLONNES;
		
		int caseCourante=ligne*NBCOLONNES; 				// on initialise la case courante à la première case de la ligne de la case joué
		int cpt=0;										//le compteur de jeton est initilisé à 0
		while(caseCourante<NBCOLONNES*(ligne+1) && (cpt < 4) ){	// tant que l'indice de la case courante est infèrieur à l"indice de la derniere case de la ligne
			if(pieces[caseCourante] == joueur){					//si la case courante à été joué par le joueur qui vient de jouer on incrémente le compteur
				cpt++;
			}else{												//sinon on le réinitialise
				cpt=0;
			}
			
			caseCourante += 1;							
		}

		return cpt==4;
	}

	public boolean analyseDiagonaleDescendante(int joueur) {
		int cpt = 1;

		// Cases accessibles en diagonale en allant vers le haut-gauche
		// et vers le bas-droit

		// Minimum entre le nombre de lignes restantes (vers le bas ou vers le haut)
		// et le nombre de colonnes restantes (vers la gauche ou vers la droite)
		int nbDiagonalesGaucheAFaire = Math.min(emplacementDernierePiece/NBCOLONNES, emplacementDernierePiece%NBCOLONNES); 
		int nbDiagonalesDroiteAFaire = Math.min(NBLIGNES-(emplacementDernierePiece/NBCOLONNES)-1, NBCOLONNES-(emplacementDernierePiece%NBCOLONNES)-1); 

		boolean continuerParcoursGauche = true, continuerParcoursDroite = true;
		int caseCouranteGauche = emplacementDernierePiece, caseCouranteDroite = emplacementDernierePiece;

		while (cpt < 4 && (continuerParcoursGauche || continuerParcoursDroite)) { // Il est possible qu'on arrête un parcours diagonale
																			// si on croise la pièce du joueur adversaire
			caseCouranteGauche -= NBCOLONNES+1; // case suivante diagonale haut-gauche
			caseCouranteDroite += NBCOLONNES+1; // case suivante diagonale bas-droit

			// -------------->  CHANGEMENT ICI, c'était caseCouranteGauche -= NBCOLONNES-1,
			// ce qui fait caseCouranteGauche = caseCouranteGauche - 6 -> faux

			if (continuerParcoursGauche) {
				if (nbDiagonalesGaucheAFaire <= 0) { // Il n'y a plus de case en diagonale haut-gauche possible
					continuerParcoursGauche = false;
				} else {
					if (pieces[caseCouranteGauche] != joueur) { // la case est adverse ou vide
						continuerParcoursGauche = false;
					} else {
						nbDiagonalesGaucheAFaire--;
						cpt++;

					}
				}
			}

			if (continuerParcoursDroite) {
				if (nbDiagonalesDroiteAFaire <= 0) { // Il n'y a plus de case en diagonale bas-droit possible
					continuerParcoursDroite = false;
				} else {
					if (pieces[caseCouranteDroite] != joueur) { // la case est adverse ou vide
						continuerParcoursDroite = false;
					} else {
						nbDiagonalesDroiteAFaire--;
						cpt++;
			
					}
				}
			}
		}

		return cpt==4;
	}

		public boolean analyseDiagonaleMontante(int joueur) {
		int cpt = 1;

		// Cases accessibles en diagonale en allant vers le haut-gauche
		// et vers le bas-droit

		// Minimum entre le nombre de lignes restantes (vers le bas ou vers le haut)
		// et le nombre de colonnes restantes (vers la gauche ou vers la droite)
		int nbDiagonalesDroiteAFaire = Math.min(emplacementDernierePiece/NBCOLONNES, NBCOLONNES-(emplacementDernierePiece%NBCOLONNES)-1); 
		int nbDiagonalesGaucheAFaire = Math.min(NBLIGNES-(emplacementDernierePiece/NBCOLONNES)-1, emplacementDernierePiece%NBCOLONNES); 

		boolean continuerParcoursGauche = true, continuerParcoursDroite = true;
		int caseCouranteGauche = emplacementDernierePiece, caseCouranteDroite = emplacementDernierePiece;

		while (cpt < 4 && (continuerParcoursGauche || continuerParcoursDroite)) { // Il est possible qu'on arrête un parcours diagonale
																			// si on croise la pièce du joueur adversaire
			caseCouranteGauche += NBCOLONNES-1; // case suivante diagonale bas-gauche
			caseCouranteDroite -= NBCOLONNES-1; // case suivante diagonale haut-droit

			// ------------> CHANGEMENT ICI, c'était caseCouranteDroite += NBCOLONNES+1, 
			// ce qui fait caseCouranteDroite = caseCouranteDroite + 8 -> faux

			if (continuerParcoursGauche) {
				if (nbDiagonalesGaucheAFaire <= 0) { // Il n'y a plus de case en diagonale haut-gauche possible
					continuerParcoursGauche = false;
				} else {
					if (pieces[caseCouranteGauche] != joueur) { // la case est adverse ou vide
						continuerParcoursGauche = false;
					} else {
						nbDiagonalesGaucheAFaire--;
						cpt++;

					}
				}
			}

			if (continuerParcoursDroite) {
				if (nbDiagonalesDroiteAFaire <= 0) { // Il n'y a plus de case en diagonale bas-droit possible
					continuerParcoursDroite = false;
				} else {
					if (pieces[caseCouranteDroite] != joueur) { // la case est adverse ou vide
						continuerParcoursDroite = false;
					} else {
						nbDiagonalesDroiteAFaire--;
						cpt++;
			
					}
				}
			}
		}

		return cpt==4;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		for(int i=0;i<this.pieces.length;i++){

			builder.append(""+pieces[i]);
			if((i+1)%NBCOLONNES==0){
				builder.append("\n");
			}
		}
		return builder.toString();
	}

	public ArrayList<Grille> getSuccesseurs(int joueur) {
		ArrayList<Grille> list = new ArrayList<>();

		for (int i=0; i<NBCOLONNES; i++) {
			Grille newGrille = new Grille(this);
			int derniereCaseJouee = newGrille.jouerCoup(joueur, i);
			if (derniereCaseJouee != -1) {
				list.add(newGrille);
			}
		}

		return list;
	}

}