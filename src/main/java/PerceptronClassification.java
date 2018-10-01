import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PerceptronClassification {
    public Tuple averagedPerceptron(List<Document> documents, List<Double> y, List<String> features, int numberOfIterations){

        List<Double> wNot = new ArrayList<Double>();
        List<Double> wAverage = new ArrayList<Double>();

        double bNot = 0;
        double bAverage = 0;

        int c = 1;

        for (int i = 0; i<numberOfIterations; i++){
            for(int n = 0; n<documents.size(); n++){

                List<Double> featureValues = getFeatureValues(features, documents.get(n));
                if(y.get(n) * dotProduct(wNot, featureValues ) + bNot <= 0){

                    wNot = addLists(wNot, scaleList(featureValues, y.get(n)));
                    bNot += y.get(n);

                    wAverage = addLists(wAverage, scaleList(featureValues, c* y.get(n)));
                    bAverage += c * y.get(n);
                }

                c = c+ 1;
            }
        }

        return new Tuple(addLists(wNot, scaleList(wAverage, -1 * c)), bNot - bAverage / c);
    }

    private List<Double> getFeatureValues(List<String> features, Document document){
        List<Double> featureValues = new ArrayList<Double>();
        Set<String> documentFeatures = document.getWords();

        for(String feature : features){
            double presenceOfFeature = documentFeatures.contains(feature) ? 1 : 0;
            featureValues.add(presenceOfFeature);
        }

        return featureValues;


    }

    private double dotProduct(List<Double> firstVector, List<Double> secondVector){
        double dotProduct = 0;

        for(int i = 0; i < firstVector.size(); i++){
            dotProduct+= (firstVector.get(i) * secondVector.get(i));
        }

        return dotProduct;
    }

    private List<Double> addLists(List<Double> originalValues, List<Double> valuesToAdd){
        List<Double> valuesToReturn = new ArrayList<Double>();

        for(int i = 0; i < originalValues.size(); i++){
            valuesToReturn.add(originalValues.get(i) + valuesToAdd.get(i));
        }

        return valuesToReturn;
    }

    private List<Double> scaleList(List<Double> listToScale, double scalar){
        List<Double> scaleList = new ArrayList<Double>();

        for(int i = 0; i < listToScale.size(); i++){
            scaleList.add(listToScale.get(i) * scalar);

        }

        return scaleList;
    }



}
