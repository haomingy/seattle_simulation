package org.matsim.project;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.events.Event;
import org.matsim.api.core.v01.events.PersonDepartureEvent;
import org.matsim.api.core.v01.events.handler.PersonDepartureEventHandler;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.Person;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.AbstractModule;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.scoring.ScoringFunction;
import org.matsim.core.scoring.ScoringFunctionFactory;

public class Test1 {
    public static void main(String[] args) {
        Config config;
        if ( args==null || args.length==0 || args[0]==null ){
            config = ConfigUtils.loadConfig( "scenarios/equil/config.xml" );
        } else {
            config = ConfigUtils.loadConfig( args );
        }

        config.controler().setOverwriteFileSetting( OutputDirectoryHierarchy.OverwriteFileSetting.deleteDirectoryIfExists );

        // possibly modify config here

        // ---

        Scenario scenario = ScenarioUtils.loadScenario(config) ;

        // possibly modify scenario here

        // ---

        Controler controler = new Controler( scenario ) ;

        // possibly modify controler here

//		controler.addOverridingModule( new OTFVisLiveModule() ) ;

//		controler.addOverridingModule( new SimWrapperModule() );

        // ---

        // Register the custom scoring function module
        controler.addOverridingModule(new CustomScoringModule());

        // Run the simulation
        controler.run();
    }
}

class CustomScoringModule extends AbstractModule {
    @Override
    public void install() {
        // Bind the custom scoring function factory to the ScoringFunctionFactory interface
        bind(ScoringFunctionFactory.class).to(CustomScoringFunctionFactory.class);
    }
}

class CustomScoringFunctionFactory implements ScoringFunctionFactory {
    @Override
    public ScoringFunction createNewScoringFunction(Person person) {
        return new CustomScoringFunction();
    }
}

class CustomScoringFunction implements ScoringFunction, PersonDepartureEventHandler {
    private double totalScore = 0.0;
    private Activity prevAvtivity;
    @Override
    public void handleEvent(PersonDepartureEvent event) {
        // Calculate scores based on the person departure event
        // For example, let's assume we want to add 10 to the total score for each person departure
        String mode = event.getLegMode();
        String activityType = prevAvtivity.getType();

        // Calculate scores based on the mode of transport and activity type
        if (mode.equalsIgnoreCase("car") && activityType.equalsIgnoreCase("work")) {
            totalScore += 10.0;

            System.out.println("Assigned score of 10 to car going to work");// Car going to work
        } else {
            totalScore += 20.0; // Other cases
        }
    }

    @Override
    public double getScore() {
        // Return the total score calculated
        return totalScore;
    }

    @Override
    public void handleEvent(Event event) {

    }

    @Override
    public void handleActivity(Activity activity) {
        prevAvtivity = activity;
    }

    @Override
    public void handleLeg(Leg leg) {

    }

    @Override
    public void agentStuck(double v) {

    }

    @Override
    public void addMoney(double v) {

    }

    @Override
    public void addScore(double v) {

    }

    @Override
    public void finish() {
    }
}