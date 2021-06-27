package org.objectweb.asm;

class Frame {
   static final int SAME_FRAME = 0;
   static final int SAME_LOCALS_1_STACK_ITEM_FRAME = 64;
   static final int RESERVED = 128;
   static final int SAME_LOCALS_1_STACK_ITEM_FRAME_EXTENDED = 247;
   static final int CHOP_FRAME = 248;
   static final int SAME_FRAME_EXTENDED = 251;
   static final int APPEND_FRAME = 252;
   static final int FULL_FRAME = 255;
   static final int ITEM_TOP = 0;
   static final int ITEM_INTEGER = 1;
   static final int ITEM_FLOAT = 2;
   static final int ITEM_DOUBLE = 3;
   static final int ITEM_LONG = 4;
   static final int ITEM_NULL = 5;
   static final int ITEM_UNINITIALIZED_THIS = 6;
   static final int ITEM_OBJECT = 7;
   static final int ITEM_UNINITIALIZED = 8;
   private static final int ITEM_ASM_BOOLEAN = 9;
   private static final int ITEM_ASM_BYTE = 10;
   private static final int ITEM_ASM_CHAR = 11;
   private static final int ITEM_ASM_SHORT = 12;
   private static final int DIM_SIZE = 6;
   private static final int KIND_SIZE = 4;
   private static final int FLAGS_SIZE = 2;
   private static final int VALUE_SIZE = 20;
   private static final int DIM_SHIFT = 26;
   private static final int KIND_SHIFT = 22;
   private static final int FLAGS_SHIFT = 20;
   private static final int DIM_MASK = -67108864;
   private static final int KIND_MASK = 62914560;
   private static final int VALUE_MASK = 1048575;
   private static final int ARRAY_OF = 67108864;
   private static final int ELEMENT_OF = -67108864;
   private static final int CONSTANT_KIND = 4194304;
   private static final int REFERENCE_KIND = 8388608;
   private static final int UNINITIALIZED_KIND = 12582912;
   private static final int LOCAL_KIND = 16777216;
   private static final int STACK_KIND = 20971520;
   private static final int TOP_IF_LONG_OR_DOUBLE_FLAG = 1048576;
   private static final int TOP = 4194304;
   private static final int BOOLEAN = 4194313;
   private static final int BYTE = 4194314;
   private static final int CHAR = 4194315;
   private static final int SHORT = 4194316;
   private static final int INTEGER = 4194305;
   private static final int FLOAT = 4194306;
   private static final int LONG = 4194308;
   private static final int DOUBLE = 4194307;
   private static final int NULL = 4194309;
   private static final int UNINITIALIZED_THIS = 4194310;
   Label owner;
   private int[] inputLocals;
   private int[] inputStack;
   private int[] outputLocals;
   private int[] outputStack;
   private short outputStackStart;
   private short outputStackTop;
   private int initializationCount;
   private int[] initializations;

   Frame(Label owner) {
      this.owner = owner;
   }

   final void copyFrom(Frame frame) {
      this.inputLocals = frame.inputLocals;
      this.inputStack = frame.inputStack;
      this.outputStackStart = 0;
      this.outputLocals = frame.outputLocals;
      this.outputStack = frame.outputStack;
      this.outputStackTop = frame.outputStackTop;
      this.initializationCount = frame.initializationCount;
      this.initializations = frame.initializations;
   }

   static int getAbstractTypeFromApiFormat(SymbolTable symbolTable, Object type) {
      if (type instanceof Integer) {
         return 4194304 | (Integer)type;
      } else if (type instanceof String) {
         String descriptor = Type.getObjectType((String)type).getDescriptor();
         return getAbstractTypeFromDescriptor(symbolTable, descriptor, 0);
      } else {
         return 12582912 | symbolTable.addUninitializedType("", ((Label)type).bytecodeOffset);
      }
   }

   static int getAbstractTypeFromInternalName(SymbolTable symbolTable, String internalName) {
      return 8388608 | symbolTable.addType(internalName);
   }

