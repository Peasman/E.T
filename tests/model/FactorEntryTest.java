package model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Test;
/**
 * Testet den FactorEntryTest
 * @author Friedemann Runte
 *
 */
public class FactorEntryTest {
	/**
	 * Überprüft ob alle Daten beim initialisieren eines FactorEntry's richtig gesetzt werden.
	 */
	@Test
	public void test() {
		FactorEntry factorEntry = new FactorEntry(LocalDateTime.of(2017, 4, 1, 9, 0, 0), "Name", "Fieber","harte grippe");
		assertEquals(factorEntry.getTime(), LocalDateTime.of(2017, 4, 1, 9, 0, 0));
		assertEquals(factorEntry.getName(), "Name");
		assertEquals(factorEntry.getComment(),"Fieber");
		assertEquals(factorEntry.getDescription(),"harte grippe");
	}

}
