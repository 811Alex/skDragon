package org.objectweb.asm;

final class SymbolTable {
   final ClassWriter classWriter;
   private final ClassReader sourceClassReader;
   private int majorVersion;
   private String className;
   private int entryCount;
   private SymbolTable.Entry[] entries;
   private int constantPoolCount;
   private ByteVector constantPool;
   private int bootstrapMethodCount;
   private ByteVector bootstrapMethods;
   private int typeCount;
   private SymbolTable.Entry[] typeTable;

   SymbolTable(ClassWriter classWriter) {
      this.classWriter = classWriter;
      this.sourceClassReader = null;
      this.entries = new SymbolTable.Entry[256];
      this.constantPoolCount = 1;
      this.constantPool = new ByteVector();
   }

   SymbolTable(ClassWriter classWriter, ClassReader classReader) {
      this.classWriter = classWriter;
      this.sourceClassReader = classReader;
      byte[] inputBytes = classReader.classFileBuffer;
      int constantPoolOffset = classReader.getItem(1) - 1;
      int constantPoolLength = classReader.header - constantPoolOffset;
      this.constantPoolCount = classReader.getItemCount();
      this.constantPool = new ByteVector(constantPoolLength);
      this.constantPool.putByteArray(inputBytes, constantPoolOffset, constantPoolLength);
      this.entries = new SymbolTable.Entry[this.constantPoolCount * 2];
      char[] charBuffer = new char[classReader.getMaxStringLength()];
      boolean hasBootstrapMethods = false;

      byte itemTag;
      for(int itemIndex = 1; itemIndex < this.constantPoolCount; itemIndex += itemTag != 5 && itemTag != 6 ? 1 : 2) {
         int itemOffset = classReader.getItem(itemIndex);
         itemTag = inputBytes[itemOffset - 1];
         int nameAndTypeItemOffset;
         switch(itemTag) {
         case 1:
            this.addConstantUtf8(itemIndex, classReader.readUtf(itemIndex, charBuffer));
            break;
         case 2:
         case 13:
         case 14:
         default:
            throw new IllegalArgumentException();
         case 3:
         case 4:
            this.addConstantIntegerOrFloat(itemIndex, itemTag, classReader.readInt(itemOffset));
            break;
         case 5:
         case 6:
            this.addConstantLongOrDouble(itemIndex, itemTag, classReader.readLong(itemOffset));
            break;
         case 7:
         case 8:
         case 16:
         case 19:
         case 20:
            this.addConstantUtf8Reference(itemIndex, itemTag, classReader.readUTF8(itemOffset, charBuffer));
            break;
         case 9:
         case 10:
         case 11:
            nameAndTypeItemOffset = classReader.getItem(classReader.readUnsignedShort(itemOffset + 2));
            this.addConstantMemberReference(itemIndex, itemTag, classReader.readClass(itemOffset, charBuffer), classReader.readUTF8(nameAndTypeItemOffset, charBuffer), classReader.readUTF8(nameAndTypeItemOffset + 2, charBuffer));
            break;
         case 12:
            this.addConstantNameAndType(itemIndex, classReader.readUTF8(itemOffset, charBuffer), classReader.readUTF8(itemOffset + 2, charBuffer));
            break;
         case 15:
            int memberRefItemOffset = classReader.getItem(classReader.readUnsignedShort(itemOffset + 1));
            nameAndTypeItemOffset = classReader.getItem(classReader.readUnsignedShort(memberRefItemOffset + 2));
            this.addConstantMethodHandle(itemIndex, classReader.readByte(itemOffset), classReader.readClass(memberRefItemOffset, charBuffer), classReader.readUTF8(nameAndTypeItemOffset, charBuffer), classReader.readUTF8(nameAndTypeItemOffset + 2, charBuffer));
            break;
         case 17:
         case 18:
            hasBootstrapMethods = true;
            nameAndTypeItemOffset = classReader.getItem(classReader.readUnsignedShort(itemOffset + 2));
            this.addConstantDynamicOrInvokeDynamicReference(itemTag, itemIndex, classReader.readUTF8(nameAndTypeItemOffset, charBuffer), classReader.readUTF8(nameAndTypeItemOffset + 2, charBuffer), classReader.readUnsignedShort(itemOffset));
         }
      }

      if (hasBootstrapMethods) {
         this.copyBootstrapMethods(classReader, charBuffer);
      }

   }

