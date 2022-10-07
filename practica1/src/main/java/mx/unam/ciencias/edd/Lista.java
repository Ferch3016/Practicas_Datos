package mx.unam.ciencias.edd;

import java.lang.management.ThreadInfo;
import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.lang.model.element.Element;

/**
 * <p>Clase genérica para listas doblemente ligadas.</p>
 *
 * <p>Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.</p>
 *
 * <p>Las listas no aceptan a <code>null</code> como elemento.</p>
 *
 * @param <T> El tipo de los elementos de la lista.
 */
public class Lista<T> implements Coleccion<T> {

    /* Clase interna privada para nodos. */
    private class Nodo {
        /* El elemento del nodo. */
        private T elemento;
        /* El nodo anterior. */
        private Nodo anterior;
        /* El nodo siguiente. */
        private Nodo siguiente;

        /* Construye un nodo con un elemento. */
        private Nodo(T elemento) {
            this.elemento = elemento;
        }
    }

    /* Clase interna privada para iteradores. */
    private class Iterador implements IteradorLista<T> {
        /* El nodo anterior. */
        private Nodo anterior;
        /* El nodo siguiente. */
        private Nodo siguiente;

        /* Construye un nuevo iterador. */
        private Iterador() {
            siguiente = cabeza;

        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return siguiente != null;
        }

        /* Nos da el elemento siguiente. */
        @Override public T next() {
            if(siguiente == null)
            throw new NoSuchElementException("No hay un elemento después");
            anterior = siguiente;
            siguiente = siguiente.siguiente;
            return anterior.elemento;
        }

        /* Nos dice si hay un elemento anterior. */
        @Override public boolean hasPrevious() {
            return anterior != null;
        }

        /* Nos da el elemento anterior. */
        @Override public T previous() {
            if(anterior == null)
            throw new NoSuchElementException("No hay ningun elemento antes");
            siguiente = anterior;
            anterior = anterior.anterior;
            return siguiente.elemento;
        }

        /* Mueve el iterador al inicio de la lista. */
        @Override public void start() {
            this.siguiente = cabeza;
            this.anterior = null;
        }

        /* Mueve el iterador al final de la lista. */
        @Override public void end() {
            this.siguiente = null;
            this.anterior = rabo;
        }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud;

    /**
     * Regresa la longitud de la lista. El método es idéntico a {@link
     * #getElementos}.
     * @return la longitud de la lista, el número de elementos que contiene.
     */
    public int getLongitud() {
        return longitud;
    }

    /**
     * Regresa el número elementos en la lista. El método es idéntico a {@link
     * #getLongitud}.
     * @return el número elementos en la lista.
     */
    @Override public int getElementos() {
        return longitud;
    }

