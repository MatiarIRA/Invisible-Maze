import java.util.Scanner;
/**
 * La classe Laby est le jeu, elle devra prendre 5 paramètres sur la ligne de commande qui sont respectivement:
 * 1.la hauteur du labyrinthe en nombre de cases
 * 2.la largeur du labyrinthe en nombre de cases
 * 3.la "densité" sous forme de probabilité de générer un muret (il s'agir d'un nombre entre 0 et 1: plus il est élevé, plus il y aura de murets, ainsi 0 génèrera un labyrinthe sans aucun muret intérieur, et 1 génèrera un labyrinthe avec tous les murets intérieurs voir exemple et explications plus bas).
 * 4.le nombre de secondes d'affichage du labyrinthe complet avant qu'il ne devienne invisible.
 * 5.le nombre de vies dont dispose votre personnage
 *
 * @author M.H.ERFANIAN AZMOUDEH et Vivianne NGUYEN-DONG
 * @since February 2016
 * To add at GitHub
 */
public class Laby {
	/**
	 * la reference du labyrinthe invisible
	 */
	private static Labyrinthe li;
	
	/**
	 * la reference du labyrinthe visible
	 */
	private static Labyrinthe lv;
	
	/**
	 * le hauteur du labyrinthe
	 */
	private static int height;
	
	/**
	 * le largeur du labyrinthe
	 */
	private static int width;
	
	/**
	 * la densite pour dessiner les murets 
	 */
	private static double densite;
	
	/**
	 * les secondes pendant lesquelles on veut pauser le jeu
	 */
	private static int sec;
	
	/**
	 * nombre de vies du perssonage au depart de chaque jeu
	 */
	private static int vies;
	
	/**
	 * l'attribut utilisé pour prendre des commands de clavier
	 * enfait il permet de switcher entre deux méthode de intélligence artificielle
	 * @ see methde ordiHautBas(p);
	 */
	private static String action="";
	
	/**
	 * la fonction main qui permet de fournir les arguments necessaire pour le jeu
	 * @param args
	 */
	public static void main(String[]args){
		
		if(args.length!=5){
			System.out.println("Nombre de parametres incorrects.\n"
					+ "Utilisation: java Laby <hauteur> <largeur> <densite> <duree visible> <nb vies>\n"
					+ "Ex: java Laby 10 20 0.20 10 5");
		}
		
		jeuLabyrintheInvisible(args[0], args[1], args[2], args[3],args[4]);
	}
	