   private void copyBootstrapMethods(ClassReader classReader, char[] charBuffer) {
      byte[] inputBytes = classReader.classFileBuffer;
      int currentAttributeOffset = classReader.getFirstAttributeOffset();

      int bootstrapMethodsOffset;
      for(bootstrapMethodsOffset = classReader.readUnsignedShort(currentAttributeOffset - 2); bootstrapMethodsOffset > 0; --bootstrapMethodsOffset) {
         String attributeName = classReader.readUTF8(currentAttributeOffset, charBuffer);
         if ("BootstrapMethods".equals(attributeName)) {
            this.bootstrapMethodCount = classReader.readUnsignedShort(currentAttributeOffset + 6);
            break;
         }

         currentAttributeOffset += 6 + classReader.readInt(currentAttributeOffset + 2);
      }

      if (this.bootstrapMethodCount > 0) {
         bootstrapMethodsOffset = currentAttributeOffset + 8;
         int bootstrapMethodsLength = classReader.readInt(currentAttributeOffset + 2) - 2;
         this.bootstrapMethods = new ByteVector(bootstrapMethodsLength);
         this.bootstrapMethods.putByteArray(inputBytes, bootstrapMethodsOffset, bootstrapMethodsLength);
         int currentOffset = bootstrapMethodsOffset;

         for(int i = 0; i < this.bootstrapMethodCount; ++i) {
            int offset = currentOffset - bootstrapMethodsOffset;
            int bootstrapMethodRef = classReader.readUnsignedShort(currentOffset);
            currentOffset += 2;
            int numBootstrapArguments = classReader.readUnsignedShort(currentOffset);
            currentOffset += 2;

            int hashCode;
            int bootstrapArgument;
            for(hashCode = classReader.readConst(bootstrapMethodRef, charBuffer).hashCode(); numBootstrapArguments-- > 0; hashCode ^= classReader.readConst(bootstrapArgument, charBuffer).hashCode()) {
               bootstrapArgument = classReader.readUnsignedShort(currentOffset);
               currentOffset += 2;
            }

            this.add(new SymbolTable.Entry(i, 64, (long)offset, hashCode & Integer.MAX_VALUE));
         }
      }

   }

   ClassReader getSource() {
      return this.sourceClassReader;
   }

   int getMajorVersion() {
      return this.majorVersion;
   }

   String getClassName() {
      return this.className;
   }

   int setMajorVersionAndClassName(int majorVersion, String className) {
      this.majorVersion = majorVersion;
      this.className = className;
      return this.addConstantClass(className).index;
   }

   int getConstantPoolCount() {
      return this.constantPoolCount;
   }

   int getConstantPoolLength() {
      return this.constantPool.length;
   }

   void putConstantPool(ByteVector output) {
      output.putShort(this.constantPoolCount).putByteArray(this.constantPool.data, 0, this.constantPool.length);
   }

   int computeBootstrapMethodsSize() {
      if (this.bootstrapMethods != null) {
         this.addConstantUtf8("BootstrapMethods");
         return 8 + this.bootstrapMethods.length;
      } else {
         return 0;
      }
   }

   void putBootstrapMethods(ByteVector output) {
      if (this.bootstrapMethods != null) {
         output.putShort(this.addConstantUtf8("BootstrapMethods")).putInt(this.bootstrapMethods.length + 2).putShort(this.bootstrapMethodCount).putByteArray(this.bootstrapMethods.data, 0, this.bootstrapMethods.length);
      }

   }

   private SymbolTable.Entry get(int hashCode) {
      return this.entries[hashCode % this.entries.length];
   }

