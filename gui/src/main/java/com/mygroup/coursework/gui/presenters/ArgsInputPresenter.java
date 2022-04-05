package com.mygroup.coursework.gui.presenters;

import java.util.Map;
import com.mygroup.coursework.gui.DataType;
import com.mygroup.coursework.gui.frames.ArgsInputFrame;
import com.mygroup.coursework.gui.net.Request;

public class ArgsInputPresenter implements Presenter {
  private final Request selectedRequest;
  private final DataType selectedType;
  private Map<String, String> args;

  public ArgsInputPresenter(Request selectedRequest, DataType selectedType) {
    this.selectedRequest = selectedRequest;
    this.selectedType = selectedType;
  }

  public Map<String, String> getArgs() {
    return args;
  }

  public void startFrame() {
    ArgsInputFrame argsInputFrame = new ArgsInputFrame(selectedRequest, selectedType);
    new Thread(argsInputFrame).run();
    synchronized (argsInputFrame) {
      while (!argsInputFrame.hasArgs()) {
        try {
          argsInputFrame.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
    argsInputFrame.dispose();
    args = argsInputFrame.getArgs();
  }
}
