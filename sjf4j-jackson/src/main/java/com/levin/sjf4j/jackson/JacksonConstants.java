package com.levin.sjf4j.jackson;

public class JacksonConstants {

    // custom config switch
    public static final String ENABLE_CUSTOM_CONFIGURATION="ENABLE_CUSTOM_CONFIGURATION";

    // nulls
    public static final String SERIALIZE_NULLS_ATTR_KEY="SERIALIZE_NULLS";

    // boolean priority attribute keys:
    public static final String SERIALIZE_BOOLEAN_USING_1_0_ATTR_KEY = "SERIALIZE_BOOLEAN_USING_1_0";
    public static final String SERIALIZE_BOOLEAN_USING_ON_OFF_ATTR_KEY = "SERIALIZE_BOOLEAN_USING_ON_OFF";

    // number priority attribute keys:
    public static final String SERIALIZE_LONG_USING_STRING_ATTR_KEY = "SERIALIZE_LONG_USING_STRING";
    public static final String SERIALIZE_NUMBER_USING_STRING_ATTR_KEY = "SERIALIZE_NUMBER_USING_STRING";

    // enum priority attribute keys:
    public static final String SERIALIZE_ENUM_USING_INDEX_ATTR_KEY = "SERIALIZE_ENUM_USING_INDEX";
    public static final String SERIALIZE_ENUM_USING_FIELD_ATTR_KEY = "SERIALIZE_ENUM_USING_FIELD";

    // date priority attribute keys:
    public static final String SERIALIZE_DATE_USING_DATE_FORMAT_ATTR_KEY = "SERIALIZE_DATE_USING_DATE_FORMAT";
    public static final String SERIALIZE_DATE_USING_PATTERN_ATTR_KEY = "SERIALIZE_DATE_USING_PATTERN";
    public static final String SERIALIZE_DATE_USING_TO_STRING_ATTR_KEY = "SERIALIZE_DATE_USING_TO_STRING";


}
