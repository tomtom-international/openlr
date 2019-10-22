package openlr.map.simplemockdb;

public class SimpleMockedException extends RuntimeException {
    public SimpleMockedException(String errorMessage) {
        super("Simple mocked database exception:" + errorMessage);
    }
}
