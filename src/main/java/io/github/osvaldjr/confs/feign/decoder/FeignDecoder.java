package io.github.osvaldjr.confs.feign.decoder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.springframework.cloud.netflix.feign.support.ResponseEntityDecoder;
import org.springframework.cloud.netflix.feign.support.SpringDecoder;

import feign.Response;
import feign.codec.Decoder;
import io.github.osvaldjr.confs.feign.converter.MessageConverter;

public class FeignDecoder implements Decoder {
  private final ResponseEntityDecoder responseEntityDecoder =
      new ResponseEntityDecoder(new SpringDecoder(new MessageConverter()));
  private final HtmlDecoder htmlDecoder = new HtmlDecoder();

  @Override
  public Object decode(Response response, Type type) throws IOException {
    if (isContentTypeHtml(response)) {
      return htmlDecoder.decode(response, type);
    } else {
      return responseEntityDecoder.decode(response, type);
    }
  }

  private boolean isContentTypeHtml(Response response) {
    Collection<String> contentType = response.headers().get("content-type");
    return Optional.ofNullable(contentType).orElseGet(Collections::emptyList).stream()
        .anyMatch(s -> s.contains("text/html"));
  }
}
