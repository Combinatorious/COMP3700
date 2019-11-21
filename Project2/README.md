# COMP 3700 Project 2
---
For project 2 I moved everything to a client/server architecture. Since I wanted my app to achieve mostly the same functionality that it did on the local database implementation, I decided rather than redesign UIs I could just implement the `DataAdapter` interface I previously used with my local database. So I use a network adapter that implements said interface, and for each request it just sends that along to the server, which pulls from the local database also implementing said `DataAdapter` interface.

With that achieved I can use my previously designed screens which display products, customers, and purchases in the database in a table, and also allow data manipulation through that table. The server then functions just as the local database would. 

I did redesign the UI to implement the separate users with their respective logins, so that specific functionality is limited to specific user types (admins, managers, cashiers, and customers). I also have the customer views limited to their own orders, and only able to make purchases locked to their specific customer IDs. 

### Folders

`/doc/` the design document for project 2

`/data/` the database used for testing

`/src/` all of the source files for this project and previous projects, many of them currently unused by the current implementation

`/test/` a youtube link and description of the test video