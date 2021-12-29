package ud.skript.sashie.skDragon.particleEngine.effects.custom;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import java.util.List;
import javax.annotation.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.util.Vector;
import ud.skript.sashie.skDragonCore;
import ud.skript.sashie.skDragon.particleEngine.effects.DragonEffect;
import ud.skript.sashie.skDragon.particleEngine.maths.EffectsLib;
import ud.skript.sashie.skDragon.particleEngine.utils.DragonParticle;
import ud.skript.sashie.skDragon.particleEngine.utils.ParticleEffect;
import ud.skript.sashie.skDragon.particleEngine.utils.RandomUtils;
import ud.skript.sashie.skDragon.particleEngine.utils.SkriptHandler;
import ud.skript.sashie.skDragon.particleEngine.utils.VectorUtils;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("Custom spiral circle")
@Description({""})
@Syntaxes({"drawSpircle %number% %particlename% particle[s] [rotating] at %objects% with id %string%, %number% point[s], density %number%, radius %number% and speed %number%[, offset %-number%, %-number%, %-number%][, rotate %-number%, %-number%, %-number%][, visibleTo %-players%][, visibleRange %-number%][, loop delay %-number%]"})
@Examples({""})
public class EffSpircle extends DragonEffect {
   private Expression partCount;
   private Expression inputParticleName;
   private Expression entLoc;
   private Expression inputIdName;
   private Expression inputPoints;
   private Expression inputDensity;
   private Expression inputRadius;
   private Expression inputSpeed;
   private Expression offX;
   private Expression offY;
   private Expression offZ;
   private Expression rotX;
   private Expression rotY;
   private Expression rotZ;
   private Expression inputPlayers;
   private Expression inputRange;
   private Expression inputPulseDelay;
   private String parsedSyntax = "";

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      int i = 0;
      this.partCount = exprs[i++];
      this.inputParticleName = exprs[i++];
      this.entLoc = exprs[i++];
      this.inputIdName = exprs[i++];
      this.inputPoints = exprs[i++];
      this.inputDensity = exprs[i++];
      this.inputRadius = exprs[i++];
      this.inputSpeed = exprs[i++];
      this.offX = exprs[i++];
      this.offY = exprs[i++];
      this.offZ = exprs[i++];
      this.rotX = exprs[i++];
      this.rotY = exprs[i++];
      this.rotZ = exprs[i++];
      this.inputPlayers = exprs[i++];
      this.inputRange = exprs[i++];
      this.inputPulseDelay = exprs[i++];
      this.parsedSyntax = parseResult.expr;
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "Directional Particles";
   }

   public static Location getLocation(Object location) {
      if (location instanceof Entity) {
         return ((Entity)location).getLocation();
      } else {
         return location instanceof Location ? (Location)location : null;
      }
   }

   protected void exec(@Nullable Event e) {
      final int count = SkriptHandler.inputParticleCount(e, this.partCount);
      ParticleEffect inputParticle = ParticleEffect.happyvillager;
      inputParticle = (ParticleEffect)this.inputParticleName.getSingle(e);
      final Object[] center = this.entLoc.getAll(e);
      final List players = SkriptHandler.inputPlayers(e, this.inputPlayers);
      final float offsetX = SkriptHandler.inputParticleOffset(e, this.offX);
      final float offsetY = SkriptHandler.inputParticleOffset(e, this.offY);
      final float offsetZ = SkriptHandler.inputParticleOffset(e, this.offZ);
      final float xRotation = SkriptHandler.inputFloat(0.0F, e, this.rotX);
      final float yRotation = SkriptHandler.inputFloat(0.0F, e, this.rotY);
      final float zRotation = SkriptHandler.inputFloat(0.0F, e, this.rotZ);
      final int points = SkriptHandler.inputInt(1, e, this.inputPoints);
      final int density = SkriptHandler.inputInt(20, e, this.inputDensity);
      final float radius = SkriptHandler.inputFloat(1.0F, e, this.inputRadius);
      float speed = SkriptHandler.inputFloat(0.2F, e, this.inputSpeed);
      int finalPulseTick = SkriptHandler.inputPulseTick(e, this.inputPulseDelay);
      double range = SkriptHandler.inputDouble(32.0D, e, this.inputRange);
      final String idName = (String)this.inputIdName.getSingle(e);
      final DragonParticle particle = new DragonParticle();
      particle.setRange(range);
      particle.setSpeed(speed);
      particle.setParticle(inputParticle);
      final boolean rotate = this.parsedSyntax.contains("rotating");
      if (!EffectsLib.arraylist.containsKey(idName)) {
         int dotGo = Bukkit.getServer().getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
            float finalOffsetX = offsetX;
            float finalOffsetY = offsetY;
            float finalOffsetZ = offsetZ;
            public double angularVelocityX = 0.015707964077591896D;
            public double angularVelocityY = 0.018479958176612854D;
            public double angularVelocityZ = 0.020268339663743973D;
            public float step = 0.0F;
            Vector v;

            public void run() {
               Object[] var4;
               int var3 = (var4 = center).length;

               for(int var2 = 0; var2 < var3; ++var2) {
                  Object loc = var4[var2];
                  Location location = EffSpircle.getLocation(loc);

                  for(int i2 = 1; i2 < points + 1; ++i2) {
                     for(int i = 0; i < count; ++i) {
                        if (offsetX > 0.0F) {
                           this.finalOffsetX = RandomUtils.randomRangeFloat(-offsetX, offsetX);
                        }

                        if (offsetY > 0.0F) {
                           this.finalOffsetY = RandomUtils.randomRangeFloat(-offsetY, offsetY);
                        }

                        if (offsetZ > 0.0F) {
                           this.finalOffsetZ = RandomUtils.randomRangeFloat(-offsetZ, offsetZ);
                        }

                        location.add(this.finalOffsetX, this.finalOffsetY, this.finalOffsetZ);
                        double angle = this.step * (6.2831855F / (float)density);
                        this.v = new Vector();
                        this.v.setX(Math.cos(angle) * (double)radius);
                        this.v.setZ(Math.sin(angle) * (double)radius);
                        VectorUtils.rotateAroundAxisY(this.v, (float)i2 * (6.2831855F / (float)points));
                        VectorUtils.rotateVector(this.v, xRotation * 0.017453292F, yRotation * 0.017453292F, zRotation * 0.017453292F);
                        if (rotate) {
                           VectorUtils.rotateVector(this.v, this.angularVelocityX * (double)this.step, this.angularVelocityY * (double)this.step, this.angularVelocityZ * (double)this.step);
                        }

                        particle.displayDirectional(idName, players, location, this.v);
                        location.subtract(this.finalOffsetX, this.finalOffsetY, this.finalOffsetZ);
                     }
                  }
               }

               ++this.step;
            }
         }, 0L, finalPulseTick).getTaskId();
         EffectsLib.arraylist.put(idName, dotGo);
      }

   }
}
