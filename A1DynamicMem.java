// Class: A1DynamicMem
// Implements DynamicMem
// Does not implement defragment (which is for A2).

public class A1DynamicMem extends DynamicMem {
      
    public A1DynamicMem() {
        super();
    }

    public A1DynamicMem(int size) {
        super(size);
    }

    public A1DynamicMem(int size, int dict_type) {
        super(size, dict_type);
    }

    public void Defragment() {
        return ;
    }

    // In A1, you need to implement the Allocate and Free functions for the class A1DynamicMem
    // Test your memory allocator thoroughly using Doubly Linked lists only (A1List.java).
    // While inserting into the list, only call insert at the head of the list
    // Please note that ALL insertions in the DLL (used either in A1DynamicMem or used independently as the “dictionary” class implementation) are to be made at the HEAD (from the front).
    // Also, the find-first should start searching from the head (irrespective of the use for A1DynamicMem). Similar arguments will follow with regards to the ROOT in the case of trees (specifying this in case it was not so trivial to anyone of you earlier)
    public int Allocate(int blockSize) {
        if(blockSize<=0){return -1;}
        Dictionary ok=this.freeBlk.Find(blockSize,false);
        if(ok!=null){
            if(ok.size==blockSize){
                int ans=ok.address;
                this.allocBlk.Insert(ok.address,ok.size,ok.address);
                this.freeBlk.Delete(ok);
                ok=null;
                return ans;
            }
            else{
                int ans=ok.address;
                int s1=ok.size-blockSize;
                int a1=ok.address+blockSize;
                this.allocBlk.Insert(ok.address,blockSize,ok.address);
                this.freeBlk.Insert(a1,s1,s1);
                this.freeBlk.Delete(ok);
                ok=null;
                return ans;
            }
        }
        return -1;
    } 
    
    public int Free(int startAddr) {
        Dictionary ok = this.allocBlk.Find(startAddr,true);
        if(ok!=null){
            this.freeBlk.Insert(ok.address,ok.size,ok.size);
            this.allocBlk.Delete(ok);
            ok=null;
            return 0;
        }
        return -1;
    }
}