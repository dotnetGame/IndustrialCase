package com.iteale.industrialcase.core.energy;

import java.util.concurrent.Callable;

public class GridCalculation implements Callable<Iterable<Node>> {
  public GridCalculation(Grid grid1) {
    this.grid = grid1;
  }

  
  public Iterable<Node> call() throws Exception {
    // FIXME
    // return this.grid.calculate();
    return null;
  }
  
  private final Grid grid;
}
