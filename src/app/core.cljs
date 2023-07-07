(ns app.core
  (:require [helix.core :refer [$]]
            [app.view.root]))

(defn ^:dev/after-load mount-ui []
  (.render app.view.root/root ($ app.view.root/view)))

(defn ^:export main []
  (mount-ui))
