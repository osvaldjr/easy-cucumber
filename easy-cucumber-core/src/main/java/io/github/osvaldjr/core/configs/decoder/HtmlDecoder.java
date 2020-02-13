package io.github.osvaldjr.core.configs.decoder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import feign.Response;
import feign.codec.Decoder;

public class HtmlDecoder implements Decoder {

  @Override
  public Object decode(Response response, Type type) throws IOException {
    String responseString =
        IOUtils.toString(response.body().asInputStream(), StandardCharsets.UTF_8);

    MultiValueMap<String, String> headers = getHeaders(response.headers());
    return new ResponseEntity<>(responseString, headers, HttpStatus.valueOf(response.status()));
  }

  private MultiValueMap<String, String> getHeaders(Map<String, Collection<String>> headers) {
    MultiValueMap<String, String> headersMap = new LinkedMultiValueMap<>();
    headers.forEach((key, values) -> headersMap.put(key, new LinkedList<>(values)));
    return headersMap;
  }
}
