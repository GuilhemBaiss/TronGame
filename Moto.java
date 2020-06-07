/**
 * Classe de l'objet Moto
 * @author Guilhem Baissus
 */ 

import java.awt.Color;

public class Moto{

    int positionX;
    int positionY;
    int score;
    String direction;
    Color color;

    /**
     * Constructeurs de la classe
    */ 
    public Moto(int positionX, int positionY, String direction){

        initier(positionX, positionY);
        this.color = Color.GREEN;
        this.direction = direction;
    }

    public Moto(int positionX, int positionY, Color couleur, String direction){
        
        initier(positionX, positionY);
        this.color = couleur;
        this.direction = direction;
    }

    public void initier(int positionX, int positionY){

        this.positionX = positionX;
        this.positionY = positionY;
        this.score = 0;

    }

    //getters
    public int getPositionX(){return this.positionX;}
    public int getPositionY(){return this.positionY;}
    public int getScore(){return this.score;}
    public Color getColor(){return this.color;}
    public String getDirection(){return this.direction;}

    //setters
    public void setPosition(int positionX, int positionY){
        this.positionX = positionX;
        this.positionY = positionY;
    }
    public void setColor(Color color){
        this.color = color;
    }

    public void setDirection(String direction){
        this.direction=direction;
    }

    public void setScore(int score){
        this.score = score;
    }

    //other methods
    public void augmenterScore(){
        this.score ++;
    }
    public void initierScore(){
        this.score = 0;
    }

    public void avancer(String direction){

        setDirection(direction);
        switch (direction) 
        {
            case "UP": // si la touche enfoncée est celle du haut
                this.positionY --;
                break;
            case "LEFT": // si la touche enfoncée est celle de gauche
                this.positionX--;
                break;
            case "RIGHT": // si la touche enfoncée est celle de droite
                this.positionX++;
                break;
            case "DOWN": // si la touche enfoncée est celle du bas
                this.positionY++;
                break;
        }

    }
}