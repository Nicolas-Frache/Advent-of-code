package _2023._05;

public class Converter {
    public long sourceRangeStart, destinationRangeStart, rangeLenght;

    public Converter(long destinationRangeStart, long sourceRangeStart, long rangeLength) {
        this.sourceRangeStart = sourceRangeStart;
        this.destinationRangeStart = destinationRangeStart;
        this.rangeLenght = rangeLength;
    }

    public boolean isInRange(long sourceValue) {
        return sourceValue >= sourceRangeStart && sourceValue < sourceRangeStart + rangeLenght;
    }

    public long convert(long sourceValue) {
        return destinationRangeStart + (sourceValue - sourceRangeStart);
    }
}

