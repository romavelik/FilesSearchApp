import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Util {

    public static void searchFiles(String rootPath, Integer depth, String mask) {
        String[] directories = directorySequence(rootPath);
        int limit = directories.length - 1 - depth;
        printFoundFiles(rootPath, lookHere(mask, rootPath));
        for (int i = directories.length - 2; i >= limit; i--) {
            String subPath = rootPath.split("\\\\".concat(directories[i + 1]))[0];
            printFoundFiles(subPath, lookHere(mask, subPath));
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
}