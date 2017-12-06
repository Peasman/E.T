package model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Die Klasse representiert einen Inhaltsstoff wie zB. Lactose, Histamin etc
 * 
 * @author Friedemann Runte
 *
 */
public class Ingredient {

	/**
	 * Der Name des Inhaltsstoffes
	 */
	private String name;

	/**
	 * Informationen zum Inhaltsstoff
	 */
	private String information;

	/**
	 * Die List der HealtchProblems die das Ingredient verursachen kann
	 */
	private Collection<HealthProblem> healthProblemList;

	/**
	 * 
	 *
	 * Der Konstruktor der einen Inhaltsstoff erstellt
	 * 
	 * @param name
	 *            Der Name des Inhalstoffes
	 * @param information
	 *            Informationen zu dem Inhaltsstoff (bspw. Unveträglichkeiten)
	 */
	public Ingredient(String name, String information) {
		this.name = name;
		this.information = information;
		healthProblemList = new ArrayList<HealthProblem>();
	}

	/**
	 * Überladener Konstruktor zum hinzufügen von HealthProblems beim erstellen
	 * 
	 * @param name
	 *            s.o.
	 * @param information
	 *            s.o.
	 * @param hpList
	 *            Die List der HealthProblems
	 */
	public Ingredient(String name, String information, Collection<HealthProblem> hpList) {
		this.name = name;
		this.information = information;
		healthProblemList = hpList;
	}

	/**
	 * Überschreibt die hash Methode um Ingredients nur nach
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ingredient other = (Ingredient) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/**
	 * Gibt den Namen zurück
	 * 
	 * @return name 
	 * 			der Name des Inhaltsstoff als String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Überschreibt den Namen des Inhaltsstoffes
	 * 
	 * @param name
	 * 			der Name der eingesetzt werden soll
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gibt die Informationen des Inhaltstoffes zurück
	 * 
	 * @return Information 
	 * 				Informationen über den Inhaltsstoff
	 */
	public String getInformation() {
		return information;
	}

	/**
	 * Überschreibt die Informationen
	 * 
	 * @param information
	 *            zum überschreiben
	 */
	public void setInformation(String information) {
		this.information = information;
	}

	/**
	 * Gibt alle mit dem Inhaltsstoff verbundenen HealthProblems zurück
	 * 
	 * @return die HealthProblems als Liste
	 */
	public Collection<HealthProblem> getHealthProblemList() {
		return healthProblemList;
	}

	/**
	 * Überschreibt die HealthProblemList
	 * 
	 * @param healthProblemList
	 *            zum überschreiben
	 */
	public void setHealthProblemList(Collection<HealthProblem> healthProblemList) {
		this.healthProblemList = healthProblemList;
	}

	@Override
	public String toString() {
		return name;
	}

}
