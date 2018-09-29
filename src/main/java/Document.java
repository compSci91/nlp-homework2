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

    public double calculateLikelihood(Classification classification) {
        //double likelihood = 1;
        double likelihood = 0;

        for(String word : words){
//            double featureLikelihood = classification.calculateFeatureLikelihood(word);
            double featureLikelihood = Math.log(classification.calculateFeatureLikelihood(word));

            //System.out.println("Feature Likelihood for word: " + word + " is " + featureLikelihood);

           // likelihood = likelihood * featureLikelihood;

            likelihood += featureLikelihood;

            if(likelihood == 0){
                System.out.println("Likelihood is 0!");
            }
        }



        return likelihood;
    }
}
