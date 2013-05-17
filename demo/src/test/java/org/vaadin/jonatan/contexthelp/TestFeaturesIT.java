package org.vaadin.jonatan.contexthelp;

import org.junit.runner.RunWith;
import cucumber.api.junit.Cucumber;

/**
 * Get JUnit to execute Cucumber tests.
 */
@RunWith(Cucumber.class)
@Cucumber.Options(features={"src/test/resources/features"},
        format={"html:target/cucumber/features.html"})
public class TestFeaturesIT {
}
