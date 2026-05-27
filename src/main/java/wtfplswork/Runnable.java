package wtfplswork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Alex811
 */
public class Runnable implements java.lang.Runnable { // My guy passes arguments to the Runnable interface??? O.o so I added this
  protected ArrayList<Object> vars = new ArrayList<>();

  public Runnable(Object... vars){
    this.vars.addAll(Arrays.asList(vars));
  }

  @Override
  public void run() {

  }
}
