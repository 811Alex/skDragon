package org.objectweb.asm;

final class AnnotationWriter extends AnnotationVisitor {
   private final SymbolTable symbolTable;
   private final boolean useNamedValues;
   private final ByteVector annotation;
   private final int numElementValuePairsOffset;
   private int numElementValuePairs;
   private final AnnotationWriter previousAnnotation;
   private AnnotationWriter nextAnnotation;

   AnnotationWriter(SymbolTable symbolTable, boolean useNamedValues, ByteVector annotation, AnnotationWriter previousAnnotation) {
      super(458752);
      this.symbolTable = symbolTable;
      this.useNamedValues = useNamedValues;
      this.annotation = annotation;
      this.numElementValuePairsOffset = annotation.length == 0 ? -1 : annotation.length - 2;
      this.previousAnnotation = previousAnnotation;
      if (previousAnnotation != null) {
         previousAnnotation.nextAnnotation = this;
      }

   }

   static AnnotationWriter create(SymbolTable symbolTable, String descriptor, AnnotationWriter previousAnnotation) {
      ByteVector annotation = new ByteVector();
      annotation.putShort(symbolTable.addConstantUtf8(descriptor)).putShort(0);
      return new AnnotationWriter(symbolTable, true, annotation, previousAnnotation);
   }

   static AnnotationWriter create(SymbolTable symbolTable, int typeRef, TypePath typePath, String descriptor, AnnotationWriter previousAnnotation) {
      ByteVector typeAnnotation = new ByteVector();
      TypeReference.putTarget(typeRef, typeAnnotation);
      TypePath.put(typePath, typeAnnotation);
      typeAnnotation.putShort(symbolTable.addConstantUtf8(descriptor)).putShort(0);
      return new AnnotationWriter(symbolTable, true, typeAnnotation, previousAnnotation);
   }

   public void visit(String name, Object value) {
      ++this.numElementValuePairs;
      if (this.useNamedValues) {
         this.annotation.putShort(this.symbolTable.addConstantUtf8(name));
      }

      if (value instanceof String) {
         this.annotation.put12(115, this.symbolTable.addConstantUtf8((String)value));
      } else if (value instanceof Byte) {
         this.annotation.put12(66, this.symbolTable.addConstantInteger((Byte)value).index);
      } else if (value instanceof Boolean) {
         int booleanValue = (Boolean)value ? 1 : 0;
         this.annotation.put12(90, this.symbolTable.addConstantInteger(booleanValue).index);
      } else if (value instanceof Character) {
         this.annotation.put12(67, this.symbolTable.addConstantInteger((Character)value).index);
      } else if (value instanceof Short) {
         this.annotation.put12(83, this.symbolTable.addConstantInteger((Short)value).index);
      } else if (value instanceof Type) {
         this.annotation.put12(99, this.symbolTable.addConstantUtf8(((Type)value).getDescriptor()));
      } else {
         int var5;
         int var6;
         if (value instanceof byte[]) {
            byte[] byteArray = (byte[])value;
            this.annotation.put12(91, byteArray.length);
            byte[] var7 = byteArray;
            var6 = byteArray.length;

            for(var5 = 0; var5 < var6; ++var5) {
               byte byteValue = var7[var5];
               this.annotation.put12(66, this.symbolTable.addConstantInteger(byteValue).index);
            }
         } else if (value instanceof boolean[]) {
            boolean[] booleanArray = (boolean[])value;
            this.annotation.put12(91, booleanArray.length);
            boolean[] var25 = booleanArray;
            var6 = booleanArray.length;

            for(var5 = 0; var5 < var6; ++var5) {
               boolean booleanValue = var25[var5];
               this.annotation.put12(90, this.symbolTable.addConstantInteger(booleanValue ? 1 : 0).index);
            }
         } else if (value instanceof short[]) {
            short[] shortArray = (short[])value;
            this.annotation.put12(91, shortArray.length);
            short[] var26 = shortArray;
            var6 = shortArray.length;

            for(var5 = 0; var5 < var6; ++var5) {
               short shortValue = var26[var5];
               this.annotation.put12(83, this.symbolTable.addConstantInteger(shortValue).index);
            }
         } else if (value instanceof char[]) {
            char[] charArray = (char[])value;
            this.annotation.put12(91, charArray.length);
            char[] var27 = charArray;
            var6 = charArray.length;

            for(var5 = 0; var5 < var6; ++var5) {
               char charValue = var27[var5];
               this.annotation.put12(67, this.symbolTable.addConstantInteger(charValue).index);
            }
         } else if (value instanceof int[]) {
            int[] intArray = (int[])value;
            this.annotation.put12(91, intArray.length);
            int[] var28 = intArray;
            var6 = intArray.length;

            for(var5 = 0; var5 < var6; ++var5) {
               int intValue = var28[var5];
               this.annotation.put12(73, this.symbolTable.addConstantInteger(intValue).index);
            }
         } else {
            int var30;
            if (value instanceof long[]) {
               long[] longArray = (long[])value;
               this.annotation.put12(91, longArray.length);
               long[] var8 = longArray;
               var30 = longArray.length;

               for(var6 = 0; var6 < var30; ++var6) {
                  long longValue = var8[var6];
                  this.annotation.put12(74, this.symbolTable.addConstantLong(longValue).index);
               }
            } else if (value instanceof float[]) {
               float[] floatArray = (float[])value;
               this.annotation.put12(91, floatArray.length);
               float[] var31 = floatArray;
               var6 = floatArray.length;

               for(var5 = 0; var5 < var6; ++var5) {
                  float floatValue = var31[var5];
                  this.annotation.put12(70, this.symbolTable.addConstantFloat(floatValue).index);
               }
            } else if (value instanceof double[]) {
               double[] doubleArray = (double[])value;
               this.annotation.put12(91, doubleArray.length);
               double[] var29 = doubleArray;
               var30 = doubleArray.length;

               for(var6 = 0; var6 < var30; ++var6) {
                  double doubleValue = var29[var6];
                  this.annotation.put12(68, this.symbolTable.addConstantDouble(doubleValue).index);
               }
            } else {
               Symbol symbol = this.symbolTable.addConstant(value);
               this.annotation.put12(".s.IFJDCS".charAt(symbol.tag), symbol.index);
            }
         }
      }

   }

