package org.exoplatform.addons.sharemood.entity.rest;

import org.exoplatform.addons.sharemood.entity.MoodEntity;

import java.util.List;

public class MoodsPeriod {
  private String mood;

  private List<Mood> moods;

  public String getMood() {
    return mood;
  }

  public void setMood(String mood) {
    this.mood = mood;
  }

  public List<Mood> getMoods() {
    return moods;
  }

  public void setMoods(List<Mood> moods) {
    this.moods = moods;
  }
}
