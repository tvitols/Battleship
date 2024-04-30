package BattleShip.src;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageHandler {
    public static enum ImageName {
        START,START_CLICK,BOARDS,EXIT,EXIT_CLICK,SCREEN,EASY,EASY_HOVER,HARD,HARD_HOVER,
        INSTRUCTIONS,HIT_TEXT,WIN_TEXT,LOSE_TEXT,GO_FIRST,GO_SECOND,BLANK_INFO,
        C_DESTROYER,C_AIRCRAFTCARRIER,C_BATTLESHIP,C_SUBMARINE,C_CRUISER,
        U_DESTROYER,U_AIRCRAFTCARRIER,U_BATTLESHIP,U_SUBMARINE,U_CRUISER,
        AIRCRAFTCARRIER,AIRCRAFTCARRIER_VERT,BATTLESHIP,BATTLESHIP_VERT,CRUISER,CRUISER_VERT,DESTROYER,DESTROYER_VERT,SUBMARINE,SUBMARINE_VERT,
        MISS,GOOD_HIT,BAD_HIT,SUNK;
    }

    public static BufferedImage getImage(ImageName name) {
        try{
            switch (name) {
                case START: return ImageIO.read(new File("BATTLESHIP/images/start.png"));
                case START_CLICK: return ImageIO.read(new File("BATTLESHIP/images/start_click.png"));
                case BOARDS: return ImageIO.read(new File("BATTLESHIP/images/boards.png"));
                case AIRCRAFTCARRIER: return ImageIO.read(new File("BATTLESHIP/images/aircraftcarrier.png"));
                case AIRCRAFTCARRIER_VERT: return ImageIO.read(new File("BATTLESHIP/images/aircraftcarrier_vert.png"));
                case BATTLESHIP: return ImageIO.read(new File("BATTLESHIP/images/battleship.png"));
                case BATTLESHIP_VERT: return ImageIO.read(new File("BATTLESHIP/images/battleship_vert.png"));
                case CRUISER: return ImageIO.read(new File("BATTLESHIP/images/cruiser.png"));
                case CRUISER_VERT: return ImageIO.read(new File("BATTLESHIP/images/cruiser_vert.png"));
                case DESTROYER: return ImageIO.read(new File("BATTLESHIP/images/destroyer.png"));
                case DESTROYER_VERT: return ImageIO.read(new File("BATTLESHIP/images/destroyer_vert.png"));
                case SUBMARINE: return ImageIO.read(new File("BATTLESHIP/images/submarine.png"));
                case SUBMARINE_VERT: return ImageIO.read(new File("BATTLESHIP/images/submarine_vert.png"));
                case EXIT: return ImageIO.read(new File("BATTLESHIP/images/exit.png"));
                case EXIT_CLICK: return ImageIO.read(new File("BATTLESHIP/images/exit_click.png"));
                case EASY: return ImageIO.read(new File("BATTLESHIP/images/easy.png"));
                case EASY_HOVER: return ImageIO.read(new File("BATTLESHIP/images/easy_hover.png"));
                case HARD: return ImageIO.read(new File("BATTLESHIP/images/hard.png"));
                case HARD_HOVER: return ImageIO.read(new File("BATTLESHIP/images/hard_hover.png"));
                case SCREEN: return ImageIO.read(new File("BATTLESHIP/images/screen.png"));
                case MISS: return ImageIO.read(new File("BATTLESHIP/images/miss.png"));
                case GOOD_HIT: return ImageIO.read(new File("BATTLESHIP/images/good_hit.png"));
                case SUNK: return ImageIO.read(new File("BATTLESHIP/images/fire.png"));
                case BAD_HIT: return ImageIO.read(new File("BATTLESHIP/images/bad_hit.png"));
                case INSTRUCTIONS: return ImageIO.read(new File("BATTLESHIP/images/setupText.png"));
                case HIT_TEXT: return ImageIO.read(new File("BATTLESHIP/images/hitText.png"));
                case WIN_TEXT: return ImageIO.read(new File("BATTLESHIP/images/winText.png"));
                case LOSE_TEXT: return ImageIO.read(new File("BATTLESHIP/images/loseText.png"));
                case GO_FIRST: return ImageIO.read(new File("BATTLESHIP/images/goFirst.png"));
                case GO_SECOND: return ImageIO.read(new File("BATTLESHIP/images/goSecond.png"));
                case BLANK_INFO: return ImageIO.read(new File("BATTLESHIP/images/blankInfo.png"));
                case C_AIRCRAFTCARRIER: return ImageIO.read(new File("BATTLESHIP/images/cAircraftCarrier.png"));
                case C_CRUISER: return ImageIO.read(new File("BATTLESHIP/images/cCruiser.png"));
                case C_BATTLESHIP: return ImageIO.read(new File("BATTLESHIP/images/cBattleship.png"));
                case C_SUBMARINE: return ImageIO.read(new File("BATTLESHIP/images/cSubmarine.png"));
                case C_DESTROYER: return ImageIO.read(new File("BATTLESHIP/images/cDestroyer.png"));
                case U_AIRCRAFTCARRIER: return ImageIO.read(new File("BATTLESHIP/images/uAircraftCarrier.png"));
                case U_CRUISER: return ImageIO.read(new File("BATTLESHIP/images/uCruiser.png"));
                case U_BATTLESHIP: return ImageIO.read(new File("BATTLESHIP/images/uBattleship.png"));
                case U_SUBMARINE: return ImageIO.read(new File("BATTLESHIP/images/uSubmarine.png"));
                case U_DESTROYER: return ImageIO.read(new File("BATTLESHIP/images/uDestroyer.png"));
            }
            return null;
        }catch (IOException e){
            return null;
        }
    }

    public static BufferedImage getHits(int sunk, boolean user){
        try{
            switch (sunk){
                case 0: return user ? ImageIO.read(new File("BATTLESHIP/images/pShipsFull.png")) : ImageIO.read(new File("BATTLESHIP/images/cShipsFull.png"));
                case 1: return user ? ImageIO.read(new File("BATTLESHIP/images/pShips4.png")) : ImageIO.read(new File("BATTLESHIP/images/cShips4.png"));
                case 2: return user ? ImageIO.read(new File("BATTLESHIP/images/pShips3.png")) : ImageIO.read(new File("BATTLESHIP/images/cShips3.png"));
                case 3: return user ? ImageIO.read(new File("BATTLESHIP/images/pShips2.png")) : ImageIO.read(new File("BATTLESHIP/images/cShips2.png"));
                case 4: return user ? ImageIO.read(new File("BATTLESHIP/images/pShips1.png")) : ImageIO.read(new File("BATTLESHIP/images/cShips1.png"));
                case 5: return user ? ImageIO.read(new File("BATTLESHIP/images/pShipsEmpty.png")) : ImageIO.read(new File("BATTLESHIP/images/cShipsEmpty.png"));
                default: return user ? ImageIO.read(new File("BATTLESHIP/images/pShipsEmpty.png")) : ImageIO.read(new File("BATTLESHIP/images/cShipsEmpty.png"));
            }
        }catch (IOException e){
            return null;
        }
    }
}
