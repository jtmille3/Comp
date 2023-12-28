package com.sas.comp.resource;

import java.text.SimpleDateFormat;

import javax.ws.rs.Produces;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@Provider
@Produces("application/json")
public class JacksonObjectMapper implements ContextResolver<ObjectMapper> {
  private final ObjectMapper objectMapper;

  public JacksonObjectMapper() throws Exception {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(Inclusion.NON_NULL);
    objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.setDateFormat(new SimpleDateFormat("yyyy/MM/dd HH:mm"));
    // objectMapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
    // objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,
    // false);
  }

  @Override
  public ObjectMapper getContext(final Class<?> type) {
    return this.objectMapper;
  }
}