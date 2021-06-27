package ud.skript.sashie.skDragon.registration;

import java.util.Comparator;

public class Registration {
   private String name;
   private String[] desc;
   private String[] syntax;
   private String[] example;
   public static Comparator COMPARE_BY_NAME = new Comparator() {
      public int compare(Object one, Object other) {
         return ((Registration)one).name.compareTo(((Registration)other).name);
      }
   };

   public Registration(String name, String[] desc, String[] syntax, String[] example) {
      this.name = name;
      this.desc = desc;
      this.syntax = syntax;
      this.example = example;
   }

   public String getName() {
      return this.name;
   }

   public String[] getDesc() {
      return this.desc;
   }

   public String[] getSyntax() {
      return this.syntax;
   }

   public String[] getExample() {
      return this.example;
   }
}
