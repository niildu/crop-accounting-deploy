package com.cropaccounting.ex;

import java.nio.charset.Charset;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

/**
 * The request could not be understood by the server due to malformed syntax. The client SHOULD NOT repeat the request without modifications. <br>
 * General error when fulfilling the request would cause an invalid state. Domain validation errors, missing data, etc. are some examples.
 * 
 * @author Imtiaz Rahi
 * @since 2017-02-23
 * @see <a href="https://en.wikipedia.org/wiki/List_of_HTTP_status_codes">List of HTTP status codes</a>
 * @see <a href="http://www.restapitutorial.com/httpstatuscodes.html">REST API Tutorial: HTTP Status Codes</a>
 * @see <a href="http://www.django-rest-framework.org/api-guide/status-codes/">Status Codes - Django REST framework</a>
 * @see <a href="https://upload.wikimedia.org/wikipedia/commons/6/65/Http-headers-status.gif">Activity diagram to describe resolution of response status code, given various headers</a>
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "malformed request syntax, too large size or invalid request message framing")
public class BadRequestException extends HttpClientErrorException {
	private static final long serialVersionUID = 400L;
	public static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

	public BadRequestException() {
		super(STATUS);
	}

	public BadRequestException(String statusText) {
		super(STATUS, statusText);
	}
	
	public BadRequestException(String connector, String statusText) {
		super(STATUS, statusText);
	}

//	public BadRequestException(String statusText, HttpHeaders headers, byte[] responseBody, Charset charset) {
//		HttpClientErrorException(STATUS, statusText, headers, responseBody, charset);
//	}
}
