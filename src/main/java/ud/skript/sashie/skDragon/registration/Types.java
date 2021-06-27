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
      Classes.registerClass((new ClassInfo(ParticleEffect.class, "particlename")).user("particlename?").defaultExpression(new EventValueExpression(ParticleEffect.class)).parser(new Parser() {
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

         public String toString(Object eff, int i) {
            return ((ParticleEffect)eff).getName().toLowerCase().replace("_", "").toLowerCase();
         }

         public String toVariableNameString(Object eff) {
            return ((ParticleEffect)eff).getName().toLowerCase().replace("_", "").toLowerCase();
         }
      }).serializer(new Serializer() {
         public Fields serialize(Object w) {
            Fields f = new Fields();
            f.putObject("name", ((ParticleEffect)w).getName());
            return f;
         }

         public void deserialize(Object o, Fields f) {
            if (!Types.$assertionsDisabled) {
               throw new AssertionError();
            }
         }

         public boolean canBeInstantiated() {
            return false;
         }

         protected ParticleEffect deserialize(Object fields) throws StreamCorruptedException {
            String name = ((Fields)fields).getObject("name", String.class);
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
      Classes.registerClass((new ClassInfo(FontStyleEnum.class, "fontstyle")).user("fontstyle?").defaultExpression(new EventValueExpression(FontStyleEnum.class)).parser(new Parser() {
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

         public String toString(Object eff, int i) {
            System.out.println(FontStyleEnum.valueOf(((FontStyleEnum)eff).getName().toLowerCase().trim().toLowerCase()));
            return ((FontStyleEnum)eff).getName().toLowerCase();
         }

         public String toVariableNameString(Object eff) {
            return ((FontStyleEnum)eff).getName().toLowerCase();
         }
      }).serializer(new Serializer() {
         public Fields serialize(Object w) {
            Fields f = new Fields();
            f.putObject("name", ((FontStyleEnum)w).getName());
            return f;
         }

         public void deserialize(Object o, Fields f) {
            if (!Types.$assertionsDisabled) {
               throw new AssertionError();
            }
         }

         public boolean canBeInstantiated() {
            return false;
         }

         protected FontStyleEnum deserialize(Fields fields) throws StreamCorruptedException {
            String name = fields.getObject("name", String.class);
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
      Classes.registerClass((new ClassInfo(PlaneEnum.class, "plane")).user("plane?").defaultExpression(new EventValueExpression(PlaneEnum.class)).parser(new Parser() {
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

         public String toString(Object eff, int i) {
            return ((PlaneEnum)eff).getName().toLowerCase();
         }

         public String toVariableNameString(Object eff) {
            return ((PlaneEnum)eff).getName().toLowerCase();
         }
      }).serializer(new Serializer() {
         public Fields serialize(Object w) {
            Fields f = new Fields();
            f.putObject("name", ((PlaneEnum)w).getName());
            return f;
         }

         public void deserialize(Object o, Fields f) {
            if (!Types.$assertionsDisabled) {
               throw new AssertionError();
            }
         }

         public boolean canBeInstantiated() {
            return false;
         }

         protected PlaneEnum deserialize(Fields fields) throws StreamCorruptedException {
            String name = fields.getObject("name", String.class);
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
      Classes.registerClass((new ClassInfo(EmoteType.class, "emotetype")).user("emotetype?").defaultExpression(new EventValueExpression(EmoteType.class)).parser(new Parser() {
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

         public String toString(Object eff, int i) {
            return ((EmoteType)eff).getName().toLowerCase().replace("_", " ").toLowerCase();
         }

         public String toVariableNameString(Object eff) {
            return ((EmoteType)eff).getName().toLowerCase().replace("_", " ").toLowerCase();
         }
      }).serializer(new Serializer() {
         public Fields serialize(Object w) {
            Fields f = new Fields();
            f.putObject("name", ((EmoteType)w).getName());
            return f;
         }

         public void deserialize(Object o, Fields f) {
            if (!Types.$assertionsDisabled) {
               throw new AssertionError();
            }
         }

         public boolean canBeInstantiated() {
            return false;
         }

         protected EmoteType deserialize(Fields fields) throws StreamCorruptedException {
            String name = fields.getObject("name", String.class);
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
