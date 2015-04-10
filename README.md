Before everything we make a transit table, that holds the important nodes and the best routes among them. This table is stored in the application.

Then we can calculate desired routes based on the transit table: 1, With a heuristic algorithm we move the start- and end nodes to the closest nodes from the transit table. 2, Then we can use the routes, that were stored previously as the best routes in the transit table.

The application has an option to use the heuristic algorithm for the entire route calculation. Then you can compare these two options, and decide which you want to take.
