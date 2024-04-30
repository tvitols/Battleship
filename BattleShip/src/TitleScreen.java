package BattleShip.src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.*;

import BattleShip.src.ImageHandler.ImageName;


public class TitleScreen extends JFrame{
    
    private JFrame frame;

    private int difficulty = 0;

    public TitleScreen(JFrame frame){
        this.frame = frame;
    }

    public void draw() throws IOException{

        JLayeredPane screen = new JLayeredPane();

        screen.setPreferredSize(new Dimension(1920,1080));

        JButton startButton = new JButton(new ImageIcon(ImageHandler.getImage(ImageName.START)));
        startButton.setBackground(new Color(36,44,36));
        startButton.setBorder(BorderFactory.createEmptyBorder());
        startButton.setBounds(690,450,548,132);
        
        JLabel compScreen = new JLabel(new ImageIcon(ImageHandler.getImage(ImageName.SCREEN)));

        compScreen.setPreferredSize(new Dimension(1920,1080));

        compScreen.setBounds(0,0,1920,1080);

        screen.add(compScreen,new Integer(0));

        screen.add(startButton,new Integer(1));

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                App.setupGame(frame,difficulty);
            }
        });

        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                startButton.setIcon(new ImageIcon(ImageHandler.getImage(ImageName.START_CLICK)));
            }

            @Override 
            public void mouseExited(MouseEvent e){
                super.mouseExited(e);
                startButton.setIcon(new ImageIcon(ImageHandler.getImage(ImageName.START)));
            }
        });

        JButton settingsButton = new JButton(new ImageIcon(ImageHandler.getImage(ImageName.EASY)));

        settingsButton.setBorder(BorderFactory.createEmptyBorder());

        settingsButton.setBackground(new Color(36,44,36));

        settingsButton.setBounds(690,650,548,132);

        ImageIcon[] difficultyIcons = {new ImageIcon(ImageHandler.getImage(ImageName.EASY)),new ImageIcon(ImageHandler.getImage(ImageName.HARD))};

        ImageIcon[] difficultyHoverIcons = {new ImageIcon(ImageHandler.getImage(ImageName.EASY_HOVER)),new ImageIcon(ImageHandler.getImage(ImageName.HARD_HOVER))};

        settingsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                settingsButton.setIcon(difficultyHoverIcons[difficulty]);
            }

            @Override 
            public void mouseExited(MouseEvent e){
                super.mouseExited(e);
                settingsButton.setIcon(difficultyIcons[difficulty]);
            }
        });

        
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt){
                if (difficulty == 1){
                    difficulty = 0;
                }else{
                    difficulty ++;
                }
                settingsButton.setIcon(difficultyHoverIcons[difficulty]);
            }
                
        });

        screen.add(settingsButton,new Integer(1));

        frame.add(screen);

        frame.repaint();
        frame.revalidate();

        frame.setVisible(true);
        
    }
}
