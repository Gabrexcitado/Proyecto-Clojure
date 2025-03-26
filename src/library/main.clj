;;Gabriel Hidalgo Linde 24939
;;Programa de biblioteca utilizando estructuras y tipos de datos
(ns library.main
  (:require [library.core :as core])
  (:gen-class))

;;genera el libro con el input del usuario
(defn read-book []
  (println "Ingrese el titulo del libro: ")
  ;;'let' permite crear variables locales y al agregar 'do' seguir una secuencia de operaciones
    ;;para que, en un solo bloque, se realice una operacion y la asignacion de variable (en este caso la creacion de el libro completo) 
  (let [  
        title (read-line)
        author (do (println "Ingrese el autor del libro") (read-line) )
        year (do (println "Ingrese el anio de publicacion") (Integer/parseInt (read-line)))
        id (rand-int 500) ;;no supe hacer que el id se vaya sumando
        ]
    {:id id, :title title, :author author, :year year}))

;;pregunta al usuario el libro y lo agrega a la lista
(defn -main []
  (loop [biblioteca []] ;;loop inicia un bucle
     (println "Seleccione una opción: \n(1) Agregar libro \n(2) Listar libros \n(3) Salir")
     (let [opcion (read-line)]
       (cond
         (= opcion "1") (let [libro (read-book)]
                          (recur (core/add-book biblioteca libro)))
         (= opcion "2") (do (core/list-books biblioteca)
                            (recur biblioteca))
         (= opcion "3") (do (core/list-books biblioteca)
                            (println "\nSaliendo del programa...\n"))
         :else (do (println "Opción no válida")
                   (recur biblioteca)))))) ;;vuelve al inicio del bucle
  
(-main)