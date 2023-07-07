(ns app.view.root
  (:require [app.lib :refer [defnc]]
            [helix.dom :as d]
            ["react-dom/client" :as rdc]
            [app.controller.weather]))

(defnc view []
  (d/div {:class "container"}
         (d/div {:class "search-box"}
                (d/i {:class "fa-solid fa-location-dot"})
                (d/input {:type "text"
                          :placeholder "Enter your location"})
                (d/button {:class "fa-solid fa-magnifying-glass"
                           :on-click app.controller.weather/search}))

         (d/div {:class "location-not-found"}
                (d/img {:src "images/location-not-found.png"})
                (d/p "Oops! Invalid location!"))

         (d/div {:class "weather-box"}
                (d/img {:src ""})
                (d/p {:class "temperature"})
                (d/p {:class "description"}))

         (d/div {:class "weather-details"}
                (d/div {:class "humidity"}
                       (d/i {:class "fa-solid fa-water"})
                       (d/div {:class "text"}
                              (d/span)
                              (d/p "Humidity")))
                (d/div {:class "wind"}
                       (d/i {:class "fa-solid fa-wind"})
                       (d/div {:class "text"}
                              (d/span)
                              (d/p "Wind Speed"))))))

(defonce root (rdc/createRoot (js/document.getElementById "root")))