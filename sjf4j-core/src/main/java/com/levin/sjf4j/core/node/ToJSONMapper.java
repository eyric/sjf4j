package com.levin.sjf4j.core.node;

/**
 * mapping a tree node to any Java Object, or Custom JSONObject, JSONArray, JSONNull etc...
 */
public interface ToJSONMapper<JSONObject, JSONArray, JSONPrimitive, JSONNull> {
    JSONNull mappingNull(JsonNullNode node);

    JSONPrimitive mappingPrimitive(JsonPrimitiveNode node);

    JSONArray mappingArray(JsonArrayNode node);

    JSONObject mappingObject(JsonObjectNode node);
}
