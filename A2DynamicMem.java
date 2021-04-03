// Class: A2DynamicMem
// Implements Degragment in A2. No other changes should be needed for other functions.

public class A2DynamicMem extends A1DynamicMem {
      
    public A2DynamicMem() {  super(); }

    public A2DynamicMem(int size) { super(size); }

    public A2DynamicMem(int size, int dict_type) { super(size, dict_type); }

    // In A2, you need to test your implementation using BSTrees and AVLTrees. 
    // No changes should be required in the A1DynamicMem functions. 
    // They should work seamlessly with the newly supplied implementation of BSTrees and AVLTrees
    // For A2, implement the Defragment function for the class A2DynamicMem and test using BSTrees and AVLTrees. 
    //Your BST (and AVL tree) implementations should obey the property that keys in the left subtree <= root.key < keys in the right subtree. How is this total order between blocks defined? It shouldn't be a problem when using key=address since those are unique (this is an important invariant for the entire assignment123 module). When using key=size, use address to break ties i.e. if there are multiple blocks of the same size, order them by address. Now think outside the scope of the allocation problem and think of handling tiebreaking in blocks, in case key is neither of the two. 
    public void Defragment() {
         Dictionary tree;
        if(this.type==2){tree=new BSTree();}
        else{tree=new AVLTree();}
        for(Dictionary i=this.freeBlk.getFirst();i!=null;i=i.getNext()){
            tree.Insert(i.address,i.size,i.address);
        }
        // inserting nodes from the freeblock into the new tree made according to keys as addresses
        Dictionary block=tree.getFirst();
        // first node in the new tree
        if(block!=null){ // if the tree is non empty
            while(block.getNext()!=null){ // while there is a next element so that we may perform merging
                Dictionary nex = block.getNext();
                if(nex.address==block.address+block.size){
                    int newAdd=block.address;   // address of new combined block
                    int newSize=nex.size+block.size;  // size of new combined block
                    // we will now delete these nodes from the old tree, in which the key values are equal to their sizes
                    Dictionary node1,node2;
                    // we will have to create new nodes with the same values, as delete function works by comparing values
                    if(this.type==2){
                    node1=new BSTree(block.address,block.size,block.size);
                    node2=new BSTree(nex.address,nex.size,nex.size);
                }
                else{
                    node1=new AVLTree(block.address,block.size,block.size);
                    node2=new AVLTree(nex.address,nex.size,nex.size);
                }
                    this.freeBlk.Delete(node1);// deleting node1 from freeblk
                    this.freeBlk.Delete(node2);//deleting node2 from freeblk
                    tree.Delete(block); // deleting the first node from new tree
                    tree.Delete(nex);    //deleting the second node from new tree
                    block=tree.Insert(newAdd,newSize,newAdd); // continue traversing with the merged node
                    // we continue traversal in the new tree indexed by addresses with the merged 
                    //block as the current node
                    this.freeBlk.Insert(newAdd,newSize,newSize);
                    //inserting merged node in the free block
                    // as nodes in freeblock have the keys with values equal to sizes
                }
                else{
                    block=nex;
                }
            }
        }
        tree=null;
        return ;
    }
}