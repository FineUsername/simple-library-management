package com.mygroup.coursework.gui.presenters;

import java.net.http.HttpResponse;
import com.mygroup.coursework.gui.frames.RequestResultFrame;

public class RequestResultPresenter implements Presenter {
  private final HttpResponse<String> response;

  public RequestResultPresenter(HttpResponse<String> response) {
    this.response = response;
  }

  public void startFrame() {
    RequestResultFrame requestResultFrame =
        new RequestResultFrame(response.statusCode(), response.body());
    new Thread(requestResultFrame).run();
    synchronized (requestResultFrame) {
      while (!requestResultFrame.wantsNewRequest()) {
        try {
          requestResultFrame.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
    requestResultFrame.dispose();
  }
}
