package ud.skript.sashie.skDragon.registration;

public class Registration {
   private String name;
   private String[] desc;
   private String[] syntax;
   private String[] example;

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
