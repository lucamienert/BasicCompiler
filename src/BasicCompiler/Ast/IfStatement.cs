using BasicCompiler.Instructions;

namespace BasicCompiler.Ast;

public class IfStatement : Statement
{
    public RelationalOperator RelationalOperator { get; set; }
    public Expression LeftExpression { get; set; }
    public Expression RightExpression { get; set; }
    public Statement Statement { get; set; }

    public IfStatement(RelationalOperator relationalOperator, Expression leftExpression, Expression rightExpression, Statement statement)
    {
        RelationalOperator = relationalOperator;
        LeftExpression = leftExpression;
        RightExpression = rightExpression;
        Statement = statement;
    }

    public override void Compile(InstructionSequence instructionSequence)
    {
        LeftExpression.Compile(instructionSequence);
        RightExpression.Compile(instructionSequence);

        var thenLabel = instructionSequence.CreateGeneratedLabel();
        var endLabel = instructionSequence.CreateGeneratedLabel();

        var opcode = RelationalOperator switch
        {
            RelationalOperator.EQ => OpCode.JMPEQ,
            RelationalOperator.NQ => OpCode.JMPNE,
            RelationalOperator.LT => OpCode.JMPLT,
            RelationalOperator.GT => OpCode.JMPGT,
            _ => throw new AssertionException()
        };

        instructionSequence.Append(
            new Instruction(opcode, thenLabel),
            new Instruction(OpCode.JMP, endLabel),
            new Instruction(OpCode.LABEL, thenLabel)
        );

        Statement.Compile(instructionSequence);
        instructionSequence.Append(new Instruction(OpCode.LABEL, endLabel));
    }
}
