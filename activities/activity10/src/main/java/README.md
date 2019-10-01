# Activity 11
---

Improvements on activity 10 viewable in source code:
* used nameless controller for main menu buttons
* combined controllers and views for AddProduct and AddCustomer into one UI class
* implemented DataAdapter interface to work with SQLite or Oracle database wrappers
note: oracle wrapper not functional, I don't think it was implemented in class though I think he just made stubs
* however, did implement an interface to choose a database file to save to at runtime, as well as create a new database file
* right now, if the database file extension is .db it uses the SQLite wrapper, and if the extension is .ora it uses the (non-functional) Oracle wrapper
* Included in pictures is the process of creating a new database at runtime and saving a product and customer to it