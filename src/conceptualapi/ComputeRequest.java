package conceptualapi;

public class ComputeRequest {
    private int upperLimit;

    public ComputeRequest(int upperLimit) {
        this.upperLimit = upperLimit;
    }

    public int getUpperLimit() {
        return upperLimit;
    }
}