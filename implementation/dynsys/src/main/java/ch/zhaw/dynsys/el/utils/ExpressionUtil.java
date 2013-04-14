package ch.zhaw.dynsys.el.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.apache.commons.jexl2.Script;

import ch.zhaw.dynsys.gui.CulturePanel;

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

	
	public static double[] evaluateExpressions(List<CulturePanel> culturePanels) {
		// put all expressions in one string
		StringBuilder expressionString = new StringBuilder();
		expressionString.append("{\n");
		for (CulturePanel culturePanel : culturePanels) {
			expressionString.append("var " + culturePanel.getVariableName() + " = " + culturePanel.getVariableValue() + "\n");
		}
		expressionString.append("}\n");
		
		// to get all values as result, put them in a map an return it 
		expressionString.append("return [");
		for (CulturePanel culturePanel : culturePanels) {
			expressionString.append(culturePanel.getVariableName() + ", ");
		}
		expressionString.delete(expressionString.length() -2, expressionString.length());
		expressionString.append("];");
		
		// create and evaluate expression
		//Expression e = EL_ENGINE.createExpression(expressionString.toString());
		Script script = EL_ENGINE.createScript(expressionString.toString());
		JexlContext context = new MapContext();
		
		Number[] results = (Number[]) script.execute(context);
		double[] resultsCasted = new double[results.length];
		for (int i = 0; i < results.length; i++) {
			resultsCasted[i] = results[i].doubleValue();
		}
		
		return resultsCasted;
	}
}
