import java.lang.StringBuilder;

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
		return analyseVerticale(joueur, empCase);
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