	/**
	 * la fonction jeuLabyrintheInvisible qui gère une partie au complet du début à la fin .
	 * @param h
	 * @param w
	 * @param densite
	 * @param sec
	 * @param vies
	 */
	public static void jeuLabyrintheInvisible (String heuteur, String largeur,String density,String secondDeVisibilite, String viesDePersonnageAuDepart){ 
		height = Integer.parseInt(heuteur);
		width = Integer.parseInt(largeur);
		densite = Double.parseDouble(density);
		sec = Integer.parseInt(secondDeVisibilite);
		vies = Integer.parseInt(viesDePersonnageAuDepart);
		long pause = sec * 1000;
		int viesRestant; 
		
		//laby initial qui va rendre invisible apres qqu seconde
		li = new Labyrinthe(height,width);
		li.construitLabyrintheAleatoire(densite);
		Personnage p = li.getPersonnage();
		Labyrinthe.effaceEcran(); 
		p.setViesRestant(vies);
		li.affiche();
		
		//laby de jeu courant (laby visible)
		lv = new Labyrinthe(li);
		li.effaceTableau();
		li.dessinePersonnage(p);
		viesRestant = p.getViesRestant();
		affichageEtatDuPersonnage(viesRestant, p);
		sleep(pause);
		Labyrinthe.effaceEcran();
		li.affiche();
			
		
		Scanner scan = new Scanner(System.in);
		affichageEtatDuPersonnage(viesRestant, p);
		
		action = scan.nextLine();
		
		looping:
		while(p.getViesRestant()>0){
			if(action.equalsIgnoreCase("d")){
				mouvementADroit(li,lv,p,viesRestant);
				//Affichage l'état du Personnage à l'interieur de la méthode!
			}
			
			if(action.equalsIgnoreCase("g")||action.equalsIgnoreCase("s")){
				mouvementAGauche(li,lv,p,viesRestant);
				affichageEtatDuPersonnage(viesRestant, p);
			}
			
			if(action.equalsIgnoreCase("h")||action.equalsIgnoreCase("e")){
				mouvementVersHaut(li,lv,p,viesRestant);
				affichageEtatDuPersonnage(viesRestant, p);
			}
			
			if(action.equalsIgnoreCase("b")||action.equalsIgnoreCase("x")){
				mouvementVersBas(li,lv,p,viesRestant);
				affichageEtatDuPersonnage(viesRestant, p);				
			}
			
			if(action.equalsIgnoreCase("q")){
				System.exit(0);
			}
			
			if(action.equalsIgnoreCase("p")){
				li.effacePersonnage(p);
				lv.effaceTableau();
				jeuLabyrintheInvisible(heuteur, largeur, density, secondDeVisibilite, viesDePersonnageAuDepart);
			}
			
			if(action.equalsIgnoreCase("v")){
				if (li!=lv){
					lv.effacePersonnage(lv.getPersonnage());
					lv.dessinePersonnage(li.getPersonnage());
					Labyrinthe.effaceEcran();
					li=lv; 
					lv.affiche();
					lv.effacePersonnage(p);
					affichageEtatDuPersonnage(viesRestant, p);
				}
			}
			
			if(action.equalsIgnoreCase("o")){			
				lv.effacePersonnage(lv.getPersonnage());
				lv.dessinePersonnage(p);
				Labyrinthe.effaceEcran();
				li=lv;
				lv.affiche();
				lv.effacePersonnage(p);
				affichageEtatDuPersonnage(viesRestant, p);
				
				while(true){
					action = scan.nextLine();
					if(action.equalsIgnoreCase("l")){
						logicMazeSolver(p,viesRestant);
						break;
					}else if(action.equalsIgnoreCase("r")){
						randMazeSolver(p,viesRestant);
						break;
					}else{
						continue looping;
					}
				}
			}
			if(p.getViesRestant()>0)
				action = scan.nextLine();	
		}
		
		lv.effacePersonnage(lv.getPersonnage());
		lv.dessinePersonnage(li.getPersonnage());
		Labyrinthe.effaceEcran();
		lv.affiche();
		lv.effacePersonnage(p);
		System.out.println("Vous avez perdu, vous avez epuise vos "+vies+" vies!\n");
		nouvellePartie(p);
	}
	
	/**
	 * la méthode nouvellePartie() pour permettant à l'usaer pour démarer une nouvelle partie désiré
	 */
	public static void nouvellePartie(Personnage p){
		System.out.println("Voulez-vous jouer une nouvelle partie?(oui/non)\n");
		while(true){
			Scanner scan = new Scanner(System.in);
			String nvlPartie = scan.nextLine();
			
			if(!nvlPartie.equalsIgnoreCase("oui")&&!nvlPartie.equalsIgnoreCase("non")){
				Labyrinthe.effaceEcran();
				lv.affiche();
				System.out.println("Voulez-vous jouer une nouvelle partie? (oui / non)\n");
				System.out.println("Vous n'avez pas repondu correctement!\nVeuillez taper oui ou non");
			}else if(nvlPartie.equalsIgnoreCase("oui")){
				lv.effaceTableau();
				action="";
				jeuLabyrintheInvisible(height+"", width+"", densite+"", sec+"", vies+"");
			}else{
				System.exit(0);
			}
		}
	}
	
	/**
	 * la méthode affichageEtatDuPersonnage qui affiche fréquement le nombre de vies restant pour l'usager
	 * @param vies
	 * @param viesRestant
	 * @param p
	 */
	public static void affichageEtatDuPersonnage(int viesRestant, Personnage p){
		System.out.println("Il vous reste "+p.getViesRestant()+ " vies sur "+ vies+"\n");
		if(action.equalsIgnoreCase("o")){
			System.out.println("Choisissez parmi deux options suiventes:\n"
					+ "tapez la lettre 'l' pour une solution plus logique\n"
					+ "tapez la lettre 'r' pour une solution basant aleatoirement");
		}else if(action.equalsIgnoreCase("l")){
			System.out.println("'solveur logique' en cours d'execution");
		}else if(action.equalsIgnoreCase("r")){
			System.out.println("'solveur aleatoire' en cours d'execution");
		}else{
			System.out.println("Quelle direction souhaitez-vous prendre?\n"
					+"(droite: d; gauche: g ou s; haut: h ou e; bas: b ou x)");
		}
	}
	
