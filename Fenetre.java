/**
 * Classe permettant d'afficher la fenetre principale
 * et de suivre les interractions des utilisateurs avec les touches du clavier et la souris
 * @author Guilhem Baissus
 */ 

import java.awt.Color;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Fenetre extends JFrame implements KeyListener, ActionListener, MouseListener {

    JPanel barreBoutons;
    Grille grille;
    JPanel monConteneurMain;

    // Les boutons de la barre du bas
    JButton pauseBouton;
    JButton playBouton;
    JButton restartBouton;

    // Barre de boutons de la moto1
    JPanel barreCouleursMoto1;
    JButton boutonVertMoto1;
    JButton boutonOrangeMoto1;
    JButton boutonJauneMoto1;
    JButton boutonRougeMoto1;
    JButton boutonBleuMoto1;

    // Barre de boutons de la moto2
    JPanel barreCouleursMoto2;
    JButton BoutonVertMoto2;
    JButton boutonOrangeMoto2;
    JButton boutonJauneMoto2;
    JButton boutonRougeMoto2;
    JButton boutonBleuMoto2;

    // JLabel de la fenetre
    JLabel scoreMoto1;
    JLabel scoreMoto2;
    JLabel compteurText;

    // Paramètres modifiables
    int tailleFenetre;
    int tailleGrille;
    int taillePixel;
    int hauteurBouton;
    int largeurBouton;
    int vitesse;
    int vitesseInitial;
    int changerVitesseMilli;

    // Relatif au temps
    Timer timer;
    int compteur;
    boolean timerActif;

    //Relatif à la souris
    int x0;
    int y0;

    String directionMoto1;
    String directionMoto2;

    /**
     * Le constructeur de la fenetre
     */
    public Fenetre() {

        super("Tron Game");

        // Paramètres modifiables
        tailleFenetre = 1000;       //1000
        tailleGrille = 700;         //700
        taillePixel = 10;           //10
        hauteurBouton = 70;         //70
        largeurBouton = 70;         //70
        vitesseInitial = 187;       //187
        changerVitesseMilli = 5000; //5000

        compteur = 0;
        Font font = new Font("Arial", Font.BOLD, 30);

        this.setSize(tailleFenetre, tailleFenetre);
        this.setLocationRelativeTo(null);

        // Les boutons et compteur de la barre du bas
        ImageIcon pauseBoutonImage = new ImageIcon("./images/pause.png");
        pauseBouton = new JButton();
        pauseBouton.setBounds((tailleFenetre - tailleGrille) / 2, 0, largeurBouton, hauteurBouton);
        pauseBouton.setIcon(resizeIcon(pauseBoutonImage, largeurBouton, hauteurBouton));
        pauseBouton.setBorder(BorderFactory.createEmptyBorder());
        pauseBouton.setContentAreaFilled(false);

        ImageIcon playImage = new ImageIcon("./images/play.png");
        playBouton = new JButton();
        playBouton.setBounds(tailleGrille + (tailleFenetre - tailleGrille)/2 - largeurBouton, 0, largeurBouton, hauteurBouton);
        playBouton.setIcon(resizeIcon(playImage, largeurBouton, hauteurBouton));
        playBouton.setBorder(BorderFactory.createEmptyBorder());
        playBouton.setContentAreaFilled(false);

        ImageIcon restartBoutonImage = new ImageIcon("./images/replay.png");
        restartBouton = new JButton();
        restartBouton.setBounds(tailleFenetre / 2 - 20, 0, largeurBouton, hauteurBouton);
        restartBouton.setIcon(resizeIcon(restartBoutonImage, largeurBouton, hauteurBouton));
        restartBouton.setBorder(BorderFactory.createEmptyBorder());
        restartBouton.setContentAreaFilled(false);

        compteurText = new JLabel();
        compteurText.setText("" + compteur);
        compteurText.setBounds(tailleFenetre - (tailleFenetre - tailleGrille) / 2, (tailleFenetre - tailleGrille) / 8,
                200, 100);
        compteurText.setForeground(Color.WHITE);
        compteurText.setFont(font);

        barreBoutons = new JPanel();
        barreBoutons.setLayout(null);
        barreBoutons.setBounds(0, (tailleFenetre - tailleGrille) / 2 + tailleGrille, tailleFenetre,
                (tailleFenetre - tailleGrille) / 2);
        barreBoutons.setBackground(Color.DARK_GRAY);
        barreBoutons.add(pauseBouton);
        barreBoutons.add(playBouton);
        barreBoutons.add(restartBouton);
        barreBoutons.add(compteurText);
        setContentPane(barreBoutons);

        // Initiation de la grille de jeu
        grille = new Grille(tailleGrille, taillePixel);
        grille.setLayout(null);
        grille.setBounds((tailleFenetre - tailleGrille) / 2, (tailleFenetre - tailleGrille) / 2, tailleGrille,
                tailleGrille);

        // Elements dans le conteneur principal
        JLabel Image = new JLabel();
        Image.setIcon(new ImageIcon("./images/fond.png"));
        Image.setBounds(0, 0, 1000, 1000);

        scoreMoto1 = new JLabel();
        scoreMoto1.setText("score : " + grille.getScoreMoto1());
        scoreMoto1.setBounds(tailleGrille + (tailleFenetre - tailleGrille)/2 -125, (tailleFenetre - tailleGrille) / 2 - 80, 200, 100);
        scoreMoto1.setForeground(Color.CYAN);
        scoreMoto1.setFont(font);

        scoreMoto2 = new JLabel();
        scoreMoto2.setText("score : " + grille.getScoreMoto2());
        scoreMoto2.setBounds((tailleFenetre - tailleGrille) / 2, (tailleFenetre - tailleGrille) / 2 - 80, 200, 100);
        scoreMoto2.setForeground(Color.RED);
        scoreMoto2.setFont(font);

        boutonVertMoto1 = new JButton();
        boutonVertMoto1.setBounds(0, 0, 20, 20);
        boutonVertMoto1.setBackground(Color.GREEN);
        boutonOrangeMoto1 = new JButton();
        boutonOrangeMoto1.setBounds(20, 0, 20, 20);
        boutonOrangeMoto1.setBackground(Color.ORANGE);
        boutonRougeMoto1 = new JButton();
        boutonRougeMoto1.setBounds(40, 0, 20, 20);
        boutonRougeMoto1.setBackground(Color.RED);
        boutonJauneMoto1 = new JButton();
        boutonJauneMoto1.setBounds(60, 0, 20, 20);
        boutonJauneMoto1.setBackground(Color.YELLOW);
        boutonBleuMoto1 = new JButton();
        boutonBleuMoto1.setBounds(80, 0, 20, 20);
        boutonBleuMoto1.setBackground(Color.BLUE);

        barreCouleursMoto1 = new JPanel();
        barreCouleursMoto1.setLayout(null);
        barreCouleursMoto1.setBounds(tailleFenetre - (tailleFenetre - tailleGrille) / 2, (tailleFenetre - tailleGrille) / 2, 100, 20);
        barreCouleursMoto1.add(boutonVertMoto1);
        barreCouleursMoto1.add(boutonOrangeMoto1);
        barreCouleursMoto1.add(boutonJauneMoto1);
        barreCouleursMoto1.add(boutonRougeMoto1);
        barreCouleursMoto1.add(boutonBleuMoto1);
        barreCouleursMoto1.setBackground(Color.WHITE);

        BoutonVertMoto2 = new JButton();
        BoutonVertMoto2.setBounds(0, 0, 20, 20);
        BoutonVertMoto2.setBackground(Color.GREEN);
        boutonOrangeMoto2 = new JButton();
        boutonOrangeMoto2.setBounds(20, 0, 20, 20);
        boutonOrangeMoto2.setBackground(Color.ORANGE);
        boutonRougeMoto2 = new JButton();
        boutonRougeMoto2.setBounds(40, 0, 20, 20);
        boutonRougeMoto2.setBackground(Color.RED);
        boutonJauneMoto2 = new JButton();
        boutonJauneMoto2.setBounds(60, 0, 20, 20);
        boutonJauneMoto2.setBackground(Color.YELLOW);
        boutonBleuMoto2 = new JButton();
        boutonBleuMoto2.setBounds(80, 0, 20, 20);
        boutonBleuMoto2.setBackground(Color.BLUE);

        barreCouleursMoto2 = new JPanel();
        barreCouleursMoto2.setLayout(null);
        barreCouleursMoto2.setBounds((tailleFenetre - tailleGrille) / 2 - 100, (tailleFenetre - tailleGrille) / 2, 100,
                20);
        barreCouleursMoto2.add(BoutonVertMoto2);
        barreCouleursMoto2.add(boutonOrangeMoto2);
        barreCouleursMoto2.add(boutonJauneMoto2);
        barreCouleursMoto2.add(boutonRougeMoto2);
        barreCouleursMoto2.add(boutonBleuMoto2);
        barreCouleursMoto2.setBackground(Color.WHITE);

        monConteneurMain = new JPanel();
        monConteneurMain.setLayout(null);
        monConteneurMain.add(barreBoutons);
        monConteneurMain.add(grille);
        monConteneurMain.add(scoreMoto1);
        monConteneurMain.add(scoreMoto2);
        monConteneurMain.add(barreCouleursMoto1);
        monConteneurMain.add(barreCouleursMoto2);
        monConteneurMain.add(Image);
        monConteneurMain.setBounds(0, 0, tailleFenetre, tailleFenetre);
        setContentPane(monConteneurMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Les evenements

        playBouton.addKeyListener(this);
        pauseBouton.addKeyListener(this);
        restartBouton.addKeyListener(this);
        boutonVertMoto1.addKeyListener(this);
        boutonRougeMoto1.addKeyListener(this);
        boutonJauneMoto1.addKeyListener(this);
        boutonBleuMoto1.addKeyListener(this);
        boutonOrangeMoto1.addKeyListener(this);
        BoutonVertMoto2.addKeyListener(this);
        boutonRougeMoto2.addKeyListener(this);
        boutonJauneMoto2.addKeyListener(this);
        boutonBleuMoto2.addKeyListener(this);
        boutonOrangeMoto2.addKeyListener(this);

        pauseBouton.addActionListener(this);
        playBouton.addActionListener(this);
        restartBouton.addActionListener(this);
        boutonVertMoto1.addActionListener(this);
        boutonRougeMoto1.addActionListener(this);
        boutonJauneMoto1.addActionListener(this);
        boutonBleuMoto1.addActionListener(this);
        boutonOrangeMoto1.addActionListener(this);
        BoutonVertMoto2.addActionListener(this);
        boutonRougeMoto2.addActionListener(this);
        boutonJauneMoto2.addActionListener(this);
        boutonBleuMoto2.addActionListener(this);
        boutonOrangeMoto2.addActionListener(this);

        // Initialisation du timer et de la vitesse des motos qui dépend du delay du
        // timer
        vitesse = vitesseInitial;
        timer = new Timer(vitesse, this);
        timerActif= false;

        //Initialisation des évènements souris
        addMouseListener(this);
        x0 = -1;
        y0 = -1;

        // On récupère les directions des motos initialisées dans la grille
        directionMoto1 = grille.getDirectionMoto1();
        directionMoto2 = grille.getDirectionMoto2();
    }

    //--------------------------------------------méthodes KeyListener ----------------------------------------------
    public void keyPressed(KeyEvent e) {

    }

    public void keyTyped(KeyEvent e) {
    }

    /**
     * Méthode permettant de prendre en compte les choix de direction des deux
     * joueurs
     */
    public void keyReleased(KeyEvent e) {

        if(timerActif){
            int touche = e.getKeyCode();

            switch (touche) {
            case KeyEvent.VK_UP:
                if (this.directionMoto1 != "DOWN")
                    this.directionMoto1 = "UP";
                break;
            case KeyEvent.VK_LEFT: // <-
                if (this.directionMoto1 != "RIGHT")
                    this.directionMoto1 = "LEFT";
                break;
            case KeyEvent.VK_RIGHT: // ->
                if (this.directionMoto1 != "LEFT")
                    this.directionMoto1 = "RIGHT";
                break;
            case KeyEvent.VK_DOWN:
                if (this.directionMoto1 != "UP")
                    this.directionMoto1 = "DOWN";
                break;
            case 90: // Z
                if (this.directionMoto2 != "DOWN")
                    this.directionMoto2 = "UP";
                break;
            case 81: // Q
                if (this.directionMoto2 != "RIGHT")
                    this.directionMoto2 = "LEFT";
                break;
            case 83: // S
                if (this.directionMoto2 != "UP")
                    this.directionMoto2 = "DOWN";
                break;
            case 68: // D
                if (this.directionMoto2 != "LEFT")
                    this.directionMoto2 = "RIGHT";
                break;
            }
        }
        
    }

    //--------------------------------------------------------------------------------------------------------

    /**
     * Méthode permettant de recommencer une partie sans modifier les scores
     */
    public void recommencerJeu() {

        grille.recommencerJeu();
        scoreMoto1.setText("score : " + grille.getScoreMoto1());
        scoreMoto2.setText("score : " + grille.getScoreMoto2());
        this.directionMoto1 = grille.getDirectionMoto1();
        this.directionMoto2 = grille.getDirectionMoto2();
        this.vitesse = vitesseInitial;
        this.compteur = 0;
        changerVitesse();
    }

    /**
     * Méthode s'activant aux moments d'évènements ou du délai du timer 
     * Vérifie si aucun des joueurs n'a perdu
     */
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == pauseBouton) {

            if(timerActif)
                pauseJeu();

        }
        else if (e.getSource() == playBouton) {

            if(!timerActif){
                repaint();
                timerActif = true;
                timer.start();
            }

        }
        else if (e.getSource() == restartBouton) {

            grille.initierScore();
            recommencerJeu();
            pauseJeu();
            repaint();
        }
        else if (e.getSource() == boutonVertMoto1) {
            grille.setColorMoto1(Color.GREEN);
            repaint();
        }
        else if (e.getSource() == boutonRougeMoto1) {
            grille.setColorMoto1(Color.RED);
            repaint();
        }
        else if (e.getSource() == boutonOrangeMoto1) {
            grille.setColorMoto1(Color.ORANGE);
            repaint();
        }
        else if (e.getSource() == boutonBleuMoto1) {
            grille.setColorMoto1(Color.BLUE);
            repaint();
        }
        else if (e.getSource() == boutonJauneMoto1) {
            grille.setColorMoto1(Color.YELLOW);
            repaint();
        }
        else if (e.getSource() == BoutonVertMoto2) {
            grille.setColorMoto2(Color.GREEN);
            repaint();
        }
        else if (e.getSource() == boutonRougeMoto2) {
            grille.setColorMoto2(Color.RED);
            repaint();
        }
        else if (e.getSource() == boutonOrangeMoto2) {
            grille.setColorMoto2(Color.ORANGE);
            repaint();
        }
        else if (e.getSource() == boutonBleuMoto2) {
            grille.setColorMoto2(Color.BLUE);
            repaint();
        }
        else if (e.getSource() == boutonJauneMoto2) {
            grille.setColorMoto2(Color.YELLOW);
            repaint();
        } else {
            this.compteur++;
            compteurText.setText("" + this.compteur);
            if (grille.avancerMotos(this.directionMoto1, this.directionMoto2)) {
                repaint();
            } else {
                pauseJeu();
                recommencerJeu();
            }
            // Fait accélerer les motos toutes les 5 secondes
            if (this.compteur == this.changerVitesseMilli / this.vitesse) {
                changerVitesse();
                this.compteur = 0;
            }
        }
    }

    /**
     * Met en pause le jeu
     */
    private void pauseJeu() {
        timerActif=false;
        timer.stop();
    }

    /**
     * Permet de faire accélerer les motos lorsque cette méthode est appelée
     */
    public void changerVitesse() {

        this.vitesse -= this.vitesse / 10;
        timer.setDelay(vitesse);
    }

    //--------------------------------------------méthodes MouseListener ----------------------------------------------
    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        this.x0=e.getX();
        this.y0=e.getY();
    }

    public void mouseReleased(MouseEvent e) {
        changerDirectionSouris(e);

    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    //---------------------------------------------------------------------------------------------------------------------

    /**
     * Prend en compte la pression sur la souris et le relachement pour définir une direction
     */
    public void changerDirectionSouris(MouseEvent e){

        if(timerActif){
            int x1 = e.getX();
            int y1 = e.getY();

            int deltaX = x1 - this.x0;
            int deltaY = y1 - this.y0;

            if(Math.abs(deltaX) > Math.abs(deltaY)){

                if(deltaX >0){
                    if(this.directionMoto1!="LEFT")
                        this.directionMoto1 = "RIGHT";
                }
                if(deltaX < 0){
                    if(this.directionMoto1!="RIGHT")
                        this.directionMoto1 = "LEFT";
                }
            }else{
                if(deltaY >0){
                    if(this.directionMoto1!="UP")
                        this.directionMoto1 = "DOWN";
                }
                if(deltaY<0){
                    if(this.directionMoto1!="DOWN")
                        this.directionMoto1 = "UP";
                }
            }
            
        }
    }

    /**
     * Permet de faire que les images s'adaptent à la taille du bouton 
     * issu de : //https://stackoverflow.com/questions/36957450/fit-size-of-an-imageicon-to-a-jbutton
     */
    private static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
}
        
