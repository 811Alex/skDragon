package ud.skript.sashie.skDragon.particleEngine.utils;

import java.awt.Color;
import org.bukkit.util.Vector;

public class ColoredVector extends Vector {
   private Vector vector;
   private Color color;

   public ColoredVector(Vector vector, Color color) {
      this.setPoint(vector);
      this.setColor(color);
   }

   public ColoredVector(ColoredVector coloredVector) {
      this(coloredVector.getPoint(), coloredVector.getColor());
   }

   public static ColoredVector createFromRGB(Vector vector, Vector color) {
      int r = (int)color.getX();
      int g = (int)color.getY();
      int b = (int)color.getZ();
      return new ColoredVector(vector, new Color(r, g, b));
   }

   public static ColoredVector createFromHSB(Vector vector, Vector color) {
      float h = (float)color.getX();
      float s = (float)color.getY();
      float b = (float)color.getZ();
      return new ColoredVector(vector, Color.getHSBColor(h, s, b));
   }

   public Vector getPoint() {
      return this.vector.clone();
   }

   public Color getColor() {
      return this.color;
   }

   public Vector getColorAsVectorRGB() {
      return new Vector(this.color.getRed(), this.color.getGreen(), this.color.getBlue());
   }

   public void setPoint(Vector point) {
      this.vector = point.clone();
   }

   public void setColor(Color color) {
      this.color = new Color(color.getRGB());
   }

   public void setColor(Vector color) {
      int r = (int)color.getX();
      int g = (int)color.getY();
      int b = (int)color.getZ();
      this.color = new Color(r, g, b);
   }

   public ColoredVector clone() {
      return new ColoredVector(this);
   }
}
