package com.esotericsoftware.reflectasm;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;
import java.util.HashSet;
import java.util.WeakHashMap;

class AccessClassLoader extends ClassLoader {
   private static final WeakHashMap accessClassLoaders = new WeakHashMap();
   private static final ClassLoader selfContextParentClassLoader = getParentClassLoader(AccessClassLoader.class);
   private static volatile AccessClassLoader selfContextAccessClassLoader;
   private static volatile Method defineClassMethod;
   private final HashSet localClassNames = new HashSet();

   static {
      selfContextAccessClassLoader = new AccessClassLoader(selfContextParentClassLoader);
   }

   private AccessClassLoader(ClassLoader parent) {
      super(parent);
   }

   Class loadAccessClass(String name) {
      if (this.localClassNames.contains(name)) {
         try {
            return this.loadClass(name, false);
         } catch (ClassNotFoundException var3) {
            throw new RuntimeException(var3);
         }
      } else {
         return null;
      }
   }

   Class defineAccessClass(String name, byte[] bytes) throws ClassFormatError {
      this.localClassNames.add(name);
      return this.defineClass(name, bytes);
   }

   protected Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
      if (name.equals(FieldAccess.class.getName())) {
         return FieldAccess.class;
      } else if (name.equals(MethodAccess.class.getName())) {
         return MethodAccess.class;
      } else if (name.equals(ConstructorAccess.class.getName())) {
         return ConstructorAccess.class;
      } else {
         return name.equals(PublicConstructorAccess.class.getName()) ? PublicConstructorAccess.class : super.loadClass(name, resolve);
      }
   }

   Class defineClass(String name, byte[] bytes) throws ClassFormatError {
      try {
         return (Class)getDefineClassMethod().invoke(this.getParent(), name, bytes, 0, bytes.length, this.getClass().getProtectionDomain());
      } catch (Exception var4) {
         return this.defineClass(name, bytes, 0, bytes.length, this.getClass().getProtectionDomain());
      }
   }

   static boolean areInSameRuntimeClassLoader(Class type1, Class type2) {
      if (type1.getPackage() != type2.getPackage()) {
         return false;
      } else {
         ClassLoader loader1 = type1.getClassLoader();
         ClassLoader loader2 = type2.getClassLoader();
         ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
         if (loader1 == null) {
            return loader2 == null || loader2 == systemClassLoader;
         } else if (loader2 == null) {
            return loader1 == systemClassLoader;
         } else {
            return loader1 == loader2;
         }
      }
   }

   private static ClassLoader getParentClassLoader(Class type) {
      ClassLoader parent = type.getClassLoader();
      if (parent == null) {
         parent = ClassLoader.getSystemClassLoader();
      }

      return parent;
   }

   private static Method getDefineClassMethod() throws Exception {
      if (defineClassMethod == null) {
         synchronized(accessClassLoaders) {
            if (defineClassMethod == null) {
               defineClassMethod = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE, ProtectionDomain.class);

               try {
                  defineClassMethod.setAccessible(true);
               } catch (Exception var2) {
               }
            }
         }
      }

      return defineClassMethod;
   }

   static AccessClassLoader get(Class type) {
      ClassLoader parent = getParentClassLoader(type);
      if (selfContextParentClassLoader.equals(parent)) {
         if (selfContextAccessClassLoader == null) {
            synchronized(accessClassLoaders) {
               if (selfContextAccessClassLoader == null) {
                  selfContextAccessClassLoader = new AccessClassLoader(selfContextParentClassLoader);
               }
            }
         }

         return selfContextAccessClassLoader;
      } else {
         synchronized(accessClassLoaders) {
            WeakReference ref = (WeakReference)accessClassLoaders.get(parent);
            AccessClassLoader accessClassLoader;
            if (ref != null) {
               accessClassLoader = (AccessClassLoader)ref.get();
               if (accessClassLoader != null) {
                  return accessClassLoader;
               }

               accessClassLoaders.remove(parent);
            }

            accessClassLoader = new AccessClassLoader(parent);
            accessClassLoaders.put(parent, new WeakReference(accessClassLoader));
            return accessClassLoader;
         }
      }
   }

   public static void remove(ClassLoader parent) {
      if (selfContextParentClassLoader.equals(parent)) {
         selfContextAccessClassLoader = null;
      } else {
         synchronized(accessClassLoaders) {
            accessClassLoaders.remove(parent);
         }
      }

   }

   public static int activeAccessClassLoaders() {
      int sz = accessClassLoaders.size();
      if (selfContextAccessClassLoader != null) {
         ++sz;
      }

      return sz;
   }
}