   private static int getAbstractTypeFromDescriptor(SymbolTable symbolTable, String buffer, int offset) {
      String internalName;
      switch(buffer.charAt(offset)) {
      case 'B':
      case 'C':
      case 'I':
      case 'S':
      case 'Z':
         return 4194305;
      case 'D':
         return 4194307;
      case 'E':
      case 'G':
      case 'H':
      case 'K':
      case 'M':
      case 'N':
      case 'O':
      case 'P':
      case 'Q':
      case 'R':
      case 'T':
      case 'U':
      case 'W':
      case 'X':
      case 'Y':
      default:
         throw new IllegalArgumentException();
      case 'F':
         return 4194306;
      case 'J':
         return 4194308;
      case 'L':
         internalName = buffer.substring(offset + 1, buffer.length() - 1);
         return 8388608 | symbolTable.addType(internalName);
      case 'V':
         return 0;
      case '[':
         int elementDescriptorOffset;
         for(elementDescriptorOffset = offset + 1; buffer.charAt(elementDescriptorOffset) == '['; ++elementDescriptorOffset) {
         }

         int typeValue;
         switch(buffer.charAt(elementDescriptorOffset)) {
         case 'B':
            typeValue = 4194314;
            break;
         case 'C':
            typeValue = 4194315;
            break;
         case 'D':
            typeValue = 4194307;
            break;
         case 'F':
            typeValue = 4194306;
            break;
         case 'I':
            typeValue = 4194305;
            break;
         case 'J':
            typeValue = 4194308;
            break;
         case 'L':
            internalName = buffer.substring(elementDescriptorOffset + 1, buffer.length() - 1);
            typeValue = 8388608 | symbolTable.addType(internalName);
            break;
         case 'S':
            typeValue = 4194316;
            break;
         case 'Z':
            typeValue = 4194313;
            break;
         default:
            throw new IllegalArgumentException();
         }

         return elementDescriptorOffset - offset << 26 | typeValue;
      }
   }

   final void setInputFrameFromDescriptor(SymbolTable symbolTable, int access, String descriptor, int maxLocals) {
      this.inputLocals = new int[maxLocals];
      this.inputStack = new int[0];
      int inputLocalIndex = 0;
      if ((access & 8) == 0) {
         if ((access & 262144) == 0) {
            this.inputLocals[inputLocalIndex++] = 8388608 | symbolTable.addType(symbolTable.getClassName());
         } else {
            this.inputLocals[inputLocalIndex++] = 4194310;
         }
      }

      Type[] var9;
      int var8 = (var9 = Type.getArgumentTypes(descriptor)).length;

      for(int var7 = 0; var7 < var8; ++var7) {
         Type argumentType = var9[var7];
         int abstractType = getAbstractTypeFromDescriptor(symbolTable, argumentType.getDescriptor(), 0);
         this.inputLocals[inputLocalIndex++] = abstractType;
         if (abstractType == 4194308 || abstractType == 4194307) {
            this.inputLocals[inputLocalIndex++] = 4194304;
         }
      }

      while(inputLocalIndex < maxLocals) {
         this.inputLocals[inputLocalIndex++] = 4194304;
      }

   }

   final void setInputFrameFromApiFormat(SymbolTable symbolTable, int numLocal, Object[] local, int numStack, Object[] stack) {
      int inputLocalIndex = 0;

      int numStackTop;
      for(numStackTop = 0; numStackTop < numLocal; ++numStackTop) {
         this.inputLocals[inputLocalIndex++] = getAbstractTypeFromApiFormat(symbolTable, local[numStackTop]);
         if (local[numStackTop] == Opcodes.LONG || local[numStackTop] == Opcodes.DOUBLE) {
            this.inputLocals[inputLocalIndex++] = 4194304;
         }
      }

      while(inputLocalIndex < this.inputLocals.length) {
         this.inputLocals[inputLocalIndex++] = 4194304;
      }

      numStackTop = 0;

      for(int i = 0; i < numStack; ++i) {
         if (stack[i] == Opcodes.LONG || stack[i] == Opcodes.DOUBLE) {
            ++numStackTop;
         }
      }

      this.inputStack = new int[numStack + numStackTop];

      for(int i = 0; i < numStack; ++i) {
         this.inputStack[i++] = getAbstractTypeFromApiFormat(symbolTable, stack[i]);
         if (stack[i] == Opcodes.LONG || stack[i] == Opcodes.DOUBLE) {
            this.inputStack[i++] = 4194304;
         }
      }

      this.outputStackTop = 0;
      this.initializationCount = 0;
   }

