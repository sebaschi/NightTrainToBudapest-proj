package ch.unibas.dmi.dbis.cs108.multiplayer.client;

import java.io.File;
import java.net.URL;
import java.util.Random;
import javafx.scene.media.*;


public class Sound {

  static final double defaultvolume = 0.7;

  static URL backgroundSoundsURL = Sound.class.getResource("sounds/tracknoise.wav");
  static AudioClip backgroundSounds = new AudioClip(backgroundSoundsURL.toString());
  static boolean playingBackgroundSounds = false;

  static URL bellURL = Sound.class.getResource("sounds/bell.wav");
  static AudioClip bell = new AudioClip(bellURL.toString());

  static URL daynoisesURL = Sound.class.getResource("sounds/daynoises.wav");
  static AudioClip daynoises = new AudioClip(daynoisesURL.toString());

  static URL nightnoisesURL = Sound.class.getResource("sounds/nightnoises.wav");
  static AudioClip nightnoises = new AudioClip(nightnoisesURL.toString());

  static URL gameoverghostsURL = Sound.class.getResource("sounds/gameoverghosts.wav");
  static AudioClip gameoverghosts = new AudioClip(gameoverghostsURL.toString());

  static URL gameoverhumansURL = Sound.class.getResource("sounds/gameoverhumans.wav");
  static AudioClip gameoverhumans = new AudioClip(gameoverhumansURL.toString());

  static URL ghost01URL = Sound.class.getResource("sounds/ghost01.wav");
  static AudioClip ghost01 = new AudioClip(ghost01URL.toString());

  static URL ghost02URL = Sound.class.getResource("sounds/ghost02.wav");
  static AudioClip ghost02 = new AudioClip(ghost02URL.toString());

  static URL ghost03URL = Sound.class.getResource("sounds/ghost03.wav");
  static AudioClip ghost03 = new AudioClip(ghost03URL.toString());

  static URL ghost04URL = Sound.class.getResource("sounds/ghost04.wav");
  static AudioClip ghost04 = new AudioClip(ghost04URL.toString());

  static URL ghost05URL = Sound.class.getResource("sounds/ghost05.wav");
  static AudioClip ghost05 = new AudioClip(ghost05URL.toString());

  static URL ghost06URL = Sound.class.getResource("sounds/ghost06.wav");
  static AudioClip ghost06 = new AudioClip(ghost06URL.toString());

  static URL ghost07URL = Sound.class.getResource("sounds/ghost07.wav");
  static AudioClip ghost07 = new AudioClip(ghost07URL.toString());

  static URL ghost08URL = Sound.class.getResource("sounds/ghost08.wav");
  static AudioClip ghost08 = new AudioClip(ghost08URL.toString());

  static URL ghost09URL = Sound.class.getResource("sounds/ghost09.wav");
  static AudioClip ghost09 = new AudioClip(ghost09URL.toString());

  static URL ghost10URL = Sound.class.getResource("sounds/ghost10.wav");
  static AudioClip ghost10 = new AudioClip(ghost10URL.toString());

  static URL ghost11URL = Sound.class.getResource("sounds/ghost11.wav");
  static AudioClip ghost11 = new AudioClip(ghost11URL.toString());

  static URL ghost12URL = Sound.class.getResource("sounds/ghost12.wav");
  static AudioClip ghost12 = new AudioClip(ghost12URL.toString());



  static URL musicdayURL = Sound.class.getResource("sounds/music_day.wav");
  static AudioClip musicday = new AudioClip(musicdayURL.toString());

  static URL voteforghostURL = Sound.class.getResource("sounds/voteforghost.wav");
  static AudioClip voteforghost = new AudioClip(voteforghostURL.toString());

  static URL voteforhumanURL = Sound.class.getResource("sounds/voteforhuman.wav");
  static AudioClip voteforhuman = new AudioClip(voteforhumanURL.toString());

  static Random random = new Random();




  public static void main(String[] args) throws Exception {

    startPlayingBackgroundSounds();
    ghost();
    while(true) {

    }

  }



  public static void startPlayingBackgroundSounds() {
    playingBackgroundSounds = true;
    backgroundSounds.setCycleCount(AudioClip.INDEFINITE);
    backgroundSounds.play(defaultvolume, 0.0, 1.0, 0.0, 6 );
  }

  public static void bell() {
    bell.play(defaultvolume - 0.5);
  }

  public static void startDaynoises() {
    daynoises.setCycleCount(AudioClip.INDEFINITE);
    daynoises.play(defaultvolume - 0.5);
  }

  public static void stopDaynoises() {
    daynoises.stop();
  }

  public static void startNightnoises() {
    nightnoises.setCycleCount(AudioClip.INDEFINITE);
    nightnoises.play(defaultvolume - 0.5);
  }

  public static void stopNightnoises() {
    nightnoises.stop();
  }

  public static void gameoverghosts() {
    stopmusicday();
    gameoverghosts.play(defaultvolume); }

  public static void gameoverhumans() {
    stopmusicday();
    gameoverhumans.play(defaultvolume);
  }

  public static void voteforghost() { voteforghost.play(defaultvolume); }

  public static void voteforhuman() { voteforhuman.play(defaultvolume); }

  public static void musicday() {
    musicday.play(defaultvolume);
  }

  public static void stopmusicday() {
    //todo: gentle fade out
    musicday.stop();
  }

  public static void ghost() {
    //double playbackspeed = (Math.random() / 5.0) + 0.9;                 causes aliasing artefacts :/
    double playbackspeed = 1;
    int ghostsoundnr = random.nextInt(12) + 1;
    System.out.println(ghostsoundnr);
    AudioClip ghost;
    switch (ghostsoundnr) {
      case 1:
        ghost = ghost01;
        break;
      case 2:
        ghost = ghost02;
        break;
      case 3:
        ghost = ghost03;
        break;
      case 4:
        ghost = ghost04;
        break;
      case 5:
        ghost = ghost05;
        break;
      case 6:
        ghost = ghost06;
        break;
      case 7:
        ghost = ghost07;
        break;
      case 8:
        ghost = ghost08;
        break;
      case 9:
        ghost = ghost09;
        break;
      case 10:
        ghost = ghost10;
        break;
      case 11:
        ghost = ghost11;
        break;
      case 12:
        ghost = ghost12;
        break;
      default:
        ghost = ghost01;
        break;
    }
    ghost.play(0.08, 0.0, playbackspeed, 0.0, 5);
  }

}


