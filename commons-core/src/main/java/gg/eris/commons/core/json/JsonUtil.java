package gg.eris.commons.core.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.TextNode;
import java.util.List;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JsonUtil {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  public static ArrayNode toStringArray(List<?> list) {
    return MAPPER.createArrayNode().addAll(list
        .stream()
        .map(item -> new TextNode(item.toString()))
        .collect(Collectors.toList())
    );
  }

  public static ArrayNode populateStringArray(ArrayNode node, List<?> list) {
    return node.addAll(list
        .stream()
        .map(item -> new TextNode(item.toString()))
        .collect(Collectors.toList())
    );
  }

}
