package co.dsproject.gestor;

public class DoubleNode<T> {

    private T data;
    private DoubleNode<T> back, next;

    public DoubleNode(T data) {
        this.data = data;
        next = null;
        back = null;
    }

    public DoubleNode(){this(null);}

    public void setNext(DoubleNode<T> next) {
        this.next = next;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setBack(DoubleNode<T> back) {
        this.back = back;
    }

    public T getData() {
        return data;
    }

    public DoubleNode<T> getBack() {
        return back;
    }

    public DoubleNode<T> getNext() {
        return next;
    }
}
