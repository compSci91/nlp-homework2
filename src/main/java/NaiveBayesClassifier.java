import java.io.File;

public class NaiveBayesClassifier {
    private Classification positiveReview;
    private Classification negativeReview;

    public NaiveBayesClassifier(Classification positiveReview, Classification negativeReview){
        this.positiveReview = positiveReview;
        this.negativeReview = negativeReview;
    }

    public Classification classifiyDocument(Document document) {

    }
}
