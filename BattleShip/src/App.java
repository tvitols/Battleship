package BattleShip.src;

import javax.swing.*;
import java.io.IOException;

public class App{
    public static void main(String[] args) {


        SwingUtilities.invokeLater(new Runnable(){

            @Override
            public void run(){
                App app = new App();
                JFrame screen = new JFrame();
                screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                screen.setSize(1920, 1080);
                app.showTitle(screen);
                
            }
        });
    }

    public void showTitle(JFrame screen){
        TitleScreen title = new TitleScreen(screen);
        try{
            title.draw();
        }catch (IOException e){
            System.err.println(e);
        }
    
    }

    public static void startGame(JFrame screen,int difficulty){
        screen.getContentPane().removeAll();
        Game game = new Game(difficulty);
        GameScreen gameScreen = new GameScreen(screen,game);
        gameScreen.view();
        //game.setup(gameScreen);
    }

    public static void setupGame(JFrame screen,int difficulty){
        screen.getContentPane().removeAll();
        SetupScreen sc = new SetupScreen(screen,difficulty);
        sc.view();
    }
}
