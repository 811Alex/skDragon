package ud.skript.sashie.skDragon.particleEngine.maths;

import java.util.ArrayList;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class SimpleShapes {
   public static ArrayList getStar(Location center, double radius, int points, int amount) {
      World world = center.getWorld();
      ArrayList locations = new ArrayList();
      double math1 = (double)(360 / points) * 0.017453292519943295D;
      double math2 = (double)(360 / (points / 2)) * 0.017453292519943295D;
      double rcos = Math.cos(math2);
      double rsin = Math.sin(math2);

      for(int i = 1; i < points + 1; ++i) {
         double x = center.getX() + Math.cos((double)i * math1) * radius;
         double z = center.getZ() + Math.sin((double)i * math1) * radius;
         double rx = x * rcos + z * rsin;
         double rz = x * -rsin + z * rcos;
         double lx = rx - x;
         double lz = rz - z;
         double length = Math.sqrt(lx * lx + lz * lz);
         lx /= length;
         lz /= length;
         double ratio = length / (double)amount;
         lx *= ratio;
         lz *= ratio;
         x = center.getX() - lx;
         z = center.getZ() - lz;

         for(int i2 = 0; i2 < amount; ++i2) {
            x += lx;
            z += lz;
            locations.add(new Location(world, x, center.getY(), z));
         }
      }

      return locations;
   }

   public static ArrayList getStar4(Location center, double radius, float points, int lineDensity) {
      World world = center.getWorld();
      ArrayList locations = new ArrayList();
      int pts = (int)(points + 1.0F);
      float hpts = points / 2.0F;
      double math1 = 360.0F / points;
      double math2 = 360.0F / hpts;

      for(int i = 1; i < pts; ++i) {
         Vector v = new Vector(Math.cos((double)i * (math1 * 3.141592653589793D / 180.0D)) * radius, 0.0D, Math.sin((double)i * (math1 * 3.141592653589793D / 180.0D)) * radius);
         Vector star = v.clone();
         double rcos = Math.cos(math2 * 3.141592653589793D / 180.0D);
         double rsin = Math.sin(math2 * 3.141592653589793D / 180.0D);
         double rx = star.getX() * rcos + star.getZ() * rsin;
         double rz = star.getX() * -rsin + star.getZ() * rcos;
         star.setX(rx).setZ(rz);
         center.add(v);
         Vector link = star.clone().subtract(v.clone());
         float length = (float)link.length();
         link.normalize();
         float ratio = length / (float)lineDensity;
         Vector v2 = link.multiply(ratio);
         Location loc = center.clone().subtract(v2);

         for(int i2 = 0; i2 < lineDensity; ++i2) {
            loc.add(v2);
            locations.add(new Location(world, loc.getX(), center.getY(), loc.getZ()));
         }

         center.subtract(v);
      }

      return locations;
   }

   public static ArrayList getCircle(Location center, double radius, int amount) {
      World world = center.getWorld();
      double increment = 6.283185307179586D / (double)amount;
      ArrayList locations = new ArrayList();

      for(int i = 0; i < amount; ++i) {
         double angle = (double)i * increment;
         double x = center.getX() + radius * Math.cos(angle);
         double z = center.getZ() + radius * Math.sin(angle);
         locations.add(new Location(world, x, center.getY(), z));
      }

      return locations;
   }

   public static ArrayList getCircleReverse(Location center, double radius, int amount) {
      World world = center.getWorld();
      double increment = 6.283185307179586D / (double)amount;
      ArrayList locations = new ArrayList();

      for(int i = 0; i < amount; ++i) {
         double angle = (double)i * increment;
         double x = center.getX() - radius * Math.cos(angle);
         double z = center.getZ() - radius * Math.sin(angle);
         locations.add(new Location(world, x, center.getY(), z));
      }

      return locations;
   }

   public static ArrayList getLine(Location center, double radius, int amount) {
      World world = center.getWorld();
      double increment = 6.283185307179586D / (double)amount;
      ArrayList locations = new ArrayList();

      for(int i = 0; i < amount; ++i) {
         double angle = (double)i * increment;
         double x = center.getX() + radius * Math.cos(angle);
         double z = center.getZ() + radius * Math.sin(angle);
         locations.add(new Location(world, x, center.getY(), z));
      }

      return locations;
   }

   public static ArrayList circle(Location loc, Integer r, Integer h, Boolean hollow, Boolean sphere, int plus_y) {
      ArrayList circleblocks = new ArrayList();
      int cx = loc.getBlockX();
      int cy = loc.getBlockY();
      int cz = loc.getBlockZ();

      for(int x = cx - r; x <= cx + r; ++x) {
         for(int z = cz - r; z <= cz + r; ++z) {
            for(int y = sphere ? cy - r : cy; y < (sphere ? cy + r : cy + h); ++y) {
               double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
               if (dist < (double)(r * r) && (!hollow || !(dist < (double)((r - 1) * (r - 1))))) {
                  Location l = new Location(loc.getWorld(), x, y + plus_y, z);
                  circleblocks.add(l);
               }
            }
         }
      }

      return circleblocks;
   }
}
