/// Data structure to check for possible available words.
/// Useful for scrabble AI player.
package Models;
import java.util.*;

public class Gaddag
{
    public Node RootNode;

    public Gaddag(char rootLetter)
    {
        RootNode = new Node(rootLetter);
    }

    public void Add(String word)
    {
        String w = word.toLowerCase();
        ArrayList<Node> prevNode = new ArrayList<>();
        // for the word length
        for (int i = 1; i <= w.length(); i++)
        {
            String prefix = w.substring(0, i);
            String suffix = "";
            // if i isn't at the end, get rest of the letters
            if (i != w.length()) suffix = w.substring(i, w.length() - (i));
            // build new string with reversed prefix, append break and rest of letters
            String addWord = _StringReverse(prefix) + Node.Break + suffix + Node.Eow;

            Node currentNode = RootNode;
            boolean breakFound = false;
            int j = 0;
            for (char c : addWord.toCharArray())
            {
                //Long words can be joined back together after the break point, cutting the tree size in half.
                if (breakFound && prevNode.size() > j)
                {
                    currentNode.AddChild(c, prevNode.get(j));
                    break;
                }

                currentNode = currentNode.AddChild(c);

                if (prevNode.size() == j)
                    prevNode.add(currentNode);

                if (c == Node.Break)
                    breakFound = true;
                j++;
            }
        }
    }


    private static String _GetWord(String str)
    {
        String word = "";

        for (int i = str.indexOf(Node.Break) - 1; i >= 0; i--)
            word += str.toCharArray()[i];

        for (int i = str.indexOf(Node.Break) + 1; i < str.length(); i++)
            word += str.toCharArray()[i];

        return word;
    }

    public Object[] ContainsHookWithRack(String hook, String rack)
    {
        hook = hook.toLowerCase();
        hook = _StringReverse(hook);

        HashSet<String> set = new HashSet<>();

        _ContainsHookWithRackRecursive(RootNode, set, "", rack, hook);
        return set.toArray();
    }

    private static void _ContainsHookWithRackRecursive(Node node, Set<String> rtn, String letters, String rack, String hook)
    {
        // Null nodes represent the EOW, so return the word.
        if (node == null)
        {
            String w = _GetWord(letters);
            if (!rtn.contains(w)) rtn.add(w);
            return;
        }

        // If the hook still contains letters, process those first.
        if (hook != null && !hook.isEmpty())
        {
            // convert hook to arraylist<character>
            ArrayList<Character> hookCA = convertToArray(hook);

            letters += node.Letter != Node.Root ? node.Letter : "";

            if (node.ContainsKey(hookCA.remove(0)))
            {
                //Pop the letter off the hook
                String h = hook.substring(0,1);
                _ContainsHookWithRackRecursive(node.getChildByIndex(hookCA.get(0)), rtn, letters, rack, h);
            }
        }
        else
        {
            letters += node.Letter != Node.Root ? node.Letter : "";
            for (char key : node.GetKeys())
            {
                String r;
                if (key != Node.Eow && key != Node.Break)
                {
                    ArrayList<Character> rackCA = convertToArray(rack);
                    rackCA.remove(rackCA.indexOf((char)key));
                    r = rackCA.toString();
                }
                else
                {
                    r = rack;
                } //Pull the letter from the rack

                _ContainsHookWithRackRecursive(node.getChildByIndex(key), rtn, letters, r, hook);
            }
        }
    }

    private static ArrayList<Character> convertToArray(String word)
    {
        // convert hook to arraylist<character>
        ArrayList<Character> wordarr = new ArrayList<Character>();
        for (char c : word.toCharArray()) {
            wordarr.add(c);
        }
        return wordarr;
    }
    private static String _StringReverse(String str)
    {
        char[] charArray = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        sb.append(charArray);
        sb.reverse();
        return (new String(sb));
    }
}

