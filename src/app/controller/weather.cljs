(ns app.controller.weather
  (:require [app.controller.openweather :as open-weather]
            [app.controller.error :as error]
            [app.view.components :as components]))

(defn set-weather-info [json]
  (let [weather (((json "weather") 0) "main")]
    (.setProperty (. components/location-not-found -style) "display" "none")
    (.remove (. components/location-not-found -classList) "fadeIn")

    (cond
      (= "Clear" weather) (.setAttribute components/image "src" "images/clear.png")
      (= "Rain" weather) (.setAttribute components/image "src" "images/rain.png")
      (= "Snow" weather) (.setAttribute components/image "src" "images/snow.png")
      (= "Haze" weather) (.setAttribute components/image "src" "images/haze.png")
      (= "Mist" weather) (.setAttribute components/image "src" "images/mist.png")
      (= "Clouds" weather) (.setAttribute components/image "src" "images/cloud.png")
      :else (.setAttribute components/image "src" ""))

    (set! (.-innerHTML components/temperature) ((json "main") "temp"))
    (set! (.-innerHTML components/description) (((json "weather") 0) "description"))
    (set! (.-innerHTML components/humidity) ((json "main") "humidity"))
    (set! (.-innerHTML components/wind) ((json "wind") "speed"))

    (.setProperty (. components/weather-box -style) "display" "")
    (.setProperty (. components/weather-details -style) "display" "")
    (.add (. components/weather-box -classList) "fadeIn")
    (.add (. components/weather-details -classList) "fadeIn")
    (.setProperty (. components/container -style) "height" "590px")))

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
                   (if (= "404" (json "cod"))
                     (error/location-not-found)
                     (set-weather-info json))))))))