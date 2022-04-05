package com.mygroup.coursework.gui.presenters;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mygroup.coursework.gui.DataType;
import com.mygroup.coursework.gui.dto.UserDto;
import com.mygroup.coursework.gui.frames.SignInFrame;
import com.mygroup.coursework.gui.net.Request;
import com.mygroup.coursework.gui.net.RequestSender;

public class SignInPresenter implements Presenter {
  private String jwt;
  private boolean signedIn;
  private boolean choseSignUp;

  public SignInPresenter() {
    signedIn = false;
    choseSignUp = false;
  }

  public String getJwt() {
    if (!signedIn) {
      throw new IllegalStateException("Must sign in before acquiring jwt");
    }
    return jwt;
  }

  public boolean signedIn() {
    return signedIn;
  }

  public boolean choseSignUp() {
    return choseSignUp;
  }

  public void startFrame() {
    while (!signedIn && !choseSignUp) {
      SignInFrame signInFrame = new SignInFrame();
      try {
        new Thread(signInFrame).run();
        synchronized (signInFrame) {
          while (!signInFrame.hasInput() && !signInFrame.choseSignUp()) {
            signInFrame.wait();
          }
          choseSignUp = signInFrame.choseSignUp();
        }
        if (choseSignUp) {
          signInFrame.dispose();
        } else { // has input, therefore attempt to sign in
          HttpResponse<String> response =
              sendSignInRequest(signInFrame.getUsername(), signInFrame.getPassword());
          if (response.statusCode() != HttpURLConnection.HTTP_OK) {
            signInFrame.displayInvalidCredentials();
          } else {
            jwt = new JSONObject(response.body()).getString("token");
            signedIn = true;
          }
          signInFrame.dispose();
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (JSONException | IOException | URISyntaxException e) {
        signInFrame.dispose();
        throw new RuntimeException(e);
      }
    }
  }

  private HttpResponse<String> sendSignInRequest(String username, String password)
      throws IOException, InterruptedException, URISyntaxException {
    RequestSender sender = new RequestSender();
    sender.setType(DataType.USER);
    sender.setRequest(Request.SIGN_IN);
    sender.setJsonBody(new ObjectMapper().writeValueAsString(new UserDto(username, password)));
    return sender.sendRequestWithBody();
  }

}
