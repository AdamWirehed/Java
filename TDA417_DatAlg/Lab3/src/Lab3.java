import javax.sound.midi.SysexMessage;
import java.util.stream.Stream;
import java.nio.file.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.function.*;

// The main plagiarism detection program.
// You only need to change buildIndex() and findSimilarity().
public class Lab3 {
    public static void main(String[] args) {
        try {
            String directory;
            if (args.length == 0) {
                System.out.print("Name of directory to scan: ");
                System.out.flush();
                directory = new Scanner(System.in).nextLine();
            } else directory = args[0];
            Path[] paths = Files.list(Paths.get(directory)).toArray(Path[]::new);
            Arrays.sort(paths);

            // Stopwatches time how long each phase of the program
            // takes to execute.
            Stopwatch stopwatch = new Stopwatch();
            Stopwatch stopwatch2 = new Stopwatch();

            // Read all input files
            BST<Path, Ngram[]> files = readPaths(paths);
            stopwatch.finished("Reading all input files");

            // Build index of n-grams (not implemented yet)
            BST<Ngram, ArrayList<Path>> index = buildIndex(files);
            stopwatch.finished("Building n-gram index");

            // Compute similarity of all file pairs
            BST<PathPair, Integer> similarity = findSimilarity(files, index);
            stopwatch.finished("Computing similarity scores");

            // Find most similar file pairs, arranged in
            // decreasing order of similarity
            ArrayList<PathPair> mostSimilar = findMostSimilar(similarity);
            stopwatch.finished("Finding the most similar files");
            stopwatch2.finished("In total the program");

            // Print out some statistics
            System.out.println("\nBST balance statistics:");
            System.out.printf("  files: size %d, height %d\n", files.size(), files.height());
            System.out.printf("  index: size %d, height %d\n", index.size(), index.height());
            System.out.printf("  similarity: size %d, height %d\n", similarity.size(), similarity.height());
            System.out.println("");

            // Print out the plagiarism report!
            System.out.println("Plagiarism report:");
            for (PathPair pair: mostSimilar)
                System.out.printf("%5d similarity: %s\n", similarity.get(pair), pair);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Phase 1: Read in each file and chop it into n-grams.
    static BST<Path, Ngram[]> readPaths(Path[] paths) throws IOException {
        BST<Path, Ngram[]> files = new BST<>();
        for (Path path: paths) {
            String contents = new String(Files.readAllBytes(path));
            Ngram[] ngrams = Ngram.ngrams(contents, 5);
            // Remove duplicates from the ngrams list
            // Uses the Java 8 streams API - very handy Java feature
            // which we don't cover in the course. If you want to
            // learn about it, see e.g.
            // https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html#package.description
            // or https://stackify.com/streams-guide-java-8/
            ngrams = Arrays.stream(ngrams).distinct().toArray(Ngram[]::new);
            files.put(path, ngrams);
        }

        return files;
    }

    // Phase 2: build index of n-grams (not implemented yet)
    static BST<Ngram, ArrayList<Path>> buildIndex(BST<Path, Ngram[]> files) {
        BST<Ngram, ArrayList<Path>> index = new BST<>();
        // TO DO: build index of n-grams

        // Creates an iterable of paths
        Iterable<Path> filePaths = files.keys();

        // loops through each path (file) and accesses every 5-gram for that file
        for(Path filePath : filePaths){
            Ngram[] fiveGs = files.get(filePath);

            for(Ngram fiveG : fiveGs){
                // If the 5gram already exist in the tree, we copy the current list and add the new path file to it
                if(index.contains(fiveG)){
                    ArrayList<Path> tmpPathArr = index.get(fiveG);
                    tmpPathArr.add(filePath);
                    index.put(fiveG, tmpPathArr);
                }
                // If it doesn't exist we just add a new node with the file path to the BST
                else{
                    ArrayList<Path> tmpPathArr = new ArrayList<>();
                    tmpPathArr.add(filePath);
                    index.put(fiveG, tmpPathArr);
                }
            }
        }

        /*
        // Simple testing for my own sake
        Iterable<Ngram> ngKeys = index.keys();
        for(Ngram ngKey : ngKeys){
            for(Path p : index.get(ngKey)){
                System.out.println(p);
            }
            System.out.println(ngKey);
            System.out.println();
        }*/

        return index;
    }

    // Phase 3: Count how many n-grams each pair of files has in common.
    static BST<PathPair, Integer> findSimilarity(BST<Path, Ngram[]> files, BST<Ngram, ArrayList<Path>> index) {
        BST<PathPair, Integer> similarity = new BST<>();
        // For PathPair pair (path1, path2) is the same as (path2, path1)
        // Building index based on paths and 5grams given by "files"

        for(Ngram fiveG : index.keys()){
            ArrayList<Path> pFiles = index.get(fiveG);
            if(pFiles.size() != 1){
                for(int ixF = 0; ixF < pFiles.size(); ixF++){
                    for(int ixL = 0; ixL < pFiles.size(); ixL++){
                        Path path1 = pFiles.get(ixF);
                        Path path2 = pFiles.get(ixL);
                        // Plagiarism on your own essay sounds like a lot of fun
                        if(path1 != path2) {
                            PathPair pair = new PathPair(pFiles.get(ixF), pFiles.get(ixL));

                            if (!similarity.contains(pair)) {
                                similarity.put(pair, 0);
                            }
                            similarity.put(pair, similarity.get(pair) + 1);
                        }
                    }
                }
            }
        }

        /*
        for(PathPair pp : similarity.keys()){
            System.out.println(similarity.get(pp));
            System.out.println(pp);
        }*/

        return similarity;
    }

    // Phase 4: find all pairs of files with more than 30 n-grams
    // in common, sorted in descending order of similarity.
    static ArrayList<PathPair> findMostSimilar(BST<PathPair, Integer> similarity) {
        // Find all pairs of files with more than 100 n-grams in common.
        ArrayList<PathPair> mostSimilar = new ArrayList<>();
        for (PathPair pair: similarity.keys()) {
            if (similarity.get(pair) < 30) continue;
            // Only consider each pair of files once - (a, b) and not
            // (b,a) - and also skip pairs consisting of the same file twice
            if (pair.path1.compareTo(pair.path2) <= 0) continue;

            mostSimilar.add(pair);
        }

        // Sort to have the most similar pairs first.
        Collections.sort(mostSimilar, Comparator.comparing((PathPair pair) -> similarity.get(pair)));
        Collections.reverse(mostSimilar);
        return mostSimilar;
    }
}
