package ud.skript.sashie.skDragon.particleEngine.utils;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.skript.util.Timespan;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import ud.skript.sashie.skDragon.particleEngine.utils.enums.FontStyleEnum;
import ud.skript.sashie.skDragon.particleEngine.utils.enums.PlaneEnum;

public class SkriptHandler {
   public static boolean hasNull(@Nullable Event e, @Nullable Expression... args) {

      for(int var3 = 0; var3 < args.length; ++var3) {
         Expression arg = args[var3];
         if (arg == null) {
            return true;
         }

         if (arg.isSingle() && arg.getSingle(e) == null) {
            return true;
         }

         if (!arg.isSingle() && (arg.getAll(e).length == 0 || arg.getAll(e) == null)) {
            return true;
         }
      }

      return false;
   }

   public static boolean isEntityOrLocation(@Nullable Expression entLoc) {
      Class type = entLoc.getReturnType();
      if (type != Entity.class || type != Location.class) {
         Skript.error(entLoc + " is neither an entity nor a location.", ErrorQuality.SEMANTIC_ERROR);
      }

      return false;
   }

   public static boolean getLocationNullPreCheck(@Nullable Expression entLoc) {
      Class type = entLoc.getReturnType();
      if (type != Entity.class || type != Location.class) {
         Skript.error(entLoc + " is neither an entity nor a location.", ErrorQuality.SEMANTIC_ERROR);
      }

      return false;
   }

   public static int inputParticleCount(@Nullable Event e, @Nullable Expression inputParticleCount) {
      return inputParticleCount != null && inputParticleCount.getSingle(e) != null ? ((Number)inputParticleCount.getSingle(e)).intValue() : 1;
   }

   public static String inputParticleString(@Nullable Event e, @Nullable Expression inputParticleString) {
      return inputParticleString != null && inputParticleString.getSingle(e) != null && ParticleEffect.NAME_MAP.containsKey(((String)inputParticleString.getSingle(e)).toLowerCase()) ? ((String)inputParticleString.getSingle(e)).toLowerCase() : "happyvillager";
   }

   public static Material inputParticleDataMat(@Nullable Event e, @Nullable Expression inputParticleData) {
      return inputParticleData != null && inputParticleData.getSingle(e) != null && ((ItemStack)inputParticleData.getSingle(e)).getType() != Material.AIR ? ((ItemStack)inputParticleData.getSingle(e)).getType() : Material.DIRT;
   }

   public static byte inputParticleDataID(@Nullable Event e, @Nullable Expression inputParticleData) {
      return inputParticleData != null && inputParticleData.getSingle(e) != null ? ((ItemStack)inputParticleData.getSingle(e)).getData().getData() : 0;
   }

   public static float inputParticleSpeed(@Nullable Event e, @Nullable Expression inputParticleSpeed) {
      return inputParticleSpeed != null && inputParticleSpeed.getSingle(e) != null ? ((Number)inputParticleSpeed.getSingle(e)).floatValue() : 0.0F;
   }

   public static float inputParticleOffset(@Nullable Event e, @Nullable Expression inputParticleOffset) {
      return inputParticleOffset != null && inputParticleOffset.getSingle(e) != null ? ((Number)inputParticleOffset.getSingle(e)).floatValue() : 0.0F;
   }

   public static List inputPlayers(@Nullable Event e, @Nullable Expression inputPlayers) {
      return inputPlayers != null && inputPlayers.getAll(e) != null && inputPlayers.getAll(e).length != 0 ? Arrays.asList((Player[])inputPlayers.getAll(e)) : null;
   }

   public static boolean inputRainbowMode(@Nullable Event e, @Nullable Expression inputRainbowMode) {
      return inputRainbowMode != null && inputRainbowMode.getSingle(e) != null ? (Boolean)inputRainbowMode.getSingle(e) : false;
   }

   public static float inputRadius(@Nullable Event e, @Nullable Expression inputRadius) {
      return inputRadius != null ? ((Number)inputRadius.getSingle(e)).floatValue() : 1.0F;
   }

   public static int inputParticleDensity(@Nullable Event e, @Nullable Expression inputParticleDensity) {
      return inputParticleDensity != null && inputParticleDensity.getSingle(e) != null ? ((Number)inputParticleDensity.getSingle(e)).intValue() : 20;
   }

   public static int inputNucleusDensity(@Nullable Event e, @Nullable Expression inputNucleusDensity) {
      return inputNucleusDensity != null && inputNucleusDensity.getSingle(e) != null ? ((Number)inputNucleusDensity.getSingle(e)).intValue() : 10;
   }

