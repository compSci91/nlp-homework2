import java.util.List;

public class Tuple {
    public List<Double> weights;
    public double bias;

    public Tuple(List<Double> weights, double bias){
        this.weights = weights;
        this.bias = bias;
    }
}
