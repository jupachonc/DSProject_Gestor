package co.dsproject.gestor;

public class Lista<T extends Comparable<T>> {

    private DoubleNode<T> head;

    public Lista(){
        head = null;
    }

    public void insert(T item){

        //boolean inserted;
        DoubleNode<T> ptr, NodeI;
        //inserted = false;
        ptr = head;
        while(ptr != null && ptr.getNext() != null && !ptr.getData().equals(item)){
            ptr = ptr.getNext();
        }
        DoubleNode newi = new DoubleNode(item);
        if(ptr == null){
            //inserted = true;
            head = newi;
        }else{
            if(!ptr.getData().equals(item)){
                //inserted = true;
                ptr.setNext(newi);
                newi.setBack(ptr);
            }
        }

        //return  inserted;
    }

    public boolean delete(T item){

        boolean deleted;
        DoubleNode<T> ptr;
        deleted = false;
        ptr = head;

        while(ptr != null && !ptr.getData().equals(item)){
            ptr = ptr.getNext();
        }
        if(ptr != null){
            deleted = true;
            if(ptr.getBack() != null && ptr.getNext() != null){
                ptr.getBack().setNext(ptr.getNext());
                ptr.getNext().setBack(ptr.getBack());
            }else if(ptr.getBack() == null && ptr.getNext() != null){
                head = ptr.getNext();
                ptr.getNext().setBack(null);
            }else if(ptr.getNext() == null && ptr.getBack() != null){
                ptr.getBack().setNext(null);
            }else{
                head = null;
            }

        }
        return deleted;
    }

    public void setHead(DoubleNode<T> head) {
        this.head = head;
    }

    public T getItem(T object){
        DoubleNode pointer = head;

        while(pointer != null){
            if(pointer.getData().equals(object)){
                return (T) pointer.getData();
            }else{
                pointer = pointer.getNext();
            }
        }
        return null;

    }

    public DoubleNode<T> getHead() {
        return head;
    }
}
