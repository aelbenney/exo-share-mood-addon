package org.exoplatform.addons.sharemood.entity.rest;

import java.util.Calendar;
import java.util.Date;

public class Mood {
  private Long id;
  private String username;
  private Date when;
  private String mood;

  public Mood() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Date getWhen() {
    return when;
  }

  public void setWhen(Date when) {
    this.when = when;
  }

  public String getMood() {
    return mood;
  }

  public void setMood(String mood) {
    this.mood = mood;
  }
}
