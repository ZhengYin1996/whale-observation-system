# Front vs Backend data manipulation
## Context
On the landing page are some filters which show a subset of the data that the view receives, say according to some
constraint that the user chooses. The decision we had to make was whether to have the data filtered on the client or the server side.


## Decision
We decided that it makes more sense to filter the data on the client side: in our case, we operated on
the passed in JSON string using javascript built in filtering functions as well as our own. This makes sense,
because the view receives the entire data set upon render. It would be wasteful to make another call
to the backend to get the subset of data that we need, because we already have it locally. Additionally, if we needed
to use the same filtering in a different view, we could abstract the filtering functionality into a new js file and include it
in both views.

## Consequences
The consequences include less server load, and if the application had many concurrent users the benefit of this would 
be substantial. It makes sense to minimize the amount of server requests, if the same functionality can be achieved locally.