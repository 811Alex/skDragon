package ud.skript.sashie.skDragon.emotes;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;
import ud.skript.sashie.skDragon.particleEngine.utils.ParticleEffect;
import ud.skript.sashie.skDragon.particleEngine.utils.VectorUtils;

public enum EmoteType {
   BLUSH(1, "blush") {
      public CustomEmote initEmote() {
         CustomEmote emote = new CustomEmote(this);
         emote.addFrame(18, SkullEffectsLib.BLUSH1);
         emote.addFrame(17, SkullEffectsLib.BLUSH2);
         emote.addFrame(5, SkullEffectsLib.BLUSH3);
         emote.addFrame(5, SkullEffectsLib.BLUSH4);
         emote.addFrame(5, SkullEffectsLib.BLUSH5);

         for(int i = 1; i <= 5; ++i) {
            emote.addFrame(5, SkullEffectsLib.BLUSH4);
            emote.addFrame(5, SkullEffectsLib.BLUSH3);
            emote.addFrame(5, SkullEffectsLib.BLUSH4);
            emote.addFrame(5, SkullEffectsLib.BLUSH5);
         }

         return emote;
      }

      public void playParticle(int time, int frame, Location loc) {
      }
   },
   CHEEKY(2, "cheeky") {
      public CustomEmote initEmote() {
         CustomEmote emote = new CustomEmote(this);
         emote.addFrame(5, SkullEffectsLib.CHEEKY1);
         emote.addFrame(5, SkullEffectsLib.CHEEKY2);
         emote.addFrame(5, SkullEffectsLib.CHEEKY3);
         emote.addFrame(5, SkullEffectsLib.CHEEKY4);
         emote.addFrame(5, SkullEffectsLib.CHEEKY5);
         emote.addFrame(5, SkullEffectsLib.CHEEKY8);

         for(int i = 1; i <= 3; ++i) {
            emote.addFrame(5, SkullEffectsLib.CHEEKY7);
            emote.addFrame(5, SkullEffectsLib.CHEEKY6);
            emote.addFrame(5, SkullEffectsLib.CHEEKY7);
            emote.addFrame(5, SkullEffectsLib.CHEEKY8);
         }

         emote.addFrame(5, SkullEffectsLib.CHEEKY5);
         emote.addFrame(5, SkullEffectsLib.CHEEKY4);
         emote.addFrame(5, SkullEffectsLib.CHEEKY3);
         return emote;
      }

      public void playParticle(int time, int frame, Location loc) {
      }
   },
   COOL(3, "cool") {
      public CustomEmote initEmote() {
         CustomEmote emote = new CustomEmote(this);
         emote.addFrame(10, SkullEffectsLib.COOL1);
         emote.addFrame(10, SkullEffectsLib.COOL2);
         emote.addFrame(80, SkullEffectsLib.COOL3);
         return emote;
      }

      public void playParticle(int time, int frame, Location loc) {
      }
   },
   CRY(4, "cry") {
      public CustomEmote initEmote() {
         CustomEmote emote = new CustomEmote(this);
         emote.addFrame(10, SkullEffectsLib.CRY1);
         emote.addFrame(10, SkullEffectsLib.CRY2);
         emote.addFrame(10, SkullEffectsLib.CRY3);
         emote.addFrame(5, SkullEffectsLib.CRY4);
         emote.addFrame(5, SkullEffectsLib.CRY5);
         emote.addFrame(5, SkullEffectsLib.CRY6);
         emote.addFrame(5, SkullEffectsLib.CRY7);

         for(int i = 1; i <= 150; ++i) {
            int r = (new Random()).nextInt(185) + 16;
            if (r <= 50) {
               emote.addFrame(1, SkullEffectsLib.CRY8);
            }

            if (r >= 51 && r <= 100) {
               emote.addFrame(1, SkullEffectsLib.CRY9);
            }

            if (r >= 101 && r <= 150) {
               emote.addFrame(1, SkullEffectsLib.CRY10);
            }

            if (r >= 151 && r <= 200) {
               emote.addFrame(1, SkullEffectsLib.CRY11);
            }
         }

         return emote;
      }

      public void playParticle(int time, int frame, Location loc) {
         int r = (new Random()).nextInt(185) + 16;
         loc.add(0.0D, -0.1D, 0.0D);
         Vector v = new Vector(0.2D, 0.0D, 0.0D);
         VectorUtils.rotateAroundAxisY(v, -(loc.getYaw() + 90.0F) * 0.017453292F);
         if (time >= 50 && time < 200 && r <= 100) {
            ParticleEffect.watersplash.display(0.1F, 0.1F, 0.1F, 0.0F, 1, loc.add(v), 32.0D);
         }

      }
   },
   DIZZY(5, "dizzy") {
      public CustomEmote initEmote() {
         CustomEmote emote = new CustomEmote(this);
         emote.addFrame(5, SkullEffectsLib.DIZZY1);
         emote.addFrame(5, SkullEffectsLib.DIZZY2);
         emote.addFrame(5, SkullEffectsLib.DIZZY3);
         emote.addFrame(5, SkullEffectsLib.DIZZY4);
         emote.addFrame(5, SkullEffectsLib.DIZZY5);

         for(int i = 1; i <= 4; ++i) {
            emote.addFrame(5, SkullEffectsLib.DIZZY2);
            emote.addFrame(5, SkullEffectsLib.DIZZY3);
            emote.addFrame(5, SkullEffectsLib.DIZZY4);
            emote.addFrame(5, SkullEffectsLib.DIZZY5);
         }

         return emote;
      }

      public void playParticle(int time, int frame, Location loc) {
      }
   },
   FROWN(6, "frown") {
      public CustomEmote initEmote() {
         CustomEmote emote = new CustomEmote(this);
         emote.addFrame(10, SkullEffectsLib.FROWN1);
         emote.addFrame(10, SkullEffectsLib.FROWN2);
         emote.addFrame(20, SkullEffectsLib.FROWN3);
         emote.addFrame(5, SkullEffectsLib.FROWN4);
         emote.addFrame(5, SkullEffectsLib.FROWN5);
         emote.addFrame(5, SkullEffectsLib.FROWN6);
         emote.addFrame(5, SkullEffectsLib.FROWN7);
         emote.addFrame(5, SkullEffectsLib.FROWN6);
         emote.addFrame(5, SkullEffectsLib.FROWN5);
         emote.addFrame(5, SkullEffectsLib.FROWN4);
         emote.addFrame(5, SkullEffectsLib.FROWN5);
         emote.addFrame(5, SkullEffectsLib.FROWN6);
         emote.addFrame(5, SkullEffectsLib.FROWN7);
         emote.addFrame(5, SkullEffectsLib.FROWN8);
         emote.addFrame(5, SkullEffectsLib.FROWN7);
         emote.addFrame(5, SkullEffectsLib.FROWN6);
         emote.addFrame(5, SkullEffectsLib.FROWN5);
         emote.addFrame(5, SkullEffectsLib.FROWN4);
         emote.addFrame(5, SkullEffectsLib.FROWN5);
         emote.addFrame(5, SkullEffectsLib.FROWN6);
         emote.addFrame(5, SkullEffectsLib.FROWN7);
         emote.addFrame(5, SkullEffectsLib.FROWN6);
         emote.addFrame(5, SkullEffectsLib.FROWN5);
         emote.addFrame(5, SkullEffectsLib.FROWN4);
         emote.addFrame(5, SkullEffectsLib.FROWN5);
         emote.addFrame(5, SkullEffectsLib.FROWN6);
         emote.addFrame(5, SkullEffectsLib.FROWN7);
         emote.addFrame(5, SkullEffectsLib.FROWN8);
         emote.addFrame(5, SkullEffectsLib.FROWN7);
         emote.addFrame(5, SkullEffectsLib.FROWN6);
         emote.addFrame(5, SkullEffectsLib.FROWN5);
         emote.addFrame(5, SkullEffectsLib.FROWN4);
         emote.addFrame(5, SkullEffectsLib.FROWN5);
         emote.addFrame(5, SkullEffectsLib.FROWN6);
         emote.addFrame(5, SkullEffectsLib.FROWN7);
         return emote;
      }

      public void playParticle(int time, int frame, Location loc) {
      }
   },
   GOOFY(7, "goofy") {
      public CustomEmote initEmote() {
         CustomEmote emote = new CustomEmote(this);

         for(int i = 1; i <= 3; ++i) {
            emote.addFrame(5, SkullEffectsLib.GOOFY1);
            emote.addFrame(5, SkullEffectsLib.GOOFY2);
            emote.addFrame(5, SkullEffectsLib.GOOFY3);
            emote.addFrame(5, SkullEffectsLib.GOOFY4);
            emote.addFrame(5, SkullEffectsLib.GOOFY3);
            emote.addFrame(5, SkullEffectsLib.GOOFY2);
         }

         emote.addFrame(5, SkullEffectsLib.GOOFY1);
         emote.addFrame(5, SkullEffectsLib.GOOFY2);
         return emote;
      }

      public void playParticle(int time, int frame, Location loc) {
      }
   },
   GRIN(8, "grin") {
      public CustomEmote initEmote() {
         CustomEmote emote = new CustomEmote(this);
         emote.addFrame(10, SkullEffectsLib.GRIN1);
         emote.addFrame(10, SkullEffectsLib.GRIN2);
         emote.addFrame(10, SkullEffectsLib.GRIN3);
         emote.addFrame(70, SkullEffectsLib.GRIN4);
         return emote;
      }

      public void playParticle(int time, int frame, Location loc) {
      }
   },
   LOVE_STRUCK(9, "love struck") {
      public CustomEmote initEmote() {
         CustomEmote emote = new CustomEmote(this);

         for(int i = 1; i <= 5; ++i) {
            emote.addFrame(10, SkullEffectsLib.LOVESTRUCK1);
            emote.addFrame(2, SkullEffectsLib.LOVESTRUCK2);
            emote.addFrame(2, SkullEffectsLib.LOVESTRUCK3);
            emote.addFrame(2, SkullEffectsLib.LOVESTRUCK4);
            emote.addFrame(2, SkullEffectsLib.LOVESTRUCK3);
            emote.addFrame(2, SkullEffectsLib.LOVESTRUCK2);
         }

         emote.addFrame(5, SkullEffectsLib.LOVESTRUCK1);
         return emote;
      }

      public void playParticle(int time, int frame, Location loc) {
      }
   },
   LOVEY(10, "lovey") {
      public CustomEmote initEmote() {
         CustomEmote emote = new CustomEmote(this);
         emote.addFrame(10, SkullEffectsLib.LOVE1);
         emote.addFrame(10, SkullEffectsLib.LOVE2);
         emote.addFrame(5, SkullEffectsLib.LOVE3);

         for(int i = 1; i <= 8; ++i) {
            emote.addFrame(5, SkullEffectsLib.LOVE4);
            emote.addFrame(5, SkullEffectsLib.LOVE5);
            emote.addFrame(5, SkullEffectsLib.LOVE6);
            emote.addFrame(5, SkullEffectsLib.LOVE7);
         }

         emote.addFrame(5, SkullEffectsLib.LOVE4);
         emote.addFrame(5, SkullEffectsLib.LOVE5);
         emote.addFrame(5, SkullEffectsLib.LOVE6);
         return emote;
      }

      public void playParticle(int time, int frame, Location loc) {
      }
   },
   RAGE(11, "rage") {
      public CustomEmote initEmote() {
         CustomEmote emote = new CustomEmote(this);
         emote.addFrame(5, SkullEffectsLib.RAGE1);
         emote.addFrame(5, SkullEffectsLib.RAGE2);
         emote.addFrame(5, SkullEffectsLib.RAGE3);
         emote.addFrame(5, SkullEffectsLib.RAGE4);

         for(int i = 1; i <= 180; ++i) {
            int r = (new Random()).nextInt(201);
            if (r <= 100) {
               emote.addFrame(1, SkullEffectsLib.RAGE5);
            }

            if (r >= 101 && r <= 150) {
               emote.addFrame(1, SkullEffectsLib.RAGE6);
            }

            if (r >= 151 && r <= 200) {
               emote.addFrame(1, SkullEffectsLib.RAGE7);
            }
         }

         return emote;
      }

      public void playParticle(int time, int frame, Location loc) {
         int r = (new Random()).nextInt(201);
         loc.add(0.0D, 0.3D, 0.0D);
         if (r <= 50) {
            ParticleEffect.angryvillager.display(0.2F, 0.2F, 0.2F, 0.0F, 1, loc, 32.0D);
         }

      }
   },
   RELAX(12, "relax") {
      public CustomEmote initEmote() {
         CustomEmote emote = new CustomEmote(this);
         emote.addFrame(8, SkullEffectsLib.RELAX1);
         emote.addFrame(7, SkullEffectsLib.RELAX2);
         emote.addFrame(25, SkullEffectsLib.RELAX3);
         emote.addFrame(10, SkullEffectsLib.RELAX4);
         emote.addFrame(5, SkullEffectsLib.RELAX5);

         for(int i = 1; i <= 3; ++i) {
            emote.addFrame(15, SkullEffectsLib.RELAX6);
            emote.addFrame(15, SkullEffectsLib.RELAX7);
         }

         emote.addFrame(10, SkullEffectsLib.RELAX1);
         emote.addFrame(10, SkullEffectsLib.RELAX2);
         emote.addFrame(10, SkullEffectsLib.RELAX3);
         emote.addFrame(10, SkullEffectsLib.RELAX4);
         emote.addFrame(20, SkullEffectsLib.RELAX5);
         return emote;
      }

      public void playParticle(int time, int frame, Location loc) {
      }
   },
   RIP(13, "rip") {
      public CustomEmote initEmote() {
         CustomEmote emote = new CustomEmote(this);
         emote.addFrame(5, SkullEffectsLib.RIP1);
         emote.addFrame(5, SkullEffectsLib.RIP2);
         emote.addFrame(5, SkullEffectsLib.RIP3);
         emote.addFrame(5, SkullEffectsLib.RIP4);
         emote.addFrame(5, SkullEffectsLib.RIP5);
         emote.addFrame(5, SkullEffectsLib.RIP6);
         emote.addFrame(70, SkullEffectsLib.RIP7);
         return emote;
      }

      public void playParticle(int time, int frame, Location loc) {
      }
   },
   SLEEPY(14, "sleepy") {
      public CustomEmote initEmote() {
         CustomEmote emote = new CustomEmote(this);
         emote.addFrame(8, SkullEffectsLib.SLEEPY1);
         emote.addFrame(8, SkullEffectsLib.SLEEPY2);
         emote.addFrame(4, SkullEffectsLib.SLEEPY3);
         emote.addFrame(10, SkullEffectsLib.SLEEPY4);
         emote.addFrame(5, SkullEffectsLib.SLEEPY5);
         emote.addFrame(10, SkullEffectsLib.SLEEPY6);
         emote.addFrame(10, SkullEffectsLib.SLEEPY7);
         emote.addFrame(25, SkullEffectsLib.SLEEPY8);
         emote.addFrame(20, SkullEffectsLib.SLEEPY9);

         for(int i = 1; i <= 4; ++i) {
            emote.addFrame(30, SkullEffectsLib.SLEEPY8);
            emote.addFrame(20, SkullEffectsLib.SLEEPY9);
         }

         emote.addFrame(5, SkullEffectsLib.SLEEPY8);
         return emote;
      }

      public void playParticle(int time, int frame, Location loc) {
      }
   },
   SMILE(15, "smile") {
      public CustomEmote initEmote() {
         CustomEmote emote = new CustomEmote(this);
         emote.addFrame(20, SkullEffectsLib.SMILE1);
         emote.addFrame(20, SkullEffectsLib.SMILE2);
         emote.addFrame(20, SkullEffectsLib.SMILE3);
         emote.addFrame(40, SkullEffectsLib.SMILE);
         return emote;
      }

      public void playParticle(int time, int frame, Location loc) {
      }
   },
   SPICY(16, "spicy") {
      public CustomEmote initEmote() {
         CustomEmote emote = new CustomEmote(this);
         emote.addFrame(5, SkullEffectsLib.SPICY1);
         emote.addFrame(5, SkullEffectsLib.SPICY2);
         emote.addFrame(5, SkullEffectsLib.SPICY3);
         emote.addFrame(5, SkullEffectsLib.SPICY4);
         emote.addFrame(5, SkullEffectsLib.SPICY5);
         emote.addFrame(5, SkullEffectsLib.SPICY6);
         emote.addFrame(5, SkullEffectsLib.SPICY7);

         for(int i = 1; i <= 35; ++i) {
            emote.addFrame(2, SkullEffectsLib.SPICY8);
            emote.addFrame(2, SkullEffectsLib.SPICY9);
         }

         return emote;
      }

      public void playParticle(int time, int frame, Location loc) {
      }
   },
   SURPRISED(17, "surprised") {
      public CustomEmote initEmote() {
         CustomEmote emote = new CustomEmote(this);
         emote.addFrame(10, SkullEffectsLib.SURPRISED1);
         emote.addFrame(10, SkullEffectsLib.SURPRISED2);
         emote.addFrame(10, SkullEffectsLib.SURPRISED3);
         emote.addFrame(70, SkullEffectsLib.SURPRISED4);
         return emote;
      }

      public void playParticle(int time, int frame, Location loc) {
      }
   },
   TAN(18, "tan") {
      public CustomEmote initEmote() {
         CustomEmote emote = new CustomEmote(this);
         emote.addFrame(5, SkullEffectsLib.TAN1);
         emote.addFrame(5, SkullEffectsLib.TAN2);
         emote.addFrame(10, SkullEffectsLib.TAN3);
         emote.addFrame(15, SkullEffectsLib.TAN4);
         emote.addFrame(5, SkullEffectsLib.TAN3);
         emote.addFrame(10, SkullEffectsLib.TAN2);
         emote.addFrame(15, SkullEffectsLib.TAN5);
         emote.addFrame(15, SkullEffectsLib.TAN6);
         emote.addFrame(10, SkullEffectsLib.TAN7);
         emote.addFrame(10, SkullEffectsLib.TAN8);
         emote.addFrame(15, SkullEffectsLib.TAN9);

         for(int i = 1; i <= 3; ++i) {
            emote.addFrame(5, SkullEffectsLib.TAN10);
            emote.addFrame(10, SkullEffectsLib.TAN11);
         }

         return emote;
      }

      public void playParticle(int time, int frame, Location loc) {
      }
   },
   TOO_COOL(19, "too cool") {
      public CustomEmote initEmote() {
         CustomEmote emote = new CustomEmote(this);
         emote.addFrame(5, SkullEffectsLib.TOOCOOL1);
         emote.addFrame(5, SkullEffectsLib.TOOCOOL2);
         emote.addFrame(5, SkullEffectsLib.TOOCOOL3);
         emote.addFrame(5, SkullEffectsLib.TOOCOOL4);
         emote.addFrame(5, SkullEffectsLib.TOOCOOL5);
         emote.addFrame(5, SkullEffectsLib.TOOCOOL6);
         emote.addFrame(10, SkullEffectsLib.TOOCOOL7);
         emote.addFrame(5, SkullEffectsLib.TOOCOOL8);
         emote.addFrame(5, SkullEffectsLib.TOOCOOL9);
         emote.addFrame(50, SkullEffectsLib.TOOCOOL10);
         return emote;
      }

      public void playParticle(int time, int frame, Location loc) {
      }
   },
   WINK(20, "wink") {
      public CustomEmote initEmote() {
         CustomEmote emote = new CustomEmote(this);
         emote.addFrame(10, SkullEffectsLib.WINK1);
         emote.addFrame(10, SkullEffectsLib.WINK2);
         emote.addFrame(20, SkullEffectsLib.WINK3);
         emote.addFrame(5, SkullEffectsLib.WINK4);
         emote.addFrame(5, SkullEffectsLib.WINK3);
         emote.addFrame(5, SkullEffectsLib.WINK4);
         emote.addFrame(10, SkullEffectsLib.WINK3);
         emote.addFrame(5, SkullEffectsLib.WINK4);
         emote.addFrame(5, SkullEffectsLib.WINK3);
         return emote;
      }

      public void playParticle(int time, int frame, Location loc) {
      }
   },
   ORC_BLINK(21, "orc blink") {
      public CustomEmote initEmote() {
         CustomEmote emote = new CustomEmote(this);
         emote.addFrame(10, SkullEffectsLib.ORC_BLINK1);
         emote.addFrame(10, SkullEffectsLib.ORC_BLINK2);
         emote.addFrame(20, SkullEffectsLib.ORC_BLINK1);
         emote.addFrame(5, SkullEffectsLib.ORC_BLINK2);
         emote.addFrame(10, SkullEffectsLib.ORC_BLINK1);
         emote.addFrame(5, SkullEffectsLib.ORC_BLINK2);
         emote.addFrame(20, SkullEffectsLib.ORC_BLINK1);
         emote.addFrame(5, SkullEffectsLib.ORC_BLINK2);
         emote.addFrame(5, SkullEffectsLib.ORC_BLINK1);
         return emote;
      }

      public void playParticle(int time, int frame, Location loc) {
      }
   },
   SANTA_BLINK(22, "santa blink") {
      public CustomEmote initEmote() {
         CustomEmote emote = new CustomEmote(this);
         emote.addFrame(10, SkullEffectsLib.SANTA_BLINK1);
         emote.addFrame(10, SkullEffectsLib.SANTA_BLINK2);
         emote.addFrame(20, SkullEffectsLib.SANTA_BLINK1);
         emote.addFrame(5, SkullEffectsLib.SANTA_BLINK2);
         emote.addFrame(10, SkullEffectsLib.SANTA_BLINK1);
         emote.addFrame(5, SkullEffectsLib.SANTA_BLINK2);
         emote.addFrame(20, SkullEffectsLib.SANTA_BLINK1);
         emote.addFrame(5, SkullEffectsLib.SANTA_BLINK2);
         emote.addFrame(5, SkullEffectsLib.SANTA_BLINK1);
         return emote;
      }

      public void playParticle(int time, int frame, Location loc) {
      }
   },
   SANTA_LOOK(23, "santa look") {
      public CustomEmote initEmote() {
         CustomEmote emote = new CustomEmote(this);

         for(int i = 1; i <= 5; ++i) {
            emote.addFrame(10, SkullEffectsLib.SANTA_LOOK1);
            emote.addFrame(10, SkullEffectsLib.SANTA_LOOK2);
            emote.addFrame(10, SkullEffectsLib.SANTA_LOOK1);
            emote.addFrame(10, SkullEffectsLib.SANTA_LOOK3);
         }

         return emote;
      }

      public void playParticle(int time, int frame, Location loc) {
      }
   },
   SANTA_WINK(24, "santa wink") {
      public CustomEmote initEmote() {
         CustomEmote emote = new CustomEmote(this);

         for(int i = 1; i <= 5; ++i) {
            emote.addFrame(20, SkullEffectsLib.SANTA_WINK1);
            emote.addFrame(5, SkullEffectsLib.SANTA_WINK2);
         }

         emote.addFrame(5, SkullEffectsLib.SANTA_WINK1);
         return emote;
      }

      public void playParticle(int time, int frame, Location loc) {
      }
   },
   XMAS_TREE(25, "xmas tree") {
      public CustomEmote initEmote() {
         CustomEmote emote = new CustomEmote(this);

         for(int i = 1; i <= 5; ++i) {
            emote.addFrame(10, SkullEffectsLib.XMAS_TREE1);
            emote.addFrame(10, SkullEffectsLib.XMAS_TREE2);
            emote.addFrame(10, SkullEffectsLib.XMAS_TREE3);
            emote.addFrame(10, SkullEffectsLib.XMAS_TREE4);
         }

         return emote;
      }

      public void playParticle(int time, int frame, Location loc) {
      }
   },
   PUMPKIN_EXPLODE(25, "pumpkin explode") {
      public CustomEmote initEmote() {
         CustomEmote emote = new CustomEmote(this);
         emote.addFrame(15, SkullEffectsLib.PUMPKIN_EXPLODE1);
         emote.addFrame(13, SkullEffectsLib.PUMPKIN_EXPLODE2);
         emote.addFrame(13, SkullEffectsLib.PUMPKIN_EXPLODE3);
         emote.addFrame(12, SkullEffectsLib.PUMPKIN_EXPLODE4);
         emote.addFrame(20, SkullEffectsLib.PUMPKIN_EXPLODE5);
         emote.addFrame(10, SkullEffectsLib.PUMPKIN_EXPLODE6);

         int i;
         for(i = 1; i <= 3; ++i) {
            emote.addFrame(5, SkullEffectsLib.PUMPKIN_EXPLODE7);
            emote.addFrame(5, SkullEffectsLib.PUMPKIN_EXPLODE8);
         }

         emote.addFrame(5, SkullEffectsLib.PUMPKIN_EXPLODE9);
         emote.addFrame(5, SkullEffectsLib.PUMPKIN_EXPLODE10);

         for(i = 1; i <= 20; ++i) {
            emote.addFrame(2, SkullEffectsLib.PUMPKIN_EXPLODE11);
            emote.addFrame(2, SkullEffectsLib.PUMPKIN_EXPLODE12);
         }

         emote.addFrame(20, SkullEffectsLib.PUMPKIN_EXPLODE13);
         return emote;
      }

      public void playParticle(int time, int frame, Location loc) {
         int r = (new Random()).nextInt(185) + 16;
         loc.add(0.0D, 0.7D, 0.0D);
         if (frame >= 13 && frame <= 54 && r <= 100) {
            ParticleEffect.smokelarge.display(0.1F, 0.1F, 0.1F, 0.0F, 1, loc, 32.0D);
         }

         if (frame >= 54 && r <= 70) {
            ParticleEffect.explosion.display(0.3F, 0.2F, 0.3F, 0.0F, 1, loc, 32.0D);
         }

      }
   },
   PUMPKIN_PUKE(25, "pumpkin puke") {
      public CustomEmote initEmote() {
         CustomEmote emote = new CustomEmote(this);
         emote.addFrame(15, SkullEffectsLib.PUMPKIN_PUKE1);
         emote.addFrame(13, SkullEffectsLib.PUMPKIN_PUKE2);
         emote.addFrame(13, SkullEffectsLib.PUMPKIN_PUKE3);

         int i;
         for(i = 1; i <= 3; ++i) {
            emote.addFrame(5, SkullEffectsLib.PUMPKIN_PUKE4);
            emote.addFrame(5, SkullEffectsLib.PUMPKIN_PUKE5);
         }

         emote.addFrame(10, SkullEffectsLib.PUMPKIN_PUKE4);
         emote.addFrame(10, SkullEffectsLib.PUMPKIN_PUKE6);
         emote.addFrame(12, SkullEffectsLib.PUMPKIN_PUKE7);
         emote.addFrame(20, SkullEffectsLib.PUMPKIN_PUKE8);
         emote.addFrame(5, SkullEffectsLib.PUMPKIN_PUKE9);
         emote.addFrame(5, SkullEffectsLib.PUMPKIN_PUKE10);

         for(i = 1; i <= 20; ++i) {
            emote.addFrame(2, SkullEffectsLib.PUMPKIN_PUKE10);
            emote.addFrame(2, SkullEffectsLib.PUMPKIN_PUKE11);
         }

         return emote;
      }

      public void playParticle(int time, int frame, Location loc) {
         int r = (new Random()).nextInt(185) + 16;
         loc.add(0.0D, -0.2D, 0.0D);
         Vector v = new Vector(0.3D, 0.0D, 0.0D);
         VectorUtils.rotateAroundAxisY(v, -(loc.getYaw() + 90.0F) * 0.017453292F);
         if (frame >= 14 && r <= 100) {
            ParticleEffect.fallingdust.display(new ParticleEffect.BlockData(Material.SANDSTONE, (byte)0), 0.02F, 0.0F, 0.02F, 0.005F, 1, loc.add(v), 32.0D);
         }

         if (frame >= 14 && r >= 100) {
            ParticleEffect.BlockData data = null;

            try {
               data = new ParticleEffect.BlockData(Material.valueOf("WOOL"), (byte)1);
            } catch (IllegalArgumentException var8) {
               data = new ParticleEffect.BlockData(Material.valueOf("ORANGE_WOOL"));
            }

            ParticleEffect.fallingdust.display(data, 0.02F, 0.0F, 0.02F, 0.005F, 1, loc.add(v), 32.0D);
         }

      }
   };

