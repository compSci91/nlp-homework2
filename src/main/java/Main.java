import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        File positiveDirectory = new File("/Users/JoshuaHowell/Desktop/Texas A&M/Year 2/Fall 2018/Natural Language Processing/Homework 2/data/imdb1/pos/");
        File negativeDirectory = new File("/Users/JoshuaHowell/Desktop/Texas A&M/Year 2/Fall 2018/Natural Language Processing/Homework 2/data/imdb1/neg/");

        Map<Document, Double> positiveScores = new HashMap<Document, Double>();
        Map<Document, Double> negativeScores = new HashMap<Document, Double>();

        boolean FILTER_STOP_WORDS = false;

        Set<String> stopWords = new HashSet<String>();

        if(FILTER_STOP_WORDS){
            File stopWordsFile = new File("/Users/JoshuaHowell/Desktop/Texas A&M/Year 2/Fall 2018/Natural Language Processing/Homework 2/data/english.stop");
            Scanner scanner = null;

            try {
                scanner = new Scanner(stopWordsFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            while(scanner.hasNext()){
                stopWords.add(scanner.next());
            }

        }




        //Create Documents
        File[] positiveFiles = positiveDirectory.listFiles();

        for (int i = 0; i < positiveFiles.length; i++){
            File positiveFile = positiveFiles[i];
            Document positiveDocument = new Document(positiveFile, stopWords);

            positiveScores.put(positiveDocument, 0.0);
            negativeScores.put(positiveDocument, 0.0);
        }

        File[] negativeFiles = negativeDirectory.listFiles();

        for (int i = 0; i < negativeFiles.length; i++){
            File negativeFile = negativeFiles[i];
            Document negativeDocument = new Document(negativeFile, stopWords);

            positiveScores.put(negativeDocument, 0.0);
            negativeScores.put(negativeDocument, 0.0);
        }

        List<Double> accuracyScores = new ArrayList<Double>();

        CrossValidationSorter crossValidationSorter = new CrossValidationSorter();

        for(int i = 0; i < 1000; i+=100){
        System.out.println("Get positive Files");
            List<File> positiveTestFiles = crossValidationSorter.retrieveFilesInRange(positiveDirectory, i, i+99);
            List<File> positiveTrainingFiles = crossValidationSorter.retrieveFilesOutNotInRange(positiveDirectory, i, i+99);
            Classification positiveClassification = new Classification(positiveTrainingFiles, stopWords);


        System.out.println("Get negative files. ");
            List<File> negativeTestFiles = crossValidationSorter.retrieveFilesInRange(negativeDirectory, i, i+99);
            List<File> negativeTrainingFiles = crossValidationSorter.retrieveFilesOutNotInRange(negativeDirectory, i, i+99);
            Classification negativeClassification = new Classification(negativeTrainingFiles, stopWords);


            int totalNumberOfDocuments = positiveTestFiles.size() + negativeTestFiles.size();


     //   System.out.println("*********** Calculate scores for positive files ***********");
            for(File positiveFile : positiveTestFiles){
                System.out.println("Working on: " + positiveFile.getName());
                Document positiveDocument = new Document(positiveFile, stopWords);

                double bayesianPositivePercentage = positiveDocument.calculateLikelihood(positiveClassification) * positiveClassification.calculatePrior(totalNumberOfDocuments);
                positiveScores.put(positiveDocument, bayesianPositivePercentage);

                double bayesianNegativePercentage = positiveDocument.calculateLikelihood(negativeClassification) * negativeClassification.calculatePrior(totalNumberOfDocuments);
                negativeScores.put(positiveDocument, bayesianNegativePercentage);
            }

     //   System.out.println("*********** Calculate scores for negative files ***********");


        for(File negativeFile : negativeTestFiles){
            System.out.println("Working on: " + negativeFile.getName());

            Document negativeDocument = new Document(negativeFile, stopWords);

                double bayesianPositivePercentage = negativeDocument.calculateLikelihood(positiveClassification) * positiveClassification.calculatePrior(totalNumberOfDocuments);
                positiveScores.put(negativeDocument, positiveScores.get(negativeDocument) + bayesianPositivePercentage);

                double bayesianNegativePercentage = negativeDocument.calculateLikelihood(negativeClassification) * negativeClassification.calculatePrior(totalNumberOfDocuments);
                negativeScores.put(negativeDocument, negativeScores.get(negativeDocument) + bayesianNegativePercentage);
            }

            int numberOfAccuratelyClassifiedDocuments = 0;

            for(File positiveFile : positiveTestFiles) {
                Document positiveDocument = new Document(positiveFile, stopWords);

                if(positiveScores.get(positiveDocument) >= negativeScores.get(positiveDocument)){
                    numberOfAccuratelyClassifiedDocuments++;
                }
            }

            for(File negativeFile : negativeTestFiles) {
                Document positiveDocument = new Document(negativeFile, stopWords);

                if(negativeScores.get(positiveDocument) >= positiveScores.get(positiveDocument)){
                    numberOfAccuratelyClassifiedDocuments++;
                }
            }


            System.out.println("Accuracy: " + (double)numberOfAccuratelyClassifiedDocuments/200 * 100 + "%");

            accuracyScores.add((double)numberOfAccuratelyClassifiedDocuments/200 * 100);

        }


        double totalOfAccuracyScores = 0;

        for(double accuracyScore : accuracyScores) {
               totalOfAccuracyScores += accuracyScore;
        }

        System.out.println("Averaged Accuracy Scores: " + totalOfAccuracyScores/10 );
















    }
}
