/*
 * A class for a body
 */
public class Body {
    int id = -1;
    String name;
    Vector3D position;
    Vector3D velocity;
    Vector3D acceleration;
    Double mass;
    Double radius;
    
    final Double gravityConstant = 6.67428 * Math.pow(10.0, -11.0);
    
    public Body(String name, Vector3D position, Vector3D velocity, Double mass, Double radius) {
        this.name = name;
        this.position = position;
        this.velocity = velocity;
        this.acceleration = new Vector3D();
        this.mass = mass;
        this.radius = radius;
    }
    
    /*
     * Copies itself into a new body
     */
    public Body copy() {
        Body newBody = new Body(this.name, this.position.copy(), this.velocity.copy(), this.mass, this.radius);
        newBody.id = this.id;
        return newBody;
    }
    
    /*
     * Updates the velocity of the body assuming constant acceleration.
     * Does it linearly and from the current time.
     */
    public void updateVelocity(Double deltaTime) {
        this.acceleration.thisTimes(deltaTime);
        this.velocity.thisAdd(acceleration);
        this.acceleration = new Vector3D(); // Sets the acceleration vector back to zero
    }
    
    /*
     * Updates the position of the body assuming constant velocity.
     * Does it linearly and from the current time.
     */
    public void updatePosition(Double deltaTime) {
        this.position.thisAdd(this.velocity.times(deltaTime));
    }
    
    /*
     * Updates the position of the body assuming constant acceleration.
     */
    public void updatePosistionComplex(Double deltaTime) {
        Vector3D changeInPosition = new Vector3D();
        changeInPosition.thisAdd(this.velocity.times(deltaTime));
        changeInPosition.thisAdd(this.acceleration.times(Math.pow(deltaTime, 2.0) / 2));
        this.position.thisAdd(changeInPosition);
    }
    
    /*
     * Adds acceleration to another body acourding to physics
     */
    public void addAccelerationTo(Body other) {
        Vector3D distanceVector = other.position.subtract(this.position);
        Double distance = distanceVector.length();
        Vector3D accelerationVector = distanceVector.unitVector();
        accelerationVector.thisTimes(gravityConstant * other.mass / Math.pow(distance, 2.0));
        this.acceleration.thisAdd(accelerationVector);
    }
    
    /*
     * Prints the body
     */
    public void print() {
        System.out.print(this.name + " (" + this.id + ") ");
        this.position.print();
        System.out.print(" ");
        this.velocity.print();
        System.out.print(" ");
        this.acceleration.print();
        System.out.print(" " + this.mass + " " + this.radius);
    }
}