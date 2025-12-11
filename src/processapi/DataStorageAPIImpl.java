package processapi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * provides a concrete implementation of the DataStorageAPI that can read
 * integers from an input file and write integers to an output file, with
 * the output file path set by the caller.
 */
public class DataStorageAPIImpl implements DataStorageAPI {

    private String outputFilePath;
    private String delimiter = ",";

    @Override
    public void setOutputFilePath(String path) {
        if (path == null || path.trim().isEmpty()) {
            throw new IllegalArgumentException("Output file path cannot be null or empty");
        }
        this.outputFilePath = path;
    }

    @Override
    public void setOutputDelimiter(String delimiter) {
        if (delimiter == null || delimiter.isEmpty()) {
            throw new IllegalArgumentException("Delimiter cannot be null or empty");
        }
        this.delimiter = delimiter;
    }

    @Override
    public DataResponse readInput(DataRequest request) {
        if (request == null || request.getSource() == null || request.getSource().trim().isEmpty()) {
            return new DataResponse(null);
        }

        String filePath = request.getSource();
        List<Integer> numbers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    numbers.add(Integer.parseInt(line));
                }
            }
        } catch (Exception e) {
            return new DataResponse(null);  
        }

        return new DataResponse(numbers);
    }

    @Override
    public DataResponse writeOutput(DataResponse response) {
        if (response == null || response.getData() == null) {
            return new DataResponse(null);   
        }
        if (outputFilePath == null) {
            return new DataResponse(null);  
        }

        File file = new File(outputFilePath);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            List<Integer> data = response.getData();

            for (int i = 0; i < data.size(); i++) {
                writer.write(data.get(i).toString());
                if (i < data.size() - 1) {
                    writer.write(this.delimiter);
                }
            }
        } catch (Exception e) {
            return new DataResponse(null);  
        }

        return response;
    }
}