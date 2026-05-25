package com.example.immutable;

import java.util.Date;

public class DateCreated {
  private Date date;

  public DateCreated(Date date) {
    this.date = date;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }
}
