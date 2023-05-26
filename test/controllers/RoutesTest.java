package controllers;

import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;
import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.*;
import static play.test.Helpers.*;

/**
 * Tests for the routes requests
 */

public class RoutesTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    /**
     * Test the Index
     * expect http found with 200 response
     */
    @Test
    public void testIndex() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/");
        Result result = route(app, request);
        assertEquals(OK, result.status());
    }

    /**
     * Test the Error request
     * expect 404 NOT_FOUND response
     */
    @Test
    public void testError() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(PUT)
                .uri("/");
        Result result = route(app, request);
        assertEquals(NOT_FOUND, result.status());
    }

    /**
     * Test different route for createObservation
     * expect 200 OK response
     */
    @Test
    public void testCreateObservation() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/createobservation");
        Result result = route(app, request);
        assertEquals(OK, result.status());
    }

    /**
     * Test for route with action required
     * expect BAD_REQUEST response
     */
    @Test
    public void testObservation() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/observation");
        Result result = route(app, request);
        assertEquals(BAD_REQUEST, result.status());

    }

}
