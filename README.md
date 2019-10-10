Demo application for Sam's Club. The main flow of the app starts at the
fragment or activity level, nagivating through a delegate as an
intermediary layer, eventually getting to the api service layer. Breaking
things down this way allows for optimal separation of concerns, with scope
of functionality effectively being modularized. Activity and fragment
levels are intentionally to contain predominantly UI lifecycle callback
implementations, along with functions that effect any of the primary
UI components of the hierarchy. An extension function is used to
house the pagination functionality. Dependency injection is used
to vary the network layer for purposed of test mocking requirements.
In the product details, a view pager is used, with a crafty method
to facilitate the shared object transition from the main product list.
Back navigation from the product details is also crafty accommodating
the desire to retain proper shared object transition functionality,
considering it's view pager nature. Overall for recycler items
an MVVM pattern is used, with the view models sole responsibility
to manage the binding of model data data to desired view components. A
multi type data bound adapter structure is used to facilitate an optimal
data bindings pattern thats useful app wide with a single implementation.
Saved state is retained in both the main product list, and the product
details activity accommodating functional configuration change events
as well as restoration of the app from a backgrounded state.
