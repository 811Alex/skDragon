package ud.skript.sashie.skDragon.emotes;

import java.util.ArrayList;
import java.util.List;

public class CustomEmote {
   private String name;
   private EmoteType emoteType;
   private List timings = new ArrayList();
   private List skulls = new ArrayList();
   private int totalLength = 0;

   public CustomEmote(String name) {
      this.name = name;
   }

   public CustomEmote(EmoteType emoteType) {
      this.emoteType = emoteType;
      this.name = emoteType.getName();
   }

   public String getName() {
      return this.name;
   }

   public EmoteType getEmoteType() {
      return this.emoteType;
   }

   public String getFrameData(int i) {
      return (String)this.skulls.get(i);
   }

   public int getFrameLength(int i) {
      return (Integer)this.timings.get(i);
   }

   public int getTotalFrameLength() {
      int total = 0;

      for(int i = 0; i < this.size(); ++i) {
         total += this.getFrameLength(i);
      }

      return total;
   }

   public int getTotalLength() {
      return this.totalLength;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setEmoteType(EmoteType emoteType) {
      this.emoteType = emoteType;
   }

   public void addFrame(int timing, String skull) {
      this.timings.add(timing);
      this.totalLength += timing;
      this.skulls.add(skull);
   }

   public int size() {
      return this.timings.size();
   }
}
