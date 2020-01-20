import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FilesSearchApp {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Enter the rootPath. For example: D:\\\\dir1\\dir2");
            String rootPath = reader.readLine();
            System.out.println("Enter the depth value. The depth of root directory is 0");
            Integer depth = Integer.valueOf(reader.readLine());
            System.out.println("Enter the mask");
            String mask = reader.readLine();
            Util.searchFiles(rootPath, depth, mask);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
