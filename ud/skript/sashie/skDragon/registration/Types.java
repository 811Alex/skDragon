package ud.skript.sashie.skDragon.registration;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.classes.Serializer;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.yggdrasil.Fields;
import java.io.StreamCorruptedException;
import javax.annotation.Nullable;
import ud.skript.sashie.skDragon.emotes.EmoteType;
import ud.skript.sashie.skDragon.particleEngine.utils.ParticleEffect;
import ud.skript.sashie.skDragon.particleEngine.utils.enums.FontStyleEnum;
import ud.skript.sashie.skDragon.particleEngine.utils.enums.PlaneEnum;

public class Types {
   // $FF: synthetic field
   static final boolean $assertionsDisabled = !Types.class.desiredAssertionStatus();

   public static void particleEffects() {
      Classes.registerClass((new ClassInfo(ParticleEffect.class, "particlename")).user(new String[]{"particlename?"}).defaultExpression(new EventValueExpression(ParticleEffect.class)).parser(new Parser() {
         public String getVariableNamePattern() {
            return ".+";
         }

         @Nullable
         public ParticleEffect parse(String s, ParseContext cont) {
            try {
               return ParticleEffect.valueOf(s.toLowerCase().replace(" ", "_").trim().toLowerCase());
            } catch (IllegalArgumentException var4) {
               return null;
            }
         }

         public String toString(ParticleEffect eff, int i) {
            return eff.getName().toLowerCase().replace("_", "").toLowerCase();
         }

         public String toVariableNameString(ParticleEffect eff) {
            return eff.getName().toLowerCase().replace("_", "").toLowerCase();
         }
      }).serializer(new Serializer() {
         public Fields serialize(ParticleEffect w) {
            Fields f = new Fields();
            f.putObject("name", w.getName());
            return f;
         }

         public void deserialize(ParticleEffect o, Fields f) {
            if (!Types.$assertionsDisabled) {
               throw new AssertionError();
            }
         }

         public boolean canBeInstantiated() {
            return false;
         }

         protected ParticleEffect deserialize(Fields fields) throws StreamCorruptedException {
            String name = (String)fields.getObject("name", String.class);
            ParticleEffect w = ParticleEffect.valueOf(name);
            if (w == null) {
               throw new StreamCorruptedException("Missing particle effect " + name);
            } else {
               return w;
            }
         }

         @Nullable
         public ParticleEffect deserialize(String s) {
            return ParticleEffect.valueOf(s);
         }

         public boolean mustSyncDeserialization() {
            return true;
         }
      }));
   }

   public static void fontStyle() {
      Classes.registerClass((new ClassInfo(FontStyleEnum.class, "fontstyle")).user(new String[]{"fontstyle?"}).defaultExpression(new EventValueExpression(FontStyleEnum.class)).parser(new Parser() {
         public String getVariableNamePattern() {
            return ".+";
         }

         @Nullable
         public FontStyleEnum parse(String s, ParseContext cont) {
            try {
               return FontStyleEnum.valueOf(s.toUpperCase().trim());
            } catch (IllegalArgumentException var4) {
               return null;
            }
         }

         public String toString(FontStyleEnum eff, int i) {
            System.out.println(FontStyleEnum.valueOf(eff.getName().toLowerCase().trim().toLowerCase()));
            return eff.getName().toLowerCase();
         }

         public String toVariableNameString(FontStyleEnum eff) {
            return eff.getName().toLowerCase();
         }
      }).serializer(new Serializer() {
         public Fields serialize(FontStyleEnum w) {
            Fields f = new Fields();
            f.putObject("name", w.getName());
            return f;
         }

         public void deserialize(FontStyleEnum o, Fields f) {
            if (!Types.$assertionsDisabled) {
               throw new AssertionError();
            }
         }

         public boolean canBeInstantiated() {
            return false;
         }

         protected FontStyleEnum deserialize(Fields fields) throws StreamCorruptedException {
            String name = (String)fields.getObject("name", String.class);
            FontStyleEnum w = FontStyleEnum.valueOf(name);
            if (w == null) {
               throw new StreamCorruptedException("Missing particle effect " + name);
            } else {
               return w;
            }
         }

         @Nullable
         public FontStyleEnum deserialize(String s) {
            return FontStyleEnum.valueOf(s);
         }

         public boolean mustSyncDeserialization() {
            return true;
         }
      }));
   }

