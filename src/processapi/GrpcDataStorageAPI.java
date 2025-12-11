package processapi;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.ArrayList;
import java.util.List;

import processapi.DataStoreServiceGrpc;
import processapi.DataReadRequestProto;
import processapi.DataReadResponseProto;
import processapi.DataWriteRequestProto;
import processapi.DataWriteResponseProto;

public class GrpcDataStorageAPI implements DataStorageAPI {

    private final ManagedChannel channel;
    private final DataStoreServiceGrpc.DataStoreServiceBlockingStub stub;

    private String outputFilePath;
    private String delimiter = ",";

    public GrpcDataStorageAPI(String host, int port) {
        this.channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();
        this.stub = DataStoreServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public DataResponse readInput(DataRequest request) {
        if (request == null || request.getSource() == null) {
            return new DataResponse(null);
        }

        DataReadRequestProto protoRequest = DataReadRequestProto.newBuilder()
                .setSource(request.getSource())
                .build();

        DataReadResponseProto protoResponse = stub.readInput(protoRequest);

        List<Integer> data = new ArrayList<>(protoResponse.getDataList());
        return new DataResponse(data);
    }

    @Override
    public DataResponse writeOutput(DataResponse response) {
        if (response == null || response.getData() == null ||
                outputFilePath == null || outputFilePath.trim().isEmpty()) {
            return new DataResponse(null);
        }

        DataWriteRequestProto protoRequest = DataWriteRequestProto.newBuilder()
                .addAllData(response.getData())
                .setOutputDestination(outputFilePath)
                .setDelimiter(delimiter)
                .build();

        DataWriteResponseProto protoResponse = stub.writeOutput(protoRequest);

        if (!protoResponse.getSuccess()) {
            return new DataResponse(null);
        }

        return response;
    }

    @Override
    public void setOutputDelimiter(String delimiter) {
        if (delimiter != null && !delimiter.isEmpty()) {
            this.delimiter = delimiter;
        }
    }

    @Override
    public void setOutputFilePath(String path) {
        this.outputFilePath = path;
    }

    public void shutdown() {
        channel.shutdown();
    }
}