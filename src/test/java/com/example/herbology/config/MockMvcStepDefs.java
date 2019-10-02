package com.example.herbology.config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.util.List;

public class MockMvcStepDefs {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockMvcStepDefs.class);

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private RequestCreationStepDefs requestCreationStepDefs;
    @Autowired
    private ResponseAssertionSteps responseAssertionSteps;

    // State variables.
    private MockMvc mockMvc;
    private MockHttpServletResponse latestResponse;
    private ObjectMapper objectMapper;

    @Before
    public void clearState() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();
        latestResponse = null;
    }

    @When("^I (\\w+) (.+) (?:from|to) (.*) with parameters$")
    public void sendHttpRequest(HttpMethod httpMethod, String templateName, String url, DataTable requestParameters)
            throws Exception {

        requestCreationStepDefs.setRequestParameters(requestParameters);
        sendHttpRequestUsingPreparedRequest(httpMethod, templateName, url);
    }


    @When("^I (\\w+) from (.*) with parameters$")
    public void sendHttpRequest(HttpMethod httpMethod, String url, DataTable requestParameters) throws Exception {
        requestCreationStepDefs.setRequestParameters(requestParameters);
        sendHttpRequest(httpMethod, url);
    }

    @When("^I (\\w+) ([^\\s]+) to ([^\\s]+)$")
    public void sendHttpRequestUsingPreparedRequest(HttpMethod httpMethod, String templateName, String url)
            throws Exception {

        String request = requestCreationStepDefs.createRequest(templateName);
        url = requestCreationStepDefs.replaceUrlParameters(url);
        switch (httpMethod) {
            case POST:
                perform(MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request));
                break;
            case PUT:
                perform(MockMvcRequestBuilders.put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request));
                break;
            case GET:
                perform(MockMvcRequestBuilders.get(url)
                    .contentType(MediaType.APPLICATION_JSON_UTF8));
                break;
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
        responseAssertionSteps.parsePattern(templateName, objectMapper.readTree(getLatestResponseContentAsString()));
    }

    @When("^I (\\w+) from ([^\\s]+)$")
    public void sendHttpRequest(HttpMethod httpMethod, String url) throws Exception {
        url = requestCreationStepDefs.replaceUrlParameters(url);
        switch (httpMethod) {
            case GET:
                perform(MockMvcRequestBuilders.get(url));
                break;
            case DELETE:
                perform(MockMvcRequestBuilders.delete(url));
                break;
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
    }

    @When("^I POST '(.+)' file to ([^\\s]+)$")
    public void uploadExcel(String filepath, String url) throws Exception {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(filepath);
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                Paths.get(filepath).getFileName().toString(),
                MediaType.APPLICATION_OCTET_STREAM_VALUE,
                resourceAsStream
        );
        perform(MockMvcRequestBuilders.fileUpload(url).file(mockMultipartFile));
    }

    public void executeSearch(String url, String searchQuery) throws Exception {
        perform(MockMvcRequestBuilders.get(url).param("searchParam", searchQuery));
    }

    public <T> List<T> getLatestResponseContentAsList(Class<T> tClass) throws IOException {
        JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, tClass);
        return objectMapper.readValue(latestResponse.getContentAsString(), type);
    }

    public String getLatestResponseContentAsString() {
        try {
            return latestResponse.getContentAsString();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T getLatestResponseContentAsObjectOf(Class<T> tClass) throws IOException {
        return objectMapper.readValue(latestResponse.getContentAsString(), tClass);
    }

    public int getLatestResponseStatus() {
        return latestResponse.getStatus();
    }

    private void perform(RequestBuilder requestBuilder) throws Exception {
        latestResponse = mockMvc.perform(requestBuilder).andReturn().getResponse();
        String responseBody = latestResponse.getContentAsString();
        try {
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            responseBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
        } catch (Exception ignored) {
        }
        LOGGER.info("HTTP response body: " + responseBody);
    }
}
