package br.usp.each.saeg.jaguar.heuristic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import br.usp.each.saeg.jaguar.model.core.requirement.AbstractTestRequirement;

/**
 * Calculates the fautl localization rank
 * 
 * @author Henrique Ribeiro
 */
public class HeuristicCalculator {

	private Heuristic heuristic;
	private Collection<AbstractTestRequirement> requirements;
	private Integer nTestsPassed;
	private Integer nTestsFailed;

	/**
	 * Receives the objects to calculate the fault localization rank
	 * 
	 * @param heuristic
	 *            An implemented SFL heuristic
	 * @param requirements
	 *            A set of test requirements which contains the information of
	 *            how many (failed and sucessed) executed tests
	 * @param nTestsPassed
	 *            Total number of passed tests
	 * @param nTestsFailed
	 *            Total number of failed tests
	 */
	public HeuristicCalculator(Heuristic heuristic, Collection<AbstractTestRequirement> requirements, Integer nTestsPassed,
			Integer nTestsFailed) {
		super();
		this.heuristic = heuristic;
		this.requirements = requirements;
		this.nTestsPassed = nTestsPassed;
		this.nTestsFailed = nTestsFailed;
	}

	/**
	 * Based on the objects (heuristic, set of test requirements, total failed
	 * and passed tests) create a fault suspicious rank.
	 * 
	 * @return A list of suspicious test requirements, order by suspiciousness
	 *         (the more suspicious come first)
	 */
	public ArrayList<AbstractTestRequirement> calculateRank() {
		int cef, cnf, cep, cnp;
		ArrayList<AbstractTestRequirement> rankList = new ArrayList<AbstractTestRequirement>();
		for (AbstractTestRequirement requirement : requirements) {
			cef = requirement.getCef();
			cep = requirement.getCep();
			cnf = nTestsFailed - cef;
			cnp = nTestsPassed - cep;
			Double suspiciousness = heuristic.eval(cef, cnf, cep, cnp);
			requirement.setSuspiciousness(suspiciousness);

			rankList.add(requirement);
		}

		Collections.sort(rankList);
		Collections.reverse(rankList);
		return rankList;
	}

}
