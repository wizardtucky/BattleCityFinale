package Game;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Game {

    private boolean isGameRunning = false;
    private Timer timer;
    private GraphicsContext gc;
    // map
    private double SCREEN_WIDTH;
    private double SCREEN_HEIGHT;
    private double UNIT_SIZE = 50;
    private ArrayList<Block> blocks = new ArrayList<>();

    // Player
    private Tankas playerTank = new Tankas(false, 50, 50, 'S', 'R', false);

    // nutral
    public ArrayList<Bullet> bullets = new ArrayList<>();
    public ArrayList<Bullet> bulletsToDelete = new ArrayList<>();
    // Bot
    private Tankas botTank = new Tankas(false, 200, 200, 'S', 'R', false);

    public Game(GraphicsContext gc, double W, double H){
        this.gc = gc;
        this.SCREEN_WIDTH = W;
        this.SCREEN_HEIGHT = H;
        addBlocks();
    }

    public void startGame(){
        isGameRunning = true;
        playerTank.setAlive(true);
        botTank.setAlive(true);
        startTimer();
    }

    // nenen
    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> tick());
            }
        }, 0, 200);
    }

    private void stopTimer() {
        timer.cancel();
    }

    private void tick() {

        moveBullets();
        addBullets();
        System.out.println("veikia");
        // cia viskas vyks.
        if(playerTank.isAlive())
            move(playerTank);
        if(botTank.isAlive()){
            botTank.setDirTank(botTank.setRandomDir());
            move(botTank);
        }
        draw();
        if(!isGameRunning){
            stopTimer();
        }
    }
    private void addBullets(){
        if(playerTank.getShootButton()) {
            bullets.add(new Bullet(playerTank.getxTank(),playerTank.getyTank(),playerTank.getLastDirTank(), UNIT_SIZE, SCREEN_WIDTH, SCREEN_HEIGHT));
            System.out.println("Bullet was shot");
            playerTank.setShootButton(false);
        }
        if(botTank.isAlive()){
            if(botTank.randomShooting()){
                bullets.add(new Bullet(botTank.getxTank(),botTank.getyTank(),botTank.getLastDirTank(), UNIT_SIZE, SCREEN_WIDTH, SCREEN_HEIGHT));
                System.out.println("Bot SHOT BULLETS");
            }
        }
    }
    public void moveBullets(){
        boolean delete = false;
        for(Bullet bat : bullets){
            if(checkBulColl(bat)){
                // check if got shot;
                System.out.println("Bullet should be deleted");
                delete = true;
                bulletsToDelete.add(bat);
            }
            bat.moveBullet();
            botTank.setAlive(enemyGotShot(bat));
            playerTank.setAlive(playerGotShot(bat));
            if(checkBulColl(bat)){
                // check if got shot;
                System.out.println("Bullet should be deleted");
                delete = true;
                bulletsToDelete.add(bat);
            }
        }
        if (delete) {
            for(Bullet batDel : bulletsToDelete)
            bullets.remove(batDel);
        }

        // no bullet delete for bot
    }

    private boolean checkBulColl(Bullet bul) {

            if (bul.getxBullet() >= SCREEN_WIDTH - UNIT_SIZE) {
                return true;
            }
            if (bul.getxBullet() < UNIT_SIZE) {
                return true;
            }
            if (bul.getyBullet() >= SCREEN_HEIGHT - UNIT_SIZE) {
                return true;
            }
            if (bul.getyBullet() < UNIT_SIZE) {
                return true;
            }
            if (botTank.getxTank() == bul.getxBullet() && botTank.getyTank() == bul.getyBullet()) {
                return true;
            }
            if (playerTank.getxTank() == bul.getxBullet() && playerTank.getyTank() == bul.getyBullet()) {
                return true;
            }
            for(Block blo : blocks) {
                if (blo.getxBlock() == bul.getxBullet() && blo.getyBlock() == bul.getyBullet()){
                    return true;
                }
            }
        return false;
    }
    boolean playerGotShot(Bullet bat){
        if(playerTank.getxTank() == bat.getxBullet() && playerTank.getyTank() == bat.getyBullet()){
            System.out.println("Player is dead");
            return false;
        }
        return playerTank.isAlive();
    }
    boolean enemyGotShot(Bullet bat){
        if(botTank.getxTank() == bat.getxBullet() && botTank.getyTank() == bat.getyBullet()){
            return false;
        }
        return botTank.isAlive();
    }

    private void draw() {
        // vertical lines
        gc.rect(0,0, SCREEN_WIDTH, SCREEN_HEIGHT);
        gc.setFill(Color.CYAN);
        gc.fillRect(0,0, SCREEN_WIDTH, SCREEN_HEIGHT);
        gc.setStroke(Color.BLACK);
        for(int i = 0 ; i < SCREEN_WIDTH ; i+=UNIT_SIZE){
            gc.strokeLine(i, 0, i, SCREEN_HEIGHT - (SCREEN_HEIGHT%UNIT_SIZE));
        }
        // horizontal lines
        for(double i = UNIT_SIZE ; i < SCREEN_HEIGHT; i+=UNIT_SIZE){
            gc.strokeLine(UNIT_SIZE - UNIT_SIZE, i, SCREEN_WIDTH, i);
        }
        // Draw Bullet
        for(Bullet bul : bullets){
            gc.setFill(Color.ORANGE);
            gc.fillOval(bul.getxBullet()+UNIT_SIZE/4, bul.getyBullet()+UNIT_SIZE/4, UNIT_SIZE/2, UNIT_SIZE/2);
        }
        // draw Blocks
        for(Block blo : blocks){
            if(blo.getColor() == "black"){
                gc.setFill(Color.BLACK);
                gc.fillRect(blo.getxBlock(),blo.getyBlock(),UNIT_SIZE,UNIT_SIZE);
            } else if (blo.getColor() == "green"){
                gc.setFill(Color.GREEN);
                gc.fillRect(blo.getxBlock(),blo.getyBlock(),UNIT_SIZE,UNIT_SIZE);
            } else if (blo.getColor() == "gray"){
                gc.setFill(Color.GRAY);
                gc.fillRect(blo.getxBlock(),blo.getyBlock(),UNIT_SIZE,UNIT_SIZE);
            } else {
                gc.setFill(Color.DARKGREY);
                gc.fillRect(blo.getxBlock(),blo.getyBlock(),UNIT_SIZE,UNIT_SIZE);
            }


        }
        // Draw Player
        if(playerTank.isAlive()){
            gc.setFill(Color.LIME);
            gc.fillRect(playerTank.getxTank(),playerTank.getyTank(),UNIT_SIZE,UNIT_SIZE);
        } else {
            gc.setFill(Color.DARKGREEN);
            gc.fillRect(playerTank.getxTank(),playerTank.getyTank(),UNIT_SIZE,UNIT_SIZE);
        }
        // Draw Bot
        if(botTank.isAlive()){
            gc.setFill(Color.RED);
            gc.fillRect(botTank.getxTank(),botTank.getyTank(),UNIT_SIZE,UNIT_SIZE);
        } else {
            gc.setFill(Color.DARKRED);
            gc.fillRect(botTank.getxTank(),botTank.getyTank(),UNIT_SIZE,UNIT_SIZE);
        }
    }
    public void addBlocks(){
        System.out.println("Blocks were added");
        for(int i = 0; i < 10; i++){
            blocks.add(new Block(400,i*50, "gray"));
        }
        blocks.add(new Block(400, 600, "green"));
        blocks.add(new Block(650, 500, "green"));
        blocks.add(new Block(350, 700, "green"));
        for(int i = 0; i < SCREEN_WIDTH; i+=UNIT_SIZE){
            for(int j = 0; j < SCREEN_HEIGHT; j+=UNIT_SIZE){
                if(i == 0 || i == SCREEN_WIDTH - UNIT_SIZE)
                    blocks.add(new Block(i, j, "black"));

                if(j == 0 || j == SCREEN_WIDTH - UNIT_SIZE)
                    blocks.add(new Block(i, j, "black"));


            }
        }
    }

    public void move(Tankas tan) {

        switch (tan.getDirTank()) {
            case 'U':
                if(checkCollisionsYYup(tan)) {
                    tan.setyTank(tan.getyTank() - (int)UNIT_SIZE);
                }
//                if(tan.getDirTank() != 'S')
//                    tan.setLastDirTank(tan.getDirTank());
                tan.setLastDirTank('U');
                break;
            case 'D':
                if(checkCollisionsYDown(tan)) {
                    tan.setyTank(tan.getyTank() + (int)UNIT_SIZE);
                }
                tan.setLastDirTank('D');
                break;
            case 'L':
                if(checkCollisionsXDown(tan)) {
                    tan.setxTank(tan.getxTank() - (int)UNIT_SIZE);
                }
                tan.setLastDirTank('L');
                break;
            case 'R':
                if(checkCollisionsXup(tan)) {
                    tan.setxTank(tan.getxTank() + (int)UNIT_SIZE);
                }
                tan.setLastDirTank('R');
                break;
            case 'S':
                break;
        }
        tan.setDirTank('S');
    }

    boolean checkCollisionsXup(Tankas tan) {
        if(tan.getxTank() >= SCREEN_WIDTH - UNIT_SIZE) {
            tan.setDirTank('S');
            return false;
        }
        for(Block blo : blocks) {
            if (tan.getxTank() == blo.getxBlock() - UNIT_SIZE && tan.getyTank() == blo.getyBlock()) {
                tan.setDirTank('S');
                return false;
            }
        }
        return true;
    }
    boolean checkCollisionsXDown(Tankas tan) {
        if(tan.getxTank() < UNIT_SIZE) {
            tan.setDirTank('S');
            return false;
        }
        for(Block blo : blocks) {
            if (tan.getxTank() == blo.getxBlock() + UNIT_SIZE && tan.getyTank() == blo.getyBlock()) {
                tan.setDirTank('S');
                return false;
            }
        }
        return true;
    }
    boolean checkCollisionsYDown(Tankas tan) {
        if(tan.getyTank() >= SCREEN_HEIGHT - UNIT_SIZE) {
            tan.setDirTank('S');
            return false;
        }
        for(Block blo : blocks) {
            if (tan.getyTank() == blo.getyBlock() - UNIT_SIZE && tan.getxTank() == blo.getxBlock()) {
                tan.setDirTank('S');
                return false;
            }
        }
        return true;
    }
    boolean checkCollisionsYYup(Tankas tan) {
        if(tan.getyTank() < UNIT_SIZE) {
            tan.setDirTank('S');
            return false;
        }
        for(Block blo : blocks) {
            if (tan.getyTank() == blo.getyBlock() + UNIT_SIZE && tan.getxTank() == blo.getxBlock()) {
                tan.setDirTank('S');
                return false;
            }
        }
        return true;
    }
    public void keyboardInput(KeyCode keyCode){
            switch (keyCode) {
                case LEFT:
                    playerTank.setDirTank('L');
                    break;
                case RIGHT:
                    playerTank.setDirTank('R');
                    break;
                case UP:
                    playerTank.setDirTank('U');
                    break;
                case DOWN:
                    playerTank.setDirTank('D');
                    break;
                case SPACE:
                    playerTank.setShootButton(true);
                    break;

            }
    }
}
