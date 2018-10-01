import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        File positiveDirectory = new File("/Users/JoshuaHowell/Desktop/Texas A&M/Year 2/Fall 2018/Natural Language Processing/Homework 2/data/imdb1/pos/");
        File negativeDirectory = new File("/Users/JoshuaHowell/Desktop/Texas A&M/Year 2/Fall 2018/Natural Language Processing/Homework 2/data/imdb1/neg/");

        Map<Document, Double> positiveScores = new HashMap<Document, Double>();
        Map<Document, Double> negativeScores = new HashMap<Document, Double>();


        //Create Documents
        File[] positiveFiles = positiveDirectory.listFiles();

        for (int i = 0; i < positiveFiles.length; i++){
            File positiveFile = positiveFiles[i];
            Document positiveDocument = new Document(positiveFile);

            positiveScores.put(positiveDocument, 0.0);
            negativeScores.put(positiveDocument, 0.0);
        }

        File[] negativeFiles = negativeDirectory.listFiles();

        for (int i = 0; i < negativeFiles.length; i++){
            File negativeFile = negativeFiles[i];
            Document negativeDocument = new Document(negativeFile);

            positiveScores.put(negativeDocument, 0.0);
            negativeScores.put(negativeDocument, 0.0);
        }


        CrossValidationSorter crossValidationSorter = new CrossValidationSorter();

      //  for(int i = 0; i < 1000; i+=100){
        System.out.println("Get positive Files");
            List<File> positiveTestFiles = crossValidationSorter.retrieveFilesInRange(positiveDirectory, 0, 99);
            List<File> positiveTrainingFiles = crossValidationSorter.retrieveFilesOutNotInRange(positiveDirectory, 0, 99);
            Classification positiveClassification = new Classification(positiveTrainingFiles);


        System.out.println("Get negative files. ");
            List<File> negativeTestFiles = crossValidationSorter.retrieveFilesInRange(negativeDirectory, 0, 99);
            List<File> negativeTrainingFiles = crossValidationSorter.retrieveFilesOutNotInRange(negativeDirectory, 0, 99);
            Classification negativeClassification = new Classification(negativeTrainingFiles);


            int totalNumberOfDocuments = positiveTestFiles.size() + negativeTestFiles.size();


        System.out.println("*********** Calculate scores for positive files ***********");
            for(File positiveFile : positiveTestFiles){
                System.out.println("Working on: " + positiveFile.getName());
                Document positiveDocument = new Document(positiveFile);

                double bayesianPositivePercentage = positiveDocument.calculateLikelihood(positiveClassification) * positiveClassification.calculatePrior(totalNumberOfDocuments);
                positiveScores.put(positiveDocument, bayesianPositivePercentage);

                double bayesianNegativePercentage = positiveDocument.calculateLikelihood(negativeClassification) * negativeClassification.calculatePrior(totalNumberOfDocuments);
                negativeScores.put(positiveDocument, bayesianNegativePercentage);
            }

        System.out.println("*********** Calculate scores for negative files ***********");


        for(File negativeFile : negativeTestFiles){
            System.out.println("Working on: " + negativeFile.getName());

            Document negativeDocument = new Document(negativeFile);

                double bayesianPositivePercentage = negativeDocument.calculateLikelihood(positiveClassification) * positiveClassification.calculatePrior(totalNumberOfDocuments);
                positiveScores.put(negativeDocument, positiveScores.get(negativeDocument) + bayesianPositivePercentage);

                double bayesianNegativePercentage = negativeDocument.calculateLikelihood(negativeClassification) * negativeClassification.calculatePrior(totalNumberOfDocuments);
                negativeScores.put(negativeDocument, negativeScores.get(negativeDocument) + bayesianNegativePercentage);
            }

       // }



        int numberOfAccuratelyClassifiedDocuments = 0;

        for(File positiveFile : positiveTestFiles) {
            Document positiveDocument = new Document(positiveFile);

            if(positiveScores.get(positiveDocument) >= negativeScores.get(positiveDocument)){
                numberOfAccuratelyClassifiedDocuments++;
            }
        }

        for(File negativeFile : negativeTestFiles) {
            Document positiveDocument = new Document(negativeFile);

            if(negativeScores.get(positiveDocument) >= positiveScores.get(positiveDocument)){
                numberOfAccuratelyClassifiedDocuments++;
            }
        }


        System.out.println("Accuracy: " + (double)numberOfAccuratelyClassifiedDocuments/200 * 100 + "%");












    }
}
