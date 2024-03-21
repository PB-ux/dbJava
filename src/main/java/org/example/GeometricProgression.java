package org.example;

public class GeometricProgression extends ProgressionEntity implements Progression {
    public int terms;
    private int currentIndex;

    GeometricProgression(int start, int step, int terms) {
        super(start, step);
        this.terms = terms;
        this.currentIndex = 1;
    }

    @Override
    public Integer next() {
        if (currentIndex < terms) {
            Integer value = getNextTerm();
            currentIndex++;
            return value;
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return "Progression {start=" + this.start + ", step=" + this.step
                + ", terms=" + this.terms + "}";
    }

    @Override
    public void reset() {
        currentIndex = 0;
    }

    @Override
    public int getNextTerm() {
        return start * (int)Math.pow(step, currentIndex);
    }
}