   private SymbolTable.Entry put(SymbolTable.Entry entry) {
      int currentCapacity;
      if (this.entryCount > this.entries.length * 3 / 4) {
         currentCapacity = this.entries.length;
         int newCapacity = currentCapacity * 2 + 1;
         SymbolTable.Entry[] newEntries = new SymbolTable.Entry[newCapacity];

         SymbolTable.Entry nextEntry;
         for(int i = currentCapacity - 1; i >= 0; --i) {
            for(SymbolTable.Entry currentEntry = this.entries[i]; currentEntry != null; currentEntry = nextEntry) {
               int newCurrentEntryIndex = currentEntry.hashCode % newCapacity;
               nextEntry = currentEntry.next;
               currentEntry.next = newEntries[newCurrentEntryIndex];
               newEntries[newCurrentEntryIndex] = currentEntry;
            }
         }

         this.entries = newEntries;
      }

      ++this.entryCount;
      currentCapacity = entry.hashCode % this.entries.length;
      entry.next = this.entries[currentCapacity];
      return this.entries[currentCapacity] = entry;
   }

   private void add(SymbolTable.Entry entry) {
      ++this.entryCount;
      int index = entry.hashCode % this.entries.length;
      entry.next = this.entries[index];
      this.entries[index] = entry;
   }

   Symbol addConstant(Object value) {
      if (value instanceof Integer) {
         return this.addConstantInteger((Integer)value);
      } else if (value instanceof Byte) {
         return this.addConstantInteger(((Byte)value).intValue());
      } else if (value instanceof Character) {
         return this.addConstantInteger((Character)value);
      } else if (value instanceof Short) {
         return this.addConstantInteger(((Short)value).intValue());
      } else if (value instanceof Boolean) {
         return this.addConstantInteger((Boolean)value ? 1 : 0);
      } else if (value instanceof Float) {
         return this.addConstantFloat((Float)value);
      } else if (value instanceof Long) {
         return this.addConstantLong((Long)value);
      } else if (value instanceof Double) {
         return this.addConstantDouble((Double)value);
      } else if (value instanceof String) {
         return this.addConstantString((String)value);
      } else if (value instanceof Type) {
         Type type = (Type)value;
         int typeSort = type.getSort();
         if (typeSort == 10) {
            return this.addConstantClass(type.getInternalName());
         } else {
            return typeSort == 11 ? this.addConstantMethodType(type.getDescriptor()) : this.addConstantClass(type.getDescriptor());
         }
      } else if (value instanceof Handle) {
         Handle handle = (Handle)value;
         return this.addConstantMethodHandle(handle.getTag(), handle.getOwner(), handle.getName(), handle.getDesc(), handle.isInterface());
      } else if (value instanceof ConstantDynamic) {
         ConstantDynamic constantDynamic = (ConstantDynamic)value;
         return this.addConstantDynamic(constantDynamic.getName(), constantDynamic.getDescriptor(), constantDynamic.getBootstrapMethod(), constantDynamic.getBootstrapMethodArgumentsUnsafe());
      } else {
         throw new IllegalArgumentException("value " + value);
      }
   }

   Symbol addConstantClass(String value) {
      return this.addConstantUtf8Reference(7, value);
   }

   Symbol addConstantFieldref(String owner, String name, String descriptor) {
      return this.addConstantMemberReference(9, owner, name, descriptor);
   }

   Symbol addConstantMethodref(String owner, String name, String descriptor, boolean isInterface) {
      int tag = isInterface ? 11 : 10;
      return this.addConstantMemberReference(tag, owner, name, descriptor);
   }

   private SymbolTable.Entry addConstantMemberReference(int tag, String owner, String name, String descriptor) {
      int hashCode = hash(tag, owner, name, descriptor);

      for(SymbolTable.Entry entry = this.get(hashCode); entry != null; entry = entry.next) {
         if (entry.tag == tag && entry.hashCode == hashCode && entry.owner.equals(owner) && entry.name.equals(name) && entry.value.equals(descriptor)) {
            return entry;
         }
      }

      this.constantPool.put122(tag, this.addConstantClass(owner).index, this.addConstantNameAndType(name, descriptor));
      return this.put(new SymbolTable.Entry(this.constantPoolCount++, tag, owner, name, descriptor, 0L, hashCode));
   }

