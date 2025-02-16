import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

/**
 * 记录玩家成绩，当程序退出时，将数据写入到文件（Record.txt）I/O
 * 
 * @author 2313040111张鑫雅
 * @version 2.0
 */
public class Recorder {
    private static int defeatNum = 0;
    private static BufferedWriter bw = null;
    private static BufferedReader br = null;
    private static String recordFile = "D:\\CODE\\code-java\\PWBattle\\lib\\Record.txt";
    private static Vector<Procurator> procurators = null;
    private static Vector<Node> pDatas = new Vector<>();

    public static int getDefeatNum() {
        return defeatNum;
    }

    public static void setDefeatNum(int defeatNum) {
        Recorder.defeatNum = defeatNum;
    }

    public static void addDefeatNum() {
        Recorder.defeatNum++;
    }

    public static void setProcurators(Vector<Procurator> procurators) {
        Recorder.procurators = procurators;
    }

    public static void saveData() {
        try {
            bw = new BufferedWriter(new FileWriter(recordFile));

            // 保存击败的检方数量
            bw.write(defeatNum + "\r\n");

            // 记录剩余检方的坐标
            for (int i = 0; i < procurators.size(); i++) {
                Procurator p = procurators.get(i);
                if (p.isLive()) {
                    String coordinates = p.getX() + " " + p.getY() + " " + p.getDirect();
                    bw.write(coordinates + "\r\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static Vector<Node> reloadProcurator() {
        String line = "";
        try {
            br = new BufferedReader(new FileReader(recordFile));
            defeatNum = Integer.parseInt(br.readLine());
            while ((line = br.readLine()) != null) {
                String[] xyd = line.split(" ");
                Node pData = new Node(Integer.parseInt(xyd[0]), Integer.parseInt(xyd[1]), Integer.parseInt(xyd[2]));
                pDatas.add(pData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return pDatas;
    }
}
