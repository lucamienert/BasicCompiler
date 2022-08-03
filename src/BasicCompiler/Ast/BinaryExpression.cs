using BasicCompiler.Instructions;

namespace BasicCompiler.Ast;

public class BinaryExpression : Expression
{
    public BinaryOperator Operator { get; set; }
    public Expression LeftExpression { get; set; }
    public Expression RightExpression { get; set; }

    public BinaryExpression(BinaryOperator op, Expression leftExpression, Expression rightExpression)
    {
        Operator = op;
        LeftExpression = leftExpression;
        RightExpression = rightExpression;
    }

    public override void Compile(InstructionSequence instructionSequence)
    {
        LeftExpression.Compile(instructionSequence);
        RightExpression.Compile(instructionSequence);

        switch(Operator)
        {
            case BinaryOperator.PLUS: instructionSequence.Append(new Instruction(OpCode.ADD)); break;
            case BinaryOperator.MINUS: instructionSequence.Append(new Instruction(OpCode.SUB)); break;
            case BinaryOperator.MULT: instructionSequence.Append(new Instruction(OpCode.MUL)); break;
            case BinaryOperator.DIV: instructionSequence.Append(new Instruction(OpCode.DIV)); break;
            default: throw new AssertionException(Operator);
        }
    }
}
