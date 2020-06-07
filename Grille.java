/**
 * Classe permet d'afficher dans une zone precise la grille de jeu
 * @author Guilhem Baissus
 */ 

import java.awt.*;
import javax.swing.*;
import java.awt.Color;

public class Grille extends JPanel{

    int jeu[][];
    int tailleGrille;
    int taillePixel;

    Moto moto1;
    Moto moto2;


    /**
	 * Le constructeur de la grille
	 */ 
    public Grille(int tailleGrille, int taillePixel){
        this.tailleGrille=tailleGrille;
        this.taillePixel = taillePixel;
        moto1 = new Moto((tailleGrille-1*taillePixel)/taillePixel,(tailleGrille-1*taillePixel)/taillePixel, Color.CYAN, "UP");
        moto2 = new Moto(0,0, Color.RED, "DOWN");
        jeu = new int [tailleGrille/taillePixel][tailleGrille/taillePixel]; 

    }

    /**
	 * Méthode permettant de vérifier si les motos sont en dehors de la grille
     * retourne le numéro de la moto qui est sorti
     * retourne 3 dans le cas où les deux motos sont sorties
	 */ 
     public int enDehors(){

        int motoQuiSort = 0;

        if(moto1.getPositionX()<=-1 || moto1.getPositionX() >= (tailleGrille)/taillePixel || moto1.getPositionY()<=-1 || moto1.getPositionY() >= (tailleGrille)/taillePixel){
            motoQuiSort =1;
        }
        if(moto2.getPositionX()<=-1 || moto2.getPositionX() >= (tailleGrille)/taillePixel || moto2.getPositionY()<=-1 || moto2.getPositionY() >= (tailleGrille)/taillePixel){
            if(motoQuiSort == 1)
                motoQuiSort = 3;
            else
                motoQuiSort = 2;
        }
        return motoQuiSort;
    }
    
    /**
	 * Méthode permettant de vérifier si les motos sont rentrées en collision
     * prend en paramètre le numéro de la moto qui sort pour empêcher un dépassement de tableau dans le cas où la moto sort de la grille
     * retourne le numéro de la moto qui est rentrée en collision
     * retourne 3 dans le cas où les deux motos sont rentrées en collision
	 */ 
    public int collision(int motoQuiSort){

        int motoCollision = 0;

        if(motoQuiSort !=1 && motoQuiSort !=3){
              //if the head of moto1 enters into its own trail or moto2's trail
              if((jeu[moto1.getPositionX()][moto1.getPositionY()] == 1)|| (jeu[moto1.getPositionX()][moto1.getPositionY()]==2)){
                motoCollision = 1;
            }
        }
        if(motoQuiSort !=2 && motoQuiSort!=3){
            //if the head of moto2 enters into its own trail or moto1's trail
            if((jeu[moto2.getPositionX()][moto2.getPositionY()] == 1)|| (jeu[moto2.getPositionX()][moto2.getPositionY()]==2)){
                if(motoCollision ==1)
                    motoCollision = 3;
                else
                    motoCollision = 2;
            }
        }
        //if the heads enter in contact
        if(moto1.getPositionX()==moto2.getPositionX() && moto1.getPositionY()==moto2.getPositionY()){
            motoCollision = 3;
        }

        return motoCollision;
    }

    /**
	 * Méthode permettant de faire avancer les motos en vérifiant les collisions et les sorties de grille
     * Augmente les scores des motos en fonction de ces évènements
     * Prend en paramètres les directions des deux motos envoyées depuis l'interface graphique
     * Retourne un boolean dépendant de si les motos peuvent continuer à avancer
	 */ 
    public boolean avancerMotos(String directionMoto1, String directionMoto2){

        int motoQuiSort = enDehors();
        int collision = collision(motoQuiSort);
        //On vérifie si on sort de la grille
        if(motoQuiSort != 0 || collision !=0){
           
           if(motoQuiSort == 1 || collision ==1) {
               moto2.augmenterScore();
           }
           if(motoQuiSort == 2 || collision == 2){
               moto1.augmenterScore();
           }
           if(motoQuiSort == 3 || collision == 3 || (motoQuiSort==2 && collision ==1)|| (motoQuiSort==1 && collision==2)){
               moto1.augmenterScore();
               moto2.augmenterScore();
           }
           return false;
        }
        
        jeu[moto1.getPositionX()][moto1.getPositionY()]=1;
        moto1.avancer(directionMoto1);

        jeu[moto2.getPositionX()][moto2.getPositionY()]=2;
        moto2.avancer(directionMoto2);

        return true;
    }

    /**
	 * Méthode permettant de réinitialiser le jeu 
	 */ 
    
    public void recommencerJeu(){
        jeu = new int [tailleGrille/taillePixel][tailleGrille/taillePixel]; 
        moto1.setPosition((tailleGrille-1*taillePixel)/taillePixel,(tailleGrille-1*taillePixel)/taillePixel);
        moto2.setPosition(0, 0);
        moto1.setDirection("UP");
        moto2.setDirection("DOWN");

    }

    /**
	 * Méthode permettant de réinitialiser les scores
	 */ 
    public void initierScore(){
        moto1.setScore(0);
        moto2.setScore(0);
    }
     
    /**
	 * Méthode paint permettant de dessiner les mouvements des joueurs dans la grille
	 */ 
    public void paint(Graphics g){

        g.setColor(Color.BLACK);
        g.fillRect(0,0,getWidth(), getHeight());
        
        for(int i =0; i<tailleGrille/taillePixel; i++){
            for(int j=0; j<tailleGrille/taillePixel; j++){

                /*
                je parcous la grille et je mets d'une certaine couleur
                */
                if(jeu[i][j]==1){
                    g.setColor((moto1.getColor()));
                    g.fillRect(i*taillePixel, j*taillePixel,taillePixel-1,taillePixel-1);
                }
                if(jeu[i][j]==2){
                    g.setColor(moto2.getColor());
                    g.fillRect(i*taillePixel, j*taillePixel,taillePixel-1,taillePixel-1);
                } 
                
            } 
        }
        g.setColor(Color.WHITE);
        g.fillRect(moto1.positionX*taillePixel, moto1.positionY*taillePixel,taillePixel,taillePixel);
        g.fillRect(moto2.positionX*taillePixel, moto2.positionY*taillePixel,taillePixel,taillePixel);
    }

    //setters
    public void setColorMoto1(Color color){
        moto1.setColor(color);
    }
    public void setColorMoto2(Color color){
        moto2.setColor(color);
    }

    //getters
    public int getScoreMoto1(){return moto1.getScore();}
    public int getScoreMoto2(){return moto2.getScore();}
    public String getDirectionMoto1(){return moto1.getDirection();}
    public String getDirectionMoto2(){return moto2.getDirection();}
}