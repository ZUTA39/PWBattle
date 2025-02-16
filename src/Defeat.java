import java.awt.Image;

/**
 * 人物被击中后退场效果
 * 
 * @author 2313040111 张鑫雅
 * @version 1.0
 */
public class Defeat {
    private int x, y;
    private int lifeTime = 10;
    private boolean isLive = true;
    private Image defeatImage;

    public Defeat(int x, int y, Image defeatImage) {
        this.x = x;
        this.y = y;
        this.defeatImage = defeatImage;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean isLive) {
        this.isLive = isLive;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getDefeatImage() {
        return defeatImage;
    }

    public void setDefeatImage(Image defeatImage) {
        this.defeatImage = defeatImage;
    }

    public int getLifeTime() {
        return lifeTime;
    }

    public void decreaseLifeTime() {
        if (lifeTime > 0) {
            lifeTime --;
        } else {
            isLive = false;
        }
    }

}
