package project.checkpointtests;

import conceptualapi.ComputeEngineAPI;
import conceptualapi.ComputeEngineAPIImpl;

import networkapi.UserComputeAPI;
import networkapi.UserComputeAPIImpl;
import networkapi.UserRequest;

import processapi.DataStorageAPIImpl;

public class ManualTestingFramework {

    public static final String INPUT = "manualTestInput.txt";
    public static final String OUTPUT = "manualTestOutput.txt";

    public static void main(String[] args) {

        // Instantiate all 3 real APIs from src/

        // Process API 
        DataStorageAPIImpl dataStorage = new DataStorageAPIImpl();
        dataStorage.setOutputFilePath(OUTPUT);   // output location

        // Conceptual API
        ComputeEngineAPI computeEngine = new ComputeEngineAPIImpl();

        // Network API 
        UserComputeAPI userAPI = new UserComputeAPIImpl(computeEngine, dataStorage);


        // Run the full computation end-to-end
        UserRequest request = new UserRequest(INPUT, OUTPUT, ",");

        userAPI.processUserRequest(request);

        System.out.println("Manual test completed. Output written to: " + OUTPUT);
    }
}