import java.awt.Image;
import java.util.Vector;

/**
 * 控制检方的类
 * 
 * @author 2313040111张鑫雅
 * @version 2.0
 */
public class Procurator extends Character implements Runnable {
    private String name;
    private Image defeatImage;
    Vector<Procurator> colleagues = new Vector<>();

    public Procurator(int x, int y, int direct, String name, Image defeatImage) {
        super(x, y, direct);
        this.name = name;
        this.defeatImage = defeatImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getDefeatImage() {
        return defeatImage;
    }

    public void setDefeatImage(Image defeatImage) {
        this.defeatImage = defeatImage;
    }

    public Vector<Procurator> getColleagues() {
        return colleagues;
    }

    // 用于将MyPanel中的procurators向量传入
    public void setColleagues(Vector<Procurator> colleagues) {
        this.colleagues = colleagues;
    }

    public boolean isOverlap() {
        for (int i = 0; i < colleagues.size(); i++) {
            Procurator p = colleagues.get(i);
            if (Math.abs((this.getX() + 50) - (p.getX() + 50)) < 100 && Math.abs((this.getY() + 50) - p.getY() + 50) < 100) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void run() {
        while (true) {
            // 子弹线程消失就再启动一个
            if (getShots().size() == 0 && isLive()) {
                BulletShot s = shotRepeat(null);
                getShots().add(s);
                new Thread(s).start();
            }

            for (int i = 0; i < 20; i++) {
                switch (getDirect()) {
                    case UP:
                        if (this.getY() + getSpeed() > 0 && !isOverlap()) {
                            moveUp();
                        }
                        break;
                    case RIGHT:
                        if (this.getX() + 100 + getSpeed() < 1152 && !isOverlap()) {
                            moveRight();
                        }
                        break;
                    case DOWN:
                        if (this.getY() + 100 + getSpeed() < 648 && !isOverlap()) {
                            moveDown();
                        }
                        break;
                    case LEFT:
                        if (this.getX() + getSpeed() > 0 && !isOverlap()) {
                            moveLeft();
                        }
                        break;
                    default:
                        break;
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            setDirect((int) (Math.random() * 4));
            if (!isLive()) {
                break;
            }
        }
    }

}