   public void visitEnum(String name, String descriptor, String value) {
      ++this.numElementValuePairs;
      if (this.useNamedValues) {
         this.annotation.putShort(this.symbolTable.addConstantUtf8(name));
      }

      this.annotation.put12(101, this.symbolTable.addConstantUtf8(descriptor)).putShort(this.symbolTable.addConstantUtf8(value));
   }

   public AnnotationVisitor visitAnnotation(String name, String descriptor) {
      ++this.numElementValuePairs;
      if (this.useNamedValues) {
         this.annotation.putShort(this.symbolTable.addConstantUtf8(name));
      }

      this.annotation.put12(64, this.symbolTable.addConstantUtf8(descriptor)).putShort(0);
      return new AnnotationWriter(this.symbolTable, true, this.annotation, (AnnotationWriter)null);
   }

   public AnnotationVisitor visitArray(String name) {
      ++this.numElementValuePairs;
      if (this.useNamedValues) {
         this.annotation.putShort(this.symbolTable.addConstantUtf8(name));
      }

      this.annotation.put12(91, 0);
      return new AnnotationWriter(this.symbolTable, false, this.annotation, (AnnotationWriter)null);
   }

   public void visitEnd() {
      if (this.numElementValuePairsOffset != -1) {
         byte[] data = this.annotation.data;
         data[this.numElementValuePairsOffset] = (byte)(this.numElementValuePairs >>> 8);
         data[this.numElementValuePairsOffset + 1] = (byte)this.numElementValuePairs;
      }

   }

   int computeAnnotationsSize(String attributeName) {
      if (attributeName != null) {
         this.symbolTable.addConstantUtf8(attributeName);
      }

      int attributeSize = 8;

      for(AnnotationWriter annotationWriter = this; annotationWriter != null; annotationWriter = annotationWriter.previousAnnotation) {
         attributeSize += annotationWriter.annotation.length;
      }

      return attributeSize;
   }

   static int computeAnnotationsSize(AnnotationWriter lastRuntimeVisibleAnnotation, AnnotationWriter lastRuntimeInvisibleAnnotation, AnnotationWriter lastRuntimeVisibleTypeAnnotation, AnnotationWriter lastRuntimeInvisibleTypeAnnotation) {
      int size = 0;
      if (lastRuntimeVisibleAnnotation != null) {
         size += lastRuntimeVisibleAnnotation.computeAnnotationsSize("RuntimeVisibleAnnotations");
      }

      if (lastRuntimeInvisibleAnnotation != null) {
         size += lastRuntimeInvisibleAnnotation.computeAnnotationsSize("RuntimeInvisibleAnnotations");
      }

      if (lastRuntimeVisibleTypeAnnotation != null) {
         size += lastRuntimeVisibleTypeAnnotation.computeAnnotationsSize("RuntimeVisibleTypeAnnotations");
      }

      if (lastRuntimeInvisibleTypeAnnotation != null) {
         size += lastRuntimeInvisibleTypeAnnotation.computeAnnotationsSize("RuntimeInvisibleTypeAnnotations");
      }

      return size;
   }

