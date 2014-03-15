3D Space Strategy Game
======================
Written by:
Christopher Hittner

Description
===========
This game is designed to provide an accurate simulation of space combat. The
game consists of opposing factions that fight in space with the sole purpose of
defeating the other(s).

Planned Features
===============
++ Implemented
+- Partially Implemented
-- Not yet implemented

Ships ++
Various types of ships +-
Gravity ++
Physics system +-
    Will always be partially implemented; could always be improved
Ship controls +-
    The code exists, but it is merely a framework
Health system +-
    Only health statistic is currently available

Version History
===============

v. 0.1.0
    Created Tester.java
        Used for testing features of the code
    Created CycleRunner.java
        Runs cycles in the game's physics
    Created EntityList.java
        Tracks Entity objects
    Created entities.Entity.java
        Stores data for an Entity with coordinates and speed
    Created entities.planetaryBodies.CelestialBody.java
        Stores data for massive planets
    Created entities.ships.Ship.java
        Holds the data and methods for a spaceship
