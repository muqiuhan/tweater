(ns app.controller.openweather)

(defn request [city]
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