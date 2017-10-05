public class Main{
	public static void main(String[] args) {
		test3();
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
		if (!g.estFinal()) {
			g.changementJoueur();
		}

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
	public static void test3() {
		Game g = new Game();
		System.out.println("grille initial");
		System.out.println(g.toString());
		
		System.out.println("J1 joue en colonne 3");
		g.jouerCoup(2);
		System.out.println(g.toString());
		if (!g.estFinal()) {
			g.changementJoueur();
		}
		
		System.out.println("J2 joue en colonne 3");
		g.jouerCoup(2);
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
		
		System.out.println("J2 joue en colonne 3");
		g.jouerCoup(2);
		System.out.println(g.toString());
		if (!g.estFinal()) {
			g.changementJoueur();
		}
		
		System.out.println("J1 joue en colonne 5");
		g.jouerCoup(4);
		System.out.println(g.toString());
		if (!g.estFinal()) {
			g.changementJoueur();
		}
		
		System.out.println("J2 joue en colonne 1");
		g.jouerCoup(0);
		System.out.println(g.toString());
		if (!g.estFinal()) {
			g.changementJoueur();
		}
		
		System.out.println("J1 joue en colonne 6");
		g.jouerCoup(5);
		System.out.println(g.toString());
		if (!g.estFinal()) {
			g.changementJoueur();
		}else{
			System.out.println("terminé");
		}
		
	}
	
}