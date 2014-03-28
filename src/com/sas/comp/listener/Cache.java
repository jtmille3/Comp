package com.sas.comp.listener;

import com.sas.comp.models.Competitive;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

public class Cache {

	private static Cache cache;

	private Cache() {

	}

	public static Cache getCache() {
		if (cache == null) {
			cache = new Cache();
		}

		return cache;
	}

	public Competitive getCompetitive() {
		return competitive;
	}

    public String getCompetitiveJson() throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.writeValueAsString(this.getCompetitive());
    }

	public void setCompetitive(final Competitive competitive) {
		this.competitive = competitive;
	}

	private Competitive competitive;
}
