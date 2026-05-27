package ud.skript.sashie.skDragon.registration;

import ch.njol.skript.Skript;
import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.util.SimpleExpression;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import ud.skript.sashie.skDragonCore;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.DontRegister;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.ExpressionsType;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.PropertyExpressionType;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

public class AnnotationParser {
   public void register() throws UnsupportedEncodingException {
      File file = null;

      file = new File(URLDecoder.decode(skDragonCore.class.getProtectionDomain().getCodeSource().getLocation().getFile(), String.valueOf(StandardCharsets.UTF_8)));

      Iterator var3 = this.getClasses(file, "ud.skript.sashie").iterator();

      while(var3.hasNext()) {
         Class clazz = (Class)var3.next();
         if (Effect.class.isAssignableFrom(clazz)) {
            this.effects(clazz);
         } else if (SimplePropertyExpression.class.isAssignableFrom(clazz)) {
            this.simplePropertyExpressions(clazz);
         } else if (PropertyExpression.class.isAssignableFrom(clazz)) {
            this.propertyExpressions(clazz);
         } else if (SimpleExpression.class.isAssignableFrom(clazz)) {
            this.simpleExpressions(clazz);
         } else if (Expression.class.isAssignableFrom(clazz)) {
            this.expressions(clazz);
         } else if (SkriptEvent.class.isAssignableFrom(clazz)) {
            this.events(clazz);
         } else if (PropertyCondition.class.isAssignableFrom(clazz)) {
            this.propertyConditions(clazz);
         } else if (Condition.class.isAssignableFrom(clazz)) {
            this.conditions(clazz);
         }
      }

   }

   private void effects(Class clazz) {
      if (clazz.getAnnotation(DontRegister.class) == null) {
         if (clazz.getAnnotation(Syntaxes.class) != null) {
            String[] syntax = this.getSyntax((Syntaxes)clazz.getAnnotation(Syntaxes.class));
            Skript.registerEffect(clazz, syntax);
            this.registerDocs(clazz, Documentation.effects, syntax);
         }
      }
   }

   private void expressions(Class clazz) {
      if (clazz.getAnnotation(DontRegister.class) == null) {
         if (clazz.getAnnotation(Syntaxes.class) != null) {
            String[] syntax = this.getSyntax((Syntaxes)clazz.getAnnotation(Syntaxes.class));
            ExpressionsType type = (ExpressionsType)clazz.getAnnotation(ExpressionsType.class);
            Class returnType = this.getExpressionReturnType(clazz);
            if (type == null) {
               Skript.registerExpression(clazz, returnType, ExpressionType.SIMPLE, syntax);
            } else {
               Skript.registerExpression(clazz, returnType, type.value(), syntax);
            }

            this.registerDocs(clazz, Documentation.expressions, syntax);
         }
      }
   }

   private void simpleExpressions(Class clazz) {
      if (clazz.getAnnotation(DontRegister.class) == null) {
         if (clazz.getAnnotation(Syntaxes.class) != null) {
            String[] syntax = this.getSyntax((Syntaxes)clazz.getAnnotation(Syntaxes.class));
            Class returnType = this.getExpressionReturnType(clazz);
            Skript.registerExpression(clazz, returnType, ExpressionType.SIMPLE, syntax);
            this.registerDocs(clazz, Documentation.expressions, syntax);
         }
      }
   }

   private void simplePropertyExpressions(Class clazz) {
      if (clazz.getAnnotation(DontRegister.class) == null) {
         if (clazz.getAnnotation(Syntaxes.class) != null) {
            String[] syntax = this.getSyntax((Syntaxes)clazz.getAnnotation(Syntaxes.class));
            PropertyExpressionType propertyType = (PropertyExpressionType)clazz.getAnnotation(PropertyExpressionType.class);
            if (propertyType == null) {
               skDragonCore.error("lel this 'SimplePropertyExpression' needs 'PropertyExpressionType' annotation and Sashie forgot to add it :p");
            } else {
               Class returnType = this.getExpressionReturnType(clazz);

               for(int i = 0; i < syntax.length; ++i) {
                  SimplePropertyExpression.register(clazz, returnType, syntax[i], propertyType.value()[0]);
               }

               this.registerDocs(clazz, Documentation.expressions, syntax);
            }
         }
      }
   }

