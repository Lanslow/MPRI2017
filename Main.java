public class Main{
	public static void main(String[] args) {
		test2();
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

		System.out.println("J2 joue en colonne 3");
		g.jouerCoup(2);
		System.out.println(g.toString());

		System.out.println("J1 joue en colonne 4");
		g.jouerCoup(3);
		System.out.println(g.toString());

		System.out.println("J2 joue en colonne 2");
		g.jouerCoup(1);
		System.out.println(g.toString());

		System.out.println("J1 joue en colonne 4");
		g.jouerCoup(3);
		System.out.println(g.toString());

		System.out.println("J2 joue en colonne 2");
		g.jouerCoup(1);
		System.out.println(g.toString());

		System.out.println("J1 joue en colonne 4");
		g.jouerCoup(3);
		System.out.println(g.toString());

		System.out.println(g.estFinal());
	}
}