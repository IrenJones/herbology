package com.example.herbology.config;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import junit.framework.AssertionFailedError;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

public class ResponseAssertionSteps {
    @Autowired
    private AliasRegistry aliasRegistry;

    private ObjectMapper objectMapper = new ObjectMapper();

    // State variables.
    private Map<String, JsonPointer> patternPointers = new HashMap<>();
    private String patternName;
    private JsonNode response;

    @PostConstruct
    public void init() {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Before
    public void clearState() {
        patternPointers.clear();
        patternName = null;
        response = null;
    }

    @Then("^([^']+) in response is equal to (.+)$")
    public void assertResponseProperty(String property, String valueOrAlias) {
        String actualValue = getParameterValueFromResponse(property);
        String expectedValue = aliasRegistry.resolve(valueOrAlias);
        Assert.assertEquals(String.format("Wrong value for property %s", property), expectedValue, actualValue);
    }

    @Then("^set '(.+)' in response is equal to set '(.+)'$")
    public void assertUnorderedListValues(List<String> propertyList, List<String> valueList) {
        List<String> actualValueList = propertyList.stream()
                .map(this::getParameterValueFromResponse)
                .collect(Collectors.toList());
        List<String> expectedValueList = valueList.stream()
                .map(aliasRegistry::resolve)
                .collect(Collectors.toList());
        Collections.sort(actualValueList);
        Collections.sort(expectedValueList);
        String message = String.format("Wrong values for properties %s: expected %s, actual %s",
                propertyList, expectedValueList, actualValueList);
        Assert.assertTrue(message, CollectionUtils.isEqualCollection(expectedValueList, actualValueList));
    }

    @Then("^every element in set '(.+)' in response is equal to '(.+)'$")
    public void assertUnorderedListValuesEqualsToValue(List<String> propertyList, String value) {
        List<String> actualValueList = propertyList.stream()
                .map(this::getParameterValueFromResponse)
                .collect(Collectors.toList());
        Collections.sort(actualValueList);

        for (String actualValue:actualValueList){
            String message = String.format("Wrong values for properties %s: expected %s, actual %s",
                    propertyList, value, actualValue);
            Assert.assertEquals(message, value, actualValue);
        }
    }

    @Then("^the following properties are absent in response:$")
    public void assertPropertyAbsence(List<String> properties) {
        List<String> existingProperties = new ArrayList<>();
        for (String p : properties) {
            JsonNode jsonNode = obtainJsonNode(p);
            if (!jsonNode.isMissingNode()) {
                existingProperties.add(p);
            }
        }
        if (!existingProperties.isEmpty()) {
            Assert.fail(String.format("Found the following properties in the response: %s", existingProperties));
        }
    }

    @And("^([^']+) from response is saved as ([^']+)$")
    public void propertyInResponseWasSetTo(String property, String valueOrAlias) {
        String actualValue = getParameterValueFromResponse(property);
        aliasRegistry.put(valueOrAlias, actualValue);
    }

    public void parsePattern(String patternName, JsonNode response) throws IOException {
        this.patternName = patternName;
        this.response = response;
        patternPointers.putAll(parsePattern(loadResponsePattern(patternName), ""));
    }

    private Map<String, JsonPointer> parsePattern(JsonNode input, String jsonPointer) {
        HashMap<String, JsonPointer> result = new HashMap<>();
        if (input.isArray()) {
            for (int i = 0; i < input.size(); i++) {
                result.putAll(parsePattern(input.get(i), jsonPointer + "/" + i));
            }
        } else if (input.isObject()) {
            for (Map.Entry<String, JsonNode> entry : ((Iterable<Map.Entry<String, JsonNode>>) input::fields)) {
                result.putAll(parsePattern(entry.getValue(), jsonPointer + "/" + entry.getKey()));
            }
        } else if (input.isValueNode()) {
            result.put(input.asText(), JsonPointer.compile(jsonPointer));
        } else {
            throw new IllegalArgumentException("Can't parse json node of type " + input.getNodeType());
        }
        return result;
    }

    private JsonNode loadResponsePattern(String patternName) throws IOException {
        String fullPath = "/json-patterns/" + patternName + ".json";
        InputStream stream = getClass().getResourceAsStream(fullPath);

        if (stream == null) {
            throw new AssertionFailedError("Resource " + fullPath + " doesn't exist");
        }

        return objectMapper.readTree(IOUtils.toString(stream, Charset.defaultCharset()));
    }

    private String getParameterValueFromResponse(String parameterName) {
        JsonNode jsonNode = obtainJsonNode(parameterName);
        String value;
        if (jsonNode.isNull()) {
            value = null;
        } else if (jsonNode.isMissingNode()) {
            throw new IllegalArgumentException("Could not find '" + parameterName + "' in response.");
        } else {
            value = jsonNode.asText();
        }
        return value;
    }

    private JsonNode obtainJsonNode(String parameterName) {
        JsonPointer jsonPointer = patternPointers.get(parameterName);
        if (jsonPointer == null) {
            throw new IllegalArgumentException("There is no parameter with name '" + parameterName + "' in pattern '" + patternName + "'.");
        }
        return response.at(jsonPointer);
    }
}
