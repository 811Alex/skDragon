package ud.skript.sashie.skDragon.registration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import ud.skript.sashie.skDragonCore;

public class Documentation {
   public static List expressions = new ArrayList();
   public static List conditions = new ArrayList();
   public static List effects = new ArrayList();
   public static List events = new ArrayList();
   File file;
   File htmlFile;
   BufferedWriter htmlWriter;

   public void setUpSyntaxes() {
      this.file = this.initFile(this.file, "Syntaxes.txt");
      this.addSyntaxes(effects, "-=Effects=-");
   }

   private void addSyntaxes(List regs, String title) {
      try {
         FileWriter fw = new FileWriter(this.file, true);
         BufferedWriter bw = new BufferedWriter(fw);
         this.writeLine(bw, "");
         this.writeLine(bw, title);
         Iterator var6 = regs.iterator();

         while(var6.hasNext()) {
            Registration reg = (Registration)var6.next();
            this.writeLine(bw, reg.getName());
            String[] var10;
            int var9 = (var10 = reg.getDesc()).length;

            String e;
            int var8;
            for(var8 = 0; var8 < var9; ++var8) {
               e = var10[var8];
               this.writeLine(bw, "    " + e);
            }

            this.writeLine(bw, "");
            var9 = (var10 = reg.getSyntax()).length;

            for(var8 = 0; var8 < var9; ++var8) {
               e = var10[var8];
               this.writeLine(bw, "    " + e.replace("object", "entity/location").replace("objects", "entities/locations").replaceAll("%-", "%"));
            }

            this.writeLine(bw, "");
            var9 = (var10 = reg.getExample()).length;

            for(var8 = 0; var8 < var9; ++var8) {
               e = var10[var8];
               this.writeLine(bw, "    " + e);
            }

            this.writeLine(bw, "");
         }

         bw.close();
         fw.close();
      } catch (IOException var11) {
         var11.printStackTrace();
      }

   }

   private void writeLine(BufferedWriter bw, String line) {
      try {
         bw.write(line);
         bw.newLine();
      } catch (IOException var4) {
         var4.printStackTrace();
      }

   }

   public void writeHTML() {
      this.htmlFile = this.initFile(this.htmlFile, "Documentation.htm");

      try {
         this.htmlWriter = new BufferedWriter(new FileWriter(this.htmlFile));
         this.htmlWriter.write("<html>");
         this.htmlWriter.write("<head><link href=\"https://fonts.googleapis.com/css?family=Roboto|Source+Sans+Pro:900\" rel=\"stylesheet\"><style>body { font-family: \"Source Sans Pro\", sans-serif; color: #000; background-color: #fff; --red: #d95356; --gray: #5f5f5f; position: relative; background-size: 120% 120%; background-repeat: no-repeat; }body:after{ background: url(https://i.imgur.com/CyegLBi.jpg) repeat center center; background-size: 150% 150%; content: \"\"; background-size: cover; top: -5; left: -5; bottom: -5; right: -5; position: fixed; z-index: -1; filter: blur(3px) saturate(20%); }h1:before{ color: var(--gray); content: \"{\"; font-family: 'Source Sans Pro', sans-serif; font-size: 32px; }h1.logo{ color: var(--red); font-family: 'Source Sans Pro', sans-serif; font-size: 32px; display: inline; }h1:after{ color: var(--gray); content: \"}\"; font-family: 'Source Sans Pro', sans-serif; font-size: 32px; }div.title{ width: auto; max-width: 100%; height: auto; background-color: rgb(44, 44, 44); border-radius: 4px; text-align: center; }div.sectionstart:before{ color: var(--gray); content: \"{\"; font-family: 'Source Sans Pro', sans-serif; }div.sectionstart:after{ color: var(--gray); content: \"}\"; font-family: 'Source Sans Pro', sans-serif; }div.sectionstart{ background-color: rgb(44, 44, 44); border-radius: 4px; height: auto; max-width: 100%; max-height: 100%; padding-left: 30px; margin-top: 15px; display: block; font-size: 24px; color: var(--red); font-family: \"Source Sans Pro\", sans-serif; }div.element{ background-color: rgba(95, 95, 95, 0.8); border-radius: 4px; width: 50%; height: auto; max-width: 100%; max-height: 100%; padding-left: 15px; margin-top: 15px; display: inline-block; font-family: \"Roboto\", sans-serif; padding-bottom: 15px; padding-right: 15px; }div.sectionstart > span.sectioncount{ color: white; }div.element > span.elementname{ font-size: 28px; font-weight: bold; margin-top: 15px; display: block; }div.element > span.description{ font-size: 18px; display: block; padding-left: 15px; margin-top: 15px; }div.element > pre.syntax{ border-bottom: 2px solid var(--red); width: 100%; display: block; -webkit-transition: all .4s ease-in-out; -moz-transition: all .4s ease-in-out; -ms-transition: all .4s ease-in-out; -o-transition: all .4s ease-in-out; transition: all .4s ease-in-out; font-size: 14px; margin-top: 15px; white-space: pre-wrap;       /* css-3 */ white-space: -moz-pre-wrap;  /* Mozilla, since 1999 */ white-space: -pre-wrap;      /* Opera 4-6 */ white-space: -o-pre-wrap;    /* Opera 7 */ word-wrap: break-word; }div.element > pre.syntax:hover{ border-bottom: 2px solid var(--gray); width: auto; }div.element > pre.example{ border-bottom: 2px solid #fff; width: 100%; display: block; -webkit-transition: all .4s ease-in-out; -moz-transition: all .4s ease-in-out; -ms-transition: all .4s ease-in-out; -o-transition: all .4s ease-in-out; transition: all .4s ease-in-out; font-size: 12px; margin-top: 15px; white-space: pre-wrap;       /* css-3 */ white-space: -moz-pre-wrap;  /* Mozilla, since 1999 */ white-space: -pre-wrap;      /* Opera 4-6 */ white-space: -o-pre-wrap;    /* Opera 7 */ word-wrap: break-word; }div.element > pre.example:hover{ border-bottom: 2px solid var(--gray); width: auto; }span.elementbreak{ width: 50%; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: var(--gray); border-right-color: var(--gray); border-bottom-color: var(--gray); border-left-color: var(--gray); background-color: var(--gray); border-radius: 4px; margin-top: 30px; margin-bottom: 30px; height: 2px; display: block; -webkit-margin-start: auto; -webkit-margin-end: auto; display: block; }</style></head>");
         this.htmlWriter.write("<body>");
         this.htmlWriter.write("<div class=\"title\"><h1 class=\"logo\">skDragon Documentation</h1></div>");
         this.insertEach(effects, "Effects");
         this.insertEach(expressions, "Expressions");
         this.htmlWriter.write("</body>");
         this.htmlWriter.write("</html>");
         this.htmlWriter.close();
      } catch (IOException var2) {
         skDragonCore.error("Something had a brainfart while generating the docs");
         var2.printStackTrace();
      }

   }