    /**
     * Nos dice si la lista es vacía.
     * @return <code>true</code> si la lista es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        return cabeza==null;

        
    }

    /**
     * Agrega un elemento a la lista. Si la lista no tiene elementos, el
     * elemento a agregar será el primero y último. El método es idéntico a
     * {@link #agregaFinal}.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        if(elemento == null)
        throw new IllegalArgumentException("No hay nada que agregar");
        longitud ++;
        Nodo n = new Nodo(elemento);
        if(rabo == null) {
            cabeza = rabo = n;
        }else{
            rabo.siguiente = n;
            n.anterior = rabo;
            rabo = n;
        }

    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaFinal(T elemento) {
        if (elemento == null)
        throw new IllegalArgumentException("No hay nada que agregar");
        longitud ++;
        Nodo n = new Nodo(elemento);
        if(rabo == null){
            cabeza = rabo = n;
        }else{
            rabo.siguiente = n;
            n.anterior = rabo;
            rabo = n;
        }
        
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaInicio(T elemento) {
        if(elemento == null)
        throw  new IllegalArgumentException("No hay nada que agregar");
        longitud ++;
        Nodo n = new Nodo(elemento);
        if(cabeza == null){
            cabeza = rabo = n;
        }else{
            n.siguiente = cabeza;
            cabeza.anterior = n;
            cabeza = n;
        }

    }

    /**
     * Inserta un elemento en un índice explícito.
     *
     * Si el índice es menor o igual que cero, el elemento se agrega al inicio
     * de la lista. Si el índice es mayor o igual que el número de elementos en
     * la lista, el elemento se agrega al fina de la misma. En otro caso,
     * después de mandar llamar el método, el elemento tendrá el índice que se
     * especifica en la lista.
     * @param i el índice dónde insertar el elemento. Si es menor que 0 el
     *          elemento se agrega al inicio de la lista, y si es mayor o igual
     *          que el número de elementos en la lista se agrega al final.
     * @param elemento el elemento a insertar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void inserta(int i, T elemento) {
        if(elemento == null)
        throw new IllegalArgumentException("No hay nada que insertar");
        if(i <= 0) {
            agregaInicio(elemento);
    
        } else if (i >= longitud){
         agregaFinal(elemento);
            
        } else {
            Nodo esteNodo = cabeza;
            int r = 0;
            this.longitud++;
            while(r != i){
                esteNodo = esteNodo.siguiente;
                r++;
            }
            Nodo n = new Nodo(elemento);
            n.siguiente = esteNodo;
            n.anterior = esteNodo.anterior;
            esteNodo.anterior = n;
            n.anterior.siguiente = n;
        }
     
    }

    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no la modifica.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        if(cabeza != null){
            Nodo n = cabeza;
            while(n != null){
                if(n.elemento.equals(elemento)) 
                    break;
                n = n.siguiente;
            }
            if(n == null){
                return;
            }
            if(n == cabeza){
                eliminaPrimero();
            }else if (n == rabo){
                eliminaUltimo();
             }else{
                n.anterior.siguiente = n.siguiente;
                n.siguiente.anterior = n.anterior;
                longitud --;
            }
        }
    }   
    

    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaPrimero() {
        if (cabeza == null)
        throw new NoSuchElementException("La lista es vacia");
        T e = cabeza.elemento;
        if(cabeza == rabo){
            cabeza = rabo = null;
        }else{
            cabeza = cabeza.siguiente;
            cabeza.anterior = null;
        } longitud--;
        return e;
        }
    

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() {
        if(rabo==null)
        throw new NoSuchElementException("La lista es vacia");
        T e = rabo.elemento;
        if(rabo.anterior == null){
            rabo = cabeza = null;
        }else{
            rabo = rabo.anterior;
            rabo.siguiente = null;

    }   longitud--;
    return e;
    }

    /**
     * Nos dice si un elemento está en la lista.
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <code>true</code> si <code>elemento</code> está en la lista,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        Nodo n = cabeza;
        while(n != null){
            if(n.elemento.equals(elemento)) 
                return true;
            n = n.siguiente;
        }
        return false;
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista<T> reversa() {
        Lista<T> r = new Lista<T>();
        Nodo n = cabeza;
        while(n != null){
            r.agregaInicio(n.elemento);
            n = n.siguiente;
        }
        return r;
    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * @return una copia de la lista.
     */
    public Lista<T> copia() {
        Lista<T> r = new Lista<T>();
        for (Nodo n = cabeza; n != null; n=n.siguiente) {
            r.agregaFinal(n.elemento);
        }
        return r;
    }

    /**
     * Limpia la lista de elementos, dejándola vacía.
     */
    @Override public void limpia() {
        rabo = null;
        cabeza = null;
        longitud = 0;
    }

    /**
     * Regresa el primer elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() {
        if(cabeza == null)
        throw new NoSuchElementException("No hay elementos");
        
        return cabeza.elemento;
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() {
        if(rabo== null)
        throw new NoSuchElementException("No hay elementos");

        return rabo.elemento;
    }    
    

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     * @throws ExcepcionIndiceInvalido si <em>i</em> es menor que cero o mayor o
     *         igual que el número de elementos en la lista.
     */
    public T get(int i) {
        if(i < 0 || i >= this.longitud)
        throw new ExcepcionIndiceInvalido("Indice fuera de rango");
        Nodo n = cabeza;
        for(int j = 0; j<i ; j++)
            n = n.siguiente;
        return n.elemento;
        
        
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si el elemento
     *         no está contenido en la lista.
     */
    public int indiceDe(T elemento) {
        Nodo n = cabeza;
        for(int i =0 ; i < this.longitud ; i++){
            if(n.elemento == elemento){
                return i;
            }
            n = n.siguiente;
        }
        return -1;
    }

    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    @Override public String toString() {
        String h = "[";
        if(cabeza != null){
            Nodo n = cabeza;
            for(; n.siguiente!=null; n = n.siguiente){
                h += n.elemento.toString() + ", ";
            }
            n = rabo;
            h+= n.elemento.toString();
        }
        h+= "]";
        return h;
    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la lista es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Lista<T> lista = (Lista<T>)objeto;
        
        if(lista.longitud != longitud)
            return false;
        Nodo c = cabeza;
        Nodo c1 = lista.cabeza;

        while(c != null){
            if(c.elemento.equals(c1.elemento)){
                c = c.siguiente;
                c1 = c1.siguiente;
            }else{
               return false; 
            }
        }
        return true;
    }

    /**
     * Regresa un iterador para recorrer la lista en una dirección.
     * @return un iterador para recorrer la lista en una dirección.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas direcciones.
     * @return un iterador para recorrer la lista en ambas direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador();
    }
}
