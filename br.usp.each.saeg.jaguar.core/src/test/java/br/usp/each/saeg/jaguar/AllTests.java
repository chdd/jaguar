package br.usp.each.saeg.jaguar;

import org.junit.runner.RunWith;

import br.usp.each.saeg.jaguar.heuristic.impl.OchiaiHeuristic;
import br.usp.each.saeg.jaguar.runner.JaguarSuite;
import br.usp.each.saeg.jaguar.runner.JaguarRunnerHeuristic;

@RunWith(JaguarSuite.class)
@JaguarRunnerHeuristic(value=OchiaiHeuristic.class, isDataflow=true )
public class AllTests {

}
