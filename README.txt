3D Space Strategy Game
======================
Written by:
Christopher Hittner

Changes List
============
0.1.0.1 [10:03 PM 3/7/2014] - Created main.CycleRunner.java for executing in-game physics calculation cycles
                              Created entities.Entity.java to serve as a template for future characters and objects in the game

0.1.0.2 [10:05 PM 3/7/2014] - Added executeCycle() to CycleRunner.java

0.1.0.3 [10:05 PM 3/7/2014] - Added constructor to entities.Entity.java
                              Added relevant variables to entities.Entity.java
                                  x-y-z coordinate variables
                                  x-y-z movement variables
                                  mass and radius
                                  Newton's Gravitational Constant
                              Added methods to entities.Entity.java
                                  move() method changes the x-y-z coordinates of the Entity object based on its motion
                                  gravitate(Entity other) method causes two objects to be pulled together by gravitational forces

0.1.0.4 [7:09 AM 3/8/2014] - Added testCollision(Entity other) method that tests whether two Entity objects are colliding and returns a boolean

0.1.0.5 [7:15 AM 3/8/2014] - Added main.EntityList.java to store and manage objects of Entity and its subclasses
                                 Added ArrayList<Entity> entities to store all Entity objects in use

0.1.0.6 [7:20 AM 3/8/2014] - Added methods to main.EntityList.java
                                 addEntity(Entity e) adds an Entity object to the list of Entities
                                 executeGravity() calculates gravity for all Entity objects in use
                                 executeMovement() calculates movement for all Entity objects in use

0.1.0.7 [7:24 AM 3/8/2014] - Added integer constant called cyclesPerSecond to main.CycleRunner.java
                                 Stores how many times the game will calculate everything in one second

0.1.0.8 [7:31 AM 3/8/2014] - Modified methods in entities.Entity.java to use cyclesPerSecond constant

0.1.0.9 [9:37 PM 3/8/2014] - Added methods to entities.Entity.java
                                 Return the x-y-z coordinates and velocities of object

0.1.0.10 [9:38 PM 3/8/2014] - Added Tester.java for testing features of the code

0.1.0.11 [9:39 PM 3/8/2014] - Added getEntity(int index) method to main.EntityList.java

0.1.0.12 [9:25 AM 3/9/2014] - Added entities.planetaryBodies.CelestialBody.java to hold data for planets and stars
                                  Created necessary constructors
                                  Instantiated required variables to track atmospheric stats

0.1.0.13 [9:30 AM 3/9/2014] - Added overloaded method getPressure() to entities.planetaryBodies.CelestialBody.java
                                  Takes either an Entity or a set of x-y-z coordinates as a parameter
                                  Returns the atmospheric pressure at a certain coordinate

0.1.0.14 [9:45 AM 3/9/2014] - Added entities.ships.Ship.java to hold data for ships in the game
                                  Created necessary constructors
                                  Instantiated variables for tracking ship stats

0.1.0.15 [10:23 AM 3/9/2014] - Added addRotation(double XY, double Z, double magnitude) method to entities.ships.Ship.java
                                   Accelerates the ship's rotation speed

0.1.0.16 [10:33 AM 3/9/2014] - Added rotate() method to entities.ships.Ship.java
                                   Rotates ship based on rotation speed

0.1.0.17 [11:12 AM 3/9/2014] - Added autopilot() method to entities.ships.Ship.java
                                   Used for automatic ship rotation

0.1.0.18 [2:01 PM 3/9/2014] - Added methods to entities.ships.Ship
                                  accelerate() accelerates the ship based on its throttle setting
                                  setAcceleration(double thrustPercent) sets the percentage value for the throttle

0.1.0.19 [2:07 PM 3/9/2014] - Fixed rotational axes in entities.ships.Ship.java
                                  XY and Z are now XZ and Y to match with Cartesian coordinate system

0.1.0.20 [2:13 PM 3/9/2014] - Split ArrayList<Entity> entities in main.EntityList.java into ArrayList<CelestialBody> bodies and ArrayList<Ship> ships
                                  Allows for easier usage of various Entity objects
                                  Modified methods for compatibility

