package model;

import java.time.LocalDateTime;

/**
 * Klasse zur Repräsentation eines Eintrages im Tagebuch bezüglich sonstiger
 * Faktoren, wie Stress, Erkältungen etc...
 * 
 * @author Moritz Ludolph
 */
public class FactorEntry extends DiaryEntry {

	/**
	 * Die Beschreibung des Faktors
	 */
	private String description;
	
	/**
	 * Der Name des Faktors
	 */
	private String name;

	
	/**
	 * Gibt den Namen des Faktors zurück
	 * @return Der Name als String
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Setzt den Namen des Faktors
	 * @param name Der neue Name des Faktors
	 */
	public void setName(String name){
		this.name = name;
	}
	
	
	/**
	 * Gibt die Beschreibung des aufgetretenen Faktors zurück
	 * 
	 * @return Die Beschreibung als String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Setzt die Beschreibung des Eintrages auf <i>description</i>
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Erzeugt ein neues FactorEntry-Objekt.
	 * 
	 * @param time
	 *            Das Datum und Uhrzeit des neuen Eintrages
	 * @param amount
	 *            Die Menge des Eintrages
	 * @param unit
	 *            Die Einheit der Menge
	 * @param comment
	 *            Der Kommentar der zum Eintrag hinzugefügt wird
	 * @param description
	 *            Eine Beschreibung des Faktors
	 */
	public FactorEntry(LocalDateTime time, String name, String comment, String description) {
		super(time, 0.0, Unit.VOID, comment);
		this.setName(name);
		this.setDescription(description);
	}
}
