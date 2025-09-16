package networkapi;

public class UserRequest {
    private String inputSource;
    private String outputDestination;
    private String delimiter;       

    public UserRequest(String inputSource, String outputDestination, String delimiter) {
        this.inputSource = inputSource;
        this.outputDestination = outputDestination;
        this.delimiter = delimiter;
    }

    public String getInputSource() {
        return inputSource;
    }

    public String getOutputDestination() {
        return outputDestination;
    }

    public String getDelimiter() {
        return delimiter;
    }
}