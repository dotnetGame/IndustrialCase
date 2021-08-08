package com.iteale.industrialcase.core.init;

import java.util.ArrayList;
import java.util.List;

public class ICConfig {
    public boolean rubberTree = true;
    public List<String> rubberTreeBlacklist = new ArrayList<>();
    public float oreDensityFactor = 1.0F;
    public float treeDensityFactor = 0.1F;
    public boolean normalizeHeight = true;
    public int retrogenCheckLimit = 0;
    public int retrogenUpdateLimit = 2;
}
