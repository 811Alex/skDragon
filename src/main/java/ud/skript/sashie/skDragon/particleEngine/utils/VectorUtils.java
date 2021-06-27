package ud.skript.sashie.skDragon.particleEngine.utils;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import ud.skript.sashie.skDragon.particleEngine.utils.enums.PlaneEnum;

public final class VectorUtils {
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$ud$skript$sashie$skDragon$particleEngine$utils$enums$PlaneEnum;

   private VectorUtils() {
   }

   public static Vector rot(Vector vector, Vector axis, double angle) {
      double sin = Math.sin(angle);
      double cos = Math.cos(angle);
      Vector a = axis.clone().normalize();
      double ax = a.getX();
      double ay = a.getY();
      double az = a.getZ();
      Vector rotx = new Vector(cos + ax * ax * (1.0D - cos), ax * ay * (1.0D - cos) - az * sin, ax * az * (1.0D - cos) + ay * sin);
      Vector roty = new Vector(ay * ax * (1.0D - cos) + az * sin, cos + ay * ay * (1.0D - cos), ay * az * (1.0D - cos) - ax * sin);
      Vector rotz = new Vector(az * ax * (1.0D - cos) - ay * sin, az * ay * (1.0D - cos) + ax * sin, cos + az * az * (1.0D - cos));
      double x = rotx.dot(vector);
      double y = roty.dot(vector);
      double z = rotz.dot(vector);
      vector.setX(x).setY(y).setZ(z);
      return vector;
   }

   public static final Vector rotateAroundAxisX(Vector v, double angle) {
      double cos = Math.cos(angle);
      double sin = Math.sin(angle);
      double y = v.getY() * cos - v.getZ() * sin;
      double z = v.getY() * sin + v.getZ() * cos;
      return v.setY(y).setZ(z);
   }

   public static final Vector rotateAroundAxisY(Vector v, double angle) {
      double cos = Math.cos(angle);
      double sin = Math.sin(angle);
      double x = v.getX() * cos + v.getZ() * sin;
      double z = v.getX() * -sin + v.getZ() * cos;
      return v.setX(x).setZ(z);
   }

   public static final Vector rotateAroundAxisY(Vector v, float angle) {
      double cos = Math.cos((double)angle);
      double sin = Math.sin((double)angle);
      double x = v.getX() * cos + v.getZ() * sin;
      double z = v.getX() * -sin + v.getZ() * cos;
      return v.setX(x).setZ(z);
   }

   public static final Vector rotateAroundAxisZ(Vector v, double angle) {
      double cos = Math.cos(angle);
      double sin = Math.sin(angle);
      double x = v.getX() * cos - v.getY() * sin;
      double y = v.getX() * sin + v.getY() * cos;
      return v.setX(x).setY(y);
   }

   public static final Vector rotateVector(Vector v, double angleX, double angleY, double angleZ) {
      rotateAroundAxisX(v, angleX);
      rotateAroundAxisY(v, angleY);
      rotateAroundAxisZ(v, angleZ);
      return v;
   }

   public static final Vector rotateVector(Vector v, Location location) {
      return rotateVectorYZ(v, location.getYaw(), location.getPitch());
   }

   public static final Vector rotateVectorYZ(Vector v, float yawDegrees, float pitchDegrees) {
      double yaw = (double)(-1.0F * (yawDegrees + 90.0F));
      double pitch = (double)(-pitchDegrees);
      double cosYaw = Math.cos(yaw);
      double cosPitch = Math.cos(pitch);
      double sinYaw = Math.sin(yaw);
      double sinPitch = Math.sin(pitch);
      double initialX = v.getX();
      double initialY = v.getY();
      double x = initialX * cosPitch - initialY * sinPitch;
      double y = initialX * sinPitch + initialY * cosPitch;
      double initialZ = v.getZ();
      double z = initialZ * cosYaw - x * sinYaw;
      x = initialZ * sinYaw + x * cosYaw;
      return new Vector(x, y, z);
   }

