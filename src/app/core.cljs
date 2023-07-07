(ns app.core
  (:require [app.lib :refer [defnc]]
            [helix.core :refer [$]]
            [helix.dom :as d]
            [helix.hooks :as hooks]
            ["react-dom/client" :as rdc]))

(defn request-api [city]
  (let [api-key "a98bb0392b4fb2a0796be798ae4a38ac"]
    (when [(seq city)]
      (->
       (js/fetch
        (str "https://api.openweathermap.org/data/2.5/weather?q="
             city
             "&units=metric"
             "&appid="
             api-key))
       (.then (fn [response] (.json response)))))))

(defn error-location-not-found []
  (let [container (js/document.querySelector ".container")
        weather-box (js/document.querySelector ".weather-box")
        weather-details (js/document.querySelector ".weather-details")
        location-not-found (js/document.querySelector ".location-not-found")]
    (.setProperty (. container -style) "height" "400px")
    (.setProperty (. weather-box -style) "display" "none")
    (.setProperty (. weather-details -style) "display" "none")
    (.setProperty (. location-not-found -style) "display" "block")
    (.add (. location-not-found -classList) "fadeIn")))

(defn set-weather-info [json]
  (let [container (js/document.querySelector ".container")
        image (js/document.querySelector ".weather-box img")
        temperature (js/document.querySelector ".weather-box .temperature")
        weather-details (js/document.querySelector ".weather-details")
        weather-box (js/document.querySelector ".weather-box")
        description (js/document.querySelector ".weather-box .description")
        humidity (js/document.querySelector ".weather-details .humidity span")
        wind (js/document.querySelector ".weather-details .wind span")
        location-not-found (js/document.querySelector ".location-not-found")
        weather (((json "weather") 0) "main")]

    (.setProperty (. location-not-found -style) "display" "none")
    (.remove (. location-not-found -classList) "fadeIn")

    (cond
      (= "Clear" weather) (.setAttribute image "src" "images/clear.png")
      (= "Rain" weather) (.setAttribute image "src" "images/rain.png")
      (= "Snow" weather) (.setAttribute image "src" "images/snow.png")
      (= "Haze" weather) (.setAttribute image "src" "images/haze.png")
      (= "Mist" weather) (.setAttribute image "src" "images/mist.png")
      (= "Clouds" weather) (.setAttribute image "src" "images/cloud.png")
      :else (.setAttribute image "src" ""))

    (set! (.-innerHTML temperature) ((json "main") "temp"))
    (set! (.-innerHTML description) (((json "weather") 0) "description"))
    (set! (.-innerHTML humidity) ((json "main") "humidity"))
    (set! (.-innerHTML wind) ((json "wind") "speed"))

    (.setProperty (. weather-box -style) "display" "")
    (.setProperty (. weather-details -style) "display" "")
    (.add (. weather-box -classList) "fadeIn")
    (.add (. weather-details -classList) "fadeIn")
    (.setProperty (. container -style) "height" "590px")))

(defn search-weather []
  (let [city (.-value (js/document.querySelector ".search-box input"))]
    (if (empty? city)
      '()
      (-> (request-api city)
          (.then (fn [response]
                   (-> (js/JSON.stringify response)
                       js/JSON.parse
                       js->clj)))
          (.then (fn [json]
                   (if (= "404" (json "cod"))
                     (error-location-not-found)
                     (set-weather-info json))))))))

(defnc root-view []
  (d/div {:class "container"}
         (d/div {:class "search-box"}
                (d/i {:class "fa-solid fa-location-dot"})
                (d/input {:type "text"
                          :placeholder "Enter your location"})
                (d/button {:class "fa-solid fa-magnifying-glass"
                           :on-click search-weather}))

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

(defn ^:dev/after-load mount-ui []
  (.render root ($ root-view)))

(defn ^:export main []
  (mount-ui))
