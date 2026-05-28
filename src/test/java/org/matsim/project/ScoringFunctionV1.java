package org.matsim.project;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.matsim.api.core.v01.events.Event;
import org.matsim.api.core.v01.events.PersonDepartureEvent;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.Person;
import org.matsim.core.controler.AbstractModule;
import org.matsim.core.scoring.ScoringFunction;
import org.matsim.core.scoring.ScoringFunctionFactory;
import org.matsim.core.scoring.functions.ModeUtilityParameters;
import org.matsim.core.scoring.functions.ScoringParameters;

public class ScoringFunctionV1  extends AbstractModule {
    @Override
    public void install() {
        // Bind the custom scoring function factory to the ScoringFunctionFactory interface
        bind(ScoringFunctionFactory.class).to(ScoringFunctionFactoryV1.class);
    }
}

class ScoringFunctionFactoryV1 implements ScoringFunctionFactory {
    @Override
    public ScoringFunction createNewScoringFunction(Person person) {

        /*ScoringParameters params = new ScoringParameters();
        ModeUtilityParameters p = ModeUtilityParameters();
        p.marginalUtilityOfTraveling_s
        // Set scoring parameters
        params.setMarginalUtilityOfTraveling_util_hr(0.1);
        params.setBeta(0.5);
        return new ScoringV1(params);*/
        System.out.println(person.getAttributes().getAsMap());
        return new ScoringV1();
    }
}

class ScoringV1 implements ScoringFunction{
    double score = 0;
    //protected final ScoringParameters params;
    Logger logger = LogManager.getLogger( ScoringV1.class ) ;
    ScoringV1(/*ScoringParameters params*/){
        //this.params = params;
    }
    @Override
    public void handleActivity(Activity activity) {

    }

    @Override
    public void handleLeg(Leg leg) {
        double legScore = calcLegScore(
                leg.getDepartureTime().seconds(), leg.getDepartureTime().seconds() + leg.getTravelTime()
                        .seconds(), leg);
        if ( Double.isNaN( legScore )) {
            logger.error( "dpTime=" + leg.getDepartureTime().seconds()
                    + "; ttime=" + leg.getTravelTime().seconds() + "; leg=" + leg ) ;
            throw new RuntimeException("score is NaN") ;
        }
        this.score += legScore;
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

    @Override
    public double getScore() {
        return 0;
    }

    @Override
    public void handleEvent(Event event) {
        if (event instanceof PersonDepartureEvent departureEvent) {
            String mode = departureEvent.getLegMode();
            String eventType = departureEvent.getEventType();
            System.out.println(departureEvent.getAttributes());
            System.out.println(mode);
            System.out.println(eventType);
        }
    }

    protected double calcLegScore(final double departureTime, final double arrivalTime, final Leg leg) {
        double tempScore = 0.0;
        double travelTime = arrivalTime - departureTime; // travel time in seconds
        /*ModeUtilityParameters modeParams = this.params.modeParams.get(leg.getMode());
        params.
        if (modeParams == null) {

        }*/
        return  tempScore;
    }
}