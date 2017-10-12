public class Game{
	private Grid grilleActuel;
	private int joueurActuel;
	private int emplacementDernierePiece = -1;
	private int nbDePieces = 0;

	public Game(){
		grilleActuel=new Grid();
		joueurActuel=1;
	}

	public int getJoueurActuel() {
		return joueurActuel;
	}

	@Override
	public String toString(){
		return grilleActuel.toString();
	}

	public int jouerCoup(int colonne){
		emplacementDernierePiece = this.grilleActuel.jouerCoup(joueurActuel,colonne);

		if (emplacementDernierePiece != -1) {
			nbDePieces++;
		}

		return emplacementDernierePiece;
	}

	public void changementJoueur() {
		this.joueurActuel=((joueurActuel-1)^1)+1;
	}

	public int joueurGagnant() {
		if (nbDePieces==(Grid.getNbColonnes()*Grid.getNbLignes()))
			return 0;

		return joueurActuel;
	}

	public boolean estFinal() {
		boolean res = false;

		if (nbDePieces > 6) { // au moins 7 cases ont jouées (ex : le premier joueur gagne au bout de son 4ème coup, cela fait donc 7 coups)
			res = grilleActuel.estFinal(joueurActuel, emplacementDernierePiece);
		}

		return res;
	}

}