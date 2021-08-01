package com.iteale.industrialcase.core.ref;

public enum ItemFolder {
    root(null),
    armor,
    battery,
    bcTrigger,
    boat,
    brewing,
    cable,
    cell,
    crafting,
    crop,
    pipe,
    reactor,
    reactorFuelRod("reactor/fuel_rod"),
    resource,
    resourceCasing("resource/casing"),
    resourceCrushed("resource/crushed"),
    resourceDust("resource/dust"),
    resourceIngot("resource/ingot"),
    resourceNuclear("resource/nuclear"),
    resourcePlate("resource/plate"),
    resourcePurified("resource/purified"),
    rotor,
    tfbp,
    tool,
    toolElectric("tool/electric"),
    toolPainter("tool/painter"),
    turnable,
    upgrade;

    ItemFolder() {
        this.path = name();
    }
    final String path;
    ItemFolder(String path) {
        this.path = path;
    }
}