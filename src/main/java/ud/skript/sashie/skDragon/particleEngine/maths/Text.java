package ud.skript.sashie.skDragon.particleEngine.maths;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import ud.skript.sashie.skDragonCore;
import ud.skript.sashie.skDragon.particleEngine.utils.DynamicLocation;
import ud.skript.sashie.skDragon.particleEngine.utils.ParticleEffect;
import ud.skript.sashie.skDragon.particleEngine.utils.StringParser;
import ud.skript.sashie.skDragon.particleEngine.utils.VectorUtils;

public class Text extends EffectsLib {
   final String text;
   final Font font;
   final ParticleEffect particle;
   final Material dataMat;
   final byte dataID;
   final float speed;
   final Vector offset;
   final String idName;
   final DynamicLocation center;
   final List players;
   final boolean rainbowMode;
   final boolean invert;
   final boolean autoFace;
   final float pixelStepX;
   final float pixelStepY;
   final float scaleSize;
   final double visibleRange;
   final Vector axis;
   final Vector displacement;
   final long delayStart;
   final long delayPulse;
   boolean initImage;
   boolean init;
   Set vectorList;
   int filter;
   BufferedImage image = null;
   float angle;
   boolean realTime;
   float finalOffsetX;
   float finalOffsetY;
   float finalOffsetZ;
   float yaw;
   float pitchX;
   float pitchZ;

   public Text(String text, Font font, ParticleEffect particle, Material dataMat, byte dataID, float speed, Vector offset, String idName, DynamicLocation center, List players, boolean rainbowMode, boolean invert, boolean autoFace, float pixelStepX, float pixelStepY, float scaleSize, double visibleRange, Vector axis, Vector displacement, long delayStart, long delayPulse) {
      this.text = text;
      this.font = font;
      this.particle = particle;
      this.dataMat = dataMat;
      this.dataID = dataID;
      this.speed = speed;
      this.offset = offset;
      this.idName = idName;
      this.center = center;
      this.players = players;
      this.rainbowMode = rainbowMode;
      this.invert = invert;
      this.autoFace = autoFace;
      this.pixelStepX = pixelStepX;
      this.pixelStepY = pixelStepY;
      this.scaleSize = scaleSize;
      this.visibleRange = visibleRange;
      this.axis = axis;
      this.displacement = displacement;
      this.delayStart = delayStart;
      this.delayPulse = delayPulse;
      this.initImage = false;
      this.init = false;
      this.vectorList = new HashSet();
      this.realTime = false;
      this.finalOffsetX = (float)offset.getX();
      this.finalOffsetY = (float)offset.getY();
      this.finalOffsetZ = (float)offset.getZ();
      this.yaw = (float)axis.getY();
      this.pitchX = (float)axis.getX();
      this.pitchZ = (float)axis.getZ();
      this.image = StringParser.stringToBufferedImage(font, text);

      for(int y = 0; y < this.image.getHeight(); y = (int)((float)y + pixelStepY)) {
         for(int x = 0; x < this.image.getWidth(); x = (int)((float)x + pixelStepX)) {
            this.filter = this.image.getRGB(x, y);
            if ((invert || Color.black.getRGB() == this.filter) && (!invert || Color.black.getRGB() != this.filter)) {
               Vector v2 = (new Vector((float)this.image.getWidth() / 2.0F - (float)x, (float)this.image.getHeight() / 2.0F - (float)y, 0.0F)).multiply(1.0F / scaleSize);
               v2 = VectorUtils.rotateVector(v2, this.pitchX * 0.017453292F, this.yaw * 0.017453292F, this.pitchZ * 0.017453292F);
               this.vectorList.add(v2);
            }
         }
      }

   }

   private void play() {
      try {
         this.center.update();
         if (!this.center.isDynamic() && !this.init) {
            this.center.add(this.displacement.getX(), this.displacement.getY(), this.displacement.getZ());
            this.init = true;
         } else if (this.center.isDynamic()) {
            this.center.add(this.displacement.getX(), this.displacement.getY(), this.displacement.getZ());
         }

         if (this.rainbowMode) {
            this.finalOffsetX = ParticleEffect.simpleRainbowHelper(this.finalOffsetX, this.particle);
            if (this.offset.getY() == 0.0D) {
               this.finalOffsetY = 1.0F;
            }

            if (this.offset.getZ() == 0.0D) {
               this.finalOffsetZ = 1.0F;
            }
         }

         Iterator var2 = this.vectorList.iterator();

         while(var2.hasNext()) {
            Vector v = (Vector)var2.next();
            if (this.center.isDynamic() && this.autoFace) {
               this.display(v, (float)Math.toRadians(this.center.getYaw()));
            } else if (!this.center.isDynamic() && this.autoFace && this.players != null) {
               Iterator var4 = this.players.iterator();

               while(var4.hasNext()) {
                  Player player = (Player)var4.next();
                  this.display(v, VectorUtils.angleXZBetweenPoints(this.center, player.getLocation()) + 90.0F);
               }
            } else if (!this.center.isDynamic() && this.autoFace && this.players == null) {
               double d2 = this.visibleRange * this.visibleRange;
               Iterator var6 = this.center.getWorld().getPlayers().iterator();

               while(var6.hasNext()) {
                  Player player = (Player)var6.next();
                  if (player.getLocation().distanceSquared(this.center) <= d2) {
                     this.display(v, VectorUtils.angleXZBetweenPoints(this.center, player.getLocation()) + 90.0F);
                  }
               }
            } else {
               this.display(v);
            }
         }
      } catch (NullPointerException var7) {
         foundNull(this.center, this.idName, var7);
         stopEffect(this.idName);
      }
   }

   private void display(Vector v, float angle) {
      this.display(VectorUtils.rotateVectorYX(v, angle, 0.0F));
   }

   private void display(Vector v) {
      this.particle.display(this.idName, this.dataMat, this.dataID, this.players, this.center.add(v), this.visibleRange, this.rainbowMode, this.finalOffsetX, this.finalOffsetY, this.finalOffsetZ, this.speed, 1);
      this.center.subtract(v);
   }

   public void draw() {
      if (!EffectsLib.arraylist.containsKey(this.idName)) {
         int txt = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(skDragonCore.skdragoncore, this::play, this.delayStart, this.delayPulse).getTaskId();
         EffectsLib.arraylist.put(this.idName, txt);
      }

   }
}
