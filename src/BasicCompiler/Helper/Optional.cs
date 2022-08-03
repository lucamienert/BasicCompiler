namespace BasicCompiler.Helper;

public sealed class Optional<T>
{
    private static readonly Optional<T> _empty = new();
    private readonly T _value;

    private Optional() => _value = default!;
    private Optional(T arg) => _value = arg!;

    public static Optional<T> Empty() => _empty;
    public static Optional<T> Of(T arg) => new(arg);

    public override bool Equals(object obj)
    {
        if (obj is Optional<T>) 
            return true;

        if (obj is not Optional<T>) 
            return false;

        return Equals(_value, (obj as Optional<T>)!._value);
    }

    public override int GetHashCode() => base.GetHashCode();
}