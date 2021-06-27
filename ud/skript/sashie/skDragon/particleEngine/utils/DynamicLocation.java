package ud.skript.sashie.skDragon.particleEngine.utils;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class DynamicLocation extends Location implements ConfigurationSerializable {
   private Entity entity = null;
   private boolean dynamic = false;
   private float epsilon = 0.05F;
   private float epsilonNoSneak = 0.1F;
   private Location lastLocation;
   private boolean isFalling;
   private boolean isMoving;
   private float movementYaw = 0.0F;

   public DynamicLocation(Location location) {
      super(location.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
   }

   public DynamicLocation(Entity entity) {
      super(entity.getWorld(), entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ(), entity.getLocation().getYaw(), entity.getLocation().getPitch());
      this.entity = entity;
      this.dynamic = true;
   }

   public DynamicLocation update() {
      if (this.dynamic) {
         this.setX(this.entity.getLocation().getX());
         this.setY(this.entity.getLocation().getY());
         this.setZ(this.entity.getLocation().getZ());
         this.setYaw(this.entity.getLocation().getYaw());
         this.setPitch(this.entity.getLocation().getPitch());
      }

      return this;
   }

   public void movementCheck() {
      if (this.dynamic) {
         Location current = this.entity.getLocation();
         Location last = this.lastLocation;
         if (this.lastLocation != null) {
            last = this.lastLocation;
         } else {
            this.lastLocation = current;
            last = this.lastLocation;
         }

         if (current.getWorld() == last.getWorld()) {
            this.lastLocation = this.entity.getLocation();
            if (last.getY() > current.getY()) {
               if (last.distanceSquared(current) > (double)(this.epsilon * this.epsilon) && !this.isFalling) {
                  this.isFalling = true;
               }
            } else {
               this.isFalling = false;
            }

            if (last.getX() == current.getX() && last.getZ() == current.getZ()) {
               this.isMoving = false;
            } else {
               Vector direction = last.toVector().subtract(current.toVector()).normalize();
               double theta = Math.atan2(-direction.getX(), direction.getZ());
               this.movementYaw = (float)Math.toDegrees((theta + 6.283185307179586D) % 6.283185307179586D);
               if (last.distanceSquared(current) > (double)(this.epsilonNoSneak * this.epsilonNoSneak) && !this.isMoving) {
                  this.isMoving = true;
               }
            }
         }
      }

   }

   public boolean isMoving() {
      return this.isMoving;
   }

   public boolean isFalling() {
      return this.isFalling;
   }

   public float getMovementYaw() {
      return this.movementYaw;
   }

   public boolean needsUpdate() {
      return this.getWorld() == this.entity.getWorld() && this.dynamic && this.distanceSquared(this.entity.getLocation()) > (double)(this.epsilon * this.epsilon);
   }

   public boolean needsUpdate(long ticks) {
      return this.getWorld() == this.entity.getWorld() && this.dynamic && this.distanceSquared(this.entity.getLocation()) > (double)(this.epsilon * this.epsilon / (float)(ticks > 0L ? ticks : 1L));
   }

   public boolean isDynamic() {
      return this.dynamic;
   }

   public float getEpsilon() {
      return this.epsilon;
   }

   public void setEpsilon(float epsilon) {
      this.epsilon = epsilon;
   }

   public Entity getEntity() {
      return this.entity;
   }

   public Vector getVector() {
      return new Vector(this.getX(), this.getY(), this.getZ());
   }

   public DynamicLocation add(Vector vec) {
      this.setX(this.getX() + vec.getX());
      this.setY(this.getY() + vec.getY());
      this.setZ(this.getZ() + vec.getZ());
      return this;
   }

   public DynamicLocation add(double x, double y, double z) {
      this.setX(this.getX() + x);
      this.setY(this.getY() + y);
      this.setZ(this.getZ() + z);
      return this;
   }

   public DynamicLocation subtract(Vector vec) {
      this.setX(this.getX() - vec.getX());
      this.setY(this.getY() - vec.getY());
      this.setZ(this.getZ() - vec.getZ());
      return this;
   }

   public DynamicLocation subtract(double x, double y, double z) {
      this.setX(this.getX() - x);
      this.setY(this.getY() - y);
      this.setZ(this.getZ() - z);
      return this;
   }

   public DynamicLocation multiply(double m) {
      this.setX(this.getX() * m);
      this.setY(this.getY() * m);
      this.setZ(this.getZ() * m);
      return this;
   }

   public DynamicLocation clone() {
      return new DynamicLocation(this);
   }

   public static DynamicLocation init(Object center) {
      if (center instanceof Entity) {
         return new DynamicLocation((Entity)center);
      } else if (center instanceof Location) {
         return new DynamicLocation((Location)center);
      } else {
         throw new IllegalArgumentException("[skDragon] The object is not of type Entity or Location");
      }
   }
}
