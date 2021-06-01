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

![Panels](https://i.imgur.com/SvlJYUp.png)

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

#### Scene Properties
The scene properties

### Object Properties
The object properties panel will appear when an object is a selected. Most of the controls are pretty self explanatory.

location
: set the (x, y, z) location of the object.

scale
: set the (x, y, z) scale of the object.

color
: set the display color of the object.

show origin
: show the origin point of the object.

show normals
: override the display color of the object with the object normals.

active
: toggle whether the object is passive or active (can be affected by forces).

velocity
: set the initial (x, y, z) velocity of the object.

velocity
: toggle the velocity vector visibilty.

acceleration
: toggle the acceleration vector visibility.

## Demo Scene
The demo scene is a premade physics simulation that is computed entirely by the physics engine. This scene is used to demo the capabilities of the Physics engine.

## The Physics
Although the Physics engine was planned to contain more concepts from AP Physics 1, many features had to be removed due to time concerns. This means that only Forces and Momentum were able to be implemented, and the only objects in the scenes were cubes.

### Gravity
Applying gravity was very simple. Every time the viewport updated I would apply a gravitational force on the object. From there, the object would then apply that force to change its velocity and position.

1. Apply force to object.
2. The Object divides the applied force by it's mass to obtain the acceleration. `*(F = ma, a = F/m)*`
3. The acceleration is then added to the velocity vector. `*(vf = vi + at) t is always 1 in this case, so (vf = vi + a)*`
4. The position of the object is updated based on the velocity. `*(d = vt) t is always 1 in this case, so (d = v)*`
5. Repeat every viewport update.

This technique of using forces was written in mind to make room for other applied forces, but due to time limitations I was unable to add more.

### Collisions
To detect where the cubes were colliding, I implemented a basic overlap test. The main idea for this came from an MDN article about [3D collision detection](https://developer.mozilla.org/en-US/docs/Games/Techniques/3D_collision_detection). I iterated through every vertex in the cube (only 4), and checked if they were inside the bounds of another object.

```java
for (vec3 point : object.verts) {
  if ((point.x >= cube.minX && point.x <= cube.maxX) &&(point.y >= cube.minY && point.y <= cube.maxY) && (point.z >= cube.minZ && point.z <= cube.maxZ)) {
    return true;
  }
}

return false;
```

This simple collision detection allowed me to see when two cubes were intersecting, regardless of their scale or position. Once the program detects a collision, it cancels out a degative force.

### Transferring Momentum
Once the engine detects a collision, it transfers the momentum from one object to the other.

if the target object is not active, the momentum being transferred will esentiallly dissapear. *(This is not possible in the real world, but can be done digitally)*
## Lessons Learnt
