package model;

import java.time.LocalDateTime;

/**
 * Klasse zur Repräsentation eines Nahrungseinnahmeeintrages im Tagebuch
 * 
 * @author Moritz Ludolph
 */
public class FoodEntry extends DiaryEntry {

	/**
	 * Das eingenommene Nahrungsmittel
	 */
	private Food food;

	/**
	 * Gibt das eingenommene Nahrungsmittel des Eintrages zurück
	 * 
	 * @return Das Nahrungsmittel als Food-Objekt
	 * @see model.Food
	 */
	public Food getFood() {
		return food;
	}

	/**
	 * Setzt das eingenommene Nahrungsmittel des Eintrages auf <i>food</i>
	 * 
	 * @param food
	 *            Das Nahrungsmittel
	 * @see model.Food
	 */
	public void setFood(Food food) {
		this.food = food;
	}

	public String getName() {
		return food.getName();
	}
	/**
	 * Erzeugt ein neues FoodEntry-Objekt.
	 * 
	 * @param time
	 *            Das Datum und Uhrzeit des neuen Eintrages
	 * @param amount
	 *            Die Menge des Eintrages
	 * @param unit
	 *            Die Einheit der Menge
	 * @param comment
	 *            Der Kommentar der zum Eintrag hinzugefügt wird
	 * @param food
	 *            Das Nahrungsmittel
	 * @see model.Food
	 */
	public FoodEntry(LocalDateTime time, double amount, Unit unit, String comment, Food food) {
		super(time, amount, unit, comment);
		this.setFood(food);
	}


}
