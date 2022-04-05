package com.mygroup.coursework.gui.net;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.util.Map;
import com.mygroup.coursework.gui.DataType;

public class RequestBuilder {
  private final static String BASE_URL = "http://localhost:8080";
  private final static String CONTENT_TYPE = "application/json";
  private final static String JWT_PREFIX = "Bearer ";
  private final static String HTTP_CONTENT_TYPE_HEADER = "content-type";
  private final static String HTTP_AUTHORIZATION_HEADER = "Authorization";

  private DataType type;
  private Request request;
  private Map<String, String> args;
  private String jsonBody;
  private String jwt;

  public RequestBuilder setDataType(DataType type) {
    this.type = type;
    return this;
  }

  public RequestBuilder setRequest(Request request) {
    this.request = request;
    return this;
  }

  public RequestBuilder setJsonBody(String body) {
    this.jsonBody = body;
    return this;
  }

  public RequestBuilder setArgs(Map<String, String> args) {
    this.args = args;
    return this;
  }

  public RequestBuilder setJwt(String jwt) {
    this.jwt = jwt;
    return this;
  }

  public HttpRequest getRequestWithBody() throws URISyntaxException {
    validateGeneralState();
    if (jsonBody == null) {
      throw new IllegalStateException("Null json body");
    }
    HttpRequest.Builder builder =
        HttpRequest.newBuilder(new URI(BASE_URL + type.getMapping() + request.getMapping()))
            .method(request.getMethod(), HttpRequest.BodyPublishers.ofString(jsonBody))
            .header(HTTP_CONTENT_TYPE_HEADER, CONTENT_TYPE);
    if (jwt != null) {
      builder.header(HTTP_AUTHORIZATION_HEADER, JWT_PREFIX + jwt);
    }
    return builder.build();
  }

  public HttpRequest getRequestWithArgs() throws URISyntaxException {
    validateGeneralState();
    if (args == null) {
      throw new IllegalStateException("Null args");
    }
    HttpRequest.Builder builder = HttpRequest
        .newBuilder(
            new URI(BASE_URL + type.getMapping() + request.getMapping() + getArgsString(args)))
        .method(request.getMethod(), HttpRequest.BodyPublishers.noBody());
    if (jwt != null) {
      builder.header(HTTP_AUTHORIZATION_HEADER, JWT_PREFIX + jwt);
    }
    return builder.build();
  }

  private void validateGeneralState() {
    if ((type == null) || (request == null)) {
      throw new IllegalStateException("Null type or request");
    }
    if (!type.isSupported(request)) {
      throw new IllegalStateException("Unsupported request");
    }
  }

  private static String getArgsString(Map<String, String> args) {
    if ((args == null) || args.isEmpty()) {
      return "";
    }
    StringBuilder result = new StringBuilder("?");

    for (Map.Entry<String, String> entry : args.entrySet()) {
      result.append(entry.getKey());
      result.append("=");
      result.append(entry.getValue());
      result.append("&");
    }
    result.deleteCharAt(result.length() - 1);
    return result.toString();
  }
}
