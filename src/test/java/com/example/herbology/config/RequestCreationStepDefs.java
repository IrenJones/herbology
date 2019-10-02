package com.example.herbology.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import io.cucumber.datatable.DataTable;
import junit.framework.AssertionFailedError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;


public class RequestCreationStepDefs {

    private static final String UNDEFINED_PARAMETER = "" + System.currentTimeMillis() + new Random().nextInt(Integer.MAX_VALUE);
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestCreationStepDefs.class);

    @Autowired
    private AliasRegistry aliasRegistry;

    private ObjectMapper objectMapper = new ObjectMapper();

    // State variables.
    private Map<String, String> rawContext = new HashMap<>();

    @Before
    public void clearState() {
        objectMapper.writerWithDefaultPrettyPrinter();
        rawContext.clear();
    }

    @Given("^request parameter (.+) = (.+)$")
    public void setRequestParameter(String parameter, String value) {
        rawContext.put(parameter, value);
    }

    @Given("^request with parameters$")
    public void setRequestParameters(DataTable requestParameters) {
        rawContext.putAll(requestParameters.asMap(String.class, String.class));
    }

    public String createRequest(String templateName) {
        Template template = loadTemplate(templateName);
        Map<String, String> context = new HashMap<>();
        for (Map.Entry<String, String> entry : rawContext.entrySet()) {
            context.put(entry.getKey(), aliasRegistry.resolve(entry.getValue()));
        }
        String rawRequest = template.execute(context);
        try {
            JsonNode jsonNode = objectMapper.readTree(rawRequest);
            JsonNode filtered = filterUndefined(jsonNode.deepCopy());
            filtered = (filtered == null) ? jsonNode : filtered;
            String result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(filtered);
            LOGGER.info(String.format("Created a request: %s", result));
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String replaceUrlParameters(String url) {
        for (Map.Entry<String, String> entry : rawContext.entrySet()) {
            url = url.replaceAll(String.format("\\{%s\\}", entry.getKey()), aliasRegistry.resolve(entry.getValue()));
        }
        return url;
    }

    private Template loadTemplate(String templateName) {
        String fullPath = "/mustache-templates/" + templateName + ".mustache";
        InputStream stream = getClass().getResourceAsStream(fullPath);
        if (stream == null) {
            throw new AssertionFailedError("Resource " + fullPath + " does not exist");
        }

        return Mustache.compiler().escapeHTML(false).defaultValue(UNDEFINED_PARAMETER).compile(new InputStreamReader(stream));
    }

    private JsonNode filterUndefined(JsonNode input) {
        Iterator<JsonNode> iterator = input.iterator();
        // Value nodes will give empty iterator.
        while (iterator.hasNext()) {
            JsonNode result = filterUndefined(iterator.next());
            if (result == null) {
                iterator.remove();
            }
        }
        if ((input.isArray() || input.isObject()) && !input.elements().hasNext()) {
            return null;
        }
        // Container nodes will give empty string.
        if (input.asText().equals(UNDEFINED_PARAMETER)) {
            return null;
        } else {
            return input;
        }
    }
}
