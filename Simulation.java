import java.util.*; 

public class Simulation {
    ArrayList<Body> bodies;
    double time;
    double deltaTime;
    int tick = 0;
    
    public Simulation(ArrayList<Body> bodies, double time, double deltaTime) {
        this.bodies = bodies;
        this.time = time;
        this.deltaTime = deltaTime;
        
        for (int i = 0; i < this.bodies.size(); i++) {
            this.bodies.get(i).id = i;
        }
        
    }
    
    /*
     * Updates all accelerations.
     */
    public void updateAcceleration() {
        for (int i = 0; i < this.bodies.size(); i++) {
            
            for (int j = 0; j < this.bodies.size(); j++) {
                
                if (i != j) {
                    this.bodies.get(i).addAccelerationTo(this.bodies.get(j));
                }
            }
        }
    }
    
    /*
     * Updates the velocity of all bodies in a non-complex way.
     */
    public void updatePosition() {
        for (int i = 0; i < this.bodies.size(); i++) {
            this.bodies.get(i).updatePosition(this.deltaTime);
            this.bodies.get(i).updateVelocity(this.deltaTime);
        }
    }
    
    /*
     * Updates the velocity of all bodies in a complex way.
     */
    public void updatePositionComplex() {
        for (int i = 0; i < this.bodies.size(); i++) {
            this.bodies.get(i).updatePosistionComplex(this.deltaTime);
            this.bodies.get(i).updateVelocity(this.deltaTime);
        }
    }
    
    /*
     * Computes and goes to the next tick.
     */
    public void nextTick() {
        this.updateAcceleration();
        this.updatePositionComplex();
        
        this.time += this.deltaTime;
        this.tick++;
    }
    
    /*
     * Runs until the desired time.
     */
    public void runUntil(double stopTime) {
        while (this.time < stopTime) {
            this.nextTick();
        }
    }
    
    /*
     * Runs the simulation until exactly a certain time
     */
    public void runUntilExactly(double stopTime) {
        double oldDeltaTime = this.deltaTime;
        
        while (this.time < stopTime) {
            if (stopTime - this.time < this.deltaTime) {
                this.deltaTime = stopTime - this.time;
                this.nextTick();
                this.deltaTime = oldDeltaTime;
                break;
            } else {
                this.nextTick();
            }
        }
    }
    
    /*
     * Copies the current simulation
     */
    public Simulation copy() {
        ArrayList<Body> bodiesCopy = new ArrayList<Body>();
        
        for (int i = 0; i < this.bodies.size(); i++) {
            bodiesCopy.add(this.bodies.get(i).copy());
        }
        
        Simulation sim = new Simulation(bodiesCopy, this.time, this.deltaTime);
        sim.tick = this.tick;
        return sim;
    }
    
    /*
     * Prints the current simulation
     */
    public void print() {
        System.out.println("Time: " + this.time + " (" + this.tick + "), Deltatime: " + this.deltaTime + ", Amount bodies: " + this.bodies.size());
        System.out.println("Name, Position, Velocity, Acceleration, Mass, Radius");
        
        for (int i = 0; i < this.bodies.size(); i++) {
            bodies.get(i).print();
            System.out.println("");
        }
        
        System.out.println("");
    }
    
}