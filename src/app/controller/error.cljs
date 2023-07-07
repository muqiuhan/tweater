(ns app.controller.error)

(defn location-not-found []
  (let [container (js/document.querySelector ".container")
        weather-box (js/document.querySelector ".weather-box")
        weather-details (js/document.querySelector ".weather-details")
        location-not-found (js/document.querySelector ".location-not-found")]
    (.setProperty (. container -style) "height" "400px")
    (.setProperty (. weather-box -style) "display" "none")
    (.setProperty (. weather-details -style) "display" "none")
    (.setProperty (. location-not-found -style) "display" "block")
    (.add (. location-not-found -classList) "fadeIn")))