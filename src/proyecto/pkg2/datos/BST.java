package proyecto.pkg2.datos;

public class BST {
    
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
  
    // Constructor 
    BST()  
    {  
         root = null;  
    }
    
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
    
    // This method mainly calls insertRec() 
    void insert(int key)  
    {  
         root = insertRec(root, key);  
    }
    
    /* A recursive function to  
       insert a new key in BST */
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
    
    // This method mainly calls InorderRec() 
    void postorder()  
    {  
         postorderRec(root);  
    } 
  
    // A utility function to  
    // do inorder traversal of BST 
    void postorderRec(Node root) 
    { 
        if (root != null) {  
            
            postorderRec(root.left);             
            postorderRec(root.right); 
            //return root.key;
            
        } 
        else{
            System.out.println(-1);
        }
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