   final int getInputStackSize() {
      return this.inputStack.length;
   }

   private int getLocal(int localIndex) {
      if (this.outputLocals != null && localIndex < this.outputLocals.length) {
         int abstractType = this.outputLocals[localIndex];
         if (abstractType == 0) {
            abstractType = this.outputLocals[localIndex] = 16777216 | localIndex;
         }

         return abstractType;
      } else {
         return 16777216 | localIndex;
      }
   }

   private void setLocal(int localIndex, int abstractType) {
      if (this.outputLocals == null) {
         this.outputLocals = new int[10];
      }

      int outputLocalsLength = this.outputLocals.length;
      if (localIndex >= outputLocalsLength) {
         int[] newOutputLocals = new int[Math.max(localIndex + 1, 2 * outputLocalsLength)];
         System.arraycopy(this.outputLocals, 0, newOutputLocals, 0, outputLocalsLength);
         this.outputLocals = newOutputLocals;
      }

      this.outputLocals[localIndex] = abstractType;
   }

   private void push(int abstractType) {
      if (this.outputStack == null) {
         this.outputStack = new int[10];
      }

      int outputStackLength = this.outputStack.length;
      if (this.outputStackTop >= outputStackLength) {
         int[] newOutputStack = new int[Math.max(this.outputStackTop + 1, 2 * outputStackLength)];
         System.arraycopy(this.outputStack, 0, newOutputStack, 0, outputStackLength);
         this.outputStack = newOutputStack;
      }

      int[] var10000 = this.outputStack;
      short var10003 = this.outputStackTop;
      this.outputStackTop = (short)(var10003 + 1);
      var10000[var10003] = abstractType;
      short outputStackSize = (short)(this.outputStackStart + this.outputStackTop);
      if (outputStackSize > this.owner.outputStackMax) {
         this.owner.outputStackMax = outputStackSize;
      }

   }

   private void push(SymbolTable symbolTable, String descriptor) {
      int typeDescriptorOffset = descriptor.charAt(0) == '(' ? Type.getReturnTypeOffset(descriptor) : 0;
      int abstractType = getAbstractTypeFromDescriptor(symbolTable, descriptor, typeDescriptorOffset);
      if (abstractType != 0) {
         this.push(abstractType);
         if (abstractType == 4194308 || abstractType == 4194307) {
            this.push(4194304);
         }
      }

   }

   private int pop() {
      return this.outputStackTop > 0 ? this.outputStack[--this.outputStackTop] : 20971520 | -(--this.outputStackStart);
   }

   private void pop(int elements) {
      if (this.outputStackTop >= elements) {
         this.outputStackTop = (short)(this.outputStackTop - elements);
      } else {
         this.outputStackStart = (short)(this.outputStackStart - (elements - this.outputStackTop));
         this.outputStackTop = 0;
      }

   }

   private void pop(String descriptor) {
      char firstDescriptorChar = descriptor.charAt(0);
      if (firstDescriptorChar == '(') {
         this.pop((Type.getArgumentsAndReturnSizes(descriptor) >> 2) - 1);
      } else if (firstDescriptorChar != 'J' && firstDescriptorChar != 'D') {
         this.pop(1);
      } else {
         this.pop(2);
      }

   }

