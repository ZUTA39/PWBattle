import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * 游戏本体背景与人物的渲染
 * 
 * @author 2313040111张鑫雅
 * @version 2.0
 */

// 为了不停的重绘子弹，需要将渲染类做成线程
public class GamePanel extends JPanel implements KeyListener, Runnable {
    Image background = null;
    Lawyer lawyer = null;
    // 可选增加检方人物："狩魔豪-检", "夕神-检" "响也-检", "亚内-检"
    Vector<Procurator> procurators = new Vector<>();
    // 接收上局检方数据的向量
    Vector<Node> pDatas = new Vector<>();
    ArrayList<String> procurator_nameList = new ArrayList<>();
    Vector<Defeat> defeats = new Vector<>();
    Image lawyerDefeatImage = null;
    Image procuratorDefeatImage1 = null;

    public static final int UP = 0;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 1;

    /**
     * 游戏初始化位置规定（背景 + 检方 + 律方 + 子弹）
     * 
     * @param key 代表新游戏（0）或继续上局游戏（1）
     */
    public GamePanel(String key) {
        // 初始化背景
        background = new ImageIcon(getClass().getResource("/img/background.png")).getImage();
        // 初始化律方
        lawyer = new Lawyer(800, 330, 3);

        // 为检方姓名列表初始化值
        procurator_nameList.add("歌德-检");
        procurator_nameList.add("御剑-检");
        procurator_nameList.add("冥-检");
        int pSize = 0;

        if (key.equals("0")) {
            // 新游戏：初始化检方及其子弹（多个）
            pSize = procurator_nameList.size();
            for (int i = 0; i < pSize; i++) {
                Image defeatImage = new ImageIcon(
                        getClass().getResource("/img/" + procurator_nameList.get(i) + "-被击中.png"))
                        .getImage();
                Procurator p = null;
                if (i <= pSize / 2) {
                    p = new Procurator(100 * (i + 1), 150 * (i + 1), 1, procurator_nameList.get(i), defeatImage);
                } else {
                    p = new Procurator(100 * (pSize - i), 150 * (i + 1), 1, procurator_nameList.get(i), defeatImage);

                }
                p.setColleagues(procurators);
                procurators.add(p);

                BulletShot shot = new BulletShot(p.getX() + 10, p.getY(), p.getDirect());
                p.getShots().add(shot);
                new Thread(shot).start();
                new Thread(p).start();
            }
        } else if (key.equals("1")) {
            // 继续上局游戏

            // 继续上局游戏需要拿到上局数据
            pDatas = Recorder.reloadProcurator();
            pSize = pDatas.size();
            for (int i = 0; i < pSize; i++) {
                Node pData = pDatas.get(i);
                Image defeatImage = new ImageIcon(
                        getClass().getResource("/img/" + procurator_nameList.get(i) + "-被击中.png"))
                        .getImage();
                Procurator p = null;
                if (i <= pSize / 2) {
                    p = new Procurator(pData.getX(), pData.getY(), pData.getDirect(), procurator_nameList.get(i),
                            defeatImage);
                } else {
                    p = new Procurator(100 * (pSize - i), 150 * (i + 1), 1, procurator_nameList.get(i), defeatImage);

                }
                p.setColleagues(procurators);
                procurators.add(p);

                BulletShot shot = new BulletShot(p.getX() + 10, p.getY(), p.getDirect());
                p.getShots().add(shot);
                new Thread(shot).start();
                new Thread(p).start();
            }
        }

        // 初始化律检的被击中效果
        lawyerDefeatImage = new ImageIcon(getClass().getResource("/img/成步堂-律-被击中.png")).getImage();

        Recorder.setProcurators(procurators);
    }

    /**
     * 显示我方得分信息
     */
    public void showInfo(Graphics g) {
        Image icon = new ImageIcon(getClass().getResource("/img/律师徽章.png")).getImage();
        String text = "累计击败检方:" + Recorder.getDefeatNum();
        int x = 940;
        int y = 30;
        Font font = new Font("黑体", Font.BOLD, 25);
        g.setFont(font);

        // 绘制icon
        g.drawImage(icon, x - 40, y - 27, 40, 40, this);

        // 绘制文本边框
        g.setColor(Color.RED);
        g.drawString(text, x - 1, y - 1);
        g.drawString(text, x - 1, y + 1);
        g.drawString(text, x + 1, y - 1);
        g.drawString(text, x + 1, y + 1);

        // 绘制文本
        g.setColor(Color.BLACK);
        g.drawString(text, x, y);
    }

