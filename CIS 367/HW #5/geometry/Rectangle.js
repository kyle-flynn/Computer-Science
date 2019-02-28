import GeometricObject from './GeometricObject';

export const RECTANGLE_VERTICES = 8;

export default class Rectangle extends GeometricObject {
    /* Create a rectangle with the given width, height, and length.
       The rectangle is centered around the points (0, 0, 0), and starts
       from the leftmost face and expands rightward.
     */
    constructor({ width = 1.0, height = 1.0, length = 1.0 }) {
        super();
        /* Points 0, 1, 2, 3 are on the left.
         * Points 4, 5, 6, 7 are on the right.
         * x = width, y = length, z = height*/
        const topLeftOut = [(width / 2), 0,  (height / 2)];
        const botLeftOut = [(width / 2), 0, -(height / 2)];
        const topLeftIn = [-(width / 2), 0,  (height / 2)];
        const botLeftIn = [-(width / 2), 0, -(height / 2)];
        const topRightOut = [(width / 2), length,  (height / 2)];
        const botRightOut = [(width / 2), length, -(height / 2)];
        const topRightIn = [-(width / 2), length,  (height / 2)];
        const botRightIn = [-(width / 2), length, -(height / 2)];
        this.vertices.push(topLeftOut);
        this.vertices.push(botLeftOut);
        this.vertices.push(topLeftIn);
        this.vertices.push(botLeftIn);
        this.vertices.push(topRightOut);
        this.vertices.push(botRightOut);
        this.vertices.push(topRightIn);
        this.vertices.push(botRightIn);

        // Left side face
        this.triangles.push([0, 1, 2]);
        this.triangles.push([2, 3, 1]);
        // Back side face
        this.triangles.push([2, 3, 6]);
        this.triangles.push([3, 6, 7]);
        // Right side face
        this.triangles.push([4, 5, 6]);
        this.triangles.push([5, 6, 7]);
        // Bottom face
        this.triangles.push([3, 5, 1]);
        this.triangles.push([5, 6, 3]);
        // Front side face
        this.triangles.push([1, 5, 4]);
        this.triangles.push([0, 1, 4]);
        // Top face
        this.triangles.push([0, 2, 4]);
        this.triangles.push([2, 4, 6]);
    }
}
