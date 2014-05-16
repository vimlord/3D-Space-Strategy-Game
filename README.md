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
================
Currently, the engine is complete. But, there is much work to be done before the engine can be considered complete. My intentions are currently to write a server-client system to host the game. The list of stuff to add is below

Server
    Runs physics and game calculations
    Create server code
        Capability to send several types of objects to hold client data
            Entity lists need to be sent to allow for the clients to output the map on the interface
            Statistics must be sent to be displayed on the interface
        Capability to receive requests
            Ship Orders
            Data requests for user input
Client
    Must be able to view and display data
        Selecting Entity objects and keeping track of them
        Drawing Entities on the screen
        Recognizing Ship objects and giving orders
    Menus
        Allow navigation through game features
        Buttons
            Allos navigation between menus
        MenuList
            Stores and manages menus
    User Interface
        Shows map
        Shows statistics
Engine
    Bug fixes for any new issues that arise

Version History
===============

v. 0.1.0
    Created Physics engine
        Gravitational physics
        Created Entity classes to work with physics engine
            Ships
            CelestialBodies
            Projectiles
            Structures
    Created Tester class
        Allows for features to be tested
        Shows a 3D environment
