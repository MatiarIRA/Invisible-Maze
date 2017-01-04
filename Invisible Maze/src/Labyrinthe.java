
/**
 * La classe Labyrinthe represantant un labyrinthe tel que represente par l'enonce 
 * @author M.H.ERFANIAN AZMOUDEH et Vivianne NGUYEN-DONG
 * @since February 2016
 * To add at GitHub
 */
public class Labyrinthe {
	
	/**
	 * Un tableau de 2 dimentions de char representant le dessin du labyrinthe
	 */
	private char [][] tabDessineur;
	
	//les atrributes height et width ajoutes
	private int height;
	private int width;
	
	/**
	 * Les constantes HMURET et LMURET definissant les distances en largeur et en hauteur entre 2 murets
	 */
	private static final int HMURET= 4;
	
	/**
	 * Les constantes HMURET et LMURET definissant les distances en largeur et en hauteur entre 2 murets
	 */
	private static final int LMURET= 8;
	
	/**
	 * Le personnage de l'objet de Labyrinthe current
	 */
	private Personnage p;
	
	/**
	 * Le constructeur de la classe Labyrinthe
	 * @param h
	 * @param w
	 */
	public Labyrinthe(int h, int w) {
		this.height = h;
		this.width = w;
		creeTableau(h,w);
	}
	
	/**
	 * le getteur de hauteur du labyrinthe
	 * @return height
	 */
	int getH(){
		return this.height;
	}
	
	/**
	 * le getteur de largeur du labyrinthe
	 * @return width
	 */
	int getW(){
		return this.width;
	}
	
	/**
	 * le getteur de tabDessineur
	 * @return tabDessineur
	 */
	char[][] getTabDessineur(){
		return tabDessineur;
	}
	
	/**
	 * le getteur du Personnage du labyrinthe current
	 * @return personnage
	 */
	Personnage getPersonnage(){
		return p;
	}
	
	/**
	 * La méthode creeTableau pour cree le tableau de base du jeu
	 * @param hauteur
	 * @param largeur
	 * @return 
	 */
	void creeTableau (int hauteur, int largeur){
		char [][] tab = new char[hauteur*HMURET+1][largeur*LMURET+1]; //WE can't USE print(tab); BCS IT PRINTS ONLY THE ADDRESS OF THE ARRAY
		this.tabDessineur = tab;
		effaceTableau();
	}
	
	/**
	 * La méthode effaceTableau qui remplit le tableaude caractere espace
	 */
	void effaceTableau(){
		char espace=' ';
		for(int i=1; i<tabDessineur.length-1; i++){
			for(int j=1; j<tabDessineur[0].length-1; j++){ //We could replace largeur by tab[0]!
				tabDessineur[i][j]=espace;
			}
		}

	}
	
	/**
	 * dessineMurdEnceinte() dessine un mur d'enceinte complètement fermé.
	 */
	void dessineMurdEnceinte(){
		for(int i=0; i<tabDessineur.length; i++){
			for(int j=0; j<tabDessineur[0].length; j++){
				if(i==0 || i==tabDessineur.length-1){
					tabDessineur[i][j]='-';
				}
				if(j==0 || j==tabDessineur[0].length-1){
					tabDessineur[i][j]='|';
				}
			}
		}	
		tabDessineur[0][0]='+';
		tabDessineur[tabDessineur.length-1][0]='+';
		tabDessineur[0][tabDessineur[0].length-1]='+';
		tabDessineur[tabDessineur.length-1][tabDessineur[0].length-1]='+';
	}
	
	/**
	 * dessineOuverture(int j) prend en paramètre la position verticale j (en nombre de cases) de
		l'ouverture de droite et la crée en effaçant la portion du mur d'enceinte correspondante.
	 * @param j
	 */
	void dessineOuverture(int j){
		for(int y=0; y<HMURET-1; y++){
			tabDessineur[j*HMURET+1+y][tabDessineur[0].length-1]=' ';
		}
	}