   private void addInitializedType(int abstractType) {
      if (this.initializations == null) {
         this.initializations = new int[2];
      }

      int initializationsLength = this.initializations.length;
      if (this.initializationCount >= initializationsLength) {
         int[] newInitializations = new int[Math.max(this.initializationCount + 1, 2 * initializationsLength)];
         System.arraycopy(this.initializations, 0, newInitializations, 0, initializationsLength);
         this.initializations = newInitializations;
      }

      this.initializations[this.initializationCount++] = abstractType;
   }

   private int getInitializedType(SymbolTable symbolTable, int abstractType) {
      if (abstractType == 4194310 || (abstractType & -4194304) == 12582912) {
         for(int i = 0; i < this.initializationCount; ++i) {
            int initializedType = this.initializations[i];
            int dim = initializedType & -67108864;
            int kind = initializedType & 62914560;
            int value = initializedType & 1048575;
            if (kind == 16777216) {
               initializedType = dim + this.inputLocals[value];
            } else if (kind == 20971520) {
               initializedType = dim + this.inputStack[this.inputStack.length - value];
            }

            if (abstractType == initializedType) {
               if (abstractType == 4194310) {
                  return 8388608 | symbolTable.addType(symbolTable.getClassName());
               }

               return 8388608 | symbolTable.addType(symbolTable.getType(abstractType & 1048575).value);
            }
         }
      }

      return abstractType;
   }

