package com.mygroup.coursework.gui.presenters;

import com.mygroup.coursework.gui.DataType;
import com.mygroup.coursework.gui.frames.RequestSelectionFrame;
import com.mygroup.coursework.gui.net.Request;

public class RequestSelectionPresenter implements Presenter {
  private final DataType selectedType;
  private Request selectedRequest;

  public RequestSelectionPresenter(DataType selectedType) {
    this.selectedType = selectedType;
  }

  public Request getSelectedRequest() {
    return selectedRequest;
  }

  public void startFrame() {
    RequestSelectionFrame requestSelectionFrame = new RequestSelectionFrame(selectedType);
    new Thread(requestSelectionFrame).run();
    synchronized (requestSelectionFrame) {
      while (!requestSelectionFrame.hasSelectedRequest()) {
        try {
          requestSelectionFrame.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
    requestSelectionFrame.dispose();
    selectedRequest = requestSelectionFrame.getSelectedRequest();
  }
}
