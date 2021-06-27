package ud.skript.sashie.skDragon.dragontravel;

import eu.phiwa.dragontravel.core.DragonTravel;
import eu.phiwa.dragontravel.core.hooks.server.IRyeDragon;
import eu.phiwa.dragontravel.core.movement.flight.Flight;
import eu.phiwa.dragontravel.core.movement.stationary.StationaryDragon;
import eu.phiwa.dragontravel.core.movement.travel.Home;
import eu.phiwa.dragontravel.core.movement.travel.Station;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class DragonTravelHook {
   private File dbStationsFile;
   private FileConfiguration dbStationsConfig;
   public static final List stations = new ArrayList();
   private File dbHomesFile;
   private FileConfiguration dbHomesConfig;
   private File dbStatDragonsFile;
   private FileConfiguration dbStatDragonsConfig;
   private File dbFlightsFile;
   private FileConfiguration dbFlightsConfig;

   public void initStationDB() {
      this.dbStationsFile = new File("plugins/DragonTravel/databases", "stations.yml");
      this.dbStationsConfig = new YamlConfiguration();

      try {
         this.dbStationsConfig.load(this.dbStationsFile);
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   public void cacheStations() {
      Iterator var2 = this.dbStationsConfig.getConfigurationSection("Stations").getKeys(false).iterator();

      while(var2.hasNext()) {
         String name = (String)var2.next();
         Station station = DragonTravel.getInstance().getDbStationsHandler().getStation(name);
         stations.add(station);
      }

   }

   public List getStationNames() {
      List stations = new ArrayList();
      Iterator var3 = this.dbStationsConfig.getConfigurationSection("Stations").getKeys(false).iterator();

      while(var3.hasNext()) {
         String name = (String)var3.next();
         Station station = getStation(name);
         stations.add(station.getName());
      }

      return stations;
   }

   public static Station getStation(String name) {
      return DragonTravel.getInstance().getDbStationsHandler().getStation(name);
   }

   public static Location getStationLocation(String name) {
      return getStation(name).toLocation();
   }

   public static String getStationDisplayName(String name) {
      return getStation(name).getDisplayName();
   }

   public static String getStationOwner(String name) {
      return getStation(name).getOwner();
   }

   public void initHomesDB() {
      this.dbHomesFile = new File("plugins/DragonTravel/databases", "homes.yml");
      this.dbHomesConfig = new YamlConfiguration();

      try {
         this.dbHomesConfig.load(this.dbHomesFile);
         Bukkit.getLogger().log(Level.INFO, "[DragonTravel] Loaded homes-database.");
      } catch (Exception var2) {
         Bukkit.getLogger().log(Level.SEVERE, "[DragonTravel] No homes-database found");
         var2.printStackTrace();
      }

   }

   public List getAllHomes() {
      List homes = new ArrayList();
      Iterator var3 = this.dbHomesConfig.getConfigurationSection("Homes").getKeys(false).iterator();

      while(var3.hasNext()) {
         String name = (String)var3.next();
         Home home = DragonTravel.getInstance().getDbHomesHandler().getHome(name);
         if (home != null) {
            homes.add(home.playerName);
         }
      }

      return homes;
   }

   public static Location getHomeLocation(String name) {
      return DragonTravel.getInstance().getDbHomesHandler().getHome(name).toLocation();
   }

   public void initStatDragonDB() {
      this.dbStatDragonsFile = new File("plugins/DragonTravel/databases", "statdragons.yml");
      this.dbStatDragonsConfig = new YamlConfiguration();

      try {
         this.dbStatDragonsConfig.load(this.dbStatDragonsFile);
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   public List getStatDragonNames() {
      List statDragons = new ArrayList();
      Iterator var3 = this.dbStatDragonsConfig.getConfigurationSection("StatDragons").getKeys(false).iterator();

      while(var3.hasNext()) {
         String name = (String)var3.next();
         StationaryDragon sDragon = getStatDragon(name);
         statDragons.add(sDragon.getName());
      }

      return statDragons;
   }

   private static StationaryDragon getStatDragon(String name) {
      return DragonTravel.getInstance().getDragonManager().getStationaryDragons().get(name);
   }

   public static Location getStatDragonLocation(String name) {
      return new Location(Bukkit.getWorld(getStatDragon(name).getWorldName()), getStatDragon(name).getX(), getStatDragon(name).getY(), getStatDragon(name).getZ(), (float)getStatDragon(name).getYaw(), (float)getStatDragon(name).getPitch());
   }

   public static String getStatDragonDisplayName(String name) {
      return getStatDragon(name).getDisplayName();
   }

   public static String getStatDragonOwner(String name) {
      return getStatDragon(name).getOwner();
   }

   public static void setStatDragonDisplayName(String name, String display) {
      getStatDragon(name).setDisplayName(display);
   }

   public static IRyeDragon getStatDragonInfo(String name) {
      return getStatDragon(name).getDragon();
   }

   public void initFlightDB() {
      this.dbFlightsFile = new File("plugins/DragonTravel/databases", "flights.yml");
      this.dbFlightsConfig = new YamlConfiguration();

      try {
         this.dbFlightsConfig.load(this.dbFlightsFile);
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   public List getFlightNames() {
      List flights = new ArrayList();
      Iterator var3 = this.dbFlightsConfig.getConfigurationSection("Flights").getKeys(false).iterator();

      while(var3.hasNext()) {
         String name = (String)var3.next();
         Flight flight = getFlight(name);
         flights.add(flight.getName());
      }

      return flights;
   }

   private static Flight getFlight(String name) {
      return DragonTravel.getInstance().getDbFlightsHandler().getFlight(name);
   }
}
