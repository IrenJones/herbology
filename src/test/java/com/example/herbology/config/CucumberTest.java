package com.example.herbology.config;

import com.example.herbology.HerbologyApplicationTests;
import cucumber.api.CucumberOptions;
import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "json:target/cucumber-reports/cucumber.json"},
        features = "src/test/resources/cucumber",
        glue = {"com.example.herbology.config"})
public class CucumberTest extends HerbologyApplicationTests {
}
