import java.lang.StringBuilder;

public class Grid {
	final private static int nbColonnes = 7;
	final private static int nbLignes = 6;


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
		int i = 0;
		//on parcours les cellules vide de la grille pour recuperer l'indice de de la première case libre de la colonne joué
		while (i<=nbLignes-1 && pieces[colonne+(i*nbColonnes)] == 0) {
			i++;
		}
		if(i>0){
			pieces[colonne+((i-1)*nbColonnes)] = joueur;	
		}
		
		return colonne+((i-1)*nbColonnes);
	}

	public boolean estFinal(int joueur, int empCase){
		return analyseVerticale(joueur, empCase);
	}

	public boolean analyseVerticale(int joueur, int empCase){
		int caseCourante = empCase+nbColonnes;
		int cpt = 1;

		if ((nbLignes-1)-(empCase / nbColonnes) > 2) {
			while (cpt < 4 && pieces[caseCourante] == joueur) {
				cpt++;
				caseCourante += nbColonnes;
			}
		}
		return cpt==4;

	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		for(int i=0;i<this.pieces.length;i++){

			builder.append(""+pieces[i]);
			if((i+1)%nbColonnes==0){
				builder.append("\n");
			}
		}
		return builder.toString();
	}

}