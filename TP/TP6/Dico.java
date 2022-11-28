
public abstract class Dico {

	/**
	 * Crée une association entre une clé et une valeur. 
	 * Si une association (cle, autre valeur) existe déjà, elle est écrasée. 
	 * 
	 * @param cle Clé de l'association, supposée non nulle
	 * @param valeur  Valeur de l'association, supposée non nulle
	 */
	public abstract void associe(String cle, String valeur) ;
	
	/**
	 * Supprime l'association (cle, valeur) si elle existe, sinon ne fait rien
	 * 
	 * @param cle Clé à supprimer
	 */
	public abstract void supprime(String cle) ;
	
	/**
	 * Renvoie la valeur associée  à la clé indiquée.
	 * 
	 * @param cle Clé à rechercher
	 * @return Valeur associée à cle, null sinon
	 */
	public abstract String get(String cle) ;
	
}
