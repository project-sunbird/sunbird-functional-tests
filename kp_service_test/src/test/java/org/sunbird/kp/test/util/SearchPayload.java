package org.sunbird.kp.test.util;

public class SearchPayload {

	public static final String SEARCH_CONTENT_WITH_BOARD = "{\n" +
			"    \"request\": {\n" +
			"        \"filters\": {\n" +
			"            \"objectType\": \"Content\",\n" +
			"            \"board\": \"boardVal\",\n" +
			"            \"status\": []\n" +
			"        }\n" +
			"    }\n" +
			"}";
	public static final String SEARCH_CONTENT_WITH_MEDIUM_SUBJECT = "{\n" +
			"    \"request\": {\n" +
			"        \"filters\": {\n" +
			"\t\t\t\"identifier\":\"${contentId}\",\n" +
			"\t\t\t\"status\":[]\n" +
			"        },\n" +
			"        \"fields\" : [\n" +
			"        \t\"identifier\",\n" +
			"        \t\"subject\",\n" +
			"        \t\"medium\"\n" +
			"\t\t]\n" +
			"\t\t}\n" +
			"}";
}
