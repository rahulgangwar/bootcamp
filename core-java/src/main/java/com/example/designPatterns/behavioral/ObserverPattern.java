package com.example.designPatterns.behavioral;

import java.util.ArrayList;
import java.util.List;

public class ObserverPattern {

  public static void main(String args[]) {
    CricketMatch cricketMatch = new CricketMatch();
    cricketMatch.addObserver(new ScoreUpdate());

    cricketMatch.updateRuns(1);
    cricketMatch.updateRuns(10);
  }

  private interface Observable {
    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers();
  }

  private interface Observer {
    void update(int runs);
  }

  private static class ScoreUpdate implements Observer {
    public void update(int runs) {
      System.out.println("Updated score : " + runs);
    }
  }

  private static class CricketMatch implements Observable {
    int totalRuns = 0;
    List<Observer> observers = new ArrayList<Observer>();

    public void addObserver(Observer observer) {
      this.observers.add(observer);
    }

    public void removeObserver(Observer observer) {
      this.observers.remove(observer);
    }

    public void notifyObservers() {
      observers.forEach(x -> x.update(totalRuns));
    }

    public void updateRuns(int runs) {
      totalRuns += runs;
      notifyObservers();
    }
  }
}
