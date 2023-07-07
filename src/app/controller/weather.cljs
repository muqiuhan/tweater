(ns app.controller.weather
   (:require [app.controller.openweather :as open-weather]
             [app.controller.error :as error]))


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

     (set! (.-innerHTML temperature) (str ((json "main") "temp") "°C"))
     (set! (.-innerHTML description) (((json "weather") 0) "description"))
     (set! (.-innerHTML humidity) (str ((json "main") "humidity") "%"))
     (set! (.-innerHTML wind) (str ((json "wind") "speed") " km/h"))

     (.setProperty (. weather-box -style) "display" "")
     (.setProperty (. weather-details -style) "display" "")
     (.add (. weather-box -classList) "fadeIn")
     (.add (. weather-details -classList) "fadeIn")
     (.setProperty (. container -style) "height" "590px")))

(defn search []
  (let [city (.-value (js/document.querySelector ".search-box input"))]
    (if (empty? city)
      '()
      (-> (open-weather/request city)
          (.then (fn [response]
                   (-> (js/JSON.stringify response)
                       js/JSON.parse
                       js->clj)))
          (.then (fn [json]
                   (js/console.log "test")
                   (if (= "404" (json "cod"))
                     (error/location-not-found)
                     (set-weather-info json))))))))