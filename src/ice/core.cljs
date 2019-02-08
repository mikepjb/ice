(ns ice.core
  (:require [ice.nrepl :as nrepl]))

(defn onBufWrite [] (.log js/console "Buffer written!"))

(defn setup [& _]
  (println "Starting setup method for ice")
  (.log js/console "Buffer written!")
  (set!
    (.-exports js/module)
    (fn [plugin]
      (defn setLine [] (.setLine (.-nvim plugin) "A line from clojurescript, for your troubles"))
      (.registerCommand
        plugin
        "SetMyLine"
        #js [(.. plugin -nvim -buffer) setLine])
      (.registerAutocmd plugin "BufWritePre" onBufWrite #js {:pattern "*"})))
  )

(set! *main-cli-fn* setup)
