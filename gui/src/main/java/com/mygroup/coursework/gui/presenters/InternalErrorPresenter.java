package com.mygroup.coursework.gui.presenters;

import com.mygroup.coursework.gui.frames.InternalErrorFrame;

public class InternalErrorPresenter implements Presenter {
  public void startFrame() {
    InternalErrorFrame internalErrorFrame = new InternalErrorFrame();
    new Thread(internalErrorFrame).run();
  }
}
