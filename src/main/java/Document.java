import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Document {
    Set<String> words;

    public Document(File sourceFile){
        words = new HashSet<String>();
        Scanner scanner = null;

        try {
            scanner = new Scanner(sourceFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while(scanner.hasNext()){
            words.add(scanner.next());
        }
    }
}
