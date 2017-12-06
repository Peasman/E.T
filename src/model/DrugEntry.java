package model;

import java.time.LocalDateTime;

/**
 * Klasse zur Verwaltung einer Medikamenteneinnahme im Tagebuch.
 * 
 * @author Moritz Ludolph
 */
public class DrugEntry extends DiaryEntry {

	/**
	 * Das Medikament, das eingenommen wurde
	 */
	private Drug drug;

	/**
	 * Gibt das eingenommene Medikament zurück
	 * 
	 * @return Das Medikament als Drug
	 * @see model.Drug
	 */
	public Drug getDrug() {
		return drug;
	}

	/**
	 * Setzt des eingenomme Medikament des Tagebucheintrages auf <i>drug</i>
	 * 
	 * @param drug
	 *            Das eingenommene Medikament
	 * @see model.Drug
	 */
	public void setDrug(Drug drug) {
		this.drug = drug;
	}

	/**
	 * 062
	 */
	public String getName(){
		return drug.getName();
	}
	
	/**
	 * Erzeugt ein neues DrugEntry-Objekt.
	 * 
	 * @param time
	 *            Das Datum und Uhrzeit des neuen Eintrages
	 * @param amount
	 *            Die Menge des Eintrages
	 * @param unit
	 *            Die Einheit der Menge
	 * @param comment
	 *            Der Kommentar der zum Eintrag hinzugefügt wird
	 * @param drug
	 *            Das eingenommene Medikament
	 * @see model.Drug
	 */
	public DrugEntry(LocalDateTime time, double amount, Unit unit, String comment, Drug drug) {
		super(time, amount, unit, comment);
		this.setDrug(drug);
	}

}
