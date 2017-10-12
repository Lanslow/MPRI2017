import java.lang.StringBuilder;
import java.lang.Math;

public class Grid {
	final private static int NBCOLONNES = 7;
	final private static int NBLIGNES = 6;

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
	private int[] pieces = new int[42];
	

	public Grid() {
		for (int i =0;i<this.pieces.length;i++){
			this.pieces[i]=0;					//la grille de départ est vide
		}
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
			pieces[colonne+((i-1)*NBCOLONNES)] = joueur;
			valeur = colonne+((i-1)*NBCOLONNES);
		}
		
		return valeur;
	}

	/**
	* empCase permet de ne regarder que autour du dernier jeton joué, au lieu de toute la grille
	*/
	public boolean estFinal(int joueur, int empCase){
		return analyseVerticale(joueur, empCase) || analyseHorizontale(joueur, empCase) || analyseDiagonaleDescendante(joueur, empCase) || analyseDiagonaleMontante(joueur, empCase);
	}
	public boolean analyseVerticale(int joueur, int empCase){
		int caseCourante = empCase+NBCOLONNES;
		int cpt = 1;

		if ((NBLIGNES-1)-(empCase / NBCOLONNES) > 2) { // il faut qu'il y ait au moins 2 cases qui séparent la
														// dernière case jouée de la dernière ligne
			while (cpt < 4 && pieces[caseCourante] == joueur) { // tant qu'on a pas atteint un compteur de 4
																// ou que la case courante est une pièce du joueur courant
				cpt++;
				caseCourante += NBCOLONNES;
			}
		}
		return cpt==4;

	}

	public boolean analyseHorizontale(int joueur, int empCase){
		int ligne =empCase/NBCOLONNES;
		
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

	public boolean analyseDiagonaleDescendante(int joueur, int empCase) {
		int cpt = 1;

		// Cases accessibles en diagonale en allant vers le haut-gauche
		// et vers le bas-droit

		// Minimum entre le nombre de lignes restantes (vers le bas ou vers le haut)
		// et le nombre de colonnes restantes (vers la gauche ou vers la droite)
		int nbDiagonalesGaucheAFaire = Math.min(empCase/NBCOLONNES, empCase%NBCOLONNES); 
		int nbDiagonalesDroiteAFaire = Math.min(NBLIGNES-(empCase/NBCOLONNES)-1, NBCOLONNES-(empCase%NBCOLONNES)-1); 

		boolean continuerParcoursGauche = true, continuerParcoursDroite = true;
		int caseCouranteGauche = empCase, caseCouranteDroite = empCase;

		while (cpt < 4 && (continuerParcoursGauche || continuerParcoursDroite)) { // Il est possible qu'on arrête un parcours diagonale
																			// si on croise la pièce du joueur adversaire
			caseCouranteGauche -= NBCOLONNES-1; // case suivante diagonale haut-gauche
			caseCouranteDroite += NBCOLONNES+1; // case suivante diagonale bas-droit

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

		public boolean analyseDiagonaleMontante(int joueur, int empCase) {
		int cpt = 1;

		// Cases accessibles en diagonale en allant vers le haut-gauche
		// et vers le bas-droit

		// Minimum entre le nombre de lignes restantes (vers le bas ou vers le haut)
		// et le nombre de colonnes restantes (vers la gauche ou vers la droite)
		int nbDiagonalesDroiteAFaire = Math.min(empCase/NBCOLONNES, NBCOLONNES-(empCase%NBCOLONNES)-1); 
		int nbDiagonalesGaucheAFaire = Math.min(NBLIGNES-(empCase/NBCOLONNES)-1, empCase%NBCOLONNES); 

		boolean continuerParcoursGauche = true, continuerParcoursDroite = true;
		int caseCouranteGauche = empCase, caseCouranteDroite = empCase;

		while (cpt < 4 && (continuerParcoursGauche || continuerParcoursDroite)) { // Il est possible qu'on arrête un parcours diagonale
																			// si on croise la pièce du joueur adversaire
			caseCouranteGauche += NBCOLONNES-1; // case suivante diagonale bas-gauche
			caseCouranteDroite -= NBCOLONNES+1; // case suivante diagonale haut-droit

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

}