package ud.skript.sashie.skDragon.particleEngine.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.bukkit.Bukkit;

public final class ReflectionUtils {
   public static Constructor getConstructor(Class clazz, Class... parameterTypes) throws NoSuchMethodException {
      Class[] primitiveTypes = ReflectionUtils.DataType.getPrimitive(parameterTypes);
      Constructor[] constructors;
      int length = (constructors = clazz.getConstructors()).length;

      for(int i = 0; i < length; ++i) {
         Constructor constructor = constructors[i];
         if (ReflectionUtils.DataType.compare(ReflectionUtils.DataType.getPrimitive(constructor.getParameterTypes()), primitiveTypes)) {
            return constructor;
         }
      }

      throw new NoSuchMethodException("There is no such constructor in this class with the specified parameter types");
   }

   public static Constructor getConstructor(String className, ReflectionUtils.PackageType packageType, Class... parameterTypes) throws NoSuchMethodException, ClassNotFoundException {
      return getConstructor(packageType.getClass(className), parameterTypes);
   }

   public static Object instantiateObject(Class clazz, Object... arguments) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
      return getConstructor(clazz, ReflectionUtils.DataType.getPrimitive(arguments)).newInstance(arguments);
   }

   public static Object instantiateObject(String className, ReflectionUtils.PackageType packageType, Object... arguments) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException {
      return instantiateObject(packageType.getClass(className), arguments);
   }

   public static Method getMethod(Class clazz, String methodName, Class... parameterTypes) throws NoSuchMethodException {
      Class[] primitiveTypes = ReflectionUtils.DataType.getPrimitive(parameterTypes);
      Method[] methods;
      int length = (methods = clazz.getMethods()).length;

      for(int i = 0; i < length; ++i) {
         Method method = methods[i];
         if (method.getName().equals(methodName) && ReflectionUtils.DataType.compare(ReflectionUtils.DataType.getPrimitive(method.getParameterTypes()), primitiveTypes)) {
            return method;
         }
      }

      throw new NoSuchMethodException("There is no such method in this class with the specified name and parameter types");
   }

   public static Method getMethod(String className, ReflectionUtils.PackageType packageType, String methodName, Class... parameterTypes) throws NoSuchMethodException, ClassNotFoundException {
      return getMethod(packageType.getClass(className), methodName, parameterTypes);
   }

   public static Object invokeMethod(Object instance, String methodName, Object... arguments) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
      return getMethod(instance.getClass(), methodName, ReflectionUtils.DataType.getPrimitive(arguments)).invoke(instance, arguments);
   }

   public static Object invokeMethod(Object instance, Class clazz, String methodName, Object... arguments) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
      return getMethod(clazz, methodName, ReflectionUtils.DataType.getPrimitive(arguments)).invoke(instance, arguments);
   }

   public static Object invokeMethod(Object instance, String className, ReflectionUtils.PackageType packageType, String methodName, Object... arguments) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException {
      return invokeMethod(instance, packageType.getClass(className), methodName, arguments);
   }

   public static Object invokeMethod(Object handle, String methodName, Class[] parameterClasses, Object... args) {
      return invokeMethod(handle.getClass(), handle, methodName, parameterClasses, args);
   }

   public static Object invokeMethod(Class clazz, Object handle, String methodName, Class[] parameterClasses, Object... args) {
      Optional methodOptional = getMethods(clazz, methodName, parameterClasses);
      if (!methodOptional.isPresent()) {
         return null;
      } else {
         Method method = (Method)methodOptional.get();

         try {
            return method.invoke(handle, args);
         } catch (InvocationTargetException | IllegalAccessException var8) {
            var8.printStackTrace();
            return null;
         }
      }
   }

   public static Optional getMethods(Class clazz, String name, Class... params) {
      try {
         return Optional.of(clazz.getMethod(name, params));
      } catch (NoSuchMethodException var5) {
         var5.printStackTrace();

         try {
            return Optional.of(clazz.getDeclaredMethod(name, params));
         } catch (NoSuchMethodException var4) {
            var4.printStackTrace();
            return Optional.empty();
         }
      }
   }

   public static boolean setField(Class from, Object obj, String field, Object newValue) {
      try {
         Field f = from.getDeclaredField(field);
         f.setAccessible(true);
         f.set(obj, newValue);
         return true;
      } catch (Exception var5) {
         return false;
      }
   }

   public static Object getField(Class from, Object obj, String field) {
      try {
         Field f = from.getDeclaredField(field);
         f.setAccessible(true);
         return f.get(obj);
      } catch (Exception var4) {
         return null;
      }
   }

   public static Field getField(Class clazz, String s, Class clazz2, int n) {
      Field[] declaredFields;
      int length = (declaredFields = clazz.getDeclaredFields()).length;

      for(int i = 0; i < length; ++i) {
         Field field = declaredFields[i];
         if ((s == null || field.getName().equals(s)) && clazz2.isAssignableFrom(field.getType()) && n-- <= 0) {
            field.setAccessible(true);
            return field;
         }
      }

      if (clazz.getSuperclass() != null) {
         return getField(clazz.getSuperclass(), s, clazz2, n);
      } else {
         throw new IllegalArgumentException("Field with type " + clazz2 + " not found");
      }
   }

   public static Field getField(Class clazz, boolean declared, String fieldName) throws NoSuchFieldException, SecurityException {
      Field field = declared ? clazz.getDeclaredField(fieldName) : clazz.getField(fieldName);
      field.setAccessible(true);
      return field;
   }

   public static Field getField(String className, ReflectionUtils.PackageType packageType, boolean declared, String fieldName) throws NoSuchFieldException, SecurityException, ClassNotFoundException {
      return getField(packageType.getClass(className), declared, fieldName);
   }

   public static Object getValue(Object instance, Class clazz, boolean declared, String fieldName) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
      return getField(clazz, declared, fieldName).get(instance);
   }

   public static Object getValue(Object instance, String className, ReflectionUtils.PackageType packageType, boolean declared, String fieldName) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, ClassNotFoundException {
      return getValue(instance, packageType.getClass(className), declared, fieldName);
   }

   public static Object getValue(Object instance, boolean declared, String fieldName) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
      return getValue(instance, instance.getClass(), declared, fieldName);
   }

   public static void setValue(Object instance, Class clazz, boolean declared, String fieldName, Object value) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
      getField(clazz, declared, fieldName).set(instance, value);
   }

   public static void setValue(Object instance, String className, ReflectionUtils.PackageType packageType, boolean declared, String fieldName, Object value) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, ClassNotFoundException {
      setValue(instance, packageType.getClass(className), declared, fieldName, value);
   }

   public static void setValue(Object instance, boolean declared, String fieldName, Object value) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
      setValue(instance, instance.getClass(), declared, fieldName, value);
   }

   public static enum DataType {
      BYTE(Byte.TYPE, Byte.class),
      SHORT(Short.TYPE, Short.class),
      INTEGER(Integer.TYPE, Integer.class),
      LONG(Long.TYPE, Long.class),
      CHARACTER(Character.TYPE, Character.class),
      FLOAT(Float.TYPE, Float.class),
      DOUBLE(Double.TYPE, Double.class),
      BOOLEAN(Boolean.TYPE, Boolean.class);

      private static final Map CLASS_MAP = new HashMap();
      private final Class primitive;
      private final Class reference;

      static {
         ReflectionUtils.DataType[] values;
         int length = (values = values()).length;

         for(int i = 0; i < length; ++i) {
            ReflectionUtils.DataType type = values[i];
            CLASS_MAP.put(type.primitive, type);
            CLASS_MAP.put(type.reference, type);
         }

      }

      private DataType(Class primitive, Class reference) {
         this.primitive = primitive;
         this.reference = reference;
      }

      public Class getPrimitive() {
         return this.primitive;
      }

      public Class getReference() {
         return this.reference;
      }

      public static ReflectionUtils.DataType fromClass(Class clazz) {
         return (ReflectionUtils.DataType)CLASS_MAP.get(clazz);
      }

      public static Class getPrimitive(Class clazz) {
         ReflectionUtils.DataType type = fromClass(clazz);
         return type == null ? clazz : type.getPrimitive();
      }

      public static Class getReference(Class clazz) {
         ReflectionUtils.DataType type = fromClass(clazz);
         return type == null ? clazz : type.getReference();
      }

      public static Class[] getPrimitive(Class[] classes) {
         int length = classes == null ? 0 : classes.length;
         Class[] types = new Class[length];

         for(int index = 0; index < length; ++index) {
            types[index] = getPrimitive(classes[index]);
         }

         return types;
      }

      public static Class[] getReference(Class[] classes) {
         int length = classes == null ? 0 : classes.length;
         Class[] types = new Class[length];

         for(int index = 0; index < length; ++index) {
            types[index] = getReference(classes[index]);
         }

         return types;
      }

      public static Class[] getPrimitive(Object[] objects) {
         int length = objects == null ? 0 : objects.length;
         Class[] types = new Class[length];

         for(int index = 0; index < length; ++index) {
            types[index] = getPrimitive(objects[index].getClass());
         }

         return types;
      }

      public static Class[] getReference(Object[] objects) {
         int length = objects == null ? 0 : objects.length;
         Class[] types = new Class[length];

         for(int index = 0; index < length; ++index) {
            types[index] = getReference(objects[index].getClass());
         }

         return types;
      }

      public static boolean compare(Class[] primary, Class[] secondary) {
         if (primary != null && secondary != null && primary.length == secondary.length) {
            for(int index = 0; index < primary.length; ++index) {
               Class primaryClass = primary[index];
               Class secondaryClass = secondary[index];
               if (!primaryClass.equals(secondaryClass) && !primaryClass.isAssignableFrom(secondaryClass)) {
                  return false;
               }
            }

            return true;
         } else {
            return false;
         }
      }
   }

   public static enum PackageType {
      MINECRAFT_SERVER("MINECRAFT_SERVER", 0, "net.minecraft.server." + getServerVersion()),
      CRAFTBUKKIT("CRAFTBUKKIT", 1, "org.bukkit.craftbukkit." + getServerVersion()),
      CRAFTBUKKIT_BLOCK("CRAFTBUKKIT_BLOCK", 2, CRAFTBUKKIT, "block"),
      CRAFTBUKKIT_CHUNKIO("CRAFTBUKKIT_CHUNKIO", 3, CRAFTBUKKIT, "chunkio"),
      CRAFTBUKKIT_COMMAND("CRAFTBUKKIT_COMMAND", 4, CRAFTBUKKIT, "command"),
      CRAFTBUKKIT_CONVERSATIONS("CRAFTBUKKIT_CONVERSATIONS", 5, CRAFTBUKKIT, "conversations"),
      CRAFTBUKKIT_ENCHANTMENS("CRAFTBUKKIT_ENCHANTMENS", 6, CRAFTBUKKIT, "enchantments"),
      CRAFTBUKKIT_ENTITY("CRAFTBUKKIT_ENTITY", 7, CRAFTBUKKIT, "entity"),
      CRAFTBUKKIT_EVENT("CRAFTBUKKIT_EVENT", 8, CRAFTBUKKIT, "event"),
      CRAFTBUKKIT_GENERATOR("CRAFTBUKKIT_GENERATOR", 9, CRAFTBUKKIT, "generator"),
      CRAFTBUKKIT_HELP("CRAFTBUKKIT_HELP", 10, CRAFTBUKKIT, "help"),
      CRAFTBUKKIT_INVENTORY("CRAFTBUKKIT_INVENTORY", 11, CRAFTBUKKIT, "inventory"),
      CRAFTBUKKIT_MAP("CRAFTBUKKIT_MAP", 12, CRAFTBUKKIT, "map"),
      CRAFTBUKKIT_METADATA("CRAFTBUKKIT_METADATA", 13, CRAFTBUKKIT, "metadata"),
      CRAFTBUKKIT_POTION("CRAFTBUKKIT_POTION", 14, CRAFTBUKKIT, "potion"),
      CRAFTBUKKIT_PROJECTILES("CRAFTBUKKIT_PROJECTILES", 15, CRAFTBUKKIT, "projectiles"),
      CRAFTBUKKIT_SCHEDULER("CRAFTBUKKIT_SCHEDULER", 16, CRAFTBUKKIT, "scheduler"),
      CRAFTBUKKIT_SCOREBOARD("CRAFTBUKKIT_SCOREBOARD", 17, CRAFTBUKKIT, "scoreboard"),
      CRAFTBUKKIT_UPDATER("CRAFTBUKKIT_UPDATER", 18, CRAFTBUKKIT, "updater"),
      CRAFTBUKKIT_UTIL("CRAFTBUKKIT_UTIL", 19, CRAFTBUKKIT, "util"),
      BUKKIT("BUKKIT", 20, "org.bukkit"),
      BUKKIT_ADVANCEMENT("BUKKIT_ADVANCEMENT", 21, BUKKIT, "advancement"),
      BUKKIT_ATTRIBUTE("BUKKIT_ATTRIBUTE", 22, BUKKIT, "attribute"),
      BUKKIT_COMMAND("BUKKIT_COMMAND", 23, BUKKIT, "command"),
      BUKKIT_BLOCK("BUKKIT_BLOCK", 24, BUKKIT, "block"),
      BUKKIT_BLOCK_DATA("BUKKIT_BLOCK_DATA", 25, BUKKIT, "block.data"),
      BUKKIT_BLOCK_TYPE("BUKKIT_BLOCK_TYPE", 26, BUKKIT, "block.data.type"),
      BUKKIT_MATERIAL("BUKKIT_MATERIAL", 27, BUKKIT, "material"),
      MINECRAFT_NETWORK_PROTOCOL_GAME("MINECRAFT_NETWORK_PROTOCOL_GAME", 28, "net.minecraft.network.protocol.game"),
      MINECRAFT_LEVEL("MINECRAFT_LEVEL", 29, "net.minecraft.server.level"),
      MINECRAFT_SERVER_NETWORK("MINECRAFT_SERVER_NETWORK", 30, "net.minecraft.server.network"),
      MINECRAFT_CORE_PARTICLES("MINECRAFT_CORE_PARTICLES", 31, "net.minecraft.core.particles"),
      MINECRAFT_NETWORK_PROTOCOL("MINECRAFT_NETWORK_PROTOCOL_GAME", 32, "net.minecraft.network.protocol"),
      MOJANG_MATH("MOJANG_MATH", 33, "com.mojang.math");

      private final String path;

      private PackageType(String s, int n, String path) {
         this.path = path;
      }

      private PackageType(String s, int n, ReflectionUtils.PackageType parent, String path) {
         this(s, n, parent + "." + path);
      }

      public String getPath() {
         return this.path;
      }

      public Class getClass(String className) throws ClassNotFoundException {
         return Class.forName(this + "." + className);
      }

      public String toString() {
         return this.path;
      }

      public static String getServerVersion() {
         return Bukkit.getServer().getClass().getPackage().getName().substring(23);
      }
   }
}
