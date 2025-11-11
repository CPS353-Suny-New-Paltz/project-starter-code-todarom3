package project.checkpointtests;

import conceptualapi.ComputeEngineAPI;
import conceptualapi.ComputeEngineAPIImpl;

import networkapi.UserComputeAPI;
import networkapi.UserComputeAPIImpl;
import networkapi.UserRequest;

import processapi.DataStorageAPIImpl;
import processapi.DataRequest;
import processapi.DataResponse;

public class ManualTestingFramework {

    public static final String INPUT = "manualTestInput.txt";
    public static final String OUTPUT = "manualTestOutput.txt";

    public static void main(String[] args) {

        // TODO 1: Instantiate all 3 APIs

        // Process API (file-based storage)
        DataStorageAPIImpl dataStorage = new DataStorageAPIImpl();
        dataStorage.setOutputFilePath(OUTPUT);   // where results will be written

        // Conceptual API (compute engine)
        ComputeEngineAPI computeEngine = new ComputeEngineAPIImpl();

        // Network API (coordinates requests)
        UserComputeAPI userAPI = new UserComputeAPIImpl(computeEngine, dataStorage);

        // TODO 2: Run computation

        // Create a request for the system
        UserRequest request = new UserRequest(INPUT, OUTPUT, ",");

        // Read input yourself 
        DataResponse inputData = dataStorage.readInput(new DataRequest(INPUT));
        System.out.println("Manual test read input: " + inputData.getData());

        // Run the full user-level API
        userAPI.processUserRequest(request);

        System.out.println("Manual test completed. Output written to: " + OUTPUT);
    }
}
