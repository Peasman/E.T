package model;

/**
 * Diese Einheiten werden für die Einträge im Tagebuch genutzt.
 * @author Moritz Ludolph
 */
public enum Unit {
	
    /**
     * keine Einheit
     */
    VOID(" "),
    /**
     * Stück, beispielsweise Pillen oder Tomaten
     */
    PIECES("Stk."),
    /**
     * Volumen in Liter
     */
    LITRE("l"),
    /**
     * Gewicht in Gramm
     */
    GRAMM("g"),
    /**
     * Zeit in Minuten
     */
    MINUTES("min");
	
	private String label;
	Unit(String label){
		this.label = label;
		
	}
	public String toString(){
		return label;
	}
	
	
}