	/**
	 * une méthode pour determiner la permission de deplacement a droite
	 * @param p
	 * @return
	 */
	static boolean droitePermi(Personnage p){
		if(!lv.muretVerticalExiste(p.getCoorVer(), p.getCoorHor()+1) &&
				!lv.ClotureVerticalExiste(p.getCoorVer(), p.getCoorHor()+1)){
			return true;
		}
		return false;
	}
	
	/**
	 * une méthode pour determiner la permission de deplacement a gauche
	 * @param p
	 * @return
	 */
	static boolean gauchePermi(Personnage p){
		if(!lv.muretVerticalExiste(p.getCoorVer(), p.getCoorHor())&&
				!lv.ClotureVerticalExiste(p.getCoorVer(), p.getCoorHor())){
			return true;
		}
		return false;
	}
	
	/**
	 * une méthode pour determiner la permission de deplacement vers haut
	 * @param p
	 * @return
	 */
	static boolean hautPermi(Personnage p){
		if(!lv.muretHorizontalExiste(p.getCoorVer(), p.getCoorHor())&&
				!lv.ClotureHorizontalExiste(p.getCoorVer(), p.getCoorHor())){
			return true;
		}
		return false;
	}
	
	/**
	 * une méthode pour determiner la permission de deplacement vers bas
	 * @param p
	 * @return
	 */
	static boolean basPermi(Personnage p){
		if(!lv.muretHorizontalExiste(p.getCoorVer()+1, p.getCoorHor())&&
				!lv.ClotureHorizontalExiste(p.getCoorVer()+1, p.getCoorHor())){
			return true;
		}
		return false;
	}
	
	/**
	 * une méthode pour deplacer a droite par l'ordinateur
	 * @param p
	 * @param vies
	 * @param viesRestant
	 */
	public static void ordiDroite(Personnage p, int viesRestant){
			sleep(500);					
			mouvementADroit(li,lv,p,viesRestant);
	}

	/**
	 * une méthode pour deplacer vers haut par l'ordinateur
	 * @param p
	 * @param vies
	 * @param viesRestant
	 */
	public static void ordiHaut(Personnage p, int viesRestant){	
			sleep(500);					
			mouvementVersHaut(li,lv,p,viesRestant);
			affichageEtatDuPersonnage(viesRestant, p);
	}

	/**
	 * une méthode pour deplacer vers bas par l'ordinateur
	 * @param p
	 * @param vies
	 * @param viesRestant
	 */
	public static void ordiBas(Personnage p,int viesRestant){	
			sleep(500);					
			mouvementVersBas(li,lv,p,viesRestant);
			affichageEtatDuPersonnage(viesRestant, p);
	}

	/**
	 * une méthode pour deplacer a gauche par l'ordinateur
	 * @param p
	 * @param vies
	 * @param viesRestant
	 */
	public static void ordiGauche(Personnage p,int viesRestant){
			sleep(500);					
			mouvementAGauche(li,lv,p,viesRestant);
			affichageEtatDuPersonnage(viesRestant, p);
	}
	
	/**
	 * une méthode pour verifier si il y a une case impassable si le personnage va a driote
	 * @param p
	 * @return
	 */
	static boolean dTrap(Personnage p){
		if(p.getCoorHor()+1>=lv.getW()){return false;}
		if(droitePermi(p)){
			p.droite();
			if(!hautPermi(p) && !droitePermi(p) && !basPermi(p)){
				p.gauche();
				return true;
			}else{
				p.gauche(); return false;
			}
		}
		return false;
	}
	
	/**
	 * une méthode pour verifier si il y a une case impassable si le personnage va a gauche
	 * @param p
	 * @return
	 */
	static boolean gTrap(Personnage p){
		if(gauchePermi(p)){
			p.gauche();
			if(!hautPermi(p) && !gauchePermi(p) && !basPermi(p)){
				p.droite();
				return true;
			}else{
				p.droite(); return false;
			}
		}
		return false;
	}
	
