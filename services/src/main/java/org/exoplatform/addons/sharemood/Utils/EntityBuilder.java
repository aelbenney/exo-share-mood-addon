package org.exoplatform.addons.sharemood.Utils;

import org.exoplatform.addons.sharemood.entity.MoodEntity;
import org.exoplatform.addons.sharemood.services.MoodDTO;

public class EntityBuilder {

  public static MoodDTO convertToDTO(MoodEntity moodEntity) {
    if(moodEntity != null) {
      return new MoodDTO(moodEntity.getUserName(), moodEntity.getSelectedMood(), moodEntity.getWhen());
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
}
