import java.util.Scanner;

public class Main{
	public static void main(String[] args) {
		boucleDeJeu();
	}

	public static void  test1(){
		Game g=new Game();
		System.out.println("grille initial");
		System.out.println(g.toString());
		System.out.println("J1 jou en colonne 4");
		g.jouerCoup(3);
		System.out.println(g.toString());
		System.out.println("J2 jou en colonne 4");
		g.jouerCoup(3);
		System.out.println(g.toString());
		System.out.println("J1 jou en colonne 4");
		g.jouerCoup(3);
		System.out.println(g.toString());
	}

	public static void test2() {
		Game g = new Game();
		System.out.println("grille initial");
		System.out.println(g.toString());

		System.out.println("J1 joue en colonne 4");
		g.jouerCoup(3);
		System.out.println(g.toString());
		System.out.println(g.estFinal());

		System.out.println("J2 joue en colonne 4");
		g.jouerCoup(3);
		System.out.println(g.toString());
		if (!g.estFinal()) {
			g.changementJoueur();
		}

		System.out.println("J1 joue en colonne 4");
		g.jouerCoup(3);
		System.out.println(g.toString());
		if (!g.estFinal()) {
			g.changementJoueur();
		}

		System.out.println("J2 joue en colonne 2");
		g.jouerCoup(1);
		System.out.println(g.toString());
		if (!g.estFinal()) {
			g.changementJoueur();
		}

		System.out.println("J1 joue en colonne 4");
		g.jouerCoup(3);
		System.out.println(g.toString());
		if (!g.estFinal()) {
			g.changementJoueur();
		}

		System.out.println("J2 joue en colonne 2");
		g.jouerCoup(1);
		System.out.println(g.toString());
		if (!g.estFinal()) {
			g.changementJoueur();
		}

		System.out.println("J1 joue en colonne 4");
		g.jouerCoup(3);
		System.out.println(g.toString());
		if (!g.estFinal()) {
			g.changementJoueur();
		}
	}

	public static void boucleDeJeu() {
		Game g = new Game();
		boolean continuer = true /* Tant que l'état courant n'est pas final la boucle continue */ ,
				mauvaisCoup = true /* Tant que le joueur n'a pas joué à un endroit possible 
									ou a entré une mauvaise valeur de colonne, redemander une valeur */;
		Scanner input = new Scanner(System.in);

		while (continuer) {
			mauvaisCoup = true;

			while (mauvaisCoup) {
				System.out.println("Sur quelle colonne voulez-vous jouer?");
				int i = input.nextInt();
				if (i < 1 || i > 7) {
					System.out.println("Valeur de colonne invalide, la valeur doit être comprise entre 1 et 7");
				} else {
					if (g.jouerCoup(i-1) == -1) {
						System.out.println("Cette colonne est remplie et ne peut pas être jouée : choisissez une autre colonne");
					} else {
						System.out.println(g.toString());
						mauvaisCoup = false;
						if (g.estFinal()) {
							int gagnant = g.joueurGagnant();
							if (gagnant == 0) {
								System.out.println("Partie terminée : égalité !");
							} else {
								System.out.println("Partie terminée : le joueur " + gagnant + " remporte la partie");
							}

							continuer = false;
						} else {
							g.changementJoueur();
						}
					}
				}
			}
		}	
	}
}