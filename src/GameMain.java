import java.awt.KeyboardFocusManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

import javax.swing.JFrame;

/**
 * 游戏主程序控制
 * 
 * @author 2313040111张鑫雅
 * @version 2.0
 */
@SuppressWarnings("all")
public class GameMain extends JFrame {
    GamePanel gp = null;
    // StartPanel sp = null;
    Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        GameMain testGame = new GameMain();
    }

    public GameMain() {
        // 开始界面渲染
        // sp = new StartPanel();
        // this.add(sp);
        // this.setSize(1162, 685);
        // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // this.setVisible(true);
        // this.setSize(1162, 685);
        // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // this.setVisible(true);

        // 等待用户点击按钮
        // while (sp.getKey().isEmpty()) {
        //     try {
        //         Thread.sleep(100);
        //     } catch (InterruptedException e) {
        //         e.printStackTrace();
        //     }
        // }

        // 移除 StartPanel 并添加 GamePanel
        // this.remove(sp);
        // gp = new GamePanel(sp.getKey());

        System.out.println("开始新的游戏--0");
        System.out.println("继续上局游戏--1");
        System.out.print("请输入：");
        String key = scan.nextLine();
        gp = new GamePanel(key);
        
        this.add(gp);
        this.addKeyListener(gp);
        this.revalidate();
        this.repaint();
        // 窗口基本设定
        this.setSize(1162, 685);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);


        new Thread(gp).start();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.saveData();
                System.exit(0);
            }

        });
    }
}
