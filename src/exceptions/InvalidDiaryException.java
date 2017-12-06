package exceptions;

/** Wird geworfen wenn ein eingelesenes Tagebuch invalid ist.
 *  @author Friedemann Runte
 */
public class InvalidDiaryException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public InvalidDiaryException(String string) {
		super(string);
	}

}
