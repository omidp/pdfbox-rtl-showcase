package org.omidbiz;

/**
 * @author omidp
 *
 */
public class PersianCharacterUnifier
{

    private static final PersianCharacterUnifier INSTANCE = new PersianCharacterUnifier();

    private PersianCharacterUnifier()
    {

    }

    public static PersianCharacterUnifier getInstance()
    {
        return INSTANCE;
    }

    public String unify(String input)
    {
        if (input == null)
            return null;
        char[] charArray = input.toCharArray();
        String output = "";
        for (int i = 0; i < charArray.length; i++)
        {
            output += replace(charArray[i]);
        }
        return output;
    }

    /**
     * @see Integer#toHexString(int);
     * @param toHexString
     *            (char)
     * @return
     */
    private char replace(char ch)
    {
        switch (ch)
        {
        case 0x643:
            return 0x6a9;// ye
        case 0x64a:
            return 0x6cc;// ke
        case 0x200f:
            return 0x20;
        case 0x202b:
            return 0x20;
        default:
            return ch;
        }
    }

    public String escapeUnicode(char ch)
    {
        if (ch < 0x10)
        {
            return "\\u000" + Integer.toHexString(ch);
        }
        else if (ch < 0x100)
        {
            return "\\u00" + Integer.toHexString(ch);
        }
        else if (ch < 0x1000)
        {
            return "\\u0" + Integer.toHexString(ch);
        }
        return "\\u" + Integer.toHexString(ch);
    }

}