   private void addConstantMemberReference(int index, int tag, String owner, String name, String descriptor) {
      this.add(new SymbolTable.Entry(index, tag, owner, name, descriptor, 0L, hash(tag, owner, name, descriptor)));
   }

   Symbol addConstantString(String value) {
      return this.addConstantUtf8Reference(8, value);
   }

   Symbol addConstantInteger(int value) {
      return this.addConstantIntegerOrFloat(3, value);
   }

   Symbol addConstantFloat(float value) {
      return this.addConstantIntegerOrFloat(4, Float.floatToRawIntBits(value));
   }

   private Symbol addConstantIntegerOrFloat(int tag, int value) {
      int hashCode = hash(tag, value);

      for(SymbolTable.Entry entry = this.get(hashCode); entry != null; entry = entry.next) {
         if (entry.tag == tag && entry.hashCode == hashCode && entry.data == (long)value) {
            return entry;
         }
      }

      this.constantPool.putByte(tag).putInt(value);
      return this.put(new SymbolTable.Entry(this.constantPoolCount++, tag, (long)value, hashCode));
   }

   private void addConstantIntegerOrFloat(int index, int tag, int value) {
      this.add(new SymbolTable.Entry(index, tag, (long)value, hash(tag, value)));
   }

   Symbol addConstantLong(long value) {
      return this.addConstantLongOrDouble(5, value);
   }

   Symbol addConstantDouble(double value) {
      return this.addConstantLongOrDouble(6, Double.doubleToRawLongBits(value));
   }

   private Symbol addConstantLongOrDouble(int tag, long value) {
      int hashCode = hash(tag, value);

      for(SymbolTable.Entry entry = this.get(hashCode); entry != null; entry = entry.next) {
         if (entry.tag == tag && entry.hashCode == hashCode && entry.data == value) {
            return entry;
         }
      }

      int index = this.constantPoolCount;
      this.constantPool.putByte(tag).putLong(value);
      this.constantPoolCount += 2;
      return this.put(new SymbolTable.Entry(index, tag, value, hashCode));
   }

   private void addConstantLongOrDouble(int index, int tag, long value) {
      this.add(new SymbolTable.Entry(index, tag, value, hash(tag, value)));
   }

   int addConstantNameAndType(String name, String descriptor) {
      int tag = true;
      int hashCode = hash(12, name, descriptor);

      for(SymbolTable.Entry entry = this.get(hashCode); entry != null; entry = entry.next) {
         if (entry.tag == 12 && entry.hashCode == hashCode && entry.name.equals(name) && entry.value.equals(descriptor)) {
            return entry.index;
         }
      }

      this.constantPool.put122(12, this.addConstantUtf8(name), this.addConstantUtf8(descriptor));
      return this.put(new SymbolTable.Entry(this.constantPoolCount++, 12, name, descriptor, hashCode)).index;
   }

   private void addConstantNameAndType(int index, String name, String descriptor) {
      int tag = true;
      this.add(new SymbolTable.Entry(index, 12, name, descriptor, hash(12, name, descriptor)));
   }

   int addConstantUtf8(String value) {
      int hashCode = hash(1, value);

      for(SymbolTable.Entry entry = this.get(hashCode); entry != null; entry = entry.next) {
         if (entry.tag == 1 && entry.hashCode == hashCode && entry.value.equals(value)) {
            return entry.index;
         }
      }

      this.constantPool.putByte(1).putUTF8(value);
      return this.put(new SymbolTable.Entry(this.constantPoolCount++, 1, value, hashCode)).index;
   }

   private void addConstantUtf8(int index, String value) {
      this.add(new SymbolTable.Entry(index, 1, value, hash(1, value)));
   }

