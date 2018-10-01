import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Classification {
    Map<String, Integer> words;
    public final int numberOfFiles;
    private int totalNumberOfWordsInClassification;

    public Classification(List<File> sourceFiles, Set<String> stopWords, boolean BOOLEAN_NB){
        this.words = new HashMap<String, Integer>();

        this.numberOfFiles = sourceFiles.size();

        for(File sourceFile : sourceFiles){
            Scanner scanner = null;

            try {
                scanner = new Scanner(sourceFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }



            while(scanner.hasNext()){
                String word = scanner.next();

                if(!stopWords.contains(word)) {

                    if (words.containsKey(word)) {

                        if(BOOLEAN_NB){
                            //Ignore it
                        } else {
                            totalNumberOfWordsInClassification++;
                            words.put(word, words.get(word) + 1);
                        }







                    } else {
                        totalNumberOfWordsInClassification++;
                        this.words.put(word, 1);

                    }
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
//        int totalNumberOfWordsInClassification = 0;
//
//        for(String word : words.keySet()){
//            totalNumberOfWordsInClassification += retrieveNumberOfWordAppearances(word);
//        }

        return this.totalNumberOfWordsInClassification;
    }

    public double calculateFeatureLikelihood(String documentWord){
        return ((double) retrieveNumberOfWordAppearances(documentWord) + 1 )/ ((double) retrieveTotalNumberOfWordsInTheClassification() + words.keySet().size());
    }

    public double calculatePrior(int totalNumberOfDocuments){
        return (double) this.numberOfFiles / (double) totalNumberOfDocuments;
    }
}
