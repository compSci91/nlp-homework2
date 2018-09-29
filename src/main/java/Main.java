import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        File positiveDirectory = new File("/Users/JoshuaHowell/Desktop/Texas A&M/Year 2/Fall 2018/Natural Language Processing/Homework 2/data/imdb1/pos/");
        File negativeDirectory = new File("/Users/JoshuaHowell/Desktop/Texas A&M/Year 2/Fall 2018/Natural Language Processing/Homework 2/data/imdb1/neg/");

        CrossValidationSorter crossValidationSorter = new CrossValidationSorter();

        List<File> positiveTestFiles = crossValidationSorter.retrieveFilesInRange(positiveDirectory, 0, 100);
        List<File> positiveTrainingFiles = crossValidationSorter.retrieveFilesOutNotInRange(positiveDirectory, 0, 100);
        Classification positiveClassification = new Classification(positiveTrainingFiles);

        List<File> negativeTestFiles = crossValidationSorter.retrieveFilesInRange(negativeDirectory, 0, 100);
        List<File> negativeTrainingFiles = crossValidationSorter.retrieveFilesOutNotInRange(negativeDirectory, 0, 100);
        Classification negativeClassification = new Classification(negativeTrainingFiles);


        int totalNumberOfDocuments = positiveTestFiles.size() + negativeTestFiles.size();


        //for(File positiveFile : positiveTestFiles){
            File positiveFile = positiveTestFiles.get(0);
            Document positiveDocument = new Document(positiveFile);
            double bayesianPositivePercentage = positiveDocument.calculateLikelihood(positiveClassification) * positiveClassification.calculatePrior(totalNumberOfDocuments);

            System.out.println("Positive Classification: " + positiveFile.getName() + " --> " + bayesianPositivePercentage);



        File negativeFile = negativeTestFiles.get(1);
        Document negativeDocument = new Document(negativeFile);
        double bayesianNegativePercentage = negativeDocument.calculateLikelihood(negativeClassification) * positiveClassification.calculatePrior(totalNumberOfDocuments);

        System.out.println("Positive Classification: " + negativeFile.getName() + " --> " + bayesianNegativePercentage);
        //}
    }
}
