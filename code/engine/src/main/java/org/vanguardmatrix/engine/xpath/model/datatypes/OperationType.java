package org.vanguardmatrix.engine.xpath.model.datatypes;

public enum OperationType {
    Insert(0), Delete(1), Update(2), Select(3);

    private int TYPE;

    private OperationType(int t) {
        TYPE = t;
    }

    public int getOperationType() {
        return TYPE;
    }
}
