package org.matsim.project;

import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.events.Event;
import org.matsim.api.core.v01.events.PersonDepartureEvent;
import org.matsim.api.core.v01.events.PersonScoreEvent;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.Person;
import org.matsim.core.config.Config;
import org.matsim.core.config.groups.PlanCalcScoreConfigGroup;
import org.matsim.core.config.groups.ScenarioConfigGroup;
import org.matsim.core.controler.AbstractModule;
import org.matsim.core.scoring.ScoringFunction;
import org.matsim.core.scoring.ScoringFunctionFactory;
import org.matsim.core.scoring.functions.CharyparNagelLegScoring;
import org.matsim.core.scoring.functions.ScoringParameters;

import java.util.Set;

public class ScoringFunctionV3  extends AbstractModule {
    @Override
    public void install() {
        // Bind the custom scoring function factory to the ScoringFunctionFactory interface
        bind(ScoringFunctionFactory.class).to(ScoringFunctionFactoryV3.class);
    }
}

class ScoringFunctionFactoryV3 implements ScoringFunctionFactory {
    Scenario scenario;
    ScoringFunctionFactoryV3(Scenario scenario){
        this.scenario = scenario;
    }
    @Override
    public ScoringFunction createNewScoringFunction(Person person) {
        return new ScoringV3(scenario);
    }
}

class ScoringV3 extends CharyparNagelLegScoring implements ScoringFunction {
    Scenario scenario;
    Activity activity;
    public ScoringV3(Scenario scenario){
        super(new ScoringParameters.Builder(scenario.getConfig().planCalcScore(), scenario.getConfig().planCalcScore().getScoringParameters(null), scenario.getConfig().scenario()).build(),scenario.getNetwork());
        this.scenario =scenario;
    }

    @Override
    public void handleActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void agentStuck(double v) {

    }

    @Override
    public void addMoney(double value) {

    }

    @Override
    public void addScore(double value) {
        score += value;
    }

    @Override
    public void handleEvent(Event event) {
        super.handleEvent(event);
    }

    @Override
    public void handleLeg(Leg leg) {
        super.handleLeg(leg);
        String activityType = activity.getType();
        String mode = leg.getMode();
        switch (mode) {
            case "car" -> {
                if (activityType.equals("shop") || activityType.equals("others")) {
                    scenario.getConfig().planCalcScore().getOrCreateModeParams(mode).setMarginalUtilityOfTraveling(-8);
                }
            }
            case "drt" -> {
                if (activityType.equals("shop") || activityType.equals("others")) {
                    scenario.getConfig().planCalcScore().getOrCreateModeParams(mode).setMarginalUtilityOfTraveling(-4);
                }
            }
            case "walk" -> {
                double DISTANCE_THRESHOLD = 8046;
                double travel_distance = leg.getRoute().getDistance();
                score += -Math.exp(Math.log(101) * travel_distance / DISTANCE_THRESHOLD) + 1;
            }
            case "bike" -> {
                double DISTANCE_THRESHOLD = 16093;
                double distance = leg.getRoute().getDistance();
                score += -Math.exp(Math.log(101) * distance / DISTANCE_THRESHOLD) + 1;
            }
        }
    }
}
