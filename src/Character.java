import java.util.Vector;

/**
 * 设置人物及其位置、方向、动作的类，是Lawyer和Procurator的父类
 * 
 * @author 2313040111张鑫雅
 * @version 2.0
 */
public class Character {
    private int x; // 人物横坐标
    private int y; // 人物纵坐标
    private int direct; // 人物方向
    private int speed = 4;
    private boolean isLive = true;
    private Vector<BulletShot> shots = new Vector<>();

    public static final int UP = 0;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 1;

    public Character(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
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

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean isLive) {
        this.isLive = isLive;
    }

    public Vector<BulletShot> getShots() {
        return shots;
    }

    public void setShots(Vector<BulletShot> shots) {
        this.shots = shots;
    }

    public void moveUp() {
        if (this.getY() - this.getSpeed() > 0) {
            this.y -= speed;
        }
    }

    public void moveDown() {
        if (this.getY() + 100 + this.getSpeed() < 648) {
            this.y += speed;
        }
    }

    public void moveLeft() {
        if (this.getX() - this.getSpeed() > 0) {
            this.x -= speed;
        }
    }

    public void moveRight() {
        if (this.getX() + 100 + this.getSpeed() < 1152) {
            this.x += speed;
        }
    }

    public BulletShot shotRepeat(BulletShot shot) {
        switch (getDirect()) {
            case UP:
                shot = new BulletShot(this.getX(), this.getY() - 20, UP);
                break;
            case DOWN:
                shot = new BulletShot(this.getX(), this.getY() + 20, DOWN);
                break;
            case LEFT:
                shot = new BulletShot(this.getX() - 20, this.getY(), LEFT);
                break;
            case RIGHT:
                shot = new BulletShot(this.getX() + 20, this.getY() + 20, RIGHT);
                break;
            default:
                break;
        }
        return shot;
    }
}
