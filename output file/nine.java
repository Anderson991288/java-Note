import java.io.FileWriter;
import java.io.IOException;

public class nine {
    public static void main(String[] args) {
        String filename = "multiplication table.txt";
        try {
            FileWriter writer = new FileWriter(filename);

            for (int i = 1; i <= 9; i++) {
                for (int j = 1; j <= 9; j++) {
                    int result = i * j;
                    writer.write(i + " x " + j + " = " + result + "\n");
                }
            }

            writer.close();
            System.out.println("成功寫入!：" + filename);
        } 
        catch (IOException e) {
            System.out.println("寫入文件時發生錯誤：" + e.getMessage());
        }
    }
}
