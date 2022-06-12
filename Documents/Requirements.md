Requirements
## Technical:
	- no GUI
	- interactions ONLY via APIs
	- we create a microService
	- built that can be extended with external functionality
	- scalable individual components
	- Java 11
	- Spring Boot framework & Gradle

## Domain:
	- 1 hour slots
	- 8 to 10
	- Team Sports (can not make a reservation on your own)
	- group reservations
	- Creation of groups functionality
	- You can leave the group or you can be added
	- Group deletion is allowed to team leader/admin (not hard req)
	- Associated to max 2 groups in one day
	- Big fields are cancelled 2 hours ahead
	- 5 or less capacity (1 hour ahead)

## Security:
	- Authentication (user/administrator)
	- Unique NETID Account 
	- Passwords (stored safely)[Spring security]

##Sports center:
General:
- can be reserved (8 AM to 10 PM)

Equipment:
- rent sport equipment (named and related to a sport)
	- amount to rent [no limit mentioned]
- log last use

Halls/nl.tudelft.sem.template.field.Field:
	- sport halls/sport fields (Make up fields/facilities (TU Delft X))
	- capacity (how many can use)
capacity should be flexible, admin should be able to change it
	- 1 hour slots to be reserved 
	- maximum 2 slots per user
- admins can see who uses the equipment
- log last use
	- People should see their reservations (can cancel a res)
	- Some reservations have to be done in groups

Users:
- Regular user:
  - netID (String)
  - password (Spring security) (don’t need single login)
  -max 2 reservations a day
  -Can cancel their reservations
  -Can be part of a group: one person can add them to a group (NOT invited based) / only team leader can remove group / (can individual person leave group?yes?)

	- Administrator(s) (TA mentioned multiple): 
- can add new equipments
- NOT add new fields
- can change capacity of rooms (e.g. covid related)
- can see who booked which room and which equipment (wasn’t sure… can they delete reservations??)
- Can see who booked an equipment or field (last) ( e.g.to see who broke it)

Database:
	- 

Extra:
- No teacher
- No paid membership

Admin features:
- Admin can make account 
- Admin can create equipment
- Admin can set capacity 
- User can make reservation (single or group) IS ADMIN A REGULAR USER WITH EXTRA FEATURES??
- Admin can see overview of all reservations
- Admin can delete reservation

User features:
- User can make an account 
- User can make group and add & delete people
- User can make reservation  for room (single or group)
- User can make reservation for equipment 
- User can see his/her reservations as well as group reservations 
- User can delete reservation 
- User can see overview of groups he/she is in
 -User can leave group

Questions for TA on tuesday
- can users who are added to groups leave?
- User can see overview of groups he/she is in?


