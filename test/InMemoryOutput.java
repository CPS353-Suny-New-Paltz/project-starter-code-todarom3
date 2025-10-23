import java.util.ArrayList;
import java.util.List;

public class InMemoryOutput {
    private List<String> outputData = new ArrayList<>();

    public List<String> getOutputData() {
        return outputData;
    }

    public void addOutput(String line) {
        outputData.add(line);
    }
}

//This holds the “output” of my test, strings that the system writes instead of writing to a file.