   private void insertEach(List regs, String title) throws IOException {
      this.htmlWriter.write("<div class=\"sectionstart\"> " + title + " <span class=\"sectioncount\">(" + regs.size() + ")</span> </div>");
      Collections.sort(regs, new Comparator() {
         public int compare(Object one, Object other) {
            return ((Registration)one).getName().compareTo(((Registration)other).getName());
         }
      });
      Iterator var4 = regs.iterator();

      while(var4.hasNext()) {
         Registration reg = (Registration)var4.next();
         this.htmlWriter.write("<div class=\"element\"><span class=\"elementname\">" + reg.getName() + "</span>");
         String[] var8;
         int var7 = (var8 = reg.getDesc()).length;

         String e;
         int var6;
         for(var6 = 0; var6 < var7; ++var6) {
            e = var8[var6];
            this.htmlWriter.write("<span class=\"description\">" + e + "</span>");
         }

         var7 = (var8 = reg.getSyntax()).length;

         for(var6 = 0; var6 < var7; ++var6) {
            e = var8[var6];
            this.htmlWriter.write("<pre class=\"syntax\">" + e.replace("object", "entity/location").replace("objects", "entities/locations").replaceAll("%-", "%") + "</pre>");
         }

         var7 = (var8 = reg.getExample()).length;

         for(var6 = 0; var6 < var7; ++var6) {
            e = var8[var6];
            this.htmlWriter.write("<pre class=\"example\">" + e + "</pre>");
         }

         this.htmlWriter.write("</div>");
      }

   }

   private void insertExample(HashMap map, String key, String title) throws IOException {
      this.htmlWriter.write(title);
      this.lineBreak();
      this.htmlWriter.write("<p class=\"indent\"><textarea cols=100 rows=10>");
      String[] var7;
      int var6 = (var7 = (String[])map.get(key)).length;

      for(int var5 = 0; var5 < var6; ++var5) {
         String s = var7[var5];
         this.htmlWriter.write(s);
         this.htmlWriter.newLine();
         this.htmlWriter.newLine();
      }

      this.htmlWriter.write("</textarea></p>");
   }

   private void insertSyntax(HashMap map, String key, String title) throws IOException {
      this.htmlWriter.write(title);
      this.lineBreak();
      this.htmlWriter.write("<p class=\"indent\"><textarea cols=100 rows=10>");
      String[] var7;
      int var6 = (var7 = (String[])map.get(key)).length;

      for(int var5 = 0; var5 < var6; ++var5) {
         String s = var7[var5];
         this.htmlWriter.write(s.replace("object", "entity/location").replace("objects", "entities/locations").replaceAll("%-", "%"));
         this.htmlWriter.newLine();
         this.htmlWriter.newLine();
      }

      this.htmlWriter.write("</textarea></p>");
   }

   private void indent(String text) throws IOException {
      this.htmlWriter.write("<p class=\"indent\">" + text + "</p>");
   }

   private void hRule() throws IOException {
      this.htmlWriter.write("<hr width=\"50%\" color=\"#6699FF\" size=\"6\">");
   }

   private void lineBreak() throws IOException {
      this.htmlWriter.write("<br>");
   }

   private void doubleLineBreak() throws IOException {
      this.htmlWriter.write("<br><br>");
   }

   private File initFile(File file, String name) {
      file = new File(skDragonCore.getFolder(), name);
      if (!file.exists()) {
         try {
            file.createNewFile();
         } catch (IOException var4) {
            var4.printStackTrace();
         }
      } else {
         file.delete();
      }

      return file;
   }
}
