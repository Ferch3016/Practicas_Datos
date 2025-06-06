package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>Clase para árboles binarios completos.</p>
 *
 * <p>Un árbol binario completo agrega y elimina elementos de tal forma que el
 * árbol siempre es lo más cercano posible a estar lleno.</p>
 */
public class ArbolBinarioCompleto<T> extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Cola para recorrer los vértices en BFS. */
        private Cola<Vertice> cola;

        /* Inicializa al iterador. */
        private Iterador() {
            cola = new Cola<Vertice>();
		    if (raiz != null) {
			cola.mete(raiz);
		}
    }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return !cola.esVacia();
        }

        /* Regresa el siguiente elemento en orden BFS. */
        @Override public T next() {
        Vertice tempo;
		tempo = cola.saca();
		if (tempo.izquierdo != null)
			cola.mete(tempo.izquierdo);

		if (tempo.derecho != null) 
			cola.mete(tempo.derecho);

		return tempo.elemento;
		
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioCompleto() { super(); }


    //Auxiliares para conseguir el ultimo elemento y el vertice sin hijos
    private Vertice getUltimoEleme(Vertice v) {
	    Cola<Vertice> cola = new Cola<Vertice>();
	    cola.mete(v);
	    while (!cola.esVacia()) {
		Vertice tempo = cola.saca();
		if (tempo.izquierdo != null)
			cola.mete(tempo.izquierdo);
		if (tempo.derecho != null)
			cola.mete(tempo.derecho);

		if (cola.esVacia())
			return tempo;
	    }

	    return null;
    }

    private Vertice getPrimVertiSinHij(Vertice v) {
	    Cola<Vertice> cola = new Cola<Vertice>();
	    cola.mete(v);
	    while (!cola.esVacia()) {
		    Vertice tempo;
		    tempo = cola.saca();
		    if (tempo.izquierdo == null) {
			    return tempo;
		    } else {
			    cola.mete(tempo.izquierdo);
		    }

		    if (tempo.derecho == null) {
			    return tempo;
		    } else { 
			    cola.mete(tempo.derecho);
		    }
	    }

	    return null;
    }

    /**
     * Construye un árbol binario completo a partir de una colección. El árbol
     * binario completo tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario completo.
     */
    public ArbolBinarioCompleto(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un elemento al árbol binario completo. El nuevo elemento se coloca
     * a la derecha del último nivel, o a la izquierda de un nuevo nivel.
     * @param elemento el elemento a agregar al árbol.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        if (elemento == null)
		throw new IllegalArgumentException();

    	Vertice n = nuevoVertice(elemento);
	        elementos++;

	if (raiz == null) {
		raiz = n;
	}else{
		Vertice f = getPrimVertiSinHij(raiz);

		if ((elementos) % 2 == 0) {
			f.izquierdo = n;
			n.padre = f;
		}else{
			f.derecho = n;
			n.padre = f;
		}
	}
 }
    
    //Auxiliar para cambiar los vértices:
    private void cambia(Vertice a, Vertice ultimo) {
        T e = a.elemento;

        a.elemento = ultimo.elemento;
        ultimo.elemento = e;
}

    //Auxiliar para saber si hay un hijo izquuierdo
    private boolean hayIzq(Vertice v) {
	    if (v == null)
		    return false;
	    if (v.padre.izquierdo.equals(v)) 
		    return true;
	    return false;
    }


    /**
     * Elimina un elemento del árbol. El elemento a eliminar cambia lugares con
     * el último elemento del árbol al recorrerlo por BFS, y entonces es
     * eliminado.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        Vertice v = (Vertice) busca(elemento);

	if (v == null)
		return;

	elementos--;
	if (elementos == 0) {
		raiz = null;
	}else{
		Vertice f = getUltimoEleme(raiz);
		cambia(v,f);

		if (hayIzq(f)) {
			f.padre.izquierdo = null;
		}else{
			f.padre.derecho = null;
		}

	}
}

    /**
     * Regresa la altura del árbol. La altura de un árbol binario completo
     * siempre es ⌊log<sub>2</sub><em>n</em>⌋.
     * @return la altura del árbol.
     */
    @Override public int altura(){
        if (raiz == null) 
		return -1;
	return (int) Math.floor(Math.log(elementos)/Math.log(2));
}

    /**
     * Realiza un recorrido BFS en el árbol, ejecutando la acción recibida en
     * cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void bfs(AccionVerticeArbolBinario<T> accion) {
        if (raiz == null)
        return;
    Cola <Vertice> cola = new Cola<Vertice>();
    cola.mete(raiz);
    while (!cola.esVacia()) {
        Vertice tempo;
        tempo = cola.saca();
        accion.actua(tempo);
        if (tempo.izquierdo != null) 
            cola.mete(tempo.izquierdo);
        if (tempo.derecho != null) 
            cola.mete(tempo.derecho);

    }
}

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden BFS.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
