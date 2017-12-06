package model;

/** Klasse zur Vermerkung von unterschiedlichen Beschwerden, die der E.T.-User erlebt hat.
 * @author Miran Shefketi
 */
public class HealthProblem {

    /** Der Name der Beschwerde
     */
    private String name;

    /** Die Beschreibung der Beschwerde
     */
    private String description;

    /** Initialisiert die Klasse mit den gegebenen Parametern, indem diese in die attribute geschrieben werden.
     * @param name Der Name der Beschwerde z.B. "Bauchschmerzen"
     * @param description Eine genauere beschreibung der Beschwerde z.B "Mittelstarke Schmerzen von 15:00 bis 16:00 Uhr"
     */
    public HealthProblem(String name, String description) {
        this.name = name;
        this.description = description;
    }

	/** Gibt den Namen der Beschwerde zurück.
	 * @return Der aktuelle Name der Beschwerde
	 */
	public String getName() {
		return name;
	}

	/** Setzt den Namen der Beschwerde dem Parameter gleich.
	 * @param name Der neue Name der Beschwerde
	 */
	public void setName(String name) {
		this.name = name;
	}

	/** Gibt die Beschreibung der Beschwerde zurück.
	 * @return Die aktuelle Beschreibung der Beschwerde
	 */
	public String getDescription() {
		return description;
	}

	/** Setzt die Beschreibung der Beschwerde dem Parameter gleich.
	 * @param description Die neue Beschreibung der Beschwerde
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return this.name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HealthProblem other = (HealthProblem) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
    
}
