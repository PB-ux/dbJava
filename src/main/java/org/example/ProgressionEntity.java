package org.example;

import java.io.Serializable;

abstract class ProgressionEntity implements Serializable {
    protected int start;
    protected int step;

    public ProgressionEntity(int start, int step) {
        this.start = start;
        this.step = step;
    }

    abstract int getNextTerm();
}
