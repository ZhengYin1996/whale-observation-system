package controllers;

import models.Observation;
import models.Whale;
import models.DataModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.api.i18n.*;
import com.typesafe.config.Config;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;

/**
 * The controller class in our MVC architecture
 */

public class ObservationController extends Controller {

    // Injection variables
    private final Form<Whale> whaleForm;
    private final Form<Observation> observationForm;
    private final MessagesApi messagesApi;

    // For logging purposes
    private final Logger logger = LoggerFactory.getLogger(getClass());

    // Data storage
    private final DataModel dataModel;

    @Inject
    public ObservationController(FormFactory formFactory, MessagesApi messagesApi, Config config) {
        this.whaleForm = formFactory.form(Whale.class);
        this.observationForm = formFactory.form(Observation.class);
        this.messagesApi = messagesApi;
        this.dataModel = new DataModel(config.getString("db.default.url"));

        // Get values
        dataModel.setAllObservations();
        dataModel.setAllWhales();
    }

    /**
     * Controller function dealing with HTTP requests to /
     * shows the landing page for HTML requests
     * returns a JSON object of all whales in db for JSON requests
     */

    public Result index(Http.Request request){
        dataModel.setAllWhales();
        dataModel.clearCurrentWhales();

        // Content negotiation

        // Html requester will receive the view
        if (request.accepts("text/html")) {
            dataModel.setAllObservations();
            return ok(views.html.index.render(dataModel.getAllObservationsJson(), dataModel.getAllWhalesJson())).as(Http.MimeTypes.HTML);

        // Json requester will get all whales currently stored in DB
        } else {
            return ok(dataModel.getAllWhalesJson()).as(Http.MimeTypes.JSON);
        }
    }

    /**
     * Controller function to navigate to create observation view
     * shows the add observation view for HTML requests
     */

    public Result createObservation(Http.Request request) {
        // Content negotiation
        // HTML requester gets the view
        if (request.accepts("text/html")) {
            return ok(views.html.createObservation.render(dataModel.getCurrentWhalesJson(), observationForm, whaleForm, request, messagesApi.preferred(request)));
        }
        // Timeout
        else {
            return badRequest(views.html.timeout.render());
        }
    }

    /**
     * Controller function to delete all observations from the DB
     */

    public Result deleteAllObservations(Http.Request request) {
        dataModel.deleteAllObservations();
        return index(request);
    }

    /**
     * Controller function dealing with HTTP requests to /observation
     * saves the observation in db and returns to landing page
     */
    public Result addObservation(Http.Request request){
        final Form<Observation> boundForm = observationForm.bindFromRequest(request);

        if (boundForm.hasErrors()) {
            logger.error("errors = {}", boundForm.errors());
            return badRequest(views.html.createObservation.render(dataModel.getCurrentWhalesJson(), boundForm, whaleForm, request, messagesApi.preferred(request)));
        }
        else if(dataModel.getWhales().isEmpty()) {
            return badRequest(views.html.createObservation.render(dataModel.getAllWhalesJson(), boundForm, whaleForm, request, messagesApi.preferred(request)));
        }
        else {
            Observation data = boundForm.get();
            Observation newObs = new Observation(data.getLatitude(), data.getLongitude(), data.getDate(), dataModel.getWhales());
            dataModel.insertObservation(newObs);
            dataModel.clearCurrentWhales();
            return redirect(routes.ObservationController.index());
        }
    }

    /**
     * Controller function dealing with users adding whales to observation
     * does form validation and redirects on correct input
     */
    public Result addWhale(Http.Request request) {
        final Form<Whale> boundForm = whaleForm.bindFromRequest(request);

        if (boundForm.hasErrors()) {
            logger.error("errors = {}", boundForm.errors());
            return badRequest(views.html.createObservation.render(dataModel.getCurrentWhalesJson(), observationForm, boundForm, request, messagesApi.preferred(request)));
        } else {
            Whale data = boundForm.get();
            Whale newWhale = new Whale(data.getSpecies(), data.getWeight(), data.getGender());
            dataModel.addWhaleToCurrent(newWhale);
            return redirect(routes.ObservationController.createObservation());
        }
    }
}

