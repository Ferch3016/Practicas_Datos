package mx.unam.ciencias.edd;

/**
 * Clase para colas genéricas.
 */
public class Cola<T> extends MeteSaca<T> {

    /**
     * Regresa una representación en cadena de la cola.
     * @return una representación en cadena de la cola.
     */
    @Override public String toString() {
        if (esVacia())
		return "";

	    String ne = "";
	    Nodo n = cabeza;

	    while (n != null) {
		ne += n.elemento.toString() + ",";
		n = n.siguiente;
	} 
	
	return ne;
}

    /**
     * Agrega un elemento al final de la cola.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void mete(T elemento) {
        if (elemento == null) 
		throw new IllegalArgumentException("No hay nada que agregar");
	
	    Nodo n = new Nodo(elemento);

	    if (rabo == null) {
		rabo = cabeza = n;
	    }else{
		    rabo.siguiente = n;
		    rabo = n;
	    }
    }
}
