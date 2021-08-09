package com.iteale.industrialcase.core.energy.grid;

public class EnergyNetSettings {
  public static final boolean logEnetApiAccesses = false;
  public static final boolean logEnetApiAccessTraces = false;
  public static boolean logGridUpdateIssues = true;
  public static boolean logGridUpdatesVerbose = false;
  public static boolean logGridCalculationIssues = true;
  public static final boolean logGridUpdatePerformance = false;
  public static final boolean logGridCalculationPerformance = false;
  public static final boolean roundLossDown = true;
  public static final int changesQueueDelay = 1;
  public static final double nonConductorResistance = 0.001D;
  public static final int bfsThreshold = 2048;
}
