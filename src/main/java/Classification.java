import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Classification {
    Map<String, Integer> words;
    public final int numberOfFiles;

    public Classification(File directory){
        words = new HashMap<String, Integer>();


        File[] listOfFiles = directory.listFiles();

        numberOfFiles = listOfFiles.length;


        for(int i = 0; i< numberOfFiles; i++){
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

    public int retrieveNumberOfWordAppearances(String word){
        if(words.containsKey(word)){
            return words.get(word);
        } else {
            return 0;
        }
    }

    public int retrieveTotalNumberOfWordsInTheClassification() {
        int totalNumberOfWordsInClassification = 0;

        for(String word : words.keySet()){
            totalNumberOfWordsInClassification += retrieveNumberOfWordAppearances(word);
        }

        return totalNumberOfWordsInClassification;
    }

    public double calculateFeatureLikelihood(String documentWord){
        return (double) retrieveNumberOfWordAppearances(documentWord) / (double) retrieveTotalNumberOfWordsInTheClassification();
    }

    public double calculatePrior(int totalNumberOfDocuments){
        return this.numberOfFiles / totalNumberOfDocuments;
    }
}
