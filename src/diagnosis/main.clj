;;Gabriel Hidalgo Linde 24939
;;Programa de diagnostico médico utilizando otras estructuras de datos
(ns diagnosis.main
  (:require [diagnosis.core :as core])
  (:gen-class))

;; Atom para almacenar los pacientes
(def pacientes (atom []))
(def id-counter (atom 0)) ;; contador para los IDs

;; Lista de enfermedades para diagnóstico
(def enfermedades
  [{:nombre "Gripe" :sintomas #{:fiebre :dolor_cabeza :tos}}
   {:nombre "COVID-19" :sintomas #{:dificultad_respirar :dolor_garganta :perdida_olfato}}
   {:nombre "Alergia" :sintomas #{:estornudos :picazon_ojos :congestion_nasal}}
   {:nombre "Migraña" :sintomas #{:sensibilidad_luz :nauseas :vision_borrosa}}
   {:nombre "Neumonia" :sintomas #{:escalofrios :dolor_pecho :fatiga_extrema}}
   {:nombre "Amigdalitis" :sintomas #{:dificultad_tragar :inflamacion_amigdalas :mal_aliento}}
   {:nombre "Gastroenteritis" :sintomas #{:vomitos :diarrea :dolor_abdominal}}
   {:nombre "Resfriado" :sintomas #{:secrecion_nasal :lagrimeo :malestar_general}}
   {:nombre "Asma" :sintomas #{:silbidos_pecho :opresion_pecho :dificultad_al_exhalar}}
   {:nombre "Otitis" :sintomas #{:dolor_oido :dificultad_oido :secrecion_oido}}])



(defn -main []
  (loop []
    ;;menu principal
    (println "\nSistema de Diagnostico Medico")
    (println "---------------------------------") 
    (println "1. Registrar paciente")
    (println "2. Diagnosticar paciente")
    (println "3. Listar pacientes")
    (println "4. Salir\n")
    (print "Elija una opcion: ")  
    (flush) ;; Asegura que el mensaje se muestre antes de esperar input
    (let [opcion (read-line)]
      (cond
        
        ;;Nuevo paciente
        (= opcion "1") (do
                         (core/new-pacient pacientes id-counter)
                         (recur))
        
        ;;Diagnosticar paciente
        (= opcion "2") (do
                         (core/diagnose-pacient pacientes enfermedades)
                         (recur))
        
        ;;Listar pacientes
        (= opcion "3") (do
                         (core/list-pacients pacientes)
                         (recur))
        
        ;;Salir
        (= opcion "4") (println "Saliendo del programa...")

        ;;default
        :else (do
                (println ">>>Opcion no valida")
                (recur))))))