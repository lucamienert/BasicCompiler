namespace BasicCompiler.Helper;

public static class CharExtension
{
    public static bool IsWhiteSpace(this char ch, int character)
    {
        return character == ' ' || character == '\0';
    }

    public static bool IsDigit(this char ch, int character)
    {
        return character >= '0' && character <= '9';
    }

    public static bool IsAlpha(this char ch, int character)
    {
        return character >= 'A' && character <= 'Z';
    }
}
