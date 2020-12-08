

public class Splay {
    int[] array = new int[15];
    int index = 0;
    private SplayNode root;
    private int count = 0;
 
     /** Constructor **/
     public Splay()
     {
         root = null;
     }
 
     /** Function to check if tree is empty **/
     public boolean isEmpty()
     {
         return root == null;
     }
 
     /** clear tree **/
     public void clear()
     {
         root = null;
         count = 0;
     }
 
     /** function to insert element */
     public void insert(int ele)
     {
         SplayNode z = root;
         SplayNode p = null;
         while (z != null)
         {
             p = z;
             if (ele > p.element)
                 z = z.right;
             else
                 z = z.left;
         }
         z = new SplayNode();
         z.element = ele;
         z.parent = p;
         if (p == null)
             root = z;
         else if (ele > p.element)
             p.right = z;
         else
             p.left = z;
         Splay(z);
         count++;
     }
     /** rotate **/
     public void makeLeftChildParent(SplayNode c, SplayNode p)
     {
         if ((c == null) || (p == null) || (p.left != c) || (c.parent != p))
             throw new RuntimeException("WRONG");
 
         if (p.parent != null)
         {
             if (p == p.parent.left)
                 p.parent.left = c;
             else 
                 p.parent.right = c;
         }
         if (c.right != null)
             c.right.parent = p;
 
         c.parent = p.parent;
         p.parent = c;
         p.left = c.right;
         c.right = p;
     }
 
     /** rotate **/
     public void makeRightChildParent(SplayNode c, SplayNode p)
     {
         if ((c == null) || (p == null) || (p.right != c) || (c.parent != p))
             throw new RuntimeException("WRONG");
         if (p.parent != null)
         {
             if (p == p.parent.left)
                 p.parent.left = c;
             else
                 p.parent.right = c;
         }
         if (c.left != null)
             c.left.parent = p;
         c.parent = p.parent;
         p.parent = c;
         p.right = c.left;
         c.left = p;
     }
 
     /** function splay **/
     private void Splay(SplayNode x)
     {
         while (x.parent != null)
         {
             SplayNode Parent = x.parent;
             SplayNode GrandParent = Parent.parent;
             if (GrandParent == null)
             {
                 if (x == Parent.left)
                     makeLeftChildParent(x, Parent);
                 else
                     makeRightChildParent(x, Parent);                 
             } 
             else
             {
                 if (x == Parent.left)
                 {
                     if (Parent == GrandParent.left)
                     {
                         makeLeftChildParent(Parent, GrandParent);
                         makeLeftChildParent(x, Parent);
                     }
                     else 
                     {
                         makeLeftChildParent(x, x.parent);
                         makeRightChildParent(x, x.parent);
                     }
                 }
                 else 
                 {
                     if (Parent == GrandParent.left)
                     {
                         makeRightChildParent(x, x.parent);
                         makeLeftChildParent(x, x.parent);
                     } 
                     else 
                     {
                         makeRightChildParent(Parent, GrandParent);
                         makeRightChildParent(x, Parent);
                     }
                 }
             }
         }
         root = x;
     }
 
     /** function to remove element **/
     public void remove(int ele)
     {
         SplayNode node = findNode(ele);
        remove(node);
     }
 
     /** function to remove node **/
     private void remove(SplayNode node)
     {
         if (node == null)
             return;
 
         Splay(node);
         if( (node.left != null) && (node.right !=null))
         { 
             SplayNode min = node.left;
             while(min.right!=null)
                 min = min.right;
 
             min.right = node.right;
             node.right.parent = min;
             node.left.parent = null;
             root = node.left;
         }
         else if (node.right != null)
         {
             node.right.parent = null;
             root = node.right;
         } 
         else if( node.left !=null)
         {
             node.left.parent = null;
             root = node.left;
         }
         else
         {
             root = null;
         }
         node.parent = null;
         node.left = null;
         node.right = null;
         node = null;
         count--;
     }
 
     /** Functions to count number of nodes **/
     public int countNodes()
     {
         return count;
     }
 
     /** Functions to search for an element **/
     public boolean search(int val)
     {
         return findNode(val) != null;
     }
 
     private SplayNode findNode(int ele)
     {
    	 SplayNode PrevNode = null;
         SplayNode z = root;
         while (z != null)
         {
        	 PrevNode = z;
             if (ele > z.element)
                 z = z.right;
             else if (ele < z.element)
                 z = z.left;
             else if(ele == z.element) {
                 Splay(z);
                 return z;
             }
 
         }
         if(PrevNode != null)
         {
             Splay(PrevNode);
             return null;
         }
         return null;
     }
 
     /** Function for preorder traversal **/
     public void preorder()
     {
         preorderRec(root,0);
         index = 0;
     }
     private void preorderRec(SplayNode root, int profundidad)
     {
         if (profundidad < 4){
             if (root != null) {

                 add(root.element);
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
 

}