	/**
	 * dessineMuretVertical(int i, int j) reçoit en paramètre la position i et j de la case où on veut dessiner
	   un muret vertical (sur son bord gauche)
	 * @param i
	 * @param j
	 */
	void dessineMuretVertical(int i, int j){
		int jj = j*LMURET;
		if(j>0){
			for(int ii= i*HMURET+1; ii<i*HMURET+HMURET; ii++){
				tabDessineur[ii][jj]='|';
			}
		}
	}
	
	/**
	 * dessineMuretHorizontal(int i, int j) reçoit en paramètre la position i et j de la case où on veut
		dessiner un muret horizontal (sur son bord haut)
	 * @param i
	 * @param j
	 */
	void dessineMuretHorizontal(int i, int j){
		int ii = i*HMURET;
		if(i>0){
			for(int jj= j*LMURET+1; jj<j*LMURET+LMURET; jj++){
				tabDessineur[ii][jj]='-';
			}
		}
	}
	
	/**
	 * muretVerticalExiste(int i, int j) reçoit en paramètre la position i et j d'une case.
	 * @param i
	 * @param j
	 * @return true si sur le bord gauche de cette case, apparaît un muret ou un mur d'enceinte
	 */
	boolean muretVerticalExiste(int i, int j){
		if(tabDessineur[i*HMURET+1][j*LMURET]=='|')
			return true;
		return false;
	}
	
	/**
	 * muretHorizontalExiste(int i, int j) reçoit en paramètre la position i et j d'une case. 
	 * @param i
	 * @param j
	 * @return true si sur le bord haut de cette case, apparaît un muret ou un mur d'enceinte
	 */
	boolean muretHorizontalExiste(int i, int j){	
		if(tabDessineur[i*HMURET][j*LMURET+1]=='-')
			return true;
		return false;		
	}
	
	/**
	 * dessinePersonnage(Personnage p) prendra en paramètre un objet de type Personnage (voir ci-dessous), et le dessinera (avec un caractère '@') au centre de la case correspondant à sa position
	 * @param p
	 */
	void dessinePersonnage(Personnage p){
		tabDessineur[p.getCoorVer()*HMURET+HMURET/2][p.getCoorHor()*LMURET+LMURET/2]=p.getPerssonage();
	}
	
	/**
	 * effacePersonnage(Personnage p) prendra en paramètre un objet de type Personnage (voir ci-dessous), et effacera (en y ettant un caractère ' ') le centre de la case correspondant à sa position
	 * @param p
	 */
	void effacePersonnage(Personnage p){
		tabDessineur[p.getCoorVer()*HMURET+HMURET/2][p.getCoorHor()*LMURET+LMURET/2]=' ';
	}

	/**
	 * une méthode pour dessiner une cloture horizontal à rendre plus efficace la intelligence artificiel
	 * @param i
	 * @param j
	 */
	void dessineClotureHorizontal(int i, int j){
		int ii = i*HMURET;
		if(i>0){
			for(int jj= j*LMURET+1; jj<j*LMURET+LMURET; jj++){
				tabDessineur[ii][jj]='x';
			}
		}
	}
	
	/**
	 * une méthode pour dessiner une cloture vertical à rendre plus efficace la intelligence artificiel
	 * @param i
	 * @param j
	 */
	void dessineClotureVertical(int i, int j){
		int jj = j*LMURET;
		if(j>0){
			for(int ii= i*HMURET+1; ii<i*HMURET+HMURET; ii++){
				tabDessineur[ii][jj]='x';
			}
		}
	}
	/**
	 * une méthode pour effacer une cloture horizontal 
	 * @param i
	 * @param j
	 */
	void effaceClotureHorizontal(int i, int j){
		int ii = i*HMURET;
		if(i>0 && tabDessineur[i][j]=='x'){
			for(int jj= j*LMURET+1; jj<j*LMURET+LMURET; jj++){
				tabDessineur[ii][jj]=' ';
			}
		}
	}
	
