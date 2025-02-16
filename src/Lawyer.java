/**
 * 控制律方的类
 * 
 * @author 2313040111张鑫雅
 * @version 2.0
 */
public class Lawyer extends Character {
    // 表示一个射击线程
    private BulletShot shot = null;
    // 律方生命值
    private int life = 4;

    public Lawyer(int x, int y, int direct) {
        super(x, y, direct);
    }

    public void shotEnemy() {
        // 限制一次仅能发射3个子弹
        if (getShots().size() == 2) {
            return;
        }
        shot = shotRepeat(shot);
        getShots().add(shot);
        new Thread(shot).start();
    }

    public BulletShot getShot() {
        return shot;
    }

    public int getLife() {
        return life;
    }

}
