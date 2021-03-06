/*
 * Copyright 2017 Seven Bridges Genomics, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sevenbridges.apiclient.impl.http.support;

import com.sevenbridges.apiclient.impl.http.HttpHeaders;
import com.sevenbridges.apiclient.impl.http.MediaType;
import com.sevenbridges.apiclient.impl.http.Response;

import java.io.InputStream;

public class DefaultResponse extends AbstractHttpMessage implements Response {

  private final int httpStatus;
  private final HttpHeaders headers;
  private final InputStream body;

  public DefaultResponse(int httpStatus, MediaType contentType, InputStream body, long contentLength) {
    this.httpStatus = httpStatus;
    this.headers = new HttpHeaders();
    this.headers.setContentType(contentType);
    this.body = body;
    this.headers.setContentLength(contentLength);
  }

  public DefaultResponse(int httpStatus, HttpHeaders httpHeaders, InputStream body, long contentLength) {
    this.httpStatus = httpStatus;
    this.headers = httpHeaders;
    this.body = body;
    this.headers.setContentLength(contentLength);
  }

  @Override
  public int getHttpStatus() {
    return httpStatus;
  }

  @Override
  public HttpHeaders getHeaders() {
    return this.headers;
  }

  @Override
  public void setHeaders(HttpHeaders headers) {
    this.headers.clear();
    this.headers.putAll(headers);
  }

  @Override
  public boolean isError() {
    return isServerError() || isClientError();
  }

  @Override
  public boolean isServerError() {
    int status = getHttpStatus();
    return (status >= 500 && status < 600);
  }

  @Override
  public boolean isClientError() {
    int status = getHttpStatus();
    return (status >= 400 && status < 500);
  }

  @Override
  public InputStream getBody() {
    return this.body;
  }
}
