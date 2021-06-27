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
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.util.Vector;
import ud.skript.sashie.skDragonCore;
import ud.skript.sashie.skDragon.particleEngine.maths.EffectsLib;
import ud.skript.sashie.skDragon.particleEngine.utils.DynamicLocation;
import ud.skript.sashie.skDragon.particleEngine.utils.ParticleEffect;
import ud.skript.sashie.skDragon.particleEngine.utils.SkriptHandler;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("drawDot")
@Description({"placeholder"})
@Syntaxes({"drawDot [count %-number%,] particle %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %objects%[, id %-string%][, isSingle %-boolean%, %-player%][, r[ainbow]M[ode] %-boolean%], visibleRange %number%[, dis[placement]XYZ %-number%, %-number%, %-number%][, pulseDelay %-number%][, keepFor %-timespan%]"})
@Examples({"placeholder"})
public class EffDot extends Effect {
   private Expression partCount;
   private Expression inputParticleString;
   private Expression inputParticleData;
   private Expression inputParticleSpeed;
   private Expression offX;
   private Expression offY;
   private Expression offZ;
   private Expression entLoc;
   private Expression inputIdName;
   private Expression inputPlayers;
   private Expression isSingle;
   private Expression isRainbowTrue;
   private Expression range;
   private Expression displaceX;
   private Expression displaceY;
   private Expression displaceZ;
   private Expression inputPulseDelay;
   private Expression inputKeepDelay;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      this.partCount = exprs[0];
      this.inputParticleString = exprs[1];
      this.inputParticleData = exprs[2];
      this.inputParticleSpeed = exprs[3];
      this.offX = exprs[4];
      this.offY = exprs[5];
      this.offZ = exprs[6];
      this.entLoc = exprs[7];
      this.inputIdName = exprs[8];
      this.isSingle = exprs[9];
      this.inputPlayers = exprs[10];
      this.isRainbowTrue = exprs[11];
      this.range = exprs[12];
      this.displaceX = exprs[13];
      this.displaceY = exprs[14];
      this.displaceZ = exprs[15];
      this.inputPulseDelay = exprs[16];
      this.inputKeepDelay = exprs[17];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "drawDot[ count %-number%,] particle %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %entites/locations%[, id %string%][, isSingle %-boolean%, %-players%][, r[ainbow]M[ode] %-boolean%], visibleRange %number%[, dis[placement]XYZ %-number%, %-number%, %-number%][, pulseDelay %-number%][, keepFor %-timespan%]";
   }

   protected void execute(@Nullable Event e) {
      final int count = SkriptHandler.inputParticleCount(e, this.partCount);
      Object[] center = this.entLoc.getAll(e);
      final Player players = SkriptHandler.inputPlayer(e, this.inputPlayers);
      final String particle = SkriptHandler.inputParticleString(e, this.inputParticleString);
      final boolean rainbowMode = SkriptHandler.inputRainbowMode(e, this.isRainbowTrue);
      final float finalSpeed = SkriptHandler.inputParticleSpeed(e, this.inputParticleSpeed);
      final float offsetX = SkriptHandler.inputParticleOffset(e, this.offX);
      final float offsetY = SkriptHandler.inputParticleOffset(e, this.offY);
      final float offsetZ = SkriptHandler.inputParticleOffset(e, this.offZ);
      final Material dataMat = SkriptHandler.inputParticleDataMat(e, this.inputParticleData);
      final byte dataID = SkriptHandler.inputParticleDataID(e, this.inputParticleData);
      final boolean isSinglePlayer = SkriptHandler.inputRainbowMode(e, this.isSingle);
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
      Object[] var26 = center;
      int var25 = center.length;

      for(int var24 = 0; var24 < var25; ++var24) {
         Object loc = var26[var24];
         DynamicLocation location = DynamicLocation.init(loc);
         if (!location.isDynamic()) {
            location.add(displacement.getX(), displacement.getY(), displacement.getZ());
         }

         locations.add(location);
      }

      if (!EffectsLib.arraylist.containsKey(idName)) {
         int dotGo = Bukkit.getServer().getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
            public float hue;

            public void run() {
               DynamicLocation loc;
               for(Iterator var2 = locations.iterator(); var2.hasNext(); ParticleEffect.valueOf(particle).display(idName, dataMat, dataID, players, loc, visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX, offsetY, offsetZ, finalSpeed, count)) {
                  loc = (DynamicLocation)var2.next();
                  loc.update();
                  if (loc.isDynamic()) {
                     loc.add(displacement.getX(), displacement.getY(), displacement.getZ());
                  }

                  if (rainbowMode) {
                     this.hue = ParticleEffect.simpleRainbowHelper(this.hue, particle);
                  }
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