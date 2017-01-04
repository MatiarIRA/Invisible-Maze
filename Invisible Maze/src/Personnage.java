
/**
 * La classe Personnage comporte des proprietes indiquant sa position et son nombre de vies restantes
 * @author M.H.ERFANIAN AZMODEH et Vivianne NGUYEN-DONG
 * @since February 2016
 * To add at GitHub
 */
public class Personnage {
	/**
	 * Attribut coorHor representant le coordonne horizontal du personnage
	 */
	private int coorHor;
	/**
	 * Attribut coorVer representant le coordonne vertical du personnage
	 */
	private int coorVer;
	private int viesRestant;
	/**
	 * Attribut symPersonnage representant le symbol du personnage
	 */
	private char symPersonnage; 
	/**
	 * Attribut l representant le Labyrinthe sur lequel le personnage trouve leur coordonnes et leur vies restant
	 */
	private Labyrinthe l;
	
	/**
	 * Le constructeur du Personnage a initialiser son Labyrinthe, ses coordonnes et son symbol
	 * @param l
	 * @param i
	 * @param j
	 */
	public Personnage(Labyrinthe l, int i, int j){
		this.l = l;
		this.coorVer=i;
		this.coorHor=j;
		this.symPersonnage  = '@';
	}
	
	/**
	 * le getteur de coordonnée horizontal du personnage
	 * @return coorHor
	 */
	public int getCoorHor() {
		return coorHor;
	}
	
	/**
	 * le setteur de coordonnée horizontal du personnage
	 * @param coorHor
	 */
	public void setCoorHor(int coorHor) {
		this.coorHor = coorHor;
	}
	
	/**
	 * le getteur de coordonnée vertical du personnage
	 * @return coorVer
	 */
	public int getCoorVer() {
		return coorVer;
	}
	
	/**
	 * le setteur de coordonnée vertical du personnage
	 * @param coorVer
	 */
	public void setCoorVer(int coorVer) {
		this.coorVer = coorVer;
	}
	
	/**
	 * le getteur de vies restant du personnage
	 * @return viesRestant
	 */
	public int getViesRestant() {
		return viesRestant;
	}
	
	/**
	 * le setteur de vies restant du personnage
	 * @param viesRestant
	 */
	public void setViesRestant(int viesRestant) {
		this.viesRestant = viesRestant;
	}
	
	/**
	 * le getteur du symbol du personnage
	 * @return symPersonnage 
	 */
	public char getPerssonage(){
		return symPersonnage;
	}
	
	/**
	 * la méthode pour deplacement à droite du personnage
	 */
	public void droite(){
			setCoorHor(getCoorHor()+1);
	}
	
	/**
	 * la méthode pour deplacement à gauche du personnage
	 */
	public void gauche(){
			setCoorHor(getCoorHor()-1);
	}
	
	/**
	 * la méthode pour deplacement en haut du personnage
	 */
	public void haut(){
			setCoorVer(getCoorVer()-1);
	}
	
	/**
	 * la méthode pour deplacement en bas du personnage
	 */
	public void bas(){
			setCoorVer(getCoorVer()+1);
	}


}
