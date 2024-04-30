package BattleShip.src;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



import javax.swing.*;

import BattleShip.src.ImageHandler.ImageName;

public class SetupScreen {

    private JFrame frame;
    JPanel screen;
    public JLayeredPane gameSpace;
    private boolean vert = false;
    private Cell[] shipCells = new Cell[5];
    private boolean[] shipVerts = new boolean[5];

    private int difficulty;

    private static final JLabel[] SHIPS = {new JLabel(new ImageIcon(ImageHandler.getImage(ImageName.AIRCRAFTCARRIER))),
        new JLabel(new ImageIcon(ImageHandler.getImage(ImageName.BATTLESHIP))),
        new JLabel(new ImageIcon(ImageHandler.getImage(ImageName.CRUISER))),
        new JLabel(new ImageIcon(ImageHandler.getImage(ImageName.SUBMARINE))),
        new JLabel(new ImageIcon(ImageHandler.getImage(ImageName.DESTROYER)))};

    private static final JLabel[] SHIPS_VERT = {new JLabel(new ImageIcon(ImageHandler.getImage(ImageName.AIRCRAFTCARRIER_VERT))),
        new JLabel(new ImageIcon(ImageHandler.getImage(ImageName.BATTLESHIP_VERT))),
        new JLabel(new ImageIcon(ImageHandler.getImage(ImageName.CRUISER_VERT))),
        new JLabel(new ImageIcon(ImageHandler.getImage(ImageName.SUBMARINE_VERT))),
        new JLabel(new ImageIcon(ImageHandler.getImage(ImageName.DESTROYER_VERT)))};

    public SetupScreen(JFrame fr, int difficulty){
        
        frame = fr;

        screen = new JPanel(new GridBagLayout());

        screen.setPreferredSize(new Dimension(1920,1080));

        this.difficulty = difficulty;
        
    }

    public void view(){

        GridBagConstraints cL = new GridBagConstraints();
        GridBagConstraints cR = new GridBagConstraints();

        gameSpace = new JLayeredPane();

        JLabel boards = new JLabel(new ImageIcon(ImageHandler.getImage(ImageName.BOARDS)));

        JPanel rightPanel = new JPanel(new GridBagLayout());

        JPanel top = new JPanel(new GridLayout(1,3));

        JButton exitButton = new JButton(new ImageIcon(ImageHandler.getImage(ImageName.EXIT)));

        exitButton.setBorder(BorderFactory.createLineBorder(new Color(000000),2, true));

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                System.exit(0);
            }
        });

        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                exitButton.setIcon(new ImageIcon(ImageHandler.getImage(ImageName.EXIT_CLICK)));
            }

            @Override 
            public void mouseExited(MouseEvent e){
                super.mouseExited(e);
                exitButton.setIcon(new ImageIcon(ImageHandler.getImage(ImageName.EXIT)));
            }
        });

        JLabel instructions = new JLabel(new ImageIcon(ImageHandler.getImage(ImageName.INSTRUCTIONS)));

        instructions.setBounds(0,0,640,1000);

        GridBagConstraints itext = new GridBagConstraints();

        itext.ipadx = 0;
        itext.ipady = 0;
        itext.fill = GridBagConstraints.BOTH;
        itext.gridheight = 2;
        itext.gridwidth = 1;
        itext.weightx = 1.0;
        itext.weighty = 1.0;
        itext.gridx = 0;
        itext.gridy = 0;

        GridBagConstraints topConstraints = new GridBagConstraints();

        topConstraints.ipadx = 0;
        topConstraints.ipady = 0;
        topConstraints.gridheight = 1;
        topConstraints.gridwidth = 1;
        topConstraints.gridx = 0;
        topConstraints.gridy = 0;

        top.setBackground(new Color(0,0,0,0));
        top.add(new JSeparator());
        top.add(new JSeparator());
        top.add(exitButton);

        top.setPreferredSize(new Dimension(640,80));

        top.setMaximumSize(new Dimension(640,80));

        rightPanel.add(top,topConstraints);

        rightPanel.add(instructions,itext);

        boards.setBounds(0, 0, 1280 , 1080);
        gameSpace.add(boards,new Integer(0));


        int[] pos = {211,168,126,126,81};

        for (int i = 0; i < 5; i++){
            SHIPS[i].setBounds(0, 0, pos[i],42);
            SHIPS_VERT[i].setBounds(0,0,42,pos[i]);
            SHIPS[i].setVisible(!vert);
            SHIPS_VERT[i].setVisible(vert);
            gameSpace.add(SHIPS[i], new Integer(3));
            gameSpace.add(SHIPS_VERT[i],new Integer(3));
        }

        gameSpace.addMouseMotionListener(new MouseAdapter() {
  
            @Override
            public void mouseMoved(MouseEvent e){
                int shipCount = gameSpace.getComponentCountInLayer(-1);
                SHIPS_VERT[shipCount].setLocation(e.getX()-21, e.getY()-21);
                SHIPS[shipCount].setLocation(e.getX()-21, e.getY()-21);
                gameSpace.repaint();
                gameSpace.revalidate();
            }
        });

        
        gameSpace.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("R"), "swapVert");
        gameSpace.getActionMap().put("swapVert", new swapVert());

        gameSpace.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e){
                int x = e.getX();
                int y = e.getY();
                Cell targetCell = Cell.getCell(x, y, false);
                int shipCount = gameSpace.getComponentCountInLayer(-1);
                int offset;
                switch (shipCount){
                    case 0: offset = 6; break;
                    case 1: offset = 7; break;
                    case 2: offset = 8; break;
                    case 3: offset = 8; break;
                    case 4: offset = 9; break;
                    default: offset = 0;
                }
                if (Game.validPlacement(shipCount, targetCell, vert, shipCells, shipVerts) && (Game.bounds(x, y, false) && vert ? targetCell.row() < offset : targetCell.col() < offset && targetCell.row() > -1 && targetCell.col() > -1)){
                    shipCells[shipCount] = targetCell;
                    shipVerts[shipCount] = vert;
                    vert = vert ? !vert : vert;
                    if (shipCount == 4){
                        Game.setShips(shipCells,shipVerts);
                        frame.getContentPane().removeAll();
                        App.startGame(frame,difficulty);
                    }else{
                        gameSpace.add(new JLabel(), new Integer(-1));
                    }
                }
            }
        });

        cL.ipadx = 0;
        cL.ipady = 0;
        cL.fill = GridBagConstraints.BOTH;
        cL.gridheight = 3;
        cL.gridwidth = 2;
        cL.weightx = 1.0;
        cL.weighty = 1.0;
        cL.gridx = 0;
        cL.gridy = 0;

        screen.add(gameSpace,cL);

        cR.ipadx = 0;
        cR.ipady = 0;
        cR.gridwidth = 1;
        cR.gridheight = 1;
        cR.gridy = 0;
        cR.gridx = 2;

        screen.add(rightPanel,cR);

        update();
    }

    private class swapVert extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e){
            vert = !vert;
            SHIPS[gameSpace.getComponentCountInLayer(-1)].setVisible(!vert);
            SHIPS_VERT[gameSpace.getComponentCountInLayer(-1)].setVisible(vert);
        }
    }


    public void update(){
        frame.add(screen);

        frame.repaint();
        frame.revalidate();

        frame.setVisible(true);
    }

}