	/**
	 * une méthode pour verifier si il y a une case impassable si le personnage va en haut
	 * @param p
	 * @return
	 */
	static boolean hTrap(Personnage p){
		if(hautPermi(p)){
			p.haut();
			if(!droitePermi(p) && !hautPermi(p) && !gauchePermi(p)){
				p.bas();
				return true;
			}else{
				p.bas(); return false;
			}
		}
		return false;
	}
	
	/**
	 * une méthode pour verifier si il y a une case impassable si le personnage va en bas
	 * @param p
	 * @return
	 */
	static boolean bTrap(Personnage p){
		if(basPermi(p)){
			p.bas();
			if(!droitePermi(p) && !basPermi(p) && !gauchePermi(p)){
				p.haut();
				return true;
			}else{
				p.haut(); return false;
			}
		}
		return false;
	}
	
	/**
	 * une méthode pour trouver une solution de labyrinthe par ordinateur
	 * @param p
	 * @param vies
	 * @param viesRestant
	 */
	public static void logicMazeSolver(Personnage p, int viesRestant){		
		int i=0;
		repeat:
		while(p.getViesRestant()>0){
			while(droitePermi(p) && !dTrap(p)){
				ordiDroite(p,viesRestant);
				i++;
				if(i>=lv.getW()){
					effaceurLesClotures();
					ordiHaut(p, viesRestant);
				}
			}	
			if(hautPermi(p) && !hTrap(p) || basPermi(p) && !bTrap(p)){
				ordiHautBas(p,viesRestant);
			}else if(gauchePermi(p)){
				do{
					ordiGauche(p,viesRestant);
					lv.dessineClotureVertical(p.getCoorVer(), p.getCoorHor()+1);
					i++;
					if(i>=lv.getW()){					
						i=0;
						ordiHautBas(p,viesRestant);
					}
					if(hautPermi(p) && !hTrap(p) || basPermi(p) && !bTrap(p)){ordiHautBas(p,viesRestant);}
				}while(gauchePermi(p) && !gTrap(p));
				lv.effaceClotureHorizontal(p.getCoorVer(), p.getCoorHor());
				ordiHautBas(p,viesRestant);
			}else if(!gauchePermi(p) || gTrap(p) && !droitePermi(p) || dTrap(p) && !basPermi(p) || bTrap(p) && !hautPermi(p) || hTrap(p)){
				effaceurLesClotures();}
		}		
	}
	
	/**
	 * une méthode combinée avec les méthodes logicMazeSolver et randMazeSolver
	 * @param p
	 * @param vies
	 * @param viesRestant
	 */
	static void ordiHautBas(Personnage p,int viesRestant){
		
		if(hautPermi(p) && !hTrap(p) || basPermi(p) && !bTrap(p)){
		ici:
		while(hautPermi(p)){
				while(hautPermi(p)&& !hTrap(p)){
					ordiHaut(p,viesRestant);
					if(droitePermi(p) && !dTrap(p)){
						if(action.equalsIgnoreCase("l"))
							logicMazeSolver(p,viesRestant);
						if(action.equalsIgnoreCase("r"))
							randMazeSolver(p,viesRestant);
					}
				}
				
				if(!hautPermi(p) || hTrap(p) && basPermi(p)){
					while(basPermi(p) && !bTrap(p)){						
						ordiBas(p,viesRestant);
						li.dessineClotureHorizontal(p.getCoorVer(), p.getCoorHor());
						if(droitePermi(p) && !dTrap(p)){
							if(action.equalsIgnoreCase("l"))
								logicMazeSolver(p,viesRestant);
							if(action.equalsIgnoreCase("r"))
								randMazeSolver(p,viesRestant);
						}
					}

					if(!droitePermi(p)|| dTrap(p) && !hautPermi(p) && !basPermi(p) 
							|| bTrap(p) || hTrap(p) && gauchePermi(p)){
						do{
							ordiGauche(p,viesRestant);
							if(hautPermi(p) && !hTrap(p) || basPermi(p) && !bTrap(p)){
								continue ici;
							}
						}while(gauchePermi(p) && !gTrap(p));
					}
				}
			}
	
			if(basPermi(p)){
				while(basPermi(p) && !bTrap(p)){
					ordiBas(p,viesRestant);
					if(droitePermi(p) && !dTrap(p)){
						if(action.equalsIgnoreCase("l"))
							logicMazeSolver(p,viesRestant);
						if(action.equalsIgnoreCase("r"))
							randMazeSolver(p,viesRestant);
					}
				}
			}
		}
	}
	