   void execute(int opcode, int arg, Symbol argSymbol, SymbolTable symbolTable) {
      int abstractType1;
      int abstractType2;
      int abstractType3;
      int previousLocalType;
      switch(opcode) {
      case 0:
      case 116:
      case 117:
      case 118:
      case 119:
      case 145:
      case 146:
      case 147:
      case 167:
      case 177:
         break;
      case 1:
         this.push(4194309);
         break;
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 16:
      case 17:
      case 21:
         this.push(4194305);
         break;
      case 9:
      case 10:
      case 22:
         this.push(4194308);
         this.push(4194304);
         break;
      case 11:
      case 12:
      case 13:
      case 23:
         this.push(4194306);
         break;
      case 14:
      case 15:
      case 24:
         this.push(4194307);
         this.push(4194304);
         break;
      case 18:
         switch(argSymbol.tag) {
         case 3:
            this.push(4194305);
            return;
         case 4:
            this.push(4194306);
            return;
         case 5:
            this.push(4194308);
            this.push(4194304);
            return;
         case 6:
            this.push(4194307);
            this.push(4194304);
            return;
         case 7:
            this.push(8388608 | symbolTable.addType("java/lang/Class"));
            return;
         case 8:
            this.push(8388608 | symbolTable.addType("java/lang/String"));
            return;
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         default:
            throw new AssertionError();
         case 15:
            this.push(8388608 | symbolTable.addType("java/lang/invoke/MethodHandle"));
            return;
         case 16:
            this.push(8388608 | symbolTable.addType("java/lang/invoke/MethodType"));
            return;
         case 17:
            this.push(symbolTable, argSymbol.value);
            return;
         }
      case 19:
      case 20:
      case 26:
      case 27:
      case 28:
      case 29:
      case 30:
      case 31:
      case 32:
      case 33:
      case 34:
      case 35:
      case 36:
      case 37:
      case 38:
      case 39:
      case 40:
      case 41:
      case 42:
      case 43:
      case 44:
      case 45:
      case 59:
      case 60:
      case 61:
      case 62:
      case 63:
      case 64:
      case 65:
      case 66:
      case 67:
      case 68:
      case 69:
      case 70:
      case 71:
      case 72:
      case 73:
      case 74:
      case 75:
      case 76:
      case 77:
      case 78:
      case 196:
      default:
         throw new IllegalArgumentException();
      case 25:
         this.push(this.getLocal(arg));
         break;
      case 46:
      case 51:
      case 52:
      case 53:
      case 96:
      case 100:
      case 104:
      case 108:
      case 112:
      case 120:
      case 122:
      case 124:
      case 126:
      case 128:
      case 130:
      case 136:
      case 142:
      case 149:
      case 150:
         this.pop(2);
         this.push(4194305);
         break;
      case 47:
      case 143:
         this.pop(2);
         this.push(4194308);
         this.push(4194304);
         break;
      case 48:
      case 98:
      case 102:
      case 106:
      case 110:
      case 114:
      case 137:
      case 144:
         this.pop(2);
         this.push(4194306);
         break;
      case 49:
      case 138:
         this.pop(2);
         this.push(4194307);
         this.push(4194304);
         break;
      case 50:
         this.pop(1);
         abstractType1 = this.pop();
         this.push(abstractType1 == 4194309 ? abstractType1 : -67108864 + abstractType1);
         break;
      case 54:
      case 56:
      case 58:
         abstractType1 = this.pop();
         this.setLocal(arg, abstractType1);
         if (arg > 0) {
            previousLocalType = this.getLocal(arg - 1);
            if (previousLocalType != 4194308 && previousLocalType != 4194307) {
               if ((previousLocalType & 62914560) == 16777216 || (previousLocalType & 62914560) == 20971520) {
                  this.setLocal(arg - 1, previousLocalType | 1048576);
               }
            } else {
               this.setLocal(arg - 1, 4194304);
            }
         }
         break;
      case 55:
      case 57:
         this.pop(1);
         abstractType1 = this.pop();
         this.setLocal(arg, abstractType1);
         this.setLocal(arg + 1, 4194304);
         if (arg > 0) {
            previousLocalType = this.getLocal(arg - 1);
            if (previousLocalType != 4194308 && previousLocalType != 4194307) {
               if ((previousLocalType & 62914560) == 16777216 || (previousLocalType & 62914560) == 20971520) {
                  this.setLocal(arg - 1, previousLocalType | 1048576);
               }
            } else {
               this.setLocal(arg - 1, 4194304);
            }
         }
         break;
      case 79:
      case 81:
      case 83:
      case 84:
      case 85:
      case 86:
         this.pop(3);
         break;
      case 80:
      case 82:
         this.pop(4);
         break;
      case 87:
      case 153:
      case 154:
      case 155:
      case 156:
      case 157:
      case 158:
      case 170:
      case 171:
      case 172:
      case 174:
      case 176:
      case 191:
      case 194:
      case 195:
      case 198:
      case 199:
         this.pop(1);
         break;
      case 88:
      case 159:
      case 160:
      case 161:
      case 162:
      case 163:
      case 164:
      case 165:
      case 166:
      case 173:
      case 175:
         this.pop(2);
         break;
      case 89:
         abstractType1 = this.pop();
         this.push(abstractType1);
         this.push(abstractType1);
         break;
      case 90:
         abstractType1 = this.pop();
         abstractType2 = this.pop();
         this.push(abstractType1);
         this.push(abstractType2);
         this.push(abstractType1);
         break;
      case 91:
         abstractType1 = this.pop();
         abstractType2 = this.pop();
         abstractType3 = this.pop();
         this.push(abstractType1);
         this.push(abstractType3);
         this.push(abstractType2);
         this.push(abstractType1);
         break;
      case 92:
         abstractType1 = this.pop();
         abstractType2 = this.pop();
         this.push(abstractType2);
         this.push(abstractType1);
         this.push(abstractType2);
         this.push(abstractType1);
         break;
      case 93:
         abstractType1 = this.pop();
         abstractType2 = this.pop();
         abstractType3 = this.pop();
         this.push(abstractType2);
         this.push(abstractType1);
         this.push(abstractType3);
         this.push(abstractType2);
         this.push(abstractType1);
         break;
      case 94:
         abstractType1 = this.pop();
         abstractType2 = this.pop();
         abstractType3 = this.pop();
         int abstractType4 = this.pop();
         this.push(abstractType2);
         this.push(abstractType1);
         this.push(abstractType4);
         this.push(abstractType3);
         this.push(abstractType2);
         this.push(abstractType1);
         break;
      case 95:
         abstractType1 = this.pop();
         abstractType2 = this.pop();
         this.push(abstractType1);
         this.push(abstractType2);
         break;
      case 97:
      case 101:
      case 105:
      case 109:
      case 113:
      case 127:
      case 129:
      case 131:
         this.pop(4);
         this.push(4194308);
         this.push(4194304);
         break;
      case 99:
      case 103:
      case 107:
      case 111:
      case 115:
         this.pop(4);
         this.push(4194307);
         this.push(4194304);
         break;
      case 121:
      case 123:
      case 125:
         this.pop(3);
         this.push(4194308);
         this.push(4194304);
         break;
      case 132:
         this.setLocal(arg, 4194305);
         break;
      case 133:
      case 140:
         this.pop(1);
         this.push(4194308);
         this.push(4194304);
         break;
      case 134:
         this.pop(1);
         this.push(4194306);
         break;
      case 135:
      case 141:
         this.pop(1);
         this.push(4194307);
         this.push(4194304);
         break;
      case 139:
      case 190:
      case 193:
         this.pop(1);
         this.push(4194305);
         break;
      case 148:
      case 151:
      case 152:
         this.pop(4);
         this.push(4194305);
         break;
      case 168:
      case 169:
         throw new IllegalArgumentException("JSR/RET are not supported with computeFrames option");
      case 178:
         this.push(symbolTable, argSymbol.value);
         break;
      case 179:
         this.pop(argSymbol.value);
         break;
      case 180:
         this.pop(1);
         this.push(symbolTable, argSymbol.value);
         break;
      case 181:
         this.pop(argSymbol.value);
         this.pop();
         break;
      case 182:
      case 183:
      case 184:
      case 185:
         this.pop(argSymbol.value);
         if (opcode != 184) {
            abstractType1 = this.pop();
            if (opcode == 183 && argSymbol.name.charAt(0) == '<') {
               this.addInitializedType(abstractType1);
            }
         }

         this.push(symbolTable, argSymbol.value);
         break;
      case 186:
         this.pop(argSymbol.value);
         this.push(symbolTable, argSymbol.value);
         break;
      case 187:
         this.push(12582912 | symbolTable.addUninitializedType(argSymbol.value, arg));
         break;
      case 188:
         this.pop();
         switch(arg) {
         case 4:
            this.push(71303177);
            return;
         case 5:
            this.push(71303179);
            return;
         case 6:
            this.push(71303170);
            return;
         case 7:
            this.push(71303171);
            return;
         case 8:
            this.push(71303178);
            return;
         case 9:
            this.push(71303180);
            return;
         case 10:
            this.push(71303169);
            return;
         case 11:
            this.push(71303172);
            return;
         default:
            throw new IllegalArgumentException();
         }
      case 189:
         String arrayElementType = argSymbol.value;
         this.pop();
         if (arrayElementType.charAt(0) == '[') {
            this.push(symbolTable, '[' + arrayElementType);
         } else {
            this.push(75497472 | symbolTable.addType(arrayElementType));
         }
         break;
      case 192:
         String castType = argSymbol.value;
         this.pop();
         if (castType.charAt(0) == '[') {
            this.push(symbolTable, castType);
         } else {
            this.push(8388608 | symbolTable.addType(castType));
         }
         break;
      case 197:
         this.pop(arg);
         this.push(symbolTable, argSymbol.value);
      }

   }

