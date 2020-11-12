# SimpleSpeedo #

As the title suggests, SimpleSpeedo is a pretty straightforward Android application that monitors your speed live and offers you few extra functions such as speed limit exceed
visual warnings and loggings of such events.

* # Speed Monitoring #
SimpleSpeedo can give show your current speed live and precisely through a comprehensive UI both in kilometers per hour and miles per hour.

* # Speed Limit Exceedances #
If you surpass a predetermined speed limit (currently stored in the database but in the future will add custom support as well), the UI's color will switch from green to red and
the event of your exceedance will get logged into the SQLite database including your username, coordinates of the event and timestamp.

* # Viewing Exceedances #
The user can view all of his exceedance log events by their timestamp and after a simple tap on each one, it's possible to see further details about them. It is also possible to see
on a map (powered by Google Maps API) markings that represent all the places that you and other users exceeded the predetermined speed limit.

* # To be done #
App will receive a new icon and SimpleSpeedo will be added as its name.

Optimizations on GPS usage.

Custom/Dynamic speed limits.

Connection to Firebase.
