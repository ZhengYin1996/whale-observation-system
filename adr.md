# App Structure
## Context
Given that the assignment is to create a web application using the Play framework, we had to familiarize
ourselves with its architecture and decide how to organize our data both on the front and backend.

## Decision
Following the MVC design pattern, we created a controller which is responsible for handling requests to and 
from the client. It acts as the intermediary between the html views and the data models which we built. 
Since our application persists its information via H2, we created a data access layer which is responsible
for interacting with our backend database. The controller then queries the access layer and our applications
models when the time is right and distributes the information accordingly, typically via JSON.

## Consequences
The application structure follows the MVC architecture and makes the program
functions reusable, decoupling our classes and making it easier to recycle code and test our application.