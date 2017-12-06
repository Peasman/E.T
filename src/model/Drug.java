package model;

import java.util.ArrayList;

/**
 * Die Modellklasse für ein Medikament mit allen Gettern und Settern
 * @author Olga Scheftelowitsch
 *
 */
public class Drug {

	/**
	 * Name des Medikaments
	 */
	private String name;

	/**
	 * Liste der Gesundheitsprobleme
	 */
	private ArrayList<HealthProblem> healthProblemList;
	
	/**
	 * Beschreibung des Medikaments
	 */
	private String description;

	/**
	 * Gibt die Beschreibung zurück
	 * @return decription: Beschreibung des Medikaments
	 * 
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * setzt die Beschreibung
	 * @param description
	 * 					Übergebe neue Beschreibung
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * gibt den Namen zurück
	 * @return der Name des Medikaments
	 */
	public String getName() {
		return name;
	}

	/**
	 * setzt den namen
	 * @param name 
	 * 				Name des Medikaments
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Liste der aktuellen Gesundheitsprobleme
	 * gibt die Liste der Potentiellen Gesundheitsprobleme zurück
	 */
	public ArrayList<HealthProblem> getHealthProblemList() {
		return healthProblemList;
	}
	
	
	/**
	 * setzt die Liste der Potentiellen Gesundheitsprobleme
	 * @param healthProblemList
	 * 							eine neue Liste an Gesundheitsproblemen
	 */
	public void setHealthProblemList(ArrayList<HealthProblem> healthProblemList) {
		this.healthProblemList = healthProblemList;
	}

	
	/**
	 * Konstruktor mit Name
	 * @param name
	 * 				Name des neuen Medikaments
	 */
	public Drug(String name){
		this.name = name;
		healthProblemList = new ArrayList<HealthProblem>();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((healthProblemList == null) ? 0 : healthProblemList.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Drug other = (Drug) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name)){
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return name;
	}
}
