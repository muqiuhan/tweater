(ns app.view.components)

(def container (js/document.querySelector ".container"))
(def image (js/document.querySelector ".weather-box img"))
(def temperature (js/document.querySelector ".weather-box .temperature"))
(def weather-details (js/document.querySelector ".weather-details"))
(def weather-box (js/document.querySelector ".weather-box"))
(def description (js/document.querySelector ".weather-box .description"))
(def humidity (js/document.querySelector ".weather-details .humidity span"))
(def wind (js/document.querySelector ".weather-details .wind span"))
(def location-not-found (js/document.querySelector ".location-not-found"))