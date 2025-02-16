/**
 * 设置射击行为的类
 * 
 * @author 2313040111张鑫雅
 * @version 2.0
 */
public class BulletShot implements Runnable {
    private int x;
    private int y;
    private int direct;
    private int speed = 7;
    private boolean isLive = true;

    public static final int UP = 0;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 1;

    public BulletShot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
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

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public void upAttack() {
        this.y -= speed;
    }

    public void downAttack() {
        this.y += speed;
    }

    public void leftAttack() {
        this.x -= speed;
    }

    public void rightAttack() {
        this.x += speed;
    }

    // 为子弹设置一个线程，根据dirct射击决定方向
    @Override
    public void run() {
        while (isLive) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            switch (direct) {
                case UP:
                    upAttack();
                    break;
                case DOWN:
                    downAttack();
                    break;
                case LEFT:
                    leftAttack();
                    break;
                case RIGHT:
                    rightAttack();
                    break;
                default:
                    break;
            }

            // 当子弹射击到边界，就将子弹线程销毁
            if (!(x >= 0 && x <= 1150 && y <= 648 && y >= 0)) {
                isLive = false;
            }
        }
    }

}
