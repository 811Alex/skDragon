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
import ud.skript.sashie.skDragon.particleEngine.utils.SkriptHandler;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("Random Lightning")
@Description({"placeholder"})
@Syntaxes({"start new lightning effect with id name %string% at %objects% with particle %particlename%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%][, axis %-number%, %-number%, %-number%], radius %number%, density %number%[, isClientside %-players%][, r[ainbow]M[ode] %-boolean%], visibleRange %number%[, dis[placement]XYZ %-number%, %-number%, %-number%][, pulseDelay %-number%]"})
@Examples({"placeholder"})
public class EffLightning extends Effect {
   private Expression inputIdName;
   private Expression entLoc;
   private Expression particleName;
   private Expression inputParticleData;
   private Expression inputParticleSpeed;
   private Expression offX;
   private Expression offY;
   private Expression offZ;
   private Expression axisX;
   private Expression axisY;
   private Expression axisZ;
   private Expression radius;
   private Expression density;
   private Expression inputPlayers;
   private Expression rainbowMode;
   private Expression range;
   private Expression displaceX;
   private Expression displaceY;
   private Expression displaceZ;
   private Expression inputPulseDelay;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      int i = 0;
      this.inputIdName = exprs[i++];
      this.entLoc = exprs[i++];
      this.particleName = exprs[i++];
      this.inputParticleData = exprs[i++];
      this.inputParticleSpeed = exprs[i++];
      this.offX = exprs[i++];
      this.offY = exprs[i++];
      this.offZ = exprs[i++];
      this.axisX = exprs[i++];
      this.axisY = exprs[i++];
      this.axisZ = exprs[i++];
      this.radius = exprs[i++];
      this.density = exprs[i++];
      this.inputPlayers = exprs[i++];
      this.rainbowMode = exprs[i++];
      this.range = exprs[i++];
      this.displaceX = exprs[i++];
      this.displaceY = exprs[i++];
      this.displaceZ = exprs[i++];
      this.inputPulseDelay = exprs[i++];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "ooof";
   }

   protected void execute(@Nullable Event e) {
      Object[] center = this.entLoc.getAll(e);
      final List players = SkriptHandler.inputPlayers(e, this.inputPlayers);
      final ParticleEffect particle = (ParticleEffect)this.particleName.getSingle(e);
      final boolean rainbowMode = SkriptHandler.inputRainbowMode(e, this.rainbowMode);
      final float finalSpeed = SkriptHandler.inputParticleSpeed(e, this.inputParticleSpeed);
      final float offsetX = SkriptHandler.inputParticleOffset(e, this.offX);
      final float offsetY = SkriptHandler.inputParticleOffset(e, this.offY);
      final float offsetZ = SkriptHandler.inputParticleOffset(e, this.offZ);
      final float axisX = SkriptHandler.inputFloat(0.0F, e, this.axisX);
      final float axisY = SkriptHandler.inputFloat(0.0F, e, this.axisY);
      final float axisZ = SkriptHandler.inputFloat(0.0F, e, this.axisZ);
      final float radius = SkriptHandler.inputFloat(1.0F, e, this.radius);
      final float density = (float)SkriptHandler.inputInt(10, e, this.density);
      final Material dataMat = SkriptHandler.inputParticleDataMat(e, this.inputParticleData);
      final byte dataID = SkriptHandler.inputParticleDataID(e, this.inputParticleData);
      long finalPulseTick = SkriptHandler.inputPulseTick(e, this.inputPulseDelay);
      final double visibleRange = ((Number)this.range.getSingle(e)).doubleValue();
      final Vector displacement = SkriptHandler.inputLocDisplacement(e, this.displaceX, this.displaceY, this.displaceZ);
      final String idName = (String)this.inputIdName.getSingle(e);
      final List locations = new ArrayList();

      for(int var25 = 0; var25 < center.length; ++var25) {
         Object loc = center[var25];
         DynamicLocation location = DynamicLocation.init(loc);
         if (!location.isDynamic()) {
            location.add(displacement.getX(), displacement.getY(), displacement.getZ());
         }

         locations.add(location);
      }

      if (!EffectsLib.arraylist.containsKey(idName)) {
         int lightning = Bukkit.getServer().getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
            public void run() {
               Iterator var2 = locations.iterator();

               while(var2.hasNext()) {
                  DynamicLocation loc = (DynamicLocation)var2.next();
                  loc.update();
                  if (loc.isDynamic()) {
                     loc.add(displacement.getX(), displacement.getY(), displacement.getZ());
                  }

                  Vector axisAlignment = new Vector(1.0F - axisX, 1.0F - axisY, 1.0F - axisZ);
                  Vector vector = Vector.getRandom().multiply(2).subtract(new Vector(1, 1, 1)).multiply(axisAlignment).normalize().multiply(0.1D);
                  float count = radius * 20.0F;

                  for(float i = 0.0F; i < count; ++i) {
                     particle.display(idName, dataMat, dataID, players, loc, visibleRange, rainbowMode, offsetX, offsetY, offsetZ, finalSpeed, 1);
                     loc.add(vector);
                     if (Math.random() < 1.5D / (double)density) {
                        Vector random = Vector.getRandom().multiply(2).subtract(new Vector(1, 1, 1)).multiply(axisAlignment).normalize().multiply(0.1D);
                        vector.add(random).normalize().multiply(0.1D);
                     }
                  }
               }

            }
         }, 0L, finalPulseTick).getTaskId();
         EffectsLib.arraylist.put(idName, lightning);
      }

   }
}
