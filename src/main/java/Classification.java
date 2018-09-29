import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Classification {
    Map<String, Integer> words;

    public Classification(File directory){
        words = new HashMap<String, Integer>();


        File[] listOfFiles = directory.listFiles();

        for(int i = 0; i< listOfFiles.length; i++){
            File sourceFile = listOfFiles[i];
            Scanner scanner = null;

            try {
                scanner = new Scanner(sourceFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            while(scanner.hasNext()){
                String word = scanner.next();

                if(words.containsKey(word)){
                    words.put(word, 1);
                } else {
                    words.put(word, words.get(word) + 1);
                }
            }
        }
    }

    public int retrieveWordAppearances(String word){
        if(words.containsKey(word)){
            return words.get(word);
        } else {
            return 0;
        }
    }

    public double calculateLikelihood(String documentWord){
        int wordAppearances = retrieveWordAppearances(documentWord);
        int numberOfWordsInCategory = 0;

        for(String word : words.keySet()){
            numberOfWordsInCategory += retrieveWordAppearances(word);
        }

        return (double) wordAppearances / (double) numberOfWordsInCategory;

    }
}
