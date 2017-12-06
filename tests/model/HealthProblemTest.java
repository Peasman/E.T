package model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Klasse testet die Klasse HealthProblem
 * @author Olga Scheftelowitsch
 *
 */
public class HealthProblemTest {
	 /**
	 * Objekt der Klasse HealthProblem, was beim setUp erstellt wird
	 */
	private HealthProblem health;
	 
	
	/**
	 * erstellt im setUp ein Gesundheitsproblem					
	 */
	@Before
    public void setUp(){
        health =  new HealthProblem("Stress","no good");
    }
	
	/**
	 * prüft ob der Konstruktor funktioniert
	 */
	@Test
	public void testHealthProblem() {
				
		assertEquals("private Attribute müssen richtig gesetzt sein", "Stress", health.getName());
		assertEquals("private Attribute müssen richtig gesetzt sein", "no good", health.getDescription());
	}

}
