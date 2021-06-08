package com.brunocesar.jsonlogic.evaluator.expressions;

import com.brunocesar.jsonlogic.JsonLogic;
import com.brunocesar.jsonlogic.ast.JsonLogicArray;
import com.brunocesar.jsonlogic.evaluator.JsonLogicEvaluationException;
import com.brunocesar.jsonlogic.evaluator.JsonLogicEvaluator;
import com.brunocesar.jsonlogic.evaluator.JsonLogicExpression;
import com.brunocesar.jsonlogic.utils.ArrayLike;

import java.util.ArrayList;
import java.util.List;

public class FilterExpression implements JsonLogicExpression {
    public static final FilterExpression INSTANCE = new FilterExpression();

    private FilterExpression() {
        // Use INSTANCE instead.
    }

    @Override
    public String key() {
        return "filter";
    }

    @Override
    public Object evaluate(JsonLogicEvaluator evaluator, JsonLogicArray arguments, Object data)
            throws JsonLogicEvaluationException {
        if (arguments.size() != 2) {
            throw new JsonLogicEvaluationException("filter expects exactly 2 arguments");
        }

        Object maybeArray = evaluator.evaluate(arguments.get(0), data);

        if (!ArrayLike.isEligible(maybeArray)) {
            throw new JsonLogicEvaluationException("first argument to filter must be a valid array");
        }

        List<Object> result = new ArrayList<>();

        for (Object item : new ArrayLike(maybeArray)) {
            if (JsonLogic.truthy(evaluator.evaluate(arguments.get(1), item))) {
                result.add(item);
            }
        }

        return result;
    }
}