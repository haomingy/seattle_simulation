package org.matsim.project;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.population.Population;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.config.groups.PlanCalcScoreConfigGroup;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.scoring.ScoringFunction;
import org.matsim.core.scoring.ScoringFunctionFactory;
import org.matsim.core.scoring.functions.ScoringParameters;


public class Test2 {
    public static void main(String[] args) {
        Config config;
        if (args == null || args.length == 0 || args[0] == null) {
            String filename1 = "scenarios/equil/config.xml";
            String filename2 = "scenarios/base_scenario/config.xml";
            String filename3 = "scenarios/drt_scenario/config_with_sav.xml";
            config = ConfigUtils.loadConfig(filename2);
        } else {
            config = ConfigUtils.loadConfig(args);
        }
        config.controler().setOverwriteFileSetting(OutputDirectoryHierarchy.OverwriteFileSetting.deleteDirectoryIfExists);
        Scenario scenario = ScenarioUtils.loadScenario(config);
        Controler controler = new Controler(scenario);
        //controler.addOverridingModule(new ScoringFunctionV3(config));
        controler.setScoringFunctionFactory(new ScoringFunctionFactoryV3(scenario));
        controler.run();
        //System.out.println(scenario.getPopulation().getPersons().size());
    }
}