   public static final Vector rotateVectorYX(Vector v, float yawDegrees, float pitchDegrees) {
      double yaw = (double)(-1.0F * yawDegrees);
      double pitch = (double)(-pitchDegrees);
      double cosYaw = Math.cos(yaw);
      double cosPitch = Math.cos(pitch);
      double sinYaw = Math.sin(yaw);
      double sinPitch = Math.sin(pitch);
      double initialY = v.getY();
      double initialZ = v.getZ();
      double z = initialY * sinPitch - initialZ * cosPitch;
      double y = initialY * cosPitch + initialZ * sinPitch;
      initialZ = z;
      double initialX = v.getX();
      z = z * cosYaw - initialX * sinYaw;
      double x = initialZ * sinYaw + initialX * cosYaw;
      return new Vector(x, y, z);
   }

   public static final Vector rotateVectorXYZ(Vector v, float xRadians, float yRadians, float zRadians) {
      double inputY = (double)(-1.0F * yRadians);
      double inputX = (double)(-xRadians);
      double inputZ = (double)(-zRadians);
      double cosY = Math.cos(inputY);
      double cosX = Math.cos(inputX);
      double cosZ = Math.cos(inputZ);
      double sinY = Math.sin(inputY);
      double sinX = Math.sin(inputX);
      double sinZ = Math.sin(inputZ);
      double initialY = v.getY();
      double initialZ = v.getZ();
      double z = initialY * sinX - initialZ * cosX;
      double y = initialY * cosX + initialZ * sinX;
      initialZ = z;
      double initialX = v.getX();
      z = z * cosY - initialX * sinY;
      double x = initialZ * sinY + initialX * cosY;
      initialX = v.getX();
      initialY = v.getY();
      x = initialX * cosZ - initialY * sinZ;
      y = initialX * sinZ + initialY * cosZ;
      return new Vector(x, y, z);
   }

   public static float angleXZBetweenPoints(Location center, Location facing) {
      double dx = facing.getX() - center.getX();
      double dz = -(facing.getZ() - center.getZ());
      double angle = Math.atan2(dz, dx);
      if (angle < 0.0D) {
         angle = Math.abs(angle);
      } else {
         angle = 6.283185307179586D - angle;
      }

      return (float)angle;
   }

   public static float angleXYBetweenPoints(Location center, Location facing) {
      double dx = facing.getX() - center.getX();
      double dy = -(facing.getY() - center.getY());
      double angle = Math.atan2(dy, dx);
      if (angle < 0.0D) {
         angle = Math.abs(angle);
      } else {
         angle = 6.283185307179586D - angle;
      }

      return (float)angle;
   }

   public static final double angleToXAxis(Vector vector) {
      return Math.atan2(vector.getX(), vector.getY());
   }

   public static Vector getBackVector(Location location) {
      float newZ = (float)(location.getZ() + 1.0D * Math.sin(Math.toRadians((double)(location.getYaw() + 90.0F))));
      float newX = (float)(location.getX() + 1.0D * Math.cos(Math.toRadians((double)(location.getYaw() + 90.0F))));
      return new Vector((double)newX - location.getX(), 0.0D, (double)newZ - location.getZ());
   }

   public static double offset(Entity a, Entity b) {
      return offset(a.getLocation().toVector(), b.getLocation().toVector());
   }

   public static double offset(Location a, Location b) {
      return offset(a.toVector(), b.toVector());
   }

   public static double offset(Vector a, Vector b) {
      return a.subtract(b).length();
   }

   public static double getSize(Vector v) {
      return Math.sqrt(v.getX() * v.getX() + v.getY() * v.getY() + v.getZ() * v.getZ());
   }

