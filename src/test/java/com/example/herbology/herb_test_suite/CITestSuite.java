package com.example.herbology.herb_test_suite;

import com.example.herbology.controller.HerbControllerIntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DatastoreSystemHealthTest.class,
        HerbControllerIntegrationTest.class
})
public class CITestSuite {
}
