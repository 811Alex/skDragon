package ud.skript.sashie.skDragon.particleEngine.utils;

import java.util.Random;

public final class MathUtils {
   public static final float nanoToSec = 1.0E-9F;
   public static final float FLOAT_ROUNDING_ERROR = 1.0E-6F;
   public static final float PI = 3.1415927F;
   public static final float PI2 = 6.2831855F;
   public static final float SQRT_3 = 1.73205F;
   public static final float E = 2.7182817F;
   private static final int SIN_BITS = 14;
   private static final int SIN_MASK = 16383;
   private static final int SIN_COUNT = 16384;
   private static final float radFull = 6.2831855F;
   private static final float degFull = 360.0F;
   private static final float radToIndex = 2607.5945F;
   private static final float degToIndex = 45.511112F;
   public static final float radiansToDegrees = 57.295776F;
   public static final float radDeg = 57.295776F;
   public static final float degreesToRadians = 0.017453292F;
   public static final float degRad = 0.017453292F;
   private static final int ATAN2_BITS = 7;
   private static final int ATAN2_BITS2 = 14;
   private static final int ATAN2_MASK = 16383;
   private static final int ATAN2_COUNT = 16384;
   static final int ATAN2_DIM = (int)Math.sqrt(16384.0D);
   private static final float INV_ATAN2_DIM_MINUS_1;
   public static Random random;
   private static final int BIG_ENOUGH_INT = 16384;
   private static final double BIG_ENOUGH_FLOOR = 16384.0D;
   private static final double CEIL = 0.9999999D;
   private static final double BIG_ENOUGH_CEIL = 16384.999999999996D;
   private static final double BIG_ENOUGH_ROUND = 16384.5D;

   static {
      INV_ATAN2_DIM_MINUS_1 = 1.0F / (float)(ATAN2_DIM - 1);
      random = new Random();
   }

   public static final float sin(float radians) {
      return MathUtils.Sin.table[(int)(radians * 2607.5945F) & 16383];
   }

   public static final float cos(float radians) {
      return MathUtils.Sin.table[(int)((radians + 1.5707964F) * 2607.5945F) & 16383];
   }

   public static final float sinDeg(float degrees) {
      return MathUtils.Sin.table[(int)(degrees * 45.511112F) & 16383];
   }

   public static final float cosDeg(float degrees) {
      return MathUtils.Sin.table[(int)((degrees + 90.0F) * 45.511112F) & 16383];
   }

   public static double atan2(double y, double x) {
      if (x == 0.0D) {
         if (y > 0.0D) {
            return 1.5707963705062866D;
         } else {
            return y == 0.0D ? 0.0D : -1.5707963705062866D;
         }
      } else {
         double z = y / x;
         double atan;
         if (Math.abs(z) < 1.0D) {
            atan = z / (1.0D + 0.2800000011920929D * z * z);
            return x < 0.0D ? atan + (double)(y < 0.0D ? -3.1415927F : 3.1415927F) : atan;
         } else {
            atan = 1.5707963705062866D - z / (z * z + 0.2800000011920929D);
            return y < 0.0D ? atan - 3.1415927410125732D : atan;
         }
      }
   }

   public static final int random(int range) {
      return random.nextInt(range + 1);
   }

   public static final int random(int start, int end) {
      return start + random.nextInt(end - start + 1);
   }

   public static final boolean randomBoolean() {
      return random.nextBoolean();
   }

   public static final boolean randomBoolean(float chance) {
      return random() < chance;
   }

   public static final float random() {
      return random.nextFloat();
   }

   public static final float random(float range) {
      return random.nextFloat() * range;
   }

   public static final float random(float start, float end) {
      return start + random.nextFloat() * (end - start);
   }

   public static int nextPowerOfTwo(int value) {
      if (value == 0) {
         return 1;
      } else {
         --value;
         value |= value >> 1;
         value |= value >> 2;
         value |= value >> 4;
         value |= value >> 8;
         value |= value >> 16;
         return value + 1;
      }
   }

   public static boolean isPowerOfTwo(int value) {
      return value != 0 && (value & value - 1) == 0;
   }

   public static int clamp(int value, int min, int max) {
      if (value < min) {
         return min;
      } else {
         return value > max ? max : value;
      }
   }

   public static short clamp(short value, short min, short max) {
      if (value < min) {
         return min;
      } else {
         return value > max ? max : value;
      }
   }

   public static float clamp(float value, float min, float max) {
      if (value < min) {
         return min;
      } else {
         return value > max ? max : value;
      }
   }

   public static int floor(float x) {
      return (int)((double)x + 16384.0D) - 16384;
   }

   public static int floorPositive(float x) {
      return (int)x;
   }

   public static int ceil(float x) {
      return (int)((double)x + 16384.999999999996D) - 16384;
   }

   public static int ceilPositive(float x) {
      return (int)((double)x + 0.9999999D);
   }

   public static int round(float x) {
      return (int)((double)x + 16384.5D) - 16384;
   }

   public static int roundPositive(float x) {
      return (int)(x + 0.5F);
   }

   public static boolean isZero(float value) {
      return Math.abs(value) <= 1.0E-6F;
   }

   public static boolean isZero(float value, float tolerance) {
      return Math.abs(value) <= tolerance;
   }

   public static boolean isEqual(float a, float b) {
      return Math.abs(a - b) <= 1.0E-6F;
   }

   public static boolean isEqual(float a, float b, float tolerance) {
      return Math.abs(a - b) <= tolerance;
   }

   public static double calcDistance(double x1, double y1, double z1, double x2, double y2, double z2) {
      return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) + (z1 - z2) * (z1 - z2));
   }

   public static byte toPackedByte(float n) {
      return (byte)((int)(n * 256.0F / 360.0F));
   }

   private static class Atan2 {
      static final float[] table = new float[16384];

      static {
         for(int i = 0; i < MathUtils.ATAN2_DIM; ++i) {
            for(int j = 0; j < MathUtils.ATAN2_DIM; ++j) {
               float x0 = (float)i / (float)MathUtils.ATAN2_DIM;
               float y0 = (float)j / (float)MathUtils.ATAN2_DIM;
               table[j * MathUtils.ATAN2_DIM + i] = (float)Math.atan2((double)y0, (double)x0);
            }
         }

      }
   }

   private static class Sin {
      static final float[] table = new float[16384];

      static {
         int i;
         for(i = 0; i < 16384; ++i) {
            table[i] = (float)Math.sin((double)(((float)i + 0.5F) / 16384.0F * 6.2831855F));
         }

         for(i = 0; i < 360; i += 90) {
            table[(int)((float)i * 45.511112F) & 16383] = (float)Math.sin((double)((float)i * 0.017453292F));
         }

      }
   }
}
