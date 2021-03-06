package ch.zhaw.dynsys.el.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.apache.commons.jexl2.Script;
import org.apache.commons.lang3.StringUtils;

import ch.zhaw.dynsys.gui.CulturePropertyEditor;
import ch.zhaw.dynsys.simulation.Culture;

/**
 * This utility classes provides functionality to evaluate expressions defined
 * in {@link CulturePropertyEditor}'s using the {@link JexlEngine}.
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

	public static void evaluateExpressions(Collection<Culture> cultures,
			double step) {
		if (cultures.size() == 0) {
			return;
		}

		for (Iterator<Culture> iterator = cultures.iterator(); iterator
				.hasNext();) {
			Culture culture = (Culture) iterator.next();
			if (culture.getExpression() == null) {
				iterator.remove();
			}
		}

		// put all expressions in one string
		StringBuilder expressionString = new StringBuilder();
		for (Culture culture : cultures) {
			expressionString.append("var "
					+ StringUtils.lowerCase(culture.getVariable()) + " = "
					+ culture.getValue() + "\n");
			expressionString.append("var "
					+ StringUtils.lowerCase(culture.getVariable()) + "_diff = "
					+ culture.getGrowRate() + "\n");
		}

		// to get all values as result, put them in a map an return it
		expressionString.append("return [\n");
		for (Culture culture : cultures) {
			String exp = StringUtils.defaultString(culture.getExpression())
					.toLowerCase();
			exp = exp.replaceAll("([a-z]+)'", "$1_diff"); // replace derivation
															// '
			exp = exp.replaceAll("([a-z]+)\\(", "math:$1\\("); // add math:
																// namespace
			expressionString.append("\tnew(\"java.lang.Double\"," + exp
					+ "),\n");
		}
		expressionString.delete(expressionString.length() - 2,
				expressionString.length());
		expressionString.append("];");

		// create and evaluate expression
		Script script = EL_ENGINE.createScript(expressionString.toString());
		JexlContext context = new MapContext();

		double[] results = (double[]) script.execute(context);
		int i = 0;
		for (Culture culture : cultures) {
			culture.setGrowRate(results[i] * step);
			culture.setValue(culture.getValue() + culture.getGrowRate());
			i++;
		}
	}
}
