import java.awt.*;
import javax.swing.*;
import java.awt.Color.*;
import javax.swing.JPanel.*;
import java.awt.event.*;
import java.util.*;

/**
 *The aim of the game is for a player to click on a square and hopefully avoid a bomb that is hidden under the square.
 *Each time a player clicks on a square and the bomb is not on that square, the player gains a point.
 *The player continues to receive points until the square with the bomb is clicked and then a player loses the game.
 *A player can choose difficulty levels, where the number of clicks on a square is reduced (to make it easy for a player wants to win the game).
 *
 * @author (Seyed Omar Moulana)
 * @version (26th March 2020)
 */

public class Game extends JFrame implements ActionListener
{
    private JPanel mid = new JPanel();    
    private JPanel right = new JPanel();
    
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
        
    private final int ROWS = 2;
    private final int COLS = 5;
    private final int GAP = 3;
    private final int NUM = ROWS * COLS;
    private int count = 1;
    private int win = 0;
    private Random randRow = new Random();
    private Random randCol =  new Random();
    
    private JButton[][] leftButton = new JButton[ROWS][COLS];
    
    private JPanel left = new JPanel();
    private Color col1 = Color.RED;
    private Color col2 = Color.BLUE;
    private Color col3 = Color.GREEN;
    private Color col4 = Color.YELLOW;
    
    public Game()
    {
        super("Evade The Bomb");
        setSize(400,300);
        makeFrame();
    }
    
    /**
     * makeFrame - used to make 3 panels with Jbuttons for squares.
     * 1st panel is made with 2 for loops,
     * to go through each cell(1st row and then all the col,2nd row then all the col).
     * 2nd/3rd is a simple panel.
     */
    public void makeFrame()
    {  
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridLayout());
        left.setLayout(new GridLayout(ROWS, COLS));
        
        for(int r = 0; r < ROWS; r++){
            for(int c = 0; c < COLS; c++){
                leftButton[r][c] = new JButton();
                left.add(leftButton[r][c]).setBackground(col1);
                leftButton[r][c].addActionListener(this);
            }        
        }
        
        add(left);
        
        add(mid).setBackground(col2);
        add(right).setBackground(col3);
        
        button1 = new JButton("Play A Game");
        button2 = new JButton("Exit");
        button3 = new JButton("Easy");
        button4 = new JButton("Intermediate");
        button5 = new JButton("Difficult");
        
        mid.add(button1);
        mid.add(button2);
        right.add(button3);
        right.add(button4);
        right.add(button5);        
        
        button1.addActionListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this);
        button4.addActionListener(this);
        button5.addActionListener(this);
        getBomb();
        setVisible(true);
    }
    
    /**
     * Restarts everything(win condition, all squares and score)
     * except the size because you'd have to resize it everytime you play a game.
     */
    public void Restart()
    {
        left.removeAll();
        mid.removeAll();
        right.removeAll();
        makeFrame();
        revalidate();
        repaint();
    }
    
    /**
     * disable buttons and take the bomb name away
     * from the bomb square.
     * Used when user either wins or loses.
     */
    public void disableButtons()
    {
        for(int r = 0; r < ROWS; r++){
            for(int c = 0; c < COLS; c++){
                leftButton[r][c].setEnabled(false);
                if(leftButton[r][c].getName() == "Bomb"){leftButton[r][c].setName("");}
            }              
        }          
    }
    
    /**
     * A while loop to go through all squares until
     * it finds a random square that is not pressed(not the color yellow), and sets the name as bomb.
     * breaks loop ones it does.
     */
    public void getBomb()
    {
        int breakLoop = 1;
  
        int rowNum = randRow.nextInt(2);
        int colNum = randCol.nextInt(5);
        //do a while loop, and if the background is not col4 set name to it as "bomb", then compare source with the text of button.
        while(breakLoop == 1)
        {
            int x = randRow.nextInt(2);
            int y = randCol.nextInt(5);
            if(leftButton[x][y].getBackground() != col4){leftButton[x][y].setName("Bomb"); breakLoop = 0;}
        }
    }
    
    /**
     * ActionListeners to change color, count scores,
     * and bomb feature.
     * Has a win condition based on difficulty.
     * score increases count when a red square changes to yellow.
     * getName of the square(button) to see if it has the name bomb,
     * if not it changes color to yellow if its already not yellow.
     * score doesn't increase when clicked on a yellow square.
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();
        for(int r = 0; r < ROWS; r++){
            for(int c = 0; c < COLS; c++){
                if(source == leftButton[r][c] && leftButton[r][c].getName() == "Bomb"){
                    System.out.println("You lose - your score is " + count);
                    disableButtons();
                }
                else if(source == leftButton[r][c] && leftButton[r][c].getBackground() == col1 && leftButton[r][c].getName() != "Bomb"){
                     leftButton[r][c].setBackground(col4);
                     count++;            
                }     
            }              
        }      
       
        if(source == button1){ count = 0; Restart();}
        if(source == button2){System.exit(0);}
        
        if(source == button3){win = 5; count = 0;}
        else if(source == button4){win = 7; count = 0;}
        else if(source == button5){win = 9; count = 0;}

        if(count == win && win != 0){System.out.println("You win - your score is " + count); count = 0; disableButtons();}
               
    }
}
    