   Symbol addConstantMethodHandle(int referenceKind, String owner, String name, String descriptor, boolean isInterface) {
      int tag = true;
      int hashCode = hash(15, owner, name, descriptor, referenceKind);

      for(SymbolTable.Entry entry = this.get(hashCode); entry != null; entry = entry.next) {
         if (entry.tag == 15 && entry.hashCode == hashCode && entry.data == (long)referenceKind && entry.owner.equals(owner) && entry.name.equals(name) && entry.value.equals(descriptor)) {
            return entry;
         }
      }

      if (referenceKind <= 4) {
         this.constantPool.put112(15, referenceKind, this.addConstantFieldref(owner, name, descriptor).index);
      } else {
         this.constantPool.put112(15, referenceKind, this.addConstantMethodref(owner, name, descriptor, isInterface).index);
      }

      return this.put(new SymbolTable.Entry(this.constantPoolCount++, 15, owner, name, descriptor, (long)referenceKind, hashCode));
   }

   private void addConstantMethodHandle(int index, int referenceKind, String owner, String name, String descriptor) {
      int tag = true;
      int hashCode = hash(15, owner, name, descriptor, referenceKind);
      this.add(new SymbolTable.Entry(index, 15, owner, name, descriptor, (long)referenceKind, hashCode));
   }

   Symbol addConstantMethodType(String methodDescriptor) {
      return this.addConstantUtf8Reference(16, methodDescriptor);
   }

   Symbol addConstantDynamic(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
      Symbol bootstrapMethod = this.addBootstrapMethod(bootstrapMethodHandle, bootstrapMethodArguments);
      return this.addConstantDynamicOrInvokeDynamicReference(17, name, descriptor, bootstrapMethod.index);
   }

   Symbol addConstantInvokeDynamic(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
      Symbol bootstrapMethod = this.addBootstrapMethod(bootstrapMethodHandle, bootstrapMethodArguments);
      return this.addConstantDynamicOrInvokeDynamicReference(18, name, descriptor, bootstrapMethod.index);
   }

   private Symbol addConstantDynamicOrInvokeDynamicReference(int tag, String name, String descriptor, int bootstrapMethodIndex) {
      int hashCode = hash(tag, name, descriptor, bootstrapMethodIndex);

      for(SymbolTable.Entry entry = this.get(hashCode); entry != null; entry = entry.next) {
         if (entry.tag == tag && entry.hashCode == hashCode && entry.data == (long)bootstrapMethodIndex && entry.name.equals(name) && entry.value.equals(descriptor)) {
            return entry;
         }
      }

      this.constantPool.put122(tag, bootstrapMethodIndex, this.addConstantNameAndType(name, descriptor));
      return this.put(new SymbolTable.Entry(this.constantPoolCount++, tag, (String)null, name, descriptor, (long)bootstrapMethodIndex, hashCode));
   }

   private void addConstantDynamicOrInvokeDynamicReference(int tag, int index, String name, String descriptor, int bootstrapMethodIndex) {
      int hashCode = hash(tag, name, descriptor, bootstrapMethodIndex);
      this.add(new SymbolTable.Entry(index, tag, (String)null, name, descriptor, (long)bootstrapMethodIndex, hashCode));
   }

   Symbol addConstantModule(String moduleName) {
      return this.addConstantUtf8Reference(19, moduleName);
   }

   Symbol addConstantPackage(String packageName) {
      return this.addConstantUtf8Reference(20, packageName);
   }

   private Symbol addConstantUtf8Reference(int tag, String value) {
      int hashCode = hash(tag, value);

      for(SymbolTable.Entry entry = this.get(hashCode); entry != null; entry = entry.next) {
         if (entry.tag == tag && entry.hashCode == hashCode && entry.value.equals(value)) {
            return entry;
         }
      }

      this.constantPool.put12(tag, this.addConstantUtf8(value));
      return this.put(new SymbolTable.Entry(this.constantPoolCount++, tag, value, hashCode));
   }

