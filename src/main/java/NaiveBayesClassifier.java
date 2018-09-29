public class NaiveBayesClassifier {
    private Classification positiveReview;
    private Classification negativeReview;
    private int totalNumberOfDocuments;
    public NaiveBayesClassifier(Classification positiveReview, Classification negativeReview){
        this.positiveReview = positiveReview;
        this.negativeReview = negativeReview;
        this.totalNumberOfDocuments = positiveReview.numberOfFiles + negativeReview.numberOfFiles;
    }

    public Classification classifiyDocument(Document document) {
        double positiveReviewLikelhood = document.calculateLikelihood(positiveReview);
        double positiveReviewPrior = positiveReview.calculatePrior(totalNumberOfDocuments);
        double positiveCalculation = positiveReviewLikelhood * positiveReviewPrior;

        double negativeReviewLikelihood = document.calculateLikelihood(negativeReview);
        double negativeReviewPrior = negativeReview.calculatePrior(totalNumberOfDocuments);
        double negativeCalculation =  negativeReviewLikelihood * negativeReviewPrior;

        if(positiveCalculation >= negativeCalculation){
            return  positiveReview;
        }

        return negativeReview;
    }
}
