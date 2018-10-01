import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Document {
    Set<String> words;
    File sourceFile;

    public Document(File sourceFile, Set<String> stopWords){
        this.sourceFile = sourceFile;
        words = new HashSet<String>();
        Scanner scanner = null;

        try {
            scanner = new Scanner(sourceFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while(scanner.hasNext()){
            String nextWord = scanner.next();

            if(!stopWords.contains(nextWord)){
                    words.add(nextWord);

            }

        }
    }

    public Set<String> getWords(){
        return words;
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




    @Override
    public int hashCode(){
        return sourceFile.getName().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Document document = (Document) o;

        return sourceFile != null ? sourceFile.getName().equals(document.sourceFile.getName()) : document.sourceFile == null;

    }
}
