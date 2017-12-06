package model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Test;
/**
 * Testet den DrugEntry
 * @author Friedemann Runte
 *
 */
public class DrugEntryTest {
	/**
	 * Testet den Konstruktor für die Drug-Klasse, übergibt Test-Werte und überprüft die dann eingespeicherten Werte.
	 */
	@Test
	public void test() {
		DrugEntry drugEntry = new DrugEntry(LocalDateTime.of(2017, 4, 1, 9, 0, 0),5,Unit.PIECES,"Ich mag paracetamol sehr gerne", new Drug("Paracetamol"));
		assertEquals(drugEntry.getTime(), LocalDateTime.of(2017, 4, 1, 9, 0, 0));
		assertEquals(drugEntry.getAmount(),5.0,0);
		assertEquals(drugEntry.getComment(),"Ich mag paracetamol sehr gerne");
		assertEquals(drugEntry.getDrug(), new Drug("Paracetamol"));
		assertEquals(drugEntry.getUnit(),Unit.PIECES);
	}

}
