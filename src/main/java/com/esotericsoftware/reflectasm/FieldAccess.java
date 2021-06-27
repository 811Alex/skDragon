package com.esotericsoftware.reflectasm;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public abstract class FieldAccess {
   private String[] fieldNames;
   private Class[] fieldTypes;
   private Field[] fields;

   public int getIndex(String fieldName) {
      int i = 0;

      for(int n = this.fieldNames.length; i < n; ++i) {
         if (this.fieldNames[i].equals(fieldName)) {
            return i;
         }
      }

      throw new IllegalArgumentException("Unable to find non-private field: " + fieldName);
   }

   public int getIndex(Field field) {
      int i = 0;

      for(int n = this.fields.length; i < n; ++i) {
         if (this.fields[i].equals(field)) {
            return i;
         }
      }

      throw new IllegalArgumentException("Unable to find non-private field: " + field);
   }

   public void set(Object instance, String fieldName, Object value) {
      this.set(instance, this.getIndex(fieldName), value);
   }

   public Object get(Object instance, String fieldName) {
      return this.get(instance, this.getIndex(fieldName));
   }

   public String[] getFieldNames() {
      return this.fieldNames;
   }

   public Class[] getFieldTypes() {
      return this.fieldTypes;
   }

   public int getFieldCount() {
      return this.fieldTypes.length;
   }

   public Field[] getFields() {
      return this.fields;
   }

   public void setFields(Field[] fields) {
      this.fields = fields;
   }

   public abstract void set(Object var1, int var2, Object var3);

   public abstract void setBoolean(Object var1, int var2, boolean var3);

   public abstract void setByte(Object var1, int var2, byte var3);

   public abstract void setShort(Object var1, int var2, short var3);

   public abstract void setInt(Object var1, int var2, int var3);

   public abstract void setLong(Object var1, int var2, long var3);

   public abstract void setDouble(Object var1, int var2, double var3);

   public abstract void setFloat(Object var1, int var2, float var3);

   public abstract void setChar(Object var1, int var2, char var3);

   public abstract Object get(Object var1, int var2);

   public abstract String getString(Object var1, int var2);

   public abstract char getChar(Object var1, int var2);

   public abstract boolean getBoolean(Object var1, int var2);

   public abstract byte getByte(Object var1, int var2);

   public abstract short getShort(Object var1, int var2);

   public abstract int getInt(Object var1, int var2);

   public abstract long getLong(Object var1, int var2);

   public abstract double getDouble(Object var1, int var2);

   public abstract float getFloat(Object var1, int var2);

   public static FieldAccess get(Class type) {
      if (type.getSuperclass() == null) {
         throw new IllegalArgumentException("The type must not be the Object class, an interface, a primitive type, or void.");
      } else {
         ArrayList fields = new ArrayList();

         int i;
         for(Class nextClass = type; nextClass != Object.class; nextClass = nextClass.getSuperclass()) {
            Field[] declaredFields = nextClass.getDeclaredFields();
            int i = 0;

            for(i = declaredFields.length; i < i; ++i) {
               Field field = declaredFields[i];
               int modifiers = field.getModifiers();
               if (!Modifier.isStatic(modifiers) && !Modifier.isPrivate(modifiers)) {
                  fields.add(field);
               }
            }
         }

         String[] fieldNames = new String[fields.size()];
         Class[] fieldTypes = new Class[fields.size()];
         i = 0;

         for(int n = fieldNames.length; i < n; ++i) {
            fieldNames[i] = ((Field)fields.get(i)).getName();
            fieldTypes[i] = ((Field)fields.get(i)).getType();
         }

         String className = type.getName();
         String accessClassName = className + "FieldAccess";
         if (accessClassName.startsWith("java.")) {
            accessClassName = "reflectasm." + accessClassName;
         }

         AccessClassLoader loader = AccessClassLoader.get(type);
         Class accessClass;
         synchronized(loader) {
            accessClass = loader.loadAccessClass(accessClassName);
            if (accessClass == null) {
               String accessClassNameInternal = accessClassName.replace('.', '/');
               String classNameInternal = className.replace('.', '/');
               ClassWriter cw = new ClassWriter(0);
               cw.visit(196653, 33, accessClassNameInternal, (String)null, "com/esotericsoftware/reflectasm/FieldAccess", (String[])null);
               insertConstructor(cw);
               insertGetObject(cw, classNameInternal, fields);
               insertSetObject(cw, classNameInternal, fields);
               insertGetPrimitive(cw, classNameInternal, fields, Type.BOOLEAN_TYPE);
               insertSetPrimitive(cw, classNameInternal, fields, Type.BOOLEAN_TYPE);
               insertGetPrimitive(cw, classNameInternal, fields, Type.BYTE_TYPE);
               insertSetPrimitive(cw, classNameInternal, fields, Type.BYTE_TYPE);
               insertGetPrimitive(cw, classNameInternal, fields, Type.SHORT_TYPE);
               insertSetPrimitive(cw, classNameInternal, fields, Type.SHORT_TYPE);
               insertGetPrimitive(cw, classNameInternal, fields, Type.INT_TYPE);
               insertSetPrimitive(cw, classNameInternal, fields, Type.INT_TYPE);
               insertGetPrimitive(cw, classNameInternal, fields, Type.LONG_TYPE);
               insertSetPrimitive(cw, classNameInternal, fields, Type.LONG_TYPE);
               insertGetPrimitive(cw, classNameInternal, fields, Type.DOUBLE_TYPE);
               insertSetPrimitive(cw, classNameInternal, fields, Type.DOUBLE_TYPE);
               insertGetPrimitive(cw, classNameInternal, fields, Type.FLOAT_TYPE);
               insertSetPrimitive(cw, classNameInternal, fields, Type.FLOAT_TYPE);
               insertGetPrimitive(cw, classNameInternal, fields, Type.CHAR_TYPE);
               insertSetPrimitive(cw, classNameInternal, fields, Type.CHAR_TYPE);
               insertGetString(cw, classNameInternal, fields);
               cw.visitEnd();
               accessClass = loader.defineAccessClass(accessClassName, cw.toByteArray());
            }
         }

         try {
            FieldAccess access = (FieldAccess)accessClass.newInstance();
            access.fieldNames = fieldNames;
            access.fieldTypes = fieldTypes;
            access.fields = (Field[])fields.toArray(new Field[fields.size()]);
            return access;
         } catch (Throwable var13) {
            throw new RuntimeException("Error constructing field access class: " + accessClassName, var13);
         }
      }
   }

   private static void insertConstructor(ClassWriter cw) {
      MethodVisitor mv = cw.visitMethod(1, "<init>", "()V", (String)null, (String[])null);
      mv.visitCode();
      mv.visitVarInsn(25, 0);
      mv.visitMethodInsn(183, "com/esotericsoftware/reflectasm/FieldAccess", "<init>", "()V");
      mv.visitInsn(177);
      mv.visitMaxs(1, 1);
      mv.visitEnd();
   }

   private static void insertSetObject(ClassWriter cw, String classNameInternal, ArrayList fields) {
      int maxStack = 6;
      MethodVisitor mv = cw.visitMethod(1, "set", "(Ljava/lang/Object;ILjava/lang/Object;)V", (String)null, (String[])null);
      mv.visitCode();
      mv.visitVarInsn(21, 2);
      if (!fields.isEmpty()) {
         --maxStack;
         Label[] labels = new Label[fields.size()];
         int i = 0;

         int i;
         for(i = labels.length; i < i; ++i) {
            labels[i] = new Label();
         }

         Label defaultLabel = new Label();
         mv.visitTableSwitchInsn(0, labels.length - 1, defaultLabel, labels);
         i = 0;

         for(int n = labels.length; i < n; ++i) {
            Field field = (Field)fields.get(i);
            Type fieldType = Type.getType(field.getType());
            mv.visitLabel(labels[i]);
            mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
            mv.visitVarInsn(25, 1);
            mv.visitTypeInsn(192, classNameInternal);
            mv.visitVarInsn(25, 3);
            switch(fieldType.getSort()) {
            case 1:
               mv.visitTypeInsn(192, "java/lang/Boolean");
               mv.visitMethodInsn(182, "java/lang/Boolean", "booleanValue", "()Z");
               break;
            case 2:
               mv.visitTypeInsn(192, "java/lang/Character");
               mv.visitMethodInsn(182, "java/lang/Character", "charValue", "()C");
               break;
            case 3:
               mv.visitTypeInsn(192, "java/lang/Byte");
               mv.visitMethodInsn(182, "java/lang/Byte", "byteValue", "()B");
               break;
            case 4:
               mv.visitTypeInsn(192, "java/lang/Short");
               mv.visitMethodInsn(182, "java/lang/Short", "shortValue", "()S");
               break;
            case 5:
               mv.visitTypeInsn(192, "java/lang/Integer");
               mv.visitMethodInsn(182, "java/lang/Integer", "intValue", "()I");
               break;
            case 6:
               mv.visitTypeInsn(192, "java/lang/Float");
               mv.visitMethodInsn(182, "java/lang/Float", "floatValue", "()F");
               break;
            case 7:
               mv.visitTypeInsn(192, "java/lang/Long");
               mv.visitMethodInsn(182, "java/lang/Long", "longValue", "()J");
               break;
            case 8:
               mv.visitTypeInsn(192, "java/lang/Double");
               mv.visitMethodInsn(182, "java/lang/Double", "doubleValue", "()D");
               break;
            case 9:
               mv.visitTypeInsn(192, fieldType.getDescriptor());
               break;
            case 10:
               mv.visitTypeInsn(192, fieldType.getInternalName());
            }

            mv.visitFieldInsn(181, field.getDeclaringClass().getName().replace('.', '/'), field.getName(), fieldType.getDescriptor());
            mv.visitInsn(177);
         }

         mv.visitLabel(defaultLabel);
         mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
      }

      mv = insertThrowExceptionForFieldNotFound(mv);
      mv.visitMaxs(maxStack, 4);
      mv.visitEnd();
   }

   private static void insertGetObject(ClassWriter cw, String classNameInternal, ArrayList fields) {
      int maxStack = 6;
      MethodVisitor mv = cw.visitMethod(1, "get", "(Ljava/lang/Object;I)Ljava/lang/Object;", (String)null, (String[])null);
      mv.visitCode();
      mv.visitVarInsn(21, 2);
      if (!fields.isEmpty()) {
         --maxStack;
         Label[] labels = new Label[fields.size()];
         int i = 0;

         int i;
         for(i = labels.length; i < i; ++i) {
            labels[i] = new Label();
         }

         Label defaultLabel = new Label();
         mv.visitTableSwitchInsn(0, labels.length - 1, defaultLabel, labels);
         i = 0;

         for(int n = labels.length; i < n; ++i) {
            Field field = (Field)fields.get(i);
            mv.visitLabel(labels[i]);
            mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
            mv.visitVarInsn(25, 1);
            mv.visitTypeInsn(192, classNameInternal);
            mv.visitFieldInsn(180, field.getDeclaringClass().getName().replace('.', '/'), field.getName(), Type.getDescriptor(field.getType()));
            Type fieldType = Type.getType(field.getType());
            switch(fieldType.getSort()) {
            case 1:
               mv.visitMethodInsn(184, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
               break;
            case 2:
               mv.visitMethodInsn(184, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;");
               break;
            case 3:
               mv.visitMethodInsn(184, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
               break;
            case 4:
               mv.visitMethodInsn(184, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
               break;
            case 5:
               mv.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
               break;
            case 6:
               mv.visitMethodInsn(184, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
               break;
            case 7:
               mv.visitMethodInsn(184, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
               break;
            case 8:
               mv.visitMethodInsn(184, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
            }

            mv.visitInsn(176);
         }

         mv.visitLabel(defaultLabel);
         mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
      }

      insertThrowExceptionForFieldNotFound(mv);
      mv.visitMaxs(maxStack, 3);
      mv.visitEnd();
   }

   private static void insertGetString(ClassWriter cw, String classNameInternal, ArrayList fields) {
      int maxStack = 6;
      MethodVisitor mv = cw.visitMethod(1, "getString", "(Ljava/lang/Object;I)Ljava/lang/String;", (String)null, (String[])null);
      mv.visitCode();
      mv.visitVarInsn(21, 2);
      if (!fields.isEmpty()) {
         --maxStack;
         Label[] labels = new Label[fields.size()];
         Label labelForInvalidTypes = new Label();
         boolean hasAnyBadTypeLabel = false;
         int i = 0;

         int i;
         for(i = labels.length; i < i; ++i) {
            if (((Field)fields.get(i)).getType().equals(String.class)) {
               labels[i] = new Label();
            } else {
               labels[i] = labelForInvalidTypes;
               hasAnyBadTypeLabel = true;
            }
         }

         Label defaultLabel = new Label();
         mv.visitTableSwitchInsn(0, labels.length - 1, defaultLabel, labels);
         i = 0;

         for(int n = labels.length; i < n; ++i) {
            if (!labels[i].equals(labelForInvalidTypes)) {
               Field field = (Field)fields.get(i);
               mv.visitLabel(labels[i]);
               mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
               mv.visitVarInsn(25, 1);
               mv.visitTypeInsn(192, classNameInternal);
               mv.visitFieldInsn(180, field.getDeclaringClass().getName().replace('.', '/'), field.getName(), "Ljava/lang/String;");
               mv.visitInsn(176);
            }
         }

         if (hasAnyBadTypeLabel) {
            mv.visitLabel(labelForInvalidTypes);
            mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
            insertThrowExceptionForFieldType(mv, "String");
         }

         mv.visitLabel(defaultLabel);
         mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
      }

      insertThrowExceptionForFieldNotFound(mv);
      mv.visitMaxs(maxStack, 3);
      mv.visitEnd();
   }

   private static void insertSetPrimitive(ClassWriter cw, String classNameInternal, ArrayList fields, Type primitiveType) {
      int maxStack = 6;
      int maxLocals = 4;
      String typeNameInternal = primitiveType.getDescriptor();
      String setterMethodName;
      byte loadValueInstruction;
      switch(primitiveType.getSort()) {
      case 1:
         setterMethodName = "setBoolean";
         loadValueInstruction = 21;
         break;
      case 2:
         setterMethodName = "setChar";
         loadValueInstruction = 21;
         break;
      case 3:
         setterMethodName = "setByte";
         loadValueInstruction = 21;
         break;
      case 4:
         setterMethodName = "setShort";
         loadValueInstruction = 21;
         break;
      case 5:
         setterMethodName = "setInt";
         loadValueInstruction = 21;
         break;
      case 6:
         setterMethodName = "setFloat";
         loadValueInstruction = 23;
         break;
      case 7:
         setterMethodName = "setLong";
         loadValueInstruction = 22;
         ++maxLocals;
         break;
      case 8:
         setterMethodName = "setDouble";
         loadValueInstruction = 24;
         ++maxLocals;
         break;
      default:
         setterMethodName = "set";
         loadValueInstruction = 25;
      }

      MethodVisitor mv = cw.visitMethod(1, setterMethodName, "(Ljava/lang/Object;I" + typeNameInternal + ")V", (String)null, (String[])null);
      mv.visitCode();
      mv.visitVarInsn(21, 2);
      if (!fields.isEmpty()) {
         --maxStack;
         Label[] labels = new Label[fields.size()];
         Label labelForInvalidTypes = new Label();
         boolean hasAnyBadTypeLabel = false;
         int i = 0;

         int i;
         for(i = labels.length; i < i; ++i) {
            if (Type.getType(((Field)fields.get(i)).getType()).equals(primitiveType)) {
               labels[i] = new Label();
            } else {
               labels[i] = labelForInvalidTypes;
               hasAnyBadTypeLabel = true;
            }
         }

         Label defaultLabel = new Label();
         mv.visitTableSwitchInsn(0, labels.length - 1, defaultLabel, labels);
         i = 0;

         for(int n = labels.length; i < n; ++i) {
            if (!labels[i].equals(labelForInvalidTypes)) {
               Field field = (Field)fields.get(i);
               mv.visitLabel(labels[i]);
               mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
               mv.visitVarInsn(25, 1);
               mv.visitTypeInsn(192, classNameInternal);
               mv.visitVarInsn(loadValueInstruction, 3);
               mv.visitFieldInsn(181, field.getDeclaringClass().getName().replace('.', '/'), field.getName(), typeNameInternal);
               mv.visitInsn(177);
            }
         }

         if (hasAnyBadTypeLabel) {
            mv.visitLabel(labelForInvalidTypes);
            mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
            insertThrowExceptionForFieldType(mv, primitiveType.getClassName());
         }

         mv.visitLabel(defaultLabel);
         mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
      }

      mv = insertThrowExceptionForFieldNotFound(mv);
      mv.visitMaxs(maxStack, maxLocals);
      mv.visitEnd();
   }

   private static void insertGetPrimitive(ClassWriter cw, String classNameInternal, ArrayList fields, Type primitiveType) {
      int maxStack = 6;
      String typeNameInternal = primitiveType.getDescriptor();
      String getterMethodName;
      short returnValueInstruction;
      switch(primitiveType.getSort()) {
      case 1:
         getterMethodName = "getBoolean";
         returnValueInstruction = 172;
         break;
      case 2:
         getterMethodName = "getChar";
         returnValueInstruction = 172;
         break;
      case 3:
         getterMethodName = "getByte";
         returnValueInstruction = 172;
         break;
      case 4:
         getterMethodName = "getShort";
         returnValueInstruction = 172;
         break;
      case 5:
         getterMethodName = "getInt";
         returnValueInstruction = 172;
         break;
      case 6:
         getterMethodName = "getFloat";
         returnValueInstruction = 174;
         break;
      case 7:
         getterMethodName = "getLong";
         returnValueInstruction = 173;
         break;
      case 8:
         getterMethodName = "getDouble";
         returnValueInstruction = 175;
         break;
      default:
         getterMethodName = "get";
         returnValueInstruction = 176;
      }

      MethodVisitor mv = cw.visitMethod(1, getterMethodName, "(Ljava/lang/Object;I)" + typeNameInternal, (String)null, (String[])null);
      mv.visitCode();
      mv.visitVarInsn(21, 2);
      if (!fields.isEmpty()) {
         --maxStack;
         Label[] labels = new Label[fields.size()];
         Label labelForInvalidTypes = new Label();
         boolean hasAnyBadTypeLabel = false;
         int i = 0;

         int i;
         for(i = labels.length; i < i; ++i) {
            if (Type.getType(((Field)fields.get(i)).getType()).equals(primitiveType)) {
               labels[i] = new Label();
            } else {
               labels[i] = labelForInvalidTypes;
               hasAnyBadTypeLabel = true;
            }
         }

         Label defaultLabel = new Label();
         mv.visitTableSwitchInsn(0, labels.length - 1, defaultLabel, labels);
         i = 0;

         for(int n = labels.length; i < n; ++i) {
            Field field = (Field)fields.get(i);
            if (!labels[i].equals(labelForInvalidTypes)) {
               mv.visitLabel(labels[i]);
               mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
               mv.visitVarInsn(25, 1);
               mv.visitTypeInsn(192, classNameInternal);
               mv.visitFieldInsn(180, field.getDeclaringClass().getName().replace('.', '/'), field.getName(), typeNameInternal);
               mv.visitInsn(returnValueInstruction);
            }
         }

         if (hasAnyBadTypeLabel) {
            mv.visitLabel(labelForInvalidTypes);
            mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
            insertThrowExceptionForFieldType(mv, primitiveType.getClassName());
         }

         mv.visitLabel(defaultLabel);
         mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
      }

      mv = insertThrowExceptionForFieldNotFound(mv);
      mv.visitMaxs(maxStack, 3);
      mv.visitEnd();
   }

   private static MethodVisitor insertThrowExceptionForFieldNotFound(MethodVisitor mv) {
      mv.visitTypeInsn(187, "java/lang/IllegalArgumentException");
      mv.visitInsn(89);
      mv.visitTypeInsn(187, "java/lang/StringBuilder");
      mv.visitInsn(89);
      mv.visitLdcInsn("Field not found: ");
      mv.visitMethodInsn(183, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V");
      mv.visitVarInsn(21, 2);
      mv.visitMethodInsn(182, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;");
      mv.visitMethodInsn(182, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;");
      mv.visitMethodInsn(183, "java/lang/IllegalArgumentException", "<init>", "(Ljava/lang/String;)V");
      mv.visitInsn(191);
      return mv;
   }

   private static MethodVisitor insertThrowExceptionForFieldType(MethodVisitor mv, String fieldType) {
      mv.visitTypeInsn(187, "java/lang/IllegalArgumentException");
      mv.visitInsn(89);
      mv.visitTypeInsn(187, "java/lang/StringBuilder");
      mv.visitInsn(89);
      mv.visitLdcInsn("Field not declared as " + fieldType + ": ");
      mv.visitMethodInsn(183, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V");
      mv.visitVarInsn(21, 2);
      mv.visitMethodInsn(182, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;");
      mv.visitMethodInsn(182, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;");
      mv.visitMethodInsn(183, "java/lang/IllegalArgumentException", "<init>", "(Ljava/lang/String;)V");
      mv.visitInsn(191);
      return mv;
   }
}