	/**
	 * une méthode pour effacer une cloture vertical
	 * @param i
	 * @param j
	 */
	void effaceClotureVertical(int i, int j){
		int jj = j*LMURET;
		if(j>0 && tabDessineur[i][j]=='x'){
			for(int ii= i*HMURET+1; ii<i*HMURET+HMURET; ii++){
				tabDessineur[ii][jj]=' ';
			}
		}
	}
	
	/**
	 * une méthode pour verifier l'existance d'une cloture vertical
	 * @param i
	 * @param j
	 * @return
	 */
	boolean ClotureVerticalExiste(int i, int j){
		if(tabDessineur[i*HMURET+1][j*LMURET]=='x')
			return true;
		return false;
	}
	
	/**
	 * une méthode pour verifier l'existance d'une cloture horizontal
	 * @param i
	 * @param j
	 * @return
	 */
	boolean ClotureHorizontalExiste(int i, int j){	
		if(tabDessineur[i*HMURET][j*LMURET+1]=='x')
			return true;
		return false;		
	}
	
	/**
	 * fonction static effaceEcran() affiche *200* lignes vides pour évacuer vers le haut ce qui était
	 * auparavant visible sur le terminal.
	 */
	static void effaceEcran(){
		int e=0;
		while(e<200){System.out.println();	e++;}
	}
	
	/**
	 * affiche() qui affiche le tableau de caractères à l'écran
	 */
	void affiche(){
		String tabJeu="";
		for(int i=0; i<tabDessineur.length; i++){
			for(int j=0; j<tabDessineur[0].length; j++){
				tabJeu+=tabDessineur[i][j];
			}
			tabJeu+="\n";
		}
		System.out.println(tabJeu);
	}
	
	/**
	 * construitLabyrintheAleatoire(double densite)qui prend en paramètre la "desité" et construit des murets aléatoirement (voir indication sur comment procéder plus bas), et pratique une ouverture (au hazard) sur le mur d'enceinte de droite.
	 * @param densite
	 */
	
	void construitLabyrintheAleatoire(double densite){
		dessineMurdEnceinte();
		int ouvert = (int)(Math.random()*getH());
		dessineOuverture(ouvert);
		int nombreDeMuretH = (int)(((getH()-1)*getW())*densite+0.5); 
		int nombreDeMuretW = (int)((getW()-1)*getH()*densite+0.5);
		int nbrTotalDeMuret = nombreDeMuretH + nombreDeMuretW;

		int k = 0;
		
		arretDessin:
		while(k < nbrTotalDeMuret){
			for(int i=0; i<getH(); i++){	
				for(int j=0; j<getW(); j++){
					//les probabilite des murets horizontaux et des murets verticaux
					boolean probabilityH = densite+Math.random()>=1;
					boolean probabilityV = densite+Math.random()>=1;
					
					if(probabilityH){
						if(muretHorizontalExiste(i, j)== false){
							dessineMuretHorizontal(i, j);
							k++;
						}
						if(k== nbrTotalDeMuret) break arretDessin;	
					}
					if(probabilityV){
						if(muretVerticalExiste(i, j)== false){
							dessineMuretVertical(i, j);
							k++;
						}
						if(k== nbrTotalDeMuret) break arretDessin;		
					}
				}
			}
		}
		
	
		int coorVerDePersonnage = (int)(Math.random()*getH());
		p = new Personnage(this,coorVerDePersonnage,0);
		dessinePersonnage(p);
	}
	
	/**
	 * un constructeur de copie Labyrinthe(Labyrinthe l) qui prend en paramètre un autre Labyrinthe et le
	 * "recopie" dans celui qui est créé.
	 * @param l
	 */
	public Labyrinthe(Labyrinthe l){
		this.height = l.height;
		this.width = l.width;
		this.tabDessineur = new char[l.tabDessineur.length][l.tabDessineur[0].length];
		for(int i=0; i<this.tabDessineur.length; i++){
			for(int j=0; j<this.tabDessineur[0].length; j++){
				this.tabDessineur[i][j]= l.tabDessineur[i][j];
			}
		}
		this.p = new Personnage(l, l.p.getCoorVer(), l.p.getCoorHor());
	}	
	
}


















