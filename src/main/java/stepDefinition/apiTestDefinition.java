package stepDefinition;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.*;
import java.util.stream.Collectors;

public class apiTestDefinition {
    public static Response response;
    public Map<String, Map<String, Object>> bpi;

    public apiTestDefinition() {

    }

    @When("I call create API <endpoint>")
    public void iCallCreateAPIEndpoint(DataTable table) {
        try {
            response = RestAssured.given().when().log().all().get((String) table.asList(String.class).get(0)).then().log()
                    .all().extract().response();

        } catch (Exception e) {
            String errorMsg = String.format("Error while sending request : %s", e.getMessage());
        }
    }

    @Then("I validate the status code is {int}")
    public void iValidateTheStatusCodeIs(int statusCode) {
        Assert.assertEquals(response.getStatusCode(), statusCode);
    }

    @And("I validate below BPI")
    public void iValidateBelowBPI(DataTable table) {
        List<String> listOfBpi = table.asList();
        JsonPath path = new JsonPath(response.body().asString());
        bpi = path.getMap("bpi");
        List<String> expectedResult = new ArrayList<>();
        Iterator<Map.Entry<String, Map<String, Object>>> test = bpi.entrySet().iterator();
        while (test.hasNext()) {
            expectedResult.add(test.next().getKey());
        }
        Assert.assertEquals(listOfBpi.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList()),
                expectedResult.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList()));
    }

    @And("I validate description {string} for bpi  {string}")
    public void iValidateDescriptionForBpi(String description, String bpiType) {
        Iterator<Map.Entry<String, Map<String, Object>>> test = bpi.entrySet().iterator();
        while (test.hasNext()) {
            if (test.next().getKey().equalsIgnoreCase(bpiType)) {
                Assert.assertEquals(test.next().getValue().get("description"), description);
            }
        }
    }
}
