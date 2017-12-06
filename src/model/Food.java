package model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 		Allgemeine Klasse um die aufgenommene oder zum zubereiten verwendete Nahrung zu verwalten
 * 
 * 		@author Christian Walczuch
 *		
 */
public class Food {

    /** 
     * 
 	 * Der Name des Lebensmittels
 	 */
    private String name;

    /**
 	 * Es ist möglich ein Bild in die Beschreibung einzufügen
 	 */
    private String ingredientPicturePath;

    /**
 	 * Liste der schon eingetragenen verschiedenen Lebensmittel
 	 */
    private Collection<Food> ingredientsOfFoodList;

    /**
 	 * Da ein Nahrungsmittel aus verschieden Inhaltsstoffen besteht, kann man diese hier aufschlüsseln.
 	 * Ein Nahrungsmittel kann auch wieder Zutat für ein weiteres Nahrungsmittel sein.
 	 */
    private Collection<Ingredient> ingredients;

    /**
	 * Konstruktor eines Foods
 	 * @param name: Der Name des Nahrungsmittels
 	 * @param ingredientPicture: Möglichkeit Bild einzufügen
 	 */
    public Food(String name, String ingredientPicturePath){
        this.name = name;
        this.ingredientPicturePath = ingredientPicturePath;
        ingredientsOfFoodList = new ArrayList<Food>();
        ingredients = new ArrayList<Ingredient>();
    }
    
    /**
     * Holt den Namen des Lebensmittels
     * @return gibt den Namen des Lebensmittels zurück
     */
	public String getName() {
		return name;
	}
	
	/**
	 * Setzt den Namen des Lebensmittels
	 * @param name: Name zum setzen
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Holt das evtl eingefügte Bild
	 * @return gibt das evtl. eingefügte Bild zurück
	 */
	public String getIngredientPicturePath() {
		return ingredientPicturePath;
	}

	/**
	 * Setzt das Bild für das Lebensmittel
	 * @param ingredientPicture: Bild zum Einfügen
	 */
	public void setIngredientPicturePath(String ingredientPicturePath) {
		this.ingredientPicturePath = ingredientPicturePath;
	}

	/**
	 * Holt die Inhaltsstoffliste
	 * @return gibt die Inhaltsstoffliste zurück
	 */
	public Collection<Food> getIngredientsOfFoodList() {
		return ingredientsOfFoodList;
	}

	/**
	 * setzt die Inhaltsstoffliste
	 * @param ingredientsOfFoodList: Liste von Inhaltsstoffen
	 */
	public void setIngredientsOfFoodList(Collection<Food> ingredientsOfFoodList) {
		this.ingredientsOfFoodList = ingredientsOfFoodList;
	}

	/**
	 * Holt die Inhaltsstoffe
	 * @return: gibt die Inhaltsstoffe zurück
	 */
	public Collection<Ingredient> getIngredients() {
		return ingredients;
	}

	/**
	 * setzt die Inhaltsstoffliste mit den Inhaltsstoffen dieses Lebensmittels
	 * @param ingredients: Inhaltsstoffe des Lebensmittels
	 */
	public void setIngredients(Collection<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	/**
	 * Override für hashCode für das vergleichen nach Name
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	/**
	 * Override für equals zum vergleichen per Name
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Food other = (Food) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return name;
	}
    
    
}
