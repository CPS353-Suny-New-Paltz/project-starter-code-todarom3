import java.util.List;

public class InMemoryInput {
	
	 private List<Integer> inputData;
	 
    public InMemoryInput(List<Integer> inputData) {
        this.inputData = inputData;
    }

    public List<Integer> getInputData() {
        return inputData;
    }
}

//This holds a list of integers that simulate input data