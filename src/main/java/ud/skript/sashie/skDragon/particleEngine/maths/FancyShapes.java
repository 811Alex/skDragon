package ud.skript.sashie.skDragon.particleEngine.maths;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import ud.skript.sashie.skDragonCore;
import ud.skript.sashie.skDragon.particleEngine.utils.DragonParticle;

public class FancyShapes {
   DragonParticle particle;
   boolean rainbowMode;
   final HashMap S = new HashMap();
   Random r = new Random();
   protected double prevPosX;
   protected double prevPosY;
   protected double prevPosZ;
   protected double posX;
   protected double posY;
   protected double posZ;
   protected double motionX;
   protected double motionY;
   protected double motionZ;
   protected int particleMaxAge;
   private double coordX;
   private double coordY;
   private double coordZ;

   public FancyShapes(DragonParticle particle, boolean rainbowMode) {
      this.particle = particle;
      this.rainbowMode = rainbowMode;
   }

   private void init1(double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
      this.motionX = xSpeedIn;
      this.motionY = ySpeedIn;
      this.motionZ = zSpeedIn;
      this.posX = xCoordIn;
      this.posY = yCoordIn;
      this.posZ = zCoordIn;
      this.coordX = this.posX;
      this.coordY = this.posY;
      this.coordZ = this.posZ;
   }

   private void init2(double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
      this.motionX = xSpeedIn;
      this.motionY = ySpeedIn;
      this.motionZ = zSpeedIn;
      this.coordX = xCoordIn;
      this.coordY = yCoordIn;
      this.coordZ = zCoordIn;
      this.prevPosX = xCoordIn + xSpeedIn;
      this.prevPosY = yCoordIn + ySpeedIn;
      this.prevPosZ = zCoordIn + zSpeedIn;
      this.posX = this.prevPosX;
      this.posY = this.prevPosY;
      this.posZ = this.prevPosZ;
   }

   public void startEnchant2(final Location pos, final String idName, final List players) {
      final int num = this.r.nextInt(999999999);
      this.S.put(num, Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(skDragonCore.skdragoncore, new Runnable() {
         boolean init = false;
         int particleAge;
         Random rand;

         public void run() {
            if (!this.init) {
               double i = FancyShapes.this.particle.getDirection().getX();
               double k = FancyShapes.this.particle.getDirection().getY();
               double j = FancyShapes.this.particle.getDirection().getZ();
               this.rand = new Random();
               FancyShapes.this.init2(pos.getX(), pos.getY() + 2.0D, pos.getZ(), (double)((float)i + this.rand.nextFloat()) - 0.5D, (float)k - this.rand.nextFloat() - 1.0F, (double)((float)j + this.rand.nextFloat()) - 0.5D);
               FancyShapes.this.particleMaxAge = (int)(Math.random() * 10.0D) + 30;
               this.init = true;
            }

            float f = (float)this.particleAge / (float)FancyShapes.this.particleMaxAge;
            f = 1.0F - f;
            float f1 = 1.0F - f;
            f1 *= f1;
            f1 *= f1;
            FancyShapes.this.posX = FancyShapes.this.coordX + FancyShapes.this.motionX * (double)f;
            FancyShapes.this.posY = FancyShapes.this.coordY + FancyShapes.this.motionY * (double)f - (double)(f1 * 1.2F);
            FancyShapes.this.posZ = FancyShapes.this.coordZ + FancyShapes.this.motionZ * (double)f;
            Location boop = new Location(pos.getWorld(), FancyShapes.this.posX, FancyShapes.this.posY, FancyShapes.this.posZ);
            FancyShapes.this.particle.displayColor(idName, players, boop, FancyShapes.this.rainbowMode);
            if (this.particleAge++ >= FancyShapes.this.particleMaxAge) {
               Bukkit.getScheduler().cancelTask((Integer)FancyShapes.this.S.get(num));
               FancyShapes.this.S.remove(num);
            }

         }
      }, 0L, 1L));
   }

   public void startEnchant1(final Location pos, final String idName, final List players) {
      final int num = this.r.nextInt(999999999);
      this.S.put(num, Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(skDragonCore.skdragoncore, new Runnable() {
         boolean init = false;
         int particleAge;

         public void run() {
            if (!this.init) {
               double i = FancyShapes.this.particle.getDirection().getX();
               double k = FancyShapes.this.particle.getDirection().getY();
               double j = FancyShapes.this.particle.getDirection().getZ();
               FancyShapes.this.init2(pos.getX(), pos.getY() + 2.0D, pos.getZ(), (double)((float)i) - 0.5D, (float)k - 1.0F, (double)((float)j) - 0.5D);
               FancyShapes.this.particleMaxAge = (int)(Math.random() * 10.0D) + 30;
               this.init = true;
            }

            float f = (float)this.particleAge / (float)FancyShapes.this.particleMaxAge;
            f = 1.0F - f;
            float f1 = 1.0F - f;
            f1 *= f1;
            f1 *= f1;
            FancyShapes.this.posX = FancyShapes.this.coordX + FancyShapes.this.motionX * (double)f;
            FancyShapes.this.posY = FancyShapes.this.coordY + FancyShapes.this.motionY * (double)f - (double)(f1 * 1.2F);
            FancyShapes.this.posZ = FancyShapes.this.coordZ + FancyShapes.this.motionZ * (double)f;
            Location boop = new Location(pos.getWorld(), FancyShapes.this.posX, FancyShapes.this.posY, FancyShapes.this.posZ);
            FancyShapes.this.particle.displayColor(idName, players, boop, FancyShapes.this.rainbowMode);
            if (this.particleAge++ >= FancyShapes.this.particleMaxAge) {
               Bukkit.getScheduler().cancelTask((Integer)FancyShapes.this.S.get(num));
               FancyShapes.this.S.remove(num);
            }

         }
      }, 0L, 1L));
   }

