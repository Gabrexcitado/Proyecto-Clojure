;;Gabriel Hidalgo Linde 24939
;;Programa simple que muestra como imprimir y asignar variables con Clojure
(ns hola-mundo.core
  (:gen-class))

(defn -main
  [& args]
  (println "Hola, Mundo!")
  (def suma (+ 2 20)) ;;Clojure utiliza formato prefix para operaciones
  (println suma))

