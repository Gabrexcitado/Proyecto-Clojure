;;Documento que guarda las funciones de la biblioteca 
(ns library.core)

;;agrega libro a la lista
(defn add-book [library book]
  (conj library book)) ;; conj es como add

;;lista los libros de la lista
(defn list-books [library]
  (doseq [book library] ;;doseq itera una secuancia de elementos (responsable de que eligiera hacer este ejercicio)
    (println book)))
