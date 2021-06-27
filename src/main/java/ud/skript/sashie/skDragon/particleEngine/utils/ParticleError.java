package ud.skript.sashie.skDragon.particleEngine.utils;

import ud.skript.sashie.skDragonCore;

public class ParticleError extends RuntimeException {
   public ParticleError(String error) {
      super(null, null, true, false);
      skDragonCore.error(error);
   }
}
