# B_Squad
We are the A team.

This read me is for assignment 3 and 4

So for assignment 3 Brandon Snow worked on the inventory restructure. Brandon Izz. worked on the complete integration of the part functions

in the inventory:
-drop down  box when editing the part name or entering a part name. In this drop down box is a part list that is updated when the view is opens. so if a part exist at the time of the window opening it will insert. If a user deletes that part from a table with the inventory view open they will have to delete that part from inventory manually. (this is a small issue as a inventory person i am taking is different from part person)

-the inventory updates everytime the user edits a record and pulls a fresh copy from the inventory so the view is always right

-to get the part view to open pretty quickly i save a local copy of the database that is refreshed everytime i edit a record

-the add inventory just pulls blank data and gets ready for a insert

-the two gateways only create one connection at a time and send a simple statment. Its very simple and i hope to build this logic out as i go on.

-i don't check length of strings because it was not stated to!!! I did limit the quan and id to 11 digits though as stated it 


//Brandon Irizarry wrote this part

in the PartList and PartDetail methods:

- part list is updated every time the user edits a record and pulls the records, and opens a part detail view for further editing

- the parts that get created define the name database that can be used when editing the inventory detailed view 

- add part initializes an empty form and ports the typed data, with error checks, to the SQL database on successful add/save

- currently have 4 gateways, can be condensed to one in later versions.

 
 
 
 
 
 
 
 
 assignment 4
 -seperate everything into packages -------done

 -write code for the container stuff-----done
 -add a template package-------done
 -add gui elements for template
 
 
 -change up the requirements for the parts list and inventory list--done
 
 
 -clean up cloud code
 
 --change check that allows only one window 
 
 
 
 
 
 
 
 
 Brandon Snow Read me assignment 4:
 
 
 -I did all locking mdi structure restructured the old code to fit within a master frame and i helped complete the template part

 -None of the views close when a user saves cause it wasn't in the constraints

 -locking is unlocked by hitting save or by hitting the x in the top right corner of the box. The second part was a bitch to get working

 -i fixed major logic issues with the old stuff to fit this project 
 
 -added ability to have a desktop and added a background to make assignment 5 easier

 
Brandon Irizarry Read me assign 4:
 
 -I did all the template work 
 
 -Added all the template GUI's, and complete template functionality within the database, including the specified constraints
 
 
 
 
 Brandon snow readme assignment 5:
 
 -i did the session state stuff
 -i create a hash table with username and password its not that efficient but it works.
 -i pass the session to all the controllers so the controllers can control a users access to a package
 -the user can logg in and log out as whoever right now but i was more worried about getting the permissions set as ive never done anything like the permissions based access stuff.
 -I added the user state checkafter being instaiated cause i didnt wanna do throw catch but it does check with a simple int
 
 -when a user hits a button it checks the state to see if its allowed 
 
 
 Brandon Irizarry readme assign5:
 
 -I did all the product addition stuff, as well as the checks against all relevant tables for when that happens
 -Added inventory constraints for product additions
 -manipulated the SQL database to handle a new id field, and made all relevant query changes
 -Also added some more readability to some of the combo-boxes and parts detail view of a template
 
 