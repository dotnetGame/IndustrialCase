package com.iteale.industrialcase.core.util;


public final class Quaternion
{
  public Vector3 v;
  public double w;
  
  public Quaternion() {}
  
  public Quaternion(Vector3 v1, double w1) {
    this(v1, w1, true);
  }
  
  private Quaternion(Vector3 v1, double w1, boolean copyV) {
    this.v = copyV ? v1.copy() : v1;
    this.w = w1;
  }
  
  public Quaternion(double x, double y, double z, double w1) {
    this(new Vector3(x, y, z), w1, false);
  }
  
  public Quaternion(Quaternion q) {
    this(q.v, q.w, true);
  }
  
  public Quaternion set(Vector3 v1, double w1, boolean copyV) {
    this.v = copyV ? v1.copy() : v1;
    this.w = w1;
    
    return this;
  }
  
  public Quaternion set(double x, double y, double z, double w1) {
    this.v.x = x;
    this.v.y = y;
    this.v.z = z;
    this.w = w1;
    
    return this;
  }
  
  public Quaternion setFromAxisAngle(Vector3 axis, double angle) {
    return set(axis.copy().scale(Math.sin(angle / 2.0D)), Math.cos(angle / 2.0D), false);
  }


  
  public Quaternion mul(Quaternion q) {
    return set(this.v.copy().scale(q.w).add(q.v.copy().scale(this.w)).add(this.v.copy().cross(q.v)), this.w * q.w - this.v
        .dot(q.v), false);
  }
  
  public Quaternion inverse() {
    return set(this.v.negate(), this.w, false);
  }
  
  public Vector3 rotate(Vector3 p) {
    Vector3 vxp = this.v.copy().cross(p);

    
    p.set(p.add(this.v.copy().cross(vxp).scale(2.0D)).add(vxp.scale(2.0D * this.w)));
    
    return p;
  }
}


/* Location:              C:\Users\wangjun\Documents\github\industrialcraft-2-2.8.221-ex112-dev\!\ic2\cor\\util\Quaternion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */