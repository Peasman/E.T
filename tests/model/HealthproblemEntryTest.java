package model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Test;
/**
 * Testet HEalthProblemEntry
 * @author Friedemann Runte
 *
 */
public class HealthproblemEntryTest {
	/**
	 * Testet ob im Konstruktor alle Were richtig gesetzt werden.
	 */
	@Test
	public void test() {
		HealthProblemEntry healthProblem = new HealthProblemEntry(LocalDateTime.of(2017, 4, 1, 9, 0, 0), 5, "vielleicht laktose gegessen", new HealthProblem("Magenschmerzen","Stechen im Magen"));
		assertEquals(healthProblem.getTime(),LocalDateTime.of(2017, 4, 1, 9, 0, 0));
		assertEquals(healthProblem.getAmount(),5.0,0);
		assertEquals(healthProblem.getComment(),"vielleicht laktose gegessen");
		assertEquals(healthProblem.getHealthProblem(), new HealthProblem("Magenschmerzen","Stechen im Magen"));
		assertEquals(healthProblem.getUnit(),Unit.MINUTES);
	}

}
