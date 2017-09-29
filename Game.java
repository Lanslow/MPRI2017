public class Game{
	private Grid grilleActuel;
	private int joueurActuel;
	private int emplacementDernierePiece = -1;

	public Game(){
		grilleActuel=new Grid();
		joueurActuel=1;
	}

	public String toString(){
		return grilleActuel.toString();
	}

	public void jouerCoup(int colonne){
		emplacementDernierePiece = this.grilleActuel.jouerCoup(joueurActuel,colonne);
	}

	public boolean estFinal() {
		boolean res = false;

		if (emplacementDernierePiece != -1) {
			res = grilleActuel.estFinal(joueurActuel, emplacementDernierePiece);

			if (!res) 
				this.joueurActuel=((joueurActuel-1)^1)+1;
		}

		return res;
	}

}