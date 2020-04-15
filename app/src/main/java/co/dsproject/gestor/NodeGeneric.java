package co.dsproject.gestor;

public class NodeGeneric<T> {
    private T data;
    private NodeGeneric<T> next;

    public NodeGeneric() {
        this(null);
    }

    public NodeGeneric(T data) {
        this.data = data;
        next = null;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public NodeGeneric getNext() {
        return next;
    }

    public void setNext(NodeGeneric<T> next) {
        this.next = next;
    }
}