   public static int inputOrbitSpeed(@Nullable Event e, @Nullable Expression inputOrbitSpeed) {
      return inputOrbitSpeed != null && inputOrbitSpeed.getSingle(e) != null ? ((Number)inputOrbitSpeed.getSingle(e)).intValue() : 1;
   }

   public static int inputOrbitalCount(@Nullable Event e, @Nullable Expression inputOrbitalCount) {
      return inputOrbitalCount != null && inputOrbitalCount.getSingle(e) != null ? ((Number)inputOrbitalCount.getSingle(e)).intValue() : 3;
   }

   public static double inputLocDisplacement(@Nullable Event e, @Nullable Expression inputLocDisplacement) {
      return inputLocDisplacement != null ? ((Number)inputLocDisplacement.getSingle(e)).doubleValue() : 0.0D;
   }

   public static boolean inputRotationMode(@Nullable Event e, @Nullable Expression inputRotationMode) {
      return inputRotationMode != null && inputRotationMode.getSingle(e) != null ? (Boolean)inputRotationMode.getSingle(e) : false;
   }

   public static double inputEffectRotation(@Nullable Event e, @Nullable Expression inputEffectRotation) {
      return inputEffectRotation != null ? ((Number)inputEffectRotation.getSingle(e)).doubleValue() : 0.0D;
   }

   public static float inputHeight(@Nullable Event e, @Nullable Expression inputHeight) {
      return inputHeight != null ? ((Number)inputHeight.getSingle(e)).floatValue() : 2.0F;
   }

   public static float inputHeightMod(@Nullable Event e, @Nullable Expression inputHeightMod) {
      return inputHeightMod != null ? ((Number)inputHeightMod.getSingle(e)).floatValue() : 0.5F;
   }

   public static int inputPulseTick(@Nullable Event e, @Nullable Expression inputPulseTick) {
      return inputPulseTick != null ? ((Number)inputPulseTick.getSingle(e)).intValue() : 0;
   }

   public static long inputKeepDelay(@Nullable Event e, @Nullable Expression inputKeepDelay) {
      return inputKeepDelay != null ? ((Timespan)inputKeepDelay.getSingle(e)).getTicks_i() : 0L;
   }

   public static int inputInt(int ifNullDefault, @Nullable Event event, @Nullable Expression input) {
      return input != null && input.getSingle(event) != null ? ((Number)input.getSingle(event)).intValue() : ifNullDefault;
   }

   public static long inputLong(int ifNullDefault, @Nullable Event event, @Nullable Expression input) {
      return input != null && input.getSingle(event) != null ? ((Number)input.getSingle(event)).longValue() : (long)ifNullDefault;
   }

   public static float inputFloat(float ifNullDefault, @Nullable Event event, @Nullable Expression input) {
      return input != null && input.getSingle(event) != null ? ((Number)input.getSingle(event)).floatValue() : ifNullDefault;
   }

   public static double inputDouble(double ifNullDefault, @Nullable Event event, @Nullable Expression input) {
      return input != null && input.getSingle(event) != null ? ((Number)input.getSingle(event)).doubleValue() : ifNullDefault;
   }

   public static boolean inputBoolean(boolean ifNullDefault, @Nullable Event event, @Nullable Expression input) {
      return input != null && input.getSingle(event) != null ? (Boolean)input.getSingle(event) : ifNullDefault;
   }

   public static long inputTimespan(int ifNullDefault, @Nullable Event event, @Nullable Expression input) {
      return input != null && input.getSingle(event) != null ? ((Timespan)input.getSingle(event)).getTicks_i() : (long)ifNullDefault;
   }

   public static Player inputPlayer(@Nullable Event event, @Nullable Expression input) {
      return input != null && input.getSingle(event) != null ? (Player)input.getSingle(event) : null;
   }

   public static Material inputItemStackOutMat(Material ifNullDefault, @Nullable Event event, @Nullable Expression input) {
      return input != null && input.getSingle(event) != null ? ((ItemStack)input.getSingle(event)).getType() : ifNullDefault;
   }

   public static byte inputItemStackOutByte(byte ifNullDefault, @Nullable Event event, @Nullable Expression input) {
      return input != null && input.getSingle(event) != null ? ((ItemStack)input.getSingle(event)).getData().getData() : ifNullDefault;
   }

   public static String inputString(String ifNullDefault, @Nullable Event event, @Nullable Expression input) {
      return input != null && input.getSingle(event) != null ? ((String)input.getSingle(event)).toLowerCase() : ifNullDefault;
   }

   public static PlaneEnum inputPlane(PlaneEnum ifNullDefault, @Nullable Event event, @Nullable Expression input) {
      return input != null && input.getSingle(event) != null ? (PlaneEnum)input.getSingle(event) : ifNullDefault;
   }

