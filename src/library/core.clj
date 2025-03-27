;;Documento que guarda las funciones de la biblioteca 
(ns library.core 
  (:require
   [clojure.data.csv :as csv]
   [clojure.java.io :as io]))

;;agrega libro a la lista
(defn add-book [library book]
  (conj library book)) ;; conj es como add

;;lista los libros de la lista
(defn list-books [library]
  (doseq [book library] ;;doseq itera una secuancia de elementos (responsable de que eligiera hacer este ejercicio)
    (println book)))

;;busca el libro segun el id
(defn search-book [library id]
  (filter #(= (:id %) id) library)) ;;filter filtra los elementos de una secuencia que cumplan con una condicion

;;elimina un libro con un id especifico
(defn delete-book [library id]
  (remove #( = (:id %) id) library))

;;guarda los libros a un csv
(defn save-books [library filename]
  (with-open [writer (io/writer filename)]
    (csv/write-csv writer
                   (cons ["id" "title" "author" "year"]
                         (map (fn [{:keys [id title author year]}]
                                [(str id) title author (str year)])
                              library)))))

;;carga los libros del csv al mapa
(defn load-books [file]
  (with-open [reader (io/reader file)]
    (doall  ;; forza la lectura de todo el documento
     (map (fn [[id title author year]]
            {:id (Integer/parseInt id), :title title, :author author, :year (Integer/parseInt year)})
          (rest (csv/read-csv reader))))))