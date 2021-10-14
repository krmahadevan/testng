
plugins {
    id("testng.java-library")
}

dependencies {
    implementation(projects.testngCollections) {
        because("Lists.newArrayList")
    }

    implementation(project(":testng-shaded", configuration = "shadow"))

    testImplementation("org.testng:testng:7.3.0") {
        because("core depends on assertions and we need testng to test assertions")
    }
    testImplementation(projects.testngTestKit)
}
