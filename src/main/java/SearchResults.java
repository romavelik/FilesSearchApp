import java.util.List;

public class SearchResults {
    private String path;
    private List<String> fileNames;
    private boolean exists;

    public SearchResults(String path, List<String> fileNames, boolean exists) {
        this.path = path;
        this.fileNames = fileNames;
        this.exists = exists;
    }


    public String getPath() {
        return path;
    }

    public List<String> getFileNames() {
        return fileNames;
    }

    public boolean isExists() {
        return exists;
    }
}
