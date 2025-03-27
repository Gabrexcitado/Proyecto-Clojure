;;Gabriel Hidalgo Linde 24939
;;Programa de library mas detallado y funcional
(ns library.main
  (:require [library.core :as core])
  (:gen-class))

;;en Clojure las variables son inmutables asi que se definen funciones\

(def id-counter (atom 0)) ;;contador para id
(def file "library.csv") ;;archivo csv


;;genera un libro con los inputs del usuario
(defn read-book []
  (println "\nIngrese el titulo del libro: ")
  (let [title (read-line)
        author (do (println "Ingrese el autor del libro") (read-line))
        year (do (println "Ingrese el anio de publicacion") (Integer/parseInt (read-line)))
        id (swap! id-counter inc)] ;;swap! suma a atomos
    {:id id, :title title, :author author, :year year}))


(defn -main []
  
  (let [library (vec (core/load-books file))]
    (reset! id-counter (count library)) ;;resetea el contador luego de cargar los libros

  (loop [library library] ;;sobreescribe library para no ignorar los libros ya agregados
    (println "\n(1) Agregar libro \n(2) Listar libros \n(3) Buscar libro por ID \n(4) Eliminar libro \n(5) Salir \nIngrese una opción:")
    (let [option (read-line)]
      (cond
        ;;agregar
        (= option "1") (let [book (read-book)]
                         (let [new-library (core/add-book library book)]
                           (core/save-books new-library file)
                           (recur new-library)))
      

        ;;listar
        (= option "2") (do (println "\nLibros registrados: ")
                           (core/list-books library)
                           (println "")
                           (recur library))

        ;;buscar
        (= option "3") (do (println "\nIngrese el ID del libro a buscar: ")
                           (let [id (Integer/parseInt (read-line))
                                 result (core/search-book library id)]

                             (if (empty? result)
                               (println "\n>>>Libro no encontrado")
                               (println "\n LIBRO:" result)))

                           (recur library))

        ;;eliminar
        (= option "4") (do (println "\nIngrese el ID del libro a eliminar: ")
                           (let [id (Integer/parseInt (read-line))
                                 result (core/search-book library id)]

                             (if (empty? result)
                               (do (println "\n>>> Libro no encontrado") (recur library))
                               (let [new-library (core/delete-book library id)]
                                 (core/save-books new-library file)
                                 (println "\nLibro eliminado con exito")
                                 (recur new-library))))) 

        ;;salir
        (= option "5") (do (println "\nLibros en la biblioteca")
                           (core/list-books library)
                           (println "\nSaliendo del programa...")
                           (System/exit 0))


        :else (do (println "Opción no válida")
                  (recur library)))))))

;; Ejecutar el programa
(-main)