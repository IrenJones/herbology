package com.example.herbology.herb_test_suite;

import com.example.herbology.controller.HerbControllerIntegrationTest;
import com.example.herbology.controller.HerbControllerTest;
import com.example.herbology.controller.HerbControllerUnitTest;
import com.example.herbology.repository.HerbRepositoryIntegrationTest;
import com.example.herbology.repository.HerbRepositoryTest;
import com.example.herbology.service.HerbServiceIntegrationTest;
import com.example.herbology.service.HerbServiceSecondTest;
import com.example.herbology.service.HerbServiceUnitTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        HerbRepositoryTest.class,
        HerbRepositoryIntegrationTest.class,
        HerbControllerIntegrationTest.class,
        HerbControllerTest.class,
        HerbControllerUnitTest.class,
        HerbServiceIntegrationTest.class,
        HerbServiceUnitTest.class,
        HerbServiceSecondTest.class
})
public class HerbFeatureTestSuite {

    // nothing - Test Suite Setup (annotation) is sufficient

}
