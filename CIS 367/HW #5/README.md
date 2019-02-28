# Installation Steps

1. Clone this repo

   ```bash
   git clone https://gitlab.com/hans.dulimarta/cs367-mvp-transformation.git
   ```

2. Install all the required Node packages to build and run the program:

   ```bash
   yarn install
   ```

3. Run the program by typing

   ```bash
   yarn serve
   ```

   to start a local web server at port 3000 (the default).

4. Use your favorite browser to open the URL `localhost:3000` you should see a perspective view of a 3D axes, a spinning hexagon (around its own Z-axis), and a revolving cone.

![canvas]

## File Organization

The sample code demonstrates the following features:

- Applying 3D transformation to individual objects
- Building basic shapes (cone and cylinder)
- Building 3D objects from a hierarchy of basic geometric shapes

## Files: `model/(GeometricObject|Cone|Polygonal).js`

The two files (`Cone.js` and `Polygonal.js`) show an example of building basic geometric shapes using triangles (GL.TRIANGLES). The two classes are a subclass of `GeometricObject`, which essentially declare two instance variables (`vertices` and `triangles`) needed to build a simple geometric shape.

- The `vertices` array holds the (x,y,z) _floating-point_ triplets of the vertices defining the skeleton of a 3D geometric shape.
- The `triangle` array holds _integer_ triplets defining all the triangles to "cover" the geometric shape.

Let's assume the 3D shapes is defined by 50 vertices and one of the triangles covers the first, fifth, and tenth vertices (**in CCW order**). Hence,

- The `vertices` array will hold 50 (x,y,z) triplets and
- One of the entry in the `triangles` array would be [0,4,9]

The above designed was adopted to make the `GeometricShape` class comp compatible with [gl-geometry] library using object definition in the following format:

```javascript
{
  position: [[x1,y1,z2], [x2,y2,z2], ...],
  cells: [[0,1,50], [1,2,50], ..., [0,4,9], ....]
}
```

> It is important that all the triangle triples are specified in the CCW order (when you view them from outside of the object).
> To help you visually debug CW triangles, the fragment shader code render CW triangles in dark gray.

The `geometry()` function of `GeometricShape` simply wraps both `vertices` and `triangles` into the format expected by [gl-geometry](https://github.com/stackgl/gl-geometry).

## Files: `core/ObjectGroup`, `model/(Arrow|Axes).js`

The `ObjectGroup` class allows you to build a composite object from other objects in a _hierarchical fashion_ (technically a tree structure). The class automatically defines an instance variable `children` and a function `add()` for adding subordinate objects to the group. \*\*

The following two classes demonstrate a practical example of `ObjectGroup`

- The `Arrow` class is a subclass of `ObjectGroup`. To build an arrow of length 1.0, this class combines the following two basic shape

  - A cylinder of height 0.8 to make the arrow body
  - A cone of height 0.2 to make the arrow head. To position the cone at the proper place, it must be translated (upward) by 0.8.

  > Pay close attention to the translation transformation applied to the cone and how the cone is addedd to the group.

  To allow visual styling, the `Arrow` constructor takes a `color` parameter.

- The `Axes` class is also a subclass of `ObjectGroup`. However, instead of using basic shapes as its children, this class is made of three instances of the `Arrow` class above painted in three different colors: red, green, and blue. This example also demonstrates how each subordinate object is transform using rotations (around X or Y axis).

## File: `app.js`

With the help of WebPack configuration file, we can emulate a typical `main()` function in a Java/C program into our JavaScript program.

The `main()` function begins at line 165.

1. The next 10 lines initialize all the matrices needed in the program.
2. The next 3 lines after that initialize the three important matrices in a typical OpenGL/WebGL program

   - `viewMatrix` to convert points in the world coordinate into the camera coordinate
   - `projectionMatrix` to define shape of the view volume of our camera
   - `cameraCF` is practially the inverse of the `viewMatrix`. To manipulate the camera, it is more **natural** to apply 3D transformations to the camera coordinate frame (as opposed to the view matrix)

3. The next 10 lines setup event listeners
   - window resize listener to reinitialize the view volue of your camera
   - slider input listeners
   - pull-dowm menu selection listener
4. Lines 202-217 creates the cone object
5. Lines 219-237 creates the hexagonal object
6. Lines 239-243 create the 3D axes
7. Finally, the last few lines load the shader into our program.

## Render "Loop"

The function `renderFunc()` is called _everytime_ the UI is refreshed by the browser. Hence the render "loop" in the title of this section.

> Be sure not to create any new resources (mat4, objects, etc.) inside this function. Otherwise, overtime the performance of your program may degrade.

The important statements inside this function are the three `render()` calls (at lines 83, 91, and 94). Prior to each call we compute the required transformation to update the pose of selected objects.

- The cone spins on its own Z-axis as well as revolves around the world Z-axis. Using the two matrices `coneSpin` and `coneRevolution` the sample code demonstrates how to achieve this effect using matrix pre- and post-multiplications.
- The hexagonal shape spins clockwise around its axis.

## Event Handling Functions

You will find foud event handling functions:

1. The main logic in `onWindowResized` is to maintain 4:3 canvas ratio and inform WebGL the new size of its canvas
2. The next two functions `onRollAngleChanged` and `onCameraPositionChanged` is simply a demonstration of how to update the camera coordinate frame based on user action on the UI (in this case changing the slider value)
3. The `onProjectionTypeChanged` method reinitializes the projection and view matrices in response to user changing the projection type.

[canvas]: cs367-mvp-transformation.png
