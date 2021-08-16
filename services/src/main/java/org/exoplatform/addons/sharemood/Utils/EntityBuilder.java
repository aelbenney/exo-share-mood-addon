package org.exoplatform.addons.sharemood.Utils;

import org.exoplatform.addons.sharemood.entity.MoodEntity;
import org.exoplatform.addons.sharemood.entity.rest.Mood;
import org.exoplatform.addons.sharemood.services.MoodDTO;

public class EntityBuilder {

  public static MoodDTO convertToDTO(MoodEntity moodEntity) {
    if(moodEntity != null) {
      return new MoodDTO(moodEntity.getId(), moodEntity.getUserName(), moodEntity.getSelectedMood(), moodEntity.getWhen());
    }
    return null;
  }

  public static  MoodEntity convertToEntity(MoodDTO moodDTO) {
    MoodEntity moodEntity = new MoodEntity();
    moodEntity.setSelectedMood(moodDTO.getMood());
    moodEntity.setUserName(moodDTO.getUsername());
    moodEntity.setWhen(moodDTO.getWhen());
    return moodEntity;
  }

  public static Mood convertToMood(MoodDTO moodDTO) {
    Mood mood = new Mood();
    mood.setId(moodDTO.getId());
    mood.setUsername(moodDTO.getUsername());
    mood.setWhen(moodDTO.getWhen().getInstance().getTime());
    mood.setMood(moodDTO.getMood().toString());
    return mood;
  }
}
