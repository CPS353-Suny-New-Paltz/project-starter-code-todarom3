package processapi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** provides a concrete implementation of the DataStorageAPI that can read 
* integers from an input file and write integers to an output file, with 
* the output file path set by the caller.
*/

public class DataStorageAPIImpl implements DataStorageAPI {

    private String outputFilePath;
    private String delimiter = ",";   

    public void setOutputFilePath(String path) {
        // VALIDATION
        if (path == null || path.trim().isEmpty()) {
            throw new IllegalArgumentException("Output file path cannot be null or empty");
        }
        this.outputFilePath = path;
    }

    public void setOutputDelimiter(String delimiter) {
        // VALIDATION
        if (delimiter == null || delimiter.isEmpty()) {
            throw new IllegalArgumentException("Delimiter cannot be null or empty");
        }
        this.delimiter = delimiter;
    }

    @Override
    public DataResponse readInput(DataRequest request) {
        // VALIDATION
        if (request == null) {
            throw new IllegalArgumentException("DataRequest cannot be null");
        }
        if (request.getSource() == null || request.getSource().trim().isEmpty()) {
            throw new IllegalArgumentException("Input file path cannot be null or empty");
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

        } catch (IOException e) {
            throw new RuntimeException("Failed to read from file: " + filePath, e);
        }

        return new DataResponse(numbers);
    }

    @Override
    public DataResponse writeOutput(DataResponse response) {

        // VALIDATION
        if (response == null) {
            throw new IllegalArgumentException("DataResponse cannot be null");
        }
        if (outputFilePath == null) {
            throw new IllegalStateException("Output file path not set");
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

        } catch (IOException e) {
            throw new RuntimeException("Failed to write to file: " + outputFilePath, e);
        }

        return response;
    }
}