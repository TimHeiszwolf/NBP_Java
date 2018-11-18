import java.util.*; 
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
 * Runs some tests for the NBP program
 */
public class Tester {   
  public static void main(String[] args) {
      
      int errorsVec = 0;
      int errorsBody = 0;
      int errorsSim = 0;
      int errorsCon = 0;
      
      
      /*
       * Testing the vectors
       */
      Vector3D zeroVector = new Vector3D();
      Vector3D xVector = new Vector3D(1.0, 0.0, 0.0);
      Vector3D randomVector = new Vector3D(-42.0, 69.2, 33.0);
      Vector3D otherVector = new Vector3D(22 * Math.PI, -96.4, 12.3);
      
      if (!(xVector.equals(xVector) && zeroVector.equals(zeroVector) && randomVector.equals(randomVector))) {
          System.out.println("Error: A vector is not equal to itself.");
          errorsVec++;
      }
      
      if (!(zeroVector.unitVector().equals(xVector, -9))) {
          System.out.println("Error: Unit vector of zero vector not x vector " + zeroVector.unitVector().equals(xVector));
          errorsVec++;
      }
      
      if (randomVector.equals(otherVector, -9)) {
          System.out.println("Error: Two vectors who are not equal are equal/");
          errorsVec++;
      }
      
      if (randomVector.length() != 87.41647441987122) {
          System.out.println(randomVector.length());
          System.out.println("Error: Length of random vector is wrong.");
          errorsVec++;
      }
      
      if (randomVector.unitVector().length() != 1.0) {
          System.out.println("Error: Length of unit vector is not 1.0.");
          errorsVec++;
      }
      
      if (!(randomVector.subtract(zeroVector).equals(randomVector, -9) && randomVector.add(zeroVector).equals(randomVector, -9))) {
          System.out.println("Error: Adding or subtracting the zero vector changes the vector.");
          errorsVec++;
      }
      
      if (!(randomVector.times(3.4).equals(new Vector3D(-142.8, 235.28, 112.2), -9))) {
          randomVector.times(3.4).print();
          System.out.println("Error: Multiplying a vector doesn't work.");
          errorsVec++;
      }
      
      if (!(randomVector.add(otherVector).equals(new Vector3D(27.11503837897544, -27.2, 45.3), -9))) {
          randomVector.add(otherVector).print();
          System.out.println("Error: Adding two vectors doesn't work.");
          errorsVec++;
      }
      
      if (!(randomVector.subtract(otherVector).equals(new Vector3D(-111.11503837897544, 165.6, 20.7), -9))) {
          randomVector.subtract(otherVector).print();
          System.out.println("Error: Subtracting two vectors doesn't work.");
          errorsVec++;
      }
      
      if (!(randomVector.copy().equals(randomVector))) {
          System.out.println("Error: Array is coppied wrongly");
          errorsVec++;
      }
      
      if (!(randomVector.times(2.0).equals(new Vector3D(-42.0*2, 69.2*2, 33.0*2), -9))) {
          System.out.println("Error: Multiplying a vector is not itself times it.");
          errorsVec++;
      }
      
      /*
       * Testing the bodies
       */
      Body body1 = new Body("body 1", zeroVector, xVector, 1.0, 1.0); // Body at zero with velocity x = 1 and mass, radius = 1
      //Body body2 = new Body("body 2", new Vector3D(), new Vector3D(), 1.0, 1.0);
      //Body body3 = new Body("body 3", new Vector3D(), new Vector3D(), 1.0, 1.0);
      Body earth = new Body("earth", new Vector3D(1.494 * Math.pow(10, 11), 0.0, 0.0), new Vector3D(0.0, 29800.0, 0.0), 5.972 * Math.pow(10.0, 24.0), 6371000.0);
      Body sun = new Body("sun", new Vector3D(), new Vector3D(), 1.0, 1.0);
      Body person = new Body("person", earth.position.add(new Vector3D(0.0, earth.radius, 0.0)), earth.velocity, 70.0, 1.0);
      
      if (!(earth.position.equals(new Vector3D(1.494 * Math.pow(10, 11), 0.0, 0.0), -9) && earth.velocity.equals(new Vector3D(0.0, 29800.0, 0.0), -9))) {
          System.out.println("Error: Position or velocity of earth is wrong.");
          errorsBody++;
      }
      
      person.addAccelerationTo(earth);
      if (!(person.acceleration.equals(new Vector3D(0.0, -9.82, 0.0), -2))) {
          person.acceleration.print();
          System.out.println("Error: Acceleration on earth is not normal.");
          errorsBody++;
      }
      
      body1.velocity = new Vector3D(1.0, 0.0, 1.0);
      body1.acceleration = new Vector3D(0.0, 10.0, 5.0);
      body1.updatePosistionComplex(2.0);
      
      if (!(body1.position.equals(new Vector3D(2.0, 20.0, 2.0 + 10.0), -9))) {
          System.out.println("Error: updatePositionComplex doesn't function propely");
          errorsBody++;
      }
      
      earth.addAccelerationTo(sun);
      earth.updateVelocity(3600.0);
      earth.updatePosition(3600.0);
      if (!(earth.position.equals(new Vector3D(1.494E11, 1.0728E8, 0.0), -9))) {
          earth.position.print();
          System.out.println("Error: Position earth is not normal.");
          errorsBody++;
      }
      
      /*
       * Testing the simulation.
       */
      ArrayList<Body> bodies = new ArrayList<Body>();
      
      sun = new Body("Sun", new Vector3D(), new Vector3D(), 1.9885 * Math.pow(10, 30), 695508000.0);
      bodies.add(sun);
      Body mercury = new Body("Mercury", sun.position.add(new Vector3D(0.0, 6.981 * Math.pow(10, 10), -Math.pow(10,8))), sun.velocity.add(new Vector3D(-38000.0, 0.0, 103.0)), 3.28 * Math.pow(10.0, 23.0), 2440000.0);
      bodies.add(mercury);
      Body venus = new Body("Venus", sun.position.add(new Vector3D(0.0, -1.07 * Math.pow(10, 11), Math.pow(10,9))), sun.velocity.add(new Vector3D(34000.0, 0.0, -103.0)), 4.867 * Math.pow(10.0, 24.0), 6052000.0);
      bodies.add(venus);
      earth = new Body("Earth", sun.position.add(new Vector3D(1.494 * Math.pow(10, 11), 0.0, 0.0)), sun.velocity.add(new Vector3D(0.0, 29800.0, 0.0)), 5.972 * Math.pow(10.0, 24.0), 6371000.0);
      bodies.add(earth);
      Body moon = new Body("Moon", earth.position.add(new Vector3D(0.0, 3.85 * Math.pow(10.0, 8.0), Math.pow(10.0, 6))), earth.velocity.add(new Vector3D(-1020.0, 0.0, -40.0)), 7.34767309 * Math.pow(10.0, 22), 1737000.0);
      bodies.add(moon);
      Body mars = new Body("Mars", sun.position.add(new Vector3D(-2.066E11, 0.0, 0.0)), sun.velocity.add(new Vector3D(0.0, -26.39E3, 0.0)), 6.39E23, 3390000.0);
      bodies.add(mars);
      Body jupiter = new Body("Jupiter", sun.position.add(new Vector3D(7.407E11, 0.0, 0.0)), sun.velocity.add(new Vector3D(0.0, 13.0E3, 0.0)), 1.898E27, 69911000.0);
      bodies.add(jupiter);
      Body saturn = new Body("Saturn", sun.position.add(new Vector3D(0.0, -1.5039E12, 0.0)), sun.velocity.add(new Vector3D(9.106E3, 0.0, 0.0)), 5.683E26, 58232000.0);
      bodies.add(saturn);
      Body uranus = new Body("Uranus", sun.position.add(new Vector3D(0.0, 3.006E12, 0.0)), sun.velocity.add(new Vector3D(-6552.0, 0.0, 0.0)), 8.681E25, 25362000.0);
      bodies.add(uranus);
      Body neptune = new Body("Neptune", sun.position.add(new Vector3D(0.0, 4.45E12, 0.0)), sun.velocity.add(new Vector3D(-5430.0, 0.0, 0.0)), 1.024E25, 24622000.0);
      bodies.add(neptune);
      Simulation sim = new Simulation(bodies, 0.0, 3600.0);
      
      //sim.print();
      sim.runUntilExactly(24*3600*365.256);
      if (!sim.bodies.get(3).position.equals(sim.bodies.get(0).position.add(new Vector3D(1.494 * Math.pow(10, 11), 0.0, 0.0)), 10)) {
          //sim.print();
          System.out.println(sim.bodies.get(3).position.subtract(sim.bodies.get(0).position.add(new Vector3D(1.494 * Math.pow(10, 11), 0.0, 0.0))).length());
          System.out.println("Error: Earth doesn't make a rotation in one year");
          errorsSim++;
      }
      
      sim.runUntilExactly(24*3600*60.182);
      if (!sim.bodies.get(9).position.equals(sim.bodies.get(0).position.add(new Vector3D(0.0, 4.45E12, 0.0)), 12)) {
          //sim.print();
          System.out.println(sim.bodies.get(9).position.subtract(sim.bodies.get(0).position.add(new Vector3D(0.0, 4.45E12, 0.0))).length());
          System.out.println("Error: Nepture doesn't make a rotation in one period");
          errorsSim++;
      }
      
      /*
       * Testing controllers
       */
      Controller test1 = new Controller(sim.copy());
      test1.simulationToCSV();
      test1.currentSimulation.nextTick();
      String fileName = (sim.tick)+".csv";
      test1.currentSimulation = test1.CSVToSimulation(fileName);
      
      File file = new File(fileName);
      if (!file.delete()) {
          System.out.println("Error: Deleting " + fileName + " failed");
          errorsCon++;
      }
      
      if (!test1.currentSimulation.bodies.get(3).position.equals(sim.bodies.get(3).position, -9)) {
          System.out.println("Error: CSV import or exoprt doesn't work correctly");
          errorsCon++;
      }
      
      int originalTick = sim.tick;
      double extraTime = 72*3600;
      test1.runUntilCSV(sim.time + extraTime*1.0);
      sim.runUntil(sim.time + extraTime*1.0);
      if (!test1.currentSimulation.bodies.get(3).position.equals(sim.bodies.get(3).position, -9)) {
          System.out.println("Error: simulation runUntil is not equal to controller run until.");
          errorsCon++;
      }
      
      for (int i = 1; i <= extraTime/sim.deltaTime; i++) {
          file = new File((originalTick + i) + ".csv");
          if (!file.delete()) {
              System.out.println("Error: Deleting " + fileName + " failed");
              errorsCon++;
          }
      }
      
      
      /*
       * Testing 
       */
      
      
      /* 
       * Printing total amount of errors.
       */
      if ((errorsVec + errorsBody + errorsSim + errorsCon) == 0) {
          System.out.println("0 errors during testing.");
      } else {
          System.out.println(errorsVec + " errors with vectors.");
          System.out.println(errorsBody + " errors with bodies.");
          System.out.println(errorsSim + " errors with simulations.");
          System.out.println(errorsCon + " errors with controllers.");
      }
      System.out.println("");
  }
}