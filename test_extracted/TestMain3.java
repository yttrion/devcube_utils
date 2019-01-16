public class TestMain3 {

	public static void main(String[] args) {
		Graphe grapheDiametre = new Graphe("Graphe pour tester Floyd et calculer le diametre");
		
		Test.assertTrue(grapheDiametre.diametreGraphe()==3,"On doit obtenir 3 pour le diametre");
		
	}	

}
