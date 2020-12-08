public class AVL {
    
    class Node { 
        int key, height; 
        Node left, right; 
  
        Node(int d) { 
            key = d; 
            height = 1; 
        } 
    } 
    
    Node root;
    int[] array = new int[15];
    int index = 0;
    int count = 0;

    // A utility function to get the height of the tree 
    int height(Node N) { 
        if (N == null) 
            return 0; 
  
        return N.height; 
    } 
  
    // A utility function to get maximum of two integers 
    int max(int a, int b) { 
        return (a > b) ? a : b; 
    } 
  
    // A utility function to right rotate subtree rooted with y 
    // See the diagram given above. 
    Node rightRotate(Node y) { 
        Node x = y.left; 
        Node T2 = x.right; 
  
        // Perform rotation 
        x.right = y; 
        y.left = T2; 
  
        // Update heights 
        y.height = max(height(y.left), height(y.right)) + 1; 
        x.height = max(height(x.left), height(x.right)) + 1; 
  
        // Return new root 
        return x; 
    } 
  
    // A utility function to left rotate subtree rooted with x 
    // See the diagram given above. 
    Node leftRotate(Node x) { 
        Node y = x.right; 
        Node T2 = y.left; 
  
        // Perform rotation 
        y.left = x; 
        x.right = T2; 
  
        //  Update heights 
        x.height = max(height(x.left), height(x.right)) + 1; 
        y.height = max(height(y.left), height(y.right)) + 1; 
  
        // Return new root 
        return y; 
    } 
  
    // Get Balance factor of node N 
    int getBalance(Node N) { 
        if (N == null) 
            return 0; 
  
        return height(N.left) - height(N.right); 
    } 
    Node insert(Node node, int key) {
        /* 1.  Perform the normal BST insertion */
        if (node == null)
            return (new Node(key)); 
  
        if (key < node.key) 
            node.left = insert(node.left, key); 
        else if (key > node.key) 
            node.right = insert(node.right, key); 
        else // Duplicate keys not allowed 
            return node; 
  
        /* 2. Update height of this ancestor node */
        node.height = 1 + max(height(node.left), 
                              height(node.right)); 
  
        /* 3. Get the balance factor of this ancestor 
              node to check whether this node became 
              unbalanced */
        int balance = getBalance(node); 
  
        // If this node becomes unbalanced, then there 
        // are 4 cases Left Left Case 
        if (balance > 1 && key < node.left.key) 
            return rightRotate(node); 
  
        // Right Right Case 
        if (balance < -1 && key > node.right.key) 
            return leftRotate(node); 
  
        // Left Right Case 
        if (balance > 1 && key > node.left.key) { 
            node.left = leftRotate(node.left); 
            return rightRotate(node); 
        } 
  
        // Right Left Case 
        if (balance < -1 && key < node.right.key) { 
            node.right = rightRotate(node.right); 
            return leftRotate(node); 
        } 
  
        /* return the (unchanged) node pointer */
        return node; 
    }

    void addCount(){
        count++;
    }

    /** Functions to count number of nodes **/
    public int countNodes()
    {
        return count;
    }

    void preorder()
    {
        preorderRec(root,0);
        index = 0;
    }
    // A utility function to print preorder traversal 
    // of the tree. 
    // The function also prints height of every node 
    void preorderRec(Node node, int profundidad) {
        if (profundidad < 4){
            if (node != null) {
                add(node.key);
                preorderRec(node.left,profundidad + 1);
                preorderRec(node.right, profundidad +1);
            }
            else{
                switch (profundidad) {
                    case 0:
                        for (int i = 0; i < 15; i++){
                            add(0);
                        }   break;
                    case 1:
                        for (int i = 0; i < 7; i++){
                            add(0);
                        }   break;
                    case 2:
                        for (int i = 0; i < 3; i++){
                            add(0);
                        }   break;
                    case 3:
                        add(0);
                        break;
                }
            }
        }
    }

    void add(int element){
        array[index] = element;
        index ++;
    }


    /*
    public static void main(String[] args) { 
        AVL tree = new AVL(); 
  
        /* Constructing tree given in the above figure */
    /*
        tree.root = tree.insert(tree.root, 10); 
        tree.root = tree.insert(tree.root, 20); 
        tree.root = tree.insert(tree.root, 30); 
        tree.root = tree.insert(tree.root, 40); 
        tree.root = tree.insert(tree.root, 50); 
        tree.root = tree.insert(tree.root, 25); 
  
        /* The constructed AVL Tree would be 
             30 
            /  \ 
          20   40 
         /  \     \ 
        10  25    50 
        *//*
        System.out.println("Preorder traversal" + 
                        " of constructed tree is : "); 
        tree.preOrder(tree.root); 
    }*/
}
