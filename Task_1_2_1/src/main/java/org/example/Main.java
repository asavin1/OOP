package org.example;

public class Main {
    public static void main(String[] args) {
        Tree<String> tree = new Tree <>("R1");
        var a = tree.addChild("A");
        var b = a.addChild("B");
        Tree<String> subtree = new Tree <>("R2");
        subtree.addChild("C");
        subtree.addChild("D");
        tree.addChild(subtree);
        b.deleteSubTree();
        var ads = tree.iterator();
        System.out.println(tree.toString());
    }
}
