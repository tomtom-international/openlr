package openlr.decoder.backtracking;

public enum EntityColor {
    RED(0),
    GREEN(1),
    WHITE(2);

    private final int value;

    EntityColor(int value) {
        this.value = value;
    }
}
