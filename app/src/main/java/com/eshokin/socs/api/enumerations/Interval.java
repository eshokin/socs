package com.eshokin.socs.api.enumerations;

public enum Interval {

    DELIVERY("delivery"),
    PICKUP("pickup"),
    ALL("all");

    private final String mIdentifier;

    Interval(String identifier) {
        mIdentifier = identifier;
    }

    public static Interval fromIdentifier(String identifier) {
        if (identifier != null)
            for (Interval value : Interval.values())
                if (value.mIdentifier.equals(identifier))
                    return value;

        assert true : "Identifier " + identifier + " does not match any of the Interval values";
        return null;
    }

    public String getIdentifier() {
        return mIdentifier;
    }


}
