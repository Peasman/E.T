package model;

import java.time.LocalDateTime;

/**
 * Klasse zur Repräsentation von Tagebucheinträgen
 * 
 * @author Moritz Ludolph
 */

public abstract class DiaryEntry {

	/**
	 * Zeit des Tagebucheintrags
	 */
	protected LocalDateTime time;

	/**
	 * Menge des zu sich genommenen Essens, Medikamentes etc.
	 */
	protected double amount;

	/**
	 * Einheit der Menge
	 * 
	 * @see model.Unit
	 */
	protected Unit unit;

	/**
	 * Kommentar zum Eintrag
	 */
	protected String comment;

	/**
	 * Gibt das Datum und Zeit des Eintrages zurück
	 * 
	 * @return Datum und Zeit als LocalDate
	 */
	public LocalDateTime getTime() {
		return time;
	}

	/**
	 * Setzt das Datum und die Zeit des Eintrages auf <i>time</i>
	 * 
	 * @param time
	 *            Die neue Zeit & Datum als LocalDateTime
	 */
	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	/**
	 * Gibt die Menge des Tagebucheintrags zurück
	 * 
	 * @return Menge als double
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * Setzt die Menge des Tagebucheintrags auf <i>amount</i>
	 * 
	 * @param amount
	 *            Die neue Menges
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * Gibt die Einheit der Menge des Eintrages zurück
	 * 
	 * @return Gibt die Einheit als Unit-Enum zurück
	 * @see model.Unit
	 */
	public Unit getUnit() {
		return unit;
	}

	/**
	 * Setzt die Einheit der Menge auf <i>unit</i>
	 * 
	 * @param unit
	 *            Die neue Einheit
	 * @see model.Unit
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	/**
	 * Gibt den Kommentar des Tagebucheintrages zurück
	 * 
	 * @return Der Kommentar des Tagebucheintrages als String
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Setzt den Kommentar des Tagebucheintrages auf <i>comment</i>
	 * 
	 * @param comment
	 *            Der neue Kommentar
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	public abstract String getName(); 
	/**
	 * Leerer Konstruktor für GSON
	 */
	public DiaryEntry() {

	}

	/**
	 * Erzeugt ein neues DiaryEntry-Objekt.
	 * 
	 * @param time
	 *            Das Datum und Uhrzeit des neuen Eintrages
	 * @param amount
	 *            Die Menge des Eintrages
	 * @param unit
	 *            Die Einheit der Menge
	 * @param comment
	 *            Der Kommentar der zum Eintrag hinzugefügt wird
	 */
	public DiaryEntry(LocalDateTime time, double amount, Unit unit, String comment) {
		this.time = time;
		this.amount = amount;
		this.unit = unit;
		this.comment = comment;
	}

}
