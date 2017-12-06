package model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import model.Drug;

/**
 * Test für Drug-Klasse
 * @author Alexander Herlez
 */
public class DrugTest {

	/** 
	 * Testmedikament
	 */
private Drug testDrug;

	/** 
	 * Konstruiert Testumgebung
	 */
	@Before
	public void setUp() throws Exception {	
	}	
	
	// triviale getter und setter Methoden werden nicht überprüft
	
	
	/** 
	 * Testet ob der Konstruktor funktioniert
	 */
	@Test
	public void testDrug() {
		assertEquals("Drug sollte noch null sein", null, testDrug);
		testDrug = new Drug ("Hustenbonbon");
		assertEquals("name des Medikaments sollte Hustenbonbon sein", "Hustenbonbon", testDrug.getName());
		assertEquals("Medikament enthält leere Liste", 0, testDrug.getHealthProblemList().size());
	}

	/**
	 * Testet ob Medikamente mit gleichem Namen als gleiches Medikament erkannt werden.
	 */
	@Test
	public void testEquals() {
		Drug secondDrug = new Drug ("Hustenbonbon");
		testDrug = new Drug ("Hustenbonbon");
		assertEquals("Medikamente mit gleichem Namen sollten als gleich erkannt werden", true, testDrug.equals(secondDrug));
	}

}