   private int getConcreteOutputType(int abstractOutputType, int numStack) {
      int dim = abstractOutputType & -67108864;
      int kind = abstractOutputType & 62914560;
      int concreteOutputType;
      if (kind == 16777216) {
         concreteOutputType = dim + this.inputLocals[abstractOutputType & 1048575];
         if ((abstractOutputType & 1048576) != 0 && (concreteOutputType == 4194308 || concreteOutputType == 4194307)) {
            concreteOutputType = 4194304;
         }

         return concreteOutputType;
      } else if (kind != 20971520) {
         return abstractOutputType;
      } else {
         concreteOutputType = dim + this.inputStack[numStack - (abstractOutputType & 1048575)];
         if ((abstractOutputType & 1048576) != 0 && (concreteOutputType == 4194308 || concreteOutputType == 4194307)) {
            concreteOutputType = 4194304;
         }

         return concreteOutputType;
      }
   }

   final boolean merge(SymbolTable symbolTable, Frame dstFrame, int catchTypeIndex) {
      boolean frameChanged = false;
      int numLocal = this.inputLocals.length;
      int numStack = this.inputStack.length;
      if (dstFrame.inputLocals == null) {
         dstFrame.inputLocals = new int[numLocal];
         frameChanged = true;
      }

      int numInputStack;
      int i;
      int abstractOutputType;
      for(numInputStack = 0; numInputStack < numLocal; ++numInputStack) {
         if (this.outputLocals != null && numInputStack < this.outputLocals.length) {
            abstractOutputType = this.outputLocals[numInputStack];
            if (abstractOutputType == 0) {
               i = this.inputLocals[numInputStack];
            } else {
               i = this.getConcreteOutputType(abstractOutputType, numStack);
            }
         } else {
            i = this.inputLocals[numInputStack];
         }

         if (this.initializations != null) {
            i = this.getInitializedType(symbolTable, i);
         }

         frameChanged |= merge(symbolTable, i, dstFrame.inputLocals, numInputStack);
      }

      if (catchTypeIndex <= 0) {
         numInputStack = this.inputStack.length + this.outputStackStart;
         if (dstFrame.inputStack == null) {
            dstFrame.inputStack = new int[numInputStack + this.outputStackTop];
            frameChanged = true;
         }

         for(i = 0; i < numInputStack; ++i) {
            abstractOutputType = this.inputStack[i];
            if (this.initializations != null) {
               abstractOutputType = this.getInitializedType(symbolTable, abstractOutputType);
            }

            frameChanged |= merge(symbolTable, abstractOutputType, dstFrame.inputStack, i);
         }

         for(i = 0; i < this.outputStackTop; ++i) {
            abstractOutputType = this.outputStack[i];
            int concreteOutputType = this.getConcreteOutputType(abstractOutputType, numStack);
            if (this.initializations != null) {
               concreteOutputType = this.getInitializedType(symbolTable, concreteOutputType);
            }

            frameChanged |= merge(symbolTable, concreteOutputType, dstFrame.inputStack, numInputStack + i);
         }

         return frameChanged;
      } else {
         for(numInputStack = 0; numInputStack < numLocal; ++numInputStack) {
            frameChanged |= merge(symbolTable, this.inputLocals[numInputStack], dstFrame.inputLocals, numInputStack);
         }

         if (dstFrame.inputStack == null) {
            dstFrame.inputStack = new int[1];
            frameChanged = true;
         }

         frameChanged |= merge(symbolTable, catchTypeIndex, dstFrame.inputStack, 0);
         return frameChanged;
      }
   }

