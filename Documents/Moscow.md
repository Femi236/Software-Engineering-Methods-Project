##Must Haves:
Domain:
- 1 hour slots
- 8 to 10
- no overlapping reservations of equipment or fields

Security:
- Authentication (user/administrator)
- Unique NETID Account
- Passwords (stored safely)[Spring security]

Regular user:
- netID (String)
- password (Spring security) (don’t need single login)
- can see their reservations
- delete reservations

Admin user:
- can see reservations
- can change capacity of rooms (e.g. covid related)

Equipment:
- rent sport equipment (named and related to a sport)
- amount to rent [no limit mentioned]
- log last use

Halls/nl.tudelft.sem.template.field.Field:
- sport halls/sport fields (Make up fields/facilities (TU Delft X))	
- 1 hour slots to be reserved
- admins can see who uses the fields
- log last use
- People should see their reservations (can cancel a res)
- capacity should be flexible, admin should be able to change it


Database:
- ...


##Should Haves:
Domain:
- Team Sports (can not make a reservation on your own)
- group reservations
- Creation of groups functionality
- You can leave the group or you can be added
- Group deletion is allowed to team leader/admin (not hard req)
- Associated to max 2 groups in one day
- Big fields are cancelled 2 hours ahead
- 5 or less capacity (1 hour ahead)

Regular user:
- max 2 reservations a day
- Can cancel their reservations
- Can create group
- Can be part of a group: one person can add them to a group (NOT invited based) / only team leader can remove group / (can individual person leave group?yes?)

Administrator(s) (TA mentioned multiple):
- Have multiple administators
- can add new equipments
- NOT add new fields
- can see who booked which room and which equipment (wasn’t sure… can they delete reservations??)
- Can see who booked an equipment or field (last) ( e.g.to see who broke it)

Halls/nl.tudelft.sem.template.field.Field:
- capacity (how many can use)
- maximum 2 slots per user
- Some reservations have to be done in groups


##Could Haves:
- ...

##Won’t haves:
Extra:
-  No teacher
-  No paid membership


##Non-functional requirements:
Technical:
- no GUI
- interactions ONLY via APIs
- we create a microService
- built that can be extended with external functionality
- scalable individual components
- Java 11
- Spring Boot framework & Gradle
