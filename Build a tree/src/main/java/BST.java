/**
 * Arbol BST
 */
public class BST {
    /**
     * Nodo del arbol BST
     */
    class Node  
    { 
        int key;         
        Node left, right; 
  
        public Node(int item) 
        { 
            key = item; 
            left = right = null; 
        } 
    }
    
    // Root of BST 
    Node root = null; 
    int[] array = new int[15];
    int index = 0;
    int count = 0;
  
    // Constructor 
    BST()  
    {  
         root = null;  
    }

    /**
     * Busca si un elemento existe en el arbol
     * @param root
     * @param key
     * @return
     */
    Boolean search(Node root, int key) 
    { 
        // Base Cases: root is null or key is present at root 
        if (root==null)
            return false;
            
        if (root.key==key) 
            return true; 
        
        // Key is greater than root's key 
        if (root.key < key) 
           return search(root.right, key); 

        // Key is smaller than root's key 
        return search(root.left, key); 
    }

    /**
     * Inserta un nodo en el arbol
     * @param key
     */
    // This method mainly calls insertRec() 
    void insert(int key)  
    {  
        count++;
        root = insertRec(root, key);
    }

    /**
     * Functions to count number of nodes
     */
    public int countNodes()
    {
        return count;
    }
    
    /** A recursive function to
     * insert a new key in BST
     */
    Node insertRec(Node root, int key) 
    { 
  
        /* If the tree is empty, 
           return a new node */
        if (root == null)  
        { 
            root = new Node(key); 
            return root; 
        } 
  
        /* Otherwise, recur down the tree */
        if (key < root.key) 
            root.left = insertRec(root.left, key); 
        else if (key > root.key) 
            root.right = insertRec(root.right, key); 
  
        /* return the (unchanged) node pointer */
        return root; 
    }

    /**
     * Recorre el arbol de manera preorder
     */
    // This method mainly calls InorderRec() 
    void preorder()  
    {  
         preorderRec(root,0);
         index = 0;
    } 


    // A utility function to  
    // do inorder traversal of BST 
    void preorderRec(Node root, int profundidad) { 
        if (profundidad < 4){
            if (root != null) {  

                add(root.key); 
                preorderRec(root.left,profundidad + 1);             
                preorderRec(root.right, profundidad +1); 
                //return root.key;

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

    
    // Driver Code 
    /*
    public static void main(String[] args) 
    { 
        BST tree = new BST(); 
  
        /* Let us create following BST 
              50 
           /     \ 
          30      70 
         /  \    /  \ 
       20   40  60   80 */
     /**   tree.insert(50); 
        tree.insert(30); 
        tree.insert(20); 
        tree.insert(40); 
        tree.insert(70); 
        tree.insert(60); 
        tree.insert(80); 
  
        // print inorder traversal of the BST 
        tree.inorder(); 
        
    }    */
}