   private static boolean merge(SymbolTable symbolTable, int sourceType, int[] dstTypes, int dstIndex) {
      int dstType = dstTypes[dstIndex];
      if (dstType == sourceType) {
         return false;
      } else {
         int srcType = sourceType;
         if ((sourceType & 67108863) == 4194309) {
            if (dstType == 4194309) {
               return false;
            }

            srcType = 4194309;
         }

         if (dstType == 0) {
            dstTypes[dstIndex] = srcType;
            return true;
         } else {
            int mergedType;
            if ((dstType & -67108864) == 0 && (dstType & 62914560) != 8388608) {
               if (dstType == 4194309) {
                  mergedType = (srcType & -67108864) == 0 && (srcType & 62914560) != 8388608 ? 4194304 : srcType;
               } else {
                  mergedType = 4194304;
               }
            } else {
               if (srcType == 4194309) {
                  return false;
               }

               int srcDim;
               if ((srcType & -4194304) == (dstType & -4194304)) {
                  if ((dstType & 62914560) == 8388608) {
                     mergedType = srcType & -67108864 | 8388608 | symbolTable.addMergedType(srcType & 1048575, dstType & 1048575);
                  } else {
                     srcDim = -67108864 + (srcType & -67108864);
                     mergedType = srcDim | 8388608 | symbolTable.addType("java/lang/Object");
                  }
               } else if ((srcType & -67108864) == 0 && (srcType & 62914560) != 8388608) {
                  mergedType = 4194304;
               } else {
                  srcDim = srcType & -67108864;
                  if (srcDim != 0 && (srcType & 62914560) != 8388608) {
                     srcDim += -67108864;
                  }

                  int dstDim = dstType & -67108864;
                  if (dstDim != 0 && (dstType & 62914560) != 8388608) {
                     dstDim += -67108864;
                  }

                  mergedType = Math.min(srcDim, dstDim) | 8388608 | symbolTable.addType("java/lang/Object");
               }
            }

            if (mergedType != dstType) {
               dstTypes[dstIndex] = mergedType;
               return true;
            } else {
               return false;
            }
         }
      }
   }