   private void addConstantUtf8Reference(int index, int tag, String value) {
      this.add(new SymbolTable.Entry(index, tag, value, hash(tag, value)));
   }

   Symbol addBootstrapMethod(Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
      ByteVector bootstrapMethodsAttribute = this.bootstrapMethods;
      if (bootstrapMethodsAttribute == null) {
         bootstrapMethodsAttribute = this.bootstrapMethods = new ByteVector();
      }

      Object[] var7 = bootstrapMethodArguments;
      int bootstrapMethodlength = bootstrapMethodArguments.length;

      int numBootstrapArguments;
      for(numBootstrapArguments = 0; numBootstrapArguments < bootstrapMethodlength; ++numBootstrapArguments) {
         Object bootstrapMethodArgument = var7[numBootstrapArguments];
         this.addConstant(bootstrapMethodArgument);
      }

      int bootstrapMethodOffset = bootstrapMethodsAttribute.length;
      bootstrapMethodsAttribute.putShort(this.addConstantMethodHandle(bootstrapMethodHandle.getTag(), bootstrapMethodHandle.getOwner(), bootstrapMethodHandle.getName(), bootstrapMethodHandle.getDesc(), bootstrapMethodHandle.isInterface()).index);
      numBootstrapArguments = bootstrapMethodArguments.length;
      bootstrapMethodsAttribute.putShort(numBootstrapArguments);
      Object[] var9 = bootstrapMethodArguments;
      int var8 = bootstrapMethodArguments.length;

      int hashCode;
      for(hashCode = 0; hashCode < var8; ++hashCode) {
         Object bootstrapMethodArgument = var9[hashCode];
         bootstrapMethodsAttribute.putShort(this.addConstant(bootstrapMethodArgument).index);
      }

      bootstrapMethodlength = bootstrapMethodsAttribute.length - bootstrapMethodOffset;
      hashCode = bootstrapMethodHandle.hashCode();
      Object[] var11 = bootstrapMethodArguments;
      int var10 = bootstrapMethodArguments.length;

      for(int var16 = 0; var16 < var10; ++var16) {
         Object bootstrapMethodArgument = var11[var16];
         hashCode ^= bootstrapMethodArgument.hashCode();
      }

      hashCode &= Integer.MAX_VALUE;
      return this.addBootstrapMethod(bootstrapMethodOffset, bootstrapMethodlength, hashCode);
   }

   private Symbol addBootstrapMethod(int offset, int length, int hashCode) {
      byte[] bootstrapMethodsData = this.bootstrapMethods.data;

      for(SymbolTable.Entry entry = this.get(hashCode); entry != null; entry = entry.next) {
         if (entry.tag == 64 && entry.hashCode == hashCode) {
            int otherOffset = (int)entry.data;
            boolean isSameBootstrapMethod = true;

            for(int i = 0; i < length; ++i) {
               if (bootstrapMethodsData[offset + i] != bootstrapMethodsData[otherOffset + i]) {
                  isSameBootstrapMethod = false;
                  break;
               }
            }

            if (isSameBootstrapMethod) {
               this.bootstrapMethods.length = offset;
               return entry;
            }
         }
      }

      return this.put(new SymbolTable.Entry(this.bootstrapMethodCount++, 64, (long)offset, hashCode));
   }

   Symbol getType(int typeIndex) {
      return this.typeTable[typeIndex];
   }

   int addType(String value) {
      int hashCode = hash(128, value);

      for(SymbolTable.Entry entry = this.get(hashCode); entry != null; entry = entry.next) {
         if (entry.tag == 128 && entry.hashCode == hashCode && entry.value.equals(value)) {
            return entry.index;
         }
      }

      return this.addTypeInternal(new SymbolTable.Entry(this.typeCount, 128, value, hashCode));
   }

