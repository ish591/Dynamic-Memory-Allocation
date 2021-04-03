// Class: Height balanced AVL Tree
// Binary Search Tree

public class AVLTree extends BSTree {
    
    private AVLTree left, right;     // Children. 
    private AVLTree parent;          // Parent pointer. 
    private int height;  // The height of the subtree
        
    public AVLTree() { 
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node !.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
        
    }

    public AVLTree(int address, int size, int key) { 
        super(address, size, key);
        this.height = 0;
    }

    // Implement the following functions for AVL Trees.
    // You need not implement all the functions. 
    // Some of the functions may be directly inherited from the BSTree class and nothing needs to be done for those.
    // Remove the functions, to not override the inherited functions.
      private AVLTree getRoot(){ //helper function
        AVLTree curr=this;
        while(curr.parent!=null){
            curr=curr.parent;
        }
        //now we are at the sentinel root node
        return curr;//returns the sentinel node
    }
        private int compare(Dictionary n1, Dictionary n2){ // helper functions to compare two dictionaries, returns true if n1 is a larger one
        if(n1.key==n2.key && n1.address==n2.address && n1.size==n2.size){return 0;} // when both nodes have the same data fields
        if(n1.key>n2.key){return 1;}//n1 is larger cases
        if(n1.key==n2.key){
            if(n1.address>n2.address){
                return 1;
            }
            if(n1.address==n2.address){
                if(n1.size>n2.size){
                    return 1;
                }
            }
        }
        return 2; // n2 is larger in all the other cases than n1
    }
     private int getHeight(AVLTree ok){   //helper function for insertion
        if(ok==null){return -1;}
        else{return ok.height;}
  }
     private void updateheights(AVLTree here){    //helper function for  updating heights of the ancestors
        if(here.parent==null){return;}//we are at the sentinel node
        here.height=1+Math.max(getHeight(here.left),getHeight(here.right));
          updateheights(here.parent);
    }
     private void balancetree(AVLTree ok, boolean deletion){   
//checks if balancing is required, and if required calls the rebalance function which decides the required rotations
        // deletion determines whether this is the rebalance for deletion/insertion. In deletion, we may have to cascade up, and hence after one
        //rotation, we do not return the function, we continue up till we reach the root of the tree
        if(ok.parent==null){return;}// if we reach the sentinel node
        if(deletion==true){ok.height=1+Math.max(getHeight(ok.left),getHeight(ok.right));}
        // if deletion is true, we dynamically update heights while cascadind up
            if (getHeight(ok.left)-getHeight(ok.right)>1 || getHeight(ok.left)-getHeight(ok.right)<-1){
                // checks the height balance invariant of AVL trees
                AVLTree cascadeup=ok.parent;// parent of the current node
                rebalance(ok,deletion);// this function will perform the required rotations
                if(deletion==true){balancetree(cascadeup,deletion);}
                else{return;}
            }
            else{
          balancetree(ok.parent,deletion); // if current subtree is balanced, call the ancestor
      }
        }

 private void rebalance(AVLTree curr,boolean deletion){ // helper function which calls for appropriate rotations to make the tree height balanced
    // if deletion is true, the only change in the algorithm is that we only update the height of the new current node instead of all of its ancestors
    //this is to reduce the time complexity from nlogn to logn. In deletion, a dynamic updation is followed in which we update heights as we cascade up.
    // in insertion, we straight away update heights of all the ancestors as it is done in only O(logn) time
   if(getHeight(curr.left)>getHeight(curr.right)){
       if(getHeight(curr.left.left)>=getHeight(curr.left.right)){
           curr=linearleft(curr);
           curr.right.height=1+Math.max(getHeight(curr.right.left),getHeight(curr.right.right));
           if(deletion==true){curr.height=1+Math.max(getHeight(curr.left),getHeight(curr.right));}
            else{updateheights(curr);}
               // this is the case of linear left rotation
            // after performing the rotations, update the heights which have been changed after the rotation operations
       }
       else{
           curr=leftright(curr);
           curr.right.height=1+Math.max(getHeight(curr.right.left),getHeight(curr.right.right));
            curr.left.height=1+Math.max(getHeight(curr.left.left),getHeight(curr.left.right));
           if(deletion==true){
            curr.height=1+Math.max(getHeight(curr.left),getHeight(curr.right));
           }
            else{updateheights(curr);}
                // this is the case of left-right rotation
            // after performing the rotations, update the heights which have been changed after the rotation operations
       }
   }
   else{
       if(getHeight(curr.right.right)>=getHeight(curr.right.left)){
          curr=linearright(curr);
               curr.left.height=1+Math.max(getHeight(curr.left.left),getHeight(curr.left.right));
           if(deletion==true){curr.height=1+Math.max(getHeight(curr.left),getHeight(curr.right));}
            else{updateheights(curr);}
                // this is the case of linear right rotation
            // after performing the rotations, update the heights which have been changed after the rotation operations
       }
       else{
           curr=rightleft(curr);
          curr.right.height=1+Math.max(getHeight(curr.right.left),getHeight(curr.right.right));
            curr.left.height=1+Math.max(getHeight(curr.left.left),getHeight(curr.left.right));
           if(deletion==true){
            curr.height=1+Math.max(getHeight(curr.left),getHeight(curr.right));
           }
            else{updateheights(curr);}
                    // this is the case of right-left rotation
            // after performing the rotations, update the heights which have been changed after the rotation operations
       }
   }
  }
  private AVLTree linearleft(AVLTree ok){    // linear left rotations
        AVLTree child=ok.left;
        ok.left=child.right;
        if(child.right!=null){child.right.parent=ok;}
        child.right=ok;
        child.parent=ok.parent;
        if(ok.parent.right==ok){
            ok.parent.right=child;
        }
        else{
            ok.parent.left=child;
        }
        ok.parent=child;
        return child;
  }
    private AVLTree leftright(AVLTree ok){   //left-right rotations
      ok.left=linearright(ok.left);
      return linearleft(ok);
    }
    private AVLTree linearright(AVLTree ok){ //linear right rotations
        AVLTree child=ok.right;
        ok.right=child.left;
        if(child.left!=null) {child.left.parent=ok;}
        child.left=ok;
        child.parent=ok.parent;
            if(ok.parent.right==ok){
                ok.parent.right=child;
            }
            else{
                ok.parent.left=child;
            }
        ok.parent=child;
        return child;
    }
    private AVLTree rightleft(AVLTree ok){   // right-left rotations
        ok.right=linearleft(ok.right);
        return linearright(ok);
    }
    private boolean helpdelete(Dictionary e){
        AVLTree curr=this;
        if(compare(e,curr)==2){
            if(curr.left==null){return false;}  //e not found
            else{return curr.left.helpdelete(e);}   //else search in the left subtree
        }
        else if (compare(e,curr)==1){
            if(curr.right==null){return false;} //e not found
            else{return curr.right.helpdelete(e);}  //else search in the right subtree
        }
        else{
                if(curr.left==null && curr.right==null){
                    AVLTree par=curr.parent;                    // node to be deleted has zero children
                    if(curr.parent.left==curr){curr.parent.left=null;}
                    else{curr.parent.right=null;}
                    curr.parent=null;
                    curr=null;
                    balancetree(par,true);//the height of the parents is updated in this function only
                    return true;

                }
                else if (curr.left==null){    
                AVLTree par=curr.parent;                              // node to be deleted has only right child
                    curr.right.parent=curr.parent;
                    if(curr.parent.left==curr){curr.parent.left=curr.right;}
                    else{curr.parent.right=curr.right;}
                    curr.right=null;
                    curr.parent=null;
                    curr=null;
                    balancetree(par,true);
                    return true;
                }
                else if (curr.right==null){ 
                AVLTree par=curr.parent;                               // node to be deleted has only left child
                      curr.left.parent=curr.parent;
                    if(curr.parent.left==curr){curr.parent.left=curr.left;}
                    else{curr.parent.right=curr.left;}
                    curr.left=null;
                    curr.parent=null;
                    curr=null;
                    balancetree(par,true);
                    return true;
                }
                else{                                                       // node to be deleted has both left and right children, finding successor in this case
                    AVLTree succ=curr.getNext();
                    curr.address=succ.address;
                    curr.key=succ.key;
                    curr.size=succ.size;
                    return curr.right.helpdelete(succ);
                }
            }
            }
    public boolean Delete(Dictionary e)
    {
        if(e==null){return false;}
          AVLTree curr=this.getRoot();
        if(curr.right==null){return false;}//tree is empty
        curr=curr.right;
        return curr.helpdelete(e);
    }
    private AVLTree helpinsert(AVLTree node){
         AVLTree curr=this;           
        if(compare(node,curr)==2){      // insertion in the left subtree of the current node
            if(curr.left==null){
                node.parent=curr;
                curr.left=node;
                updateheights(node);
                balancetree(node,false);
                return node;
            }
            else{
            return curr.left.helpinsert(node);
        }
        }
        else {                                       // insertion in the right subtree of the current node
            if(curr.right==null){
                node.parent=curr;
                curr.right=node;
                  updateheights(node);
                balancetree(node,false);
                return node;
            }
            else{return curr.right.helpinsert(node);}
        }
    }
    public AVLTree Insert(int address, int size, int key) 
    { 
         AVLTree curr=this.getRoot();
        if(curr.right==null){// when tree is empty
            AVLTree node =new AVLTree (address,size,key);
            node.parent=curr;
            curr.right=node;
            return node;
        }
        curr=curr.right;//if tree is non empty, call the function for the actual root node
        //which contains the data
        AVLTree node1 =new AVLTree (address,size,key);// create the node to be inserted
        return curr.helpinsert(node1);
    }
    private AVLTree helpfind(int key, boolean exact){ //helper function for finding key. if exact is true, return exact match with minimum address
        AVLTree curr=this;
            if(curr!=null){
                if(curr.key<key){
                    if(curr.right!=null){
                        return curr.right.helpfind(key,exact);
                    }
                    else{return null;}
                }
                else{
                    if(curr.left==null){
                        if (!exact || (exact && key==curr.key)){return curr;}
                        else{return null;}
                    }
                    else{
                    AVLTree okay=curr.left.helpfind(key,false); // find min node in the left subtree greater than equal to the key, thus exact is false
                    if(okay==null){ // all nodes are lesser than the key
                        if(!exact || (exact && key==curr.key)){return curr;}
                        else{return null;}
                    }
                    else{
                      if(!exact ||(exact && okay.key==key)){return okay;}
                    }
                }
        }
    }
    return null;
    }
     private AVLTree Successor(AVLTree node){ //helper function to find successor in the right subtee of a node, given that right subtree exists
        AVLTree curr=node.right;
        while(curr.left!=null){curr=curr.left;}
        return curr;
    }
    public AVLTree Find(int k, boolean exact) 
    { 
        AVLTree curr=this.getRoot();
        if(curr.right==null){return null;}//empty tree
        return curr.right.helpfind(k,exact);
    }

    public AVLTree getFirst()
    { 
        AVLTree curr=this.getRoot();// gives the sentinel node in the tree
        if(curr.right==null){return null;}//empty tree 
        else{
        curr=curr.right;                                         
            while(curr.left!=null){
                curr=curr.left;
            }
            return curr;
        }
    }

    public AVLTree getNext()
    { 
         AVLTree node=this;  // for this we do not go to the root, as we need next element for a given element
        if(node.parent==null){return null;} // sentinel node
        if(node.right!=null){
            return Successor(node);
        }
        else{
            if(node.parent.parent!=null){
            AVLTree x=node;AVLTree y;                                 //else propagate up to find the lowest ancestor of node such that its left child is also
            while(x.parent.parent!=null){                           //an ancestor of the node
                y=x.parent;
                if(x==y.left){
                    return y;
                }
                else{
                    x=y;
                }
            }
        }
    }
        return null;
    }
  
        private boolean helpcheckbst(AVLTree curr,AVLTree emin, AVLTree emax){ //helper function for checkBST
        if(curr==null){return true;}
        if(compare(curr,emin)==2 || compare(curr,emax)==1){ //current node is lesser than min allowed value, or larger than max allowed value
            return false;
        }
        int mk=emax.key;int ma=emax.address;int ms=emax.size;
        emax.key=curr.key;emax.address=curr.address;emax.size=curr.size-1;
        boolean res1= helpcheckbst(curr.left,emin,emax);
        emax.key=mk;emax.address=ma;emax.size=ms;
        emin.key=curr.key;emin.address=curr.address;emin.size=curr.size+1;
        return res1 && helpcheckbst(curr.right,emin,emax); // updated emin and emax

    }
    private boolean checkbst(){ // helper for sanity
        AVLTree curr=this.getRoot();
        if(curr.right==null){return true;}// an empty tree is a BST by default
        else{
            AVLTree emin= new AVLTree(Integer.MIN_VALUE,Integer.MIN_VALUE,Integer.MIN_VALUE);
            AVLTree emax= new AVLTree(Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE);
            return helpcheckbst(curr.right,emin,emax);
        }
    }
    private boolean helpcycle(AVLTree curr){
        if(curr==null){return true;}
        if(curr.right!=null){if(curr.right.parent!=curr){return false;}}
        if(curr.left!=null){if(curr.left.parent!=curr){return false;}}
        return helpcycle(curr.left) && helpcycle(curr.right);

    }
    private boolean checkCycle(){
           AVLTree curr=this;// slow pointer
           AVLTree fcurr=curr;// fast pointer
           while(curr!=null && fcurr!=null && fcurr.parent!=null){
                curr=curr.parent;
                fcurr=fcurr.parent.parent;
                if(curr==fcurr){
                    return false;
                }
            }
            //if this is not false,then there is no cycle of parent pointers. Thus we can safely reach the
            //root node in that case
            curr=this.getRoot(); // now we can reach the root node without being stuck in a cycle
            //now we will perform a DFS traversal of the tree and check for cycles
            if(curr.right==null){return true;} //empty tree is acyclic
            else{
                return helpcycle(curr.right);
            }
    }
    private boolean helpcheckavl(AVLTree curr){
        if(curr==null){return true;}
        int val=getHeight(curr.left)-getHeight(curr.right);
        if(val > 1 || val < -1){return false;}
        else{return helpcheckavl(curr.left) && helpcheckavl(curr.right);}
    }
    private boolean checkavl(){
        AVLTree curr =this.getRoot();
        if(curr.right==null){return true;} // an empty tree is height balanced by default
        else{
            return helpcheckavl(curr.right);
        }
    }
    public boolean sanity(){
        //The invariants checked are as follows:
        //1.) Cycle detection - a tree is an acyclic graph, so cannot have a cycle
        //2.) BST invariant, all the nodes in the left subtree are lesser than the current node, and greater in right subtree
        //3.) AVL tree height balancing property, checks if the difference in height of two siblings is atmost 1
        //4.) The left child of the sentinel root node must be null
        //5.) The value of key, address and size are all -1 for the sentinel root node

    // for cycle detection, I will traverse up to the root from the current node. I will check the cycle of the parent 
        //pointers, by floyd algorithm. then after I get to the root, i traverse the tree recursuvely and check the fact
        //that curr.right.parent=curr, if the right child exists and similarly for the left child
        if(this==null){return false;}//null pointer exception
    if(this.checkCycle()==false){return false;}//checks the presence of cycle in a tree
     if(this.checkbst()==false){return false;} // check the BST invariant, as AVL tree is also a BST
     if(this.checkavl()==false){return false;} //checks the height balance property of the AVL tree
     AVLTree curr=this.getRoot();
     if(curr.left!=null){return false;}   // the left child of the sentinel root must be null
     if(!(curr.key==-1 && curr.size==-1 && curr.address==-1)){return false;} // the key,address and size of the sentinel root
     return true;
    }

    }


