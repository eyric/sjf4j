package com.levin.sjf4j.core.node;

import com.levin.sjf4j.core.JsonTreeNode;

public interface JsonTreeNodeFactory<JsonNode> {
    JsonTreeNode create(JsonNode node);
}
