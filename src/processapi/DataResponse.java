package processapi;

import java.util.List;

public class DataResponse {
    private List<Integer> data;

    public DataResponse(List<Integer> data) {
        this.data = data;
    }

    public List<Integer> getData() {
        return data;
    }
}

//represents the data