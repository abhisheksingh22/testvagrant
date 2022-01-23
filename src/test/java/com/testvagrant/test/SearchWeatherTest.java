package com.testvagrant.test;

import com.testvagrant.api.BaseAPIConfiguration;
import com.testvagrant.ui.BaseUIConfiguration;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.testvagrant.util.SetProperties;

public class SearchWeatherTest extends BaseAPIConfiguration {

    private Response response = null;
    private Float temp;
    private WebDriver webDriver;

    @BeforeMethod(alwaysRun = true)
    public void doBeforeMethod(){
        SetProperties setProperties = new SetProperties();
        RestAssured.baseURI = SetProperties.env.getValue("base_api_uri");
        requestSpecification = getRequestSpecification();
    }

    @Test
    public void searchWeatherByCity() {
        // get weather information for a city from API source
        requestSpecification.pathParam("city", "Singapore");
        requestSpecification.pathParam("apiKey", SetProperties.env.getValue("apiKey"));
        response = BaseAPIConfiguration.getCallResponse(requestSpecification, SetProperties.env.getValue("search_weather_api"), HttpStatus.SC_OK);
        temp = response.path("main.temp");
        Assert.assertEquals(response.path("name"), "Singapore");
        Assert.assertEquals(response.path("cod"), 200);
        // get weather information for a city from UI source
        webDriver = BaseUIConfiguration.setup_driver_and_open_browser(webDriver);
        webDriver.get(SetProperties.env.getValue("base_ui_uri"));
        System.out.println(webDriver.getTitle());
        webDriver.findElement(By.name("query")).sendKeys("Singapore");
        webDriver.findElement(By.cssSelector("div[class='results-container'] div:nth-child(1)")).click();
        String current_ui_temp = webDriver.findElement(By.xpath("(//div[@class='temp'])[1]")).getText().split("°")[0];
        System.out.println("current_ui_temp: "+current_ui_temp);
        Float temp_ui = Float.valueOf(current_ui_temp);
        Assert.assertEquals(temp, temp_ui);
    }

    @AfterMethod
    public void doAfterMethod() {
        webDriver.quit();
    }
}
