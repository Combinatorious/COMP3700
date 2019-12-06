# COMP3700 Project 3

### Overview

Since I mostly had multi-user functionality up and running in my previous project, I didn't have much to add for this project except implementing a few of the user types' features. I also cleaned it up a little and added a dark mode, since everyone adds a dark mode these days.

* `doc/` contains the design document
* `src/` contains all of the currently used java files
* `data/` contains the database used in the demo
* `test/` contains a link to a youtube video of the demo

---

### Features added

* User management for admin
* Purchase view for management
* The ability for each user (of any type) to update their username and password
* UI alignment fixes and cleanups
* Dark mode

---

### Design description and potential improvements

Since the server interaction is implemented through the DataAdapter interface providing the same behavior of a local database, all of these changes were pretty simple and just involved a few UI additions. I did have to change some logic in my table display model to be able to display the user database and update it with changes to the table UI correctly. If I were to have another go at it, I would have used a more generic scheme to implement table displays and edit displays for each of the items stored in the database (users, customers, products, purchases). Since a lot of my views are pretty similar it would have benefitted to subclass some template frames and gone from there. 