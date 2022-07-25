using BasicCompiler.Instructions;

namespace BasicCompiler.Ast;

public class UnaryExpression : Expression
{
    public UnaryOperator Operator { get; set; }
    public Expression Expression { get; set; }

    public UnaryExpression(UnaryOperator @operator, Expression expression)
    {
        Operator = @operator;
        Expression = expression;
    }

    public override void Compile(InstructionSequence instructionSequence)
    {
        switch(Operator)
        {
            case UnaryOperator.PLUS: Expression.Compile(instructionSequence); break;
            case UnaryOperator.MINUS:
                {
                    instructionSequence.Append(new Instruction(OpCode.PUSHI, 0));
                    Expression.Compile(instructionSequence);
                    instructionSequence.Append(new Instruction(OpCode.SUB));
                    break;
                }
            default: throw new AssertionException();
        }
    }
}