   final void accept(MethodWriter methodWriter) {
      int[] localTypes = this.inputLocals;
      int numLocal = 0;
      int numTrailingTop = 0;
      int i = 0;

      while(i < localTypes.length) {
         int localType = localTypes[i];
         i += localType != 4194308 && localType != 4194307 ? 1 : 2;
         if (localType == 4194304) {
            ++numTrailingTop;
         } else {
            numLocal += numTrailingTop + 1;
            numTrailingTop = 0;
         }
      }

      int[] stackTypes = this.inputStack;
      int numStack = 0;

      int frameIndex;
      for(i = 0; i < stackTypes.length; ++numStack) {
         frameIndex = stackTypes[i];
         i += frameIndex != 4194308 && frameIndex != 4194307 ? 1 : 2;
      }

      frameIndex = methodWriter.visitFrameStart(this.owner.bytecodeOffset, numLocal, numStack);
      i = 0;

      int stackType;
      while(numLocal-- > 0) {
         stackType = localTypes[i];
         i += stackType != 4194308 && stackType != 4194307 ? 1 : 2;
         methodWriter.visitAbstractType(frameIndex++, stackType);
      }

      i = 0;

      while(numStack-- > 0) {
         stackType = stackTypes[i];
         i += stackType != 4194308 && stackType != 4194307 ? 1 : 2;
         methodWriter.visitAbstractType(frameIndex++, stackType);
      }

      methodWriter.visitFrameEnd();
   }

   static void putAbstractType(SymbolTable symbolTable, int abstractType, ByteVector output) {
      int arrayDimensions = (abstractType & -67108864) >> 26;
      if (arrayDimensions == 0) {
         int typeValue = abstractType & 1048575;
         switch(abstractType & 62914560) {
         case 4194304:
            output.putByte(typeValue);
            break;
         case 8388608:
            output.putByte(7).putShort(symbolTable.addConstantClass(symbolTable.getType(typeValue).value).index);
            break;
         case 12582912:
            output.putByte(8).putShort((int)symbolTable.getType(typeValue).data);
            break;
         default:
            throw new AssertionError();
         }
      } else {
         StringBuilder typeDescriptor = new StringBuilder();

         while(arrayDimensions-- > 0) {
            typeDescriptor.append('[');
         }

         if ((abstractType & 62914560) == 8388608) {
            typeDescriptor.append('L').append(symbolTable.getType(abstractType & 1048575).value).append(';');
         } else {
            switch(abstractType & 1048575) {
            case 1:
               typeDescriptor.append('I');
               break;
            case 2:
               typeDescriptor.append('F');
               break;
            case 3:
               typeDescriptor.append('D');
               break;
            case 4:
               typeDescriptor.append('J');
               break;
            case 5:
            case 6:
            case 7:
            case 8:
            default:
               throw new AssertionError();
            case 9:
               typeDescriptor.append('Z');
               break;
            case 10:
               typeDescriptor.append('B');
               break;
            case 11:
               typeDescriptor.append('C');
               break;
            case 12:
               typeDescriptor.append('S');
            }
         }

         output.putByte(7).putShort(symbolTable.addConstantClass(typeDescriptor.toString()).index);
      }

   }
}