	/**
	 * une méthode pour trouver une solution de labyrinthe par ordinateur en base aleatoire
	 * @param p
	 * @param vies
	 * @param viesRestant
	 */
	static void randMazeSolver(Personnage p,int viesRestant){		
		int i=0;
		repeat:
		while(p.getViesRestant()>0){
			
			int randNumber = (int)(Math.random()*3.0);
			
			if(!gauchePermi(p) || gTrap(p) && !droitePermi(p) || dTrap(p) && !basPermi(p) || bTrap(p) && !hautPermi(p) || hTrap(p))
				effaceurLesClotures();
			
			while(droitePermi(p) && !dTrap(p)){
				ordiDroite(p,viesRestant);
				i++;
				if(i>=lv.getW())
					effaceurLesClotures();
			}
			
			if(randNumber==0){
				if(gauchePermi(p) && !gTrap(p)){
					while(gauchePermi(p) && !gTrap(p)){
						ordiGauche(p,viesRestant);
						if(hautPermi(p) && !hTrap(p) || basPermi(p) && !bTrap(p)){
							ordiHautBas(p,viesRestant);
							i=0;
						}						
					}
				}
				if(!gauchePermi(p) || gTrap(p) && !droitePermi(p) || dTrap(p) && !basPermi(p) || bTrap(p) && !hautPermi(p) || hTrap(p))
					effaceurLesClotures();
			}
			
			if(randNumber==1){
				if(hautPermi(p) && !hTrap(p)){ 
					while(hautPermi(p) && !hTrap(p)){
						if(droitePermi(p) && !dTrap(p)){continue repeat;}
					ordiHaut(p,viesRestant);
					}	
				}
				if(!gauchePermi(p) || gTrap(p) && !droitePermi(p) || dTrap(p) && !basPermi(p) || bTrap(p) && !hautPermi(p) || hTrap(p))
					effaceurLesClotures();
			}
			
			if(randNumber==2){
				if(basPermi(p) && !bTrap(p)){
					while(basPermi(p) && !bTrap(p)){
						if(droitePermi(p) && !dTrap(p)){; continue repeat;}
						ordiBas(p,viesRestant);
					}
				}
				if(!gauchePermi(p) || gTrap(p) && !droitePermi(p) || dTrap(p) && !basPermi(p) || bTrap(p) && !hautPermi(p) || hTrap(p))
					effaceurLesClotures();
			}
		}
	}
	
	static void effaceurLesClotures(){
		for(int i=0; i<lv.getH(); i++){
			for(int j=0; j<lv.getW(); j++){
				lv.effaceClotureHorizontal(i, j);
				lv.effaceClotureVertical(i, j);
			}
		}
	}
	
	/**
	 * la méthode mouvementADroit qui verifie la possibilite et puis fait le mouvement a droite
	 * @param li
	 * @param lv
	 * @param p
	 * @param viesRestant
	 */
	public static void mouvementADroit(Labyrinthe li,Labyrinthe lv, Personnage p, int viesRestant){
		int newCoorVer = p.getCoorVer();
		int newCoorHor = p.getCoorHor()+1;
		
		if(newCoorHor>=li.getW() && lv.muretVerticalExiste(newCoorVer,newCoorHor)== true){
			p.setViesRestant(p.getViesRestant()-1);
			Labyrinthe.effaceEcran();
			li.affiche();
			affichageEtatDuPersonnage(viesRestant, p);
		}else if(lv.muretVerticalExiste(newCoorVer,newCoorHor)){
			p.setViesRestant(p.getViesRestant()-1);
			li.dessineMuretVertical(newCoorVer,newCoorHor);
			Labyrinthe.effaceEcran();
			li.affiche();
			affichageEtatDuPersonnage(viesRestant, p);
		}else if(lv.muretVerticalExiste(newCoorVer,newCoorHor)== false && newCoorHor>=lv.getW()){
			lv.effacePersonnage(lv.getPersonnage());
			Labyrinthe.effaceEcran();
			li.affiche();
			li.effacePersonnage(p);
			System.out.println("Bravo, vous etes parvenu jusqu'a la sortie en comettant seulement "+(vies-p.getViesRestant())+" erreurs.\n\n");
			nouvellePartie(p);
		}else{
			li.effacePersonnage(p);
			p.droite();
			li.dessinePersonnage(p);
			Labyrinthe.effaceEcran();
			li.affiche();
			affichageEtatDuPersonnage(viesRestant, p);
		}
	}
	
