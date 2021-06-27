package ud.skript.sashie.skDragon.particleEngine.utils.enums;

public enum PlaneEnum {
   X("x"),
   Y("y"),
   Z("z"),
   XY("xy"),
   XZ("xz"),
   XYZ("xyz"),
   YZ("yz");

   private final String name;

   PlaneEnum(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }
}