   int addUninitializedType(String value, int bytecodeOffset) {
      int hashCode = hash(129, value, bytecodeOffset);

      for(SymbolTable.Entry entry = this.get(hashCode); entry != null; entry = entry.next) {
         if (entry.tag == 129 && entry.hashCode == hashCode && entry.data == (long)bytecodeOffset && entry.value.equals(value)) {
            return entry.index;
         }
      }

      return this.addTypeInternal(new SymbolTable.Entry(this.typeCount, 129, value, (long)bytecodeOffset, hashCode));
   }

   int addMergedType(int typeTableIndex1, int typeTableIndex2) {
      long data = typeTableIndex1 < typeTableIndex2 ? (long)typeTableIndex1 | (long)typeTableIndex2 << 32 : (long)typeTableIndex2 | (long)typeTableIndex1 << 32;
      int hashCode = hash(130, typeTableIndex1 + typeTableIndex2);

      for(SymbolTable.Entry entry = this.get(hashCode); entry != null; entry = entry.next) {
         if (entry.tag == 130 && entry.hashCode == hashCode && entry.data == data) {
            return entry.info;
         }
      }

      String type1 = this.typeTable[typeTableIndex1].value;
      String type2 = this.typeTable[typeTableIndex2].value;
      int commonSuperTypeIndex = this.addType(this.classWriter.getCommonSuperClass(type1, type2));
      this.put(new SymbolTable.Entry(this.typeCount, 130, data, hashCode)).info = commonSuperTypeIndex;
      return commonSuperTypeIndex;
   }

   private int addTypeInternal(SymbolTable.Entry entry) {
      if (this.typeTable == null) {
         this.typeTable = new SymbolTable.Entry[16];
      }

      if (this.typeCount == this.typeTable.length) {
         SymbolTable.Entry[] newTypeTable = new SymbolTable.Entry[2 * this.typeTable.length];
         System.arraycopy(this.typeTable, 0, newTypeTable, 0, this.typeTable.length);
         this.typeTable = newTypeTable;
      }

      this.typeTable[this.typeCount++] = entry;
      return this.put(entry).index;
   }

   private static int hash(int tag, int value) {
      return Integer.MAX_VALUE & tag + value;
   }

   private static int hash(int tag, long value) {
      return Integer.MAX_VALUE & tag + (int)value + (int)(value >>> 32);
   }

   private static int hash(int tag, String value) {
      return Integer.MAX_VALUE & tag + value.hashCode();
   }

   private static int hash(int tag, String value1, int value2) {
      return Integer.MAX_VALUE & tag + value1.hashCode() + value2;
   }

   private static int hash(int tag, String value1, String value2) {
      return Integer.MAX_VALUE & tag + value1.hashCode() * value2.hashCode();
   }

   private static int hash(int tag, String value1, String value2, int value3) {
      return Integer.MAX_VALUE & tag + value1.hashCode() * value2.hashCode() * (value3 + 1);
   }

   private static int hash(int tag, String value1, String value2, String value3) {
      return Integer.MAX_VALUE & tag + value1.hashCode() * value2.hashCode() * value3.hashCode();
   }

   private static int hash(int tag, String value1, String value2, String value3, int value4) {
      return Integer.MAX_VALUE & tag + value1.hashCode() * value2.hashCode() * value3.hashCode() * value4;
   }

   private static class Entry extends Symbol {
      final int hashCode;
      SymbolTable.Entry next;

      Entry(int index, int tag, String owner, String name, String value, long data, int hashCode) {
         super(index, tag, owner, name, value, data);
         this.hashCode = hashCode;
      }

      Entry(int index, int tag, String value, int hashCode) {
         super(index, tag, (String)null, (String)null, value, 0L);
         this.hashCode = hashCode;
      }

      Entry(int index, int tag, String value, long data, int hashCode) {
         super(index, tag, (String)null, (String)null, value, data);
         this.hashCode = hashCode;
      }

      Entry(int index, int tag, String name, String value, int hashCode) {
         super(index, tag, (String)null, name, value, 0L);
         this.hashCode = hashCode;
      }

      Entry(int index, int tag, long data, int hashCode) {
         super(index, tag, (String)null, (String)null, (String)null, data);
         this.hashCode = hashCode;
      }
   }
}
