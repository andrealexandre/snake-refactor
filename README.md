# Snake Refactor

This is mostly a humbling exercise in looking at code I did almost 10 years ago during my Computer Science bachelor's course.

The main goal I hope to take away from this exercise is to learn from common mistakes I used to do and build a post from this.

# Context

The initial solution was build in eclipse, so it's not working out of the gate.
Far as I remember at the time we were using Java 7.
This project seemed to have two components, the game and convenience scripts.

# Going forward

My first steps will be to support a gradle build instead, to remove the dependency from Eclipse.
Since most coding will be done in IntelliJ. 
Run in Java 7 or 8, depending on Java Swing lib support for these libraries.

# Roadmap

* [x] Organize folder structure
* [x] Implement build in gradle
* [ ] Run application using `gradle run`
* [ ] Run in Java 8
* [ ] Analyse code and pinpoint design mistakes
* [ ] Refactor and release small increments of improvements