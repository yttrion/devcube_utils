import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class Test {

	/**
	 * Vérifie que le résultat donné d'une expression booléenne est vraie et
	 * affiche dans la console le bilan de cette vérification.
	 * @param result le résultat d'une expression booléenne à vérifier
	 * @param description la description du test
	 */
	static void assertTrue(boolean result, String description) {
		try {
			throw new AssertionError(description);
		}
		catch (AssertionError error) {
			printResult(result, description);
		}
	}

	/**
	 * Vérifie que le résultat donné d'une expression booléenne est faux et
	 * affiche dans la console le bilan de cette vérification.
	 * @param result le résultat d'une expression booléenne à vérifier
	 * @param description la description du test
	 */
	static void assertFalse(boolean result, String description) {
		try {
			throw new AssertionError(description);
		}
		catch (AssertionError error) {
			printResult(!result, description);
		}
	}

	/**
	 * Vérifie que le résultat obtenu est égal au résultat attendu et affiche
	 * dans la console le bilan de cette vérification.
	 * 
	 * Le test d'égalité utilise la méthode "egale" si elle est définie dans la
	 * classe dont les deux résultats sont instances, ou la méthode "equals"
	 * sinon.
	 * 
	 * L'affichage utilise la méthode "versChaine" si elle est définie dans la
	 * classe dont les deux résultats sont instances, ou la méthode "toString"
	 * sinon.
	 * 
	 * @param result le résultat obtenu
	 * @param expectedResult le résultat attendu
	 * @param description la description du test
	 */
	static void assertEquals(Object result, Object expectedResult, String description) {
		try {
			throw new AssertionError(description);
		}
		catch (AssertionError error) {
			// case when both result and expected result are null
			boolean passed = (result == null && expectedResult == null) || ((result != null) && (expectedResult != null) && equals(result, expectedResult));
			if (!passed) {
				description = description + " (result is " + toString(result) + " but " + toString(expectedResult) + " is expected)";
			}
			printResult(passed, description);
		}
	}

	/**
	 * Vérifie que l'exécution d'une bloc d'instructions conduit à une erreur et
	 * affiche dans la console le bilan de cette vérification.
	 * @param runnable le bloc d'instructions à exécuter
	 * @param description la description du test
	 */
	static void assertError(Runnable runnable, String description) {
		try {
			runnable.run();
			printResult(false, description);
		}
		catch (Error error) {
			printResult(true, description);
		}
	}

	// ====================================================
	// | Méthodes internes volontairement non documentées |
	// ====================================================

	static boolean equals(Object object1, Object object2) {
		try {
			Class<?>[] parameterTypes = { object2.getClass() };
			Method egale = object1.getClass().getDeclaredMethod("egale", parameterTypes);
			return (Boolean) egale.invoke(object1, object2);
		}
		catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassCastException e) {
			return object1.equals(object2);
		}
	}

	static String toString(Object object) {
		try {
			Method versChaine = object.getClass().getDeclaredMethod("versChaine", new Class<?>[0]);
			return (String) versChaine.invoke(object);
		}
		catch (NullPointerException npe) {
			return "null";
		}
		catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassCastException e) {
			return object.toString();
		}
	}

	static int testCount = 0;

	static void printResult(boolean passed, String message) {
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		int index = stack.length - 1;
		int i = 0;
		boolean found = false;
		while (i < stack.length - 1 && !found) {
			if (stack[i].getMethodName().startsWith("assert")) {
				index = i + 1;
				found = true;
			}
			else {
				i++;
			}
		}
		StackTraceElement trace = stack[index];
		System.out.print(trace.getFileName());
		System.out.print(":");
		System.out.print(trace.getLineNumber());
		System.out.print(": ");
		System.out.print((passed) ? "passed" : "error");
		System.out.print(": ");
		System.out.println(message);
		testCount++;
	}

}
