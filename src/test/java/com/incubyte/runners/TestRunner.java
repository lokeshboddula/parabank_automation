package com.incubyte.runners;

import org.junit.platform.suite.api.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = "cucumber.plugin", value =
        "pretty," +
                "html:target/cucumber-reports/report.html," +
                "json:target/cucumber-reports/report.json," +
                "junit:target/cucumber-reports/report.xml")
    @ConfigurationParameter(key = "cucumber.glue",        value = "com.incubyte.stepdefinitions")
@ConfigurationParameter(key = "cucumber.execution.dry-run", value = "false")
@ConfigurationParameter(key = "cucumber.filter.tags", value = "@e2e")
public class TestRunner {
    // Entry point for Cucumber + JUnit 5 Platform Suite
}