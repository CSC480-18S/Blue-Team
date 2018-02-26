// Node utilized in gaddag

package main.java.Models;

import java.util.HashMap;
import java.util.Set;

public class Node
{
    public static final char Break = '>';
    public static final char Eow = '$';
    public static final char Root = ' ';

    public char Letter;
    public HashMap<Character, Node> Children;

    // Constructors
    public Node(char letter)
    {
        this.Letter = letter;
    }

    public Node getChildByIndex(Object index)
    {
        return Children.get(index);
    }

    public Set<Character> GetKeys()
    {
        return Children.keySet();
    }

    public boolean ContainsKey(char key)
    {
        return Children.containsKey(key);
    }

    public Node AddChild(char letter)
    {
        if (Children == null)
            Children = new HashMap<Character, Node>();

        if (!Children.containsKey(letter))
        {
            Node node = letter != Eow ? new Node(letter) : null;
            Children.put(letter, node);
            return node;
        }

        return Children.get(letter);
    }

    public Node AddChild(char letter, Node node)
    {
        if (Children == null)
            Children = new HashMap<Character, Node>();

        if (!Children.containsKey(letter))
        {
            Children.put(letter, node);
            return node;
        }

        return (Node)Children.get(letter);
    }

    public String toString()
    {
        return Character.toString(this.Letter);
    }
}