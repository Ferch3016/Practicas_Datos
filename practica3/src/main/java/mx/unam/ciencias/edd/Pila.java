package mx.unam.ciencias.edd;

/**
 * Clase para pilas genéricas.
 */
public class Pila<T> extends MeteSaca<T> {

    /**
     * Regresa una representación en cadena de la pila.
     * @return una representación en cadena de la pila.
     */
    @Override public String toString() {
        if ( cabeza == null )
		return "";

	String ne = "";
	Nodo n = cabeza;

	while ( n != null ) {
		ne += n.elemento.toString() + "\n";
		n = n.siguiente;
	}
	return ne;
}

    /**
     * Agrega un elemento al tope de la pila.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void mete(T elemento) {
        if ( elemento == null ) 
		throw new IllegalArgumentException("No hay nada que agregar");

	Nodo n = new Nodo(elemento);

	if ( rabo == null ) {
		rabo = cabeza = n;
	} else {
		n.siguiente = cabeza;
		cabeza = n;
	    }
    }
}
