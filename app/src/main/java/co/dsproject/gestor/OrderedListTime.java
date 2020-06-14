package co.dsproject.gestor;


import co.dsproject.gestor.models.TaskModel;


public class OrderedListTime{

    private NodeGeneric<TaskModel> head;

    public OrderedListTime() {
        head = null;
    }

    public void add(TaskModel item) {

        NodeGeneric<TaskModel> ptr, prev;

        ptr = head;
        prev = null;

        while(ptr != null && ptr.getData().compareTo(item) < 0){
            prev = ptr;
            ptr = ptr.getNext();
        }
        if(ptr == null || !(ptr.getData().equals(item))){
            NodeGeneric<TaskModel> newp = new NodeGeneric<TaskModel>(item);
            newp.setNext(ptr);
            if(prev == null)
                head = newp;
            else
                prev.setNext(newp);
        }

    }

    public void remove(TaskModel item){
        NodeGeneric<TaskModel> ptr, prev;

        ptr = head;
        prev = null;
        while(ptr != null && !(ptr.getData().equals(item))){
            prev = ptr;
            ptr = ptr.getNext();
        }

        if(ptr != null){
            if(prev == null)
                head = ptr.getNext();
            else
                prev.setNext(ptr.getNext());
        }
    }

    public NodeGeneric<TaskModel> getHead() {
        return head;
    }
}