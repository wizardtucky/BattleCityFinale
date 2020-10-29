package Game;

public class Block {
    private int xBlock;
    private int yBlock;
    private String color;

    public Block(int xBlock, int yBlock, String color) {
        this.xBlock = xBlock;
        this.yBlock = yBlock;
        this.color = color;
    }

    public int getxBlock() {
        return xBlock;
    }

    public void setxBlock(int xBlock) {
        this.xBlock = xBlock;
    }

    public int getyBlock() {
        return yBlock;
    }

    public void setyBlock(int yBlock) {
        this.yBlock = yBlock;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
