import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * 游戏开始界面渲染
 * 
 * @author 2313040111张鑫雅
 * @version 2.0
 */

public class StartPanel extends JPanel {
    Image background = null;
    Image title = null;
    JButton startButton = null;
    JButton continueButton = null;
    String key = "";

    public StartPanel() {
        background = new ImageIcon(getClass().getResource("/img/background.png")).getImage();
        title = new ImageIcon(getClass().getResource("/img/title.png")).getImage();

        // 使用绝对布局
        setLayout(null);

        startButton = new JButton("新游戏");
        startButton.setBounds(200, 500, 150, 50);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                key = "0";
            }
        });

        continueButton = new JButton("继续游戏");
        continueButton.setBounds(800, 500, 150, 50);
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                key = "1";
            }
        });

        add(startButton);
        add(continueButton);
    }

    public String getKey() {
        return key;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.drawImage(background, 0, 0, 1152, 648, this);
        g.drawImage(title, 300, 30, 500, 500, this);
    }
}
