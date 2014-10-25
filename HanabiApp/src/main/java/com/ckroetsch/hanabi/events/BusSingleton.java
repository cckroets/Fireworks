package com.ckroetsch.hanabi.events;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * @author curtiskroetsch
 */
public class BusSingleton {

   static Bus sBus = new Bus(ThreadEnforcer.MAIN);

   public static Bus get() {
       return sBus;
   }

}
