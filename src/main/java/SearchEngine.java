import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SearchEngine implements Runnable{
    private BlockingQueue <SearchResults> results;

    private SearchResults searchResults;

    private String rootPath;

    private String mask;

    private Integer depth;

    private AtomicBoolean isTerminated;

    public SearchEngine(BlockingQueue<SearchResults> results, String rootPath, String mask, Integer depth, AtomicBoolean isTerminated) {
        this.results = results;
        this.rootPath = rootPath;
        this.mask = mask;
        this.depth = depth;
        this.isTerminated = isTerminated;
    }

    public SearchEngine(BlockingQueue<SearchResults> results) {
        this.results = results;
    }

    public SearchEngine(BlockingQueue<SearchResults> results, String rootPath, String mask, Integer depth) {
        this.results = results;
        this.rootPath = rootPath;
        this.mask = mask;
        this.depth = depth;
    }

    public static void searchFiles(String rootPath, Integer depth, String mask, BlockingQueue <SearchResults> results, AtomicBoolean isTerminated) {
        String[] directories = directorySequence(rootPath);
        int limit = directories.length - 1 - depth;
        try {
            results.put(new SearchResults(rootPath, lookHere(mask, rootPath), true));
            for (int i = directories.length - 2; i >= limit; i--) {
                String subPath = rootPath.split("\\\\".concat(directories[i + 1]))[0];
                results.put(new SearchResults(subPath, lookHere(mask, subPath), true));
            }
            isTerminated.set(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static String[] directorySequence(String rootPath) {
        return rootPath.split("\\\\");
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

    private static List<String> lookHere(String mask, String path) {
        Pattern pattern = Pattern.compile("(.*)".concat(mask).concat("(.*)"));
        File directory = new File(path);
        File[] filesArray = directory.listFiles();
        List<String> files;
        if (filesArray == null) {
            files = Arrays.asList("Directory is absolutely empty or doesn't exist at all");
        } else {
            files = Arrays.stream(filesArray)
                    .map(file -> file.getName())
                    .filter(String -> pattern.matcher(String).matches())
                    .collect(Collectors.toList());
        }
        return files;
    }


    @Override
    public void run() {
        searchFiles(rootPath, depth, mask, results, isTerminated);
//        try {
//            results.put(new SearchResults("path", Arrays.asList("bla", "bla1")));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
