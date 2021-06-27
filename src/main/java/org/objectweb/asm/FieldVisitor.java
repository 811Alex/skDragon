package org.objectweb.asm;

public abstract class FieldVisitor {
   protected final int api;
   protected FieldVisitor fv;

   public FieldVisitor(int api) {
      this(api, (FieldVisitor)null);
   }

   public FieldVisitor(int api, FieldVisitor fieldVisitor) {
      if (api != 458752 && api != 393216 && api != 327680 && api != 262144) {
         throw new IllegalArgumentException("Unsupported api " + api);
      } else {
         this.api = api;
         this.fv = fieldVisitor;
      }
   }

   public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
      return this.fv != null ? this.fv.visitAnnotation(descriptor, visible) : null;
   }

   public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
      if (this.api < 327680) {
         throw new UnsupportedOperationException("This feature requires ASM5");
      } else {
         return this.fv != null ? this.fv.visitTypeAnnotation(typeRef, typePath, descriptor, visible) : null;
      }
   }

   public void visitAttribute(Attribute attribute) {
      if (this.fv != null) {
         this.fv.visitAttribute(attribute);
      }

   }

   public void visitEnd() {
      if (this.fv != null) {
         this.fv.visitEnd();
      }

   }
}
