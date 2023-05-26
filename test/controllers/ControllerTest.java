package controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.typesafe.config.Optional;
import models.DataModel;
import org.junit.Before;
import org.junit.Test;
import play.data.FormFactory;
import play.api.i18n.MessagesApi;
import play.inject.guice.GuiceApplicationBuilder;
import play.Application;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;
import play.libs.Json;
import play.twirl.api.Content;
import com.typesafe.config.Config;

import java.lang.reflect.Field;
import java.util.OptionalInt;

public class ControllerTest extends WithApplication{
  @Override
  protected Application provideApplication()
  {
    return new GuiceApplicationBuilder().build();
  }


  @Test
  public void testCreateObservation()
  {
    Http.RequestBuilder request = new Http.RequestBuilder()
            .method(GET)
            .uri("/createobservation");

    Result result=route(app, request);
    assertEquals(OK,result.status());
    assertEquals("text/html",result.contentType().get());
  }

  @Test
  public void testIndex()
  {
    ObservationController observationController;
    final ObjectNode jsonNode = Json.newObject();

    jsonNode.put("species","ORCA");


    Http.RequestBuilder requestBuilder = new Http.RequestBuilder()
            .method(GET)
            .uri("/")
            .bodyJson(jsonNode);
    Result result = route(app,requestBuilder);
    assertEquals(OK,result.status());
  }
  /**
   * Test the contentType of the result
   * expect JSON response
   */
  @Test
  public void testContectNegotiationWithJSON() {
    Http.RequestBuilder request = new Http.RequestBuilder()
            .method(GET)
            .uri("/").header("accept", "application/txt+json");
    Result result = route(app, request);
    assertEquals("Optional[application/json]", result.contentType().toString());
  }

  /**
   * Test the contentType of the result
   * expect HTML
   */
  @Test
  public void testContectNegotiationWithHTML() {
    Http.RequestBuilder request = new Http.RequestBuilder()
            .method(GET)
            .uri("/").header("accept", "html");
    Result result = route(app, request);
    assertEquals("Optional[text/html]", result.contentType().toString());
  }

  /**
   * Test the contentType of the result with undefined content type
   * expect JSON
   */
  @Test
  public void testContectNegotiationWithUndefine() {
    Http.RequestBuilder request = new Http.RequestBuilder()
            .method(GET)
            .uri("/").header("accept", "image/gif");
    Result result = route(app, request);
    assertEquals("Optional[application/json]", result.contentType().toString());
  }
}