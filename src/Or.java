/**
 * Yehonatan Abramovitch yona132@gmail.com 318800182.
 */

import java.util.Map;

/**
 * Or Class.
 */
public class Or extends BinaryExpression {
    /**
     * Constructor for Or.
     *
     * @param expression1 the first expression
     * @param expression2 the second expression
     */
    public Or(Expression expression1, Expression expression2) {
        super(expression1, expression2);
    }

    /**
     * Evaluates the OR operation with variable assignments.
     *
     * @param assignment a map containing variable assignments
     * @return the result of the OR operation
     * @throws Exception if an error occurs during the evaluation
     */
    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        return this.getExpression().evaluate(assignment) || this.getExpression2().evaluate(assignment);
    }

    /**
     * Evaluates the OR operation without variable assignments.
     *
     * @return the result of the OR operation
     * @throws Exception if an error occurs during the evaluation
     */
    @Override
    public Boolean evaluate() throws Exception {
        return this.getExpression().evaluate() || this.getExpression2().evaluate();
    }

    /**
     * Converts the OR operation to a string representation.
     *
     * @return a string representation of the OR operation
     */
    @Override
    public String toString() {
        return "(" + this.getExpression().toString() + " | " + this.getExpression2().toString() + ")";
    }

    /**
     * Assigns a new expression to both operands.
     *
     * @param var        the variable to be assigned
     * @param expression the new expression to assign to the variable
     * @return a new expression with the variable assignment
     */
    @Override
    public Expression assign(String var, Expression expression) {
        return new Or(this.getExpression().assign(var, expression), this.getExpression2().assign(var, expression));
    }

    /**
     * Performs NANDification on the Or operation.
     *
     * @return the result of the expression uses only Nand operator
     */
    @Override
    public Expression nandify() {
        Expression e1 = this.getExpression().nandify();
        Expression e2 = this.getExpression2().nandify();
        Expression e = new Nand(new Nand(e1, e1), new Nand(e2, e2));
        return e;
    }

    /**
     * Performs NORification on the Or operation.
     *
     * @return the result of the expression uses only Nor operator
     */
    @Override
    public Expression norify() {
        Expression e1 = this.getExpression().norify();
        Expression e2 = this.getExpression2().norify();
        Expression e = new Nor(new Nor(e1, e2), new Nor(e1, e2));
        return e;
    }

    /**
     * Simplifies the OR operation.
     *
     * @return a simplified expression
     */
    @Override
    public Expression simplify() {
        Or ex = new Or((this.getExpression()).simplify(), (this.getExpression2()).simplify());
        try {
            return new Val(ex.evaluate());
        } catch (Exception e) {
            Expression ex1, ex2;
            try {
                ex1 = ex.getExpression();
                if (ex1.evaluate()) {
                    return new Val(true);
                }
                return ex.getExpression2();
            } catch (Exception e1) {
                ex2 = ex.getExpression2();
                try {
                    boolean b = ex2.evaluate();
                    if (b) {
                        return new Val(true);
                    }
                    return ex.getExpression();
                } catch (Exception e2) {
                }
            }
            if ((ex.getExpression().toString()).equals(ex.getExpression2().toString())) {
                return ex.getExpression();
            }
            return ex;
        }
    }
}
