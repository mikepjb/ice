(ns ice.core)

(defn onBufWrite [] (.log js/console "Buffer written!"))

(set!
  (.-exports js/module)
  (fn [plugin]
    (defn setLine [] (.setLine (.-nvim plugin) "A line from clojurescript, for your troubles"))
    (.registerCommand
      plugin
      "SetMyLine"
      #js [(.. plugin -nvim -buffer) setLine])
    (.registerAutocmd plugin "BufWritePre" onBufWrite #js {:pattern "*"})))
