import java.util.*;

public class Tree {

    public Node root; // The root of the tree
    public Node NYT; // Always points to the existing NYT
    
    public Tree() {
        order = new Vector();
        Exist = new HashMap();
        NYT = root = new Node(null);
        order.add(root);
    }

    public boolean exists(int c) {
        return Exist.containsKey(c);
    }

    public void Add(int c, Vector <Boolean> code) {
        if (!this.exists(c)) { // the passed code vector will have the value of bits if this character  after tree traversed
            getCode(NYT, code);
        } else {
            getCode(Exist.get(c), code); //Here means that we want to change the old node place as there's a new comer
        }
        //if node isn't exist then get it's value from the the Exist vector, otherwise addNew to add the new char to the method
        Node node = Exist.containsKey(c) ? Exist.get(c) : addNew(c);
        Update(node);
    }

    public boolean isEmpty() {
        return root == NYT;
    }

    private void getCode(Node n, Vector <Boolean> code) {
        String a = "";
        Node node = n;
        Node parent;
        //Iterate till we get to the root or could be made recursively
        while ((parent = node.parent) != null) {
            if (parent.left == node) {
                code.add(false);
                a += "0";
            } else {
                code.add(true);
                a += "1";
            }
            node = parent;
        }
        //Printing the generated code for each character we have traversed , print not println
        System.out.print(a);
    }

    private Node addNew(int value) {
        Node newNYT = new Node(NYT);
        Node leaf = new Node(NYT, value);
        Exist.put(value, leaf);         // Add new value to Exist HashMap.
        order.add(0, leaf);
        order.add(0, newNYT);
        //the right is now the new leaf that has the new character
        //the left is then newNYT
        //we set NYT to false as till now we are dealing with the old one
        Node oldNYT = NYT;
        NYT.set(leaf, newNYT, false);
        NYT = newNYT; // Turn NYT pointer into the new NYT node.

        for (int i = 0; i < order.size(); i++) {
            //we gets the pointer for each node
            //we set the node.idx = to the index of this node in the vector
            //assuming order.get(i) = node ;
            order.get(i).setIndex(order.indexOf(order.get(i)));
        }
        return oldNYT;
    }

    private void Update(Node node) {
        //starting from the node up to the root
        while (node != root) {
            //if It's not the max then do the swap
            if (IsMaxW(node)) {
                swap(MaxW(node), node);//Implementing here the all conditions we should met
            }
            // Increment node weight ==> Then go to the parent
            node.weight++;
            node = node.parent;
        }
        //reached the rood then also increment its weight
        node.weight++;
        node.setIndex(order.indexOf(node));
    }

    private boolean IsMaxW(Node node) {
        int index = order.indexOf(node);
        int weight = node.weight;
        for (int i = index + 1; i < order.size(); i++) {
            Node next = order.get(i);
            if (next != node.parent && next.weight == weight) {
                return true;
            } else if (next != node.parent && next.weight > weight) {
                return false;
            }
        }
        return false;
    }
    
    private Node MaxW(Node node) {
        Node next;
        int index = node.idx + 1;
        int weight = node.weight;
        while (true) {
            index++;
            if(order.get(index).weight != weight) break;
        }
        next = order.get(--index); //gets from the vector of ordered nodes by indices we get a pointer to this node
        return next;
    }

    private void swap(Node newPos, Node oldPos) {
        int newIndex = newPos.idx;
        int oldIndex = oldPos.idx;
        // Keep track of parents of both nodes getting swapped.
        Node oldParent = oldPos.parent;
        Node newParent = newPos.parent;

        // as we want to know if nodes were left or right child comparing to the parent.
        boolean oldRight, newRight;
        oldRight = newRight = false;
        if (newPos.parent.right == newPos) newRight=true;
        if (oldPos.parent.right == oldPos) oldRight=true;
        if (oldRight)oldParent.right = newPos;
        else oldParent.left = newPos;
        if (newRight)newParent.right = oldPos;
        else newParent.left = oldPos;

        oldPos.parent = newParent;newPos.parent = oldParent;
        //We should swap the Indices to be keep updated
        order.set(newIndex, oldPos);
        order.set(oldIndex, newPos);
        for (int i = 0; i < order.size(); i++) {
            //we gets the pointer for each node
            //we set the node.idx = to the index of this node in the vector
            //assuming order.get(i) = node ;
            order.get(i).setIndex(order.indexOf(order.get(i)));
        }
    }

    public void insert(int c) {
        // Deal with value that exists in tree first.
        Node node = Exist.containsKey(c) ? Exist.get(c) : addNew(c);
        Update(node);
    }
    
    // To Search if this character is in the tree or not easily than iterating the whole tree
    private Map <Integer, Node> Exist;
    // Keep nodes in order based on weight.
    private Vector <Node> order;
}

class Node {

    public Node parent;
    public Node left = null;
    public Node right = null;

    protected boolean NYT;
    protected boolean Leaf = false;

    public int weight, idx, val;

    //if only sent one parameter then it means that we want to make it a NYT and its value is zero
    public Node(Node p) {
        parent = p;
        idx = weight = val = 0;
        NYT = true;
    }

    //if sent two parameters then it means add a new node to the char and its value is given
    public Node(Node p, int val) {
        parent = p;
        NYT = false;
        Leaf = true;
        this.val = val;
        idx = weight = 1;
    }

    public boolean NYT() {return NYT;}

    public boolean Leaf() { return Leaf;}

    public void set(Node right, Node left, boolean NYT) {
        this.right = right;
        this.left = left;
        this.NYT = NYT;
    }

    public void setIndex(int idx) {
        this.idx = idx;
    }
}
