package BattleShip.src;

import java.awt.Color;
import java.awt.Component;
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

public class GameScreen {

    private JFrame frame;
    JPanel screen;
    public JLayeredPane gameSpace;
    private Game game;

    JLabel turnInfo;

    JLabel userHit;

    JLabel compHit;

    public GameScreen(JFrame fr, Game g){
        this.game = g;
        
        frame = fr;
    }

    public void view(){

        screen = new JPanel(new GridBagLayout());

        screen.setPreferredSize(new Dimension(1920,1080));

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

        top.setBackground(new Color(0,0,0,0));
        top.add(new JSeparator());
        top.add(new JSeparator());
        top.add(exitButton);

        GridBagConstraints topConstraints = new GridBagConstraints();

        topConstraints.ipadx = 0;
        topConstraints.ipady = 0;
        topConstraints.gridheight = 1;
        topConstraints.gridwidth = 1;
        topConstraints.gridx = 0;
        topConstraints.gridy = 0;

        rightPanel.add(top,topConstraints);

        GridBagConstraints infoConstraints = new GridBagConstraints();

        infoConstraints.ipadx = 0;
        infoConstraints.ipady = 0;
        infoConstraints.fill = GridBagConstraints.BOTH;
        infoConstraints.gridheight = 2;
        infoConstraints.gridwidth = 1;
        infoConstraints.weightx = 1.0;
        infoConstraints.weighty = 1.0;
        infoConstraints.gridx = 0;
        infoConstraints.gridy = 0;

        turnInfo = new JLabel(new ImageIcon(ImageHandler.getImage(game.turn() ? ImageName.GO_FIRST : ImageName.GO_SECOND)));

        turnInfo.setBounds(0,0,640,413);

        rightPanel.add(turnInfo,infoConstraints);


        userHit = new JLabel(new ImageIcon(ImageHandler.getHits(0, true)));

        compHit = new JLabel(new ImageIcon(ImageHandler.getHits(0, false)));

        userHit.setBounds(0,0,640,355);

        compHit.setBounds(0,0,640,311);

        infoConstraints.gridheight = 1;
        infoConstraints.gridy = 2;

        rightPanel.add(compHit,infoConstraints);

        infoConstraints.gridy = 3;

        rightPanel.add(userHit,infoConstraints);

        boards.setBounds(0, 0, 1280 , 1080);
        gameSpace.add(boards,new Integer(0));

        if (!game.turn()){
            game.compTurn();
        }

        gameSpace.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getComponent() == gameSpace){
                    int x = e.getX();
                    int y = e.getY();
                    if (game.bounds(x,y) && game.playing()){
                        turnInfo.setIcon(new ImageIcon(ImageHandler.getImage(ImageName.BLANK_INFO)));
                        switch (game.takeTurn(x,y)){
                            case "Battleship": turnInfo.setIcon(new ImageIcon(ImageHandler.getImage(ImageName.C_BATTLESHIP)));break;
                            case "Carrier": turnInfo.setIcon(new ImageIcon(ImageHandler.getImage(ImageName.C_AIRCRAFTCARRIER)));break;
                            case "Cruiser": turnInfo.setIcon(new ImageIcon(ImageHandler.getImage(ImageName.C_CRUISER)));break;
                            case "Submarine": turnInfo.setIcon(new ImageIcon(ImageHandler.getImage(ImageName.C_SUBMARINE)));break;
                            case "Destroyer": turnInfo.setIcon(new ImageIcon(ImageHandler.getImage(ImageName.C_DESTROYER)));break;
                            default: break;
                        }
                        switch(game.compTurn()[1]){
                            case "Battleship": turnInfo.setIcon(new ImageIcon(ImageHandler.getImage(ImageName.U_BATTLESHIP)));break;
                            case "Carrier": turnInfo.setIcon(new ImageIcon(ImageHandler.getImage(ImageName.U_AIRCRAFTCARRIER)));break;
                            case "Cruiser": turnInfo.setIcon(new ImageIcon(ImageHandler.getImage(ImageName.U_CRUISER)));break;
                            case "Submarine": turnInfo.setIcon(new ImageIcon(ImageHandler.getImage(ImageName.U_SUBMARINE)));break;
                            case "Destroyer": turnInfo.setIcon(new ImageIcon(ImageHandler.getImage(ImageName.U_DESTROYER)));break;
                            default: break;
                        }
                        updateBoards();
                    }
                    if (!game.playing()){
                        turnInfo.setIcon(new ImageIcon(ImageHandler.getImage(game.getWinner() ? ImageName.LOSE_TEXT : ImageName.WIN_TEXT)));
                        gameSpace.removeMouseListener(this);
                    }
                    gameSpace.repaint();
                    gameSpace.revalidate();
                }
            }
        });


        cL.ipadx = 0;
        cL.ipady = 0;
        cL.fill = GridBagConstraints.BOTH;
        cL.gridheight = 3;
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



        updateBoards();
        update();
    }

    private void updateBoards(){
        Component[] comps = gameSpace.getComponentsInLayer(4);
        for (Component cp : comps){
            gameSpace.remove(cp);
        }
        for (int row = 0; row < 10; row++){
            for (int col = 0; col < 10; col++){
                ImageHandler.ImageName stat = game.getCellStatus(row, col, true);
                if (stat != null){
                    JLabel mark = new JLabel(new ImageIcon(ImageHandler.getImage(stat)));
                    mark.setBounds(0, 0, 853 + (86*col) + col, 1240 + (86*row) +row);
                    gameSpace.add(mark, new Integer(4));
                }
                ImageHandler.ImageName compStat = game.getCellStatus(row, col, false);
                if ( compStat != null){
                    JLabel compMark = new JLabel(new ImageIcon(ImageHandler.getImage(compStat)));
                    compMark.setBounds(0, 0, 853 + (86*col) + col, 206 + (86*row) +row);
                    gameSpace.add(compMark, new Integer(4));
                }
            }
        }

        Object[][] userShips = game.getShips(true);

        Object[][] compShips = game.getShips(false);


        Component[] c = gameSpace.getComponentsInLayer(3);
            for (Component cp : c){
                gameSpace.remove(cp);
        }

        int userSunkCount = 0;
        int compSunkCount = 0;

        for (int s = 0; s < 5; s++){
            if (userShips[s][0] != null){
                JLabel uShip = new JLabel(new ImageIcon(ImageHandler.getImage((ImageName)userShips[s][0])));
                Move uCell = (Move)userShips[s][1];
                if ((boolean)userShips[s][2]){
                    uShip.setBounds(406 + (int)(43.5*(uCell.col())), 597 + (int)(43.5*uCell.row()),(int)userShips[s][3],42);
                }else{
                    uShip.setBounds(406 + (int)(43.5*(uCell.col())), 597 + (int)(43.5*uCell.row()),42,(int)userShips[s][3]);
                }
                gameSpace.add(uShip,new Integer(3));
                if ((char)userShips[s][4] != 'o' && (char)userShips[s][4] != 'x' && (char)userShips[s][4] != 'H'){
                    userSunkCount++;
                }
            }
            if (compShips[s][0] != null){
                JLabel cShip = new JLabel(new ImageIcon(ImageHandler.getImage((ImageName)compShips[s][0])));
                Move cCell = (Move)compShips[s][1];
                if ((boolean)compShips[s][2]){
                    cShip.setBounds(406 + (int)(43.5*(cCell.col())), 82 + (int)(43.5*cCell.row()),(int)compShips[s][3],42);
                }else{
                    cShip.setBounds(406 + (int)(43.5*(cCell.col())), 82 + (int)(43.5*cCell.row()),42,(int)compShips[s][3]);
                }
                gameSpace.add(cShip,new Integer(2));
                if ((char)compShips[s][4] != 'o' && (char)compShips[s][4] != 'x' && (char)compShips[s][4] != 'H'){
                    compSunkCount++;
                }
            }
        }
        compHit.setIcon(new ImageIcon(ImageHandler.getHits(compSunkCount,false)));
        userHit.setIcon(new ImageIcon(ImageHandler.getHits(userSunkCount,true)));
    }

    public void update(){
        frame.add(screen);

        frame.repaint();
        frame.revalidate();
        // add components and stuff
        frame.setVisible(true);
    }

}
