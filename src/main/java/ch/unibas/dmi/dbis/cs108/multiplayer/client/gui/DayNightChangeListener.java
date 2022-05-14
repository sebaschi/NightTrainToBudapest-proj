package ch.unibas.dmi.dbis.cs108.multiplayer.client.gui;


import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.game.GameController;
import ch.unibas.dmi.dbis.cs108.multiplayer.client.gui.lounge.LoungeSceneViewController;

/**
 * This class adds methods to listen if there is a change in the day&night state and calls methods accordingly
 */
public class DayNightChangeListener implements Runnable {

  private GameStateModel gameStateModel;
  private ChatApp chatApp;
  private int position;
  private boolean noiseButtonInvisible;

  public DayNightChangeListener(GameStateModel gameStateModel, ChatApp chatApp, int position) {
    this.gameStateModel = gameStateModel;
    this.chatApp = chatApp;
  }

  public void setNoiseButtonInvisible(boolean visible) {
    noiseButtonInvisible = visible;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  @Override
  public void run() {
    try{
      while(!gameStateModel.isGameOver()) {
        if(gameStateModel.getDayClone()) { //its Day
          LoungeSceneViewController.getTrainAnimationDayController().dontShowFullWagon();
          Sprites.setDaySprites(gameStateModel.getPassengerTrainClone()[1],
              GameController.getGameStateModel().getKickedOff());
          chatApp.getGameController()
              .updateGameSprites(LoungeSceneViewController.getTrainAnimationDayController());
          if(!noiseButtonInvisible) {
            chatApp.getGameController().setNoiseButtonVisible();
          }
          chatApp.getGameController().setVoteButtonVisibilityDay(gameStateModel);
        } else { //its night
          try {
            if (gameStateModel.getYourRoleFromPosition(position).equals("")) {
              LoungeSceneViewController.getTrainAnimationDayController().showFullWagon();
            }
          } catch (Exception e) {
            e.getMessage();
          }
          Sprites.setNightSprites(gameStateModel.getPassengerTrainClone()[1],
              GameController.getGameStateModel().getKickedOff());
          chatApp.getGameController()
              .updateGameSprites(LoungeSceneViewController.getTrainAnimationDayController());
          chatApp.getGameController().setNoiseButtonInvisible();
          chatApp.getGameController().setVoteButtonVisibilityNight(gameStateModel);
        }
        chatApp.getGameController().updateGameSprites(LoungeSceneViewController.getTrainAnimationDayController());
        chatApp.getGameController().updateRoomLabels();
        gameStateModel.setRoleFromPosition(position);
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
