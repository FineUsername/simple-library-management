package com.mygroup.coursework.gui.presenters;

import com.mygroup.coursework.gui.DataType;
import com.mygroup.coursework.gui.frames.DataSelectionFrame;

public class DataSelectionPresenter implements Presenter {
  private DataType selectedType;

  public DataType getSelectedType() {
    return selectedType;
  }

  public void startFrame() {
    DataSelectionFrame dataSelectionFrame = new DataSelectionFrame();
    new Thread(dataSelectionFrame).run();
    synchronized (dataSelectionFrame) {
      while (!dataSelectionFrame.hasSelectedData()) {
        try {
          dataSelectionFrame.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
    dataSelectionFrame.dispose();
    selectedType = dataSelectionFrame.getSelectedData();
  }
}
