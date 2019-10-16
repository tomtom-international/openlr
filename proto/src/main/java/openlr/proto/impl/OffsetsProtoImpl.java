package openlr.proto.impl;

import openlr.Offsets;

public class OffsetsProtoImpl implements Offsets {
    private final int positiveOffset;
    private final int negativeOffset;

    public OffsetsProtoImpl(int positiveOffset, int negativeOffset) {
        this.positiveOffset = positiveOffset;
        this.negativeOffset = negativeOffset;
    }

    public OffsetsProtoImpl(int positiveOffset) {
        this(positiveOffset, 0);
    }

    @Override
    public int getPositiveOffset(int length) {
        return positiveOffset;
    }

    @Override
    public int getNegativeOffset(int length) {
        return negativeOffset;
    }

    @Override
    public boolean hasPositiveOffset() {
        return positiveOffset != 0;
    }

    @Override
    public boolean hasNegativeOffset() {
        return negativeOffset != 0;
    }
}
