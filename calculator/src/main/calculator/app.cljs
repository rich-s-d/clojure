(ns calculator.app
  (:require
    [reagent.core :as r]
    [reagent.dom :as rdom]))

(def current-value (r/atom 0))
(def zero-atom (r/atom ""))
(def xy-atom (atom {:x 0 :y "" :z "" :sym ""}))
(def mem-atom (atom 0))


(defn get-value [event]
      (print @xy-atom)
      (js/console.log event)
      ;(reset! current-value event)
      ;(swap! xy-atom update-in [:x] + event )
      (swap! xy-atom update-in [:y] + event)
      (reset! current-value (:y @xy-atom))
      (print @xy-atom))


(defn clear-display []
      (reset! current-value 0)
      (reset! xy-atom {:x 0 :y "" :z "" :sym ""})
      )

(defn calculate [event]
      ;calculate result for every computing
      ;(@y @sym )
      (do
        (print @xy-atom)
        (swap! xy-atom update-in [:sym] + event )
        (swap! xy-atom update-in [:z] + (:y @xy-atom))
        (swap! xy-atom assoc-in [:y] "")
        (print @xy-atom)
        )
      )

(defn result []
      (print @xy-atom)
      (reset! current-value (js/eval (str(:z @xy-atom)
                                         (:sym @xy-atom)
                                         (:y @xy-atom)
                                         )))
      (print @xy-atom)
      )

(defn add-to-memory []
      (print @mem-atom)
      (print @xy-atom)
      (swap! mem-atom + @current-value)
      (print @mem-atom)
      (print @xy-atom)
      )

(defn mem-recall []
      (print @mem-atom)
      (print @xy-atom)
      (swap! xy-atom assoc-in [:y] @mem-atom)
      (reset! current-value @mem-atom)
      (print @mem-atom)
      (print @xy-atom)
      )

(defn mem-reset []
      (print @mem-atom)
      (reset! mem-atom 0))
      (print @mem-atom)

(defn pi []
      (print @xy-atom)
      (reset! current-value (.-PI js/Math))                               ;
      (swap! xy-atom assoc-in [:y] @current-value)
      (print @xy-atom)
      )

(defn x-squared []
      (print @xy-atom)
      (reset! current-value (* @current-value @current-value))
      (swap! xy-atom assoc-in [:y] @current-value)
      (print @xy-atom)
      )

(defn mini-app []
      [:table {:border "1"}
       [:tbody
        [:tr
         [:td {:colspan "3"} [:input#result {:readonly "" :type "text" :value  @current-value  }]]
         [:td [:input {:type "button" :value "c" :on-click #(clear-display)} ]]]
        [:tr
         [:td [:input {:type "button" :value "1" :on-click #(get-value 1)  }]]
         [:td [:input {:type "button" :value "2" :on-click #(get-value 2) }]]
         [:td [:input {:type "button" :value "3" :on-click #(get-value 3)}]]
         [:td [:input {:type "button" :value "/" :on-click #(calculate "/")}]]]
        [:tr
         [:td [:input {:type "button" :value "4" :on-click #(get-value 4)}]]
         [:td [:input {:type "button" :value "5" :on-click #(get-value 5)}]]
         [:td [:input {:type "button" :value "6" :on-click #(get-value 6)}]]
         [:td [:input {:type "button" :value "-" :on-click #(calculate "-")}]]]
        [:tr
         [:td [:input {:type "button" :value "7" :on-click #(get-value 7)}]]
         [:td [:input {:type "button" :value "8" :on-click #(get-value 8)}]]
         [:td [:input {:type "button" :value "9" :on-click #(get-value 9)}]]
         [:td [:input {:type "button" :value "+" :on-click #(calculate "+")}]]]
        [:tr
         [:td [:input {:type "button" :value "." :on-click #(get-value ".")}]]
         [:td [:input {:type "button" :value "0" :on-click #(get-value 0)}]]
         [:td [:input {:type "button" :value "=" :on-click #(result)}]]
         [:td [:input {:type "button" :value "*" :on-click #(calculate "*")}]]]
        [:tr
         [:td [:input {:type "button" :value "π" :on-click #(pi)}]]
         [:td [:input {:type "button" :value "MemReset" :on-click #(mem-reset)}]]
         [:td [:input {:type "button" :value "MemRecall" :on-click #(mem-recall)}]]
         [:td [:input {:type "button" :value "Mem+" :on-click #(add-to-memory)}]]]
        [:tr
         [:td [:input {:type "button" :value "x²" :on-click #(x-squared)}]]
         [:td [:input {:type "button" :value "Sp." :on-click #()}]]
         [:td [:input {:type "button" :value "Sp." :on-click #()}]]
         [:td [:input {:type "button" :value "Sp." :on-click #()}]]]]])

(defn ^:export run []
      (rdom/render [mini-app] (js/document.getElementById "app")))
(defn ^:export reload []
      (.log js/console "reload...")
      (run))
