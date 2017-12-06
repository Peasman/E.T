package model;

import java.time.LocalDateTime;

/**
 * Klasse zur Repräsentation einer Eintrages einer Beschwerde im Tagebuch
 * 
 * @author Moritz Ludolph
 */
public class HealthProblemEntry extends DiaryEntry {

	/**
	 * Die aufgetretene Beschwerde
	 */
	private HealthProblem healthProblem;

	/**
	 * Gibt die aufgetretene Beschwerde des Eintrages zurück
	 * 
	 * @return Die Beschwerde als HealthProblem-Objekt
	 * @see model.HealthProblem
	 */
	public HealthProblem getHealthProblem() {
		return healthProblem;
	}

	/**
	 * Setzt die aufgetretene Beschwerde des Eintrages auf <i>healthProblem</i>
	 * 
	 * @param healthProblem
	 *            Die neue Beschwerde als HealthProblem-Objekt
	 * @see model.HealthProblem
	 */
	public void setHealthProblem(HealthProblem healthProblem) {
		this.healthProblem = healthProblem;
	}

	public String getName() {
		return healthProblem.getName();
	}
	/**
	 * Erzeugt ein neues HealthproblemEntry-Objekt.
	 * 
	 * @param time
	 *            Das Datum und Uhrzeit des neuen Eintrages
	 * @param amount
	 *            Die Menge des Eintrages
	 * @param unit
	 *            Die Einheit der Menge
	 * @param comment
	 *            Der Kommentar der zum Eintrag hinzugefügt wird
	 * @param HealthProblem
	 *            Die aufgetretene Beschwerde als HealthProblem-Objekt
	 * @see model.HealthProblem
	 */
	public HealthProblemEntry(LocalDateTime time, double duration, String comment, HealthProblem healthProblem) {
		super(time, duration, Unit.MINUTES, comment);
		this.setHealthProblem(healthProblem);
	}

}
