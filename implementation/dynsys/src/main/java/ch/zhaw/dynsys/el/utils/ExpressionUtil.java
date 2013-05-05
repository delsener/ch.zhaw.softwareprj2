package ch.zhaw.dynsys.el.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.apache.commons.jexl2.Script;

import ch.zhaw.dynsys.gui.CulturePanel;
import ch.zhaw.dynsys.simulation.Culture;

/**
 * This utility classes provides functionality to evaluate expressions defined in {@link CulturePanel}'s using the
 * {@link JexlEngine}.
 */
public class ExpressionUtil {

	// expression language engine
	private static final JexlEngine EL_ENGINE = new JexlEngine();

	// configurate the expresssion language engine
	static {
		EL_ENGINE.setCache(512);
		EL_ENGINE.setLenient(false);
		EL_ENGINE.setSilent(false);
		
		Map<String, Object> functions = new HashMap<String, Object>();
        functions.put("math", new MathFunctionProvider()); 
		EL_ENGINE.setFunctions(functions);
	};
	
	private ExpressionUtil() {
		// utiltiy class
	}

	
	public static void evaluateExpressions(Collection<Culture> cultures, long time) {
		if (cultures.size() == 0) {
			return;
		}
		
		// put all expressions in one string
		StringBuilder expressionString = new StringBuilder();
		expressionString.append("{\n");
		for (Culture culture : cultures) {
			expressionString.append("var " + culture.getVariable() + " = " + culture.getPopulation() + "\n");
			expressionString.append("var " + culture.getVariable() + "_diff = " + culture.getGrowRate() + "\n");
		}
		expressionString.append("}\n");
		
		// to get all values as result, put them in a map an return it 
		expressionString.append("return [");
		for (Culture culture : cultures) {
			expressionString.append("new(\"java.lang.Double\"," + culture.getExpression() + "), ");
		}
		expressionString.delete(expressionString.length() -2, expressionString.length());
		expressionString.append("];");
		
		// create and evaluate expression
		Script script = EL_ENGINE.createScript(expressionString.toString());
		JexlContext context = new MapContext();
		
		double[] results = (double[]) script.execute(context);
		int i = 0;
		for (Culture culture : cultures) {
			culture.setGrowRate(results[i] * (time/1000.0d));
			if (culture.isExcintion() && culture.getGrowRate() < -culture.getPopulation()) {
				culture.setGrowRate(-culture.getPopulation());
			}

			culture.setPopulation(culture.getPopulation() + culture.getGrowRate());
			i++;
		}
	}
}
