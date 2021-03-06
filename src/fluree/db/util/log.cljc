(ns fluree.db.util.log
  (:require #?(:clj  [clojure.tools.logging :as log]
               :cljs [goog.log :as glog]))
  #?(:cljs (:import goog.debug.Console)))


#?(:cljs
   (def logger
     (glog/getLogger "app")))

#?(:cljs
   (def levels {:severe  goog.debug.Logger.Level.SEVERE
                :warning goog.debug.Logger.Level.WARNING
                :info    goog.debug.Logger.Level.INFO
                :config  goog.debug.Logger.Level.CONFIG
                :fine    goog.debug.Logger.Level.FINE
                :finer   goog.debug.Logger.Level.FINER
                :finest  goog.debug.Logger.Level.FINEST}))

#?(:cljs
   (defn log-to-console! []
     (.setCapturing (goog.debug.Console.) true)))

#?(:cljs
   (defn set-level! [level]
     (.setLevel logger (get levels level (:info levels)))))

(defn fmt [msgs]
  (apply str (interpose " " (map pr-str msgs))))

(defn error [& s]
  #?(:clj  (if (instance? Exception (first s))
             (log/error (first s) (fmt (rest s)))
             (log/error (fmt (rest s))))
     :cljs (glog/error logger (fmt s))))

(defn warn [& s]
  #?(:clj  (log/warn (fmt s))
     :cljs (glog/warning logger (fmt s))))

(defn info [& s]
  #?(:clj  (log/info (fmt s))
     :cljs (glog/info logger (fmt s))))

(defn debug [& s]
  #?(:clj  (log/debug (fmt s))
     :cljs (glog/fine logger (fmt s))))


(defn trace [& s]
  #?(:clj  (log/trace (fmt s))
     :cljs (glog/fine logger (fmt s))))


#?(:cljs
   (set-level! :info))

#?(:cljs
   (log-to-console!))



