package com.mygroup.coursework.gui.presenters;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mygroup.coursework.gui.DataType;
import com.mygroup.coursework.gui.dto.UserDto;
import com.mygroup.coursework.gui.frames.SignUpFrame;
import com.mygroup.coursework.gui.net.Request;
import com.mygroup.coursework.gui.net.RequestSender;

public class SignUpPresenter implements Presenter {
  private boolean signedUp;

  public SignUpPresenter() {
    signedUp = false;
  }

  public boolean signedUp() {
    return signedUp;
  }

  public void startFrame() {
    while (!signedUp) {
      SignUpFrame signUpFrame = new SignUpFrame();
      try {
        new Thread(signUpFrame).run();
        synchronized (signUpFrame) {
          while (!signUpFrame.hasInput()) {
            signUpFrame.wait();
          }
        }
        HttpResponse<String> response =
            sendSignUpRequest(signUpFrame.getUsername(), signUpFrame.getPassword());
        if (response.statusCode() != HttpURLConnection.HTTP_OK) {
          signUpFrame.displayUserAlreadyExists();
        } else {
          signedUp = true;
          signUpFrame.dispose();
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (IOException | URISyntaxException e) {
        signUpFrame.dispose();
        throw new RuntimeException(e);
      }
    }
  }

  private HttpResponse<String> sendSignUpRequest(String username, String password)
      throws IOException, InterruptedException, URISyntaxException {
    RequestSender sender = new RequestSender();
    sender.setType(DataType.USER);
    sender.setRequest(Request.SIGN_UP);
    sender.setJsonBody(new ObjectMapper().writeValueAsString(new UserDto(username, password)));
    return sender.sendRequestWithBody();
  }
}
