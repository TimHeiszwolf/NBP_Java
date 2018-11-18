/*
 * A class that is a 3D carthesian vector and does some things.
 */
public class Vector3D {
    Double x;
    Double y;
    Double z;
    
    /*
     * Initialises are the zero vector
     */
    public Vector3D() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
    }
    
    /*
     * Initialises a vector with values of x, y, z
     */
    public Vector3D(Double x, Double y, Double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    /*
     * Initialises array with values of x, y, z from array.
     */
    public Vector3D(Double[] array) {
        this.x = array[0];
        this.y = array[1];
        this.z = array[2];
    }
    
    /*
     * Prints the vector
     */
    public void print() {
        System.out.print("("+this.x+", "+this.y+", "+this.z+")");
    }
    
    /*
     * Returns the vector as array
     */
    public Double[] asArray() {
        Double[] array = {this.x, this.y, this.z};
        return array;
    }
    
    /*
     * Returns a new identical vector
     */
    public Vector3D copy() {
        return new Vector3D(this.x, this.y, this.z);
    }
    
    /*
     * Compares the values of the vector to see if identical (doesn't have a delta).
     */
    public Boolean equals(Vector3D other) {
        return ((this.x == other.x) && (this.y == other.y) && (this.z == other.z));
    }
    
    /*
     * Checks if the length of the difference vector is smaller or larger than delta.
     * The delta is given as a power of 10
     */
    public Boolean equals(Vector3D other, int delta) {
        return this.subtract(other).length() <= Math.pow(10.0, ((1.0 * delta)));
    }
    
    /*
     * Returns the length of the vector acourding to pythogoras
     */
    public double length() {
        return Math.sqrt(Math.pow(this.x, 2.0) + Math.pow(this.y, 2.0) + Math.pow(this.z, 2.0));
    }
    
    /*
     * Retruns the sum of all elements
     */
    public double sum() {
        return this.x + this.y + this.z;
    }
    
    /*
     * Returns a unit vector
     */
    public Vector3D unitVector() {
        if (this.x == 0.0 && this.y == 0.0 && this.z == 0.0) {
            // If the vector is zero return a x unit vector.
            // Not sure if this works since double precission and such.
            return new Vector3D(1.0, 0.0, 0.0);
        }
        
        Double length = this.length();
        return new Vector3D(this.x / length, this.y / length, this.z / length);
    }
    
    /*
     * Returns a scalar multiple of the vector
     */
    public Vector3D times(double scalar) {
        return new Vector3D(this.x * scalar, this.y * scalar, this.z * scalar);
    }
    
    /*
     * Makes this vector a scalar multiple 
     */
    public void thisTimes(double scalar) {
        this.x = this.x * scalar;
        this.y = this.y * scalar;
        this.z = this.z * scalar;
    }
    
    /*
     * Returns a direct vector multiplication
     */
    public Vector3D Times (Vector3D other) {
        return new Vector3D(this.x * other.x, this.y * other.y, this.z * other.z);
    }
    
    /*
     * Makes this vector a direct vector mulitplication
     */
    public void thisTimes(Vector3D other) {
        this.x = this.x * other.x;
        this.y = this.y * other.y;
        this.z = this.z * other.z;
    }
    
    /*
     * Adds two vectors
     */
    public Vector3D add(Vector3D other) {
        return new Vector3D(this.x + other.x, this.y + other.y, this.z + other.z);
    }
    
    /*
     * Adds a vector to this one
     */
    public void thisAdd(Vector3D other) {
        this.x += other.x;
        this.y += other.y;
        this.z += other.z;
    }
    
    /*
     * Subtracts two vectors
     */
    public Vector3D subtract(Vector3D other) {
        return new Vector3D(this.x - other.x, this.y - other.y, this.z - other.z);
    }
    
    /*
     * Subtracts a vector to this one
     */
    public void thisSubtract(Vector3D other) {
        this.x -= other.x;
        this.y -= other.y;
        this.z -= other.z;
    }
}