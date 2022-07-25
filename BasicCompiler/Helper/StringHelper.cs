namespace BasicCompiler.Helper;

public static class StringHelper
{
    public static string Escape(string value)
    {
        value = value.Replace("\n", "\", 10, \"");
        return value;
    }
}
