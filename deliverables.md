# Deliverable Record

| Req # | Implemented? | Code location | Implemented by |
|----|----|----|----| 
| 2. Display whales and observations| Yes| Shown at landing page (/) endpoint, [View](/app/views/index.scala.html), [JS](/public/javascripts/landing.js) | @selfsim|
| 3. Create a whale record| Yes| Shown at  (/createobservation) endpoint, [View](/app/views/createObservation.scala.html), [JS](/public/javascripts/observation.js), good/bad form response handled in [Controller](app/controllers/ObservationController.java)| @selfsim, @ben|
| 4. Create an observation record| Yes| Shown at  (/createobservation) endpoint, [View](/app/views/createObservation.scala.html), [JS](/public/javascripts/observation.js), good/bad form response handled in [Controller](app/controllers/ObservationController.java) | @selfsim, @ben|
| 5. Search whales by date and type| Yes| Shown at landing page (/) endpoint, [View](/app/views/index.scala.html), [JS](/public/javascripts/landing.js)  | @selfsim|
| 6. REST API| Yes| |@wenbo |
| 7. Unit tests| Yes| Shown in test directory, [RoutesTest](test/controllers/RoutesTest.java), [ControllerTest](test/controllers/ControllerTest.java), [DataAccessTest](test/model/DataAccessTest.java), [DataModelTest](test/model/DataModelTest.java), [ObservationTest](test/model/ObservationTest.java),[WhaleTest](test/model/WhaleTest.java) |@zheng, @wenbo|
| 8. ADRs| Yes| [ADR1](adr.md), [ADR2](adr2.md) | @ben, @wenbo, @selfsim|
| 9. MVC using Play| Yes| Shown in the [app](app/) directory, data transport demonstrated in [Controller](app/controllers/ObservationController.java) class | @all|
| 10*. Form validation + UI tweaks| Yes| Form validation through Twirl constraints in model classes [Observation](app/models/Observation.java), [Whale](app/models/Whale.java), UI in [Landing](/app/views/index.scala.html), [AddObservation](/app/views/createObservation.scala.html) views  | @selfsim|
| 11*. H2 persistent data store| Yes| [Data Access Layer](app/models/DataAccess.java) | @ben|

*=bonus