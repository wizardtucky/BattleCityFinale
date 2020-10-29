package Game;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

public class Bullet{
    // bullet
    private int xBullet;
    private int yBullet;
    private char bulletDirrection;
    private double UNIT_SIZE;
    private double SCREEN_WIDTH;
    private double SCREEN_HEIGHT;
    //    public boolean bulletExists = true;
    public Bullet() {
    }

    public Bullet(int xBul, int yBul, char bulDirrectionn, double UNIT_SIZE, double SCREEN_WIDTH, double SCREEN_HEIGHT) {
        this.xBullet = xBul;
        this.yBullet = yBul;
        this.bulletDirrection = bulDirrectionn;
        this.UNIT_SIZE = UNIT_SIZE;
        this.SCREEN_HEIGHT = SCREEN_HEIGHT;
        this.SCREEN_WIDTH = SCREEN_WIDTH;
        moveBullet();
    }

    public void moveBullet(){
        switch (bulletDirrection) {
            case 'U':
                yBullet = yBullet - (int)UNIT_SIZE;
                break;
            case 'D':
                yBullet = yBullet + (int)UNIT_SIZE;
                break;
            case 'L':
                xBullet = xBullet - (int)UNIT_SIZE;
                break;
            case 'R':
                xBullet = xBullet + (int)UNIT_SIZE;
                break;
            case 'S':
                break;
        }
    }


//    boolean checkBulCollYdown() {
//        if(xBullet < UNIT_SIZE) {
//            return false;
//        }
//        return true;
//    }
//    boolean checkBulCollXleft() {
//        if(yBullet >= SCREEN_HEIGHT - UNIT_SIZE) {
//
//            return false;
//        }
//        return true;
//    }
//    boolean checkBulCollXright() {
//        if(yBullet < UNIT_SIZE) {
//
//            return false;
//        }
//        return true;
//    }

    public int getxBullet() {
        return xBullet;
    }

    public void setxBullet(int xBullet) {
        this.xBullet = xBullet;
    }

    public int getyBullet() {
        return yBullet;
    }

    public void setyBullet(int yBullet) {
        this.yBullet = yBullet;
    }
}
