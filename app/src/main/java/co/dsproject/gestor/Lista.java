package co.dsproject.gestor;

import org.w3c.dom.Node;

public class Lista<T extends Comparable<T>> {

    private NodeGeneric<T> head;

    public Lista(){
        head = null;
    }

    public boolean insert(T item){

        boolean inserted;
        NodeGeneric<T> ptr, prev;
        inserted = false;
        ptr = head;
        prev = null;
        while(ptr != null && ptr.getData().compareTo(item) < 0){
            prev = ptr;
            ptr = ptr.getNext();
        }

        if(ptr == null && !(prev.getData().equals(item))){
            inserted = true;
            NodeGeneric<T> newNode = new NodeGeneric();
            newNode.setData(item);
            newNode.setNext(ptr);
            if(prev == null)
                head = newNode;
            else
                prev.setNext(newNode);
        }
        return  inserted;
    }

    public boolean delete(T item){

        boolean deleted;
        NodeGeneric<T> ptr, prev;
        deleted = false;
        ptr = head;
        prev = null;

        while(ptr != null && !ptr.getData().equals(item)){
            prev = ptr;
            ptr = ptr.getNext();
        }
        if(ptr != null && ptr.getData().equals(item)){
            deleted = true;
            prev.setNext(ptr.getNext());
        }
        return deleted;
    }




}