   public void startPortal(final Location loc, final String idName, final List players) {
      final int num = this.r.nextInt(999999999);
      this.S.put(num, Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(skDragonCore.skdragoncore, new Runnable() {
         boolean init = false;
         int particleAge;

         public void run() {
            if (!this.init) {
               double d0 = loc.clone().getX();
               double d1 = loc.clone().getY();
               double d2 = loc.clone().getZ();
               double i = FancyShapes.this.particle.getDirection().getX();
               double k = FancyShapes.this.particle.getDirection().getY();
               double j = FancyShapes.this.particle.getDirection().getZ();

               for(double d11 = 0.0D; d11 < 6.283185307179586D; d11 += 0.15707963267948966D) {
                  FancyShapes.this.init1(d0, d1 - 0.4D, d2, i + Math.cos(d11), k, j + Math.sin(d11));
                  FancyShapes.this.particleMaxAge = (int)(Math.random() * 10.0D) + 40;
               }

               this.init = true;
            }

            float f = (float)this.particleAge / (float)FancyShapes.this.particleMaxAge;
            float f1 = -f + f * f * 2.0F;
            float f2 = 1.0F - f1;
            FancyShapes.this.posX = FancyShapes.this.coordX + FancyShapes.this.motionX * (double)f2;
            FancyShapes.this.posY = FancyShapes.this.coordY + FancyShapes.this.motionY * (double)f2 + (double)(1.0F - f);
            FancyShapes.this.posZ = FancyShapes.this.coordZ + FancyShapes.this.motionZ * (double)f2;
            Location boop = new Location(loc.getWorld(), FancyShapes.this.posX, FancyShapes.this.posY, FancyShapes.this.posZ);
            FancyShapes.this.particle.displayColor(idName, players, boop, FancyShapes.this.rainbowMode);
            if (this.particleAge++ >= FancyShapes.this.particleMaxAge) {
               Bukkit.getScheduler().cancelTask((Integer)FancyShapes.this.S.get(num));
               FancyShapes.this.S.remove(num);
            }

         }
      }, 0L, 1L));
   }

   public void startLine1(final Location pos, final String idName, final List players) {
      final int num = this.r.nextInt(999999999);
      this.S.put(num, Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(skDragonCore.skdragoncore, new Runnable() {
         boolean init = false;
         int particleAge;

         public void run() {
            if (!this.init) {
               double i = FancyShapes.this.particle.getDirection().getX();
               double k = FancyShapes.this.particle.getDirection().getY();
               double j = FancyShapes.this.particle.getDirection().getZ();
               FancyShapes.this.init2(pos.getX(), pos.getY() + 2.0D, pos.getZ(), (double)((float)i) - 0.5D, (float)k - 1.0F, (double)((float)j) - 0.5D);
               FancyShapes.this.particleMaxAge = (int)(Math.random() * 10.0D) + 30;
               this.init = true;
            }

            float f = (float)this.particleAge / (float)FancyShapes.this.particleMaxAge;
            f = 1.0F - f;
            FancyShapes.this.posX = FancyShapes.this.coordX + FancyShapes.this.motionX * (double)f;
            FancyShapes.this.posY = FancyShapes.this.coordY + FancyShapes.this.motionY * (double)f;
            FancyShapes.this.posZ = FancyShapes.this.coordZ + FancyShapes.this.motionZ * (double)f;
            Location boop = new Location(pos.getWorld(), FancyShapes.this.posX, FancyShapes.this.posY, FancyShapes.this.posZ);
            FancyShapes.this.particle.displayColor(idName, players, boop, FancyShapes.this.rainbowMode);
            if (this.particleAge++ >= FancyShapes.this.particleMaxAge) {
               Bukkit.getScheduler().cancelTask((Integer)FancyShapes.this.S.get(num));
               FancyShapes.this.S.remove(num);
            }

         }
      }, 0L, 1L));
   }

   public void startLine2(final Location pos, final String idName, final List players) {
      final int num = this.r.nextInt(999999999);
      this.S.put(num, Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(skDragonCore.skdragoncore, new Runnable() {
         boolean init = false;
         int particleAge;

         public void run() {
            if (!this.init) {
               double i = FancyShapes.this.particle.getDirection().getX();
               double k = FancyShapes.this.particle.getDirection().getY();
               double j = FancyShapes.this.particle.getDirection().getZ();
               FancyShapes.this.init2(pos.getX(), pos.getY() + 2.0D, pos.getZ(), (double)((float)i) - 0.5D, (float)k - 1.0F, (double)((float)j) - 0.5D);
               FancyShapes.this.particleMaxAge = (int)(Math.random() * 10.0D) + 30;
               this.init = true;
            }

            float f = (float)this.particleAge / (float)FancyShapes.this.particleMaxAge;
            f = 1.0F - f;
            FancyShapes.this.posX = FancyShapes.this.coordX + FancyShapes.this.motionX * (double)f;
            FancyShapes.this.posY = FancyShapes.this.coordY + FancyShapes.this.motionY * (double)f;
            FancyShapes.this.posZ = FancyShapes.this.coordZ + FancyShapes.this.motionZ * (double)f;
            Location boop = new Location(pos.getWorld(), FancyShapes.this.posX, FancyShapes.this.posY, FancyShapes.this.posZ);
            FancyShapes.this.particle.displayColor(idName, players, boop, FancyShapes.this.rainbowMode);
            if (this.particleAge++ >= FancyShapes.this.particleMaxAge) {
               Bukkit.getScheduler().cancelTask((Integer)FancyShapes.this.S.get(num));
               FancyShapes.this.S.remove(num);
            }

         }
      }, 0L, 1L));
   }
}
