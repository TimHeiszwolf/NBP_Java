import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Controller {
    Simulation currentSimulation;
    ArrayList<Simulation> oldSimulations = new ArrayList<Simulation>();
    Double trailTime = 0.0;
    
    /*
     * Initialises controler with a current simulation
     */
    public Controller(Simulation currentSim) {
        this.currentSimulation = currentSim;
    }
    
    /*
     * Initialasation with csv input
     */
    public Controller(String filename) {
        this.currentSimulation = this.CSVToSimulation(filename);
    }
    
    /*
     * Initialisation with no input
     */
    public Controller() {
        
    }
    
    /*
     * Runs next tick
     */
    public void nextTick() {
        this.oldSimulations.add(this.currentSimulation.copy());
        this.currentSimulation.nextTick();
        this.shortenTail();
    }
    
    /*
     * Runs until a certain time and exports each step to csv
     */
    public void runUntilCSV(double stopTime) {
        trailTime = 0.0;
        while (this.currentSimulation.time < stopTime) {
            this.nextTick();
            this.simulationToCSV();
        }
    }
    
    /*
     * Very broken at the moment
     */
    public void runVisualy() {
        Boolean pause = true;
        
        JFrame viewer = new JFrame("Viewer");
        JFrame viewController = new JFrame("Controller");
        
        viewer.setSize(800, 800);
        viewController.setSize(400, 400);
        viewController.setLocation(800,800);
        viewer.setVisible(true);
        viewController.setVisible(true);
        
        
    }
    
    /*
     * Runs until exactly a certain time and exports each step to csv
     */
    public void runUntilExactlyCSV(double stopTime) {
        trailTime = 0.0;
        double oldDeltaTime = this.currentSimulation.deltaTime;
        
        while (this.currentSimulation.time < stopTime) {
            if (stopTime - this.currentSimulation.time < this.currentSimulation.deltaTime) {
                this.currentSimulation.deltaTime = stopTime - this.currentSimulation.time;
                this.nextTick();
                this.currentSimulation.deltaTime = oldDeltaTime;
                break;
            } else {
                this.nextTick();
            }
            this.simulationToCSV();
        }
    }
    
    /*
     * Shortens the trail to trailTime
     */
    public void shortenTail() {
        while (this.oldSimulations.get(0).time < (this.currentSimulation.time - this.trailTime)) {
            
            if (this.oldSimulations.size() > 1) {
                
                if (this.oldSimulations.get(1).time < (this.currentSimulation.time - this.trailTime)) {
                    this.oldSimulations.remove(this.oldSimulations.get(0));
                }
            } else {
                this.oldSimulations.get(0).runUntilExactly(this.currentSimulation.time - this.trailTime);
            }
        }
    }
    
    
    /*
     * Writes to a simulation to a csv file
     */
    public void simulationToCSV(Simulation simulation){
        try{
            PrintWriter printer = new PrintWriter(new FileWriter(simulation.tick + ".csv"));
        
            printer.println(simulation.time + "," + simulation.deltaTime + "," + simulation.tick + "," + simulation.bodies.size() + ",");
            printer.println("Name,Position,Velocity,Mass,Radius,");
            
            Body body;
            for (int i = 0; i < simulation.bodies.size(); i++) {
                body = simulation.bodies.get(i);
                printer.print(body.name + "," + body.id + ",");
                printer.print(body.position.x + "," + body.position.y + "," + body.position.z + ",");
                printer.print(body.velocity.x + "," + body.velocity.y + "," + body.velocity.z + ",");
                printer.print(body.mass + "," + body.radius + ",\n");
            }
            
            printer.close();
        }catch (IOException e) {
            System.out.println("Error: Didn't write the file correctly.");
        }
    }
    
    /*
     * Writes the current simulation to a csv file
     */
    public void simulationToCSV() {
        simulationToCSV(this.currentSimulation);
    }
    
    /*
     * Imports a simulation state from a csv file.
     */
    public Simulation CSVToSimulation(String filename) {
        try{
            ArrayList<Body> bodies = new ArrayList<Body>();
            Scanner scanner = new Scanner(new FileReader(filename));
            scanner.useDelimiter(",");
            
            double time = scanner.nextDouble();
            double deltaTime = scanner.nextDouble();
            int tick = scanner.nextInt();
            int amountBodies = scanner.nextInt();
            
            scanner.nextLine();
            scanner.nextLine();
            
            String name;
            int id;
            Vector3D position;
            Vector3D velocity;
            Double mass;
            Double radius;
            Body body;
        
            for (int i = 0; i < amountBodies; i++) {
                name = scanner.next();
                id = scanner.nextInt();
                position = new Vector3D(scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble());
                velocity = new Vector3D(scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble());
                mass = scanner.nextDouble();
                radius = scanner.nextDouble();
                body = new Body(name, position, velocity, mass, radius);
                body.id = id;
                bodies.add(body);
                scanner.nextLine();
            }
        
            scanner.close();
        
            Simulation simulation;
            simulation = new Simulation(bodies, time, deltaTime);
            simulation.tick = tick;
            return simulation;
        } catch (IOException e) {
            System.out.println("Error: Didn't read the file correctly.");
            return new Simulation(new ArrayList<Body>(), -1.0, -1.0);
        }
    }
}