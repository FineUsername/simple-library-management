package com.mygroup.coursework.gui.net;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Map;
import com.mygroup.coursework.gui.DataType;

public class RequestSender {
  private DataType type;
  private Request request;
  private String jsonBody;
  private Map<String, String> args;
  private String jwt;

  public void setType(DataType type) {
    this.type = type;
  }

  public void setRequest(Request request) {
    this.request = request;
  }

  public void setJsonBody(String body) {
    this.jsonBody = body;
  }

  public void setArgs(Map<String, String> args) {
    this.args = args;
  }

  public void setJwt(String jwt) {
    this.jwt = jwt;
  }

  public HttpResponse<String> sendRequestWithBody()
      throws IOException, InterruptedException, URISyntaxException {
    RequestBuilder builder = getInitialBuilder();
    HttpRequest httpRequest = builder.setJsonBody(jsonBody).getRequestWithBody();
    return HttpClient.newBuilder().build().send(httpRequest, BodyHandlers.ofString());
  }

  public HttpResponse<String> sendRequestWithArgs()
      throws IOException, InterruptedException, URISyntaxException {
    RequestBuilder builder = getInitialBuilder();
    HttpRequest httpRequest = builder.setArgs(args).getRequestWithArgs();
    return HttpClient.newBuilder().build().send(httpRequest, BodyHandlers.ofString());
  }

  private RequestBuilder getInitialBuilder() {
    RequestBuilder builder = new RequestBuilder();
    if (jwt != null) {
      builder.setJwt(jwt);
    }
    builder.setDataType(type).setRequest(request);
    return builder;
  }

}
