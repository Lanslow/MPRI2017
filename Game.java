public class Game{
	private Grid grilleActuel;
	private int joueurActuel;
	private int emplacementDernierePiece = -1;
	private int nbDePieces = 0;

	public Game(){
		grilleActuel=new Grid();
		joueurActuel=1;
	}

	public String toString(){
		return grilleActuel.toString();
	}

	public void jouerCoup(int colonne){
		emplacementDernierePiece = this.grilleActuel.jouerCoup(joueurActuel,colonne);
		nbDePieces++;
	}

	public boolean estFinal() {
		boolean res = false;

		if (nbDePieces > 7) { // au moins une case a été jouée
			res = grilleActuel.estFinal(joueurActuel, emplacementDernierePiece);

			if (!res)  // l'état n'est pas final, le joueur adversaire peut jouer
				this.joueurActuel=((joueurActuel-1)^1)+1;
		}

		return res;
	}

}