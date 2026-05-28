package org.matsim.project;

public class Test3 {
    /*public static void main(String[] args) {
        // Create a MATSim configuration
        Config config = ConfigUtils.createConfig();

        // Set up Charypar-Nagel scoring parameters
        CharyparNagelScoringParameters scoringParameters = new CharyparNagelScoringParameters(config);

        // Set up the CharyparNagelLegScoring
        CharyparNagelLegScoring legScoring = new CharyparNagelLegScoring(scoringParameters);

        // Create a ScoringFunctionFactory
        ScoringFunctionFactory scoringFunctionFactory = new ScoringFunctionFactory() {
            @Override
            public ScoringFunction createNewScoringFunction(org.matsim.api.core.v01.population.Person person) {
                return new CharyparNagelLegScoring(legScoring);
            }

            @Override
            public void finish() {
                // No additional setup required
            }
        };

        // Set the ScoringFunctionFactory in the MATSim configuration
        config.controler().setScoringFunctionFactory(scoringFunctionFactory);

        // Create a Controler with the configuration
        Controler controler = new Controler(config);

        // Continue with the rest of your MATSim simulation setup and execution
        controler.run();
    }*/
}