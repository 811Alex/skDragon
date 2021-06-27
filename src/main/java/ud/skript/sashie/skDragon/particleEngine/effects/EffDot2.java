package ud.skript.sashie.skDragon.particleEngine.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.util.Vector;
import ud.skript.sashie.skDragonCore;
import ud.skript.sashie.skDragon.particleEngine.maths.EffectsLib;
import ud.skript.sashie.skDragon.particleEngine.utils.DynamicLocation;
import ud.skript.sashie.skDragon.particleEngine.utils.ParticleEffect;
import ud.skript.sashie.skDragon.particleEngine.utils.RandomUtils;
import ud.skript.sashie.skDragon.particleEngine.utils.SkriptHandler;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;
import wtfplswork.Runnable;

@Name("drawDot 2")
@Description({"placeholder"})
@Syntaxes({"drawDot[ count %-number%,] particle %particlename%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%][, colo[u]rOffset %-number%, %-number%, %-number%], center %objects%[, id %-string%][, isClientside %-players%][, r[ainbow]M[ode] %-boolean%][, randomColor %-boolean%], visibleRange %number%[, dis[placement]XYZ %-number%, %-number%, %-number%][, pulseDelay %-number%][, keepFor %-timespan%]"})
@Examples({"placeholder"})
public class EffDot2 extends Effect {
   private Expression partCount;
   private Expression particleName;
   private Expression inputParticleData;
   private Expression inputParticleSpeed;
   private Expression offX;
   private Expression offY;
   private Expression offZ;
   private Expression coffX;
   private Expression coffY;
   private Expression coffZ;
   private Expression entLoc;
   private Expression inputIdName;
   private Expression inputPlayers;
   private Expression isRainbowTrue;
   private Expression inputRandomColor;
   private Expression range;
   private Expression displaceX;
   private Expression displaceY;
   private Expression displaceZ;
   private Expression inputPulseDelay;
   private Expression inputKeepDelay;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      this.partCount = exprs[0];
      this.particleName = exprs[1];
      this.inputParticleData = exprs[2];
      this.inputParticleSpeed = exprs[3];
      this.offX = exprs[4];
      this.offY = exprs[5];
      this.offZ = exprs[6];
      this.coffX = exprs[7];
      this.coffY = exprs[8];
      this.coffZ = exprs[9];
      this.entLoc = exprs[10];
      this.inputIdName = exprs[11];
      this.inputPlayers = exprs[12];
      this.isRainbowTrue = exprs[13];
      this.inputRandomColor = exprs[14];
      this.range = exprs[15];
      this.displaceX = exprs[16];
      this.displaceY = exprs[17];
      this.displaceZ = exprs[18];
      this.inputPulseDelay = exprs[19];
      this.inputKeepDelay = exprs[20];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "drawDot[ count %-number%,] particle %particlename%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%][, colo[u]rOffset %-number%, %-number%, %-number%], center %entites/locations%[, id %string%][, (visibleTo|isClientside) %-players%][, r[ainbow]M[ode] %-boolean%][, randomColor %-boolean%], visibleRange %number%[, dis[placement]XYZ %-number%, %-number%, %-number%][, pulseDelay %-number%][, keepFor %-timespan%]";
   }

   protected void execute(@Nullable Event e) {
      final int count = SkriptHandler.inputParticleCount(e, this.partCount);
      Object[] center = this.entLoc.getAll(e);
      final List players = SkriptHandler.inputPlayers(e, this.inputPlayers);
      final ParticleEffect particle = (ParticleEffect)this.particleName.getSingle(e);
      final boolean rainbowMode = SkriptHandler.inputRainbowMode(e, this.isRainbowTrue);
      final boolean randomColor = SkriptHandler.inputBoolean(false, e, this.inputRandomColor);
      final float finalSpeed = SkriptHandler.inputParticleSpeed(e, this.inputParticleSpeed);
      float offsetX = SkriptHandler.inputParticleOffset(e, this.offX);
      final float offsetY = SkriptHandler.inputParticleOffset(e, this.offY);
      final float offsetZ = SkriptHandler.inputParticleOffset(e, this.offZ);
      final float coffsetX = SkriptHandler.inputFloat(0.0F, e, this.coffX);
      final float coffsetY = SkriptHandler.inputFloat(0.0F, e, this.coffY);
      final float coffsetZ = SkriptHandler.inputFloat(0.0F, e, this.coffZ);
      final Material dataMat = SkriptHandler.inputParticleDataMat(e, this.inputParticleData);
      final byte dataID = SkriptHandler.inputParticleDataID(e, this.inputParticleData);
      long finalPulseTick = (long)SkriptHandler.inputPulseTick(e, this.inputPulseDelay);
      long finalKeepDelay = SkriptHandler.inputKeepDelay(e, this.inputKeepDelay);
      final double visibleRange = ((Number)this.range.getSingle(e)).doubleValue();
      final Vector displacement = SkriptHandler.inputLocDisplacement(e, this.displaceX, this.displaceY, this.displaceZ);
      final String idName;
      if (this.inputIdName != null) {
         idName = (String)this.inputIdName.getSingle(e);
      } else {
         idName = "&dot-" + Math.random() + "-&dot";
      }

      if (this.inputKeepDelay != null && finalPulseTick > finalKeepDelay) {
         finalPulseTick = finalKeepDelay;
      }

      final List locations = new ArrayList();
      Object[] var29 = center;
      int var28 = center.length;

      for(int var27 = 0; var27 < var28; ++var27) {
         Object loc = var29[var27];
         DynamicLocation location = DynamicLocation.init(loc);
         if (!location.isDynamic()) {
            location.add(displacement.getX(), displacement.getY(), displacement.getZ());
         }

         locations.add(location);
      }

      if (!EffectsLib.arraylist.containsKey(idName)) {
         int dotGo = Bukkit.getServer().getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable(offsetX) {
            float finalOffsetX;
            float finalOffsetY;
            float finalOffsetZ;
            float finalColorOffsetX;
            float finalColorOffsetY;
            float finalColorOffsetZ;

            {
               this.finalOffsetX = (float) vars.get(0);
               this.finalOffsetY = offsetY;
               this.finalOffsetZ = offsetZ;
               this.finalColorOffsetX = coffsetX;
               this.finalColorOffsetY = coffsetY;
               this.finalColorOffsetZ = coffsetZ;
            }

            public void run() {
               Iterator var2 = locations.iterator();

               while(true) {
                  while(var2.hasNext()) {
                     DynamicLocation loc = (DynamicLocation)var2.next();
                     loc.update();
                     if (loc.isDynamic()) {
                        loc.add(displacement.getX(), displacement.getY(), displacement.getZ());
                     }

                     if (randomColor) {
                        if (rainbowMode) {
                           this.finalOffsetY = RandomUtils.randomRangeFloat(0.0F, 255.0F);
                           this.finalOffsetZ = RandomUtils.randomRangeFloat(0.0F, 255.0F);
                        } else {
                           this.finalOffsetX = RandomUtils.randomRangeFloat(0.0F, 255.0F);
                           this.finalOffsetY = RandomUtils.randomRangeFloat(0.0F, 255.0F);
                           this.finalOffsetZ = RandomUtils.randomRangeFloat(0.0F, 255.0F);
                        }
                     }

                     if (rainbowMode) {
                        if (!randomColor) {
                           this.finalOffsetX = ParticleEffect.simpleRainbowHelper(this.finalOffsetX, particle);
                        } else {
                           this.finalOffsetX = ParticleEffect.simpleRainbowHelper(this.finalOffsetX, particle);
                           if (offsetY == 0.0F) {
                              this.finalOffsetY = 1.0F;
                           }

                           if (offsetZ == 0.0F) {
                              this.finalOffsetZ = 1.0F;
                           }
                        }
                     }

                     if (particle.hasProperty(ParticleEffect.ParticleProperty.COLORABLE)) {
                        for(int i = 0; i < count; ++i) {
                           if (coffsetX > 0.0F) {
                              this.finalColorOffsetX = RandomUtils.randomRangeFloat(-coffsetX, coffsetX);
                           }

                           if (coffsetY > 0.0F) {
                              this.finalColorOffsetY = RandomUtils.randomRangeFloat(-coffsetY, coffsetY);
                           }

                           if (coffsetZ > 0.0F) {
                              this.finalColorOffsetZ = RandomUtils.randomRangeFloat(-coffsetZ, coffsetZ);
                           }

                           loc.add((double)this.finalColorOffsetX, (double)this.finalColorOffsetY, (double)this.finalColorOffsetZ);
                           particle.display(idName, dataMat, dataID, players, loc, visibleRange, rainbowMode, this.finalOffsetX, this.finalOffsetY, this.finalOffsetZ, finalSpeed, 1);
                           loc.subtract((double)this.finalColorOffsetX, (double)this.finalColorOffsetY, (double)this.finalColorOffsetZ);
                        }
                     } else {
                        particle.display(idName, dataMat, dataID, players, loc, visibleRange, rainbowMode, this.finalOffsetX, this.finalOffsetY, this.finalOffsetZ, finalSpeed, count);
                     }
                  }

                  return;
               }
            }
         }, 0L, finalPulseTick).getTaskId();
         EffectsLib.arraylist.put(idName, dotGo);
         if (this.inputIdName == null) {
            EffectsLib.stopEffectDelayed(finalKeepDelay, idName);
         }
      }

   }
}
