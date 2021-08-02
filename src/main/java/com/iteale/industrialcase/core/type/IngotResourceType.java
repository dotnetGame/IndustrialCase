package com.iteale.industrialcase.core.type;


public enum IngotResourceType {
    alloy(0),
    bronze(1),
    copper(2),
    lead(3),

    silver(4),

    steel(5),

    tin(6),
    refined_iron(7),

    uranium(8);
    private final int id;

    IngotResourceType(int id) {
        this.id = id;
    }


    public String getName() {
        return name();
    }


    public int getId() {
        return this.id;
    }
}