   public static final Map ID_MAP = new HashMap();
   public static final Map NAME_MAP = new HashMap();
   private final Integer id;
   private final String name;

   static {
      EmoteType[] var3;
      int var2 = (var3 = values()).length;

      for(int var1 = 0; var1 < var2; ++var1) {
         EmoteType effect = var3[var1];
         ID_MAP.put(effect.id, effect);
         NAME_MAP.put(effect.name, effect);
      }

   }

   public abstract CustomEmote initEmote();

   public abstract void playParticle(int var1, int var2, Location var3);

   private EmoteType(Integer id, String name) {
      this.id = id;
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   public Integer getId() {
      return this.id;
   }

   public static EmoteType fromId(Integer id) {
      Iterator var2 = ID_MAP.entrySet().iterator();

      while(var2.hasNext()) {
         Entry entry = (Entry)var2.next();
         if (entry.getKey() == id) {
            return (EmoteType)entry.getValue();
         }
      }

      return null;
   }

   public static EmoteType fromName(String name) {
      Iterator var2 = NAME_MAP.entrySet().iterator();

      while(var2.hasNext()) {
         Entry entry = (Entry)var2.next();
         if (((String)entry.getKey()).equalsIgnoreCase(name)) {
            return (EmoteType)entry.getValue();
         }
      }

      return null;
   }

   // $FF: synthetic method
   EmoteType(Integer var3, String var4, EmoteType var5) {
      this(var3, var4);
   }
}
