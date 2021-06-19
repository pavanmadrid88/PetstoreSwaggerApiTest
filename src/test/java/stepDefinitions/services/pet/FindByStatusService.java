package stepDefinitions.services.pet;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import models.response.pet.FindByStatusResponsePojo;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stepDefinitions.services.base.BaseService;
import utils.TestUtils;

import java.util.HashMap;

public class FindByStatusService extends BaseService {

    private static Logger logger = LoggerFactory.getLogger(FindByStatusService.class);
    FindByStatusResponsePojo[] findByStatusResponsePojo = null;
    Response response;

    @Given("a valid pet status {string}")
    public void isPetStatusValid(String status) {
        String validStatus = properties.getProperty("validPetStatuses");
        if (validStatus.contains(status)) {
            logger.info("given status:" + status + " is valid");
        } else {
            Assert.fail("given status:" + status + " is invalid!");
        }
    }


    @When("I retrieve pets by status {string}")
    public void iRetrievePetsByStatus(String petStatus) {
        String endPoint = properties.getProperty("petFindByStatusEndpoint");
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("status", petStatus);
        response = restDriver.getRequestWithQueryParams(endPoint, queryParams);
        logger.info("Response:" + response.getBody().asPrettyString());
    }

    @Then("I should see response status code as {string}")
    public void iShouldSeeResponseStatusCodeAs(String expectedResponseCode) {
        TestUtils.validateResponseStatusCode(response, Integer.parseInt(expectedResponseCode));
        logger.info("Response status code validation:PASS!:" + response.statusCode());
    }

    @And("response time should be less than {string} milliseconds")
    public void responseTimeShouldBeLessThan(String expectedResponseTimeInMilliSeconds) {
        TestUtils.validateResponseTime(response, Long.parseLong(expectedResponseTimeInMilliSeconds));
        ;
        logger.info("Response time validation:PASS!:" + response.getTime());
    }

    @And("I should be able to see details of pet {string}")
    public void iShouldBeAbleToSeeDetailsOfPet(String petCategoryName) {
        findByStatusResponsePojo = response.getBody().as(FindByStatusResponsePojo[].class);
        int petDetailsValidationFlag = 0;

        for (int i = 0; i < findByStatusResponsePojo.length; i++) {
            String actualPetCategoryName = findByStatusResponsePojo[i].getCategory().getName();
            if (actualPetCategoryName.trim().toUpperCase().equals(petCategoryName.toUpperCase().toString())) {
                petDetailsValidationFlag = 1;
                logger.info("Pet category name:" + petCategoryName + " found.Here are the pet details whose Id is:" + findByStatusResponsePojo[i].getId() + ";Pet Name:" + findByStatusResponsePojo[i].getName() + ";Pet Status:" + findByStatusResponsePojo[i].getStatus());
            }
        }

        if (petDetailsValidationFlag == 0) {
            logger.warn("Pet details NOT found for:" + petCategoryName);
        }

    }
}