   void putAnnotations(int attributeNameIndex, ByteVector output) {
      int attributeLength = 2;
      int numAnnotations = 0;
      AnnotationWriter annotationWriter = this;

      AnnotationWriter firstAnnotation;
      for(firstAnnotation = null; annotationWriter != null; annotationWriter = annotationWriter.previousAnnotation) {
         annotationWriter.visitEnd();
         attributeLength += annotationWriter.annotation.length;
         ++numAnnotations;
         firstAnnotation = annotationWriter;
      }

      output.putShort(attributeNameIndex);
      output.putInt(attributeLength);
      output.putShort(numAnnotations);

      for(annotationWriter = firstAnnotation; annotationWriter != null; annotationWriter = annotationWriter.nextAnnotation) {
         output.putByteArray(annotationWriter.annotation.data, 0, annotationWriter.annotation.length);
      }

   }

   static void putAnnotations(SymbolTable symbolTable, AnnotationWriter lastRuntimeVisibleAnnotation, AnnotationWriter lastRuntimeInvisibleAnnotation, AnnotationWriter lastRuntimeVisibleTypeAnnotation, AnnotationWriter lastRuntimeInvisibleTypeAnnotation, ByteVector output) {
      if (lastRuntimeVisibleAnnotation != null) {
         lastRuntimeVisibleAnnotation.putAnnotations(symbolTable.addConstantUtf8("RuntimeVisibleAnnotations"), output);
      }

      if (lastRuntimeInvisibleAnnotation != null) {
         lastRuntimeInvisibleAnnotation.putAnnotations(symbolTable.addConstantUtf8("RuntimeInvisibleAnnotations"), output);
      }

      if (lastRuntimeVisibleTypeAnnotation != null) {
         lastRuntimeVisibleTypeAnnotation.putAnnotations(symbolTable.addConstantUtf8("RuntimeVisibleTypeAnnotations"), output);
      }

      if (lastRuntimeInvisibleTypeAnnotation != null) {
         lastRuntimeInvisibleTypeAnnotation.putAnnotations(symbolTable.addConstantUtf8("RuntimeInvisibleTypeAnnotations"), output);
      }

   }

   static int computeParameterAnnotationsSize(String attributeName, AnnotationWriter[] annotationWriters, int annotableParameterCount) {
      int attributeSize = 7 + 2 * annotableParameterCount;

      for(int i = 0; i < annotableParameterCount; ++i) {
         AnnotationWriter annotationWriter = annotationWriters[i];
         attributeSize += annotationWriter == null ? 0 : annotationWriter.computeAnnotationsSize(attributeName) - 8;
      }

      return attributeSize;
   }

   static void putParameterAnnotations(int attributeNameIndex, AnnotationWriter[] annotationWriters, int annotableParameterCount, ByteVector output) {
      int attributeLength = 1 + 2 * annotableParameterCount;

      int i;
      AnnotationWriter annotationWriter;
      for(i = 0; i < annotableParameterCount; ++i) {
         annotationWriter = annotationWriters[i];
         attributeLength += annotationWriter == null ? 0 : annotationWriter.computeAnnotationsSize((String)null) - 8;
      }

      output.putShort(attributeNameIndex);
      output.putInt(attributeLength);
      output.putByte(annotableParameterCount);

      for(i = 0; i < annotableParameterCount; ++i) {
         annotationWriter = annotationWriters[i];
         AnnotationWriter firstAnnotation = null;

         int numAnnotations;
         for(numAnnotations = 0; annotationWriter != null; annotationWriter = annotationWriter.previousAnnotation) {
            annotationWriter.visitEnd();
            ++numAnnotations;
            firstAnnotation = annotationWriter;
         }

         output.putShort(numAnnotations);

         for(annotationWriter = firstAnnotation; annotationWriter != null; annotationWriter = annotationWriter.nextAnnotation) {
            output.putByteArray(annotationWriter.annotation.data, 0, annotationWriter.annotation.length);
         }
      }

   }
}
