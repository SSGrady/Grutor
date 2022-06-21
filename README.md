# Grutor - Learn from someone who gets it.

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
Grutor is a peer-to-peer tutoring platform where students match with each other for private lessons based on their strongest and weakest subject.

### App Evaluation
[Evaluation of your app across the following attributes]
- **Category:** Education
- **Mobile:** Uses camera, texting, calender api.
- **Story:** Allows users to tutor another student in a subject the user excels in while learning from the same student in a subject they are poor in.
- **Market:** Anyone who is a student can use this app. Specifically, students who prefer remote learning or cannot afford a more conventional tutor would benefit from learning with Grutor.
- **Habit:** Texting among students is highly encouraged and  keeps users coming back to the app. Additional in-app push notifications and calender updates inform the user when their next tutoring session is fast approaching.
- **Scope:** Grutor is a user matching, texting, and calender-planning tutoring app. Students in elementary pay zero. High school and above pay $9.99 a month.

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* Register/Login
* Feed Activity with Home, Lessons, Messaging, and Profile navigation bar fragments.
* Instant-messaging between two users
* Tutoring 'Details' Activity
* Users can generate an event on their phone calendar with tutoring details

**Optional Nice-to-have Stories**

* Users can set an urgency level to their request
* Camera functionality
* Scanner functionality on the camera
* Allow Push notifications alert upon signing up
* Toast alert if the user registers a new account without adding in a photo for their User profile
* Home feed displays an image view of the sun, sun-set, or moon alongside the User welcome message depending on the User's local time.
* Users can use navigate to a settings activity where they can sign out, change their grade, and change their best subject

### 2. Screen Archetypes

* Login/Register
   * Register a new account.
   * Users sign up with email and complete registration by selecting their grade level, best subject, and uploading a profile picture.
* Home
   * Welcome message
   * Users can choose a subject and request a tutoring session:
   * Recycler view populated with School subject cards
   * Users can tap on a school subject card
   * Onclick intent is fired to the Detail Activity
   * Add details & confirm

* Lessons
  * Students are matched if their grade & best subject corresponds to the requested subject
  * Users can tap a chat icon besides 'matched students' to begin messaging

* Messaging
   * Users can instant-message one another
   * Users can tap on a Zoom icon to generate an event on their phone calendar (auto-filled with Detail activity details)

* Profile
   * Total Hours studied
   * View best subject
   * Settings:
     * Sign out

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Login Activity
  * Sign in
  * Sign Up
* Home
* Lessons
  * Detail
* Messaging
    * Chat Activity (direct messages)
    * Zoom API -- ICS file
* Profile

**Flow Navigation** (Screen to Screen)
* LoginActivity
  * Sign in -> Feed Activity (Home)
  * Sign Up -> Registration Fragment -> Feed Activity (Home)
* Home -> Details
       -> Lessons/Messaging/Profile
* Lessons -> Home/Messaging/Profile
* Messaging -> Home/Lessons/Profile
            -> Chat Activity (direct messages) -> Zoom API -- ICS file -> User's mobile Calendar app pop up
* Profile -> Home/Lessons/Messaging
          -> Settings -- log out button -> LoginActivity

## Wireframes
<img src="https://i.ibb.co/F6x93cg/thumbnail-Grutor-wireframe-V2-for-Real.png" width=600>

## Schema 
### Models

#### User
   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the User (default field) |
   | createdAt     | DateTime | date when User is created (default field) |
   | updatedAt     | DateTime | date when User is last updated (default field) |
   | username      | String   | the username that the User registered with (default field) |
   | password      | String   | the password that the User registered with (default field) |
   | email         | String   | email that was registered to the User (default field) |
   | profileImage  | File     | image that user posts as headshot |
   | grade         | Number   | the grade of the author |
   | hoursCount    | Number   | number of hours that has the user has cataloged using the calender api |
   | worstAt       | String   | the subject that the user has most trouble with |
   | bestAt        | String   | the subject that the user the least trouble with |
  
#### Message
   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the Message (default field) |
   | createdAt     | DateTime | date when Message is created (default field) |
   | updatedAt     | DateTime | date when Message is last updated (default field) |
   | message*      | String   | the text field written by one User to another |
   | userFrom      | Pointer  | pointer to the User who sends a message |
   | groupchat     | Pointer  | pointer to a Groupchat class object |
   
#### Groupchat
   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the Groupchat (default field) |
   | createdAt     | DateTime | date when Groupchat is created (default field) |
   | updatedAt     | DateTime | date when Groupchat is last updated (default field) |
   | participants  | Array    | array of User pointers who are participating in the chat|
   
   
   
### Networking
- [Add list of network requests by screen ]
- [Create basic snippets for each Parse network request]
- [OPTIONAL: List endpoints if using existing API such as Yelp]




### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype
