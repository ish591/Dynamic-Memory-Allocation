// Class: Implementation of BST in A2
// Implement the following functions according to the specifications provided in Tree.java

public class BSTree extends Tree {

    private BSTree left, right;     // Children.
    private BSTree parent;          // Parent pointer.
        
    public BSTree(){  
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node!.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
    }    

    public BSTree(int address, int size, int key){
        super(address, size, key); 
    }
    private BSTree getRoot(){ //helper function
        BSTree curr=this;
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
    private BSTree helpinsert(BSTree node){   //helper function for insertion in a BST tree
        BSTree curr=this;
        if(compare(node,curr)==2){                         // insertion in the left subtree of the current node
            if(curr.left==null){
                node.parent=curr;                               // assuming there are no duplicates in the tree
                curr.left=node;
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
                return node;
            }
            else{return curr.right.helpinsert(node);}
        }
    }

    public BSTree Insert(int address, int size, int key) 
    { 
        BSTree curr=this.getRoot();
        if(curr.right==null){// when tree is empty
            BSTree node =new BSTree (address,size,key);
            node.parent=curr;
            curr.right=node;
            return node;
        }
        curr=curr.right;//if tree is non empty, call the function for the actual root node
        //which contains the data
        BSTree node1 =new BSTree (address,size,key);// create the node to be inserted
        return curr.helpinsert(node1);
    }
    private BSTree Successor(BSTree node){ //helper function to find successor only in the right subtee of a node, given that right subtree exists
        BSTree curr=node.right;
        while(curr.left!=null){curr=curr.left;}
        return curr;
    }
    private boolean helpdelete(Dictionary e){
        BSTree curr=this;
        if(compare(e,curr)==2){  // e is smaller than curr
            if(curr.left==null){return false;}  //e not found
            else{return curr.left.helpdelete(e);}   //else search in the left subtree
        }
        else if (compare(e,curr)==1){  // e is larger than curr
            if(curr.right==null){return false;} //e not found
            else{return curr.right.helpdelete(e);}  //else search in the right subtree
        }
        else{                                      // the nodes match all the fields
                if(curr.left==null && curr.right==null){                    // node to be deleted has zero children
                    if(curr.parent.left==curr){curr.parent.left=null;} // if current node is left child of its parent
                    else{curr.parent.right=null;}                       // if current node is right child of its parent
                    curr.parent=null; 
                    curr=null; 
                    return true;                                 
                }
                else if (curr.left==null){                                  // node to be deleted has only right child
                    curr.right.parent=curr.parent;
                    if(curr.parent.left==curr){curr.parent.left=curr.right;} 
                    else{curr.parent.right=curr.right;}
                    curr.right=null;
                    curr.parent=null;
                    curr=null;
                    return true;
                }
                else if (curr.right==null){                                 // node to be deleted has only left child
                      curr.left.parent=curr.parent;
                    if(curr.parent.left==curr){curr.parent.left=curr.left;}
                    else{curr.parent.right=curr.left;}
                    curr.left=null;
                    curr.parent=null;
                    curr=null;
                    return true;
                }
                else{                                                       // node to be deleted has both left and right children, finding successor in this case
                    BSTree succ=Successor(curr);// returns the first node greater than equal to the current node
                    curr.address=succ.address;//copying the data of the node in the successor
                    curr.key=succ.key;
                    curr.size=succ.size;
                    return curr.right.helpdelete(succ);// delete the actual successor node now
                }
        }
    }
    public boolean Delete(Dictionary e)
    { 
        BSTree curr=this.getRoot();
        if(curr.right==null){return false;}//tree is empty
        curr=curr.right;
        return curr.helpdelete(e);
    }
    private BSTree helpfind(int key, boolean exact){ //helper function for finding key. if exact is true, return exact match with minimum address
        BSTree curr=this;
            if(curr!=null){
                if(curr.key<key){ // if key of the current node is lesser, then only search in the right subtree, if not null
                    if(curr.right!=null){
                        return curr.right.helpfind(key,exact);
                    }
                    else{return null;}
                }
                else{ // curr.key<=key
                    if(curr.left==null){                // no left subtree
                        if (!exact || (exact && key==curr.key)){return curr;}
                        else{return null;}
                    }
                    else{                   // now, either the required node is in the left subtree, or it is the current node
                    BSTree okay=curr.left.helpfind(key,false); // find min node in the left subtree greater than equal to the key, thus exact is false
                    if(okay==null){ // all nodes are lesser than the key in the left subtree
                        if(!exact || (exact && key==curr.key)){return curr;}  // curr node is the only possible node
                        else{return null;}
                    }
                    else{          
                         if(!exact || okay.key==key){return okay;}
                        }
                    }
                }
        }
    return null;
    }
    public BSTree Find(int key, boolean exact) 
    { 
        BSTree curr=this.getRoot();
        if(curr.right==null){return null;}//empty tree
        return curr.right.helpfind(key,exact);
    }

    public BSTree getFirst()
    { 
        BSTree curr=this.getRoot();// gives the sentinel node in the tree
        if(curr.right==null){return null;}//empty tree 
        else{
        curr=curr.right;                                         
            while(curr.left!=null){
                curr=curr.left;
            }
            return curr;
        }
    }

    public BSTree getNext()
    { 
         BSTree node=this;  // for this we do not go to the root, as we need next element for a given element
        if(node.parent==null){return null;} // sentinel node
        if(node.right!=null){
            return Successor(node);
        }
        else{
            if(node.parent.parent!=null){
            BSTree x=node;BSTree y;                                 //else propagate up to find the lowest ancestor of node such that its left child is also
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
    private boolean helpcheckbst(BSTree curr,BSTree emin, BSTree emax){ //helper function for checkBST
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
        BSTree curr=this.getRoot();
        if(curr.right==null){return true;}// an empty tree is a BST by default
        else{
            BSTree emin= new BSTree(Integer.MIN_VALUE,Integer.MIN_VALUE,Integer.MIN_VALUE);
            BSTree emax= new BSTree(Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE);
            return helpcheckbst(curr.right,emin,emax);
        }
    }
    private boolean helpcycle(BSTree curr){
        if(curr==null){return true;}
        if(curr.right!=null){if(curr.right.parent!=curr){return false;}}
        if(curr.left!=null){if(curr.left.parent!=curr){return false;}}
        return helpcycle(curr.left) && helpcycle(curr.right);

    }
    private boolean checkCycle(){
           BSTree curr=this;// slow pointer
           BSTree fcurr=curr;// fast pointer
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
    public boolean sanity()
    { 
          //The invariants checked are as follows:
        //1.) Cycle detection - a tree is an acyclic graph, so cannot have a cycle
        //2.) BST invariant, all the nodes in the left subtree are lesser than the current node, and greater in right subtree
        //3.) The left child of the sentinel root node must be null
        //4.) The value of key, address and size are all -1 for the sentinel root node
        
        // for cycle detection, I will traverse up to the root from the current node. I will check the cycle of the parent 
        //pointers, by floyd algorithm. then after I get to the root, i traverse the tree recursuvely and check the fact
        //that curr.right.parent=curr, if the right child exists and similarly for the left child
        if(this==null){return false;}//null pointer exception
    if(this.checkCycle()==false){return false;}//checks the presence of cycle in a tree
     if(this.checkbst()==false){return false;} // check the BST invariant
     BSTree curr=this.getRoot();
     if(curr.left!=null){return false;}   // the left child of the sentinel root must be null
     if(!(curr.key==-1 && curr.size==-1 && curr.address==-1)){return false;} // the key,address and size of the sentinel root
     return true;
    }



}