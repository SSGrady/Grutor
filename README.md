# Grutor - Learn from someone who gets it.

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
Grutor is a peer-to-peer tutoring platform where students match with eachother for private lessons based on their strongest and weakest subject.

### App Evaluation
[Evaluation of your app across the following attributes]
- **Category:** Education
- **Mobile:** Uses camera, texting, calender api.
- **Story:** Allows users to tutor another student in a subject the user excels in while learning from the same student in a subject they are poor in.
- **Market:** Anyone who is a student can use this app. Specifically, students who prefer remote learning or cannot afford a more conventional tutor would benefit from learning with Mentutor.
- **Habit:** Texting among students is highly encouraged and  keeps users coming back to the app. Additional in-app push notifications and calender updates inform the user when their next tutoring session is fast approaching.
- **Scope:** Mentutor is a user matching, texting, and calender-planning tutoring app. Students in elementary pay zero. High school and above pay $9.99 a month.

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* Register/Login
* Home
* Profile
* Messaging
* Calender API

**Optional Nice-to-have Stories**

* Other User profiles
* Camera functionality
* GPS
* Profile Add on

### 2. Screen Archetypes

* Login/Register
   * Register a new account.
* Home
   * Match with other users
   * Swipe right to message / left to ignore

* Messaging
   * Message with other students you have matched with

* Profile
   * Total Hours studied
   * View best/worst subject
   * Sign out

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Home
* Message
    * Calender (Scheduling)
* Profile

**Flow Navigation** (Screen to Screen)
* Forced Log-in -> Registration if no log in is available
* Home (Matching queue if possible) -> New contact added to Chat upon "matching"
* Messaging -> Text field "bubbles" to be implemented. 
* Proffile -> Toggle settings

## Wireframes
<img src="https://i.ibb.co/F6x93cg/thumbnail-Grutor-wireframe-V2-for-Real.png" width=600>

## Schema 
### Models

#### User
   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the user (default field) |
   | createdAt     | DateTime | date when User is created (default field) |
   | updatedAt     | DateTime | date when User is last updated (default field) |
   | username      | String   | the username that the User registered with (default field) |
   | password      | String   | the password that the User registered with (default field) |
   | email         | String   | email that was registered to the User (default field) |
   | image         | File     | image that user posts as headshot |
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
