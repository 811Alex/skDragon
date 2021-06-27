package ud.skript.sashie.skDragon.particleEngine.utils;

import java.util.Random;
import org.bukkit.Material;
import org.bukkit.util.Vector;

public final class RandomUtils {
   public static final Random random = new Random(System.nanoTime());

   private RandomUtils() {
   }

   public static Vector getRandomVector() {
      double x = random.nextDouble() * 2.0D - 1.0D;
      double y = random.nextDouble() * 2.0D - 1.0D;
      double z = random.nextDouble() * 2.0D - 1.0D;
      return (new Vector(x, y, z)).normalize();
   }

   public static Vector getRandomCircleVector() {
      double rnd = random.nextDouble() * 2.0D * 3.141592653589793D;
      double x = Math.cos(rnd);
      double z = Math.sin(rnd);
      return new Vector(x, 0.0D, z);
   }

   public static Vector getRandomVectorline() {
      int n3 = (int)(Math.random() * 10.0D + -5.0D);
      int n4 = (int)(Math.random() * 10.0D + -5.0D);
      return (new Vector((double)n4, Math.random() * 4.0D + -5.0D, (double)n3)).normalize();
   }

   public static Material getRandomMaterial(Material[] materials) {
      return materials[random.nextInt(materials.length)];
   }

   public static double getRandomAngle() {
      return random.nextDouble() * 2.0D * 3.141592653589793D;
   }

   public static double randomDouble(double min, double max) {
      return Math.random() < 0.5D ? (1.0D - Math.random()) * (max - min) + min : Math.random() * (max - min) + min;
   }

   public static float randomRangeFloat(float min, float max) {
      return (float)(Math.random() < 0.5D ? (1.0D - Math.random()) * (double)(max - min) + (double)min : Math.random() * (double)(max - min) + (double)min);
   }

   public static double randomRangeDouble(double min, double max) {
      return Math.random() < 0.5D ? (1.0D - Math.random()) * (max - min) + min : Math.random() * (max - min) + min;
   }

   public static int randomRangeInt(int min, int max) {
      return (new Random()).nextInt(max - min + 1) + min;
   }

   public static double randomExcludedDouble(double min, double max, double... array) {
      double var10000 = min + (max - min) * random.nextDouble();
      double output = Math.random() < 0.5D ? (1.0D - Math.random()) * (max - min) + min + 1.0D - (double)array.length : Math.random() * (max - min) + min + 1.0D - (double)array.length;
      int length = array.length;

      for(int n = 0; n < length && output >= array[n]; ++n) {
         ++output;
      }

      return output;
   }

   public static double randomDoubleWithExclusion(double min, double max, double... exclude) {
      double output = min + (max - min + 1.0D - (double)exclude.length) * random.nextDouble();

      for(int i = 0; i < exclude.length; ++i) {
         if (exclude[i] > output) {
            return output;
         }

         ++output;
      }

      return output;
   }

   public static int randomIntWithExclusion(int min, int max, int... exclude) {
      int output = min + random.nextInt(max - min + 1 - exclude.length);
      int length = exclude.length;

      for(int n = 0; n < length && output >= exclude[n]; ++n) {
         ++output;
      }

      return output;
   }

   public static float random(float range) {
      return random.nextFloat() * range;
   }

   public static int randomSign() {
      return 1 | random.nextInt() >> 31;
   }
}
