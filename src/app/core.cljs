(ns app.core
  (:require [app.lib :refer [defnc]]
            [helix.core :refer [$]]
            [helix.dom :as d]
            [helix.hooks :as hooks]
            ["react-dom/client" :as rdc]
            ["@tauri-apps/api/tauri" :as tauri]))

(defn open-file [])

(defnc root-view []
  (d/div {:class "container"}
         (d/button {:id "submit-file"
                    :text "open file"
                    :on-click open-file})))

(defonce root (rdc/createRoot (js/document.getElementById "root")))

(defn ^:dev/after-load mount-ui []
  (.render root ($ root-view)))

(defn ^:export main []
  (mount-ui))