   public static Vector planeRotation(Vector v, PlaneEnum plane, Vector angularVelocity, int rotationStep) {
      double rotX = 0.0D;
      double rotY = 0.0D;
      double rotZ = 0.0D;
      switch($SWITCH_TABLE$ud$skript$sashie$skDragon$particleEngine$utils$enums$PlaneEnum()[plane.ordinal()]) {
      case 1:
         rotX = 3.141592653589793D / angularVelocity.getX() * (double)rotationStep;
         break;
      case 2:
         rotY = 3.141592653589793D / angularVelocity.getY() * (double)rotationStep;
         break;
      case 3:
         rotZ = 3.141592653589793D / angularVelocity.getZ() * (double)rotationStep;
         break;
      case 4:
         rotX = 3.141592653589793D / angularVelocity.getX() * (double)rotationStep;
         rotY = 3.141592653589793D / angularVelocity.getY() * (double)rotationStep;
         break;
      case 5:
         rotX = 3.141592653589793D / angularVelocity.getX() * (double)rotationStep;
         rotZ = 3.141592653589793D / angularVelocity.getZ() * (double)rotationStep;
         break;
      case 6:
         rotX = 3.141592653589793D / angularVelocity.getX() * (double)rotationStep;
         rotY = 3.141592653589793D / angularVelocity.getY() * (double)rotationStep;
         rotZ = 3.141592653589793D / angularVelocity.getZ() * (double)rotationStep;
         break;
      case 7:
         rotY = 3.141592653589793D / angularVelocity.getY() * (double)rotationStep;
         rotZ = 3.141592653589793D / angularVelocity.getZ() * (double)rotationStep;
      }

      v = rotateVectorYX(v, (float)rotY, (float)rotX);
      v = rotateVectorYZ(v, 0.0F, (float)rotZ);
      return v;
   }

   public static Vector calculateBezierPoint(float t, Vector p0, Vector p1, Vector p2, Vector p3) {
      float u = 1.0F - t;
      float tt = t * t;
      float uu = u * u;
      float uuu = uu * u;
      float ttt = tt * t;
      Vector p = p0.multiply(uuu);
      p.add(p1.multiply(3.0F * uu * t));
      p.add(p2.multiply(3.0F * u * tt));
      p.add(p3.multiply(ttt));
      return p;
   }

   public static Vector calculateBezierPoint(float t, Location p0, Location p1, Location p2, Location p3) {
      float u = 1.0F - t;
      float tt = t * t;
      float uu = u * u;
      float uuu = uu * u;
      float ttt = tt * t;
      Vector p = p0.toVector().multiply(uuu);
      p.add(p1.toVector().multiply(3.0F * uu * t));
      p.add(p2.toVector().multiply(3.0F * u * tt));
      p.add(p3.toVector().multiply(ttt));
      return p;
   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$ud$skript$sashie$skDragon$particleEngine$utils$enums$PlaneEnum() {
      int[] var10000 = $SWITCH_TABLE$ud$skript$sashie$skDragon$particleEngine$utils$enums$PlaneEnum;
      if (var10000 != null) {
         return var10000;
      } else {
         int[] var0 = new int[PlaneEnum.values().length];

         try {
            var0[PlaneEnum.X.ordinal()] = 1;
         } catch (NoSuchFieldError var7) {
         }

         try {
            var0[PlaneEnum.XY.ordinal()] = 4;
         } catch (NoSuchFieldError var6) {
         }

         try {
            var0[PlaneEnum.XYZ.ordinal()] = 6;
         } catch (NoSuchFieldError var5) {
         }

         try {
            var0[PlaneEnum.XZ.ordinal()] = 5;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[PlaneEnum.Y.ordinal()] = 2;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[PlaneEnum.YZ.ordinal()] = 7;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[PlaneEnum.Z.ordinal()] = 3;
         } catch (NoSuchFieldError var1) {
         }

         $SWITCH_TABLE$ud$skript$sashie$skDragon$particleEngine$utils$enums$PlaneEnum = var0;
         return var0;
      }
   }
}