	/**
	 * la méthode mouvementAGauche qui verifie la possibilite et puis fait le mouvement a gauche
	 * @param li
	 * @param lv
	 * @param p
	 * @param viesRestant
	 */
	public static void mouvementAGauche(Labyrinthe li,Labyrinthe lv, Personnage p, int viesRestant){
		int newCoorVer = p.getCoorVer();
		int newCoorHor = p.getCoorHor();
		if(newCoorHor<0){
			p.setViesRestant(p.getViesRestant()-1);
			Labyrinthe.effaceEcran();
			li.affiche();
		}else if(lv.muretVerticalExiste(newCoorVer,newCoorHor)){
			p.setViesRestant(p.getViesRestant()-1);
			li.dessineMuretVertical(newCoorVer,newCoorHor);
			Labyrinthe.effaceEcran();
			li.affiche();
		}else{
			li.effacePersonnage(p);
			p.gauche();
			li.dessinePersonnage(p);
			Labyrinthe.effaceEcran();
			li.affiche();
		}
	}
	
	/**
	 * la méthode mouvementVersHaut qui verifie la possibilite et puis fait le mouvement vers haut
	 * @param li
	 * @param lv
	 * @param p
	 * @param viesRestant
	 */
	public static void mouvementVersHaut(Labyrinthe li,Labyrinthe lv, Personnage p, int viesRestant){
		int newCoorVer = p.getCoorVer();
		int newCoorHor = p.getCoorHor();
		if(newCoorVer<0){
			p.setViesRestant(p.getViesRestant()-1);
			Labyrinthe.effaceEcran();
			li.affiche();
		}else if(lv.muretHorizontalExiste(newCoorVer,newCoorHor)){
			p.setViesRestant(p.getViesRestant()-1);
			li.dessineMuretHorizontal(newCoorVer,newCoorHor);
			Labyrinthe.effaceEcran();
			li.affiche();
		}else{
			li.effacePersonnage(p);
			p.haut();
			li.dessinePersonnage(p);
			Labyrinthe.effaceEcran();
			li.affiche();
		}
	}
	
	/**
	 * la méthode mouvementVersBas qui verifie la possibilite et puis fait le mouvement vers bas
	 * @param li
	 * @param lv
	 * @param p
	 * @param viesRestant
	 */
	public static void mouvementVersBas(Labyrinthe li,Labyrinthe lv, Personnage p, int viesRestant){
		int newCoorVer = p.getCoorVer()+1;
		int newCoorHor = p.getCoorHor();
		if(newCoorVer>=li.getH()){
			p.setViesRestant(p.getViesRestant()-1);
			Labyrinthe.effaceEcran();
			li.affiche();
		}else if(lv.muretHorizontalExiste(newCoorVer,newCoorHor)){
			p.setViesRestant(p.getViesRestant()-1);
			li.dessineMuretHorizontal(newCoorVer,newCoorHor);
			Labyrinthe.effaceEcran();
			li.affiche();
		}else{
			li.effacePersonnage(p);
			p.bas();
			li.dessinePersonnage(p);
			Labyrinthe.effaceEcran();
			li.affiche();
		}
	}
	
	/**
	 * la méthode sleep pour faire un pause durant execution du code
	 * @param millisecondes
	 */
	public static void sleep(long millisecondes){
        try {
            Thread.sleep(millisecondes);
        }
        catch(InterruptedException e){
            System.out.println("Sleep interrompu");
        }
    }
}
