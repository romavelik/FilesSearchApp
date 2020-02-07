import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class ResultsPrinter implements Runnable {
    private BlockingQueue <SearchResults> results;

    private AtomicBoolean isTerminated;

    public ResultsPrinter(BlockingQueue<SearchResults> results, AtomicBoolean isTerminated) {
        this.results = results;
        this.isTerminated = isTerminated;
    }

    public ResultsPrinter(BlockingQueue<SearchResults> results) {
        this.results = results;
    }

    private static void printFoundFiles(String directory, List<String> fileNames) {
        System.out.printf("Files in %s\n", directory);
        if (fileNames.size() == 0) {
            System.out.println("No files matching the mask");
        }
        for (String fileName : fileNames) {
            System.out.printf("\t| %s\n", fileName);
        }
    }

    @Override
    public void run() {
        while (!isTerminated.get() || !results.isEmpty()){
            try {
                SearchResults searchResults = results.take();
                printFoundFiles(searchResults.getPath(), searchResults.getFileNames());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
