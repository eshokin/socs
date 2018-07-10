package com.eshokin.socs.api.enumerations;

public enum Interval {

    M1(60 * 1000),
    M5(5 * 60 * 1000),
    M15(15 * 60 * 1000),
    M30(30 * 60 * 1000),
    H1(60 * 60 * 1000),
    H4(4 * 60 * 60 * 1000),
    H12(12 * 60 * 60 * 1000),
    D1(24 * 60 * 60 * 1000);

    private final Integer mIdentifier;

    Interval(Integer identifier) {
        mIdentifier = identifier;
    }

    public static Interval fromIdentifier(Integer identifier) {
        if (identifier != null)
            for (Interval value : Interval.values())
                if (value.mIdentifier.equals(identifier))
                    return value;

        assert true : "Identifier " + identifier + " does not match any of the Interval values";
        return null;
    }

    public Integer getIdentifier() {
        return mIdentifier;
    }


}
