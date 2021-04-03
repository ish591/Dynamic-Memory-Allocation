// Implements Dictionary using Doubly Linked List (DLL)
// Implement the following functions using the specifications provided in the class List

public class A1List extends List {

    private A1List  next; // Next Node
    private A1List prev;  // Previous Node 

    public A1List(int address, int size, int key) { 
        super(address, size, key);
    }
    
    public A1List(){
        super(-1,-1,-1);
        // This acts as a head Sentinel

        A1List tailSentinel = new A1List(-1,-1,-1); // Intiate the tail sentinel
        
        this.next = tailSentinel;
        tailSentinel.prev = this;
    }

    public A1List Insert(int address, int size, int key)
    {
         // inserts a new node at the current node
        A1List curr=this;
        A1List node = new A1List(address,size,key);
        node.next=curr.next;
        node.prev=curr;
        node.next.prev=node;
        node.prev.next=node;
        return node;
    }

    public boolean Delete(Dictionary d) 
    {
        // will have to perform a bidirectional search in the DLL, here exact object matching is used
        A1List curr1=this;
        A1List curr2=curr1;
        // below code propagates forwards from the current node and checks for d in the list
        if(curr1.prev==null){curr1=curr1.next;} // in case that curr1 is at the head node
        if(curr1==null){return false;}//if no next of head
        while(curr1.next!=null){
            if(curr1.key!=d.key){
                curr1=curr1.next;
            }
            else{
                if(curr1.address==d.address && curr1.size ==d.size){       //matching by value
                    curr1.next.prev=curr1.prev;
                    curr1.prev.next=curr1.next;
                    curr1.next=null;
                    curr1.prev=null;
                    return true;
                }
            }
        }
        // below code propagates backwards from the current node and checks for d in the list
            if(curr2.next==null){curr2=curr2.prev;} // in case curr2 is at the tail node
            if(curr2==null){return false;}// if no prev of tail
        while(curr2.prev!=null){
            if(curr2.key!=d.key){
                curr2=curr2.prev;
            }
            else{
                if(curr2.address==d.address && curr2.size==d.size){
                    curr2.next.prev=curr2.prev;
                    curr2.prev.next=curr2.next;
                    curr2.next=null;
                    curr2.prev=null;
                    return true;
                }
            }
        }
        return false;
    }

    public A1List Find(int k, boolean exact)
    { 
       // first travel to the head, and then start finding
        A1List curr1=this.getFirst();
       if(curr1==null){return null;}
            while(curr1.next!=null) {
            if ((!exact && curr1.key>=k) || (exact && curr1.key == k)) { return curr1; }
            else{curr1=curr1.next;}
       }
       return null;
    }

    public A1List getFirst()
    {
        // returns element next to head in case of a non empty list
        A1List curr=this;
        while(curr.prev!=null){
            curr=curr.prev;
        }
        if(curr.next==null){return null;} // if there is no next of head, won't happen in this assignment
        if(curr.next.next!=null){       // checks if next element of the curr (which is head now) is tail or not
            return curr.next;
        }
        return null;
    }
    
    public A1List getNext() 
    {
          A1List curr=this;
        if(curr.next==null){return null;}// in case the current node was a sentinel tail node
        if(curr.next.next!=null){
            return curr.next;   // if the element is just before the tail node, it has no next element
        }
        return null;
    }

    public boolean sanity()
    {
        //The followinf invariants have beenc checked in this sanity function
        //1.) We have checked the presence of a cycle in the node, while traversing in both directions from the current pointer
        //2.) We have checked that the sentinel nodes exist or not
        //3.) We have checked whether previous of head is null
        //4.) we have checked whether next of tail is null
        //5.) we have checked that curr.next.prev = curr for any node (DLL invariant)
        //6.) we have checked that curr.prev.next =curr for any node (another DLL invariant)
        //checking cycle in the forward direction
        // detects if there is a cycle in a doubly linked list using two pointers
        if(this==null){return false;} // null pointer exception
            A1List curr=this;// slow pointer
            A1List fcurr=curr;          // fast pointer
            while(curr!=null && fcurr!=null && fcurr.next!=null){
                curr=curr.next;
                fcurr=fcurr.next.next;
                if(curr==fcurr){
                    return false;
                }
            }
            //checks cycle in the dll in the backward direction

            curr=this;
            fcurr=this;
            while(curr!=null && fcurr!=null && fcurr.prev!=null){
                curr=curr.prev;
                fcurr=fcurr.prev.prev;
                if(curr==fcurr){return false;}
            }
          // case of empty list

      curr=this;
      // let us handle the case when we are presently at one of the sentinel nodes
      if(curr.key==-1 && curr.size==-1 && curr.address==-1){
        if(curr.prev!=null && curr.next!=null){return false;} // one of these pointers must be null
        if(curr.prev==null && curr.next==null){return false;} // both cannot be null
        else if (curr.prev==null){ // we are at the head node. Now we will propagate right to find the tail sentinel node
            if(curr.next.prev!=curr){return false;}// invariant, prev of next is the node itself
            curr=curr.next;
            while(!(curr.key==-1 && curr.size==-1 && curr.address==-1)){if(curr.next==null){return false;}
            if(curr.next.prev!=curr){return false;}curr=curr.next;
             // prev of next is the node itself
            }// we also check the case when tail node is not present
            // now we are at the tail sentinel node
            if(curr.next!=null){return false;} // next of the tail node must be null
        }
        else{ // we are at the tail node
            if(curr.prev.next!=curr){return false;}
            curr=curr.prev;
            while(!(curr.key==-1 && curr.size==-1 && curr.address==-1)){if(curr.prev==null){return false;}// no head node
            if(curr.prev.next!=curr){return false;}curr=curr.prev;
        }
        if(curr.prev!=null){return false;} //head must have prev as null
        }
      }
      else{
      // else we propgate backwards and forwards to find head and tail
        A1List curr1=curr;
      while(!(curr.key==-1 && curr.size==-1 && curr.address==-1)){if(curr.prev==null){return false;}
      if(curr.prev.next!=curr){return false;}curr=curr.prev;
      }//there is no head node
      // going to the head node
      if(curr.prev!=null){return false;}//not a valid head node, as prev is not null
      // checking that the prev of next is the node itself, travelling from head to tail
      // now go forward from curr to the tail
      curr=curr1; // original position
       while(!(curr.key==-1 && curr.size==-1 && curr.address==-1)){if(curr.next==null){return false;}//no tail node
      if(curr.next.prev!=curr){return false;}curr=curr.next;
      }//there is no head node
      if(curr.next!=null){return false;} // tail node does not have the next node as null
  }
       
        return true;

    }


}