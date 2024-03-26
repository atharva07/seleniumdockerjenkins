package com.flightreservation;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.flightreservation.pages.FlightConfirmationPage;
import com.flightreservation.pages.FlightSearchPage;
import com.flightreservation.pages.FlightsSelectionPage;
import com.flightreservation.pages.RegistrationConfirmationPage;
import com.flightreservation.pages.RegistrationPage;
import com.flightreservation.model.FlightReservationTestData;
import com.flightreservation.utils.Config;
import com.flightreservation.utils.Constants;
import com.flightreservation.utils.JsonUtil;
import org.testng.annotations.Parameters;
import org.testng.Assert;

public class FlightReservationTest extends AbstractTest{

    private FlightReservationTestData testData;
    // Once we are done with scripting the test file execute the test with the folllowing commands.
    // Open the terminal.
    // go to the location of the pom.xml file
    // hit the command mvn clean test
    // check the reports in the target folder of the project.
    // dont forget to push the code.

    // For executing from target folder, where we created packages 
    // navigate to docker-resources folder
    // hit the command: java -cp "libs/*" org.testng.TestNG -threadcount 1 test-suites/flightreservation.xml
    
    @BeforeTest
    @Parameters("testDataPath")
    public void setParameters(String testDataPath) {
        this.testData = JsonUtil.getTestData(testDataPath, FlightReservationTestData.class);
    }
    
    @Test
    public void userRegistrationTest() {
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.goTo(Config.get(Constants.FLIGHT_RESERVATION_URL));
        //registrationPage.goTo("https://d1uh9e7cu07ukd.cloudfront.net/selenium-docker/reservation-app/index.html");
        driver.manage().window().maximize();
        Assert.assertTrue(registrationPage.isAt());
        System.out.println(testData.firstName());
        registrationPage.enterUserDetails(testData.firstName(), testData.lastName());
        registrationPage.enterUserCredentials(testData.email(), testData.password());
        registrationPage.enterAddress(testData.street(), testData.city(), testData.zip());
        registrationPage.register();   
    }

    @Test(dependsOnMethods = "userRegistrationTest")
    public void registrationConfirmationTest() {
        RegistrationConfirmationPage registrationConfirmationPage = new RegistrationConfirmationPage(driver);
        Assert.assertTrue(registrationConfirmationPage.isAt());
        Assert.assertEquals(registrationConfirmationPage.getFirstName(), testData.firstName());
        registrationConfirmationPage.gotoflightSearch();
    }

    @Test(dependsOnMethods = "registrationConfirmationTest")
    public void flightsSearchTest() {
        FlightSearchPage flightSearchPage = new FlightSearchPage(driver);
        Assert.assertTrue(flightSearchPage.isAt());
        flightSearchPage.selectPassengers(testData.passengerCount());
        flightSearchPage.searchFlights();
    }

    @Test(dependsOnMethods = "flightsSearchTest")
    public void flightSelectionTest() {
        FlightsSelectionPage flightSelectionPage = new FlightsSelectionPage(driver);
        Assert.assertTrue(flightSelectionPage.isAt());
        flightSelectionPage.selectFlights();
        flightSelectionPage.confirmflightsButton();
    }

    @Test(dependsOnMethods = "flightSelectionTest")
    public void flightReservationConfirmationTest() {
        FlightConfirmationPage flightConfirmationPage = new FlightConfirmationPage(driver);
        Assert.assertTrue(flightConfirmationPage.isAt());
        Assert.assertEquals(flightConfirmationPage.getPrice(), testData.expectedPrice());
    }
}
