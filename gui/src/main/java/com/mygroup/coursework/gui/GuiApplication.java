package com.mygroup.coursework.gui;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mygroup.coursework.gui.net.Request;
import com.mygroup.coursework.gui.net.RequestSender;
import com.mygroup.coursework.gui.presenters.ArgsInputPresenter;
import com.mygroup.coursework.gui.presenters.DataSelectionPresenter;
import com.mygroup.coursework.gui.presenters.InternalErrorPresenter;
import com.mygroup.coursework.gui.presenters.Presenter;
import com.mygroup.coursework.gui.presenters.RequestResultPresenter;
import com.mygroup.coursework.gui.presenters.RequestSelectionPresenter;
import com.mygroup.coursework.gui.presenters.SignInPresenter;
import com.mygroup.coursework.gui.presenters.SignUpPresenter;

public class GuiApplication {

  public static void main(String[] args) {
    String jwt = getJwt();

    while (true) {
      DataSelectionPresenter dataSelectionPresenter = new DataSelectionPresenter();
      dataSelectionPresenter.startFrame();
      DataType selectedType = dataSelectionPresenter.getSelectedType();

      RequestSelectionPresenter requestSelectionPresenter =
          new RequestSelectionPresenter(selectedType);
      requestSelectionPresenter.startFrame();
      Request selectedRequest = requestSelectionPresenter.getSelectedRequest();

      ArgsInputPresenter argsInputPresenter = new ArgsInputPresenter(selectedRequest, selectedType);
      argsInputPresenter.startFrame();
      Map<String, String> arguments = argsInputPresenter.getArgs();

      HttpResponse<String> response = null;
      RequestSender sender = new RequestSender();
      sender.setType(selectedType);
      sender.setRequest(selectedRequest);
      sender.setJwt(jwt);
      try {
        if (selectedRequest.getMethod().equals("POST")) {
          sender.setJsonBody(new ObjectMapper().writeValueAsString(arguments));
          response = sender.sendRequestWithBody();
        } else {
          sender.setArgs(arguments);
          response = sender.sendRequestWithArgs();
        }
      } catch (URISyntaxException | IOException | InterruptedException e) {
        Presenter internalErrorPresenter = new InternalErrorPresenter();
        internalErrorPresenter.startFrame();
        return;
      }
      RequestResultPresenter requestResultPresenter = new RequestResultPresenter(response);
      requestResultPresenter.startFrame();
    }
  }

  private static String getJwt() {
    boolean authenticated = false;
    String jwt = null;
    try {
      while (!authenticated) {
        SignInPresenter signInPresenter = new SignInPresenter();
        signInPresenter.startFrame();
        if (signInPresenter.choseSignUp()) {
          SignUpPresenter signUpPresenter = new SignUpPresenter();
          signUpPresenter.startFrame();
        } else if (signInPresenter.signedIn()) {
          authenticated = true;
          jwt = signInPresenter.getJwt();
        }
      }
    } catch (RuntimeException e) {
      Presenter internalErrorPresenter = new InternalErrorPresenter();
      internalErrorPresenter.startFrame();
    }
    return jwt;
  }

}
