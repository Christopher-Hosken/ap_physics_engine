# AP Physics Engine

AP Physics Engine is a 3D Java based program that allows you to simulate concepts that are learnt in AP Physics 1.

## Requirements
Requirements: <br>

[Java SE 11 (LTS)](https://www.oracle.com/java/technologies/javase-downloads.html) <br>

[JavaFX 11](https://gluonhq.com/products/javafx/) <br>

[Jogl](https://jogamp.org/deployment/jogamp-current/archive/)

## Installing Dependencies
The needed dependencies should be built into the program. However if not,

1. Install JavaFX for your preferred IDE
2. Follow the [JOGL installation guide](https://www.tutorialspoint.com/jogl/jogl_installation.htm)
3. Create the folder `C://src//jogl`, and paste the copied files obtained from the installation guide. (If you are in linux, you will need to change the source paths)

## User Interface
There are 3 panels in the app: [Header](https://github.com/Christopher-Hosken/ap_physics_engine/blob/main/README.md#header), [Viewport](https://github.com/Christopher-Hosken/ap_physics_engine/blob/main/README.md#viewport), and [Properties](https://github.com/Christopher-Hosken/ap_physics_engine/blob/main/README.md#properties).

### Header
The header panel is located at the top of the Physics Engine application. There are 7 buttons that do basic functions in the app.

![logo]()
Logo
: Reset the app. This will destroy all objects in the scene.

![menu]()
Menu
: Toggle the visibility of the properties panel *(toggleable)*.

![cube]()
cube
: Generate a default cube at the world center.

![wire]()
Wire
: Toggle wireframe view of all the objects *(toggleable)*.

![info]()
Info
: Open README file.

![bug]()
Bug
: Open Github issues page.

![quit]()
Quit
: Exit the application.

Any active or pressed buttons will have a highlighted glow around them. As well as this, grabbing and dragging the header will move the entire application.

### Viewport
The viewport has been designed for easy control. Users are able to zoom, pan, and rotate around the scene.

#### Navigation

**Mouse and Keyboard**
zoom
: scroll wheel.

pan
: shift-rightmouse

rotate
: alt-rightmouse

reset
: home

**Trackpad and Laptop Keyboard**
zoom
: something

pan
: something

rotate
: something

reset
: something

#### Shortcuts

pause/play
: spacebar

reset simulation
: alt-f

reset object location
: alt-g

reset object scale
: alt-s

delete
: x *(or del)*

#### Selection
To select and object, simply hover over it with your mouse and left click. Selected objects should have a yellow highlight, and its color should brighten. There is no way to deselect an object, and you cannot select more than one object.

#### Clipping
If you start zooming in or out, you may reach a point where your objects start to dissapear *(or appear to be cut off)*. This has to do with limtations with the 3d rendering engine, and can be fixed in the properies panel. 

### Properties
The properties panel is where users will spend most of their time. Here they can customize the scene and prepare the objects for simulations.
