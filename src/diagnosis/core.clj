(ns diagnosis.core
   ;;dependencias/ imports
  (:require [clojure.string :as str]
            [clojure.set :as set]))

;; Función para registrar un nuevo paciente
(defn new-pacient [pacientes id-counter]
  (println "\nIngrese el nombre: ")
  (let [nombre (read-line)
        edad (do (println "Ingrese la edad: ") (Integer/parseInt (read-line)))

        sintomas (do
                   (println "Ingrese los sintomas separados por coma (ver en codigo): ")
                   (->> (clojure.string/split (read-line) #",")
                        (map clojure.string/trim)
                        (map keyword) ;; convierte los síntomas a keywords para poder compararlos
                        set)) 
        
        id (swap! id-counter inc)
        diagnostico "Sin diagnostico"
        paciente {:id id :nombre nombre :edad edad :sintomas sintomas :diagnostico diagnostico}]
    (swap! pacientes conj paciente) ;; agrega el paciente a la lista

    (println (str "\n>>> Paciente [id=" id "] registrado con exito"))))


;; Función para diagnosticar a un paciente
(defn diagnose-pacient [pacientes enfermedades]
  (println "\nIngrese el ID del paciente a diagnosticar: ")
  (let [id (Integer/parseInt (read-line))
        paciente (first (filter #(= (:id %) id) @pacientes))] ;; busca el paciente por ID
    ;;si no existe
    (if (nil? paciente)
      (println "\n>>> Paciente no encontrado")

      ;;si existe
      (let [sintomas-paciente (:sintomas paciente)
            diagnostico (or
                         (some (fn [{:keys [nombre sintomas]}]
                                 (when (not-empty (set/intersection sintomas sintomas-paciente)) ;; verifica si hay intersección entre los síntomas
                                   nombre))
                               enfermedades)
                         
                         ;;si no hay coincidencias
                         "Desconocido")
            
            actualizado (assoc paciente :diagnostico diagnostico)]
        (swap! pacientes (fn [ps] (mapv #(if (= (:id %) id) actualizado %) ps))) ;; swap actualiza el paciente en la lista
        (println "\n>>> Diagnostico completado: " diagnostico)))))

;; Función para listar pacientes
(defn list-pacients [pacientes]
  (if (empty? @pacientes)
    (println "\n>>> No hay pacientes registrados")
    (doseq [p @pacientes]
      (println (str "\nID: " (:id p)
                    "\nNombre: " (:nombre p)
                    "\nEdad: " (:edad p)
                    "\nSintomas: " (str/join ", " (:sintomas p))
                    "\nDiagnostico: " (:diagnostico p)
                    "\n---------------------")))))