    /**
     * 主界面集中控制渲染
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // 画背景
        g.drawImage(background, 0, 0, 1152, 648, this);
        // 画得分情况
        showInfo(g);
        // 画律方
        if (lawyer != null && lawyer.isLive()) {
            loadCharacter(lawyer.getX(), lawyer.getY(), g, lawyer.getDirect(), 0);
        }

        // 画检方及其子弹（多个）
        for (int i = 0; i < procurators.size(); i++) {
            Procurator procurator_member = procurators.get(i);
            loadCharacter(procurator_member.getX(), procurator_member.getY(), g, procurator_member.getDirect(), 1);
            for (int j = 0; j < procurator_member.getShots().size(); j++) {
                BulletShot pshot = procurator_member.getShots().get(j);
                if (pshot != null && pshot.isLive()) {
                    loadBullet(g, procurator_member.getDirect(), pshot);
                } else {
                    procurator_member.getShots().remove(pshot);
                }
            }
        }

        // 画律方子弹
        for (int i = 0; i < lawyer.getShots().size(); i++) {
            BulletShot lshot = lawyer.getShots().get(i);
            if (lshot != null && lshot.isLive()) {
                loadBullet(g, lawyer.getDirect(), lshot);
            } else {
                lawyer.getShots().remove(lshot);
            }
        }

        // 画被击中效果
        for (int i = 0; i < defeats.size(); i++) {
            Defeat defeat = defeats.get(i);
            g.drawImage(defeat.getDefeatImage(), defeat.getX(), defeat.getY(), 100, 100, this);
            defeat.decreaseLifeTime();
            if (!defeat.isLive()) {
                defeats.remove(defeat);
            }
        }
    }

    /**
     * 游戏人物
     * 
     * @param x      人物左上角x坐标
     * @param y      人物右上角坐标
     * @param g      可以理解为画笔
     * @param direct 方向（左3右1，人物没有上下差分）
     * @param type   人物类型（0--律方，1--检方）
     * @see 关于xy初始值的参考：律方--800，330；检方--260，330，双方均正好在席位上
     */
    public void loadCharacter(int x, int y, Graphics g, int direct, int type) {
        String c_type = null;
        // 人物类型分支
        if (type == 0) {
            c_type = "成步堂-律";
            this.drawDirectCharactor(c_type, lawyer.getX(), lawyer.getY(), lawyer.getDirect(), g);
        } else if (type == 1) {
            for (int i = 0; i < procurators.size(); i++) {
                c_type = procurator_nameList.get(i);
                this.drawDirectCharactor(c_type, procurators.get(i).getX(), procurators.get(i).getY(),
                        procurators.get(i).getDirect(), g);
            }

        } else {
            System.out.println("人物类型输入错误！！");
        }

    }

    // 人物方向分支
    public void drawDirectCharactor(String name, int x, int y, int direct, Graphics g) {
        Image character = null;
        switch (direct) {
            case UP:
            case LEFT:
                character = new ImageIcon(getClass().getResource("/img/" + name + "左.png")).getImage();
                g.drawImage(character, x, y, 100, 100, this);
                break;
            case DOWN:
            case RIGHT:
                character = new ImageIcon(getClass().getResource("/img/" + name + "右.png")).getImage();
                g.drawImage(character, x, y, 100, 100, this);
                break;
            default:
                System.out.println("方向代码输入错误！！");
                break;
        }
    }

    /**
     * 子弹预加载，内部调用drawBulletDirect函数来渲染子弹
     * 
     * @param g      相当于画笔
     * @param direct 传入人物方向来决定子弹方向
     * @param shot   每个人物有自己子弹的线程，需要输入这个人物的子弹对象才能渲染到位
     */
    public void loadBullet(Graphics g, int direct, BulletShot shot) {
        String type = "子弹-o";
        if (shot != null && shot.isLive()) {
            Image bullet = null;
            switch (shot.getDirect()) {
                case UP:
                    type += "上";
                    break;
                case DOWN:
                    type += "下";
                    break;
                default:
                    break;
            }
            bullet = new ImageIcon(getClass().getResource("/img/" + type + ".png")).getImage();
            g.drawImage(bullet, shot.getX(), shot.getY(), 100, 100, this);
        }
    }

    /**
     * 判断子弹是否击中人物的逻辑并做出相应处理的函数
     * 
     * @param s 传入的子弹
     * @param c 人物
     */
    public boolean isHitCharacter(BulletShot s, Character c) {
        // System.out.println("子弹坐标: (" + s.getX() + ", " + s.getY() + ")");
        // System.out.println(c.getClass() + "人物坐标: (" + c.getX() + ", " + c.getY() +
        // ")");
        if ((s.getX() + 100 > c.getX() && s.getX() < c.getX() && s.getY() + 100 > c.getY() && s.getY() < c.getY())
                || (s.getX() < c.getX() + 100 && s.getX() > c.getX() && s.getY() < c.getY() + 100
                        && s.getY() > c.getY())) {
            s.setLive(false);
            c.setLive(false);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否击中律方并做出相应处理的函数
     */
    public void isHitLawyer() {
        // 遍历所有检方
        for (int i = 0; i < procurators.size(); i++) {
            Procurator p = procurators.get(i);
            // 遍历每个检方的所有子弹
            for (int j = 0; j < p.getShots().size(); j++) {
                BulletShot pshot = p.getShots().get(j);
                if (pshot != null && pshot.isLive()) {
                    if (isHitCharacter(pshot, lawyer)) {
                        System.out.println("击中我方");
                        Defeat defeat = new Defeat(lawyer.getX(), lawyer.getY(), lawyerDefeatImage);
                        defeats.add(defeat);
                    }
                }
            }
        }
    }

    /**
     * 在线程中检查检方是否有被击中并做出相应处理的函数
     */
    public void isHitProcurator() {
        // 判断是否击中敌方
        // 遍历我方子弹，遍历敌方所有人物
        for (int i = 0; i < lawyer.getShots().size(); i++) {
            BulletShot lshot = lawyer.getShots().get(i);
            if (lshot != null && lshot.isLive()) {
                for (int j = 0; j < procurators.size(); j++) {
                    Procurator p = procurators.get(j);
                    if (isHitCharacter(lshot, p)) {
                        procurators.remove(p);
                        procurator_nameList.remove(p.getName());
                        Recorder.addDefeatNum();
                        System.out.println("击中：" + p.getName());
                        System.out.println("还有：" + procurators.size() + " 人");
                        Defeat defeat = new Defeat(p.getX(), p.getY(), p.getDefeatImage());
                        defeats.add(defeat);
                    }

                }
            }
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            lawyer.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            lawyer.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            lawyer.setDirect(3);
            lawyer.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            lawyer.setDirect(1);
            lawyer.moveRight();
        }
        
        if (e.getKeyCode() == KeyEvent.VK_J) {
            lawyer.shotEnemy();
        }

        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            isHitLawyer();
            isHitProcurator();

            repaint();
        }

    }

}
