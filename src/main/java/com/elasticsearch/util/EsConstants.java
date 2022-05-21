package com.elasticsearch.util;

public final class EsConstants {
	public static final String INDEX_NAME = "productindex";
	public static final String INDEX_TYPE_NAME = "product";
	public static final String SUCCESS_MSG = "success";
	public static final String EMPTY_STRING = "";
	public static final String BLANK_STRING = " ";
	public static final String FIELD = "field";
	public static final String VALUE = "value";
	public static final String SCORE = "Score:";
	public static final String ERROR = "Error:";
	public static final String NEW_LINE = "\r\n";
	public static final String OR_SCRIPT = "{\"query\":{\"bool\":{\"should\":[{\"match\":{\"{{field1}}\":{\"query\":\"{{value1}}\"}}},{\"match\":{\"{{field2}}\":{\"query\":\"{{value2}}\"}}}]}}}";

}
