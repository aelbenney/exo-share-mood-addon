package org.exoplatform.addons.sharemood.services;

import org.exoplatform.addons.sharemood.Utils.EntityBuilder;
import org.exoplatform.addons.sharemood.dao.MoodDAO;
import org.exoplatform.addons.sharemood.entity.MoodEntity;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;

import javax.persistence.EntityNotFoundException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MoodService {
    private final MoodDAO moodDao;
    Log LOG = ExoLogger.getExoLogger(MoodService.class);

    public MoodService(MoodDAO moodDAO) {
        this.moodDao = moodDAO;
    }

    public MoodDTO saveMood(MoodEntity.Mood mood, String userName) {
      MoodEntity moodEntity = new MoodEntity();
      moodEntity.setWhen(Calendar.getInstance());
      moodEntity.setUserName(userName);
      moodEntity.setSelectedMood(mood);
      MoodEntity savedMood = moodDao.create(moodEntity);
      return EntityBuilder.convertToDTO(savedMood);
    }

    public MoodDTO updateMood(MoodEntity.Mood mood, String userName){
        Calendar now = Calendar.getInstance();
        MoodEntity moodEntity = moodDao.findByUserAndTime(userName,now);
      MoodEntity updatedMood = null;
      if(moodEntity != null) {
          moodEntity.setSelectedMood(mood);
        updatedMood = moodDao.update(moodEntity);
        } else {
          throw new EntityNotFoundException("No mood saved for user " + userName + " for today");
        }
        return EntityBuilder.convertToDTO(updatedMood);
    }

  public List<MoodDTO> loadMoods(String userName) {
    List<MoodEntity> moods = moodDao.loadMoods(userName);
    return moods.stream().map(EntityBuilder::convertToDTO).collect(Collectors.toList());
  }

  public List<MoodDTO> loadMoods(String userName, MoodEntity.Mood mood) {
    List<MoodEntity> moods = moodDao.loadMoods(userName, mood);
    return moods.stream().map(EntityBuilder::convertToDTO).collect(Collectors.toList());
  }

  public List<MoodDTO> loadMoods(String userName, MoodEntity.Mood mood, Calendar since) {
    List<MoodEntity> moods = moodDao.loadMoods(userName, mood, since);
    return moods.stream().map(EntityBuilder::convertToDTO).collect(Collectors.toList());
  }

  public MoodDTO find(String userName, Calendar today){
    return EntityBuilder.convertToDTO(moodDao.findByUserAndTime(userName,today));
  }

  public long countAllMoodsByUser(String userName, Calendar since) {
      return moodDao.countMoodsByUser(userName, since);
  }
  public List<MoodDTO> findAllByUserAndSince(String userName, Calendar since) {
    List<MoodEntity> moods = moodDao.findAllMoodsByUserAndSince(userName, since);
    return moods.stream().map(EntityBuilder::convertToDTO).collect(Collectors.toList());
  }
}