   public static Font inputFont(@Nullable Event e, @Nullable Expression inputFontName, @Nullable Expression inputFontStyle, @Nullable Expression inputFontSize) {
      String fontName = "Tahoma";
      FontStyleEnum fontStyle = FontStyleEnum.PLAIN;
      int fontSize = 16;
      if (inputFontName != null && inputFontName.getSingle(e) != null && inputFontStyle != null && inputFontStyle.getSingle(e) != null && inputFontSize != null && inputFontSize.getSingle(e) != null) {
         String[] families = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
         fontName = (String)inputFontName.getSingle(e);
         fontStyle = (FontStyleEnum)inputFontStyle.getSingle(e);
         fontSize = ((Number)inputFontSize.getSingle(e)).intValue();

         for(int var9 = 0; var9 < families.length; ++var9) {
            String f = families[var9];
            if (fontName.equals(f)) {
               if (fontStyle.equals(FontStyleEnum.BOLD)) {
                  return new Font(fontName, 1, fontSize);
               }

               if (!fontStyle.equals(FontStyleEnum.BOLD_ITALIC) && !fontStyle.equals(FontStyleEnum.ITALIC_BOLD)) {
                  if (fontStyle.equals(FontStyleEnum.ITALIC)) {
                     return new Font(fontName, 2, fontSize);
                  }

                  return new Font(fontName, 0, fontSize);
               }

               return new Font(fontName, 3, fontSize);
            }
         }
      }

      return new Font(fontName, 0, fontSize);
   }

   public static Vector inputThreeValues(@Nullable Event e, @Nullable Expression input1, @Nullable Expression input2, @Nullable Expression input3) {
      return input1 != null && input1.getSingle(e) != null && input2 != null && input2.getSingle(e) != null && input3 != null && input3.getSingle(e) != null ? new Vector(((Number)input1.getSingle(e)).doubleValue(), ((Number)input2.getSingle(e)).doubleValue(), ((Number)input3.getSingle(e)).doubleValue()) : new Vector(0, 0, 0);
   }

   public static Vector inputParticleOffset(@Nullable Event e, @Nullable Expression inputParticleOffsetX, @Nullable Expression inputParticleOffsetY, @Nullable Expression inputParticleOffsetZ) {
      return inputParticleOffsetX != null && inputParticleOffsetX.getSingle(e) != null && inputParticleOffsetY != null && inputParticleOffsetY.getSingle(e) != null && inputParticleOffsetZ != null && inputParticleOffsetZ.getSingle(e) != null ? new Vector(((Number)inputParticleOffsetX.getSingle(e)).doubleValue(), ((Number)inputParticleOffsetY.getSingle(e)).doubleValue(), ((Number)inputParticleOffsetZ.getSingle(e)).doubleValue()) : new Vector(0, 0, 0);
   }

   public static Vector inputEffectRotationOld(@Nullable Event e, @Nullable Expression inputEffectRotationX, @Nullable Expression inputEffectRotationY, @Nullable Expression inputEffectRotationZ) {
      return inputEffectRotationX != null && inputEffectRotationX.getSingle(e) != null && inputEffectRotationY != null && inputEffectRotationY.getSingle(e) != null && inputEffectRotationZ != null && inputEffectRotationZ.getSingle(e) != null ? new Vector(((Number)inputEffectRotationX.getSingle(e)).doubleValue(), ((Number)inputEffectRotationY.getSingle(e)).doubleValue(), ((Number)inputEffectRotationZ.getSingle(e)).doubleValue()) : new Vector(0, 0, 0);
   }

   public static Vector inputLocDisplacement(@Nullable Event e, @Nullable Expression inputLocDisplacementX, @Nullable Expression inputLocDisplacementY, @Nullable Expression inputLocDisplacementZ) {
      return inputLocDisplacementX != null && inputLocDisplacementX.getSingle(e) != null && inputLocDisplacementY != null && inputLocDisplacementY.getSingle(e) != null && inputLocDisplacementZ != null && inputLocDisplacementZ.getSingle(e) != null ? new Vector(((Number)inputLocDisplacementX.getSingle(e)).doubleValue(), ((Number)inputLocDisplacementY.getSingle(e)).doubleValue(), ((Number)inputLocDisplacementZ.getSingle(e)).doubleValue()) : new Vector(0, 0, 0);
   }

   public static Vector inputVector(@Nullable Event e, @Nullable Expression input) {
      return input != null ? (Vector)input.getSingle(e) : new Vector(0, 1, 0);
   }
}
