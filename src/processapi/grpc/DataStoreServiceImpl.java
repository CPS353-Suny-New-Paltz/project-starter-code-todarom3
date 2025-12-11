package processapi.grpc;

import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;

import processapi.DataRequest;
import processapi.DataResponse;
import processapi.DataStorageAPI;

import processapi.DataReadRequestProto;
import processapi.DataReadResponseProto;
import processapi.DataWriteRequestProto;
import processapi.DataWriteResponseProto;
import processapi.DataStoreServiceGrpc;

public class DataStoreServiceImpl extends DataStoreServiceGrpc.DataStoreServiceImplBase {

    private final DataStorageAPI storage;

    public DataStoreServiceImpl(DataStorageAPI storage) {
        this.storage = storage;
    }

    @Override
    public void readInput(
            DataReadRequestProto request,
            StreamObserver<DataReadResponseProto> responseObserver) {

        try {
            DataResponse response = storage.readInput(
                    new DataRequest(request.getSource())
            );

            List<Integer> data = (response != null && response.getData() != null)
                    ? response.getData()
                    : new ArrayList<>();

            DataReadResponseProto reply = DataReadResponseProto.newBuilder()
                    .addAllData(data)
                    .build();

            responseObserver.onNext(reply);
            responseObserver.onCompleted();

        } catch (Exception e) {
            // Error = return empty list
            DataReadResponseProto reply = DataReadResponseProto.newBuilder()
                    .build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
    }

    @Override
    public void writeOutput(
            DataWriteRequestProto request,
            StreamObserver<DataWriteResponseProto> responseObserver) {

        boolean success = false;
        String errorMessage = "";

        try {
            storage.setOutputFilePath(request.getOutputDestination());
            storage.setOutputDelimiter(request.getDelimiter());

            List<Integer> numbers = new ArrayList<>(request.getDataList());
            DataResponse writeResponse = storage.writeOutput(new DataResponse(numbers));

            success = (writeResponse != null && writeResponse.getData() != null);
            if (!success) {
                errorMessage = "Underlying storage returned null or missing data.";
            }

        } catch (Exception e) {
            success = false;
            errorMessage = "Exception in writeOutput: " + e.getMessage();
        }

        DataWriteResponseProto reply = DataWriteResponseProto.newBuilder()
                .setSuccess(success)
                .setErrorMessage(errorMessage)
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}