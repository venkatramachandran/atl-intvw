import java.util.*;

class Solution {
    public long getTotalFileSize(Store s) {
        long totalSize = 0;
        for (File f : s.getFiles()) {
            totalSize += f.getSize();
        }
        return totalSize;
    }

    public List<Collection> getTopNByFileSize(Store s, int n) {
        Map<Collection, Long> cSizes = new HashMap<>();
        for (Collection c : s.getCollections()) {
            long cSize = 0;
            for (File f : c.getFiles()) {
                cSize += f.getSize();
            }
            cSizes.put(c, cSize);
        }
        List<Collection> unsorted = Arrays.asList(cSizes.keySet().toArray(new Collection[0]));
        Collections.sort(unsorted, (c1, c2) -> (int) (cSizes.get(c2) - cSizes.get(c1)));

        return unsorted.subList(0, n);
    }
}

class File {
    private long size;
    private String name;

    public long getSize() {
        return this.size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public File(long size, String name) {
        this.size = size;
        this.name = name;
    }
}

class Collection {
    private String name;
    private List<File> files;

    public Collection(String name, List<File> files) {
        this.name = name;
        this.files = files;
    }

    public List<File> getFiles() {
        return this.files;
    }

    public String getName() {
        return this.name;
    }
    // add and remove for files
}

class Store {
    private List<Collection> collections;
    // add a file to store
    // add a file to a collection
    // remove a file from a collection
    private List<File> files;

    // get List of files as a convenience method
    public Store() {
        this.files = new List<>();
        this.collections = new List<>();
    }

    public Store(List<File> files, List<Collection> collections) {
        this.files = files;
        this.collections = collections;
    }

    public List<Collection> getCollections() {
        return this.collections;
    }

    public List<File> getFiles() {
        return this.files;
    }
}

public class Main {
    public static void main(String[] args) {
        // file1.txt (size: 100)
        // file2.txt (size: 200) in collection "collection1"
        // file3.txt (size: 200) in collection "collection1"
        // file4.txt (size: 300) in collection "collection2"
        // file5.txt (size: 10)
        File f1 = new File(100, "file1.txt");
        File f2 = new File(200, "file2.txt");
        File f3 = new File(200, "file3.txt");
        File f4 = new File(300, "file4.txt");
        File f5 = new File(10, "file5.txt");
        Collection c1 = new Collection("collection1", List.of(f2, f3));
        Collection c2 = new Collection("collection2", List.of(f4));
        List<File> allF = List.of(f1, f2, f3, f4, f5);
        List<Collection> allC = List.of(c1, c2);
        Store s = new Store(allF, allC);
        Solution sol = new Solution();
        System.out.println("Total Size:" + sol.getTotalFileSize(s));
        List<Collection> topN = sol.getTopNByFileSize(s, 2);
        for (Collection c : topN) {
            System.out.println("Name: " + c.getName());
        }
    }
}