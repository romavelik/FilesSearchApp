import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class FilesSearchApp {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Enter the rootPath");
            String rootPath = reader.readLine();
            System.out.println("Enter the depth value. The depth of root directory is 0");
            Integer depth = Integer.valueOf(reader.readLine());
            System.out.println("Enter the mask");
            String mask = reader.readLine();
//            Util.searchFiles(rootPath, depth, mask);
            AtomicBoolean isTeminated = new AtomicBoolean(false);
            BlockingQueue <SearchResults> results = new ArrayBlockingQueue<SearchResults>(1);
            Thread searchEngine = new Thread(new SearchEngine(results, rootPath, mask, depth, isTeminated));
            Thread resultsPrinter = new Thread(new ResultsPrinter(results, isTeminated));
            searchEngine.start();
            resultsPrinter.start();
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
