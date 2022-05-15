# Assessment_Test

-> Show Simple rounded android Logo (Splash Screen)
- Use fade animation to display the logo for 4 seconds
-> List all of the current popular movie (Home Screen)
- Display latest movie first
- List should support pagination
- Should support pull to refresh functionality
- Use pagination (fetch one page at time and load more as user scrolls down)
- Show a red colored Snackbar if the user is trying to load more and no further data is available or
API fails.
- Each movie cell item should display image, title and release data (eg "May 2024")
- Each movie item should be clickable and should present movie details on click(Use Navigation
Component)
- Whatever data is populated in this screen needs to be persisted(Use RoomDb ).So that user can
view a list of movies previously fetched in offline mode.
->Display Movie Details (Second screen)
- Use Shared Element Transitions
- Movie details should display image, title and description
- Button to watch trailer(On click open player for watching movie trailer )
- Multiline TextView for user to add comment (Save this comment in Shared Preferences) and
display it next time when you reopens the Home Screen as green Toast for 4 seconds.
- Display only front camera image in Imageview on click of Button with text 'Set Profile'.
