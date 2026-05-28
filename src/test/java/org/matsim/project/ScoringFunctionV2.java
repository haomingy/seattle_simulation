package org.matsim.project;

import org.matsim.api.core.v01.events.Event;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.core.controler.AbstractModule;
import org.matsim.core.router.TripStructureUtils;
import org.matsim.core.scoring.ScoringFunction;
import org.matsim.core.scoring.ScoringFunctionFactory;
import org.matsim.core.scoring.SumScoringFunction;
import org.matsim.vehicles.PersonVehicles;

import java.util.List;

public class ScoringFunctionV2  extends AbstractModule {
    @Override
    public void install() {
        // Bind the custom scoring function factory to the ScoringFunctionFactory interface
        bind(ScoringFunctionFactory.class).to(ScoringFunctionFactoryV2.class);
    }
}

class ScoringFunctionFactoryV2 implements ScoringFunctionFactory {

    @Override
    public ScoringFunction createNewScoringFunction(Person person) {
        return new ScoringV2();
    }
}
class ScoringV2 implements ScoringFunction, SumScoringFunction.LegScoring, SumScoringFunction.ArbitraryEventScoring {
    @Override
    public void handleEvent(Event event) {
        System.out.println("v2");
    }

    @Override
    public void handleTrip(TripStructureUtils.Trip trip) {
        ScoringFunction.super.handleTrip(trip);
    }

    @Override
    public void handleActivity(Activity activity) {

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

    @Override
    public double getScore() {
        return 0;
    }
}
