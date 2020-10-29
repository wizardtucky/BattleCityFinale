package Game;


import java.util.Random;

public class Tankas {
    private boolean isAlive;
    private char dirTank;
    private char lastDirTank;
    private int xTank;
    private int yTank;
    private Boolean shootButton;

    Random rand;

    public Tankas(boolean isAlive, int xTank, int yTank, char dirTank, char lastDirTank, Boolean shootButton) {
        this.isAlive = isAlive;
        this.xTank = xTank;
        this.yTank = yTank;
        this.dirTank = dirTank;
        this.lastDirTank = lastDirTank;
        this.shootButton = shootButton;
    }

    public char setRandomDir(){
        Random random = new Random();
        int exp = random.nextInt(5);

        switch (exp){
            case 0:
                return 'U';
            case 1:
                return 'D';
            case 2:
                return 'L';
            case 3:
                return 'R';
        }
        return 'S';
    }

    public boolean randomShooting() {
        Random random = new Random();
        int exp = random.nextInt(3);

        if(exp == 1) {
            return true;
        }
        return false;
    }

    public char getDirTank() {
        return dirTank;
    }

    public void setDirTank(char dirTank) {
        this.dirTank = dirTank;
    }

    public char getLastDirTank() {
        return lastDirTank;
    }

    public void setLastDirTank(char lastDirTank) {
        this.lastDirTank = lastDirTank;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int getxTank() {
        return xTank;
    }

    public void setxTank(int xTank) {
        this.xTank = xTank;
    }

    public int getyTank() {
        return yTank;
    }

    public void setyTank(int yTank) {
        this.yTank = yTank;
    }

    public Boolean getShootButton() {
        return shootButton;
    }

    public void setShootButton(Boolean shootButton) {
        this.shootButton = shootButton;
    }
}