   private void propertyExpressions(Class clazz) {
      if (clazz.getAnnotation(DontRegister.class) == null) {
         if (clazz.getAnnotation(Syntaxes.class) != null) {
            String[] syntax = this.getSyntax((Syntaxes)clazz.getAnnotation(Syntaxes.class));
            PropertyExpressionType propertyType = (PropertyExpressionType)clazz.getAnnotation(PropertyExpressionType.class);
            if (propertyType == null) {
               skDragonCore.error("lel this 'PropertyExpression' needs 'PropertyExpressionType' annotation and Sashie forgot to add it :p");
            } else {
               Class returnType = this.getExpressionReturnType(clazz);
               PropertyExpression.register(clazz, returnType, syntax[0], propertyType.value()[0]);
               this.registerDocs(clazz, Documentation.expressions, syntax);
            }
         }
      }
   }

   private void events(Class clazz) {

   }

   private void propertyConditions(Class clazz) {
      if (clazz.getAnnotation(DontRegister.class) == null) {
         if (clazz.getAnnotation(Syntaxes.class) != null) {
            String[] properties = ((Syntaxes)clazz.getAnnotation(Syntaxes.class)).value();
            String[] syntax = new String[]{"%" + properties[0] + "% " + properties[1] + " (is|are) " + properties[2], "%" + properties[0] + "% " + properties[1] + " (isn't|is not|aren't|are not) " + properties[2]};
            Skript.registerCondition(clazz, syntax[0], syntax[1]);
            this.registerDocs(clazz, Documentation.conditions, syntax);
         }
      }
   }

   private void conditions(Class clazz) {
      if (clazz.getAnnotation(DontRegister.class) == null) {
         if (clazz.getAnnotation(Syntaxes.class) != null) {
            String[] syntax = ((Syntaxes)clazz.getAnnotation(Syntaxes.class)).value();
            Skript.registerCondition(clazz, syntax[0], syntax[1]);
            this.registerDocs(clazz, Documentation.conditions, syntax);
         }
      }
   }

   private String[] getSyntax(Syntaxes syntaxes) {
      return this.appendPrefix(syntaxes.value());
   }

   private void registerDocs(Class clazz, List list, String[] syntax) {
      if (clazz.getAnnotation(Name.class) != null && !((Name)clazz.getAnnotation(Name.class)).value().isEmpty()) {
         list.add(new Registration(((Name)clazz.getAnnotation(Name.class)).value(), ((Description)clazz.getAnnotation(Description.class)).value(), syntax, ((Examples)clazz.getAnnotation(Examples.class)).value()));
      } else {
         skDragonCore.error(clazz.getCanonicalName() + " is not documented, get your lazy ass going and fix it Sashie!");
      }

   }

   private String[] appendPrefix(String[] syntax) {
      String[] syntaxes = null;
      int i = 0;
      if (syntax == null) {
         return null;
      } else {
         syntaxes = new String[syntax.length];

         for(int var5 = 0; var5 < syntax.length; ++var5) {
            String s = syntax[var5];
            syntaxes[i] = "[skDragon] " + s;
            ++i;
         }

         return syntaxes;
      }
   }

   public Set getClasses(File jarFile, String packageName) {
      HashSet classes = new HashSet();

      try {
         JarFile file = new JarFile(jarFile);
         Enumeration entry = file.entries();

         while(true) {
            String name;
            do {
               do {
                  do {
                     if (!entry.hasMoreElements()) {
                        file.close();
                        return classes;
                     }

                     JarEntry jarEntry = (JarEntry)entry.nextElement();
                     name = jarEntry.getName().replace("/", ".");
                  } while(!name.startsWith(packageName));
               } while(!name.endsWith(".class"));
            } while(name.contains("dragontravel") && !skDragonCore.isDragonTravelEnabled());

            classes.add(Class.forName(name.substring(0, name.length() - 6)));
         }
      } catch (Exception var8) {
         ClassNotFoundException.class.isAssignableFrom(var8.getClass());
         var8.printStackTrace();
         return classes;
      }
   }

   private Class getExpressionReturnType(Class clazz) {
      try {
         Method method = clazz.getDeclaredMethod("getReturnType");
         method.setAccessible(true);
         Object o = clazz.newInstance();
         Object returnType = method.invoke(o);
         return returnType.getClass();
      } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException var5) {
         var5.printStackTrace();
         return null;
      }
   }
}