   public static void rotationPlane() {
      Classes.registerClass((new ClassInfo(PlaneEnum.class, "plane")).user(new String[]{"plane?"}).defaultExpression(new EventValueExpression(PlaneEnum.class)).parser(new Parser() {
         public String getVariableNamePattern() {
            return ".+";
         }

         @Nullable
         public PlaneEnum parse(String s, ParseContext cont) {
            try {
               return PlaneEnum.valueOf(s.toUpperCase().trim());
            } catch (IllegalArgumentException var4) {
               return null;
            }
         }

         public String toString(PlaneEnum eff, int i) {
            return eff.getName().toLowerCase();
         }

         public String toVariableNameString(PlaneEnum eff) {
            return eff.getName().toLowerCase();
         }
      }).serializer(new Serializer() {
         public Fields serialize(PlaneEnum w) {
            Fields f = new Fields();
            f.putObject("name", w.getName());
            return f;
         }

         public void deserialize(PlaneEnum o, Fields f) {
            if (!Types.$assertionsDisabled) {
               throw new AssertionError();
            }
         }

         public boolean canBeInstantiated() {
            return false;
         }

         protected PlaneEnum deserialize(Fields fields) throws StreamCorruptedException {
            String name = (String)fields.getObject("name", String.class);
            PlaneEnum w = PlaneEnum.valueOf(name);
            if (w == null) {
               throw new StreamCorruptedException("Missing particle effect " + name);
            } else {
               return w;
            }
         }

         @Nullable
         public PlaneEnum deserialize(String s) {
            return PlaneEnum.valueOf(s);
         }

         public boolean mustSyncDeserialization() {
            return true;
         }
      }));
   }

   public static void emotes() {
      Classes.registerClass((new ClassInfo(EmoteType.class, "emotetype")).user(new String[]{"emotetype?"}).defaultExpression(new EventValueExpression(EmoteType.class)).parser(new Parser() {
         public String getVariableNamePattern() {
            return ".+";
         }

         @Nullable
         public EmoteType parse(String s, ParseContext cont) {
            try {
               return EmoteType.valueOf(s.toUpperCase().replace(" ", "_").trim().toUpperCase());
            } catch (IllegalArgumentException var4) {
               return null;
            }
         }

         public String toString(EmoteType eff, int i) {
            return eff.getName().toLowerCase().replace("_", " ").toLowerCase();
         }

         public String toVariableNameString(EmoteType eff) {
            return eff.getName().toLowerCase().replace("_", " ").toLowerCase();
         }
      }).serializer(new Serializer() {
         public Fields serialize(EmoteType w) {
            Fields f = new Fields();
            f.putObject("name", w.getName());
            return f;
         }

         public void deserialize(EmoteType o, Fields f) {
            if (!Types.$assertionsDisabled) {
               throw new AssertionError();
            }
         }

         public boolean canBeInstantiated() {
            return false;
         }

         protected EmoteType deserialize(Fields fields) throws StreamCorruptedException {
            String name = (String)fields.getObject("name", String.class);
            EmoteType w = EmoteType.valueOf(name);
            if (w == null) {
               throw new StreamCorruptedException("Missing emote effect " + name);
            } else {
               return w;
            }
         }

         @Nullable
         public EmoteType deserialize(String s) {
            return EmoteType.valueOf(s);
         }

         public boolean mustSyncDeserialization() {
            return true;
         }
      }));
   }
}
