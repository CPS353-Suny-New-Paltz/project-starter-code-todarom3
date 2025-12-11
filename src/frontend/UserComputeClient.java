package frontend;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import networkapi.UserComputeServiceGrpc;
import networkapi.UserRequestProto;
import networkapi.UserResponseProto;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;

public class UserComputeClient {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Prime Computation Client ===");

        System.out.println("Would you like to:");
        System.out.println("1. Enter numbers manually");
        System.out.println("2. Provide a file path to upload");
        System.out.print("Choose (1 or 2): ");

        int choice = scanner.nextInt();
        scanner.nextLine(); 

        String inputFile = null;

        if (choice == 1) {
            System.out.print("Enter comma-separated numbers (example: 10,20,30): ");
            String typed = scanner.nextLine();

            inputFile = "manual_input.txt";
            try (FileWriter writer = new FileWriter(inputFile)) {
                for (String number : typed.split(",")) {
                    writer.write(number.trim());
                    writer.write(System.lineSeparator());
                }
            } catch (Exception e) {
                System.err.println("Failed to create manual input file: " + e.getMessage());
                return;
            }

        } else if (choice == 2) {
            System.out.print("Enter the input file path: ");
            inputFile = scanner.nextLine();
        } else {
            System.out.println("Invalid choice.");
            return;
        }

        System.out.print("Enter the output file path: ");
        String outputFile = scanner.nextLine();

        System.out.print("Enter delimiter to use (default is comma): ");
        String delimiter = scanner.nextLine();
        if (delimiter.isBlank()) {
            delimiter = ",";
        }

        //grpc channel
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 5050) 
                .usePlaintext()               
                .build();

        UserComputeServiceGrpc.UserComputeServiceBlockingStub stub =
                UserComputeServiceGrpc.newBlockingStub(channel);

        //build protobuf request
        UserRequestProto request = UserRequestProto.newBuilder()
                .setInputSource(inputFile)
                .setOutputDestination(outputFile)
                .setDelimiter(delimiter)
                .build();

        System.out.println("\nSending request to server...\n");

        //call server
        try {
            UserResponseProto response = stub.processUserRequest(request);

            System.out.println("=== Server Response ===");
            System.out.println(response.getMessage());

        } catch (Exception e) {
            System.err.println("Error contacting server: " + e.getMessage());
        }

        channel.shutdown();
        scanner.close();
    }
}